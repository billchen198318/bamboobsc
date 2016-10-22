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
package com.netsteadfast.greenstep.support;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.model.ExpressionJobObj;
import com.netsteadfast.greenstep.po.hbm.TbSysExprJob;
import com.netsteadfast.greenstep.po.hbm.TbSysExprJobLog;
import com.netsteadfast.greenstep.po.hbm.TbSysMailHelper;
import com.netsteadfast.greenstep.service.ISysExprJobLogService;
import com.netsteadfast.greenstep.service.ISysExprJobService;
import com.netsteadfast.greenstep.service.ISysMailHelperService;
import com.netsteadfast.greenstep.model.ExpressionJobConstants;
import com.netsteadfast.greenstep.util.MailClientUtils;
import com.netsteadfast.greenstep.util.ScriptExpressionUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.SysExprJobLogVO;
import com.netsteadfast.greenstep.vo.SysExprJobVO;
import com.netsteadfast.greenstep.vo.SysMailHelperVO;

public class ExpressionJobExecuteCallable implements Callable<ExpressionJobObj> {	
	protected static Logger logger = Logger.getLogger(ExpressionJobExecuteCallable.class);
	private ExpressionJobObj jobObj = null;
	
	public ExpressionJobExecuteCallable(ExpressionJobObj jobObj) {
		this.jobObj = jobObj;
	}

	public ExpressionJobObj getJobObj() {
		return jobObj;
	}

	public void setJobObj(ExpressionJobObj jobObj) {
		this.jobObj = jobObj;
	}
	
	@Override
	public ExpressionJobObj call() throws Exception {
		Date beginDatetime = new Date();
		String faultMsg = "";
		String runStatus = "";
		String logStatus = "";
		@SuppressWarnings("unchecked")
		ISysExprJobService<SysExprJobVO, TbSysExprJob, String> sysExprJobService = 
				(ISysExprJobService<SysExprJobVO, TbSysExprJob, String>) AppContext.getBean("core.service.SysExprJobService");
		@SuppressWarnings("unchecked")
		ISysExprJobLogService<SysExprJobLogVO, TbSysExprJobLog, String> sysExprJobLogService = 
				(ISysExprJobLogService<SysExprJobLogVO, TbSysExprJobLog, String>) AppContext.getBean("core.service.SysExprJobLogService");
		try {
			logger.info("[Expression-Job] (Start) ID: " + this.jobObj.getSysExprJob().getId() + " , NAME: " + this.jobObj.getSysExprJob().getName());
			if (StringUtils.isBlank(jobObj.getSysExpression().getContent())) {
				faultMsg = "No expression content!";
				runStatus = ExpressionJobConstants.RUNSTATUS_FAULT;
				logStatus = ExpressionJobConstants.LOGSTATUS_NO_EXECUTE;
				return this.jobObj;
			}
			if (YesNo.YES.equals(this.jobObj.getSysExprJob().getCheckFault()) 
					&& ExpressionJobConstants.RUNSTATUS_FAULT.equals(this.jobObj.getSysExprJob().getRunStatus())) {
				faultMsg = "Before proccess fault, cannot execute expression job!";
				runStatus = ExpressionJobConstants.RUNSTATUS_FAULT;
				logStatus = ExpressionJobConstants.LOGSTATUS_NO_EXECUTE;
				return this.jobObj;
			}
			
			this.jobObj.getSysExprJob().setRunStatus(ExpressionJobConstants.RUNSTATUS_PROCESS_NOW);
			sysExprJobService.updateObject( this.jobObj.getSysExprJob() );
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("jobObj", this.jobObj);
			ScriptExpressionUtils.execute(
					jobObj.getSysExpression().getType(), 
					jobObj.getSysExpression().getContent(), 
					paramMap, 
					paramMap);
			runStatus = ExpressionJobConstants.RUNSTATUS_SUCCESS;
			logStatus = ExpressionJobConstants.LOGSTATUS_SUCCESS;			
		} catch (ServiceException se) {
			faultMsg = se.getMessage().toString();
			runStatus = ExpressionJobConstants.RUNSTATUS_FAULT;
			logStatus = ExpressionJobConstants.LOGSTATUS_FAULT;				
			logger.error( se.getMessage().toString() );
		} catch (Exception e) {
			faultMsg = e.getMessage().toString();
			if (e.getMessage()==null) { 
				faultMsg=e.toString();
			} else {
				faultMsg=e.getMessage().toString();
			}			
			runStatus = ExpressionJobConstants.RUNSTATUS_FAULT;
			logStatus = ExpressionJobConstants.LOGSTATUS_FAULT;			
			logger.error( faultMsg );
		} finally {
			if (faultMsg.length()>2000) {
				faultMsg = faultMsg.substring(0, 2000);
			}
			jobObj.getSysExprJob().setRunStatus(runStatus);
			jobObj.getSysExprJobLog().setFaultMsg(faultMsg);
			jobObj.getSysExprJobLog().setLogStatus(logStatus);
			jobObj.getSysExprJobLog().setId( jobObj.getSysExprJob().getId() );
			jobObj.getSysExprJobLog().setBeginDatetime(beginDatetime);
			jobObj.getSysExprJobLog().setEndDatetime( new Date() );
			
			sysExprJobService.updateObject( this.jobObj.getSysExprJob() );
			DefaultResult<SysExprJobLogVO> jobLogResult = sysExprJobLogService.saveObject(jobObj.getSysExprJobLog());
			if (jobLogResult.getValue() != null) {
				jobObj.setSysExprJobLog( jobLogResult.getValue() );
			}
			
			this.sendMail();
			
			logger.info("[Expression-Job] (End) ID: " + this.jobObj.getSysExprJob().getId() + " , NAME: " + this.jobObj.getSysExprJob().getName());
		}
		return this.jobObj;
	}	
	
