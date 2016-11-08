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

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.cxf.jaxrs.ext.MessageContext;

import com.netsteadfast.greenstep.bsc.vo.BscApiServiceResponse;

@WebService
@SOAPBinding
@Path("/")
@Produces("application/json")
public interface ApiWebService {
	
	@WebMethod
	@GET
	@Path("/scorecard1/")
	public BscApiServiceResponse getScorecard1(
			@WebParam(name="visionOid") @PathParam("visionOid") String visionOid, 
			@WebParam(name="startDate") @PathParam("startDate") String startDate, 
			@WebParam(name="endDate") @PathParam("endDate") String endDate, 
			@WebParam(name="startYearDate") @PathParam("startYearDate") String startYearDate, 
			@WebParam(name="endYearDate") @PathParam("endYearDate") String endYearDate, 
			@WebParam(name="frequency") @PathParam("frequency") String frequency, 
			@WebParam(name="dataFor") @PathParam("dataFor") String dataFor, 
			@WebParam(name="measureDataOrganizationOid") @PathParam("measureDataOrganizationOid") String measureDataOrganizationOid, 
			@WebParam(name="measureDataEmployeeOid") @PathParam("measureDataEmployeeOid") String measureDataEmployeeOid,
			@Context MessageContext context) throws Exception;	
	
	@WebMethod
	@GET
	@Path("/scorecard2/")
	public BscApiServiceResponse getScorecard2(
			@WebParam(name="visionId") @PathParam("visionId") String visionId, 
			@WebParam(name="startDate") @PathParam("startDate") String startDate, 
			@WebParam(name="endDate") @PathParam("endDate") String endDate, 
			@WebParam(name="startYearDate") @PathParam("startYearDate") String startYearDate, 
			@WebParam(name="endYearDate") @PathParam("endYearDate") String endYearDate, 
			@WebParam(name="frequency") @PathParam("frequency") String frequency, 
			@WebParam(name="dataFor") @PathParam("dataFor") String dataFor, 
			@WebParam(name="measureDataOrganizationId") @PathParam("measureDataOrganizationId") String measureDataOrganizationId, 
			@WebParam(name="measureDataEmployeeId") @PathParam("measureDataEmployeeId") String measureDataEmployeeId,
			@Context MessageContext context) throws Exception;
	
}
