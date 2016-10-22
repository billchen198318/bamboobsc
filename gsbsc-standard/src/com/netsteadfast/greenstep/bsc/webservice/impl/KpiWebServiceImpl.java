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
package com.netsteadfast.greenstep.bsc.webservice.impl;

//import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

//import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.subject.Subject;
//import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.netsteadfast.greenstep.base.AppContext;
//import com.netsteadfast.greenstep.base.exception.AuthorityException;
//import com.netsteadfast.greenstep.base.exception.ServiceException;
//import com.netsteadfast.greenstep.base.model.DefaultResult;
//import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.bsc.service.logic.IKpiLogicService;
//import com.netsteadfast.greenstep.bsc.service.logic.IMeasureDataLogicService;
//import com.netsteadfast.greenstep.bsc.vo.SimpleWsServiceKpiVO;
//import com.netsteadfast.greenstep.bsc.vo.SimpleWsServiceResponseVO;
import com.netsteadfast.greenstep.bsc.webservice.KpiWebService;
//import com.netsteadfast.greenstep.sys.ShiroLoginSupport;
//import com.netsteadfast.greenstep.vo.KpiVO;
//import com.netsteadfast.greenstep.vo.MeasureDataVO;

@Service("bsc.webservice.KpiWebService")
@WebService
@SOAPBinding
public class KpiWebServiceImpl implements KpiWebService {

	/**
	 * for TEST
	 * 這是測試 SOAP 用的  metod , 暴露 KPIs 主檔資料
	 * 
	 */
	@WebMethod
	@Override
	public String outputKpisResult(@WebParam(name="format") String format) throws Exception {
		IKpiLogicService kpiLogicService = (IKpiLogicService)AppContext.getBean("bsc.service.logic.KpiLogicService");		
		return kpiLogicService.findKpis(format);
	}
	
	/*
	 * Create KPIs webService.
	 * note:
	 * Not enable for Internet env, only for intranet. because security!
	 * 
	@WebMethod
	@Override
	public SimpleWsServiceResponseVO createKpi(
			@WebParam(name="kpi") SimpleWsServiceKpiVO kpi, 
			@WebParam(name="objectiveOid") String objectiveOid, 
			@WebParam(name="formulaOid") String formulaOid, 
			@WebParam(name="aggrOid") String aggrOid,
			@WebParam(name="organizationOids") List<String> organizationOids, 
			@WebParam(name="employeeOids") List<String> employeeOids, 
			@WebParam(name="trendsFormulaOid") String trendsFormulaOid, 
			@WebParam(name="attachment") List<String> attachment) throws Exception {
		
		SimpleWsServiceResponseVO response = new SimpleWsServiceResponseVO();
		response.setSuccess(YesNo.NO);
		
		Subject subject = null;
		ShiroLoginSupport loginSupport = new ShiroLoginSupport();
		try {
			subject = loginSupport.forceCreateLoginSubject("admin");
			IKpiLogicService kpiLogicService = (IKpiLogicService)AppContext.getBean("bsc.service.logic.KpiLogicService");
			KpiVO kpiValueObject = new KpiVO();
			BeanUtils.copyProperties(kpi, kpiValueObject);
			DefaultResult<KpiVO> result = kpiLogicService.create(
					kpiValueObject, 
					objectiveOid, 
					formulaOid, 
					aggrOid, 
					organizationOids, 
					employeeOids, 
					trendsFormulaOid, 
					attachment);
			if (result.getValue() != null && !StringUtils.isBlank(result.getValue().getOid())) {
				response.setSuccess(YesNo.YES);
				response.setMessage( StringUtils.defaultString(result.getSystemMessage().toString()) );
			}
		} catch (AuthorityException ae) {
			response.setMessage( ae.getMessage().toString() );
		} catch (ServiceException se) {
			response.setMessage( se.getMessage().toString() );
		} catch (Exception e) {
			response.setMessage( e.getMessage().toString() );
		} finally {
			if (null != subject && subject.isAuthenticated()) {
				subject.logout();
			}
		}
		return response;
	}
	*/

	/*
	 * Create measure-data webService.
	 * note:
	 * Not enable for Internet env, only for intranet. because security!
	 * 
	@WebMethod
	@Override	
	public SimpleWsServiceResponseVO measureDataSaveOrUpdate(
			@WebParam(name="kpiOid") String kpiOid, 
			@WebParam(name="date") String date,
			@WebParam(name="frequency") String frequency, 
			@WebParam(name="dataFor") String dataFor, 
			@WebParam(name="organizationId") String organizationId,
			@WebParam(name="employeeId") String employeeId, 
			@WebParam(name="measureDatas") List<MeasureDataVO> measureDatas) throws Exception {
		
		SimpleWsServiceResponseVO response = new SimpleWsServiceResponseVO();
		response.setSuccess(YesNo.NO);
		
		Subject subject = null;
		ShiroLoginSupport loginSupport = new ShiroLoginSupport();
		try {
			subject = loginSupport.forceCreateLoginSubject("admin");
			IMeasureDataLogicService measureDataService = (IMeasureDataLogicService)AppContext.getBean("bsc.service.logic.MeasureDataLogicService");
			DefaultResult<Boolean> result = measureDataService.saveOrUpdate(
					kpiOid, 
					date, 
					frequency, 
					dataFor, 
					organizationId, 
					employeeId, 
					measureDatas);
			if (result.getValue() != null && result.getValue()) {
				response.setSuccess(YesNo.YES);
				response.setMessage( StringUtils.defaultString(result.getSystemMessage().toString()) );
			}
		} catch (AuthorityException ae) {
			response.setMessage( ae.getMessage().toString() );
		} catch (ServiceException se) {
			response.setMessage( se.getMessage().toString() );
		} catch (Exception e) {
			response.setMessage( e.getMessage().toString() );
		} finally {
			if (null != subject && subject.isAuthenticated()) {
				subject.logout();
			}
		}
		return response;
	}
	*/

}
