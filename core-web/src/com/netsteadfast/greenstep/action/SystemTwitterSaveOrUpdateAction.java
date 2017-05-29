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
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.po.hbm.TbSys;
import com.netsteadfast.greenstep.po.hbm.TbSysTwitter;
import com.netsteadfast.greenstep.service.ISysService;
import com.netsteadfast.greenstep.service.ISysTwitterService;
import com.netsteadfast.greenstep.vo.SysTwitterVO;
import com.netsteadfast.greenstep.vo.SysVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemTwitterSaveOrUpdateAction")
@Scope
public class SystemTwitterSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = 409768847837561944L;
	protected Logger logger=Logger.getLogger(SystemTwitterSaveOrUpdateAction.class);
	private ISysService<SysVO, TbSys, String> sysService;
	private ISysTwitterService<SysTwitterVO, TbSysTwitter, String> sysTwitterService;
	private String message = "";
	private String success = IS_NO;
	
	public SystemTwitterSaveOrUpdateAction() {
		super();
	}

	@JSON(serialize=false)
	public ISysService<SysVO, TbSys, String> getSysService() {
		return sysService;
	}

	@Autowired
	@Resource(name="core.service.SysService")			
	public void setSysService(ISysService<SysVO, TbSys, String> sysService) {
		this.sysService = sysService;
	}

	@JSON(serialize=false)
	public ISysTwitterService<SysTwitterVO, TbSysTwitter, String> getSysTwitterService() {
		return sysTwitterService;
	}

	@Autowired
	@Resource(name="core.service.SysTwitterService")				
	public void setSysTwitterService(
			ISysTwitterService<SysTwitterVO, TbSysTwitter, String> sysTwitterService) {
		this.sysTwitterService = sysTwitterService;
	}
	
	private void checkFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("systemOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0010A_systemOid") )
		.add("title", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0010A_title") )
		.add("content", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.CORE_PROG001D0010A_content") )
		.process().throwMessage();
		
		this.getCheckFieldHandler().single(
				"content", 
				( this.getFields().get("content").length() > 2000 || this.getFields().get("content").indexOf("twitter") == -1 || this.getFields().get("content").indexOf("data-widget-id") == -1 ), 
				this.getText("MESSAGE.CORE_PROG001D0010A_contentTwitterWidget") )
		.throwMessage();
	}	
	
	private SysVO findSys() throws ServiceException, Exception {		
		SysVO sys = new SysVO();
		sys.setOid( this.getFields().get("systemOid") );
		DefaultResult<SysVO> result = this.sysService.findObjectByOid(sys);
		if ( result.getValue() == null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		sys = result.getValue();
		return sys;
	}
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysVO sys = this.findSys();
		SysTwitterVO sysTwitter = new SysTwitterVO();
		sysTwitter.setSystem(sys.getSysId());
		sysTwitter.setTitle(this.getFields().get("title"));
		sysTwitter.setEnableFlag( ( "true".equals( this.getFields().get("enableFlag")) ? YesNo.YES : YesNo.NO ) );
		sysTwitter.setContent( this.getFields().get("content").getBytes() );
		DefaultResult<SysTwitterVO> result = this.sysTwitterService.saveObject(sysTwitter);
		this.message = result.getSystemMessage().getValue();
		if ( result.getValue() != null ) {
			this.success = IS_YES;
		}
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		SysTwitterVO oldSysTwitter = new SysTwitterVO();
		oldSysTwitter.setOid( this.getFields().get("oid") );
		DefaultResult<SysTwitterVO> oldResult = this.sysTwitterService.findObjectByOid(oldSysTwitter);
		if (oldResult.getValue() == null) {
			throw new ServiceException( oldResult.getSystemMessage().getValue() );
		}
		oldSysTwitter = oldResult.getValue();
		SysTwitterVO sysTwitter = new SysTwitterVO();
		sysTwitter.setOid( oldSysTwitter.getOid() );
		sysTwitter.setSystem( oldSysTwitter.getSystem() );
		sysTwitter.setTitle(this.getFields().get("title"));
		sysTwitter.setEnableFlag( ( "true".equals( this.getFields().get("enableFlag")) ? YesNo.YES : YesNo.NO ) );
		sysTwitter.setContent(null); // 先清除之前的blob資料
		this.sysTwitterService.updateObject(sysTwitter); // 先清除之前的blob資料		
		String content = StringEscapeUtils.unescapeEcmaScript(this.getFields().get("content"));				
		sysTwitter.setContent( content.getBytes() );
		DefaultResult<SysTwitterVO> result = this.sysTwitterService.updateObject(sysTwitter);
		this.message = result.getSystemMessage().getValue();
		if ( result.getValue() != null ) {
			this.success = IS_YES;
		}		
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		SysTwitterVO sysTwitter = new SysTwitterVO();
		sysTwitter.setOid( this.getFields().get("oid") );
		DefaultResult<Boolean> result = this.sysTwitterService.deleteObject(sysTwitter);
		this.message = result.getSystemMessage().getValue();
		if ( result.getValue() && result.getValue() ) {
			this.success = IS_YES;
		}
	}
	
	/**
	 * core.systemTwitterSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0010A")
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
	 * core.systemTwitterUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0010E")
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
	 * core.systemTwitterDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG001D0010Q")
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
