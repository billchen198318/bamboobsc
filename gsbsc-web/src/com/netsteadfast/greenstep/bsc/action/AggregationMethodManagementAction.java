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

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.ScriptTypeCode;
import com.netsteadfast.greenstep.bsc.service.IAggregationMethodService;
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.po.hbm.BbAggregationMethod;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.AggregationMethodVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.AggregationMethodManagementAction")
@Scope
public class AggregationMethodManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -3666323274644214853L;
	private IAggregationMethodService<AggregationMethodVO, BbAggregationMethod, String> aggregationMethodService;
	private Map<String, String> typeMap = this.providedSelectZeroDataMap(true);
	private AggregationMethodVO aggr = new AggregationMethodVO();
	private String uploadExprOid1 = "";
	private String uploadExprOid2 = "";
	
	public AggregationMethodManagementAction() {
		super();
	}
	
	public IAggregationMethodService<AggregationMethodVO, BbAggregationMethod, String> getAggregationMethodService() {
		return aggregationMethodService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.AggregationMethodService")		
	public void setAggregationMethodService(
			IAggregationMethodService<AggregationMethodVO, BbAggregationMethod, String> aggregationMethodService) {
		this.aggregationMethodService = aggregationMethodService;
	}

	private void initData() throws ServiceException, Exception {
		this.typeMap = ScriptTypeCode.getTypeMap(true);
	}	
	
	private void loadAggregationMethodData() throws ServiceException, Exception {
		this.transformFields2ValueObject(this.aggr, new String[]{"oid"});
		DefaultResult<AggregationMethodVO> result = this.aggregationMethodService.findObjectByOid(this.aggr);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		this.aggr = result.getValue();		
		this.uploadExprOid1 = UploadSupportUtils.create(
				Constants.getSystem(), 
				UploadTypes.IS_TEMP, 
				false, 
				this.aggr.getExpression1().getBytes(Constants.BASE_ENCODING), 
				this.aggr.getAggrId()+"_p1.txt");
		this.uploadExprOid2 = UploadSupportUtils.create(
				Constants.getSystem(), 
				UploadTypes.IS_TEMP, 
				false, 
				this.aggr.getExpression2().getBytes(Constants.BASE_ENCODING), 
				this.aggr.getAggrId()+"_p2.txt");		
	}
	
	/**
	 * bsc.aggregationMethodManagementAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0008Q")
	public String execute() throws Exception {
		try {
			this.initData();
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return SUCCESS;		
	}		
	
	/**
	 * bsc.aggregationMethodCreateAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0008A")
	public String create() throws Exception {
		try {
			this.initData();
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return SUCCESS;				
	}		
	
	/**
	 * bsc.aggregationMethodEditAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0008E")
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadAggregationMethodData();
			forward = SUCCESS;
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return forward;				
	}	

	@Override
	public String getProgramName() {
		return MenuSupportUtils.getProgramName(this.getProgramId(), this.getLocaleLang());
	}

	@Override
	public String getProgramId() {
		return super.getActionMethodProgramId();
	}

	public Map<String, String> getTypeMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.typeMap);
		return typeMap;
	}

	public AggregationMethodVO getAggr() {
		return aggr;
	}

	public void setAggr(AggregationMethodVO aggr) {
		this.aggr = aggr;
	}

	public String getUploadExprOid1() {
		return uploadExprOid1;
	}

	public String getUploadExprOid2() {
		return uploadExprOid2;
	}

}
