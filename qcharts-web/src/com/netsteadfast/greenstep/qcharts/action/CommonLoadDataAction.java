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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.po.hbm.QcDataQuery;
import com.netsteadfast.greenstep.po.hbm.QcDataQueryMapperSet;
import com.netsteadfast.greenstep.po.hbm.QcDataSourceConf;
import com.netsteadfast.greenstep.po.hbm.QcOlapMdx;
import com.netsteadfast.greenstep.qcharts.service.IDataQueryMapperSetService;
import com.netsteadfast.greenstep.qcharts.service.IDataQueryService;
import com.netsteadfast.greenstep.qcharts.service.IDataSourceConfService;
import com.netsteadfast.greenstep.qcharts.service.IOlapMdxService;
import com.netsteadfast.greenstep.vo.DataQueryMapperSetVO;
import com.netsteadfast.greenstep.vo.DataQueryVO;
import com.netsteadfast.greenstep.vo.DataSourceConfVO;
import com.netsteadfast.greenstep.vo.OlapMdxVO;

/**
 * 抓取json資料共用的action
 *
 */
@ControllerAuthority(check=false)
@Controller("qcharts.web.controller.CommonLoadDataAction")
@Scope
public class CommonLoadDataAction extends BaseJsonAction {
	private static final long serialVersionUID = -2788947127108134017L;
	protected Logger logger=Logger.getLogger(CommonLoadDataAction.class);
	private IDataQueryMapperSetService<DataQueryMapperSetVO, QcDataQueryMapperSet, String> dataQueryMapperSetService;
	private IDataQueryService<DataQueryVO, QcDataQuery, String> dataQueryService;
	private IDataSourceConfService<DataSourceConfVO, QcDataSourceConf, String> dataSourceConfService;
	private IOlapMdxService<OlapMdxVO, QcOlapMdx, String> olapMdxService;
	private String message = "";
	private String success = IS_NO;
	private List<Map<String, String>> items=new LinkedList<Map<String, String>>();
	private DataQueryVO dataQuery = new DataQueryVO();
	private OlapMdxVO olapMdx = new OlapMdxVO();
	
	public CommonLoadDataAction() {
		super();
	}
	
	@JSON(serialize=false)
	public IDataQueryMapperSetService<DataQueryMapperSetVO, QcDataQueryMapperSet, String> getDataQueryMapperSetService() {
		return dataQueryMapperSetService;
	}

	@Autowired
	@Resource(name="qcharts.service.DataQueryMapperSetService")	
	public void setDataQueryMapperSetService(
			IDataQueryMapperSetService<DataQueryMapperSetVO, QcDataQueryMapperSet, String> dataQueryMapperSetService) {
		this.dataQueryMapperSetService = dataQueryMapperSetService;
	}
	
	@JSON(serialize=false)
	public IDataQueryService<DataQueryVO, QcDataQuery, String> getDataQueryService() {
		return dataQueryService;
	}

	@Autowired
	@Resource(name="qcharts.service.DataQueryService")	
	public void setDataQueryService(
			IDataQueryService<DataQueryVO, QcDataQuery, String> dataQueryService) {
		this.dataQueryService = dataQueryService;
	}	

	@JSON(serialize=false)
	public IDataSourceConfService<DataSourceConfVO, QcDataSourceConf, String> getDataSourceConfService() {
		return dataSourceConfService;
	}

	@Autowired
	@Resource(name="qcharts.service.DataSourceConfService")		
	public void setDataSourceConfService(
			IDataSourceConfService<DataSourceConfVO, QcDataSourceConf, String> dataSourceConfService) {
		this.dataSourceConfService = dataSourceConfService;
	}

	@JSON(serialize=false)
	public IOlapMdxService<OlapMdxVO, QcOlapMdx, String> getOlapMdxService() {
		return olapMdxService;
	}

	@Autowired
	@Resource(name="qcharts.service.OlapMdxService")			
	public void setOlapMdxService(
			IOlapMdxService<OlapMdxVO, QcOlapMdx, String> olapMdxService) {
		this.olapMdxService = olapMdxService;
	}

	private void fillDataMap2Items(Map<String, String> sourceDataMap) throws Exception {
		if (null==sourceDataMap) {
			return;
		}
		for (Map.Entry<String, String> entry : sourceDataMap.entrySet()) {
			Map<String, String> dataMap = new HashMap<String, String>();
			dataMap.put("key", entry.getKey());
			dataMap.put("value", entry.getValue());
			this.items.add(dataMap);
		}		
	}		
	
