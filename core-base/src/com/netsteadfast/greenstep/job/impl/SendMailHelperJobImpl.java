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

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.mail.MailException;
import org.springframework.web.context.ContextLoader;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.job.BaseJob;
import com.netsteadfast.greenstep.po.hbm.TbSysMailHelper;
import com.netsteadfast.greenstep.service.ISysMailHelperService;
import com.netsteadfast.greenstep.util.MailClientUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.SysMailHelperVO;

@DisallowConcurrentExecution
public class SendMailHelperJobImpl extends BaseJob implements Job {
	protected static Logger log = Logger.getLogger(SendMailHelperJobImpl.class);
	
	public SendMailHelperJobImpl() {
		super();
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		if (ContextLoader.getCurrentWebApplicationContext() == null) {
			log.warn( "ApplicationContext no completed, AppContext.getApplicationContext() == null" );			
			return;
		}		
		//log.info("begin....");		
		if (this.checkCurrentlyExecutingJobs(context, this)) {
			log.warn("Same schedule job, current working...");
			return;
		}		
		try {
			this.loginForBackgroundProgram();
			//log.info("Background Program userId: " + this.getAccountId());
			
			@SuppressWarnings("unchecked")
			ISysMailHelperService<SysMailHelperVO, TbSysMailHelper, String> sysMailHelperService = 
					(ISysMailHelperService<SysMailHelperVO, TbSysMailHelper, String>) AppContext.getBean("core.service.SysMailHelperService");				
			
			if (MailClientUtils.getEnable()) {
				String linkMailId = SimpleUtils.getStrYMD("").substring(0, 6);
				DefaultResult<List<TbSysMailHelper>> result = sysMailHelperService.findForJobList(
						linkMailId, YesNo.NO);			
				if (result.getValue()!=null) {
					this.process(sysMailHelperService, result.getValue());
				}				
			} else {
				log.warn("************ mail sender is disable. please modify config CNF/CNF_CONF002 ************");				
			}
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
		//log.info("end....");		
	}
	
	private void process(ISysMailHelperService<SysMailHelperVO, TbSysMailHelper, String> sysMailHelperService,
			List<TbSysMailHelper> mailHelperList) throws ServiceException, Exception {
		if (mailHelperList==null || mailHelperList.size()<1) {
			return;
		}	
		for (TbSysMailHelper mailHelper : mailHelperList) {
			new ProcessWorker(sysMailHelperService, mailHelper);
		}
	}
	
	private class ProcessWorker extends Thread {
		private ISysMailHelperService<SysMailHelperVO, TbSysMailHelper, String> sysMailHelperService = null;
		private TbSysMailHelper mailHelper = null;
		private Thread flag = this;
		private long sleepTime = 3000; // 3 - sec
		private int rety = 3; // 重試3次
		private boolean success = false;
		
		public ProcessWorker(
				ISysMailHelperService<SysMailHelperVO, TbSysMailHelper, String> sysMailHelperService, 
				TbSysMailHelper mailHelper) {
			this.sysMailHelperService = sysMailHelperService;
			this.mailHelper = mailHelper;
			this.flag = this;
			this.start();
		}
		
		public void run() {
			
			this.flag = this;
			while ( !this.success && this.flag == Thread.currentThread() && rety>0 ) {
				if ( this.mailHelper == null) {
					this.flag = null;
				}
				
				try {
					log.info("process mail-id: " + this.mailHelper.getMailId());	
					MailClientUtils.send(
							this.mailHelper.getMailFrom(), 
							this.mailHelper.getMailTo(), 
							this.mailHelper.getMailCc(), 
							this.mailHelper.getMailBcc(), 
							this.mailHelper.getSubject(), 
							new String(this.mailHelper.getText(), "utf8") );
					success = true;					
				} catch (MailException e1) {
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				if (success) {
					try {
						if ( YesNo.YES.equals(this.mailHelper.getRetainFlag()) ) {
							this.mailHelper.setSuccessFlag(YesNo.YES);
							this.mailHelper.setSuccessTime(new Date());							
							this.sysMailHelperService.update(mailHelper);
						} else {
							this.sysMailHelperService.delete(mailHelper);
						}						
					} catch (ServiceException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					log.info("success mail-id: " + this.mailHelper.getMailId());
					this.flag = null;
				}
				this.rety--;
				
				try {
					Thread.sleep(this.sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}			
			this.flag = null;	
		}
		
	}
	
}
