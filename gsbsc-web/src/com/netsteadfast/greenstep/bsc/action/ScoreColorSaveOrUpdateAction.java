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

import java.util.List;
import java.util.Map;

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
import com.netsteadfast.greenstep.bsc.action.utils.BscNumberFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.service.IScoreColorService;
import com.netsteadfast.greenstep.po.hbm.BbScoreColor;
import com.netsteadfast.greenstep.vo.ScoreColorVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.ScoreColorSaveOrUpdateAction")
@Scope
public class ScoreColorSaveOrUpdateAction extends BaseJsonAction {
	private static final long serialVersionUID = -2709500391388974626L;
	protected Logger logger=Logger.getLogger(ScoreColorSaveOrUpdateAction.class);
	private IScoreColorService<ScoreColorVO, BbScoreColor, String> scoreColorService;
	private String message = "";
	private String success = IS_NO;
	
	public ScoreColorSaveOrUpdateAction() {
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
	
	private void checkFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("score", BscNumberFieldCheckUtils.class, this.getText("MESSAGE.BSC_PROG001D0004Q_score") )
		.process().throwMessage();
		
		this.getCheckFieldHandler()
		.single("score", ( NumberUtils.toLong(this.getFields().get("score"), 0) < BscConstants.SCORE_COLOR_MIN_VALUE ), this.getText("MESSAGE.BSC_PROG001D0004Q_score_msg1") + " " + BscConstants.SCORE_COLOR_MIN_VALUE )
		.single("score", ( NumberUtils.toLong(this.getFields().get("score"), 0) > BscConstants.SCORE_COLOR_MAX_VALUE ), this.getText("MESSAGE.BSC_PROG001D0004Q_score_msg2") + " " + BscConstants.SCORE_COLOR_MAX_VALUE )
		.throwMessage();
		
		if ( StringUtils.isBlank(this.getFields().get("bgColor")) || StringUtils.isBlank(this.getFields().get("fontColor")) ) {
			super.throwMessage( this.getText("MESSAGE.BSC_PROG001D0004Q_bgColorfontColor_msg") );
		}
	}	
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		int score = NumberUtils.toInt(this.getFields().get("score"), 0);
		int nowScoreMaxValue = this.scoreColorService.findForMaxValue();
		this.getCheckFieldHandler().single(
				"score", 
				( score < nowScoreMaxValue + 1 ), 
				this.getText("MESSAGE.BSC_PROG001D0004Q_score_msg3") + " " + nowScoreMaxValue ).throwMessage();
		ScoreColorVO scoreColor = new ScoreColorVO();
		scoreColor.setScoreMin( nowScoreMaxValue + 1 );
		scoreColor.setScoreMax( score );
		scoreColor.setFontColor( this.getFields().get("fontColor") );
		scoreColor.setBgColor( this.getFields().get("bgColor") );
		DefaultResult<ScoreColorVO> result = this.scoreColorService.saveObject(scoreColor);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		ScoreColorVO scoreColor = new ScoreColorVO();
		this.transformFields2ValueObject(scoreColor, new String[]{"oid"});
		DefaultResult<ScoreColorVO> oldResult = this.scoreColorService.findObjectByOid(scoreColor);
		if (oldResult.getValue()==null) {
			throw new ServiceException( oldResult.getSystemMessage().getValue() );
		}
		scoreColor = oldResult.getValue();
		scoreColor.setFontColor( this.getFields().get("fontColor") );
		scoreColor.setBgColor( this.getFields().get("bgColor") );
		int score = NumberUtils.toInt(this.getFields().get("score"), 0);
		int nowScoreMaxValue = this.scoreColorService.findForMaxValue();
		if ( nowScoreMaxValue == scoreColor.getScoreMax() ) {
			scoreColor.setScoreMax( score );
		}
		DefaultResult<ScoreColorVO> result = this.scoreColorService.updateObject(scoreColor);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		ScoreColorVO scoreColor = new ScoreColorVO();
		this.transformFields2ValueObject(scoreColor, new String[]{"oid"});
		DefaultResult<ScoreColorVO> oldResult = this.scoreColorService.findObjectByOid(scoreColor);
		if (oldResult.getValue()==null) {
			throw new ServiceException( oldResult.getSystemMessage().getValue() );
		}
		int nowScoreMaxValue = this.scoreColorService.findForMaxValue();
		if (oldResult.getValue().getScoreMax() != nowScoreMaxValue) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.DATA_ERRORS));
		}
		/*
		DefaultResult<Boolean> result = this.scoreColorService.deleteObject(scoreColor);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}
		*/
		boolean delete = this.scoreColorService.deleteByPKng( scoreColor.getOid() );
		if (delete) {
			this.success = IS_YES;
			this.message = SysMessageUtil.get(GreenStepSysMsgConstants.DELETE_SUCCESS);
		} else {
			this.message = SysMessageUtil.get(GreenStepSysMsgConstants.DELETE_FAIL);
		}
	}
	
	/**
	 * bsc.scoreColorSaveOrUpdateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0004Q")
	public String doSaveOrUpdate() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			String oid = this.getFields().get("oid");
			if (!StringUtils.isBlank(oid)) {
				this.update();
			} else {
				this.save();
			}
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}			

	/**
	 * bsc.scoreColorDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0004Q")
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
