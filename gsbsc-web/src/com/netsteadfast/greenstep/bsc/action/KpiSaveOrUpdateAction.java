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
import com.netsteadfast.greenstep.bsc.action.utils.BscNumberFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.action.utils.IdFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.model.BscKpiCode;
import com.netsteadfast.greenstep.bsc.service.logic.IKpiLogicService;
import com.netsteadfast.greenstep.vo.KpiVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.KpiSaveOrUpdateAction")
@Scope
public class KpiSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = 3591579231127212607L;
	protected Logger logger=Logger.getLogger(KpiSaveOrUpdateAction.class);
	private IKpiLogicService kpiLogicService; 
	private String message = "";
	private String success = IS_NO;
	
	public KpiSaveOrUpdateAction() {
		super();
	}

	@JSON(serialize=false)
	public IKpiLogicService getKpiLogicService() {
		return kpiLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.KpiLogicService")		
	public void setKpiLogicService(IKpiLogicService kpiLogicService) {
		this.kpiLogicService = kpiLogicService;
	}
	
	@SuppressWarnings("unchecked")
	private void checkFields() throws ControllerException {
		try {
			super.checkFields(
					new String[]{
							"visionOid",
							"perspectiveOid",
							"objectiveOid",
							"id",
							"name",
							"formulaOid",
							"weight",
							"max",
							"target",
							"min",
							"compareType",
							"unit",
							"management",
							"cal",
							"dataType"
					}, 
					new String[]{
							this.getText("MESSAGE.BSC_PROG002D0004A_visionOid") + "<BR/>",
							this.getText("MESSAGE.BSC_PROG002D0004A_perspectiveOid") + "<BR/>",
							this.getText("MESSAGE.BSC_PROG002D0004A_objectiveOid") + "<BR/>",
							this.getText("MESSAGE.BSC_PROG002D0004A_id") + "<BR/>",
							this.getText("MESSAGE.BSC_PROG002D0004A_name") + "<BR/>",
							this.getText("MESSAGE.BSC_PROG002D0004A_formulaOid") + "<BR/>",
							this.getText("MESSAGE.BSC_PROG002D0004A_weight") + "<BR/>",
							this.getText("MESSAGE.BSC_PROG002D0004A_max") + "<BR/>",
							this.getText("MESSAGE.BSC_PROG002D0004A_target") + "<BR/>",
							this.getText("MESSAGE.BSC_PROG002D0004A_min") + "<BR/>",
							this.getText("MESSAGE.BSC_PROG002D0004A_compareType") + "<BR/>",
							this.getText("MESSAGE.BSC_PROG002D0004A_unit") + "<BR/>",
							this.getText("MESSAGE.BSC_PROG002D0004A_management") + "<BR/>",
							this.getText("MESSAGE.BSC_PROG002D0004A_cal") + "<BR/>",
							this.getText("MESSAGE.BSC_PROG002D0004A_dataType") + "<BR/>"
					}, 
					new Class[]{
							SelectItemFieldCheckUtils.class,
							SelectItemFieldCheckUtils.class,
							SelectItemFieldCheckUtils.class,
							IdFieldCheckUtils.class,
							NotBlankFieldCheckUtils.class,
							SelectItemFieldCheckUtils.class,
							BscNumberFieldCheckUtils.class,
							BscNumberFieldCheckUtils.class,
							BscNumberFieldCheckUtils.class,
							BscNumberFieldCheckUtils.class,
							SelectItemFieldCheckUtils.class,
							NotBlankFieldCheckUtils.class,
							SelectItemFieldCheckUtils.class,
							SelectItemFieldCheckUtils.class,
							SelectItemFieldCheckUtils.class
					},
					this.getFieldsId() );			
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new ControllerException(e.getMessage().toString());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new ControllerException(e.getMessage().toString());
		}	
		String dataType = this.getFields().get("dataType");
		if (BscKpiCode.DATA_TYPE_DEPARTMENT.equals(dataType) && StringUtils.isBlank(this.getFields().get("orgaOids")) ) {
			this.getFieldsId().add("dataType");
			throw new ControllerException(this.getText("MESSAGE.BSC_PROG002D0004A_dataType_msg1") + "<BR/>");
		}
		if (BscKpiCode.DATA_TYPE_PERSONAL.equals(dataType) && StringUtils.isBlank(this.getFields().get("emplOids")) ) {
			this.getFieldsId().add("dataType");
			throw new ControllerException(this.getText("MESSAGE.BSC_PROG002D0004A_dataType_msg2") + "<BR/>");
		}
		if (BscKpiCode.DATA_TYPE_BOTH.equals(dataType) 
				&& ( StringUtils.isBlank(this.getFields().get("orgaOids")) || StringUtils.isBlank(this.getFields().get("emplOids")) ) ) {
			this.getFieldsId().add("dataType");
			throw new ControllerException(this.getText("MESSAGE.BSC_PROG002D0004A_dataType_msg3") + "<BR/>");			
		}
	}	
	
	private void checkMaxTargetMinCriteria(KpiVO kpi) throws ControllerException, Exception {		
		if (kpi.getMax() <= kpi.getTarget()) {
			this.getFieldsId().add("max");
			this.getFieldsId().add("target");
			throw new ControllerException( this.getText("MESSAGE.BSC_PROG002D0004A_maxTargetMinCriteria_msg1") );			
		}
		if (kpi.getTarget() <= kpi.getMin()) {
			this.getFieldsId().add("target");
			this.getFieldsId().add("min");			
			throw new ControllerException( this.getText("MESSAGE.BSC_PROG002D0004A_maxTargetMinCriteria_msg2") );
		}
	}	
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		KpiVO kpi = new KpiVO();
		this.transformFields2ValueObject(kpi, new String[]{"id", "name", "weight", "max", "target", "min", 
				"compareType", "unit", "management", "quasiRange", "dataType", "description"});
		kpi.setOrgaMeasureSeparate(YesNo.NO);
		kpi.setUserMeasureSeparate(YesNo.NO);
		if ("true".equals(this.getFields().get("orgaMeasureSeparate")) ) {
			kpi.setOrgaMeasureSeparate(YesNo.YES);
		}
		if ("true".equals(this.getFields().get("userMeasureSeparate")) ) {
			kpi.setUserMeasureSeparate(YesNo.YES);
		}
		this.checkMaxTargetMinCriteria(kpi);		
		DefaultResult<KpiVO> result = this.kpiLogicService.create(
				kpi, 
				this.getFields().get("objectiveOid"), 
				this.getFields().get("formulaOid"), 
				this.getFields().get("cal"),
				this.transformAppendIds2List(this.getFields().get("orgaOids")), 
				this.transformAppendIds2List(this.getFields().get("emplOids"))); 
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		KpiVO kpi = new KpiVO();
		this.transformFields2ValueObject(kpi, new String[]{"oid", "id", "name", "weight", "max", "target", "min", 
				"compareType", "unit", "management", "quasiRange", "dataType", "description"});
		kpi.setOrgaMeasureSeparate(YesNo.NO);
		kpi.setUserMeasureSeparate(YesNo.NO);
		if ("true".equals(this.getFields().get("orgaMeasureSeparate")) ) {
			kpi.setOrgaMeasureSeparate(YesNo.YES);
		}
		if ("true".equals(this.getFields().get("userMeasureSeparate")) ) {
			kpi.setUserMeasureSeparate(YesNo.YES);
		}
		this.checkMaxTargetMinCriteria(kpi);		
		DefaultResult<KpiVO> result = this.kpiLogicService.update(
				kpi, 
				this.getFields().get("objectiveOid"), 
				this.getFields().get("formulaOid"), 
				this.getFields().get("cal"),
				this.transformAppendIds2List(this.getFields().get("orgaOids")), 
				this.transformAppendIds2List(this.getFields().get("emplOids"))); 
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}		
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		KpiVO kpi = new KpiVO();
		this.transformFields2ValueObject(kpi, new String[]{"oid"});		
		DefaultResult<Boolean> result = this.kpiLogicService.delete(kpi);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}			
	}

	/**
	 * bsc.kpiSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0003A")
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
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}	
	
	/**
	 * bsc.kpiUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0003E")
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
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}		

	/**
	 * bsc.kpiDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0004Q")
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
