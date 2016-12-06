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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseQueryGridJsonAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.SystemForm;
import com.netsteadfast.greenstep.model.FormResultType;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.util.SystemFormUtils;
import com.netsteadfast.greenstep.vo.SysFormMethodVO;

@SystemForm
@Controller("core.web.controller.CommonLoadFormAction")
@Scope
public class CommonLoadFormAction extends BaseQueryGridJsonAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = 1616413828281190977L;
	protected Logger logger=Logger.getLogger(CommonLoadFormAction.class);
	private String message = "";
	private String success = IS_NO;
	private Map<String, Object> datas = new HashMap<String, Object>(); // 備用放資料用的
	private List<Map<String, String>> items=new ArrayList<Map<String, String>>(); // 如果用查詢事件, 拿來放結果
	private String prog_id = ""; // 程式id 與 TB_SYS_PROG.PROG_ID 配合
	private String form_id = ""; // form 的 id
	private String form_method = ""; // form 的 method
	private String viewPage = ""; // jsp 位置
	private String redirectUrl = ""; // 導向用的url 
	
	public CommonLoadFormAction() {
		super();
	}

	public void processExpression(SysFormMethodVO formMethod) throws ControllerException, ServiceException, Exception {		
		Map<String, String> resultMap = SystemFormUtils.processExpression(
				formMethod, 
				this, this.datas, this.getPageOf(), this.getSearchValue(), this.items, 
				this.getFields(), this.getFieldsId(), this.getFieldsMessage(),
				super.getHttpServletRequest());	
		if (FormResultType.DEFAULT.equals(formMethod.getResultType())) {
			this.viewPage = SystemFormUtils.getViewPage(resultMap);
		}		
		if (FormResultType.JSON.equals(formMethod.getResultType())) {
			this.message = SystemFormUtils.getJsonMessage(resultMap);
			this.success = SystemFormUtils.getJsonSuccess(resultMap);
		}
		if (FormResultType.REDIRECT.equals(formMethod.getResultType())) {
			this.redirectUrl = SystemFormUtils.getRedirectUrl(resultMap);
		}
	}
	
	@JSON(serialize=false)
	@SystemForm
	public String execute() throws Exception {
		String resultName = RESULT_SEARCH_NO_DATA;
		if ( StringUtils.isBlank(form_id) || StringUtils.isBlank(form_method) ) {			
			this.message = "no settings data to init form!";
			this.setErrorMessage( this.message );
			return resultName;
		}
		try {
			SysFormMethodVO formMethod = SystemFormUtils.findFormMethod(
					this.form_id, this.form_method);
			resultName = formMethod.getResultType();
			this.processExpression(formMethod);
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
			this.setErrorMessage( this.message );							
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
			this.setErrorMessage( this.message );
			if (FormResultType.DEFAULT.equals(resultName)) {
				resultName = RESULT_NO_AUTHORITH;
			}						
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
			this.setErrorMessage( this.message );	
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			if (e.getMessage()==null) { 
				this.message=e.toString();
				this.logger.error(e.toString());
			} else {
				this.message=e.getMessage().toString();
				this.logger.error(e.getMessage());
			}						
			this.setErrorMessage( this.message );
			this.success = IS_EXCEPTION;				
			if (FormResultType.DEFAULT.equals(resultName)) {
				resultName = ERROR;
			}
		}
		return resultName;
	}
	
	@JSON
	@Override
	public String getProgramName() {
		try {
			return MenuSupportUtils.getProgramName(this.getProgramId(), this.getLocaleLang());
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@JSON
	@Override
	public String getProgramId() {
		return super.getActionMethodProgramId();
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
	public Map<String, Object> getDatas() {
		return datas;
	}
	
	@JSON
	@Override
	public String getPageOfShowRow() {
		return super.getPageOf().getShowRow();
	}
	
	@JSON
	@Override
	public String getPageOfSelect() {
		return super.getPageOf().getSelect();
	}
	
	@JSON
	@Override
	public String getPageOfCountSize() {
		return super.getPageOf().getCountSize();
	}
	
	@JSON
	@Override
	public String getPageOfSize() {
		return super.getPageOf().getSize();
	}		
	
	@JSON
	@Override
	public List<Map<String, String>> getItems() {
		return items;
	}	

	@JSON
	public String getProg_id() {
		return prog_id;
	}

	public void setProg_id(String prog_id) {
		this.prog_id = prog_id;
	}

	@JSON
	public String getForm_id() {
		return form_id;
	}

	public void setForm_id(String form_id) {
		this.form_id = form_id;
	}

	@JSON
	public String getForm_method() {
		return form_method;
	}

	public void setForm_method(String form_method) {
		this.form_method = form_method;
	}

	@JSON
	public String getViewPage() {
		return viewPage;
	}

	@JSON
	public String getRedirectUrl() {
		return redirectUrl;
	}
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
}
