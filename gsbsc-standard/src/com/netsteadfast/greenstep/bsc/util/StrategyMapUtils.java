/* 
 * Copyright 2012-2017 bambooCORE, greenstep of copyright Chen Xin Nien
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * -----------------------------------------------------------------------
 * 
 * author: 	Chen Xin Nien
 * contact: chen.xin.nien@gmail.com
 * 
 */
package com.netsteadfast.greenstep.bsc.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.bsc.service.IPerspectiveService;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.po.hbm.BbPerspective;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.util.LocaleLanguageUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

@SuppressWarnings("unchecked")
public class StrategyMapUtils {
	protected static Logger logger = Logger.getLogger(StrategyMapUtils.class);
	private static final String _CONFIG = "META-INF/strategy-map.json";
	private static String _datas = " { } ";
	private static Map<String, Object> _configDataMap;	
	private static int MIN_WIDTH = 800;
	private static int MIN_HEIGH = 600;
	private static int MAX_WIDTH = 4096;
	private static int MAX_HEIGH = 2160;
	private static String SRC_IMG = "META-INF/resource/s-map-bg-grid.png";
	private static IVisionService<VisionVO, BbVision, String> visionService;
	private static IPerspectiveService<PerspectiveVO, BbPerspective, String> perspectiveService;
	
	static {
		try {
			InputStream is = LocaleLanguageUtils.class.getClassLoader().getResource( _CONFIG ).openStream();
			_datas = IOUtils.toString(is, Constants.BASE_ENCODING);
			is.close();
			is = null;
			_configDataMap = loadDatas();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null==_configDataMap) {
				_configDataMap = new HashMap<String, Object>();
			}
			if (_configDataMap.get("minWidth") != null) {
				MIN_WIDTH = (int) _configDataMap.get("minWidth");
			}
			if (_configDataMap.get("minHeight") != null) {
				MIN_HEIGH = (int) _configDataMap.get("minHeight");
			}		
			if (_configDataMap.get("maxWidth") != null) {
				MAX_WIDTH = (int) _configDataMap.get("maxWidth");
			}
			if (_configDataMap.get("maxHeight") != null) {
				MAX_HEIGH = (int) _configDataMap.get("maxHeight");
			}	
			if (!StringUtils.isBlank((String)_configDataMap.get("background-img"))) {
				SRC_IMG = (String) _configDataMap.get("background-img");
			}
		}		
		visionService = (IVisionService<VisionVO, BbVision, String>) AppContext.getBean("bsc.service.VisionService");
		perspectiveService = (IPerspectiveService<PerspectiveVO, BbPerspective, String>) AppContext.getBean("bsc.service.PerspectiveService");
	}
	
	public static Map<String, Object> loadDatas() {
		Map<String, Object> datas = null;
		try {
			datas = (Map<String, Object>)new ObjectMapper().readValue( _datas, LinkedHashMap.class );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}	
	
	public static int getWidth(int width) {
		if (width < MIN_WIDTH) {
			return MIN_WIDTH;
		}
		if (width > MAX_WIDTH) {
			return MAX_WIDTH;
		}
		return width;
	}
	
	public static int getHeigth(int height) {
		if (height < MIN_HEIGH) {
			return MIN_HEIGH;
		}
		if (height > MAX_HEIGH) {
			return MAX_HEIGH;
		}
		return height;
	}
	
	public static byte[] getBackgroundImage(String visionOid, int width, int height) throws Exception {
		if (StringUtils.isBlank(visionOid)) {
			throw new Exception("vision OID is blank!");
		}
		VisionVO vision = new VisionVO();
		vision.setOid(visionOid);
		DefaultResult<VisionVO> result = visionService.findObjectByOid(vision);
		vision = result.getValue();
		return getBackgroundImage(vision, width, height);
	}
	
	public static byte[] getBackgroundImage(VisionVO vision, int width, int height) throws Exception {
		byte[] data = null;
		List<PerspectiveVO> perspectivesList = perspectiveService.findForListByVisionOid(vision.getOid());
		
		BufferedImage bi = new BufferedImage(getWidth(width), getHeigth(height), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bi.createGraphics();	
		Font font = new Font("", Font.BOLD, 16);
		g2.setFont(font);		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);		
		
		int fontLeft = 3;
		int fontAddY = 150;
		int fontNowY = 80;
		int lineAddY = 60;
		
		// 填滿 grid 圖片的底
		BufferedImage bg = ImageIO.read( StrategyMapUtils.class.getClassLoader().getResource(SRC_IMG) );
		for (int y=0; y<height; y+=bg.getHeight()) {
			for (int x=0; x<width; x+=bg.getWidth()) {
				g2.drawImage(bg, x, y, null);
			}
		}
		
		Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
		
		for (int i=0; perspectivesList != null && i < perspectivesList.size(); i++) {
			
			PerspectiveVO perspective = perspectivesList.get(i);
			
			if (fontNowY > bi.getHeight()) {
				logger.warn("Perspective item over heigh, no paint it : " + perspective.getName());
				continue;
			}
			if (fontLeft > bi.getWidth()) {
				logger.warn("Perspective item over width, no paint it : " + perspective.getName());
				continue;				
			}
			
			g2.setPaint( new Color(64, 64, 64) );
			g2.drawString(perspective.getName(), fontLeft, fontNowY);
			
			g2.setStroke(dashed);
			g2.drawLine(0, fontNowY+lineAddY, width, fontNowY+lineAddY);
			
			fontNowY = fontNowY + fontAddY;
		}
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			ImageIO.write(bi, "PNG", baos);
			data = baos.toByteArray();
			baos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			baos = null;
		}
		return data;
	}
	
	public static String createUpload(String visionOid, int width, int height) throws ServiceException, Exception {
		if (StringUtils.isBlank(visionOid)) {
			throw new Exception("vision OID is blank!");
		}
		VisionVO vision = new VisionVO();
		vision.setOid(visionOid);
		DefaultResult<VisionVO> result = visionService.findObjectByOid(vision);
		vision = result.getValue();
		return createUpload(vision, width, height);
	}	
	
	public static String createUpload(VisionVO vision, int width, int height) throws ServiceException, Exception {
		byte[] data = getBackgroundImage(vision, width, height);
		return UploadSupportUtils.create(Constants.getSystem(), UploadTypes.IS_TEMP, false, data, "strategy-map-background.png");
	}
	
	// 主要給 mobile-version 用的, 因為 mobile-version 沒有 使勇 CommonLoadUploadFileAction , 所以換個方式來放 background-image:url
	public static String getBackgroundImageBase64FromUpload(String uploadOid) throws ServiceException, Exception {
		byte data[] = UploadSupportUtils.getDataBytes(uploadOid);
		if (data == null) {
			return "";
		}
		return "data:image/gif;base64," + Base64.getEncoder().encodeToString(data);
	}
	
}
