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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.util.MeasureDataCalendarUtils;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.MeasureDataCalendarQueryAction")
@Scope
public class MeasureDataCalendarQueryAction extends BaseJsonAction {
	private static final long serialVersionUID = -4686051236562296624L;
	protected Logger logger=Logger.getLogger(MeasureDataCalendarQueryAction.class);
	private String message = "";
	private String success = IS_NO;
	private String body = "";
	private String dateValue = "";
	
	public MeasureDataCalendarQueryAction() {
		super();
	}
	
	private void checkFields() throws ControllerException {
		String oid = this.getFields().get("oid");
		String dataFor = this.getFields().get("dataFor");
		String organizationOid = this.getFields().get("organizationOid");
		String employeeOid = this.getFields().get("employeeOid");
		String frequency = this.getFields().get("frequency");
		if (StringUtils.isBlank(oid) || oid.startsWith(BscConstants.KPI_TREE_NOT_ITEM)) {
			super.throwMessage( "mainInfoTemp", "Please select KPI!" );
		}
		this.getCheckFieldHandler()
		.single("employeeOid", ( BscConstants.MEASURE_DATA_FOR_EMPLOYEE.equals(dataFor) && this.isNoSelectId(employeeOid) ), this.getText("BSC_PROG002D0005Q_msg1") )
		.single("frequency", (this.isNoSelectId(frequency)), this.getText("BSC_PROG002D0005Q_msg2") )
		.single("organizationOid", ( BscConstants.MEASURE_DATA_FOR_ORGANIZATION.equals(dataFor) && this.isNoSelectId(organizationOid) ), this.getText("BSC_PROG002D0005Q_msg3") )
		.single("dataFor", ( BscConstants.MEASURE_DATA_FOR_ALL.equals(dataFor) && !this.isNoSelectId(employeeOid) && !this.isNoSelectId(organizationOid) ), this.getText("BSC_PROG002D0005Q_msg4") )
		.throwMessage();
	}
	
	private String handlerDate() throws Exception {
		String dateStr = this.getFields().get("date");
		String frequency = this.getFields().get("frequency");
		String dateStatus = this.getFields().get("dateStatus");
		DateTime dateTime = new DateTime(dateStr);
		if ("-1".equals(dateStatus)) { // 上一個
			if (BscMeasureDataFrequency.FREQUENCY_DAY.equals(frequency) || BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency) ) { // 上一個月
				dateTime = dateTime.plusMonths(-1);
			} else { // 上一個年
				dateTime = dateTime.plusYears(-1);
			}			
		}
		if ("1".equals(dateStatus)) { // 下一個
			if (BscMeasureDataFrequency.FREQUENCY_DAY.equals(frequency) || BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency) ) { // 下一個月
				dateTime = dateTime.plusMonths(1);
			} else { // 下一個年
				dateTime = dateTime.plusYears(1);
			}			
		}		
		return dateTime.toString("yyyy-MM-dd");
	}
	
	private Map<String, String> getLabels() {
		Map<String, String> labels = new HashMap<String, String>();
		labels.put("management", this.getText("TPL.BSC_PROG002D0005Q_management"));
		labels.put("calculation", this.getText("TPL.BSC_PROG002D0005Q_calculation"));
		labels.put("unit", this.getText("TPL.BSC_PROG002D0005Q_unit"));
		labels.put("target", this.getText("TPL.BSC_PROG002D0005Q_target"));
		labels.put("min", this.getText("TPL.BSC_PROG002D0005Q_min"));
		labels.put("formulaName", this.getText("TPL.BSC_PROG002D0005Q_formulaName"));
		labels.put("targetValueName", this.getText("TPL.BSC_PROG002D0005Q_targetValueName"));
		labels.put("actualValueName", this.getText("TPL.BSC_PROG002D0005Q_actualValueName"));
		return labels;
	}
	
	private void renderCalendar() throws ControllerException, ServiceException, Exception {
		this.checkFields();
		this.dateValue = this.handlerDate();
		this.body = MeasureDataCalendarUtils.renderBody(
				this.getFields().get("oid"), 
				this.dateValue, 
				this.getFields().get("frequency"), 
				this.getFields().get("dataFor"), 
				this.getFields().get("organizationOid"), 
				this.getFields().get("employeeOid"),
				this.getLabels());		
		this.success = IS_YES;
	}
		
	/**
	 * bsc.measureDataCalendarQueryAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0005Q")
	public String execute() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.renderCalendar();
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

	public String getBody() {
		return body;
	}

	public String getDateValue() {
		return dateValue;
	}
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
}
