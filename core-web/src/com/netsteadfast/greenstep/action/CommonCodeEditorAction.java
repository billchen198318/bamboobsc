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
package com.netsteadfast.greenstep.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.util.UploadSupportUtils;

@ControllerAuthority(check=false)
@Controller("core.web.controller.CommonCodeEditorAction")
@Scope
public class CommonCodeEditorAction extends BaseSupportAction {
	private static final long serialVersionUID = 7206242435343900788L;
	private String codeContent = "";
	private String oid = "";
	private String cbMode = YesNo.NO; // 下方有 button 點選執行代入的 javascript function 模式
	private String valueFieldId = ""; // 要放值的hidden field
	private String okFn = ""; // ok button 會執行的 js function
	private String lang = "java"; // java, jsp
	
	public CommonCodeEditorAction() {
		super();
	}	
	
	/**
	 * core.commonCodeEditorAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROGCOMM0004Q")	
	public String execute() throws Exception {
		try {
			if ( !StringUtils.isBlank(this.oid) ) {
				this.codeContent = new String( UploadSupportUtils.getDataBytes(this.oid) , Constants.BASE_ENCODING );
			}			
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			this.exceptionPage(e);
		}
		return SUCCESS;		
	}

	public String getCodeContent() {
		return codeContent;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getCbMode() {
		return cbMode;
	}

	public void setCbMode(String cbMode) {
		this.cbMode = cbMode;
	}

	public String getValueFieldId() {
		return valueFieldId;
	}

	public void setValueFieldId(String valueFieldId) {
		this.valueFieldId = valueFieldId;
	}

	public String getOkFn() {
		return okFn;
	}

	public void setOkFn(String okFn) {
		this.okFn = okFn;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

}
