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
package com.netsteadfast.greenstep.vo;

import com.netsteadfast.greenstep.base.model.BaseValueObj;

public class DataQueryVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = 2938284020447071708L;
	private String oid;
	private String name;
	private String conf;
	private String queryExpression;
	private String mapperOid;
	
	// 頁面抓取資料要用
	private String dataSourceConfOid; // QC_DATA_SOURCE_CONF.OID
	
	public DataQueryVO() {
		
	}
	
	public DataQueryVO(String oid, String name) {
		this.oid = oid;
		this.name = name;
	}
	
	@Override
	public String getOid() {
		return oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConf() {
		return conf;
	}

	public void setConf(String conf) {
		this.conf = conf;
	}

	public String getQueryExpression() {
		return queryExpression;
	}

	public void setQueryExpression(String queryExpression) {
		this.queryExpression = queryExpression;
	}

	public String getMapperOid() {
		return mapperOid;
	}

	public void setMapperOid(String mapperOid) {
		this.mapperOid = mapperOid;
	}

	public String getDataSourceConfOid() {
		return dataSourceConfOid;
	}

	public void setDataSourceConfOid(String dataSourceConfOid) {
		this.dataSourceConfOid = dataSourceConfOid;
	}

}