	private void loadDataQueryMapperSetItems() throws ControllerException, AuthorityException, ServiceException, Exception {
		Map<String, String> mapperSetMap = this.dataQueryMapperSetService.findForMap(
				true, this.getFields().get("dataQueryMapperOid"));
		this.resetPleaseSelectDataMapFromLocaleLang(mapperSetMap);
		this.fillDataMap2Items(mapperSetMap);
		this.success = IS_YES;
	}
	
	private void loadDataQueryItems() throws ControllerException, AuthorityException, ServiceException, Exception {
		Map<String, String> queryMap = this.dataQueryService.findForMap(true);
		this.resetPleaseSelectDataMapFromLocaleLang(queryMap);
		this.fillDataMap2Items(queryMap);
		this.success = IS_YES;
	}
	
	private void loadQueryHistory() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.transformFields2ValueObject(this.dataQuery, new String[]{"oid"});
		DefaultResult<DataQueryVO> result = this.dataQueryService.findObjectByOid(this.dataQuery);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		this.dataQuery = result.getValue();
		DataSourceConfVO conf = new DataSourceConfVO();
		conf.setId( this.dataQuery.getConf() );
		DefaultResult<DataSourceConfVO> confResult = this.dataSourceConfService.findByUK(conf);
		if (confResult.getValue()!=null) {
			this.dataQuery.setDataSourceConfOid( confResult.getValue().getOid() );
		}
		this.success = IS_YES;
	}
	
	private void loadMdxItems() throws ControllerException, AuthorityException, ServiceException, Exception {
		Map<String, String> mdxMap = this.olapMdxService.findForMap(true);
		this.resetPleaseSelectDataMapFromLocaleLang(mdxMap);
		this.fillDataMap2Items(mdxMap);
		this.success = IS_YES;
	}
	
	private void loadMdxHistory() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.transformFields2ValueObject(this.olapMdx, new String[]{"oid"});
		DefaultResult<OlapMdxVO> result = this.olapMdxService.findObjectByOid(this.olapMdx);
		if ( result.getValue() == null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		this.olapMdx = result.getValue();
		this.getFields().put("expression", new String(this.olapMdx.getExpression(), Constants.BASE_ENCODING) );
		this.success = IS_YES;
	}
	
	/**
	 * qcharts.commonGetDataQueryMapperSetItemsAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false) 
	public String getDataQueryMapperSetItems() throws Exception {
		try {
			this.loadDataQueryMapperSetItems();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;			
	}	

	/**
	 * qcharts.commonGetDataQueryItemsAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false) 
	public String getDataQueryItems() throws Exception {
		try {
			this.loadDataQueryItems();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;			
	}	
	
	/**
	 * qcharts.commonGetQueryHistoryAction.action
	 * 
	 * @return
	 * @throws Exception
	 */	
	@JSON(serialize=false) 
	public String getQueryHistory() throws Exception {
		try {
			this.loadQueryHistory();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;			
	}
	
	/**
	 * qcharts.commonGetMdxItemsAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false) 
	public String getMdxItems() throws Exception {
		try {
			this.loadMdxItems();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;			
	}		
	
	/**
	 * qcharts.commonGetMdxHistoryAction.action
	 * 
	 * @return
	 * @throws Exception
	 */	
	@JSON(serialize=false) 
	public String getMdxHistory() throws Exception {
		try {
			this.loadMdxHistory();
		} catch (ControllerException ce) {
			this.message=ce.getMessage().toString();
		} catch (AuthorityException ae) {
			this.message=ae.getMessage().toString();
		} catch (ServiceException se) {
			this.message=se.getMessage().toString();
		} catch (Exception e) { // 因為是 JSON 所以不用拋出 throw e 了
			e.printStackTrace();
			this.message=e.getMessage().toString();
			this.logger.error(e.getMessage());
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
	public List<Map<String, String>> getItems() {
		return items;
	}

	@JSON
	public DataQueryVO getDataQuery() {
		return dataQuery;
	}

	@JSON
	public OlapMdxVO getOlapMdx() {
		return olapMdx;
	}	

	@JSON
	public Map<String, String> getFields() {
		return super.getFields();
	}
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
		
}
