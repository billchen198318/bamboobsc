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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.json.annotations.JSON;

import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.model.CheckFieldHandler;
import com.netsteadfast.greenstep.base.model.YesNo;

public abstract class BaseJsonAction extends BaseSupportAction {
	private static final long serialVersionUID = 6421837320727443428L;
	public static final String IS_YES=YesNo.YES;
	public static final String IS_NO=YesNo.NO;
	public static final String IS_EXCEPTION = "E"; // exception 狀態
	private String noAllowMessage = "";
	protected List<String> fieldsId = new LinkedList<String>(); // 當次 submit 的送出欄位 id 記錄用 , 主要用來記錄輸入條件不合的id 
	protected Map<String, String> fieldsMessage = new HashMap<String, String>(); // 主要用來記錄輸入條件不合的id 的訊息
	
	public BaseJsonAction() {
		super();
	}
	
	/**
	 * 元責上 UserLoginInterceptor 與 ControllerAuthorityCheckInterceptor 會處理沒登入 or 沒有權限的 action
	 * 這理因該都會是回傳 true
	 * 
	 * @return
	 */
	protected boolean allowJob() {
		if (!IS_YES.equals(this.getLogin())) {
			this.noAllowMessage = "Login status failure!";
			return false;
		}
		if (!IS_YES.equals(this.getIsAuthorize())) {
			this.noAllowMessage = "no permission!";
			return false;
		}		
		return true;
	}
	
	protected String getNoAllowMessage() {
		return this.noAllowMessage;
	}
	
	protected CheckFieldHandler getCheckFieldHandler() {
		return super.checkFields(this.fieldsId, this.fieldsMessage);
	}
	
	protected void throwMessage(String message) throws ControllerException {
		throw new ControllerException(message + super.getHtmlBr());
	}
	
	protected void throwMessage(String fieldId, String message) throws ControllerException {
		if (!StringUtils.isBlank(fieldId)) {
			String name[] = fieldId.replaceAll(" ", "").split("[|]");
			for (int i=0; i<name.length; i++) {
				if (StringUtils.isBlank(name[i])) {
					continue;
				}
				String idName = name[i].trim();
				this.fieldsId.add(idName);
				/**
				 * 因為 inputfieldNoticeMsgLabel 不需要斷行效果
				 * 這裡的 <BR/> 必須與 BaseSupportAction 的 joinPageMessage 所加上的<BR/> 一樣字串
				 */				
				this.fieldsMessage.put(idName, message.replaceAll(super.getHtmlBr(), " "));
			}			
		}
		this.throwMessage(message);
	}	
	
	protected void addFieldsNoticeMessage(String fieldId, String message) {
		this.fieldsId.add(fieldId);
		/**
		 * 因為 inputfieldNoticeMsgLabel 不需要斷行效果
		 * 這裡的 <BR/> 必須與 BaseSupportAction 的 joinPageMessage 所加上的<BR/> 一樣字串
		 */				
		this.fieldsMessage.put(fieldId, message.replaceAll(super.getHtmlBr(), " "));
	}
	
	// -----------------------------------------------------
	// 最外層的 json action 要曝出method 才可
	// -----------------------------------------------------
	@JSON
	public abstract String getLogin(); 
	
	@JSON
	public abstract String getIsAuthorize();
	
	@JSON
	public abstract String getMessage();	
	
	@JSON
	public abstract String getSuccess();	
	
	@JSON
	public abstract List<String> getFieldsId();
	
	@JSON
	public abstract Map<String, String> getFieldsMessage();
	
	// -----------------------------------------------------
	
}