	private void sendMail() {
		try {
			if (ExpressionJobConstants.CONTACT_MODE_NO.equals(this.jobObj.getSysExprJob().getContactMode())) {
				return;
			}
			if (ExpressionJobConstants.CONTACT_MODE_ONLY_FAULT.equals(this.jobObj.getSysExprJob().getContactMode()) 
					&& !ExpressionJobConstants.RUNSTATUS_FAULT.equals(this.jobObj.getSysExprJob().getRunStatus())) {
				return;
			}
			if (ExpressionJobConstants.CONTACT_MODE_ONLY_SUCCESS.equals(this.jobObj.getSysExprJob().getContactMode()) 
					&& !ExpressionJobConstants.RUNSTATUS_SUCCESS.equals(this.jobObj.getSysExprJob().getRunStatus())) {
				return;
			}
			String contact = StringUtils.defaultString(this.jobObj.getSysExprJob().getContact()).trim();
			if (StringUtils.isBlank(contact)) {
				return;
			}
			String subject = this.jobObj.getSysExprJob().getId() + " - " + this.jobObj.getSysExprJob().getName();
			String content = subject + Constants.HTML_BR;
			content += "Run status: " + this.jobObj.getSysExprJob().getRunStatus() + Constants.HTML_BR;
			content += "Start: " + this.jobObj.getSysExprJobLog().getBeginDatetime().toString() + Constants.HTML_BR;
			content += "End: " + this.jobObj.getSysExprJobLog().getEndDatetime().toString() + Constants.HTML_BR;
			if (ExpressionJobConstants.RUNSTATUS_FAULT.equals(this.jobObj.getSysExprJob().getRunStatus())) {
				content += Constants.HTML_BR;
				content += "Fault: " + Constants.HTML_BR;
				content += this.jobObj.getSysExprJobLog().getFaultMsg();
			}
			@SuppressWarnings("unchecked")
			ISysMailHelperService<SysMailHelperVO, TbSysMailHelper, String> sysMailHelperService = 
					(ISysMailHelperService<SysMailHelperVO, TbSysMailHelper, String>) AppContext.getBean("core.service.SysMailHelperService");
			String mailId = SimpleUtils.getStrYMD("");
			SysMailHelperVO mailHelper = new SysMailHelperVO();
			mailHelper.setMailId( sysMailHelperService.findForMaxMailIdComplete(mailId) );
			mailHelper.setMailFrom( MailClientUtils.getDefaultFrom() );
			mailHelper.setMailTo( contact );
			mailHelper.setSubject( subject );
			mailHelper.setText( content.getBytes("utf8") );
			mailHelper.setRetainFlag(YesNo.NO);
			mailHelper.setSuccessFlag(YesNo.NO);
			sysMailHelperService.saveObject(mailHelper);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
