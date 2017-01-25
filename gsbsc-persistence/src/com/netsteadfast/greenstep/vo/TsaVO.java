/* 
 * Copyright 2012-2017 bambooCORE, greenstep of copyright Chen Xin Nien
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

public class TsaVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = 1180435792141403132L;
	private String oid;
	private String name;
	private String description;
	private int integrationOrder;
	private int forecastNext;
	
	public TsaVO() {
		
	}
	
	public TsaVO(String oid, String name, String description, int integrationOrder, int forecastNext) {
		super();
		this.oid = oid;
		this.name = name;
		this.description = description;
		this.integrationOrder = integrationOrder;
		this.forecastNext = forecastNext;
	}
	
	@Override
	public String getOid() {
		return this.oid;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIntegrationOrder() {
		return integrationOrder;
	}

	public void setIntegrationOrder(int integrationOrder) {
		this.integrationOrder = integrationOrder;
	}

	public int getForecastNext() {
		return forecastNext;
	}

	public void setForecastNext(int forecastNext) {
		this.forecastNext = forecastNext;
	}
	
}
