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
package com.netsteadfast.greenstep.base.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.ControllerException;

public class CheckFieldHandler implements java.io.Serializable {
	private static final long serialVersionUID = -8080347109488249788L;
	private Map<String, String> actionFields = null;
	private List<String> actionFieldsId = null;
	private Map<String, String> actionFieldsMessage = null;
	private List<String> fieldsNames = new LinkedList<String>();
	private List<String> fieldsMessages = new LinkedList<String>();
	private List<Class<IActionFieldsCheckUtils>> checkUtilsClazzs = new LinkedList<Class<IActionFieldsCheckUtils>>();
	private StringBuilder msg = new StringBuilder();
	
	public CheckFieldHandler() {
		
	}
	
	public CheckFieldHandler(Map<String, String> actionFields, List<String> actionFieldsId, Map<String, String> actionFieldsMessage) {
		this.actionFields = actionFields;
		this.actionFieldsId = actionFieldsId;
		this.actionFieldsMessage = actionFieldsMessage;
	}
	
	@SuppressWarnings("unchecked")
	public CheckFieldHandler add(String fieldsName, Class<?> checkUtilsClass, String message) {
		this.fieldsNames.add(fieldsName);
		this.fieldsMessages.add(StringUtils.defaultString(message));
		this.checkUtilsClazzs.add( (Class<IActionFieldsCheckUtils>) checkUtilsClass );
		return this;
	}
	
	public Map<String, String> getActionFields() {
		return actionFields;
	}

	public void setActionFields(Map<String, String> actionFields) {
		this.actionFields = actionFields;
	}

	public List<String> getActionFieldsId() {
		return actionFieldsId;
	}

	public void setActionFieldsId(List<String> actionFieldsId) {
		this.actionFieldsId = actionFieldsId;
	}

	public Map<String, String> getActionFieldsMessage() {
		return actionFieldsMessage;
	}

	public void setActionFieldsMessage(Map<String, String> actionFieldsMessage) {
		this.actionFieldsMessage = actionFieldsMessage;
	}

	public CheckFieldHandler process() {
		if (this.actionFieldsId == null) {
			this.actionFieldsId = new LinkedList<String>();
		}
		if (this.actionFieldsMessage == null) {
			this.actionFieldsMessage = new HashMap<String, String>();
		}
		if (fieldsNames==null || fieldsMessages==null || checkUtilsClazzs==null 
				|| (fieldsNames.size()!=fieldsMessages.size()) 
				|| (fieldsNames.size()!=checkUtilsClazzs.size())) {
			throw new java.lang.IllegalArgumentException("check filed args error!");
		}
		for (int i=0; i<fieldsNames.size(); i++) {
			String value = this.actionFields.get(fieldsNames.get(i));
			try {
				IActionFieldsCheckUtils checkUtils = checkUtilsClazzs.get(i).newInstance();
				if (!checkUtils.check(value)) {
					actionFieldsId.add( fieldsNames.get(i) );
					/**
					 * 因為 inputfieldNoticeMsgLabel 不需要斷行效果
					 * 這裡的 <BR/> 必須與 BaseSupportAction 的 joinPageMessage 所加上的<BR/> 一樣字串
					 */					
					actionFieldsMessage.put(fieldsNames.get(i), fieldsMessages.get(i).replaceAll(Constants.HTML_BR, " ") );
					msg.append( fieldsMessages.get(i) ).append(Constants.HTML_BR);
				}				
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return this;
	}
	
	public CheckFieldHandler single(String fieldsName, boolean checkResult, String message) {
		if (this.actionFieldsId == null) {
			this.actionFieldsId = new LinkedList<String>();
		}
		if (this.actionFieldsMessage == null) {
			this.actionFieldsMessage = new HashMap<String, String>();
		}
		if (!checkResult) {
			return this;
		}
		String name[] = fieldsName.replaceAll(" ", "").split("[|]");
		for (int i=0; i<name.length; i++) {
			if (StringUtils.isBlank(name[i])) {
				continue;
			}
			String idName = name[i].trim();
			actionFieldsId.add(idName);
			/**
			 * 因為 inputfieldNoticeMsgLabel 不需要斷行效果
			 * 這裡的 <BR/> 必須與 BaseSupportAction 的 joinPageMessage 所加上的<BR/> 一樣字串
			 */
			actionFieldsMessage.put(idName, message.replaceAll(Constants.HTML_BR, " "));
			fieldsMessages.add( message );
		}
		msg.append( StringUtils.defaultString(message) ).append(Constants.HTML_BR);
		return this;
	}
	
	public String getMessage() {
		return this.msg.toString();
	}
	
	/**
	 * only for extends BaseJsonAction action object
	 * 
	 * @throws ControllerException
	 */
	public void throwMessage() throws ControllerException {
		if (this.msg == null || this.msg.length() < 1) {
			return;
		}
		throw new ControllerException( this.msg.toString() );
	}
	
}
