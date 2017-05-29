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

import javax.annotation.Resource;

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
import com.netsteadfast.greenstep.bsc.service.IScoreColorService;
import com.netsteadfast.greenstep.po.hbm.BbScoreColor;
import com.netsteadfast.greenstep.util.TemplateUtils;
import com.netsteadfast.greenstep.vo.ScoreColorVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.ScoreColorContentQueryAction")
@Scope
public class ScoreColorContentQueryAction extends BaseJsonAction {
	private static final long serialVersionUID = 4183439356298557070L;
	protected Logger logger=Logger.getLogger(ScoreColorContentQueryAction.class);
	private IScoreColorService<ScoreColorVO, BbScoreColor, String> scoreColorService;
	private String message = "";
	private String success = IS_NO;
	private String body = ""; // 權重設定頁面資料內容
	
	public ScoreColorContentQueryAction() {
		super();
	}
	
	@JSON(serialize=false)
	public IScoreColorService<ScoreColorVO, BbScoreColor, String> getScoreColorService() {
		return scoreColorService;
	}

	@Autowired
	@Resource(name="bsc.service.ScoreColorService")		
	public void setScoreColorService(
			IScoreColorService<ScoreColorVO, BbScoreColor, String> scoreColorService) {
		this.scoreColorService = scoreColorService;
	}
	
	private void renderBody() throws ControllerException, ServiceException, Exception {
		if ( this.scoreColorService.countByParams(null) < 1 ) {
			this.message = SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA);
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> orderParams = new HashMap<String, String>();
		orderParams.put("scoreMin", "ASC");
		List<BbScoreColor> scoreColors = this.scoreColorService.findListByParams(
				params, null, orderParams);
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("scoreColors", scoreColors);
		parameter.put("scoreRange", this.getText("TPL.BSC_PROG001D0004Q_scoreRange"));
		parameter.put("scoreColor", this.getText("TPL.BSC_PROG001D0004Q_scoreColor"));
		parameter.put("colorTest", this.getText("TPL.BSC_PROG001D0004Q_colorTest"));		
		parameter.put("btnEdit", this.getText("TPL.BSC_PROG001D0004Q_btnEdit"));
		parameter.put("btnDelete", this.getText("TPL.BSC_PROG001D0004Q_btnDelete"));		
		this.body = TemplateUtils.processTemplate(
				"resourceTemplate", 
				TemplateUtils.class.getClassLoader(), 
				"META-INF/resource/score-color-body.ftl", 
				parameter);
		this.success = IS_YES;
	}
	
	/**
	 * bsc.scoreColorContentQueryAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0004Q")
	public String execute() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.renderBody();
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
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
}
