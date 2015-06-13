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
import java.util.Enumeration;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.service.logic.IMeasureDataLogicService;
import com.netsteadfast.greenstep.vo.MeasureDataVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.MeasureDataSaveOrUpdateAction")
@Scope
public class MeasureDataSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = 4987586611446130067L;
	protected Logger logger=Logger.getLogger(MeasureDataSaveOrUpdateAction.class);
	private IMeasureDataLogicService measureDataLogicService;
	private String message = "";
	private String success = IS_NO;
	
	public MeasureDataSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public IMeasureDataLogicService getMeasureDataLogicService() {
		return measureDataLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.MeasureDataLogicService")		
	public void setMeasureDataLogicService(
			IMeasureDataLogicService measureDataLogicService) {
		this.measureDataLogicService = measureDataLogicService;
	}

	private String getDateFromInputName(String name) {
		if (name.startsWith(BscConstants.MEASURE_DATA_TARGET_ID)) {
			return name.substring(BscConstants.MEASURE_DATA_TARGET_ID.length(), name.length());
		}
		if (name.startsWith(BscConstants.MEASURE_DATA_ACTUAL_ID)) {
			return name.substring(BscConstants.MEASURE_DATA_ACTUAL_ID.length(), name.length());
		}		
		return name;
	}
	
	private MeasureDataVO getMeasureDataFromList(String date, List<MeasureDataVO> measureDatas) {
		for (MeasureDataVO measureData : measureDatas) {
			if (date.equals(measureData.getDate())) {
				return measureData;
			}
		}
		MeasureDataVO measureData = new MeasureDataVO();
		return measureData;
	}
	
	private void putMeasureDataToList(MeasureDataVO measureData, List<MeasureDataVO> measureDatas) {
		if ( measureDatas.size() < 1 ) {
			measureDatas.add(measureData);
			return;
		}
		boolean found = false;
		for (MeasureDataVO oldMeasureData : measureDatas) {
			if (oldMeasureData.getDate().equals(measureData.getDate()) ) {
				found = true;
			}
		}
		if (!found) {
			measureDatas.add(measureData);
		}
	}
	
	private List<MeasureDataVO> fillMeasureDatas() throws Exception {
		List<MeasureDataVO> measureDatas = new ArrayList<MeasureDataVO>();
		String frequency = this.defaultString( (String)this.getHttpServletRequest().getParameter("BSC_PROG002D0005Q_frequency") );
		Enumeration<String> parameterNames = this.getHttpServletRequest().getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String name = parameterNames.nextElement();
			if (!(name.startsWith(BscConstants.MEASURE_DATA_TARGET_ID) 
					|| name.startsWith(BscConstants.MEASURE_DATA_ACTUAL_ID) ) ) {
				continue;
			}
			String value = this.getHttpServletRequest().getParameter(name);
			if (value.trim().equals("")) {
				continue;
			}
			String date = this.getDateFromInputName(name);
			if (!NumberUtils.isNumber(value)) {
				value = "0";
			}
			MeasureDataVO measureData = this.getMeasureDataFromList(date, measureDatas);
			measureData.setDate(date);
			measureData.setFrequency(frequency);
			if (name.startsWith(BscConstants.MEASURE_DATA_TARGET_ID)) {
				measureData.setTarget( NumberUtils.toFloat(value) );
			}
			if (name.startsWith(BscConstants.MEASURE_DATA_ACTUAL_ID)) {
				measureData.setActual( NumberUtils.toFloat(value) );
			}
			this.putMeasureDataToList(measureData, measureDatas);
		}
		return measureDatas;
	}
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		if ( !YesNo.YES.equals( (String)this.getHttpServletRequest().getParameter("BSC_PROG002D0005Q_queryCalendar") ) ) {
			throw new ControllerException(
					this.getText("BSC_PROG002D0005Q_saveStep_label") + ":<BR/>"
					+ this.getText("BSC_PROG002D0005Q_saveStep_01") + "<BR/>" 
					+ this.getText("BSC_PROG002D0005Q_saveStep_02") + "<BR/>" 
					+ this.getText("BSC_PROG002D0005Q_saveStep_03") + "<BR/>");
		}
		String oid = this.defaultString( (String)this.getHttpServletRequest().getParameter("BSC_PROG002D0005Q_kpiOid") );
		String date = this.defaultString( (String)this.getHttpServletRequest().getParameter("BSC_PROG002D0005Q_date") );
		String frequency = this.defaultString( (String)this.getHttpServletRequest().getParameter("BSC_PROG002D0005Q_frequency") );
		String dataFor = this.defaultString( (String)this.getHttpServletRequest().getParameter("BSC_PROG002D0005Q_dataFor") );
		date = date.replaceAll("-", "").replaceAll("/", "");
		if (StringUtils.isBlank(oid) || StringUtils.isBlank(date) || StringUtils.isBlank(frequency)
				|| StringUtils.isBlank(dataFor) ) {
			throw new ControllerException( SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK) );
		}
		if (BscMeasureDataFrequency.FREQUENCY_DAY.equals(frequency) || BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency) ) {
			date = date.substring(0, 6);
		} else {
			date = date.substring(0, 4);
		}
		List<MeasureDataVO> measureDatas = this.fillMeasureDatas();
		DefaultResult<Boolean> result = this.measureDataLogicService.saveOrUpdate(
				oid, 
				date, 
				frequency, 
				dataFor, 
				(String)this.getHttpServletRequest().getParameter("BSC_PROG002D0005Q_orgId"), 
				(String)this.getHttpServletRequest().getParameter("BSC_PROG002D0005Q_empId"), 
				measureDatas);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}
	}

	/**
	 * bsc.measureDataSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0005Q")
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
