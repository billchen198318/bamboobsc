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
package com.netsteadfast.greenstep.model;

import com.netsteadfast.greenstep.base.model.YesNo;

/**
 * 發佈訊息基本的產出物件
 *
 */
public class WebMessagePublishBaseObj implements java.io.Serializable {
	private static final long serialVersionUID = -4240811053746561554L;
	private String success = YesNo.NO;
	private String message = "";
	private String isAuthorize = YesNo.NO;
	private String login = YesNo.NO;
	
	public String getSuccess() {
		return success;
	}
	
	public void setSuccess(String success) {
		this.success = success;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getIsAuthorize() {
		return isAuthorize;
	}
	
	public void setIsAuthorize(String isAuthorize) {
		this.isAuthorize = isAuthorize;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}

}
