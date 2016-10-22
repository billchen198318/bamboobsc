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
package com.netsteadfast.greenstep.webservice.impl;

import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.sys.ShiroLoginSupport;
import com.netsteadfast.greenstep.util.SystemExpressionJobUtils;
import com.netsteadfast.greenstep.vo.SysExprJobLogVO;
import com.netsteadfast.greenstep.webservice.IManualJobService;

@Service("core.webservice.ManualJobService")
@WebService
@Path("/")
@Produces("application/json")
public class ManualJobServiceImpl implements IManualJobService {
	protected static Logger log = Logger.getLogger(ManualJobServiceImpl.class);

	@WebMethod
	@POST
	@Path("/executeJob/{uploadOid}")
	@Override	
	public String execute(
			@WebParam(name="uploadOid") @PathParam("uploadOid") String uploadOid) throws Exception {
		SysExprJobLogVO result = null;
		Subject subject = null;
		ObjectMapper objectMapper = new ObjectMapper();
		String exceptionMessage = "";
		try {
			Map<String, Object> dataMap = SystemExpressionJobUtils.getDecUploadOid(uploadOid);
			if ( dataMap == null || StringUtils.isBlank((String)dataMap.get("accountId")) || StringUtils.isBlank((String)dataMap.get("sysExprJobOid")) ) {
				log.error( "no data accountId / sysExprJobOid" );
				result = new SysExprJobLogVO();
				result.setFaultMsg("no data accountId / sysExprJobOid");
				return objectMapper.writeValueAsString(result);
			}
			String accountId = (String)dataMap.get("accountId");
			String sysExprJobOid = (String)dataMap.get("sysExprJobOid");
			ShiroLoginSupport loginSupport = new ShiroLoginSupport();
			subject = loginSupport.forceCreateLoginSubject(accountId);
			result = SystemExpressionJobUtils.executeJobForManual(sysExprJobOid);
		} catch (ServiceException se) {
			se.printStackTrace();
			exceptionMessage = se.getMessage().toString();
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage() == null) {
				exceptionMessage = e.toString();
			} else {
				exceptionMessage = e.getMessage().toString();
			}
		} finally {
			if (result == null) {
				result = new SysExprJobLogVO();
			}		
			if (subject != null) {
				subject.logout();
			}
			if (!StringUtils.isBlank(exceptionMessage) && StringUtils.isBlank(result.getFaultMsg())) {
				result.setFaultMsg(exceptionMessage);
			}
		}
		return objectMapper.writeValueAsString(result);
	}
	
}
