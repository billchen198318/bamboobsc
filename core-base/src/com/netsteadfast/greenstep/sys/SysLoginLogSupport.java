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
package com.netsteadfast.greenstep.sys;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.util.SimpleUtils;

public class SysLoginLogSupport {
	protected static Logger log=Logger.getLogger(SysLoginLogSupport.class);
	
	public static void log(String userId) {
		if ( StringUtils.isBlank(userId) ) {
			log.warn("null userId");
			return;
		}
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = (NamedParameterJdbcTemplate)AppContext.getBean("namedParameterJdbcTemplate");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("oid", SimpleUtils.getUUIDStr());
		paramMap.put("user", userId);
		paramMap.put("cuserid", "SYS");
		paramMap.put("cdate", new Date());
		try {
			namedParameterJdbcTemplate.update("insert into tb_sys_login_log(OID, USER, CUSERID, CDATE) values(:oid, :user, :cuserid, :cdate)", paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.error( e.getMessage().toString() );
		}		
	}
	
}
