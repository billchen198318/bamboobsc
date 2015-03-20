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

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.webservice.IHelloService;

/**
 * this is for demo sample!
 *
 */
@Service("core.webservice.HelloService")
@WebService
@Path("/")
@Produces("application/json")
public class HelloServiceImpl implements IHelloService {

	@WebMethod
	@POST
	@Path("/hello/")	
	@Override
	public String play(
			@WebParam(name="message") 
			String message) throws Exception {
		return Constants.getSystem() + ":" + StringUtils.defaultString(message);
	}
	
}
