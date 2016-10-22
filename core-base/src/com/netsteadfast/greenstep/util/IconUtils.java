/* 
 * Copyright 2012-2016 bambooCORE, greenstep of copyright Chen Xin Nien
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
package com.netsteadfast.greenstep.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.po.hbm.TbSysIcon;
import com.netsteadfast.greenstep.service.ISysIconService;
import com.netsteadfast.greenstep.vo.SysIconVO;

@SuppressWarnings("unchecked")
public class IconUtils {
	public static final String ICON_FOLDER = "./icons/";
	private static ISysIconService<SysIconVO, TbSysIcon, String> sysIconService;
	
	static {
		sysIconService = (ISysIconService<SysIconVO, TbSysIcon, String>)AppContext.getBean("core.service.SysIconService");
	}
	
	public static ISysIconService<SysIconVO, TbSysIcon, String> getSysIconService() {
		return sysIconService;
	}

	public static void setSysIconService(
			ISysIconService<SysIconVO, TbSysIcon, String> sysIconService) {
		IconUtils.sysIconService = sysIconService;
	}

	public static String getUrl(String basePath, String iconId) throws ServiceException, Exception {
		String url = "";
		if (StringUtils.isBlank(iconId)) {
			return url;
		}
		TbSysIcon sysIcon = new TbSysIcon();
		sysIcon.setIconId(iconId);
		sysIcon = sysIconService.findByEntityUK(sysIcon);
		if (sysIcon!=null && StringUtils.defaultString(sysIcon.getFileName()).trim().length()>0) {
			url = basePath + "/" + ICON_FOLDER + StringUtils.defaultString(sysIcon.getFileName());
		}
		return url;
	}
	
	public static String getMenuIcon(String basePath, String iconId) throws ServiceException, Exception {
		String img = getHtmlImg(basePath, iconId);
		if (!"".equals(img)) {
			img += "&nbsp;";
		}
		return img;		
	}
	
	public static String getHtmlImg(String basePath, String iconId) throws ServiceException, Exception {
		String img = "";
		String url = "";
		if (!"".equals( url=getUrl(basePath, iconId) ) ) {
			img = "<img src='" + url + "' border='0' />";
		}
		return img;
	}
	
	public static Map<String, String> getIconsSelectData() throws ServiceException, Exception {
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		List<TbSysIcon> iconList = sysIconService.findListByParams(null);
		if (null==iconList || iconList.size()<1) {
			return dataMap;
		}
		for (TbSysIcon entity : iconList) {
			String label = "<img src='./icons/" + entity.getFileName() + "' border='0'/>&nbsp;" + entity.getIconId();
			dataMap.put(entity.getOid(), label);
		}
		return dataMap;
	}
	
}
