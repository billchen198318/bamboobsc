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

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

import ognl.Ognl;

public class ManualDataSourceFactory {
	
	private static boolean checkDataSourceClass(Class<?> clazz) throws Exception {
		if (null == clazz) {
			return false;
		}
		boolean isDataSource = false;
		Class<?>[] ifs = clazz.getInterfaces();
		for (int i=0; ifs!=null && i<ifs.length; i++) {
			if (ifs[i].getSimpleName().equals("DataSource")) {
				isDataSource = true;
			}
		}
		return isDataSource;
	}
	
	public static DataSource getDataSource(Class<?> dataSourceClass, String url, String user, String password) throws Exception {
		if (!checkDataSourceClass(dataSourceClass)) {
			throw new Exception("DataSource Class is not implements DataSource. error!");
		}
		if (StringUtils.isBlank(url) || StringUtils.isBlank(user)) {
			throw new Exception("url or user is required!");
		}
		DataSource ds = (DataSource)dataSourceClass.newInstance();
		Ognl.setValue("url", ds, url);
		Ognl.setValue("user", ds, user);
		Ognl.setValue("password", ds, (null==password ? "" : password) );
		return ds;
	}

}
