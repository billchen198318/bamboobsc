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
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.bsc.service.logic.ISwotLogicService;
import com.netsteadfast.greenstep.vo.SwotVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.SwotSaveOrUpdateAction")
@Scope
public class SwotSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = -2665195954670151129L;
	protected Logger logger=Logger.getLogger(SwotSaveOrUpdateAction.class);
	private ISwotLogicService swotLogicService;
	private String message = "";
	private String success = IS_NO;
	
	public SwotSaveOrUpdateAction() {
		super();		
	}
	
	@JSON(serialize=false)
	public ISwotLogicService getSwotLogicService() {
		return swotLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.SwotLogicService")		
	public void setSwotLogicService(ISwotLogicService swotLogicService) {
		this.swotLogicService = swotLogicService;
	}

	private void checkFields() throws ControllerException, Exception {
		String errMsg = "";
		String visionOid = this.getHttpServletRequest().getParameter("BSC_PROG002D0008Q_visionOid");
		String organizationOid = this.getHttpServletRequest().getParameter("BSC_PROG002D0008Q_organizationOid");
		errMsg = this.getCheckFieldHandler()
		.single("visionOid", ( this.isNoSelectId(visionOid) || StringUtils.isBlank(visionOid) ) , this.getText("MESSAGE.BSC_PROG002D0008Q_visionOid") )
		.single("organizationOid", ( this.isNoSelectId(organizationOid) || StringUtils.isBlank(organizationOid) ), this.getText("MESSAGE.BSC_PROG002D0008Q_organizationOid") ).getMessage();
		
		if ( !StringUtils.isBlank(errMsg) ) {
			errMsg += this.getText("MESSAGE.BSC_PROG002D0008Q_msg1");
			super.throwMessage( errMsg );
		}	
	}
	
	private List<SwotVO> fillDatas() throws Exception {
		String headParam = "BSC_PROG002D0008Q" + BscConstants.SWOT_TEXT_INPUT_ID_SEPARATE;
		List<SwotVO> datas = new ArrayList<SwotVO>();
		Enumeration<String> names = this.getHttpServletRequest().getParameterNames();
		while (names.hasMoreElements()) {
			String paramName = names.nextElement();
			if (!paramName.startsWith(headParam)) {
				continue;
			}
			// BSC_PROG002D0008Q:TYPE:VIS_ID:PER_ID:ORG_ID
			String tmpId[] = paramName.split(BscConstants.SWOT_TEXT_INPUT_ID_SEPARATE);
			if (tmpId.length!=5) {
				continue;
			}
			SwotVO obj = new SwotVO();
			obj.setType(tmpId[1]);
			obj.setVisId(tmpId[2]);
			obj.setPerId(tmpId[3]);
			obj.setOrgId(tmpId[4]);
			obj.setIssues( StringUtils.defaultString(this.getHttpServletRequest().getParameter(paramName)) );
			datas.add(obj);
		}
		return datas;
	}
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		String visionOid = this.getHttpServletRequest().getParameter("BSC_PROG002D0008Q_visionOid");
		String organizationOid = this.getHttpServletRequest().getParameter("BSC_PROG002D0008Q_organizationOid");
		List<SwotVO> datas = this.fillDatas();
		DefaultResult<Boolean> result = this.swotLogicService.create(visionOid, organizationOid, datas);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}
	}
	
	/**
	 * bsc.swotSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0008Q")
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
