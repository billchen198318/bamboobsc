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

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseQueryGridJsonAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.SystemForm;
import com.netsteadfast.greenstep.model.FormResultType;
import com.netsteadfast.greenstep.po.hbm.TbSysForm;
import com.netsteadfast.greenstep.po.hbm.TbSysFormMethod;
import com.netsteadfast.greenstep.po.hbm.TbSysFormTemplate;
import com.netsteadfast.greenstep.service.ISysFormMethodService;
import com.netsteadfast.greenstep.service.ISysFormService;
import com.netsteadfast.greenstep.service.ISysFormTemplateService;
import com.netsteadfast.greenstep.util.FSUtils;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.util.ScriptExpressionUtils;
import com.netsteadfast.greenstep.vo.SysFormMethodVO;
import com.netsteadfast.greenstep.vo.SysFormTemplateVO;
import com.netsteadfast.greenstep.vo.SysFormVO;

@SystemForm
@Controller("core.web.controller.CommonLoadFormAction")
@Scope
public class CommonLoadFormAction extends BaseQueryGridJsonAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = 1616413828281190977L;
	protected Logger logger=Logger.getLogger(CommonLoadFormAction.class);
	private static final String FORM_PAGE_PATH = "pages/sys-form-pages/";
	private ISysFormMethodService<SysFormMethodVO, TbSysFormMethod, String> sysFormMethodService;
	private ISysFormService<SysFormVO, TbSysForm, String> sysFormService;
	private ISysFormTemplateService<SysFormTemplateVO, TbSysFormTemplate, String> sysFormTemplateService;
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
	
	@JSON(serialize=false)
	public ISysFormMethodService<SysFormMethodVO, TbSysFormMethod, String> getSysFormMethodService() {
		return sysFormMethodService;
	}

	@Autowired
	@Resource(name="core.service.SysFormMethodService")		
	public void setSysFormMethodService(
			ISysFormMethodService<SysFormMethodVO, TbSysFormMethod, String> sysFormMethodService) {
		this.sysFormMethodService = sysFormMethodService;
	}
	
	@JSON(serialize=false)
	public ISysFormService<SysFormVO, TbSysForm, String> getSysFormService() {
		return sysFormService;
	}

	@Autowired
	@Resource(name="core.service.SysFormService")	
	public void setSysFormService(
			ISysFormService<SysFormVO, TbSysForm, String> sysFormService) {
		this.sysFormService = sysFormService;
	}

	@JSON(serialize=false)
	public ISysFormTemplateService<SysFormTemplateVO, TbSysFormTemplate, String> getSysFormTemplateService() {
		return sysFormTemplateService;
	}

	@Autowired
	@Resource(name="core.service.SysFormTemplateService")		
	public void setSysFormTemplateService(
			ISysFormTemplateService<SysFormTemplateVO, TbSysFormTemplate, String> sysFormTemplateService) {
		this.sysFormTemplateService = sysFormTemplateService;
	}

	private Map<String, Object> getParameters(SysFormMethodVO formMethod) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("formMethodObj", formMethod);
		paramMap.put("form_id", this.form_id);
		paramMap.put("form_method", this.form_method);
		paramMap.put("actionObj", this);
		paramMap.put("redirectUrl", this.redirectUrl);
		paramMap.put("datas", this.datas);
		paramMap.put("pageOf", this.getPageOf());
		paramMap.put("searchValue", this.getSearchValue());
		return paramMap;
	}
	
	private SysFormMethodVO findFormMethod() throws ServiceException, Exception {
		SysFormMethodVO formMethod = new SysFormMethodVO();
		formMethod.setFormId( this.form_id );
		formMethod.setName( this.form_method );
		DefaultResult<SysFormMethodVO> result = this.sysFormMethodService.findByUK(formMethod);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		formMethod = result.getValue();
		return formMethod;
	}
	
	private SysFormVO findForm(SysFormMethodVO formMethod) throws ServiceException, Exception {
		SysFormVO form = new SysFormVO();
		form.setFormId(formMethod.getFormId());
		DefaultResult<SysFormVO> result = this.sysFormService.findByUK(form);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		form = result.getValue();
		return form;
	}
	
	private SysFormTemplateVO findTemplate(SysFormVO form) throws ServiceException, Exception {
		SysFormTemplateVO template = new SysFormTemplateVO();
		template.setTplId(form.getTemplateId());
		DefaultResult<SysFormTemplateVO> result = this.sysFormTemplateService.findByUK(template);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		template = result.getValue();
		this.writePage(template);		
		return template;
	}
	
	private void writePage(SysFormTemplateVO template) throws Exception {
		String pageFileFullPath = this.getHttpServletRequest().getSession().getServletContext().getRealPath("/");
		pageFileFullPath += FORM_PAGE_PATH + template.getFileName();
		if ( !FSUtils.writeStr2(pageFileFullPath, new String(template.getContent(), "utf-8") ) ) {
			throw new Exception("create page file error.");
		}		
	}

	public String processExpression() throws ControllerException, ServiceException, Exception {
		SysFormMethodVO formMethod = this.findFormMethod();
		SysFormVO form = this.findForm(formMethod);
		String expression = new String(formMethod.getExpression(), "utf-8");
		ScriptExpressionUtils.execute(
				formMethod.getType(), 
				expression, 
				null, 
				this.getParameters(formMethod));		
		if (FormResultType.DEFAULT.equals(formMethod.getResultType())) {
			SysFormTemplateVO template = this.findTemplate(form);
			this.viewPage = FORM_PAGE_PATH + template.getFileName();
		}		
		return formMethod.getResultType();
	}
	
	@JSON(serialize=false)
	@SystemForm
	public String execute() throws Exception {
		String resultName = RESULT_SEARCH_NO_DATA;
		if ( StringUtils.isBlank(form_id) || StringUtils.isBlank(form_method) ) {
			return resultName;
		}
		try {
			resultName = this.processExpression();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			if (e.getMessage()==null) { 
				this.message=e.toString();
				this.logger.error(e.toString());
			} else {
				this.message=e.getMessage().toString();
				this.logger.error(e.getMessage());
			}						
			this.success = IS_EXCEPTION;
		}
		return resultName;
	}
	
	@JSON
	@Override
	public String getProgramName() {
		try {
			return MenuSupportUtils.getProgramName(this.getProgramId());
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

}
