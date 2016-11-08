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

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.bsc.vo.ApiServiceResponse;
import com.netsteadfast.greenstep.bsc.webservice.ApiWebService;
import com.netsteadfast.greenstep.sys.WsAuthenticateUtils;

@Service("bsc.webservice.ApiWebService")
@WebService
@SOAPBinding
@Path("/")
@Produces("application/json")
public class ApiWebServiceImpl implements ApiWebService {

	@WebMethod
	@GET
	@Path("/scorecard/")	
	@Override
	public ApiServiceResponse getScorecard(
			@WebParam(name="visionId") @PathParam("visionId") String visionId, 
			@WebParam(name="startDate") @PathParam("startDate") String startDate, 
			@WebParam(name="endDate") @PathParam("endDate") String endDate, 
			@WebParam(name="startYearDate") @PathParam("startYearDate") String startYearDate, 
			@WebParam(name="endYearDate") @PathParam("endYearDate") String endYearDate, 
			@WebParam(name="frequency") @PathParam("frequency") String frequency, 
			@WebParam(name="dataFor") @PathParam("dataFor") String dataFor, 
			@WebParam(name="measureDataOrganizationId") @PathParam("measureDataOrganizationId") String measureDataOrganizationId, 
			@WebParam(name="measureDataEmployeeId") @PathParam("measureDataEmployeeId") String measureDataEmployeeId) throws Exception {
		
		Subject subject = null;
		ApiServiceResponse responseObj = new ApiServiceResponse();
		responseObj.setSuccess( YesNo.NO );
		try {	
			subject = WsAuthenticateUtils.login();
			// do ....
			
		} catch (Exception e) {
			responseObj.setMessage( e.getMessage() );
		} finally {
			if (!YesNo.YES.equals(responseObj.getSuccess())) {
				responseObj.setMessage( SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA) );
			}	
			WsAuthenticateUtils.logout(subject);			
		}
		subject = null;
		return responseObj;
	}
	
}
