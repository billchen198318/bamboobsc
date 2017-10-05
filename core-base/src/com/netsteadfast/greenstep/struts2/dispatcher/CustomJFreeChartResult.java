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
package com.netsteadfast.greenstep.struts2.dispatcher;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.dispatcher.ChartResult;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;

public class CustomJFreeChartResult extends ChartResult {
	protected Logger logger=Logger.getLogger(CustomJFreeChartResult.class);
	private static final long serialVersionUID = -8295368376627015016L;
	private static final int MAX_WIDTH = 1024;
	private static final int MAX_HEIGHT = 1024;
	private static final int MIN_WIDTH = 96;
	private static final int MIN_HEIGHT = 96;
	
	@Override
	public void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {		
		Object newWidth = ActionContext.getContext().getValueStack().findValue("width");
		Object newHeight = ActionContext.getContext().getValueStack().findValue("height");
		if ( NumberUtils.isCreatable(String.valueOf(newWidth)) && NumberUtils.isCreatable(String.valueOf(newHeight)) ) {			
			int w = NumberUtils.toInt( String.valueOf(newWidth) );
			int h = NumberUtils.toInt( String.valueOf(newHeight) );
			if ( w > MAX_WIDTH ) {
				w = MAX_WIDTH;
			}
			if ( h > MAX_HEIGHT ) {
				h = MAX_HEIGHT;				
			}
			if ( w < MIN_WIDTH ) {
				w = MIN_WIDTH;				
			}
			if ( h < MIN_HEIGHT ) {
				h = MIN_HEIGHT;
			}
			super.setWidth(String.valueOf(w));
			super.setHeight(String.valueOf(h));
			logger.info("reset chart width=" + w + " , heigh=" + h);			
		}
		super.doExecute(finalLocation, invocation);
	}

}
