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
package com.netsteadfast.greenstep.base.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

public abstract class BaseQueryGridJsonAction extends BaseJsonAction {
	private static final long serialVersionUID = 2263282813660658050L;
	
	public BaseQueryGridJsonAction() {
		super();
	}
	
	@JSON
	public abstract List<Map<String, String>> getItems();
	
	@JSON
	public abstract String getPageOfShowRow();
	
	@JSON
	public abstract String getPageOfSelect();
	
	@JSON
	public abstract String getPageOfCountSize();
	
	@JSON
	public abstract String getPageOfSize();
	
}
