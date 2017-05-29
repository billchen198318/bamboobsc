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

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.netsteadfast.greenstep.bsc.service.IKpiService;
import com.netsteadfast.greenstep.po.hbm.BbKpi;
import com.netsteadfast.greenstep.vo.KpiVO;

@ControllerAuthority(check=false)
@Controller("bsc.web.controller.KpiTreeJsonAction")
@Scope
public class KpiTreeJsonAction extends BaseJsonAction {
	private static final long serialVersionUID = -1219998276303688017L;
	protected Logger logger=Logger.getLogger(KpiTreeJsonAction.class);
	private IKpiService<KpiVO, BbKpi, String> kpiService;
	private String message = "";
	private String success = IS_NO;
	
	// -------------------------------------------------------------------------------
	// KPI json tree 資料用的
	private String identifier = "id";
	private String label = "name";
	private List<Map<String, Object>> items;
	// -------------------------------------------------------------------------------		
	
	public KpiTreeJsonAction() {
		super();
	}
	
	@JSON(serialize=false)
	public IKpiService<KpiVO, BbKpi, String> getKpiService() {
		return kpiService;
	}

	@Autowired
	@Resource(name="bsc.service.KpiService")	
	public void setKpiService(IKpiService<KpiVO, BbKpi, String> kpiService) {
		this.kpiService = kpiService;
	}

	@SuppressWarnings("unchecked")
	private void loadTreeItems() throws ServiceException, Exception {
		Context context = new ContextBase();
		context.put("http", this.getHttpServletRequest().getScheme()+"://");
		SimpleChain chain = new SimpleChain();		
		ChainResultObj result = chain.getResultFromResource("kpiTreeItemsChain", context);
		this.message = super.defaultString( result.getMessage() );
		if (result.getValue() != null && result.getValue() instanceof List) {
			this.items = (List<Map<String, Object>>)result.getValue();
		}
	}
	
	private void countTreeItems() throws ServiceException, Exception {
		if (this.kpiService.countForMixData(null, null, null, null, null, null) > 0) {
			this.success = IS_YES;
		}
	}
	
	/**
	 * bsc.kpiTreeJsonAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false)
	@ControllerMethodAuthority(programId="BSC_PROG002D0005Q")
	public String doGetTree() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.loadTreeItems();
			this.success = SUCCESS;
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}		
	
	/**
	 * bsc.kpiCountTreeJsonAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false)
	@ControllerMethodAuthority(programId="BSC_PROG002D0005Q")
	public String doGetCountTree() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.countTreeItems();
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

	public String getIdentifier() {
		return identifier;
	}

	public String getLabel() {
		return label;
	}

	public List<Map<String, Object>> getItems() {
		return items;
	}
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
}
