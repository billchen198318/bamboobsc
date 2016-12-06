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

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.ScriptTypeCode;
import com.netsteadfast.greenstep.model.FormResultType;
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.po.hbm.TbSysForm;
import com.netsteadfast.greenstep.po.hbm.TbSysFormMethod;
import com.netsteadfast.greenstep.service.ISysFormMethodService;
import com.netsteadfast.greenstep.service.ISysFormService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.SysFormMethodVO;
import com.netsteadfast.greenstep.vo.SysFormVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemFormMethodManagementAction")
@Scope
public class SystemFormMethodManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = 7951389999447539518L;
	private ISysFormService<SysFormVO, TbSysForm, String> sysFormService;
	private ISysFormMethodService<SysFormMethodVO, TbSysFormMethod, String> sysFormMethodService; 
	private SysFormVO form = new SysFormVO();
	private Map<String, String> resultTypeMap = this.providedSelectZeroDataMap( true );
	private Map<String, String> typeMap = this.providedSelectZeroDataMap( true );
	private SysFormMethodVO formMethod = new SysFormMethodVO();
	private String exprOid = "";
	
	public SystemFormMethodManagementAction() {
		super();
	}
	
	public ISysFormService<SysFormVO, TbSysForm, String> getSysFormService() {
		return sysFormService;
	}

	@Autowired
	@Resource(name="core.service.SysFormService")
	@Required		
	public void setSysFormService(
			ISysFormService<SysFormVO, TbSysForm, String> sysFormService) {
		this.sysFormService = sysFormService;
	}	
	
	public ISysFormMethodService<SysFormMethodVO, TbSysFormMethod, String> getSysFormMethodService() {
		return sysFormMethodService;
	}

	@Autowired
	@Resource(name="core.service.SysFormMethodService")
	@Required			
	public void setSysFormMethodService(
			ISysFormMethodService<SysFormMethodVO, TbSysFormMethod, String> sysFormMethodService) {
		this.sysFormMethodService = sysFormMethodService;
	}

	private void initData() throws ServiceException, Exception {
		this.resultTypeMap = FormResultType.getTypeMap( true );
		this.typeMap = ScriptTypeCode.getTypeMap( true );
	}
	
	private void loadFormData() throws ServiceException, Exception {
		this.transformFields2ValueObject(this.form, new String[]{"oid"});
		DefaultResult<SysFormVO> result = this.sysFormService.findObjectByOid(form);
		if ( result.getValue()==null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		this.form = result.getValue();
	}
	
	private void loadFormMethodData() throws ServiceException, Exception {
		this.transformFields2ValueObject(this.formMethod, new String[]{"oid"});
		DefaultResult<SysFormMethodVO> result = this.sysFormMethodService.findObjectByOid(formMethod);
		if ( result.getValue() == null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		this.formMethod = result.getValue();
		this.form.setFormId( this.formMethod.getFormId() );
		DefaultResult<SysFormVO> fResult = this.sysFormService.findByUK(this.form);
		if ( fResult.getValue() == null ) {
			throw new ServiceException( fResult.getSystemMessage().getValue() );
		}
		this.form = fResult.getValue();
		this.exprOid = UploadSupportUtils.create(
				Constants.getSystem(), 
				UploadTypes.IS_TEMP, 
				false, 
				this.formMethod.getExpression(), 
				this.formMethod.getFormId() + "." + this.formMethod.getType() );
	}
	
	/**
	 * core.systemFormMethodManagementAction.action
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0014Q")
	public String execute() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadFormData();
			forward = SUCCESS;
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return forward;
	}		
	
	/**
	 * core.systemFormMethodCreateAction.action
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0014A")
	public String create() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadFormData();
			forward = SUCCESS;
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return forward;
	}		
	
	/**
	 * core.systemFormMethodEditAction.action
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0014E")
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadFormMethodData();
			forward = SUCCESS;
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return forward;
	}		
	
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

	@Override
	public String getProgramId() {
		return super.getActionMethodProgramId();
	}

	public SysFormVO getForm() {
		return form;
	}

	public Map<String, String> getResultTypeMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.resultTypeMap);
		return resultTypeMap;
	}

	public Map<String, String> getTypeMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.typeMap);
		return typeMap;
	}

	public SysFormMethodVO getFormMethod() {
		return formMethod;
	}

	public String getExprOid() {
		return exprOid;
	}

}
