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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import ognl.Ognl;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.BaseValueObj;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.bsc.service.logic.IWeightLogicService;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.WeightSaveOrUpdateAction")
@Scope
public class WeightSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = -3998640109128600261L;
	protected Logger logger=Logger.getLogger(WeightSaveOrUpdateAction.class);
	private IWeightLogicService weightLogicService;
	private String message = "";
	private String success = IS_NO;
	
	public WeightSaveOrUpdateAction() {
		super();
	}
	
	@JSON(serialize=false)
	public IWeightLogicService getWeightLogicService() {
		return weightLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.WeightLogicService")		
	public void setWeightLogicService(IWeightLogicService weightLogicService) {
		this.weightLogicService = weightLogicService;
	}

	@SuppressWarnings("unchecked")
	private <T extends BaseValueObj> List<T> fillDatas(Class<?> clazz, String inputHeanId) throws Exception {
		List<T> elements = new ArrayList<T>();
		Enumeration<String> parameterNames = this.getHttpServletRequest().getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String name = parameterNames.nextElement();
			if (!name.startsWith(inputHeanId)) {
				continue;
			}
			Float value = NumberUtils.toFloat( this.getHttpServletRequest().getParameter(name), 1f );
			T obj = (T)clazz.newInstance();
			Ognl.setValue("oid", obj, name.substring(inputHeanId.length(), name.length()) );
			Ognl.setValue("weight", obj, new BigDecimal(value) );
			elements.add(obj);
		}
		return elements;
	}
	
	private List<PerspectiveVO> getPerspectives() throws Exception {
		return this.fillDatas(PerspectiveVO.class, BscConstants.WEIGHT_PERSPECTIVE_ID);
	}
	
	private List<ObjectiveVO>  getObjectives() throws Exception {
		return this.fillDatas(ObjectiveVO.class, BscConstants.WEIGHT_OBJECTIVE_ID);
	}
	
	private List<KpiVO> getKpis() throws Exception {
		return this.fillDatas(KpiVO.class, BscConstants.WEIGHT_KPI_ID);
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		String errMessage = super.joinPageMessage(
				this.getText("MESSAGE.BSC_PROG002D0006Q_saveStep_label")+":",
				this.getText("MESSAGE.BSC_PROG002D0006Q_saveStep_01"),
				this.getText("MESSAGE.BSC_PROG002D0006Q_saveStep_02"),
				this.getText("MESSAGE.BSC_PROG002D0006Q_saveStep_03"));
		this.getCheckFieldHandler().single(
				"visionOid", 
				( !YesNo.YES.equals(this.getHttpServletRequest().getParameter("BSC_PROG002D0006Q_queryWeight")) ), 
				errMessage ).throwMessage();
		DefaultResult<Boolean> result = this.weightLogicService.update(
				this.getPerspectives(), 
				this.getObjectives(), 
				this.getKpis());
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}
	}
	
	/**
	 * bsc.weightUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0006Q")
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
