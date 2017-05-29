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
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.chain.SimpleChain;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ChainResultObj;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.bsc.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.bsc.service.IPdcaKpisService;
import com.netsteadfast.greenstep.bsc.service.IPdcaMeasureFreqService;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.bsc.service.logic.IPdcaLogicService;
import com.netsteadfast.greenstep.bsc.vo.PdcaProjectRelatedVO;
import com.netsteadfast.greenstep.po.hbm.BbPdcaKpis;
import com.netsteadfast.greenstep.po.hbm.BbPdcaMeasureFreq;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.PdcaKpisVO;
import com.netsteadfast.greenstep.vo.PdcaMeasureFreqVO;
import com.netsteadfast.greenstep.vo.PdcaVO;
import com.netsteadfast.greenstep.vo.VisionVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.PdcaReportContentQueryAction")
@Scope
public class PdcaReportContentQueryAction extends BaseJsonAction {
	private static final long serialVersionUID = -3513975229265102278L;
	protected Logger logger=Logger.getLogger(PdcaReportContentQueryAction.class);
	private IVisionService<VisionVO, BbVision, String> visionService;
	private IPdcaMeasureFreqService<PdcaMeasureFreqVO, BbPdcaMeasureFreq, String> pdcaMeasureFreqService;
	private IPdcaKpisService<PdcaKpisVO, BbPdcaKpis, String> pdcaKpisService;
	private IPdcaLogicService pdcaLogicService;
	private String message = "";
	private String success = IS_NO;
	private String body = "";
	private PdcaVO pdca = new PdcaVO();
	private String uploadOid = "";
	private PdcaProjectRelatedVO projectRelated;
	
