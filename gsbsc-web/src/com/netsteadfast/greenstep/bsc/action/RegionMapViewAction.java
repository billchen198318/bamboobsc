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
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.chain.SimpleChain;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ChainResultObj;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.bsc.service.logic.IReportRoleViewLogicService;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.VisionVO;

@ControllerAuthority(check=false)
@Controller("bsc.web.controller.RegionMapViewAction")
@Scope
public class RegionMapViewAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = 1561474757352388077L;
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private IVisionService<VisionVO, BbVision, String> visionService; 
	private IReportRoleViewLogicService reportRoleViewLogicService;
	private List<BbOrganization> organizations = new ArrayList<BbOrganization>();
	private int discreteValues = 3;
	private int maximum = 0;
	private int minimum = 0;
	private List<String> yearRangeList = new LinkedList<String>();	
	private String content = "";
	
	public RegionMapViewAction() {
		super();
	}
	
	public IOrganizationService<OrganizationVO, BbOrganization, String> getOrganizationService() {
		return organizationService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.OrganizationService")	
	public void setOrganizationService(
			IOrganizationService<OrganizationVO, BbOrganization, String> organizationService) {
		this.organizationService = organizationService;
	}
	
	@JSON(serialize=false)
	public IVisionService<VisionVO, BbVision, String> getVisionService() {
		return visionService;
	}

	@Autowired
	@Resource(name="bsc.service.VisionService")			
	public void setVisionService(
			IVisionService<VisionVO, BbVision, String> visionService) {
		this.visionService = visionService;
	}	
	
	public IReportRoleViewLogicService getReportRoleViewLogicService() {
		return reportRoleViewLogicService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.logic.ReportRoleViewLogicService")		
	public void setReportRoleViewLogicService(
			IReportRoleViewLogicService reportRoleViewLogicService) {
		this.reportRoleViewLogicService = reportRoleViewLogicService;
	}	

	private void initData() throws ServiceException, Exception {		
		if ( !YesNo.YES.equals(super.getIsSuperRole()) ) {
			this.organizations = this.reportRoleViewLogicService.findForOrganization(
					super.getAccountId());
		}
		if ( this.organizations==null || this.organizations.size()<1 ) { // 這個使用者沒被限只能看某些部門
			this.organizations = this.organizationService.findListByParams(null);
		}
		this.maximum=Integer.parseInt(SimpleUtils.getStrYMD(SimpleUtils.IS_YEAR));
		this.minimum=this.maximum-(this.discreteValues-1);		
		for (int year=this.minimum; year<=this.maximum; year++) {
			this.yearRangeList.add(String.valueOf(year));
		}				
	}
	
	@SuppressWarnings("unchecked")
	private Context getChainContext(String visionOid, String year) throws Exception {
		String dateStr1 = year+"0101";
		String dateStr2 = year+"1230";
		Context context = new ContextBase();
		context.put("visionOid", visionOid);
		context.put("startDate", dateStr1);
		context.put("endDate", dateStr2);		
		context.put("startYearDate",  year);
		context.put("endYearDate",  year);		
		context.put("frequency", BscMeasureDataFrequency.FREQUENCY_YEAR);
		context.put("dataFor", BscConstants.MEASURE_DATA_FOR_ORGANIZATION);
		context.put("orgId", this.getFields().get("orgId"));
		context.put("empId", BscConstants.MEASURE_DATA_EMPLOYEE_FULL);
		return context;
	}		
	
	private void renderInfowindowContent() throws ServiceException, Exception {
		
		StringBuilder outStr = new StringBuilder();
		String year = this.getFields().get("year");
		String orgId = this.getFields().get("orgId");		
		List<String> visionOids = this.visionService.findForOidByKpiOrga(orgId);
		if (visionOids==null || visionOids.size()<1 ) {
			this.setPageMessage( SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA) + " , for KPIs." );
			return;
		}
		for (String visionOid : visionOids) {
			Context context = this.getChainContext(visionOid, year);
			SimpleChain chain = new SimpleChain();
			ChainResultObj resultObj = chain.getResultFromResource("kpiReportHtmlContentChain", context);	
			outStr.append( String.valueOf( resultObj.getValue() ) );
		}
		this.content = outStr.toString();
		
	}
	
	/**
	 *  bsc.regionMapViewAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG001D0006Q")
	public String execute() throws Exception {
		if ( YesNo.NO.equals(super.getGoogleMapEnable()) ) {
			//ActionContext.getContext().put("value", this.getProgramId() + ": no enable google map! can not use this feature.");			
			//return RESULT_BLANK_VALUE;
			super.setErrorMessage(this.getProgramId() + ": no enable google map! can not use this feature.");
			return ERROR;			
		}		
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
	 *  bsc.regionMapViewGetInfowindowContent.action
	 */	
	@ControllerMethodAuthority(programId="BSC_PROG001D0006Q")
	public String getInfowindowContent() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.renderInfowindowContent();
			if (!StringUtils.isBlank(this.content)) {
				forward = SUCCESS;
			}
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
		try {
			return MenuSupportUtils.getProgramName(this.getProgramId(), this.getLocaleLang());
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getProgramId() {
		return super.getActionMethodProgramId();
	}

	public List<BbOrganization> getOrganizations() {
		return organizations;
	}
	
	public int getDiscreteValues() {
		return discreteValues;
	}

	public int getMaximum() {
		return maximum;
	}

	public int getMinimum() {
		return minimum;
	}

	public List<String> getYearRangeList() {
		return yearRangeList;
	}

	public String getContent() {
		return content;
	}
	
}
