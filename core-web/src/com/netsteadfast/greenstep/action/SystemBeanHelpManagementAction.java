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
import com.netsteadfast.greenstep.model.ScriptExpressionRunType;
import com.netsteadfast.greenstep.po.hbm.TbSys;
import com.netsteadfast.greenstep.po.hbm.TbSysBeanHelp;
import com.netsteadfast.greenstep.po.hbm.TbSysBeanHelpExpr;
import com.netsteadfast.greenstep.po.hbm.TbSysExpression;
import com.netsteadfast.greenstep.service.ISysBeanHelpExprService;
import com.netsteadfast.greenstep.service.ISysBeanHelpService;
import com.netsteadfast.greenstep.service.ISysExpressionService;
import com.netsteadfast.greenstep.service.ISysService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.SysBeanHelpExprVO;
import com.netsteadfast.greenstep.vo.SysBeanHelpVO;
import com.netsteadfast.greenstep.vo.SysExpressionVO;
import com.netsteadfast.greenstep.vo.SysVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemBeanHelpManagementAction")
@Scope
public class SystemBeanHelpManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -9028589319514146807L;
	private ISysService<SysVO, TbSys, String> sysService;
	private ISysBeanHelpService<SysBeanHelpVO, TbSysBeanHelp, String> sysBeanHelpService;
	private ISysBeanHelpExprService<SysBeanHelpExprVO, TbSysBeanHelpExpr, String> sysBeanHelpExprService;
	private ISysExpressionService<SysExpressionVO, TbSysExpression, String> sysExpressionService; 
	private Map<String, String> sysMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> expressionMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> runTypeMap = this.providedSelectZeroDataMap(true);
	private SysBeanHelpVO sysBeanHelp = new SysBeanHelpVO(); // edit模式用
	private String selectValue = ""; // edit模式用 , 放 TB_SYS.OID
	private SysBeanHelpExprVO sysBeanHelpExpr = new SysBeanHelpExprVO(); // edit expression mapper 模式用
	
	public SystemBeanHelpManagementAction() {
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

	public ISysBeanHelpService<SysBeanHelpVO, TbSysBeanHelp, String> getSysBeanHelpService() {
		return sysBeanHelpService;
	}

	@Autowired
	@Resource(name="core.service.SysBeanHelpService")
	@Required			
	public void setSysBeanHelpService(
			ISysBeanHelpService<SysBeanHelpVO, TbSysBeanHelp, String> sysBeanHelpService) {
		this.sysBeanHelpService = sysBeanHelpService;
	}

	public ISysBeanHelpExprService<SysBeanHelpExprVO, TbSysBeanHelpExpr, String> getSysBeanHelpExprService() {
		return sysBeanHelpExprService;
	}

	@Autowired
	@Resource(name="core.service.SysBeanHelpExprService")
	@Required			
	public void setSysBeanHelpExprService(
			ISysBeanHelpExprService<SysBeanHelpExprVO, TbSysBeanHelpExpr, String> sysBeanHelpExprService) {
		this.sysBeanHelpExprService = sysBeanHelpExprService;
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

	private void initData(String id) throws ServiceException, Exception {
		if (id.endsWith("_S01")) {
			// nothing...
		} else if (id.endsWith("_S00")) {
			this.expressionMap = this.sysExpressionService.findExpressionMap(true);
			this.runTypeMap = ScriptExpressionRunType.getRunTypeMap(true);
		} else {
			this.sysMap = this.sysService.findSysMap(super.getBasePath(), true);
		}		
	}
	
	private void loadSysBeanHelpData() throws ServiceException, Exception {
		this.transformFields2ValueObject(this.sysBeanHelp, new String[]{"oid"});
		DefaultResult<SysBeanHelpVO> result = this.sysBeanHelpService.findObjectByOid(sysBeanHelp);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		this.sysBeanHelp = result.getValue();
		SysVO sys = new SysVO();
		sys.setSysId(this.sysBeanHelp.getSystem());
		DefaultResult<SysVO> sResult = this.sysService.findByUK(sys);
		if (sResult.getValue()!=null) {
			this.selectValue = sResult.getValue().getOid();
		}		
	}
	
	private void loadSysBeanHelpExprData() throws ServiceException, Exception {
		this.transformFields2ValueObject(this.sysBeanHelpExpr, new String[]{"oid"});
		DefaultResult<SysBeanHelpExprVO> result = this.sysBeanHelpExprService.findObjectByOid(this.sysBeanHelpExpr);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		this.sysBeanHelpExpr = result.getValue();		
	}
	
	/**
	 * core.systemBeanHelpManagementAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0003Q")
	public String execute() throws Exception {
		try {
			this.initData("CORE_PROG003D0003Q");
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
	 * core.systemBeanHelpCreateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0003A")
	public String create() throws Exception {
		try {
			this.initData("CORE_PROG003D0003A");
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
	 * core.systemBeanHelpEditAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0003E")
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData("CORE_PROG003D0003E");
			this.loadSysBeanHelpData();
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
	 * core.systemBeanHelpEditExpressionAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0003E_S00")
	public String editExpression() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData("CORE_PROG003D0003E_S00");
			this.loadSysBeanHelpData();
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
	 * core.systemBeanHelpEditExpressionMapperAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0003E_S01")
	public String editExpressionMapper() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData("CORE_PROG003D0003E_S01");
			this.loadSysBeanHelpExprData();
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

	public Map<String, String> getSysMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.sysMap);
		return sysMap;
	}

	public SysBeanHelpVO getSysBeanHelp() {
		return sysBeanHelp;
	}

	public String getSelectValue() {
		return selectValue;
	}

	public Map<String, String> getExpressionMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.expressionMap);
		return expressionMap;
	}

	public Map<String, String> getRunTypeMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.runTypeMap);
		return runTypeMap;
	}

	public SysBeanHelpExprVO getSysBeanHelpExpr() {
		return sysBeanHelpExpr;
	}

}