	public PdcaReportContentQueryAction() {
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
	
	@JSON(serialize=false)
	public IPdcaMeasureFreqService<PdcaMeasureFreqVO, BbPdcaMeasureFreq, String> getPdcaMeasureFreqService() {
		return pdcaMeasureFreqService;
	}

	@Autowired
	@Resource(name="bsc.service.PdcaMeasureFreqService")
	public void setPdcaMeasureFreqService(
			IPdcaMeasureFreqService<PdcaMeasureFreqVO, BbPdcaMeasureFreq, String> pdcaMeasureFreqService) {
		this.pdcaMeasureFreqService = pdcaMeasureFreqService;
	}	
	
	@JSON(serialize=false)
	public IPdcaKpisService<PdcaKpisVO, BbPdcaKpis, String> getPdcaKpisService() {
		return pdcaKpisService;
	}

	@Autowired
	@Resource(name="bsc.service.PdcaKpisService")
	public void setPdcaKpisService(IPdcaKpisService<PdcaKpisVO, BbPdcaKpis, String> pdcaKpisService) {
		this.pdcaKpisService = pdcaKpisService;
	}
	
	@JSON(serialize=false)
	public IPdcaLogicService getPdcaLogicService() {
		return pdcaLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.PdcaLogicService")
	public void setPdcaLogicService(IPdcaLogicService pdcaLogicService) {
		this.pdcaLogicService = pdcaLogicService;
	}	
	
	private void checkFields() throws ControllerException, Exception {
		this.getCheckFieldHandler()
		.add("pdcaOid", SelectItemFieldCheckUtils.class, "Please select PDCA project!")
		.process().throwMessage();
	}
	
	@SuppressWarnings("unchecked")
	private void getContent() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		
		Context pdcaContext = new ContextBase();
		pdcaContext.put("pdcaOid", this.getFields().get("pdcaOid"));
		SimpleChain chain = new SimpleChain();
		ChainResultObj pdcaReportObj = chain.getResultFromResource("pdcaReportHtmlContentChain", pdcaContext);		
		
		List<ChainResultObj> bscReportResults = null;
		if ("true".equals(this.getFields().get("showBscReport"))) {
			bscReportResults = this.getBscReportContent( "kpiReportHtmlContentChain" );
		}
		if ( pdcaReportObj.getValue() instanceof String ) {
			this.body = String.valueOf(pdcaReportObj.getValue());
			this.pdca = (PdcaVO) pdcaContext.get("pdca");
		}
		this.message = super.defaultString(pdcaReportObj.getMessage()).trim();
		for (int i=0; bscReportResults!=null && i<bscReportResults.size(); i++) {
			ChainResultObj bscReportObj = bscReportResults.get(i);
			if ( bscReportObj.getValue() instanceof String ) {
				this.body += String.valueOf(bscReportObj.getValue());
			}
			if (!this.message.equals(bscReportObj.getMessage())) {
				if (!"".equals(this.message)) {
					this.message += super.getHtmlBr();
				}
				this.message += bscReportObj.getMessage();
			}			
		}
		if (!StringUtils.isBlank(this.body)) {
			this.success = IS_YES;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void getExcel() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		
		List<String> bscExcelFileUploadOids = new ArrayList<String>();
		List<ChainResultObj> bscReportResults = null;
		if ("true".equals(this.getFields().get("showBscReport"))) {
			bscReportResults = this.getBscReportContent( "kpiReportExcelContentChain" );
		}
		for (int i=0; bscReportResults!=null && i<bscReportResults.size(); i++) {
			ChainResultObj resultObj = bscReportResults.get(i);
			if ( resultObj.getValue() instanceof String ) {
				bscExcelFileUploadOids.add( (String)resultObj.getValue() );
			}
		}
		
		Context pdcaContext = new ContextBase();
		pdcaContext.put("pdcaOid", this.getFields().get("pdcaOid"));
		pdcaContext.put("bscExcelFileUploadOids", bscExcelFileUploadOids);
		SimpleChain chain = new SimpleChain();
		ChainResultObj resultObj = chain.getResultFromResource("pdcaReportExcelContentChain", pdcaContext);		
		this.message = resultObj.getMessage();
		if ( resultObj.getValue() instanceof String ) {
			this.uploadOid = (String)resultObj.getValue();
			this.success = IS_YES;
		}
	}
	
	private List<ChainResultObj> getBscReportContent(String chainId) throws ControllerException, AuthorityException, ServiceException, Exception {
		List<ChainResultObj> results = new ArrayList<ChainResultObj>();
		
		String pdcaOid = this.getFields().get("pdcaOid");
		
		// 先找出 bb_pdca_kpis 的最上層 vision
		List<String> visionOids = this.visionService.findForOidByPdcaOid( pdcaOid );
		
		for (String visionOid : visionOids) {
			PdcaMeasureFreqVO measureFreq = new PdcaMeasureFreqVO();
			measureFreq.setPdcaOid(pdcaOid);
			DefaultResult<PdcaMeasureFreqVO> mfResult = this.pdcaMeasureFreqService.findByUK(measureFreq);
			if (mfResult.getValue() == null) {
				throw new ServiceException( mfResult.getSystemMessage().getValue() );
			}
			measureFreq = mfResult.getValue();
			Context context = this.getBscReportChainContext(pdcaOid, visionOid, measureFreq);
			SimpleChain chain = new SimpleChain();
			ChainResultObj resultObj = chain.getResultFromResource(chainId, context);
			results.add(resultObj);
		}
		return results;
	}
	
	@SuppressWarnings("unchecked")
	private Context getBscReportChainContext(String pdcaOid, String visionOid, PdcaMeasureFreqVO measureFreq) throws Exception {
		Context context = new ContextBase();
		context.put("visionOid", visionOid);
		context.put("startDate", SimpleUtils.getStrYMD(measureFreq.getStartDate(), "/"));
		context.put("endDate", SimpleUtils.getStrYMD(measureFreq.getEndDate(), "/"));		
		context.put("startYearDate", SimpleUtils.getStrYMD(measureFreq.getStartDate(), "/"));
		context.put("endYearDate", SimpleUtils.getStrYMD(measureFreq.getEndDate(), "/"));		
		context.put("frequency", measureFreq.getFreq());
		context.put("dataFor", measureFreq.getDataType());
		context.put("orgId", measureFreq.getOrgId());
		context.put("empId", measureFreq.getEmpId());
		context.put("account", "");
		context.put("ngVer", YesNo.NO);
		
		List<String> kpiIds = new ArrayList<String>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pdcaOid", pdcaOid);
		List<BbPdcaKpis> pdcaKpisList = this.pdcaKpisService.findListByParams(paramMap);
		for (BbPdcaKpis pdcaKpi : pdcaKpisList) {
			kpiIds.add( pdcaKpi.getKpiId() );
		}
		
		context.put("kpiIds", kpiIds); // 只顯示,要顯示的KPI
		
		return context;
	}
	
	private void loadProjectRelatedData() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		this.projectRelated = this.pdcaLogicService.findProjectRelated(this.getFields().get("pdcaOid"));
		if (null != this.projectRelated && null != this.projectRelated.getProject() 
				&& (this.projectRelated.getChild().size()>0 || this.projectRelated.getParent().size()>0) ) {
			this.message = "success!";
			this.success = IS_YES;
		} else {
			this.message = "Project related: " + SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA);
		}
	}
	
	/**
	 * bsc.pdcaReportContentQuery.action
	 */
	@JSON(serialize=false)
	@ControllerMethodAuthority(programId="BSC_PROG006D0002Q")
	public String execute() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.getContent();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;
	}
	
	/**
	 * bsc.pdcaReportExcelQuery.action
	 */
	@JSON(serialize=false)
	@ControllerMethodAuthority(programId="BSC_PROG006D0002Q")	
	public String doExcel() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.getExcel();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}	
	
	/**
	 * bsc.pdcaProjectRelatedQuery.action
	 */
	@JSON(serialize=false)
	@ControllerMethodAuthority(programId="BSC_PROG006D0002Q")		
	public String doProjectRelated() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.loadProjectRelatedData();
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
	public String getBody() {
		return body;
	}

	@JSON
	public PdcaVO getPdca() {
		return pdca;
	}

	@JSON
	public String getUploadOid() {
		return uploadOid;
	}

	public void setUploadOid(String uploadOid) {
		this.uploadOid = uploadOid;
	}

	@JSON
	public PdcaProjectRelatedVO getProjectRelated() {
		return projectRelated;
	}

	public void setProjectRelated(PdcaProjectRelatedVO projectRelated) {
		this.projectRelated = projectRelated;
	}	
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
}
