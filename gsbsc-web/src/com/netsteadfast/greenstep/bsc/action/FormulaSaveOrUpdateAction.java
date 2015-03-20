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
	
	@SuppressWarnings("unchecked")
	private void checkFields() throws ControllerException {
		try {	
			super.checkFields(
					new String[]{
							"forId",
							"name",
							"type",
							"returnMode",
							"expression"
					}, 
					new String[]{
							"Id is required and only normal characters!<BR/>",
							"name is required!<BR/>",
							"Please select type!<BR/>",
							"Please select return mode!<BR/>",
							"expression is required!<BR/>"
					}, 
					new Class[]{
							IdFieldCheckUtils.class,
							NotBlankFieldCheckUtils.class,
							SelectItemFieldCheckUtils.class,
							SelectItemFieldCheckUtils.class,
							NotBlankFieldCheckUtils.class
					},
					this.getFieldsId() );			
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new ControllerException(e.getMessage().toString());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new ControllerException(e.getMessage().toString());
		}			
		if (this.getFields().get("expression").length() > 500 ) {
			this.getFieldsId().add("expression");
			throw new ControllerException("expression maximum of 500 characters!<BR/>");			
		}
		if (FormulaMode.MODE_CUSTOM.equals(this.getFields().get("returnMode")) 
				&& StringUtils.isBlank(this.getFields().get("returnVar")) ) {
			this.getFieldsId().add("returnVar");
			throw new ControllerException("When return-mode is custom return-variable is required!<BR/>");
		}
	}			
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		this.testFormula();
		FormulaVO formula = new FormulaVO();
		this.transformFields2ValueObject(formula, 
				new String[]{"forId", "name", "type", "returnMode", "returnVar", "expression", "description"});
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
				new String[]{"oid", "forId", "name", "type", "returnMode", "returnVar", "expression", "description"});
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
		if (this.isNoSelectId(this.getFields().get("type")) ) {
			this.getFieldsId().add("type");
			throw new ControllerException("Please select type!<BR/>");
		}
		if (this.isNoSelectId(this.getFields().get("returnMode")) ) {
			this.getFieldsId().add("returnMode");
			throw new ControllerException("Please select return mode!<BR/>");
		}
		if (FormulaMode.MODE_CUSTOM.equals(this.getFields().get("returnMode")) 
				&& StringUtils.isBlank(this.getFields().get("returnVar")) ) {
			this.getFieldsId().add("returnVar");
			throw new ControllerException("When return-mode is custom return-variable is required!<BR/>");
		}		
		if (StringUtils.isBlank(this.getFields().get("expression")) ) {
			this.getFieldsId().add("expression");
			throw new ControllerException("expression is required!<BR/>");			
		}
		String actual = this.getFields().get("actual");
		String target = this.getFields().get("target");
		if (!NumberUtils.isNumber(actual)) {
			actual = "60.0";
		}
		if (!NumberUtils.isNumber(target)) {
			target = "100.0";
		}		
		FormulaVO formula = new FormulaVO();
		this.transformFields2ValueObject(formula, new String[]{"type", "returnMode", "returnVar", "expression"});		
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
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			if (e.getMessage()==null) { // for jpython throws exception
				this.message=e.toString();
				this.logger.error(e.toString());
			} else {
				this.message=e.getMessage().toString();
				this.logger.error(e.getMessage());
			}						
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
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			if (e.getMessage()==null) { // for jpython throws exception
				this.message=e.toString();
				this.logger.error(e.toString());
			} else {
				this.message=e.getMessage().toString();
				this.logger.error(e.getMessage());
			}						
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
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
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
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			if (e.getMessage()==null) { // for jpython throws exception
				this.message=e.toString();
				this.logger.error(e.toString());
			} else {
				this.message=e.getMessage().toString();
				this.logger.error(e.getMessage());
			}						
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

}
