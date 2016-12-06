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
package com.netsteadfast.greenstep.qcharts.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.po.hbm.QcDataQueryMapper;
import com.netsteadfast.greenstep.po.hbm.QcDataQueryMapperSet;
import com.netsteadfast.greenstep.qcharts.service.IDataQueryMapperService;
import com.netsteadfast.greenstep.qcharts.service.IDataQueryMapperSetService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.DataQueryMapperSetVO;
import com.netsteadfast.greenstep.vo.DataQueryMapperVO;

@ControllerAuthority(check=true)
@Controller("qcharts.web.controller.DataQueryMapperManagementAction")
@Scope
public class DataQueryMapperManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = 1007510723164045726L;
	private IDataQueryMapperService<DataQueryMapperVO, QcDataQueryMapper, String> dataQueryMapperService;
	private IDataQueryMapperSetService<DataQueryMapperSetVO, QcDataQueryMapperSet, String> dataQueryMapperSetService;
	private DataQueryMapperVO dataQueryMapper = new DataQueryMapperVO();
	private List<QcDataQueryMapperSet> dataQueryMapperSets = new ArrayList<QcDataQueryMapperSet>();
	
	public DataQueryMapperManagementAction() {
		super();
	}
	
	public IDataQueryMapperService<DataQueryMapperVO, QcDataQueryMapper, String> getDataQueryMapperService() {
		return dataQueryMapperService;
	}

	@Autowired
	@Resource(name="qcharts.service.DataQueryMapperService")
	@Required		
	public void setDataQueryMapperService(
			IDataQueryMapperService<DataQueryMapperVO, QcDataQueryMapper, String> dataQueryMapperService) {
		this.dataQueryMapperService = dataQueryMapperService;
	}

	public IDataQueryMapperSetService<DataQueryMapperSetVO, QcDataQueryMapperSet, String> getDataQueryMapperSetService() {
		return dataQueryMapperSetService;
	}

	@Autowired
	@Resource(name="qcharts.service.DataQueryMapperSetService")
	@Required			
	public void setDataQueryMapperSetService(
			IDataQueryMapperSetService<DataQueryMapperSetVO, QcDataQueryMapperSet, String> dataQueryMapperSetService) {
		this.dataQueryMapperSetService = dataQueryMapperSetService;
	}	
	
	private void initData() throws ServiceException, Exception {
		
	}
	
	private void loadDataQueryMapperData() throws ServiceException, Exception {		
		this.transformFields2ValueObject(this.dataQueryMapper, new String[]{"oid"});
		DefaultResult<DataQueryMapperVO> result = this.dataQueryMapperService.findObjectByOid(this.dataQueryMapper);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		this.dataQueryMapper = result.getValue();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mapperOid", this.dataQueryMapper.getOid());
		this.dataQueryMapperSets = this.dataQueryMapperSetService.findListByParams(paramMap);
	}
	
	/**
	 * qcharts.dataQueryMapperManagementAction.action
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG001D0002Q")
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
	 * qcharts.dataQueryMapperCreateAction.action
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG001D0002A")	
	public String create() throws Exception {
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
	 * qcharts.dataQueryMapperEditAction.action
	 */
	@ControllerMethodAuthority(programId="QCHARTS_PROG001D0002E")
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData();
			this.loadDataQueryMapperData();
			forward = SUCCESS;
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

	public DataQueryMapperVO getDataQueryMapper() {
		return dataQueryMapper;
	}

	public List<QcDataQueryMapperSet> getDataQueryMapperSets() {
		return dataQueryMapperSets;
	}

}
