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
import com.netsteadfast.greenstep.bsc.service.logic.IAggregationMethodLogicService;
import com.netsteadfast.greenstep.vo.AggregationMethodVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.AggregationMethodSaveOrUpdateAction")
@Scope
public class AggregationMethodSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = 7389191166697805402L;
	protected Logger logger=Logger.getLogger(AggregationMethodSaveOrUpdateAction.class);
	private IAggregationMethodLogicService aggregationMethodLogicService;
	private String message = "";
	private String success = IS_NO;
	
	public AggregationMethodSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public IAggregationMethodLogicService getAggregationMethodLogicService() {
		return aggregationMethodLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.AggregationMethodLogicService")			
	public void setAggregationMethodLogicService(
			IAggregationMethodLogicService aggregationMethodLogicService) {
		this.aggregationMethodLogicService = aggregationMethodLogicService;
	}

	@SuppressWarnings("unchecked")
	private void checkFields() throws ControllerException {
		try {	
			super.checkFields(
					new String[]{
							"aggrId",
							"name",
							"type",
							"expression1",
							"expression2"
					}, 
					new String[]{
							this.getText("MESSAGE.BSC_PROG001D0008A_aggrId") + "<BR/>",
							this.getText("MESSAGE.BSC_PROG001D0008A_name") + "<BR/>",
							this.getText("MESSAGE.BSC_PROG001D0008A_type") + "<BR/>",
							this.getText("MESSAGE.BSC_PROG001D0008A_iframe1") + "<BR/>",
							this.getText("MESSAGE.BSC_PROG001D0008A_iframe2") + "<BR/>"
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
		if (this.getFields().get("expression1").length() > 4000 ) {
			this.getFieldsId().add("expression1");
			throw new ControllerException( this.getText("MESSAGE.BSC_PROG001D0008A_iframe1_msg1") + "<BR/>" );			
		}
		if (this.getFields().get("expression2").length() > 4000 ) {
			this.getFieldsId().add("expression2");
			throw new ControllerException( this.getText("MESSAGE.BSC_PROG001D0008A_iframe2_msg1") + "<BR/>" );			
		}		
	}
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		AggregationMethodVO aggr = new AggregationMethodVO();
		this.transformFields2ValueObject(
				aggr, new String[]{"aggrId", "name", "type", "expression1", "expression2", "description"});
		DefaultResult<AggregationMethodVO> result = this.aggregationMethodLogicService.create(aggr);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}		
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		AggregationMethodVO aggr = new AggregationMethodVO();
		this.transformFields2ValueObject(
				aggr, new String[]{"oid", "aggrId", "name", "type", "expression1", "expression2", "description"});
		DefaultResult<AggregationMethodVO> result = this.aggregationMethodLogicService.update(aggr);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}			
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		AggregationMethodVO aggr = new AggregationMethodVO();
		this.transformFields2ValueObject(aggr, new String[]{"oid"});
		DefaultResult<Boolean> result = this.aggregationMethodLogicService.delete(aggr);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}	
	}
	
	/**
	 * bsc.aggregationMethodSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0008A")
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
	 * bsc.aggregationMethodUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0008E")
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
	 * bsc.aggregationMethodDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0008Q")
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
