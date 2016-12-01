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
package com.netsteadfast.greenstep.sys;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.model.YesNo;

public class StopOrReloadCXFServlet extends HttpServlet {
	private static final long serialVersionUID = -7250520230589446265L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String success = YesNo.NO;
		String type = StringUtils.defaultString(request.getParameter("type")).trim();
		String value = StringUtils.defaultString(request.getParameter("value")).trim();
		try {
			long now = System.currentTimeMillis();
			long before = CxfServerBean.getBeforeValue(value);
			if (now - before <= 30000) {
				if ("restart".equals(type)) {
					CxfServerBean.shutdown();
					CxfServerBean.restart();
					success = YesNo.YES;
				}
				if ("shutdown".equals(type)) {
					CxfServerBean.shutdown();
					success = YesNo.YES;
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().println("{\"success\" : \"" + success + "\"}");
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}	
	
}
