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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.chain.SimpleChain;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.bsc.util.BscReportSupportUtils;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.util.JFreeChartDataMapperUtils;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

@ControllerAuthority(check=false)
@Controller("bsc.web.controller.RegionMapRelationKpisAction")
@Scope
public class RegionMapRelationKpisAction extends BaseJsonAction {
	private static final long serialVersionUID = 7019465947133316161L;
	protected Logger logger=Logger.getLogger(RegionMapRelationKpisAction.class);
	private IVisionService<VisionVO, BbVision, String> visionService; 
	private String message = "";
	private String success = IS_NO;
	private List<Map<String, Object>> relationKpis = new ArrayList<Map<String, Object>>();
	private String content = "";
	private List<String> barUploadOids = new ArrayList<String>();
	private List<String> pieUploadOids = new ArrayList<String>();
	
	public RegionMapRelationKpisAction() {
		super();
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
	
	private void renderTableContent() throws ControllerException, AuthorityException, ServiceException, Exception {
		
		String year = this.getFields().get("year");
		String orgId = this.getFields().get("orgId");		
		List<String> visionOids = this.visionService.findForOidByKpiOrga(orgId);
		if (visionOids==null || visionOids.size()<1 ) {
			this.message = SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA) + " , for KPIs.";
			return;
		}
		for (String visionOid : visionOids) {
			Context context = this.getChainContext(visionOid, year);
			SimpleChain chain = new SimpleChain();
			chain.getResultFromResource("performanceScoreChain", context);			
			if (context.get("treeObj")==null) {
				continue;
			}
			BscStructTreeObj treeObj = (BscStructTreeObj)context.get("treeObj");
			this.fillRelationKpis(treeObj);
			this.fillCharts(treeObj);
		}		
		this.success = IS_YES;
		
	}
	
	private void fillRelationKpis(BscStructTreeObj treeObj) throws Exception {
		List<VisionVO> visions = treeObj.getVisions();
		for (VisionVO vision : visions) {
			for (PerspectiveVO perspective : vision.getPerspectives()) {
				for (ObjectiveVO objective : perspective.getObjectives()) {
					for (KpiVO kpi : objective.getKpis()) {
						boolean isFound = false;
						for (Map<String, Object> founds : this.relationKpis) {
							if ( kpi.getId().equals( founds.get("id") ) ) {
								isFound = true;
							}
						}
						if (isFound) {
							continue;
						}
						Map<String, Object> paramMap = new HashMap<String, Object>();
						paramMap.put("id", kpi.getId());
						paramMap.put("name", kpi.getName());
						paramMap.put("score", NumberUtils.toFloat( BscReportSupportUtils.parse2(kpi.getScore()) ) );
						paramMap.put("bgColor", kpi.getBgColor());
						paramMap.put("fontColor", kpi.getFontColor());
						paramMap.put("target", kpi.getTarget());
						paramMap.put("min", kpi.getMin());
						this.relationKpis.add(paramMap);
					}
				}
			}
		}
	}
	
	private void fillCharts(BscStructTreeObj treeObj) throws Exception {
		List<VisionVO> visions = treeObj.getVisions();
		for (VisionVO vision : visions) {
			List<String> names = new LinkedList<String>();
			List<Float> values = new LinkedList<Float>();
			List<String> colors = new LinkedList<String>();
			for (PerspectiveVO perspective : vision.getPerspectives()) {
				names.add( perspective.getName() + "(" + BscReportSupportUtils.parse2(perspective.getScore()) + ")" );
				values.add(perspective.getScore());
				colors.add(perspective.getBgColor());
			}
			this.barUploadOids.add(
					JFreeChartDataMapperUtils.createBarData(
							vision.getTitle(), 
							"Score", 
							"", 
							names, 
							values, 
							colors,
							480,
							280,
							false)
			);			
			this.pieUploadOids.add(
					JFreeChartDataMapperUtils.createPieData(
							vision.getTitle(), 
							names, 
							values, 
							colors, 
							480, 
							280)
			);
		}
	}
	
	/**
	 * bsc.regionMapRelationKpisAction.action
	 * 
	 * @return
	 * @throws Exception
	 */	
	@JSON(serialize=false) // *** 不加 @JSON(serialize=false) 會被觸發兩次 ***
	@ControllerMethodAuthority(programId="BSC_PROG001D0006Q")
	public String getKpis() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.renderTableContent();
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
	public List<Map<String, Object>> getRelationKpis() {
		return relationKpis;
	}

	@JSON
	public String getContent() {
		return content;
	}

	@JSON
	public List<String> getBarUploadOids() {
		return barUploadOids;
	}

	@JSON
	public List<String> getPieUploadOids() {
		return pieUploadOids;
	}
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
		
}
