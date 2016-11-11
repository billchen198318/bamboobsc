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
package com.netsteadfast.greenstep.bsc.webservice.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ChainResultObj;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.base.service.logic.BscBaseLogicServiceCommonSupport;
import com.netsteadfast.greenstep.bsc.command.KpiReportBodyCommand;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.bsc.util.BscReportSupportUtils;
import com.netsteadfast.greenstep.bsc.util.PerformanceScoreChainUtils;
import com.netsteadfast.greenstep.bsc.vo.BscApiServiceResponse;
import com.netsteadfast.greenstep.bsc.webservice.ApiWebService;
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbMeasureData;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.sys.WsAuthenticateUtils;
import com.netsteadfast.greenstep.util.ApplicationSiteUtils;
import com.netsteadfast.greenstep.util.HostUtils;
import com.netsteadfast.greenstep.util.JFreeChartDataMapperUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.DateRangeScoreVO;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;
import com.thoughtworks.xstream.XStream;

@Service("bsc.webservice.ApiWebService")
@WebService
@SOAPBinding
@Path("/")
@Produces("application/json")
public class ApiWebServiceImpl implements ApiWebService {
	private static final int MeterChart_width = 320;
	private static final int MeterChart_height = 280;	
    private WebServiceContext webServiceContext;
	
	public WebServiceContext getWebServiceContext() {
		return webServiceContext;
	}

	@Autowired
	@Resource(name="webServiceContext")	
	public void setWebServiceContext(WebServiceContext webServiceContext) {
		this.webServiceContext = webServiceContext;
	}
	
	private int getMeterChartLowerBound(float score, float min) {
		if (score < min) {
			return (int)score;
		}
		return (int)min;
	}
	
	private int getMeterChartUpperBound(float score, float target) {
		if (score > target) {
			return (int)score;
		}
		return (int)target;
	}
	
