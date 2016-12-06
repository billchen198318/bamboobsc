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

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.chain.SimpleChain;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ChainResultObj;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.VisionVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.PerspectivesDashboardAction")
@Scope
public class PerspectivesDashboardAction extends BaseJsonAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -2448544036438639172L;
	protected Logger logger=Logger.getLogger(PerspectivesDashboardAction.class);
	private IVisionService<VisionVO, BbVision, String> visionService;
	private Map<String, String> visionMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> frequencyMap = BscMeasureDataFrequency.getFrequencyMap(true);
	private String message = "";
	private String success = IS_NO;
	private String uploadOid = "";
	
	public PerspectivesDashboardAction() {
		super();
	}
	
	@JSON(serialize=false)
	public IVisionService<VisionVO, BbVision, String> getVisionService() {
		return visionService;
	}

	@Autowired
	@Required
	@Resource(name="bsc.service.VisionService")	
	public void setVisionService(
			IVisionService<VisionVO, BbVision, String> visionService) {
		this.visionService = visionService;
	}	
	
	private void initData() throws ServiceException, Exception {
		this.visionMap = this.visionService.findForMap(true);
		
	}
	
	@SuppressWarnings("unchecked")
	private void getExcel() throws ControllerException, AuthorityException, ServiceException, Exception {
		Context context = this.getContext();
		List< Map<String, Object> > chartDatas = (List<Map<String, Object>>) context.get("chartDatas");
		if ( chartDatas == null || chartDatas.size() < 1 ) {
			super.throwMessage( this.getText("MESSAGE.BSC_PROG003D0004Q_msg1") );
		}
		SimpleChain chain = new SimpleChain();
		ChainResultObj resultObj = chain.getResultFromResource("perspectivesDashboardExcelContentChain", context);
		this.message = resultObj.getMessage();
		if ( resultObj.getValue() instanceof String ) {
			this.uploadOid = (String)resultObj.getValue();
			this.success = IS_YES;
		}			
	}
	
	@SuppressWarnings("unchecked")
	private Context getContext() throws Exception {
		List< Map<String, Object> > chartDatas = new LinkedList< Map<String, Object> >();
		String pieCanvasToData = "";
		String barCanvasToData = "";
		Enumeration<String> paramNames = this.getHttpServletRequest().getParameterNames();
		while ( paramNames.hasMoreElements() ) {
			String paramName = paramNames.nextElement();
			String value = this.getHttpServletRequest().getParameter(paramName);
			if ( paramName.startsWith("BSC_PROG003D0004Q_meterGaugeChartDatas:") ) {		
				Map<String, Object> dataMap = (Map<String, Object>)
						new ObjectMapper().readValue(value, LinkedHashMap.class);	
				chartDatas.add( dataMap );
			}
			if ( paramName.equals("BSC_PROG003D0004Q_barChartDatas") ) {
				barCanvasToData = SimpleUtils.deHex( this.defaultString(value) );
			}
			if ( paramName.equals("BSC_PROG003D0004Q_pieChartDatas") ) {
				pieCanvasToData = SimpleUtils.deHex( this.defaultString(value) );
			}						
		}		
		Context context = new ContextBase();
		context.put("barCanvasToData", barCanvasToData);
		context.put("pieCanvasToData", pieCanvasToData);
		context.put("chartDatas", chartDatas);
		context.put("year", this.getHttpServletRequest().getParameter("BSC_PROG003D0004Q_year") );
		return context;
	}
	
	/**
	 *  bsc.perspectivesDashboardAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG003D0004Q")
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
	 * bsc.perspectivesDashboardExcelAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG003D0004Q")
	public String doExcel() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.getExcel();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			if (e.getMessage()==null) { 
				this.message=e.toString();
				this.logger.error(e.toString());
			} else {
				this.message=e.getMessage().toString();
				this.logger.error(e.getMessage());
			}						
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
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
	
	@Override
	public String getProgramId() {
		return super.getActionMethodProgramId();
	}

	@JSON(serialize=false)
	public Map<String, String> getVisionMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.visionMap);
		return visionMap;
	}

	@JSON(serialize=false)
	public Map<String, String> getFrequencyMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.frequencyMap);
		return frequencyMap;
	}

	@JSON
	public String getUploadOid() {
		return uploadOid;
	}
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
}
