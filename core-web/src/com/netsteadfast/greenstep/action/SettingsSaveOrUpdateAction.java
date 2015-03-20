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
package com.netsteadfast.greenstep.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.action.utils.EmailFieldCheckUtils;
import com.netsteadfast.greenstep.action.utils.NotBlankFieldCheckUtils;
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
import com.netsteadfast.greenstep.po.hbm.TbSysCode;
import com.netsteadfast.greenstep.service.ISysCodeService;
import com.netsteadfast.greenstep.vo.SysCodeVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SettingsSaveOrUpdateAction")
@Scope
public class SettingsSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = 7340763400093706218L;
	protected Logger logger=Logger.getLogger(SettingsSaveOrUpdateAction.class);
	private ISysCodeService<SysCodeVO, TbSysCode, String> sysCodeService;
	private String message = "";
	private String success = IS_NO;
	
	public SettingsSaveOrUpdateAction() {
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
	
	@SuppressWarnings("unchecked")
	private void checkFields() throws ControllerException {
		try {
			super.checkFields(
					new String[]{
							"mailFrom",
							"mailFrom"
					}, 
					new String[]{
							"Mail from format incorrect!<BR/>",
							"Mail from is required!"
					}, 
					new Class[]{
							EmailFieldCheckUtils.class,
							NotBlankFieldCheckUtils.class
					},
					this.getFieldsId() );			
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new ControllerException(e.getMessage().toString());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new ControllerException(e.getMessage().toString());
		}
	}		
	
	private boolean updateCode(String code, String value) throws ServiceException, Exception {
		SysCodeVO sysCode = new SysCodeVO();
		sysCode.setType("CNF");
		sysCode.setCode(code);
		DefaultResult<SysCodeVO> result = this.sysCodeService.findByUK(sysCode);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		sysCode = result.getValue();
		sysCode.setParam1( value );
		result = this.sysCodeService.updateObject(sysCode);	
		this.message = result.getSystemMessage().getValue();
		if ( result.getValue()!=null ) {
			return true;
		}
		return false;
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		String mailFrom = this.getFields().get("mailFrom");
		String mailEnable = YesNo.YES;
		if ( "false".equals(this.getFields().get("mailEnable")) ) {
			mailEnable = YesNo.NO;
		}
		if ( this.updateCode("CNF_CONF001", mailFrom) && this.updateCode("CNF_CONF002", mailEnable) ) {
			this.success = IS_YES;
		} else {
			this.message = SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_FAIL);
		}
	}
	
	/**
	 * core.settingsUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0011Q")
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
