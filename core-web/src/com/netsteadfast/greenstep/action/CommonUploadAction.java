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

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.StrutsConstants;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.YesNo;

@ControllerAuthority(check=true)
@Controller("core.web.controller.CommonUploadAction")
@Scope
public class CommonUploadAction extends BaseSupportAction {
	private static final long serialVersionUID = 2015902987674490973L;
	protected Logger logger=Logger.getLogger(CommonUploadAction.class);
	private String type=""; // 參考 UploadSupportUtils 的 TYPE
	private String system=""; // 系統 CORE, BSC ...
	private String uploadOidField=""; // 完成後要填入 TB_SYS_UPLOAD.OID 的欄位
	private String callJsFunction=""; // 正常完成後呼叫的JS
	private String callJsErrFunction=""; // 上傳失敗後呼叫的JS
	private String isFile = YesNo.YES; // 是以檔案還是blob模式存入
	
	public CommonUploadAction() {
		super();
	}
	
	/**
	 * core.commonUploadAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROGCOMM0002Q")	
	public String execute() throws Exception {
		try {
			// nothing...
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			this.exceptionPage(e);
		}
		return SUCCESS;		
	}	
	
	public String getUploadMultipartMaxSize() {
		String maxsize = super.getLoadStrutsConstants().get(StrutsConstants.STRUTS_MULTIPART_MAXSIZE);
		return NumberUtils.isCreatable(maxsize) ? maxsize : "8388608";
	}
	
	public String getUploadMultipartMaxSizeLabel() {
		Long size = NumberUtils.toLong(this.getUploadMultipartMaxSize(), 0L);
		if (size<1024) {
			return size + " bytes";
		}
		size = size/1024;
		if (size<1024) {
			return size + " KB";
		}
		return (size/1024) + " MB";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getIsFile() {
		return isFile;
	}

	public void setIsFile(String isFile) {
		this.isFile = isFile;
	}

}
