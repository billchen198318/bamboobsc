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

import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.model.CtxBeanTypes;
import com.netsteadfast.greenstep.po.hbm.TbSys;
import com.netsteadfast.greenstep.po.hbm.TbSysCtxBean;
import com.netsteadfast.greenstep.service.ISysCtxBeanService;
import com.netsteadfast.greenstep.service.ISysService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.SysCtxBeanVO;
import com.netsteadfast.greenstep.vo.SysVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemContextBeanManagmentAction")
@Scope
public class SystemContextBeanManagmentAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = 6446285923478673602L;
	private ISysService<SysVO, TbSys, String> sysService;
	private ISysCtxBeanService<SysCtxBeanVO, TbSysCtxBean, String> sysCtxBeanService;
	private Map<String, String> sysMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> typeMap = this.providedSelectZeroDataMap(true);
	private SysCtxBeanVO sysCtxBean = new SysCtxBeanVO();
	private String selectValue = "";
	
	public SystemContextBeanManagmentAction() {
		super();
	}
	
	public ISysService<SysVO, TbSys, String> getSysService() {
		return sysService;
	}

	@Autowired
	@Resource(name="core.service.SysService")	
	@Required	
	public void setSysService(ISysService<SysVO, TbSys, String> sysService) {
		this.sysService = sysService;
	}

	public ISysCtxBeanService<SysCtxBeanVO, TbSysCtxBean, String> getSysCtxBeanService() {
		return sysCtxBeanService;
	}

	@Autowired
	@Resource(name="core.service.SysCtxBeanService")	
	@Required		
	public void setSysCtxBeanService(
			ISysCtxBeanService<SysCtxBeanVO, TbSysCtxBean, String> sysCtxBeanService) {
		this.sysCtxBeanService = sysCtxBeanService;
	}

	private void initData() throws ServiceException, Exception {
		this.sysMap = this.sysService.findSysMap(super.getBasePath(), true);
		this.typeMap = CtxBeanTypes.getTypes(true);
	}
	
	private void loadSysCtxBeanData() throws ServiceException, Exception {
		this.transformFields2ValueObject(sysCtxBean, new String[]{"oid"});
		DefaultResult<SysCtxBeanVO> result = this.sysCtxBeanService.findObjectByOid(sysCtxBean);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		this.sysCtxBean = result.getValue();
		SysVO sys = new SysVO();
		sys.setSysId(this.sysCtxBean.getSystem());
		DefaultResult<SysVO> sResult = this.sysService.findByUK(sys);
		if (sResult.getValue()!=null) {
			this.selectValue = sResult.getValue().getOid();
		}
	}
	
	/**
	 * core.systemContextBeanManagmentAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0009Q")
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
	 * core.systemContextBeanCreateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0009A")	
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
	 * core.systemContextBeanEditAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0009E")	
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadSysCtxBeanData();
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

	public Map<String, String> getSysMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.sysMap);
		return sysMap;
	}

	public Map<String, String> getTypeMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.typeMap);
		return typeMap;
	}

	public SysCtxBeanVO getSysCtxBean() {
		return sysCtxBean;
	}

	public String getSelectValue() {
		return selectValue;
	}

}
