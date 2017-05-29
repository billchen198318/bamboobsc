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
	
	private void checkFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("aggrId", IdFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG001D0008A_aggrId") )
		.add("name", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG001D0008A_name") )
		.add("type", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG001D0008A_type") )
		.add("expression1", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG001D0008A_iframe1") )
		.add("expression2", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG001D0008A_iframe2") )
		.process().throwMessage();
		
		this.getCheckFieldHandler()
		.single("expression1", ( this.getFields().get("expression1").length() > 4000 ), this.getText("MESSAGE.BSC_PROG001D0008A_iframe1_msg1") )
		.single("expression2", ( this.getFields().get("expression2").length() > 4000 ), this.getText("MESSAGE.BSC_PROG001D0008A_iframe2_msg1") )
		.throwMessage();
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
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
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
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
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
