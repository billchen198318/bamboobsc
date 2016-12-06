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
import com.netsteadfast.greenstep.base.model.ScriptTypeCode;
import com.netsteadfast.greenstep.bsc.model.FormulaMode;
import com.netsteadfast.greenstep.bsc.service.IFormulaService;
import com.netsteadfast.greenstep.bsc.util.BscFormulaUtils;
import com.netsteadfast.greenstep.po.hbm.BbFormula;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.FormulaVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.FormulaManagementAction")
@Scope
public class FormulaManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -1489660824481165752L;
	private IFormulaService<FormulaVO, BbFormula, String> formulaService; 
	private Map<String, String> typeMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> modeMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> trendsFlagMap = this.providedSelectZeroDataMap(true);
	private FormulaVO formula = new FormulaVO();
	
	public FormulaManagementAction() {
		super();
	}

	public IFormulaService<FormulaVO, BbFormula, String> getFormulaService() {
		return formulaService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.FormulaService")		
	public void setFormulaService(
			IFormulaService<FormulaVO, BbFormula, String> formulaService) {
		this.formulaService = formulaService;
	}
	
	private void initData() throws ServiceException, Exception {
		this.typeMap = ScriptTypeCode.getTypeMap(true);
		this.modeMap = FormulaMode.getReturnModeMap(true);
		this.trendsFlagMap = BscFormulaUtils.getTrendsFlagMap(true);
	}
	
	private void loadFormulaData() throws ServiceException, Exception {
		this.transformFields2ValueObject(this.formula, new String[]{"oid"});
		DefaultResult<FormulaVO> result = this.formulaService.findObjectByOid(formula);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		this.formula = result.getValue();
	}
	
	/**
	 * bsc.formulaManagementAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0003Q")
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
	 * bsc.formulaCreateAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0003A")
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
	 * bsc.formulaEditAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0003E")
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadFormulaData();
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

	public Map<String, String> getTypeMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.typeMap);
		return typeMap;
	}

	public Map<String, String> getModeMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.modeMap);
		return modeMap;
	}

	public Map<String, String> getTrendsFlagMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.trendsFlagMap);
		return trendsFlagMap;
	}

	public FormulaVO getFormula() {
		return formula;
	}

}
