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
package com.netsteadfast.greenstep.bsc.support;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ContextInitializedAndDestroyedBean;
import com.netsteadfast.greenstep.bsc.service.logic.IEmployeeLogicService;
import com.netsteadfast.greenstep.sys.BackgroundProgramUserUtils;

/**
 * 準備 0.7.1 版本需要的 bb_employee_hier 資料
 * 0.7.4 廢除使用, 請用 "09 - Web Context bean" 來移除設定
 */
@Deprecated
public class UpgradeVersion071ForContextInitAndDestroy extends ContextInitializedAndDestroyedBean {
	private static final long serialVersionUID = 6662283552130953159L;
	protected static Logger logger = Logger.getLogger(UpgradeVersion071ForContextInitAndDestroy.class);
	
	@Override
	public void execute(ServletContextEvent event) throws Exception {
		logger.info("begin....");
		try {
			BackgroundProgramUserUtils.login();
			IEmployeeLogicService employeeLogicService = (IEmployeeLogicService) AppContext.getBean("bsc.service.logic.EmployeeLogicService");
			logger.info("init employee hierarchy data, since version 0.7.1");			
			employeeLogicService.initHierarchyForFirst();
		} catch (ServiceException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BackgroundProgramUserUtils.logout();
		}
		logger.warn("Please use [09 - Web Context bean] remove [UpgradeVersion071ForContextInitAndDestroy] config item in 0.7.4 version or later.");
		logger.info("end....");
	}

}
