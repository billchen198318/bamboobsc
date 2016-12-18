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
package com.netsteadfast.greenstep.bsc.mobile.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.chain.SimpleChain;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ChainResultObj;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.bsc.vo.StrategyMapItemsVO;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.vo.VisionVO;

@ControllerAuthority(check=false)
@Controller("bsc.mobile.web.controller.IndexAction")
@Scope
public class IndexAction extends BaseSupportAction {
	private static final long serialVersionUID = -1938608913545322784L;
	protected Logger logger=Logger.getLogger(IndexAction.class);
	private IVisionService<VisionVO, BbVision, String> visionService;
	private Map<String, String> frequencyMap = BscMeasureDataFrequency.getFrequencyMap(false);
	private Map<String, String> visionMap = this.providedSelectZeroDataMap(true);
	
	// for Strategy-MAP
	private List<String> divItems = new ArrayList<String>();
	private List<String> cssItems = new ArrayList<String>();
	private List<String> conItems = new ArrayList<String>();	
	
	public IndexAction() {
		super();
	}
	
	public IVisionService<VisionVO, BbVision, String> getVisionService() {
		return visionService;
	}

	@Autowired
	@Resource(name="bsc.service.VisionService")
	@Required		
	public void setVisionService(IVisionService<VisionVO, BbVision, String> visionService) {
		this.visionService = visionService;
	}
	
	private void initData(String type) throws ServiceException, Exception {
		if ("lnkDashboard".equals(type) || "lnkStrategyMap".equals(type)) {
			this.visionMap = this.visionService.findForMap(true);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void loadRecord() throws ControllerException, ServiceException, Exception {
		String visionOid = this.getFields().get("visionOid");
		if ( super.isNoSelectId(visionOid) ) {
			return;
		}
		Context context = new ContextBase();
		context.put("visionOid", visionOid);		
		SimpleChain chain = new SimpleChain();
		ChainResultObj resultObj = chain.getResultFromResource("strategyMapItemsForRecChain", context);
		this.setPageMessage( resultObj.getMessage() );
		if ( resultObj.getValue() instanceof StrategyMapItemsVO ) {
			this.divItems = ( (StrategyMapItemsVO)resultObj.getValue() ).getDiv();
			this.cssItems = ( (StrategyMapItemsVO)resultObj.getValue() ).getCss();
			this.conItems = ( (StrategyMapItemsVO)resultObj.getValue() ).getCon();
		}		
	}	

	@ControllerMethodAuthority(programId="BSC_MOBILE_INDEX")
	public String execute() throws Exception {
		try {
			this.initData("execute");
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
	
	@ControllerMethodAuthority(programId="BSC_MOBILE_OLD_INDEX")
	public String oldHomePage() throws Exception {
		try {
			this.initData("oldHomePage");
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
	
	@ControllerMethodAuthority(programId="BSC_MOBILE_INDEX")
	public String lnkDashboard() throws Exception {
		try {
			this.initData("lnkDashboard");
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
	
	@ControllerMethodAuthority(programId="BSC_MOBILE_INDEX")
	public String lnkStrategyMap() throws Exception {
		try {
			this.initData("lnkStrategyMap");
			this.loadRecord();
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

	public Map<String, String> getFrequencyMap() {
		return frequencyMap;
	}
	
	public Map<String, String> getVisionMap() {
		super.resetPleaseSelectDataMapFromLocaleLang(visionMap);
		return visionMap;
	}

	// yyyy/mm/dd to mm/dd/yyyy
	public String getMeasureDataDate1() throws Exception {
		String nowDate[] = this.getNowDate().split("/");		
		return nowDate[1] + "/" + nowDate[2] + "/" + nowDate[0];
	}
	
	// yyyy-mm-dd
	public String getMeasureDataDate2() throws Exception {
		String nowDate[] = this.getNowDate().split("/");		
		return nowDate[0] + "-" + nowDate[1] + "-" + nowDate[2];
	}	
	
	public List<String> getDivItems() {
		return divItems;
	}

	public List<String> getCssItems() {
		return cssItems;
	}

	public List<String> getConItems() {
		return conItems;
	}
	
}
