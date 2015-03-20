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

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.bsc.service.ISwotReportDtlService;
import com.netsteadfast.greenstep.bsc.service.ISwotReportMstService;
import com.netsteadfast.greenstep.bsc.vo.SwotDataVO;
import com.netsteadfast.greenstep.bsc.vo.SwotIssuesVO;
import com.netsteadfast.greenstep.po.hbm.BbSwotReportDtl;
import com.netsteadfast.greenstep.po.hbm.BbSwotReportMst;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.SwotReportDtlVO;
import com.netsteadfast.greenstep.vo.SwotReportMstVO;

public class SwotReportPdfDataCommand extends BaseChainCommandSupport implements Command {
	private ISwotReportMstService<SwotReportMstVO, BbSwotReportMst, String> swotReportMstService;
	private ISwotReportDtlService<SwotReportDtlVO, BbSwotReportDtl, String> swotReportDtlService;
	
	@SuppressWarnings("unchecked")
	public SwotReportPdfDataCommand() {
		super();
		swotReportMstService = (ISwotReportMstService<SwotReportMstVO, BbSwotReportMst, String>)
				AppContext.getBean("bsc.service.SwotReportMstService");
		swotReportDtlService = (ISwotReportDtlService<SwotReportDtlVO, BbSwotReportDtl, String>)
				AppContext.getBean("bsc.service.SwotReportDtlService");
	}

	@Override
	public boolean execute(Context context) throws Exception {
		if (this.getResult(context)==null || !(this.getResult(context) instanceof SwotDataVO) ) {
			return false;
		}		
		SwotDataVO swotData = (SwotDataVO)this.getResult(context);
		String reportId = this.createReportData(swotData);
		if (!StringUtils.isBlank(reportId)) {
			this.setResult(context, reportId);
		} else {
			this.setMessage(context, "create report data fail!");
		}
		return false;
	}
	
	private String createReportData(SwotDataVO swotData) throws Exception {
		if (swotData.getIssues()==null || swotData.getIssues().size()<1) {
			return "";
		}
		String reportId = SimpleUtils.getUUIDStr();
		SwotReportMstVO reportMst = new SwotReportMstVO();
		reportMst.setReportId(reportId);
		reportMst.setVisionTitle(swotData.getVision().getTitle());
		reportMst.setOrgName(swotData.getOrganization().getName());
		this.swotReportMstService.saveIgnoreUK(reportMst);
		for (int i=0; i<swotData.getIssues().size(); i++) {
			SwotIssuesVO issuesData = swotData.getIssues().get(i);
			SwotReportDtlVO reportDtl = new SwotReportDtlVO();
			reportDtl.setReportId(reportId);
			reportDtl.setSeq(i);
			reportDtl.setLabel(issuesData.getPerspectiveName());
			reportDtl.setIssues1(issuesData.getStrengths());
			reportDtl.setIssues2(issuesData.getWeaknesses());
			reportDtl.setIssues3(issuesData.getOpportunities());
			reportDtl.setIssues4(issuesData.getThreats());
			this.swotReportDtlService.saveIgnoreUK(reportDtl);
		}
		return reportId;
	}

}
