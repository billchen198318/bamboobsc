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
package com.netsteadfast.greenstep.bsc.util;

import java.util.List;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.bsc.service.IScoreColorService;
import com.netsteadfast.greenstep.po.hbm.BbScoreColor;
import com.netsteadfast.greenstep.vo.ScoreColorVO;

@SuppressWarnings("unchecked")
public class BscScoreColorUtils {
	public static final String DEFAULT_BACKGROUND_COLOR = "#ffffff";
	public static final String DEFAULT_FONT_COLOR = "#000000";
	private static IScoreColorService<ScoreColorVO, BbScoreColor, String> scoreColorService;
	private static ThreadLocal<List<BbScoreColor>> scoreColorsThreadLocal = new ThreadLocal<List<BbScoreColor>>();
	
	static {
		scoreColorService = (IScoreColorService<ScoreColorVO, BbScoreColor, String>)
				AppContext.getBean("bsc.service.ScoreColorService");
	}
	
	public static void clearThreadLocal() {
		scoreColorsThreadLocal.remove();
	}
	
	public static void loadScoreColors() throws ServiceException, Exception {
		if ( scoreColorsThreadLocal.get()!=null && scoreColorsThreadLocal.get().size()>0 ) { // 2015-04-10 add
			return;
		}
		//List<BbScoreColor> scoreColors = scoreColorService.findListByParams(null); // 2015-04-10 rem
		List<BbScoreColor> scoreColors = scoreColorService.findListByParamsCacheable(); // 2015-04-10 add
		scoreColorsThreadLocal.set(scoreColors);
	}
	
	public static String getFontColor(int score) throws Exception {
		List<BbScoreColor> scoreColors = scoreColorsThreadLocal.get();
		if (scoreColors==null || scoreColors.size()<1) {
			return DEFAULT_FONT_COLOR;
		}
		String value = DEFAULT_FONT_COLOR;
		for (BbScoreColor scoreColor : scoreColors) {
			if ( score >= scoreColor.getScoreMin() && score <= scoreColor.getScoreMax() ) {
				value = scoreColor.getFontColor();
			}
		}
		return value;
	}
	
	public static String getFontColor(float score) throws Exception {
		return getFontColor((int)score);
	}	
	
	public static String getBackgroundColor(int score) throws Exception {
		List<BbScoreColor> scoreColors = scoreColorsThreadLocal.get();
		if (scoreColors==null || scoreColors.size()<1) {
			return DEFAULT_BACKGROUND_COLOR;
		}
		String value = DEFAULT_BACKGROUND_COLOR;
		for (BbScoreColor scoreColor : scoreColors) {
			if ( score >= scoreColor.getScoreMin() && score <= scoreColor.getScoreMax() ) {
				value = scoreColor.getBgColor();
			}
		}
		return value;		
	}
	
	public static String getBackgroundColor(float score) throws Exception {
		return getBackgroundColor((int)score);
	}
	
}
