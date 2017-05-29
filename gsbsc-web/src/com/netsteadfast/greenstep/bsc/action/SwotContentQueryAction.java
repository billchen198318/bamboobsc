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
package com.netsteadfast.greenstep.bsc.action;

import java.util.List;
import java.util.Map;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.chain.SimpleChain;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ChainResultObj;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.bsc.action.utils.SelectItemFieldCheckUtils;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.SwotContentQueryAction")
@Scope
public class SwotContentQueryAction extends BaseJsonAction {
	private static final long serialVersionUID = -4805022436667752727L;
	protected Logger logger=Logger.getLogger(SwotContentQueryAction.class);
	private String message = "";
	private String success = IS_NO;
	private String body = "";
	private String reportId = ""; // 產生 BB_SWOT_REPORT_MST , BB_SWOT_REPORT_DTL 資料的 report_id
	
	public SwotContentQueryAction() {
		super();
	}
	
	private void checkFields() throws ControllerException, Exception {
		this.getCheckFieldHandler()
		.add("visionOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG002D0008Q_visionOid") )
		.add("organizationOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG002D0008Q_organizationOid") )
		.process().throwMessage();	
	}
	
	@SuppressWarnings("unchecked")
	private Context getChainContext() throws Exception {
		Context context = new ContextBase();
		context.put("visionOid", this.getFields().get("visionOid"));
		context.put("organizationOid", this.getFields().get("organizationOid"));
		context.put("strengthsName", this.getText("TPL.BSC_PROG002D0008Q_s"));
		context.put("weaknessesName", this.getText("TPL.BSC_PROG002D0008Q_w"));
		context.put("opportunitiesName", this.getText("TPL.BSC_PROG002D0008Q_o"));
		context.put("threatsName", this.getText("TPL.BSC_PROG002D0008Q_t"));
		return context;
	}
	
	private void getContent() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		Context context = this.getChainContext();
		SimpleChain chain = new SimpleChain();
		ChainResultObj resultObj = chain.getResultFromResource("swotHtmlContentChain", context);
		this.body = String.valueOf( resultObj.getValue() );
		this.message = resultObj.getMessage();
		if ( !StringUtils.isBlank(this.body) && this.body.startsWith("<!-- BSC_PROG002D0008Q -->") ) {
			this.success = IS_YES;
		}				
	}
	
	private void getPdfDataContent() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		Context context = this.getChainContext();
		SimpleChain chain = new SimpleChain();
		ChainResultObj resultObj = chain.getResultFromResource("swotPdfDataContentChain", context);		
		this.reportId = String.valueOf( resultObj.getValue() );
		this.message = resultObj.getMessage();
		if ( !StringUtils.isBlank(this.reportId) ) {
			this.success = IS_YES;
		}
	}
	
	/**
	 * bsc.swotContentQueryAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0008Q")
	public String execute() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.getContent();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}	
	
	/**
	 * bsc.swotPdfDataAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0008Q")
	public String doPdfData() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.getPdfDataContent();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}		
	
	@JSON
	@Override
	public String getLogin() {
		return super.isAccountLogin();
	}
	
	@JSON
	@Override
	public String getIsAuthorize() {
		return super.isActionAuthorize();
	}	

	@JSON
	@Override
	public String getMessage() {
		return this.message;
	}

	@JSON
	@Override
	public String getSuccess() {
		return this.success;
	}

	@JSON
	@Override
	public List<String> getFieldsId() {
		return this.fieldsId;
	}

	@JSON
	public String getBody() {
		return body;
	}

	@JSON
	public String getReportId() {
		return reportId;
	}
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
}
