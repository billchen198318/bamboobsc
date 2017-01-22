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
package com.netsteadfast.greenstep.bsc.command;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.util.BscReportPropertyUtils;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.util.TemplateUtils;
import com.netsteadfast.greenstep.vo.OrganizationVO;

public class OrganizationReportBodyCommand extends BaseChainCommandSupport implements Command {
	private static final String templateResource = "META-INF/resource/organization-report-body.ftl";
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	
	@SuppressWarnings("unchecked")
	public OrganizationReportBodyCommand() {
		super();
		organizationService = (IOrganizationService<OrganizationVO, BbOrganization, String>)
				AppContext.getBean("bsc.service.OrganizationService");		
	}
	
	@Override
	public boolean execute(Context context) throws Exception {
		if (this.getResult(context)==null || !(this.getResult(context) instanceof BscStructTreeObj) ) {
			return false;
		}
		String dateType = (String)context.get("dateType");
		String orgId = (String)context.get("orgId");
		BscStructTreeObj treeObj = (BscStructTreeObj)this.getResult(context);
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("treeObj", treeObj);
		parameter.put("dateType", dateType);
		parameter.put("year", (String)context.get("startYearDate"));
		parameter.put("departmentName", " ");
		parameter.put("departmentOid", " ");
		parameter.put("total", 0.0f);
		if ( context.get("total") != null && context.get("total") instanceof Float ) {
			parameter.put("total", context.get("total") );
		}
		OrganizationVO organization = new OrganizationVO();
		organization.setOrgId(orgId);
		DefaultResult<OrganizationVO> result = this.organizationService.findByUK(organization);
		if ( result.getValue() != null ) {
			organization = result.getValue();
			parameter.put("departmentName", organization.getName() );
			parameter.put("departmentOid", organization.getOid() );
		}		
		this.fillReportProperty(parameter);
		String content = TemplateUtils.processTemplate(
				"resourceTemplate", 
				OrganizationReportBodyCommand.class.getClassLoader(), 
				templateResource, 
				parameter);
		this.setResult(context, content);			
		return false;
	}
	
	private void fillReportProperty(Map<String, Object> parameter) throws ServiceException, Exception {
		BscReportPropertyUtils.loadData();
		parameter.put("perspectiveTitle", BscReportPropertyUtils.getPerspectiveTitle() );
		parameter.put("objectiveTitle", BscReportPropertyUtils.getObjectiveTitle() );
		parameter.put("kpiTitle", BscReportPropertyUtils.getKpiTitle() );
	}		

}
