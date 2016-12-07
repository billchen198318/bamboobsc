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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.util.MenuSupportUtils;

@ControllerAuthority(check=true)
@Controller("core.web.controller.CommonSignatureAction")
@Scope
public class CommonSignatureAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = 8863724031372295431L;
	private String system=""; // 系統 CORE, BSC ...
	private String uploadOidField=""; // 完成後要填入 TB_SYS_UPLOAD.OID 的欄位
	private String callJsFunction=""; // 正常完成後呼叫的JS
	private String callJsErrFunction=""; // 上傳失敗後呼叫的JS
	
	public CommonSignatureAction() {
		super();
	}
	
	/**
	 * core.commonSignatureAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROGCOMM0003Q")	
	public String execute() throws Exception {
		try {
			// nothing...
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return SUCCESS;		
	}
	
	@Override
	public String getProgramName() {
		return MenuSupportUtils.getProgramName(this.getProgramId(), this.getLocaleLang());
	}

	@Override
	public String getProgramId() {
		return super.getActionMethodProgramId();
	}	

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getUploadOidField() {
		return uploadOidField;
	}

	public void setUploadOidField(String uploadOidField) {
		this.uploadOidField = uploadOidField;
	}

	public String getCallJsFunction() {
		return callJsFunction;
	}

	public void setCallJsFunction(String callJsFunction) {
		this.callJsFunction = callJsFunction;
	}

	public String getCallJsErrFunction() {
		return callJsErrFunction;
	}

	public void setCallJsErrFunction(String callJsErrFunction) {
		this.callJsErrFunction = callJsErrFunction;
	}	

}
