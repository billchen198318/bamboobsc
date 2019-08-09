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
package com.netsteadfast.greenstep.job.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.context.ContextLoader;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.job.BaseJob;
import com.netsteadfast.greenstep.util.ApplicationSiteUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.SysVO;

@DisallowConcurrentExecution
public class ClearTempDataJobImpl extends BaseJob implements Job {
	protected static Logger log = Logger.getLogger(ClearTempDataJobImpl.class);
	
	public ClearTempDataJobImpl() {
		super();
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		if (ContextLoader.getCurrentWebApplicationContext() == null) {
			log.warn( "ApplicationContext no completed, AppContext.getApplicationContext() == null" );			
			return;
		}		
		log.info("begin....");
		if (this.checkCurrentlyExecutingJobs(context, this)) {
			log.warn("Same schedule job, current working...");
			return;
		}
		try {
			
			/**
			 * document reference:
			 * com.netsteadfast.greenstep.support.CleanTempUploadForContextInitAndDestroy.java
			 */
			this.loginForBackgroundProgram();
			List<SysVO> systems = ApplicationSiteUtils.getSystems();
			if (systems==null || systems.size()<1) {
				return;
			}
			for (SysVO sys : systems) {
				UploadSupportUtils.cleanTempUpload(sys.getSysId());
			}
			
			/**
			 * document reference:
			 * com.netsteadfast.greenstep.bsc.support.CleanJasperReportTempDataForContextInitAndDestroy.java
			 * 
			 */
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = (NamedParameterJdbcTemplate)
					AppContext.getBean("namedParameterJdbcTemplate");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			namedParameterJdbcTemplate.update("delete from bb_swot_report_mst", paramMap);
			namedParameterJdbcTemplate.update("delete from bb_swot_report_dtl", paramMap);
			
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.logoutForBackgroundProgram();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.info("end....");
	}

}
