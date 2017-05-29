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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.bsc.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.model.BscReportCode;
import com.netsteadfast.greenstep.po.hbm.TbSysCode;
import com.netsteadfast.greenstep.service.ISysCodeService;
import com.netsteadfast.greenstep.vo.SysCodeVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.ReportPropertySaveOrUpdateAction")
@Scope
public class ReportPropertySaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = -3582283067632046161L;
	protected Logger logger=Logger.getLogger(ReportPropertySaveOrUpdateAction.class);
	private ISysCodeService<SysCodeVO, TbSysCode, String> sysCodeService;
	private String message = "";
	private String success = IS_NO;
	
	public ReportPropertySaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public ISysCodeService<SysCodeVO, TbSysCode, String> getSysCodeService() {
		return sysCodeService;
	}

	@Autowired
	@Resource(name="core.service.SysCodeService")		
	public void setSysCodeService(
			ISysCodeService<SysCodeVO, TbSysCode, String> sysCodeService) {
		this.sysCodeService = sysCodeService;
	}
	
	private void checkFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("backgroundColor", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG004D0001Q_backgroundColor") )
		.add("fontColor", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG004D0001Q_fontColor") )
		.add("perspectiveTitle", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG004D0001Q_perspectiveTitle") )
		.add("objectiveTitle", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG004D0001Q_objectiveTitle") )
		.add("kpiTitle", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG004D0001Q_kpiTitle") )
		.add("classNote", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG004D0001Q_classNote") )
		.add("scoreLabel", NotBlankFieldCheckUtils.class, "Score label is required!" )
		.add("weightLabel", NotBlankFieldCheckUtils.class, "Weight label is required!" )
		.add("maxLabel", NotBlankFieldCheckUtils.class, "Max label is required!" )
		.add("targetLabel", NotBlankFieldCheckUtils.class, "Target label is required!" )
		.add("minLabel", NotBlankFieldCheckUtils.class, "Min label is required!" )
		.add("managementLabel", NotBlankFieldCheckUtils.class, "Management label is required!" )
		.add("calculationLabel", NotBlankFieldCheckUtils.class, "Calculation label is required!" )
		.add("unitLabel", NotBlankFieldCheckUtils.class, "Unit label is required!" )
		.add("formulaLabel", NotBlankFieldCheckUtils.class, "Formula label is required!" )
		.add("organizationLabel", NotBlankFieldCheckUtils.class, "Organization label is required!" )
		.add("employeeLabel", NotBlankFieldCheckUtils.class, "Employee label is required!" )
		.process().throwMessage();
		
		String classNote = this.getFields().get("classNote");
		this.getCheckFieldHandler().single("classNote", (classNote.length()>100), this.getText("MESSAGE.BSC_PROG004D0001Q_classNote_msg1") ).throwMessage();
	}		
	
	private void updateSysCode(String code, String value) throws ServiceException, Exception {
		TbSysCode sysCode = new TbSysCode();
		sysCode.setCode(code);
		sysCode.setType(BscReportCode.CODE_TYPE);
		sysCode = this.sysCodeService.findByEntityUK(sysCode);
		if ( null == sysCode || StringUtils.isBlank(sysCode.getOid()) ) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS));
		}
		sysCode.setParam1(value);
		this.sysCodeService.update(sysCode);
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		this.updateSysCode(BscReportCode.BACKGROUND_COLOR, this.getFields().get("backgroundColor") );
		this.updateSysCode(BscReportCode.FONT_COLOR, this.getFields().get("fontColor") );
		this.updateSysCode(BscReportCode.PERSPECTIVE_TITLE, this.getFields().get("perspectiveTitle") );
		this.updateSysCode(BscReportCode.OBJECTIVE_TITLE, this.getFields().get("objectiveTitle") );
		this.updateSysCode(BscReportCode.KPI_TITLE, this.getFields().get("kpiTitle") );
		this.updateSysCode(BscReportCode.PERSONAL_REPORT_CLASS_LEVEL, this.getFields().get("classNote") );
		this.updateSysCode(BscReportCode.SCORE_LABEL, this.getFields().get("scoreLabel") );
		this.updateSysCode(BscReportCode.WEIGHT_LABEL, this.getFields().get("weightLabel") );
		this.updateSysCode(BscReportCode.MAX_LABEL, this.getFields().get("maxLabel") );
		this.updateSysCode(BscReportCode.TARGET_LABEL, this.getFields().get("targetLabel") );
		this.updateSysCode(BscReportCode.MIN_LABEL, this.getFields().get("minLabel") );
		this.updateSysCode(BscReportCode.MANAGEMENT_LABEL, this.getFields().get("managementLabel") );
		this.updateSysCode(BscReportCode.CALCULATION_LABEL, this.getFields().get("calculationLabel") );
		this.updateSysCode(BscReportCode.UNIT_LABEL, this.getFields().get("unitLabel") );
		this.updateSysCode(BscReportCode.FORMULA_LABEL, this.getFields().get("formulaLabel") );
		this.updateSysCode(BscReportCode.ORGANIZATION_LABEL, this.getFields().get("organizationLabel") );
		this.updateSysCode(BscReportCode.EMPLOYEE_LABEL, this.getFields().get("employeeLabel") );
		this.message = SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_SUCCESS);
		this.success = IS_YES;
	}	
	
	/**
	 * bsc.reportPropertyUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG004D0001Q")
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
