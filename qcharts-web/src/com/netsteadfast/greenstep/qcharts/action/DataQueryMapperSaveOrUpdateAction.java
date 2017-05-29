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
package com.netsteadfast.greenstep.qcharts.action;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.qcharts.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.qcharts.service.logic.IDataQueryMapperLogicService;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.DataQueryMapperVO;

@ControllerAuthority(check=true)
@Controller("qcharts.web.controller.DataQueryMapperSaveOrUpdateAction")
@Scope
public class DataQueryMapperSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = 753640335352540919L;
	protected Logger logger=Logger.getLogger(DataQueryMapperSaveOrUpdateAction.class);
	private IDataQueryMapperLogicService dataQueryMapperLogicService;
	private String message = "";
	private String success = IS_NO;
	
	public DataQueryMapperSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public IDataQueryMapperLogicService getDataQueryMapperLogicService() {
		return dataQueryMapperLogicService;
	}

	@Autowired
	@Resource(name="qcharts.service.logic.DataQueryMapperLogicService")		
	public void setDataQueryMapperLogicService(
			IDataQueryMapperLogicService dataQueryMapperLogicService) {
		this.dataQueryMapperLogicService = dataQueryMapperLogicService;
	}	
	
	private void checkFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("name", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.QCHARTS_PROG001D0002A_name") )
		.process().throwMessage();
		
		this.getCheckFieldHandler().single(
				"labelField|valueField", 
				(StringUtils.isBlank(this.getFields().get("appendFields"))), 
				this.getText("MESSAGE.QCHARTS_PROG001D0002A_appendFields") ).throwMessage();
	}

	private List<Map<String, String>> fillFieldsMapperData() throws Exception {
		String appendFields = this.getFields().get("appendFields");
		appendFields = SimpleUtils.deB64( appendFields ); // 這邊要 decode btoa		
		String fieldMapperDataStr = URLDecoder.decode( appendFields, "utf8" ); // 這邊要 decode 因為 encodeURIComponent( escape( ) )
		List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
		String[] mapperTmp = fieldMapperDataStr.split(Constants.ID_DELIMITER);
		for (int i=0; mapperTmp!=null && i<mapperTmp.length; i++) {
			String fieldTmp[] = mapperTmp[i].split(":");
			if (fieldTmp==null || fieldTmp.length!=2) {
				continue;
			}
			String labelField = fieldTmp[0].trim();
			String valueField = fieldTmp[1].trim();
			if (StringUtils.isBlank(labelField) || StringUtils.isBlank(valueField)) {
				continue;
			}
			boolean isFound = false;
			for (Map<String, String> data : datas) {
				if (data.get(labelField)!=null && data.get(labelField).equals(valueField)) {
					isFound = true;
				}
			}
			if (!isFound) {
				Map<String, String> dataMap = new HashMap<String, String>();
				dataMap.put(labelField, valueField);
				datas.add(dataMap);				
			}
		}
		this.getCheckFieldHandler().single(
				"labelField|valueField", 
				(null == datas || datas.size()<1), 
				this.getText("MESSAGE.QCHARTS_PROG001D0002A_appendFields") ).throwMessage();
		return datas;
	}
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();	
		DataQueryMapperVO queryMapper = new DataQueryMapperVO();
		this.transformFields2ValueObject(queryMapper, new String[]{"name", "description"});
		DefaultResult<DataQueryMapperVO> result = this.dataQueryMapperLogicService.createMapper(
				queryMapper, this.fillFieldsMapperData()); 
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();	
		DataQueryMapperVO queryMapper = new DataQueryMapperVO();
		this.transformFields2ValueObject(queryMapper, new String[]{"oid", "name", "description"});
		DefaultResult<DataQueryMapperVO> result = this.dataQueryMapperLogicService.updateMapper(
				queryMapper, this.fillFieldsMapperData()); 
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}		
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		DataQueryMapperVO queryMapper = new DataQueryMapperVO();
		this.transformFields2ValueObject(queryMapper, new String[]{"oid"});
		DefaultResult<Boolean> result = this.dataQueryMapperLogicService.deleteMapper(queryMapper);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}
	}
	
	/**
	 * qcharts.dataQueryMapperSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG001D0002A")
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
	 * qcharts.dataQueryMapperUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG001D0002E")
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
	 * qcharts.dataQueryMapperDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG001D0002Q")
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
