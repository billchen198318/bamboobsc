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

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.chain.SimpleChain;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ChainResultObj;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.YesNo;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.WeightContentQueryAction")
@Scope
public class WeightContentQueryAction extends BaseJsonAction {
	private static final long serialVersionUID = -4286828813266505582L;
	protected Logger logger=Logger.getLogger(WeightContentQueryAction.class);
	private String message = "";
	private String success = IS_NO;
	private String body = ""; // 權重設定頁面資料內容
	
	public WeightContentQueryAction() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	private void renderBody() throws ControllerException, ServiceException, Exception {
		this.getCheckFieldHandler().single(
				"visionOid", 
				( super.isNoSelectId(this.getFields().get("visionOid")) ), 
				this.getText("MESSAGE.BSC_PROG002D0006Q_visionOid") ).throwMessage();
		SimpleChain simpleChanin = new SimpleChain();
		Context context = new ContextBase();
		context.put("visionOid", this.getFields().get("visionOid") );
		if (YesNo.YES.equals(this.getFields().get("autoAllocation")) ) {
			context.put("autoAllocation", this.getFields().get("autoAllocation") );
		}
		context.put("weightName", this.getText("TPL.BSC_PROG002D0006Q_weightName"));
		ChainResultObj resultObj = simpleChanin.getResultFromResource("weightItemsChain", context);
		this.body = String.valueOf( resultObj.getValue() );
		this.message = resultObj.getMessage();
		this.success = IS_YES;
	}
	
	/**
	 * bsc.weightContentQueryAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0006Q")
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
