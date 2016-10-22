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
package com.netsteadfast.greenstep.bsc.webservice;

//import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

//import com.netsteadfast.greenstep.bsc.vo.SimpleWsServiceKpiVO;
//import com.netsteadfast.greenstep.bsc.vo.SimpleWsServiceResponseVO;
//import com.netsteadfast.greenstep.vo.MeasureDataVO;

@WebService
@SOAPBinding
public interface KpiWebService {
	
	@WebMethod
	public String outputKpisResult(@WebParam(name="format") String format) throws Exception;
	
	/*
	 * Create KPIs webService.
	 * note:
	 * Not enable for Internet env, only for intranet. because security!
	 * 
	@WebMethod
	public SimpleWsServiceResponseVO createKpi(
			@WebParam(name="kpi") SimpleWsServiceKpiVO kpi, 
			@WebParam(name="objectiveOid") String objectiveOid, 
			@WebParam(name="formulaOid") String formulaOid, 
			@WebParam(name="aggrOid") String aggrOid,
			@WebParam(name="organizationOids") List<String> organizationOids, 
			@WebParam(name="employeeOids") List<String> employeeOids, 
			@WebParam(name="trendsFormulaOid") String trendsFormulaOid, 
			@WebParam(name="attachment") List<String> attachment) throws Exception;
	*/
	
	/*
	 * Create measure-data webService.
	 * note:
	 * Not enable for Internet env, only for intranet. because security!
	 * 
	public SimpleWsServiceResponseVO measureDataSaveOrUpdate(
			@WebParam(name="kpiOid") String kpiOid, 
			@WebParam(name="date") String date,
			@WebParam(name="frequency") String frequency, 
			@WebParam(name="dataFor") String dataFor, 
			@WebParam(name="organizationId") String organizationId,
			@WebParam(name="employeeId") String employeeId, 
			@WebParam(name="measureDatas") List<MeasureDataVO> measureDatas) throws Exception;
	*/
	
}
