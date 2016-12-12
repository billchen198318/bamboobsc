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
package com.netsteadfast.greenstep.bsc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.bsc.service.IKpiService;
import com.netsteadfast.greenstep.bsc.service.IMonitorItemScoreService;
import com.netsteadfast.greenstep.bsc.service.IObjectiveService;
import com.netsteadfast.greenstep.bsc.service.IPerspectiveService;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.po.hbm.BbKpi;
import com.netsteadfast.greenstep.po.hbm.BbMonitorItemScore;
import com.netsteadfast.greenstep.po.hbm.BbObjective;
import com.netsteadfast.greenstep.po.hbm.BbPerspective;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.po.hbm.TbSysMailHelper;
import com.netsteadfast.greenstep.service.ISysMailHelperService;
import com.netsteadfast.greenstep.util.MailClientUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.MonitorItemScoreVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.SysMailHelperVO;
import com.netsteadfast.greenstep.vo.VisionVO;

import ognl.Ognl;
import ognl.OgnlException;

public class HistoryItemScoreNoticeHandler implements java.io.Serializable {
	private static final long serialVersionUID = -2839229894386555550L;
	protected static Logger logger = Logger.getLogger(HistoryItemScoreNoticeHandler.class);
	private IMonitorItemScoreService<MonitorItemScoreVO, BbMonitorItemScore, String> monitorItemScoreService;
	private IVisionService<VisionVO, BbVision, String> visionService;
	private IPerspectiveService<PerspectiveVO, BbPerspective, String> perspectiveService; 
	private IObjectiveService<ObjectiveVO, BbObjective, String> objectiveService;
	private IKpiService<KpiVO, BbKpi, String> kpiService;	
	private ISysMailHelperService<SysMailHelperVO, TbSysMailHelper, String> sysMailHelperService;
	private List<String> toMail = new ArrayList<String>();
	private Map<String, String> visions = new HashMap<String, String>();
	private Map<String, String> perspectives = new HashMap<String, String>();
	private Map<String, String> objectives = new HashMap<String, String>();
	private Map<String, String> kpis = new HashMap<String, String>();
	private String dateStr = SimpleUtils.getStrYMD("");
	private String frequency = BscMeasureDataFrequency.FREQUENCY_YEAR;
	private List<String> employees = new ArrayList<String>();
	private List<String> organizations = new ArrayList<String>();
	
	@SuppressWarnings("unchecked")
	public HistoryItemScoreNoticeHandler() {
		super();
		monitorItemScoreService = (IMonitorItemScoreService<MonitorItemScoreVO, BbMonitorItemScore, String>) AppContext.getBean("bsc.service.MonitorItemScoreService");
		visionService = (IVisionService<VisionVO, BbVision, String>) AppContext.getBean("bsc.service.VisionService");
		perspectiveService = (IPerspectiveService<PerspectiveVO, BbPerspective, String>) AppContext.getBean("bsc.service.PerspectiveService");
		objectiveService = (IObjectiveService<ObjectiveVO, BbObjective, String>) AppContext.getBean("bsc.service.ObjectiveService");
		kpiService = (IKpiService<KpiVO, BbKpi, String>) AppContext.getBean("bsc.service.KpiService");	
		sysMailHelperService = (ISysMailHelperService<SysMailHelperVO, TbSysMailHelper, String>) AppContext.getBean("core.service.SysMailHelperService");			
	}
	