	private void processForScorecard(
			BscApiServiceResponse responseObj, 
			HttpServletRequest request,
			String visionOid, String startDate, String endDate, String startYearDate, String endYearDate, String frequency, 
			String dataFor, String measureDataOrganizationOid, String measureDataEmployeeOid,
			String contentFlag) throws ServiceException, Exception {
		
		org.apache.commons.chain.Context context = PerformanceScoreChainUtils.getContext(
				visionOid, startDate, endDate, startYearDate, endYearDate, frequency, dataFor, measureDataOrganizationOid, measureDataEmployeeOid);			
		ChainResultObj result = PerformanceScoreChainUtils.getResult(context);			
		if (result.getValue() == null || ( (BscStructTreeObj)result.getValue() ).getVisions() == null || ( (BscStructTreeObj)result.getValue() ).getVisions().size() == 0) {
			return;
		}		
		String meterChartBaseUrl = ""; // 給 Meter Chart 用的
		String barChartBaseUrl = ""; // 給 BAR Chart 用的
		String pieChartBaseUrl = ""; // 給 PIE Chart 用的
		String url = ""; // 給 HTML 報表用的
		if (request != null) {
			url = ApplicationSiteUtils.getBasePath(Constants.getSystem(), request);
		} else {
			url = HostUtils.getHostAddress() + ":" + HostUtils.getHttpPort() + "/" + ApplicationSiteUtils.getContextPath(Constants.getSystem());
		}
		if (!url.endsWith("/")) {
			url += "/";
		}		
		meterChartBaseUrl = url + "bsc.commonMeterChartAction.action";		
		barChartBaseUrl = url + "bsc.commonBarChartAction.action";
		pieChartBaseUrl = url + "bsc.commonPieChartAction.action";
		BscStructTreeObj resultObj = (BscStructTreeObj)result.getValue();
		KpiReportBodyCommand reportBody = new KpiReportBodyCommand();
		reportBody.execute(context);
		Object htmlBody = reportBody.getResult(context);
		if (htmlBody != null && htmlBody instanceof String) {
			String htmlUploadOid = UploadSupportUtils.create(
					Constants.getSystem(), UploadTypes.IS_TEMP, false, String.valueOf(htmlBody).getBytes(), "KPI-HTML-REPORT.html");	
			url += "bsc.printContentAction.action?oid=" + htmlUploadOid;
			responseObj.setHtmlBodyUrl(url);
		}
		VisionVO visionObj = resultObj.getVisions().get(0);
		// 產生 Meter Chart 資料
		for (PerspectiveVO perspective : visionObj.getPerspectives()) {
			String perspectiveMeterChartOid = JFreeChartDataMapperUtils.createMeterData(
					perspective.getName(), 
					perspective.getScore(), 
					this.getMeterChartLowerBound(perspective.getScore(), perspective.getMin()), 
					this.getMeterChartUpperBound(perspective.getScore(), perspective.getTarget()), 
					MeterChart_width, 
					MeterChart_height);
			responseObj.getPerspectivesMeterChartUrl().add( meterChartBaseUrl + "?oid=" + perspectiveMeterChartOid );
			for (ObjectiveVO objective : perspective.getObjectives()) {
				String objectiveMeterChartOid = JFreeChartDataMapperUtils.createMeterData(
						objective.getName(), 
						(int)objective.getScore(), 
						this.getMeterChartLowerBound(objective.getScore(), objective.getMin()), 
						this.getMeterChartUpperBound(objective.getScore(), objective.getTarget()), 
						MeterChart_width, 
						MeterChart_height);
				responseObj.getObjectivesMeterChartUrl().add( meterChartBaseUrl + "?oid=" + objectiveMeterChartOid );
				for (KpiVO kpi : objective.getKpis()) {
					String kpiMeterChartOid = JFreeChartDataMapperUtils.createMeterData(
							kpi.getName(), 
							kpi.getScore(), 
							this.getMeterChartLowerBound(kpi.getScore(), kpi.getMin()), 
							this.getMeterChartUpperBound(kpi.getScore(), kpi.getTarget()), 
							MeterChart_width, 
							MeterChart_height);
					responseObj.getKpisMeterChartUrl().add( meterChartBaseUrl + "?oid=" + kpiMeterChartOid );
				}
			}
		}
		
		// 產生 Perspectives Bar/Pie chart
		String barUploadOid = "";
		String pieUploadOid = "";
		List<String> names = new LinkedList<String>();
		List<Float> values = new LinkedList<Float>();
		List<String> colors = new LinkedList<String>();
		for (PerspectiveVO perspective : visionObj.getPerspectives()) {
			names.add( perspective.getName() + "(" + BscReportSupportUtils.parse2(perspective.getScore()) + ")" );
			values.add(perspective.getScore());
			colors.add(perspective.getBgColor());
		}
		barUploadOid = JFreeChartDataMapperUtils.createBarData(
				visionObj.getTitle(), 
				"Score", 
				"", 
				names, 
				values, 
				colors,
				480,
				280,
				false);	
		pieUploadOid = JFreeChartDataMapperUtils.createPieData(
				visionObj.getTitle(), 
				names, 
				values, 
				colors, 
				480, 
				280);
		responseObj.setPieChartUrl( pieChartBaseUrl + "?oid=" + pieUploadOid );
		responseObj.setBarChartUrl( barChartBaseUrl + "?oid=" + barUploadOid );
		
		PerformanceScoreChainUtils.clearExpressionContentOut(visionObj);
		responseObj.setSuccess(YesNo.YES);
		if (YesNo.YES.equals(contentFlag)) {
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonData = objectMapper.writeValueAsString(visionObj);	
			XStream xstream = new XStream();
			xstream.setMode(XStream.NO_REFERENCES);
			xstream.alias("vision", VisionVO.class);
			xstream.alias("perspective", PerspectiveVO.class);
			xstream.alias("objective", ObjectiveVO.class);
			xstream.alias("kpi", KpiVO.class);
			xstream.alias("measureData", BbMeasureData.class);
			xstream.alias("dateRangeScore", DateRangeScoreVO.class);
			xstream.alias("employee", EmployeeVO.class);
			xstream.alias("organization", OrganizationVO.class);
			String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + xstream.toXML(visionObj);
			responseObj.setOutJsonData(jsonData);
			responseObj.setOutXmlData(xmlData);			
		}
	}
	
