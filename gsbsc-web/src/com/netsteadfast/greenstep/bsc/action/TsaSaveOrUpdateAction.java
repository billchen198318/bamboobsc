/* 
 * Copyright 2012-2017 bambooCORE, greenstep of copyright Chen Xin Nien
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

import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.bsc.action.utils.BscNumberFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.service.logic.ITsaLogicService;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.TsaMaCoefficientsVO;
import com.netsteadfast.greenstep.vo.TsaMeasureFreqVO;
import com.netsteadfast.greenstep.vo.TsaVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.TsaSaveOrUpdateAction")
@Scope
public class TsaSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = -8168150823165308909L;
	protected Logger logger = Logger.getLogger(TsaSaveOrUpdateAction.class);
	private ITsaLogicService tsaLogicService;
	private String message = "";
	private String success = IS_NO;
	
	public TsaSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public ITsaLogicService getTsaLogicService() {
		return tsaLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.TsaLogicService")	
	public void setTsaLogicService(ITsaLogicService tsaLogicService) {
		this.tsaLogicService = tsaLogicService;
	}
	
	private void checkFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("name", NotBlankFieldCheckUtils.class, "Name is required!")
		.add("integration", BscNumberFieldCheckUtils.class, "Integration field value must be number!")
		.add("forecast", BscNumberFieldCheckUtils.class, "Forecast field value must be number!")
		.add("coefficient1", BscNumberFieldCheckUtils.class, "Coefficient (1) field value must be number!")
		.add("coefficient2", BscNumberFieldCheckUtils.class, "Coefficient (2) field value must be number!")
		.add("coefficient3", BscNumberFieldCheckUtils.class, "Coefficient (3) field value must be number!")
		.process().throwMessage();
		
		this.checkMeasureFrequency();
	}
	
	private void checkMeasureFrequency() throws ControllerException {
		String frequency = this.getFields().get("measureFreq_frequency");
		String startDate = this.getFields().get("measureFreq_startDate");
		String endDate = this.getFields().get("measureFreq_endDate");
		String startYearDate = this.getFields().get("measureFreq_startYearDate");
		String endYearDate = this.getFields().get("measureFreq_endYearDate");
		if ( BscMeasureDataFrequency.FREQUENCY_DAY.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_MONTH.equals(frequency) ) {
			if ( StringUtils.isBlank( startDate ) || StringUtils.isBlank( endDate ) ) {
				super.throwMessage("measureFreq_startDate|measureFreq_endDate", "Measure settings, Start-date and end-date is required!");		
			}
		}
		if ( BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_YEAR.equals(frequency) ) {
			if ( StringUtils.isBlank( startYearDate ) || StringUtils.isBlank( endYearDate ) ) {
				super.throwMessage("measureFreq_startYearDate|measureFreq_endYearDate", "Measure settings, Start-year and end-year is required!");			
			}			
		}		
		if ( !StringUtils.isBlank( startDate ) || !StringUtils.isBlank( endDate ) ) {
			if ( !SimpleUtils.isDate( startDate ) ) {
				super.throwMessage("measureFreq_startDate", "Measure settings, Start-date format is incorrect!");
			}
			if ( !SimpleUtils.isDate( endDate ) ) {
				super.throwMessage("measureFreq_endDate", "Measure settings, End-date format is incorrect!");		
			}
			if ( Integer.parseInt( endDate.replaceAll("/", "").replaceAll("-", "") )
					< Integer.parseInt( startDate.replaceAll("/", "").replaceAll("-", "") ) ) {
				super.throwMessage("measureFreq_startDate|measureFreq_endDate", "Measure settings, Start-date / end-date incorrect!");
			}			
		}
		if ( !StringUtils.isBlank( startYearDate ) && !StringUtils.isBlank( endYearDate ) ) {
			if ( !SimpleUtils.isDate( startYearDate+"/01/01" ) ) {
				super.throwMessage("measureFreq_startYearDate", "Measure settings, Start-year format is incorrect!");			
			}
			if ( !SimpleUtils.isDate( endYearDate+"/01/01" ) ) {
				super.throwMessage("measureFreq_endYearDate", "Measure settings, End-year format is incorrect!");				
			}
			if ( Integer.parseInt( endYearDate.replaceAll("/", "").replaceAll("-", "") )
					< Integer.parseInt( startYearDate.replaceAll("/", "").replaceAll("-", "") ) ) {
				super.throwMessage("measureFreq_startYearDate|measureFreq_endYearDate", "Measure settings, Start-year / end-year incorrect!");	
			}					
		}
		String dataFor = this.getFields().get("measureFreq_dataFor");
		if ("organization".equals(dataFor) 
				&& this.isNoSelectId(this.getFields().get("measureFreq_measureDataOrganizationOid")) ) {
			super.throwMessage("measureFreq_measureDataOrganizationOid", "Measure settings, Please select measure-data organization!");
		}
		if ("employee".equals(dataFor)
				&& this.isNoSelectId(this.getFields().get("measureFreq_measureDataEmployeeOid")) ) {
			super.throwMessage("measureFreq_measureDataEmployeeOid", "Measure settings, Please select measure-data employee!");
		}			
	}

	private void saveOrUpdate(String type) throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		TsaVO tsa = new TsaVO();
		this.transformFields2ValueObject(
				tsa, 
				new String[]{"name", "integrationOrder", "forecastNext", "description"}, 
				new String[]{"name", "integration", "forecast", "description"});
		this.getFields().remove("measureFreq_date1");
		this.getFields().remove("measureFreq_date2");
		this.getFields().remove("measureFreq_dateType");
		String frequency = this.getFields().get("measureFreq_frequency");		
		if (BscMeasureDataFrequency.FREQUENCY_DAY.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_MONTH.equals(frequency) ) {
			this.getFields().put("measureFreq_date1", this.getFields().get("measureFreq_startDate"));
			this.getFields().put("measureFreq_date2", this.getFields().get("measureFreq_endDate"));
		} else {
			this.getFields().put("measureFreq_date1", this.getFields().get("measureFreq_startYearDate"));
			this.getFields().put("measureFreq_date2", this.getFields().get("measureFreq_endYearDate"));
		}
		String dataFor = this.getFields().get("measureFreq_dataFor");
		this.getFields().put("measureFreq_dateType", "3");
		if ("organization".equals(dataFor)) {
			this.getFields().put("measureFreq_dateType", "1");
		}
		if ("employee".equals(dataFor)) {
			this.getFields().put("measureFreq_dateType", "2");
		}
		TsaMeasureFreqVO measureFreq = new TsaMeasureFreqVO();
		this.transformFields2ValueObject(
				measureFreq, 
				new String[]{
						"freq",
						"dataType",
						"organizationOid",
						"employeeOid",
						"startDate",
						"endDate"
				}, 
				new String[]{
						"measureFreq_frequency",
						"measureFreq_dateType",
						"measureFreq_measureDataOrganizationOid",
						"measureFreq_measureDataEmployeeOid",
						"measureFreq_date1",
						"measureFreq_date2"
				});
		TsaMaCoefficientsVO coefficient1 = new TsaMaCoefficientsVO();
		TsaMaCoefficientsVO coefficient2 = new TsaMaCoefficientsVO();
		TsaMaCoefficientsVO coefficient3 = new TsaMaCoefficientsVO();
		this.transformFields2ValueObject(coefficient1, new String[]{"seqValue"}, new String[]{"coefficient1"});
		this.transformFields2ValueObject(coefficient2, new String[]{"seqValue"}, new String[]{"coefficient2"});
		this.transformFields2ValueObject(coefficient3, new String[]{"seqValue"}, new String[]{"coefficient3"});
		
		DefaultResult<TsaVO> result = null;
		if ("save".equals(type)) {
			result = this.tsaLogicService.create(tsa, measureFreq, coefficient1, coefficient2, coefficient3);
		} else {
			tsa.setOid( this.getFields().get("oid") );
			result = this.tsaLogicService.update(tsa, measureFreq, coefficient1, coefficient2, coefficient3);
		}
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
		
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		TsaVO tsa = new TsaVO();
		this.transformFields2ValueObject(tsa, new String[]{"oid"});
		DefaultResult<Boolean> result = this.tsaLogicService.delete(tsa);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}		
	}
	
	/**
	 * bsc.tsaSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG007D0001A")
	public String doSave() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.saveOrUpdate("save");
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}		
	
	/**
	 * bsc.tsaUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG007D0001E")
	public String doUpdate() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.saveOrUpdate("update");
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}		

	/**
	 * bsc.tsaDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG007D0001Q")
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
