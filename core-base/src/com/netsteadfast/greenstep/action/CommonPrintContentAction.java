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
import com.netsteadfast.greenstep.util.UploadSupportUtils;

/**
 * 這個 controller action 主要是配合 ApiWebService , 讀取輸出的 url 顯示報表結果
 */
@ControllerAuthority(check=true)
@Controller("core.web.controller.CommonPrintContentAction")
@Scope
public class CommonPrintContentAction extends BaseSupportAction {
	private static final long serialVersionUID = -5861669971660024741L;
	private String oid = ""; // TB_UPLOAD.OID
	private String value = "";
	
	public CommonPrintContentAction() {
		super();
	}
	
	private void loadData() throws ServiceException, Exception {
		byte[] data = UploadSupportUtils.getDataBytes(this.oid);
		this.value = new String(data, Constants.BASE_ENCODING);
	}
	
	/**
	 * core.printContentAction.action
	 * bsc.printContentAction.action
	 * qcharts.printContentAction.action
	 */
	public String execute() throws Exception {
		String forward = RESULT_BLANK;
		try {
			if (StringUtils.isBlank(this.oid)) {
				return forward;
			}
			this.loadData();
			forward = RESULT_BLANK_VALUE;
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			this.exceptionPage(e);
		}
		return forward;				
	}	
	
	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getValue() {
		if (null == this.value) {
			return "";
		}
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
