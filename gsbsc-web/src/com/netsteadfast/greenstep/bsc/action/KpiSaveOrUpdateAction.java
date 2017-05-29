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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.netsteadfast.greenstep.po.hbm.TbSysExpression;
import com.netsteadfast.greenstep.service.ISysExpressionService;
import com.netsteadfast.greenstep.util.ScriptExpressionUtils;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.SysExpressionVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.KpiSaveOrUpdateAction")
@Scope
public class KpiSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = 3591579231127212607L;
	protected Logger logger=Logger.getLogger(KpiSaveOrUpdateAction.class);
	private IKpiLogicService kpiLogicService; 
	private ISysExpressionService<SysExpressionVO, TbSysExpression, String> sysExpressionService;
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
	
	@JSON(serialize=false)
	public ISysExpressionService<SysExpressionVO, TbSysExpression, String> getSysExpressionService() {
		return sysExpressionService;
	}

	@Autowired
	@Resource(name="core.service.SysExpressionService")		
	public void setSysExpressionService(
			ISysExpressionService<SysExpressionVO, TbSysExpression, String> sysExpressionService) {
		this.sysExpressionService = sysExpressionService;
	}
	
	private void checkFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("visionOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG002D0004A_visionOid") )
		.add("perspectiveOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG002D0004A_perspectiveOid") )
		.add("objectiveOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG002D0004A_objectiveOid") )
		.add("id", IdFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG002D0004A_id") )
		.add("name", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG002D0004A_name") )
		.add("formulaOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG002D0004A_formulaOid") )
		.add("trendsFormulaOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG002D0004A_trendsFormulaOid") )
		.add("weight", BscNumberFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG002D0004A_weight") )
		.add("max", BscNumberFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG002D0004A_max") )
		.add("target", BscNumberFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG002D0004A_target") )
		.add("min", BscNumberFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG002D0004A_min") )
		.add("compareType", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG002D0004A_compareType") )
		.add("unit", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG002D0004A_unit") )
		.add("management", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG002D0004A_management") )
		.add("cal", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG002D0004A_cal") )
		.add("dataType", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG002D0004A_dataType") )
		.process().throwMessage();
		
		String dataType = this.getFields().get("dataType");
		this.getCheckFieldHandler()
		.single("dataType|orgaOids", ( BscKpiCode.DATA_TYPE_DEPARTMENT.equals(dataType) && StringUtils.isBlank(this.getFields().get("orgaOids")) ), this.getText("MESSAGE.BSC_PROG002D0004A_dataType_msg1") )
		.single("dataType|emplOids", ( BscKpiCode.DATA_TYPE_PERSONAL.equals(dataType) && StringUtils.isBlank(this.getFields().get("emplOids")) ), this.getText("MESSAGE.BSC_PROG002D0004A_dataType_msg2") )
		.single("dataType|orgaOids|emplOids", ( BscKpiCode.DATA_TYPE_BOTH.equals(dataType) && ( StringUtils.isBlank(this.getFields().get("orgaOids")) || StringUtils.isBlank(this.getFields().get("emplOids")) ) ), this.getText("MESSAGE.BSC_PROG002D0004A_dataType_msg3") )
		.throwMessage();
	}	
	
	private void checkMaxTargetMinCriteria(KpiVO kpi) throws ServiceException, ControllerException, Exception {	
		/*
		 * 不一定是固定MAX>TARGET>MIN, 所以移到表達式中處理, 讓以後比較能客製化
		 * 
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
		*/
		SysExpressionVO expressionObj = new SysExpressionVO();
		expressionObj.setExprId( "BSC_KPI_EXPR0003" );
		DefaultResult<SysExpressionVO> exprResult = this.sysExpressionService.findByUK(expressionObj); 
		if (exprResult.getValue()==null) {
			throw new ServiceException(exprResult.getSystemMessage().getValue());
		}
		expressionObj = exprResult.getValue();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("kpi", kpi);
		paramMap.put("fieldsId", this.getFieldsId());
		paramMap.put("fields", this.getFields());
		paramMap.put("fieldsMessage", this.getFieldsMessage());
		paramMap.put("msg1", this.getText("MESSAGE.BSC_PROG002D0004A_maxTargetMinCriteria_msg1"));
		paramMap.put("msg2", this.getText("MESSAGE.BSC_PROG002D0004A_maxTargetMinCriteria_msg2"));
		ScriptExpressionUtils.execute(expressionObj.getType(), expressionObj.getContent(), null, paramMap);		
	}	
	
	@SuppressWarnings("unchecked")
	private List<String> getUploadOids() throws Exception {
		String uploadOidsJsonStr = super.defaultString( this.getFields().get("uploadOids") ).trim();
		if (StringUtils.isBlank(uploadOidsJsonStr)) {
			uploadOidsJsonStr = "{'oids' : [] }";
		}
		Map<String, List<Map<String, Object>>> jsonData = (Map<String, List<Map<String, Object>>>)
				new ObjectMapper().readValue( uploadOidsJsonStr, LinkedHashMap.class );
		List<String> oids = new ArrayList<String>();
		if (jsonData.get("oids")==null || !(jsonData.get("oids") instanceof List) ) {
			return oids;
		}
		List<Map<String, Object>> uploadDatas = jsonData.get("oids");
		if (uploadDatas.size()<1) {
			return oids;
		}
		for (Map<String, Object> uploadData : uploadDatas) {
			oids.add( String.valueOf(uploadData.get("oid")) );
		}
		return oids;
	}
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		KpiVO kpi = new KpiVO();
		this.transformFields2ValueObject(kpi, new String[]{"id", "name", "weight", "max", "target", "min", 
				"compareType", "unit", "management", "quasiRange", "dataType", "description"});
		kpi.setOrgaMeasureSeparate(YesNo.NO);
		kpi.setUserMeasureSeparate(YesNo.NO);
		kpi.setActivate(YesNo.NO);
		if ("true".equals(this.getFields().get("orgaMeasureSeparate")) ) {
			kpi.setOrgaMeasureSeparate(YesNo.YES);
		}
		if ("true".equals(this.getFields().get("userMeasureSeparate")) ) {
			kpi.setUserMeasureSeparate(YesNo.YES);
		}
		if ("true".equals(this.getFields().get("activate"))) {
			kpi.setActivate(YesNo.YES);
		}
		this.checkMaxTargetMinCriteria(kpi);		
		DefaultResult<KpiVO> result = this.kpiLogicService.create(
				kpi, 
				this.getFields().get("objectiveOid"), 
				this.getFields().get("formulaOid"), 
				this.getFields().get("cal"),
				this.transformAppendIds2List(this.getFields().get("orgaOids")), 
				this.transformAppendIds2List(this.getFields().get("emplOids")),
				this.getFields().get("trendsFormulaOid"),
				this.getUploadOids()); 
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
		kpi.setActivate(YesNo.NO);
		if ("true".equals(this.getFields().get("orgaMeasureSeparate")) ) {
			kpi.setOrgaMeasureSeparate(YesNo.YES);
		}
		if ("true".equals(this.getFields().get("userMeasureSeparate")) ) {
			kpi.setUserMeasureSeparate(YesNo.YES);
		}
		if ("true".equals(this.getFields().get("activate"))) {
			kpi.setActivate(YesNo.YES);
		}
		this.checkMaxTargetMinCriteria(kpi);		
		DefaultResult<KpiVO> result = this.kpiLogicService.update(
				kpi, 
				this.getFields().get("objectiveOid"), 
				this.getFields().get("formulaOid"), 
				this.getFields().get("cal"),
				this.transformAppendIds2List(this.getFields().get("orgaOids")), 
				this.transformAppendIds2List(this.getFields().get("emplOids")),
				this.getFields().get("trendsFormulaOid"),
				this.getUploadOids()); 
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
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
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
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
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
