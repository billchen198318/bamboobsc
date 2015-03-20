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
import java.util.List;
import java.util.Map;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.bsc.model.BscSwotCode;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.service.IPerspectiveService;
import com.netsteadfast.greenstep.bsc.service.ISwotService;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.bsc.vo.SwotDataVO;
import com.netsteadfast.greenstep.bsc.vo.SwotIssuesVO;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.po.hbm.BbPerspective;
import com.netsteadfast.greenstep.po.hbm.BbSwot;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.SwotVO;
import com.netsteadfast.greenstep.vo.VisionVO;

public class SwotDataProvideCommand extends BaseChainCommandSupport implements Command {
	private IVisionService<VisionVO, BbVision, String> visionService;
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private ISwotService<SwotVO, BbSwot, String> swotService; 
	private IPerspectiveService<PerspectiveVO, BbPerspective, String> perspectiveService;
	
	@SuppressWarnings("unchecked")
	public SwotDataProvideCommand() {
		super();
		visionService = (IVisionService<VisionVO, BbVision, String>)AppContext.getBean("bsc.service.VisionService");
		organizationService = (IOrganizationService<OrganizationVO, BbOrganization, String>)
				AppContext.getBean("bsc.service.OrganizationService");
		swotService = (ISwotService<SwotVO, BbSwot, String>)AppContext.getBean("bsc.service.SwotService");
		perspectiveService = (IPerspectiveService<PerspectiveVO, BbPerspective, String>)
				AppContext.getBean("bsc.service.PerspectiveService");
	}

	@Override
	public boolean execute(Context context) throws Exception {
		String visionOid = (String)context.get("visionOid");
		String organizationOid = (String)context.get("organizationOid");
		VisionVO vision = this.findVision(visionOid);
		OrganizationVO organization = this.findOrganization(organizationOid);
		if ( StringUtils.isBlank(vision.getVisId()) || StringUtils.isBlank(organization.getOrgId()) ) {
			this.setMessage(context, SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
			return false;
		}		
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> orderParams = new HashMap<String, String>();
		params.put("visId", vision.getVisId());
		orderParams.put("perId", "asc");
		List<BbPerspective> perspectives = this.perspectiveService.findListByParams(params, null, orderParams);
		if (perspectives==null || perspectives.size()<1) {
			this.setMessage(context, SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA));
			return false;
		}		
		params.put("orgId", organization.getOrgId());		
		List<BbSwot> swots = this.swotService.findListByParams(params, null, orderParams);
		SwotDataVO swotData = new SwotDataVO();
		swotData.setVision(vision);
		swotData.setOrganization(organization);
		swotData.setContents(swots);
		swotData.setPerspectives(perspectives);
		this.initIssues(swotData, vision.getVisId(), organization.getOrgId());
		this.setResult(context, swotData);
		return false;
	}
	
	private void initIssues(SwotDataVO swotData, String visId, String orgId) throws Exception {
		String sep = BscConstants.SWOT_TEXT_INPUT_ID_SEPARATE;
		for (BbPerspective perspective : swotData.getPerspectives()) {
			SwotIssuesVO issues = new SwotIssuesVO();
			BbSwot sMain = null;
			BbSwot wMain = null;
			BbSwot oMain = null;
			BbSwot tMain = null;
			String sStr = "";
			String wStr = "";
			String oStr = "";
			String tStr = "";
			String sId = "BSC_PROG002D0008Q";
			String wId = "BSC_PROG002D0008Q";
			String oId = "BSC_PROG002D0008Q";
			String tId = "BSC_PROG002D0008Q";
			for (String type : BscSwotCode.CODES) {			
				if (BscSwotCode.STRENGTHS_CODE.equals(type)) {
					sMain = this.getIssuesMain(perspective.getPerId(), type, swotData.getContents());
					if (sMain!=null) {
						sStr = StringUtils.defaultString( sMain.getIssues() );
					}
					sId += sep + type + sep + visId + sep + perspective.getPerId() + sep + orgId;
				}
				if (BscSwotCode.WEAKNESSES_CODE.equals(type)) {
					wMain = this.getIssuesMain(perspective.getPerId(), type, swotData.getContents());
					if (wMain!=null) {
						wStr = StringUtils.defaultString( wMain.getIssues() );
					}
					wId += sep + type + sep + visId + sep + perspective.getPerId() + sep + orgId;
				}
				if (BscSwotCode.OPPORTUNITIES_CODE.equals(type)) {
					oMain = this.getIssuesMain(perspective.getPerId(), type, swotData.getContents());
					if (oMain!=null) {
						oStr = StringUtils.defaultString( oMain.getIssues() );
					}
					oId += sep + type + sep + visId + sep + perspective.getPerId() + sep + orgId;
				}
				if (BscSwotCode.THREATS_CODE.equals(type)) {
					tMain = this.getIssuesMain(perspective.getPerId(), type, swotData.getContents());
					if (tMain!=null) {
						tStr = StringUtils.defaultString( tMain.getIssues() );
					}					
					tId += sep + type + sep + visId + sep + perspective.getPerId() + sep + orgId;
				}
			}
			issues.setPerspectiveName(perspective.getName());
			issues.setStrengthsMain(sMain);
			issues.setStrengths(sStr);
			issues.setWeaknessesMain(wMain);
			issues.setWeaknesses(wStr);
			issues.setOpportunitiesMain(oMain);
			issues.setOpportunities(oStr);
			issues.setThreatsMain(tMain);
			issues.setThreats(tStr);
			issues.setStrengthsTextId(sId);
			issues.setWeaknessesTextId(wId);
			issues.setOpportunitiesTextId(oId);
			issues.setThreatsTextId(tId);
			swotData.getIssues().add(issues);
		}
	}
	
	private BbSwot getIssuesMain(String perId, String type, List<BbSwot> swotList) throws Exception {
		BbSwot data = null;
		for (BbSwot swot : swotList) {
			if ( swot.getPerId().equals(perId) && swot.getType().equals(type) ) {
				data = swot;
			}
		}
		return data;
	}
	
	private VisionVO findVision(String oid) throws Exception {
		VisionVO vision = new VisionVO();
		vision.setOid(oid);
		DefaultResult<VisionVO> result = this.visionService.findObjectByOid(vision);
		if ( result.getValue() == null ) {
			return vision;
		}
		vision = result.getValue();
		return vision;
	}
	
	private OrganizationVO findOrganization(String oid) throws Exception {
		OrganizationVO organization = new OrganizationVO();
		organization.setOid(oid);
		DefaultResult<OrganizationVO> result = this.organizationService.findObjectByOid(organization);
		if (result.getValue()==null) {
			return organization;
		}
		organization = result.getValue();
		return organization;
	}

}
