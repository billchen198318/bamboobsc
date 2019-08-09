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
package com.netsteadfast.greenstep.job;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.netsteadfast.greenstep.sys.BackgroundProgramUserUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;

public abstract class BaseJob extends QuartzJobBean {
	
	/**
	 * check before job in working.
	 * 
	 * @param context
	 * @param currentJob
	 * @return
	 */
	protected boolean checkCurrentlyExecutingJobs(JobExecutionContext context, QuartzJobBean currentJob) {
		boolean beofreJobWork = false;
		try {
			List<JobExecutionContext> jobs = context.getScheduler().getCurrentlyExecutingJobs();
			for (int j = 0; (jobs != null && !jobs.isEmpty()) && j < jobs.size() && !beofreJobWork; j++) {
				JobExecutionContext job = jobs.get(j);
				if (job.getTrigger().equals(context.getTrigger()) && !job.getJobInstance().equals(currentJob)) {
					beofreJobWork = true;
				}
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return beofreJobWork;
	}
	
	public String getAccountId() {		
		return StringUtils.defaultString( (String)BackgroundProgramUserUtils.getSubject().getPrincipal() );		
	}	
	
	public String generateOid() {
		return SimpleUtils.getUUIDStr();
	}
	
	public void loginForBackgroundProgram() throws Exception {		
		BackgroundProgramUserUtils.login();
	}
	
	public void logoutForBackgroundProgram() throws Exception {
		BackgroundProgramUserUtils.logout();
	}
	
}
