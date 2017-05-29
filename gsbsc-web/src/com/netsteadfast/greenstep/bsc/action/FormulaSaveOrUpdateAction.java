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

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.bsc.action.utils.IdFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.model.BscMeasureData;
import com.netsteadfast.greenstep.bsc.model.FormulaMode;
import com.netsteadfast.greenstep.bsc.service.logic.IFormulaLogicService;
import com.netsteadfast.greenstep.bsc.util.BscFormulaUtils;
import com.netsteadfast.greenstep.vo.FormulaVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.FormulaSaveOrUpdateAction")
@Scope
public class FormulaSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = -6260791527924990036L;
	protected Logger logger=Logger.getLogger(FormulaSaveOrUpdateAction.class);
	private IFormulaLogicService formulaLogicService;
	private String message = "";
	private String success = IS_NO;
	
	public FormulaSaveOrUpdateAction() {
		super();
	}

	@JSON(serialize=false)
	public IFormulaLogicService getFormulaLogicService() {
		return formulaLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.FormulaLogicService")		
	public void setFormulaLogicService(IFormulaLogicService formulaLogicService) {
		this.formulaLogicService = formulaLogicService;
	}
	
	private void checkFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("forId", IdFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG001D0003A_forId") )
		.add("name", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG001D0003A_name") )
		.add("type", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG001D0003A_type") )
		.add("trendsFlag", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG001D0003A_trendsFlag") )
		.add("returnMode", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG001D0003A_returnMode") )
		.add("expression", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG001D0003A_expression") )
		.process().throwMessage();
		
		this.getCheckFieldHandler()
		.single(
				"expression", 
				( this.getFields().get("expression").length() > 4000 ), 
				this.getText("MESSAGE.BSC_PROG001D0003A_msg1") )
		.single(
				"returnVar", 
				( FormulaMode.MODE_CUSTOM.equals(this.getFields().get("returnMode")) && StringUtils.isBlank(this.getFields().get("returnVar")) ), 
				this.getText("MESSAGE.BSC_PROG001D0003A_msg2") )
		.throwMessage();
	}
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		this.testFormula();
		FormulaVO formula = new FormulaVO();
		this.transformFields2ValueObject(formula, 
				new String[]{"forId", "name", "type", "trendsFlag", "returnMode", "returnVar", "expression", "description"});
		DefaultResult<FormulaVO> result = this.formulaLogicService.create(formula);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		this.testFormula();
		FormulaVO formula = new FormulaVO();
		this.transformFields2ValueObject(formula, 
				new String[]{"oid", "forId", "name", "type", "trendsFlag", "returnMode", "returnVar", "expression", "description"});
		DefaultResult<FormulaVO> result = this.formulaLogicService.update(formula);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}		
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		FormulaVO formula = new FormulaVO();
		this.transformFields2ValueObject(formula, new String[]{"oid"});
		DefaultResult<Boolean> result = this.formulaLogicService.delete(formula);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}			
	}
	
	private Object testFormula() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.getCheckFieldHandler()
		.single("type", ( this.isNoSelectId(this.getFields().get("type")) ), this.getText("MESSAGE.BSC_PROG001D0003A_type") )
		.single("trendsFlag", ( this.isNoSelectId(this.getFields().get("trendsFlag")) ), this.getText("MESSAGE.BSC_PROG001D0003A_trendsFlag") )
		.single("returnMode", ( this.isNoSelectId(this.getFields().get("returnMode")) ), this.getText("MESSAGE.BSC_PROG001D0003A_returnMode") )
		.single("returnVar", ( FormulaMode.MODE_CUSTOM.equals(this.getFields().get("returnMode")) && StringUtils.isBlank(this.getFields().get("returnVar")) ), this.getText("MESSAGE.BSC_PROG001D0003A_msg2") )
		.single("expression", ( StringUtils.isBlank(this.getFields().get("expression")) ), this.getText("MESSAGE.BSC_PROG001D0003A_expression") )
		.throwMessage();
		
		String actual = this.getFields().get("actual");
		String target = this.getFields().get("target");
		String cv = this.getFields().get("cv");
		String pv = this.getFields().get("pv");
		if (!NumberUtils.isNumber(actual)) {
			actual = "60.0";
		}
		if (!NumberUtils.isNumber(target)) {
			target = "100.0";
		}		
		if (!NumberUtils.isNumber(cv)) {
			cv = "70.0";
		}
		if (!NumberUtils.isNumber(pv)) {
			pv = "55.0";
		}
		FormulaVO formula = new FormulaVO();
		this.transformFields2ValueObject(formula, new String[]{"type", "trendsFlag", "returnMode", "returnVar", "expression"});		
		if (YesNo.YES.equals(formula.getTrendsFlag())) {
			return BscFormulaUtils.parseKPIPeroidScoreChangeValue(formula, Float.parseFloat(cv), Float.parseFloat(pv));
		}
		BscMeasureData data = new BscMeasureData();
		data.setActual( Float.parseFloat(actual) );
		data.setTarget( Float.parseFloat(target) );
		return BscFormulaUtils.parse(formula, data);
	}
	
	/**
	 * bsc.formulaSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0003A")
	public String doSave() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.save();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}	
	
	/**
	 * bsc.formulaUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0003E")
	public String doUpdate() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.update();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}		
	
	/**
	 * bsc.formulaDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0003Q")
	public String doDelete() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.delete();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}	
	
	/**
	 * bsc.formulaTestAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0003Q")
	public String doTestFormula() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			Object result = this.testFormula();
			this.message = String.valueOf(result);
			this.success = IS_YES;
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
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
}
