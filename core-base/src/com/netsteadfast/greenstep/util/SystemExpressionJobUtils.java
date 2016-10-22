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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.model.ExpressionJobConstants;
import com.netsteadfast.greenstep.model.ExpressionJobObj;
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.po.hbm.TbSysExprJob;
import com.netsteadfast.greenstep.po.hbm.TbSysExpression;
import com.netsteadfast.greenstep.service.ISysExprJobService;
import com.netsteadfast.greenstep.service.ISysExpressionService;
import com.netsteadfast.greenstep.support.ExpressionJobExecuteCallable;
import com.netsteadfast.greenstep.vo.SysExprJobLogVO;
import com.netsteadfast.greenstep.vo.SysExprJobVO;
import com.netsteadfast.greenstep.vo.SysExpressionVO;

public class SystemExpressionJobUtils {
	protected static Logger log = Logger.getLogger(SystemExpressionJobUtils.class);
	
	private static boolean isRunTime(SysExprJobVO exprJob, String dayOfWeek, String hour, String minute) {
		
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
	
	public static void initRunStatusFlag(String system) throws ServiceException, Exception {
		@SuppressWarnings("unchecked")
		ISysExprJobService<SysExprJobVO, TbSysExprJob, String> sysExprJobService = 
				(ISysExprJobService<SysExprJobVO, TbSysExprJob, String>) AppContext.getBean("core.service.SysExprJobService");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("system", system);
		paramMap.put("runStatus", ExpressionJobConstants.RUNSTATUS_PROCESS_NOW);
		List<TbSysExprJob> exprJobList = sysExprJobService.findListByParams(paramMap);
		if (exprJobList == null || exprJobList.size() < 1) {
			return;
		}
		Date udate = new Date();
		String uuserid = "system";
		for (TbSysExprJob exprJob : exprJobList) {
			log.warn( "ExpressionJob current RUN_STATUS is 'R' update to 'Y' , Id: " + exprJob.getId() + " , Name: " + exprJob.getName() );
			exprJob.setRunStatus(ExpressionJobConstants.RUNSTATUS_SUCCESS);
			exprJob.setUdate(udate);
			exprJob.setUuserid(uuserid);
			sysExprJobService.update(exprJob);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getDecUploadOid(String encUploadOidStr) throws ServiceException, Exception {
		if (StringUtils.isBlank(encUploadOidStr)) {
			throw new Exception("error, decrypt uploadOid value is blank!");
		}
		String uploadOid = EncryptorUtils.decrypt(Constants.getEncryptorKey1(), Constants.getEncryptorKey2(), SimpleUtils.deHex(encUploadOidStr));
		String jsonData = new String(UploadSupportUtils.getDataBytes(uploadOid));		
		ObjectMapper mapper = new ObjectMapper();
		return (Map<String, Object>)mapper.readValue(jsonData, HashMap.class);
	}
	
	public static String getEncUploadOid(String accountId, String sysExprJobOid) throws ServiceException, Exception {
		if (StringUtils.isBlank(accountId) || StringUtils.isBlank(sysExprJobOid)) {
			throw new Exception("error, accountId or sysExprJobOid value is blank!");
		}		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("accountId", accountId);
		dataMap.put("sysExprJobOid", sysExprJobOid);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonData = objectMapper.writeValueAsString(dataMap);		
		String uploadOid = UploadSupportUtils.create(
				Constants.getSystem(), 
				UploadTypes.IS_TEMP, 
				false, 
				jsonData.getBytes(), 
				SimpleUtils.getUUIDStr() + ".json");
		uploadOid = SimpleUtils.toHex(EncryptorUtils.encrypt(Constants.getEncryptorKey1(), Constants.getEncryptorKey2(), uploadOid));
		return uploadOid;
	}
	
	public static SysExprJobLogVO executeJobForManual(String expressionJobOid) throws ServiceException, Exception {
		ExpressionJobObj jobObj = getExpressionJobForManualMode(expressionJobOid);
		ExecutorService exprJobPool = Executors.newFixedThreadPool( 1 );
		jobObj = exprJobPool.submit( new ExpressionJobExecuteCallable(jobObj) ).get();
		exprJobPool.shutdown();	
		return jobObj.getSysExprJobLog();
	}
	
	public static SysExprJobLogVO executeJobForManualWebClient(SysExprJobVO sysExprJob, String accountId, 
			HttpServletRequest request) throws ServiceException, Exception {
		SysExprJobLogVO result = new SysExprJobLogVO();
		// 注意 executeJob/ 請參考 ManualJobServiceImpl.java @Path("/executeJob/") 設定是否有變更, 如果有變更的話, 這裡的 url 也要修改
		String url = ApplicationSiteUtils.getBasePath(sysExprJob.getSystem(), request);
		if (!url.endsWith("/")) {
			url += "/";
		}
		//url += "services/jaxrs/";
		url += Constants.getCxfWebServiceMainPathName() + Constants.getJAXRSServerFactoryBeanAddress();
		String encUploadOidStr = SystemExpressionJobUtils.getEncUploadOid(accountId, sysExprJob.getOid());
		WebClient client = WebClient.create(url);
		Response response = client.accept("application/json")
				.path("executeJob/{uploadOid}", encUploadOidStr)
				.post(encUploadOidStr);
        int statusCode = response.getStatus();
        if (statusCode != 200 && statusCode != 202 ) {
        	throw new Exception("error, http status code: " + statusCode);
        }
        String responseStr = response.readEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        result = mapper.readValue(responseStr, SysExprJobLogVO.class);        
		return result;
	}
	
	public static SysExprJobLogVO executeJobForManualFromRestServiceUrl(SysExprJobVO sysExprJob, String accountId, 
			HttpServletRequest request) throws ServiceException, Exception {
		SysExprJobLogVO result = new SysExprJobLogVO();
		// 注意 executeJob/ 請參考 ManualJobServiceImpl.java @Path("/executeJob/") 設定是否有變更, 如果有變更的話, 這裡的 url 也要修改
		String url = ApplicationSiteUtils.getBasePath(sysExprJob.getSystem(), request);
		if (!url.endsWith("/")) {
			url += "/";
		}
		//url += "services/jaxrs/executeJob/";
		url += Constants.getCxfWebServiceMainPathName() + Constants.getJAXRSServerFactoryBeanAddress() + "executeJob/";
		String encUploadOidStr = SystemExpressionJobUtils.getEncUploadOid(accountId, sysExprJob.getOid());
		url += encUploadOidStr;
		InputStreamReader isr = null;
		BufferedReader reader = null;
		try {
			HttpClient httpClient = new HttpClient();
			PostMethod post = new PostMethod(url);
			/*
	        NameValuePair[] data = {
	        		new NameValuePair("uploadOid", encUploadOidStr)
	        };
	        post.setRequestBody(data);
	        */
	        post.setParameter("uploadOid", encUploadOidStr);
	        int statusCode = httpClient.executeMethod(post);
	        if (statusCode != 200 && statusCode != 202 ) {
	        	throw new Exception("error, http status code: " + statusCode);
	        }
	        isr = new InputStreamReader(post.getResponseBodyAsStream());
	        reader = new BufferedReader(isr);
	        String line = "";
	        StringBuilder str = new StringBuilder();
	        while ((line = reader.readLine()) != null) {
	        	str.append(line);
	        }
	        ObjectMapper mapper = new ObjectMapper();
	        result = mapper.readValue(str.toString(), SysExprJobLogVO.class);
		} catch (IOException e) {
			e.printStackTrace();
			result.setFaultMsg( e.getMessage().toString() );
		} catch (Exception e) {
			e.printStackTrace();
			result.setFaultMsg( e.getMessage().toString() );
		} finally {
			if (reader != null) {
				reader.close();
			}
	        if (isr != null) {
	        	isr.close();
	        }
	        reader = null;
	        isr = null;
		}
		return result;
	}
	
	public static ExpressionJobObj getExpressionJobForManualMode(String expressionJobOid) throws ServiceException, Exception {
		if (StringUtils.isBlank(expressionJobOid)) {
			throw new Exception("error, expressionJobId is blank!");
		}
		@SuppressWarnings("unchecked")
		ISysExprJobService<SysExprJobVO, TbSysExprJob, String> sysExprJobService = 
				(ISysExprJobService<SysExprJobVO, TbSysExprJob, String>) AppContext.getBean("core.service.SysExprJobService");
		@SuppressWarnings("unchecked")
		ISysExpressionService<SysExpressionVO, TbSysExpression, String> sysExpressionService =
				(ISysExpressionService<SysExpressionVO, TbSysExpression, String>) AppContext.getBean("core.service.SysExpressionService");
		SysExprJobVO exprJob = new SysExprJobVO();
		exprJob.setOid(expressionJobOid);
		DefaultResult<SysExprJobVO> exprJobResult = sysExprJobService.findObjectByOid(exprJob);
		if (exprJobResult.getValue() == null) {
			throw new ServiceException( exprJobResult.getSystemMessage().getValue() );
		}
		exprJob = exprJobResult.getValue();
		
		SysExpressionVO expr = new SysExpressionVO();
		expr.setExprId(exprJob.getExprId());
		DefaultResult<SysExpressionVO> exprResult = sysExpressionService.findByUK(expr);
		if (exprResult.getValue() == null) {
			throw new ServiceException( exprResult.getSystemMessage().getValue() );
		}
		expr = exprResult.getValue();		
		
		ExpressionJobObj jobObj = new ExpressionJobObj();
		jobObj.setSysExprJob(exprJob);
		jobObj.setSysExprJobLog( new SysExprJobLogVO() );
		jobObj.setSysExpression(expr);
		
		return jobObj;
	}
	
	public static void executeJobs() throws ServiceException, Exception {
		List<ExpressionJobObj> jobObjList = getExpressionJobs();
		if (jobObjList == null || jobObjList.size() < 1) {
			return;
		}
		ExecutorService exprJobPool = Executors.newFixedThreadPool( SimpleUtils.getAvailableProcessors(jobObjList.size()) );
		for (ExpressionJobObj jobObj : jobObjList) {
			jobObj = exprJobPool.submit( new ExpressionJobExecuteCallable(jobObj) ).get();
		}
		exprJobPool.shutdown();		
	}
	
	public static List<ExpressionJobObj> getExpressionJobs() throws ServiceException, Exception {
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
				log.warn( "[Expression-Job] Please check it, process now, Id: " + exprJob.getExprId() + " , name: " + exprJob.getName() );				
				continue;
			}
			if (!isRunTime(exprJob, dayOfWeek, hour, minute)) {
				continue;
			}
			ExpressionJobObj jobObj = new ExpressionJobObj();
			jobObj.setSysExprJob(exprJob);
			jobObj.setSysExprJobLog( new SysExprJobLogVO() );
			SysExpressionVO expr = new SysExpressionVO();
			expr.setExprId(exprJob.getExprId());
			DefaultResult<SysExpressionVO> exprResult = sysExpressionService.findByUK(expr);
			if (exprResult.getValue() == null) {
				log.error( "[Expression-Job] Id: " + exprJob.getExprId() + " , data not found.");				
				log.error( exprResult.getSystemMessage().getValue() );
				continue;
			}
			expr = exprResult.getValue();
			jobObj.setSysExpression(expr);
			jobObjList.add(jobObj);			
		}
		return jobObjList;
	}	

}
