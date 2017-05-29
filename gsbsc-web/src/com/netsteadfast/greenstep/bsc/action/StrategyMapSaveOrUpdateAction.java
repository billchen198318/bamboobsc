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

import java.net.URLDecoder;
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
import com.netsteadfast.greenstep.bsc.service.logic.IStrategyMapLogicService;
import com.netsteadfast.greenstep.util.SimpleUtils;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.StrategyMapSaveOrUpdateAction")
@Scope
public class StrategyMapSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = -6576302092263107184L;
	protected Logger logger=Logger.getLogger(StrategyMapSaveOrUpdateAction.class);
	private IStrategyMapLogicService strategyMapLogicService;
	private String message = "";
	private String success = IS_NO;
	
	public StrategyMapSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public IStrategyMapLogicService getStrategyMapLogicService() {
		return strategyMapLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.StrategyMapLogicService")			
	public void setStrategyMapLogicService(
			IStrategyMapLogicService strategyMapLogicService) {
		this.strategyMapLogicService = strategyMapLogicService;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> fillStrategyMapItems() throws Exception {
		String mapData = this.getFields().get("mapData");
		mapData = SimpleUtils.deB64( mapData );	// decode-base64 因為 btoa	
		String jsonData = URLDecoder.decode( mapData, "utf8" ); // 這邊要 decode 因為 encodeURIComponent
		Map<String, Object> dataMap = (Map<String, Object>)
				new ObjectMapper().readValue(jsonData, LinkedHashMap.class);
		return dataMap;
	}
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		String visionOid = this.getFields().get("visionOid");
		String mapData = this.getFields().get("mapData");
		if ( StringUtils.isBlank(visionOid) || super.isNoSelectId(visionOid) ) {
			throw new ControllerException( this.getText("MESSAGE.BSC_PROG002D0007Q_vision") );
		}
		if ( StringUtils.isBlank(mapData) ) {
			throw new ControllerException( this.getText("MESSAGE.BSC_PROG002D0007Q_msg1") );
		}		
		Map<String, Object> items = this.fillStrategyMapItems();		
		DefaultResult<Boolean> result = this.strategyMapLogicService.create(visionOid, items);
		this.message = result.getSystemMessage().getValue();
		if ( result.getValue()!=null && result.getValue() ) {
			this.success = IS_YES;
		}
	}
	
	/**
	 * bsc.strategyMapSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0007Q")
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