	public void action() throws ServiceException, Exception {
		if (toMail.size()<1) {
			return;
		}
		if (!MailClientUtils.getEnable()) {
			logger.warn("MailClientUtils no enable.");
			return;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("frequency", frequency);
		paramMap.put("dateVal", dateStr);
		List<BbMonitorItemScore> monitorItemScores = monitorItemScoreService.findListByParams(paramMap);
		this.clearNoNeedItem(monitorItemScores);
		if (null == monitorItemScores || monitorItemScores.size() < 1) {
			logger.warn("No history monitor item score data.");
			return;
		}		
		StringBuilder outContent = new StringBuilder();
		this.createContent(monitorItemScores, outContent);
		if (null == outContent || outContent.length() < 1) {
			logger.warn("No history monitor mail content data.");
			return;
		}
		SysMailHelperVO sysMailHelper = new SysMailHelperVO();
		sysMailHelper.setSubject("bambooBSC monitor item score mail - frequency: " + BscMeasureDataFrequency.getFrequencyMap(false).get(frequency) 
				+ " , date: " + SimpleUtils.getStrYMD(dateStr, "/"));
		sysMailHelper.setText( outContent.toString().getBytes() );
		sysMailHelper.setMailFrom( MailClientUtils.getDefaultFrom() );
		StringBuilder to = new StringBuilder();
		for (String mail : toMail) {
			to.append(mail).append(";");
		}
		sysMailHelper.setMailTo(to.toString());
		sysMailHelperService.saveObject(sysMailHelper);
	}
	
	private void createContent(List<BbMonitorItemScore> monitorItemScores, StringBuilder out) throws ServiceException, Exception {
		// create mail content
		
	}
	
	/*
	public static void main(String args[]) throws Exception {
		BbMonitorItemScore monitorItemScore = new BbMonitorItemScore();
		monitorItemScore.setScore("59");
		String value = String.valueOf( Ognl.getValue("score >= 60", monitorItemScore) );
		System.out.println(value);
	}
	*/
	
	private boolean isRule(BbMonitorItemScore monitorItemScore, String expression) {
		boolean status = false;
		try {
			String value = String.valueOf( Ognl.getValue(expression, monitorItemScore) );
			if ("true".equals(value)) {
				status = true;
			}
		} catch (OgnlException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	private void clearNoNeedItem(List<BbMonitorItemScore> monitorItemScores) {
		if (null == monitorItemScores) {
			return;
		}
		for (String orgId : organizations) {
			for (Iterator<BbMonitorItemScore> iterator = monitorItemScores.iterator(); iterator.hasNext(); ) {
				BbMonitorItemScore itemScore = iterator.next();
				if (!orgId.equals(itemScore.getOrgId())) {
					iterator.remove();		
				}
			}
		}
		for (String empId : employees) {
			for (Iterator<BbMonitorItemScore> iterator = monitorItemScores.iterator(); iterator.hasNext(); ) {
				BbMonitorItemScore itemScore = iterator.next();
				if (!empId.equals(itemScore.getOrgId())) {
					iterator.remove();		
				}
			}
		}		
		this.clearNoNeedItem(MonitorItemType.VISION, visions, monitorItemScores);
		this.clearNoNeedItem(MonitorItemType.PERSPECTIVES, perspectives, monitorItemScores);
		this.clearNoNeedItem(MonitorItemType.STRATEGY_OF_OBJECTIVES, objectives, monitorItemScores);
		this.clearNoNeedItem(MonitorItemType.KPI, kpis, monitorItemScores);
	}
	
	private void clearNoNeedItem(String itemType, Map<String, String> typeDataMap, List<BbMonitorItemScore> monitorItemScores) {
		for (Entry<String, String> entry : typeDataMap.entrySet()) {
			for (Iterator<BbMonitorItemScore> iterator = monitorItemScores.iterator(); iterator.hasNext(); ) {
				BbMonitorItemScore itemScore = iterator.next();
				if (itemType.equals(itemScore.getItemType()) && !entry.getKey().equals(itemScore.getItemId())) {
					iterator.remove();
					continue;
				}
				if (!this.isRule(itemScore, entry.getValue())) {
					iterator.remove();
					continue;		
				}
			}
		}		
	}
	
	public HistoryItemScoreNoticeHandler employee(String empId) {
		if (StringUtils.isBlank(empId) || this.employees.contains(empId)) {
			return this;
		}
		this.employees.add(empId);
		return this;
	}
	
	public HistoryItemScoreNoticeHandler organization(String orgId) {
		if (StringUtils.isBlank(orgId) || this.organizations.contains(orgId)) {
			return this;
		}
		this.organizations.add(orgId);
		return this;
	}	
	
	public HistoryItemScoreNoticeHandler frequency(String frequency) {
		this.frequency = frequency;
		if (BscMeasureDataFrequency.getFrequencyMap(false).get(frequency) == null) {
			this.frequency = BscMeasureDataFrequency.FREQUENCY_YEAR;
		}
		return this;
	}
	
	public HistoryItemScoreNoticeHandler date(String dateStr) {
		this.dateStr = dateStr;
		if (!SimpleUtils.isDate(dateStr)) {
			this.dateStr = SimpleUtils.getStrYMD("");
		}
		return this;
	}
	
	public HistoryItemScoreNoticeHandler vision(String visionId, String ruleExpression) {
		if (StringUtils.isBlank(visionId) || this.visions.get(visionId)!=null) {
			return this;
		}
		this.visions.put(visionId, StringUtils.defaultString(ruleExpression).trim());
		return this;
	}	
	
	public HistoryItemScoreNoticeHandler perspective(String perspectiveId, String ruleExpression) {
		if (StringUtils.isBlank(perspectiveId) || this.perspectives.get(perspectiveId)!=null) {
			return this;
		}
		this.perspectives.put(perspectiveId, StringUtils.defaultString(ruleExpression).trim());
		return this;
	}
	
	public HistoryItemScoreNoticeHandler objective(String objectiveId, String ruleExpression) {
		if (StringUtils.isBlank(objectiveId) || this.objectives.get(objectiveId)!=null) {
			return this;
		}
		this.objectives.put(objectiveId, StringUtils.defaultString(ruleExpression).trim());
		return this;
	}
	
	public HistoryItemScoreNoticeHandler kpi(String kpiId, String ruleExpression) {
		if (StringUtils.isBlank(kpiId) || this.kpis.get(kpiId)!=null) {
			return this;
		}
		this.kpis.put(kpiId, StringUtils.defaultString(ruleExpression).trim());
		return this;
	}	
	
	public HistoryItemScoreNoticeHandler vision(String visionId) {
		return this.vision(visionId, "null != score");
	}	
	
	public HistoryItemScoreNoticeHandler perspective(String perspectiveId) {
		return this.perspective(perspectiveId, "null != score");
	}
	
	public HistoryItemScoreNoticeHandler objective(String objectiveId) {
		return this.objective(objectiveId, "null != score");
	}
	
	public HistoryItemScoreNoticeHandler kpi(String kpiId) {
		return this.kpi(kpiId, "null != score");
	}		
	
	public HistoryItemScoreNoticeHandler to(String email) {
		if (StringUtils.isBlank(email) || this.toMail.contains(email) || email.indexOf("@") == -1 ) {
			return this;
		}
		this.toMail.add(email);
		return this;
	}
	
}
