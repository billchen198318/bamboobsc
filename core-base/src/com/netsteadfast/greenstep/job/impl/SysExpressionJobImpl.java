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

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.ContextLoader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.job.BaseJob;
import com.netsteadfast.greenstep.util.SystemExpressionJobUtils;

import javassist.Modifier;

/**
 * 注意: 這個Job 在 Quartz 中的設定, 要每分鐘都需執行處理
 *
 */
@DisallowConcurrentExecution
public class SysExpressionJobImpl extends BaseJob implements Job {
	protected static Logger log = Logger.getLogger(SysExpressionJobImpl.class);
	
	private static final String _CONFIG = "SysExpressionJob.json";
	private static String _datas = " { } ";
	private static Map<String, Object> _configDataMap;	
	
	static {
		try {
			InputStream is = SysExpressionJobImpl.class.getClassLoader().getResource( _CONFIG ).openStream();
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
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> loadDatas() {
		Map<String, Object> datas = null;
		try {
			datas = (Map<String, Object>)new ObjectMapper().readValue( _datas, LinkedHashMap.class );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}	
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		if (ContextLoader.getCurrentWebApplicationContext() == null) {
			log.warn( "ApplicationContext no completed, AppContext.getApplicationContext() == null" );			
			return;
		}
		if (this.checkCurrentlyExecutingJobs(context, this)) {
			log.warn("Same schedule job, current working...");
			return;
		}		
		try {
			this.loginForBackgroundProgram();
			/*
			List<ExpressionJobObj> jobObjList = this.getExpressionJobs();
			if (jobObjList == null || jobObjList.size() < 1) {
				return;
			}
			ExecutorService exprJobPool = Executors.newFixedThreadPool( SimpleUtils.getAvailableProcessors(jobObjList.size()) );
			for (ExpressionJobObj jobObj : jobObjList) {
				jobObj = exprJobPool.submit( new ExpressionJobExecuteCallable(jobObj) ).get();
			}
			exprJobPool.shutdown();
			*/
			SystemExpressionJobUtils.executeJobs();
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
			this.finalProcess();
		}
		
	}
	
	private void finalProcess() {
		String finalProcessClassName = "";
		String methodName = "";
		if (null != _configDataMap && !StringUtils.isBlank(finalProcessClassName = (String)_configDataMap.get("finalProcessClass"))) {
			methodName = StringUtils.defaultString( (String) _configDataMap.get("method") );
			try {
				Class<?> finalProcessClass = Class.forName(finalProcessClassName);
				Method[] methods = finalProcessClass.getMethods();
				for (Method method : methods) {
					if (method.getName().equals(methodName) && Modifier.isStatic(method.getModifiers())) {
						try {
							method.invoke(finalProcessClass);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}		
	}
	
	/*
	private boolean isRunTime(SysExprJobVO exprJob, String dayOfWeek, String hour, String minute) {
		
		// 查 DAY_OF_WEEK
		if (!ExpressionJobConstants.DATEOFWEEK_HOUR_MINUTE_ALL.equals(exprJob.getRunDayOfWeek()) 
				&& !dayOfWeek.equals(exprJob.getRunDayOfWeek())) {
			return false;
		}
		
		// 查 HOUR
		if (!ExpressionJobConstants.DATEOFWEEK_HOUR_MINUTE_ALL.equals(exprJob.getRunHour()) 
				&& !hour.equals(exprJob.getRunHour())) {
			return false;
		}	
		
		// 查MINUTE
		if (!ExpressionJobConstants.DATEOFWEEK_HOUR_MINUTE_ALL.equals(exprJob.getRunMinute()) 
				&& !minute.equals(exprJob.getRunMinute())) {
			return false;
		}
		
		return true;
	}
	
	private List<ExpressionJobObj> getExpressionJobs() throws ServiceException, Exception {
		int year = Integer.parseInt(SimpleUtils.getStrYMD(SimpleUtils.IS_YEAR));
		int month = Integer.parseInt(SimpleUtils.getStrYMD(SimpleUtils.IS_MONTH));
		String dayOfWeek = String.valueOf( SimpleUtils.getDayOfWeek(year, month) );
		String hour = String.valueOf( LocalDateTime.now().getHourOfDay() );
		String minute = String.valueOf( LocalDateTime.now().getMinuteOfHour() );		
		List<ExpressionJobObj> jobObjList = new ArrayList<ExpressionJobObj>();
		@SuppressWarnings("unchecked")
		ISysExprJobService<SysExprJobVO, TbSysExprJob, String> sysExprJobService = 
				(ISysExprJobService<SysExprJobVO, TbSysExprJob, String>) AppContext.getBean("core.service.SysExprJobService");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("system", Constants.getSystem());
		paramMap.put("active", YesNo.YES);
		List<SysExprJobVO> exprJobList = sysExprJobService.findListVOByParams(paramMap);
		if (null == exprJobList || exprJobList.size() < 1) {
			return jobObjList;
		}
		@SuppressWarnings("unchecked")
		ISysExpressionService<SysExpressionVO, TbSysExpression, String> sysExpressionService =
				(ISysExpressionService<SysExpressionVO, TbSysExpression, String>) AppContext.getBean("core.service.SysExpressionService");
		for (SysExprJobVO exprJob : exprJobList) {
			if (ExpressionJobConstants.RUNSTATUS_PROCESS_NOW.equals(exprJob.getRunStatus())) {
				log.warn( "Expression Job is process now: " + exprJob.getExprId() + " - " + exprJob.getName() );				
				continue;
			}
			if (!this.isRunTime(exprJob, dayOfWeek, hour, minute)) {
				continue;
			}
			ExpressionJobObj jobObj = new ExpressionJobObj();
			jobObj.setSysExprJob(exprJob);
			jobObj.setSysExprJobLog( new SysExprJobLogVO() );
			SysExpressionVO expr = new SysExpressionVO();
			expr.setExprId(exprJob.getExprId());
			DefaultResult<SysExpressionVO> exprResult = sysExpressionService.findByUK(expr);
			if (exprResult.getValue() == null) {
				log.error( "Expression Id: " + exprJob.getExprId() + " , data not found.");				
				log.error( exprResult.getSystemMessage().getValue() );
				continue;
			}
			expr = exprResult.getValue();
			jobObj.setSysExpression(expr);
			jobObjList.add(jobObj);			
		}
		return jobObjList;
	}
	*/
	
}
