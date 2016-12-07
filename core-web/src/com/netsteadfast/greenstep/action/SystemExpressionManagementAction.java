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
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.po.hbm.TbSysExpression;
import com.netsteadfast.greenstep.service.ISysExpressionService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.SysExpressionVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemExpressionManagementAction")
@Scope
public class SystemExpressionManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -8394144852840378695L;
	private ISysExpressionService<SysExpressionVO, TbSysExpression, String> sysExpressionService;
	private Map<String, String> typeMap = this.providedSelectZeroDataMap(true);	
	private SysExpressionVO sysExpression = new SysExpressionVO();
	private String exprOid = "";
	
	public SystemExpressionManagementAction() {
		super();
	}
	
	public ISysExpressionService<SysExpressionVO, TbSysExpression, String> getSysExpressionService() {
		return sysExpressionService;
	}

	@Autowired
	@Resource(name="core.service.SysExpressionService")
	@Required		
	public void setSysExpressionService(
			ISysExpressionService<SysExpressionVO, TbSysExpression, String> sysExpressionService) {
		this.sysExpressionService = sysExpressionService;
	}

	private void initData() throws ServiceException, Exception {
		this.typeMap = ScriptTypeCode.getTypeMap(true);
	}
	
	private void loadSysExpressionData() throws ServiceException, Exception {
		this.transformFields2ValueObject(this.sysExpression, new String[]{"oid"});
		DefaultResult<SysExpressionVO> result = this.sysExpressionService.findObjectByOid(sysExpression);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		this.sysExpression = result.getValue();
		this.exprOid = UploadSupportUtils.create(
				Constants.getSystem(), 
				UploadTypes.IS_TEMP, 
				false, 
				this.sysExpression.getContent().getBytes(Constants.BASE_ENCODING), 
				this.sysExpression.getExprId()+".txt");			
	}
	
	/**
	 * core.systemExpressionManagementAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0002Q")
	public String execute() throws Exception {
		try {
			this.initData();
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
	
	/**
	 * core.systemExpressionCreateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0002A")
	public String create() throws Exception {
		try {
			this.initData();
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
	
	/**
	 * core.systemExpressionEditAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0002E")
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadSysExpressionData();
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
		return MenuSupportUtils.getProgramName(this.getProgramId(), this.getLocaleLang());
	}

	@Override
	public String getProgramId() {
		return super.getActionMethodProgramId();
	}

	public Map<String, String> getTypeMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.typeMap);
		return typeMap;
	}

	public SysExpressionVO getSysExpression() {
		return sysExpression;
	}

	public String getExprOid() {
		return exprOid;
	}

}