	/**
	 * SOAP 請使用 SoapUI 來測試
	 * ==================================================================================
		<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:web="http://webservice.bsc.greenstep.netsteadfast.com/">
		   <soapenv:Header/>
		   <soapenv:Body>
		   
		      <web:getScorecard1>
		      
		         <visionOid>1089abb5-3faf-445d-88ff-cd7690ac6743</visionOid>
		         <startDate></startDate>
		         <endDate></endDate>
		         <startYearDate>2015</startYearDate>
		         <endYearDate>2016</endYearDate>
		         <frequency>6</frequency>
		         <dataFor>all</dataFor>
		         <measureDataOrganizationOid></measureDataOrganizationOid>
		         <measureDataEmployeeOid></measureDataEmployeeOid>
		         <contentFlag></contentFlag>
		         
		      </web:getScorecard1>
		      
		   </soapenv:Body>
		</soapenv:Envelope>
	 * ==================================================================================
	 * 
	 * 
	 * REST 範例:
	 * curl -i -X GET "http://127.0.0.1:8080/gsbsc-web/services/jaxrs/scorecard1?visionOid=1089abb5-3faf-445d-88ff-cd7690ac6743&startDate=&endDate=&startYearDate=2015&endYearDate=2016&frequency=6&dataFor=all&measureDataOrganizationOid=&measureDataEmployeeOid=&contentFlag="
	 * 
	 */
	@WebMethod
	@GET
	@Path("/scorecard1/")	
	@Override
	public BscApiServiceResponse getScorecard1(
			@WebParam(name="visionOid") @QueryParam("visionOid") String visionOid, 
			@WebParam(name="startDate") @QueryParam("startDate") String startDate, 
			@WebParam(name="endDate") @QueryParam("endDate") String endDate, 
			@WebParam(name="startYearDate") @QueryParam("startYearDate") String startYearDate, 
			@WebParam(name="endYearDate") @QueryParam("endYearDate") String endYearDate, 
			@WebParam(name="frequency") @QueryParam("frequency") String frequency, 
			@WebParam(name="dataFor") @QueryParam("dataFor") String dataFor, 
			@WebParam(name="measureDataOrganizationOid") @QueryParam("measureDataOrganizationOid") String measureDataOrganizationOid, 
			@WebParam(name="measureDataEmployeeOid") @QueryParam("measureDataEmployeeOid") String measureDataEmployeeOid,
			@WebParam(name="contentFlag") @QueryParam("contentFlag") String contentFlag) throws Exception {
		
		HttpServletRequest request = null;
		if (this.getWebServiceContext() != null && this.getWebServiceContext().getMessageContext() != null) {
			request = (HttpServletRequest) this.getWebServiceContext().getMessageContext().get(MessageContext.SERVLET_REQUEST);
		}
		Subject subject = null;
		BscApiServiceResponse responseObj = new BscApiServiceResponse();
		responseObj.setSuccess( YesNo.NO );
		try {	
			subject = WsAuthenticateUtils.login();			
			this.processForScorecard(
					responseObj, 
					request,
					visionOid, startDate, endDate, startYearDate, endYearDate, frequency, dataFor, measureDataOrganizationOid, measureDataEmployeeOid, contentFlag);
		} catch (Exception e) {
			responseObj.setMessage( e.getMessage() );
		} finally {
			if (!YesNo.YES.equals(responseObj.getSuccess())) {
				responseObj.setMessage( SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA) );
			}	
			WsAuthenticateUtils.logout(subject);			
		}
		subject = null;
		return responseObj;
	}
	
	@WebMethod
	@GET
	@Path("/scorecard2/")	
	@Override
	public BscApiServiceResponse getScorecard2(
			@WebParam(name="visionId") @QueryParam("visionId") String visionId, 
			@WebParam(name="startDate") @QueryParam("startDate") String startDate, 
			@WebParam(name="endDate") @QueryParam("endDate") String endDate, 
			@WebParam(name="startYearDate") @QueryParam("startYearDate") String startYearDate, 
			@WebParam(name="endYearDate") @QueryParam("endYearDate") String endYearDate, 
			@WebParam(name="frequency") @QueryParam("frequency") String frequency, 
			@WebParam(name="dataFor") @QueryParam("dataFor") String dataFor, 
			@WebParam(name="measureDataOrganizationId") @QueryParam("measureDataOrganizationId") String measureDataOrganizationId, 
			@WebParam(name="measureDataEmployeeId") @QueryParam("measureDataEmployeeId") String measureDataEmployeeId,
			@WebParam(name="contentFlag") @QueryParam("contentFlag") String contentFlag) throws Exception {
		
		HttpServletRequest request = null;
		if (this.getWebServiceContext() != null && this.getWebServiceContext().getMessageContext() != null) {
			request = (HttpServletRequest) this.getWebServiceContext().getMessageContext().get(MessageContext.SERVLET_REQUEST);
		}
		Subject subject = null;
		BscApiServiceResponse responseObj = new BscApiServiceResponse();
		responseObj.setSuccess( YesNo.NO );
		try {	
			subject = WsAuthenticateUtils.login();
			@SuppressWarnings("unchecked")
			IVisionService<VisionVO, BbVision, String> visionService = 
					(IVisionService<VisionVO, BbVision, String>) AppContext.getBean("bsc.service.VisionService");
			@SuppressWarnings("unchecked")
			IEmployeeService<EmployeeVO, BbEmployee, String> employeeService = 
					(IEmployeeService<EmployeeVO, BbEmployee, String>) AppContext.getBean("bsc.service.EmployeeService");
			@SuppressWarnings("unchecked")
			IOrganizationService<OrganizationVO, BbOrganization, String> organizationService = 
					(IOrganizationService<OrganizationVO, BbOrganization, String>) AppContext.getBean("bsc.service.OrganizationService");
			String visionOid = "";
			String measureDataOrganizationOid = "";
			String measureDataEmployeeOid = ""; 
			DefaultResult<VisionVO> visionResult = visionService.findForSimpleByVisId(visionId);
			if (visionResult.getValue() == null) {
				throw new Exception( visionResult.getSystemMessage().getValue() );
			}
			visionOid = visionResult.getValue().getOid();
			if (StringUtils.isBlank(measureDataOrganizationId)) {
				measureDataOrganizationOid = BscBaseLogicServiceCommonSupport
						.findEmployeeDataByEmpId(employeeService, measureDataOrganizationId)
						.getOid();
			}
			if (StringUtils.isBlank(measureDataEmployeeId)) {
				measureDataEmployeeOid = BscBaseLogicServiceCommonSupport
						.findOrganizationDataByUK(organizationService, measureDataEmployeeId)
						.getOid();
			}
			this.processForScorecard(
					responseObj, 
					request, 
					visionOid, startDate, endDate, startYearDate, endYearDate, frequency, dataFor, measureDataOrganizationOid, measureDataEmployeeOid, contentFlag);
		} catch (Exception e) {
			responseObj.setMessage( e.getMessage() );
		} finally {
			if (!YesNo.YES.equals(responseObj.getSuccess())) {
				responseObj.setMessage( SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA) );
			}	
			WsAuthenticateUtils.logout(subject);			
		}
		subject = null;
		return responseObj;
	}
	
}
