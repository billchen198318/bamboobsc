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
package com.netsteadfast.greenstep.base;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.model.SqlGenerateUtil;
import com.netsteadfast.greenstep.po.hbm.TbSysCode;

public class SysMessageUtil {
	private static JdbcTemplate jdbcTemplate;
	private static Map<String, String> sysMsgMap=new HashMap<String, String>();
	
	static {
		init();
	}
	
	private static void init() {
		if (jdbcTemplate!=null) {
			return;
		}
		try {
			jdbcTemplate=(JdbcTemplate)AppContext.getBean("jdbcTemplate");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String get(String code) {
		if (code==null || "".equals(code)) {
			return "";
		}
		if (sysMsgMap.get(code)!=null) {
			return sysMsgMap.get(code);
		}
		init();
		if (jdbcTemplate==null) {
			return "";
		}
		String message=null;
		TbSysCode sysCode=new TbSysCode();
		sysCode.setCode(code);
		Map<String, Object> queryMap=getQuery(sysCode);
		if (queryMap==null) {
			return "";
		}
		try {
			message=jdbcTemplate.queryForObject(
					((String)queryMap.get(SqlGenerateUtil.RETURN_SQL)), 
					(Object[])queryMap.get(SqlGenerateUtil.RETURN_PARAMS), 
					String.class);	
			if (message!=null) {
				sysMsgMap.put(code, message);
			}
		} catch (EmptyResultDataAccessException eda) {
			System.out.println(eda.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message==null ? code : message;
	}
	
	private static Map<String, Object> getQuery(TbSysCode tbSysCode) {
		if (tbSysCode==null) {
			return null;
		}
		try {
			return SqlGenerateUtil.getFindByUK(tbSysCode, new String[]{"NAME"});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
