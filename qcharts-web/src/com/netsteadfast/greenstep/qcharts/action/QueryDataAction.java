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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.qcharts.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.qcharts.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.qcharts.service.logic.IDataQueryLogicService;
import com.netsteadfast.greenstep.qcharts.utils.ChartsDataUtils;
import com.netsteadfast.greenstep.qcharts.utils.QueryDataUtils;
import com.netsteadfast.greenstep.util.TemplateUtils;
import com.netsteadfast.greenstep.vo.DataQueryVO;

@ControllerAuthority(check=true)
@Controller("qcharts.web.controller.QueryDataAction")
@Scope
public class QueryDataAction extends BaseJsonAction {
	private static final long serialVersionUID = 2904495231935982667L;
	protected Logger logger=Logger.getLogger(QueryDataAction.class);
	private IDataQueryLogicService dataQueryLogicService;
	private String message = "";
	private String success = IS_NO;
	private List<Map<String, Object>> searchDatas = new ArrayList<Map<String, Object>>();
	private String gridContent = "";
	private List<Map<String, Object>> pieDatas = new ArrayList<Map<String, Object>>();
	private List<String> seriesCategories = new ArrayList<String>();
	private List<Map<String, Object>> seriesData = new ArrayList<Map<String, Object>>();
	
	public QueryDataAction() {
		super();
	}

	@JSON(serialize=false)
	public IDataQueryLogicService getDataQueryLogicService() {
		return dataQueryLogicService;
	}

	@Autowired
	@Resource(name="qcharts.service.logic.DataQueryLogicService")	
	public void setDataQueryLogicService(
			IDataQueryLogicService dataQueryLogicService) {
		this.dataQueryLogicService = dataQueryLogicService;
	}
	
	private void checkFields() throws ControllerException {
		this.getCheckFieldHandler()
		.add("dataSourceConfOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.QCHARTS_PROG002D0001Q_dataSourceConfOid") )
		.add("queryExpression", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.QCHARTS_PROG002D0001Q_queryExpression") )
		.process().throwMessage();
	}		
	
	private void checkFieldsForCreate() throws ControllerException {
		this.getCheckFieldHandler()
		.add("dataSourceConfOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.QCHARTS_PROG002D0001Q_dataSourceConfOid") )
		.add("dataQueryMapperOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.QCHARTS_PROG002D0001Q_dataQueryMapperOid") )
		.add("name", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.QCHARTS_PROG002D0001Q_name") )
		.add("queryExpression", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.QCHARTS_PROG002D0001Q_queryExpression") )
		.process().throwMessage();
	}		
	
	private void query() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields();
		String dataSourceConfOid = this.getFields().get("dataSourceConfOid");
		String queryExpression = this.getFields().get("queryExpression");
		String queryType = this.getFields().get("queryType"); // 1 = Grid , 2 = Pie, 3 = Bar, 4 = Line
		String dataQueryMapperOid = this.getFields().get("dataQueryMapperOid");
		String dataQueryMapperSetOid = this.getFields().get("dataQueryMapperSetOid");
		String name = this.getFields().get("name");
		
		this.getCheckFieldHandler()
		.single(
				"name", 
				( ("2".equals(queryType) || "3".equals(queryType)) && StringUtils.isBlank(name) ), 
				this.getText("MESSAGE.QCHARTS_PROG002D0001Q_name") )
		.single(
				"dataQueryMapperOid|dataQueryMapperSetOid", 
				( "2".equals(queryType) && (this.isNoSelectId(dataQueryMapperOid) || this.isNoSelectId(dataQueryMapperSetOid)) ), 
				this.getText("MESSAGE.QCHARTS_PROG002D0001Q_msg1") )
		.single(
				"dataQueryMapperOid", 
				( ("3".equals(queryType) || "4".equals(queryType)) && this.isNoSelectId(dataQueryMapperOid) ), 
				this.getText("MESSAGE.QCHARTS_PROG002D0001Q_dataQueryMapperOid") )
		.throwMessage();
		
		this.searchDatas = QueryDataUtils.query(dataSourceConfOid, queryExpression);
		this.message = this.getText("MESSAGE.QCHARTS_PROG002D0001Q_msg2") + super.getHtmlBr();
		if (this.searchDatas!=null) {
			this.renderGridContent();
			if ("2".equals(queryType)) {
				this.pieDatas = ChartsDataUtils.getHighchartsPieData(dataQueryMapperSetOid, this.searchDatas);
			}
			if ("3".equals(queryType) || "4".equals(queryType) ) {
				this.seriesCategories = ChartsDataUtils.getHighchartsSeriesCategories(dataQueryMapperOid, this.searchDatas);
				this.seriesData = ChartsDataUtils.getHighchartsSeriesData(
						dataQueryMapperOid, this.seriesCategories, this.searchDatas);
			}
			this.success = IS_YES;
			this.message = this.getText("MESSAGE.QCHARTS_PROG002D0001Q_msg3") + super.getHtmlBr();			
		} 
	}
	
	private void renderGridContent() throws Exception {
		if (this.searchDatas==null || this.searchDatas.size()<1) {
			return;
		}
		List<String> labels = new LinkedList<String>();
		Map<String, Object> data = this.searchDatas.get(0);
		for (Map.Entry<String, Object> entry : data.entrySet()) {			
			labels.add(entry.getKey());
		}
		if (labels.size()<1) {
			return;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("labels", labels);
		paramMap.put("searchDatas", this.searchDatas);
		this.gridContent = TemplateUtils.processTemplate(
				"resourceTemplate", 
				QueryDataUtils.class.getClassLoader(), 
				"META-INF/resource/data-query-grid.ftl", 
				paramMap);		
	}
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFieldsForCreate();
		String dataSourceConfOid = this.getFields().get("dataSourceConfOid");
		String dataQueryMapperOid = this.getFields().get("dataQueryMapperOid");
		DataQueryVO dataQuery = new DataQueryVO();
		this.transformFields2ValueObject(dataQuery, new String[]{"name", "queryExpression"});
		DefaultResult<DataQueryVO> result = this.dataQueryLogicService.create(
				dataSourceConfOid, dataQueryMapperOid, dataQuery);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFieldsForCreate();
		String dataSourceConfOid = this.getFields().get("dataSourceConfOid");
		String dataQueryMapperOid = this.getFields().get("dataQueryMapperOid");
		DataQueryVO dataQuery = new DataQueryVO();
		this.transformFields2ValueObject(dataQuery, new String[]{"oid", "name", "queryExpression"});
		DefaultResult<DataQueryVO> result = this.dataQueryLogicService.update(
				dataSourceConfOid, dataQueryMapperOid, dataQuery);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}		
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		DataQueryVO dataQuery = new DataQueryVO();
		this.transformFields2ValueObject(dataQuery, new String[]{"oid"});		
		DefaultResult<Boolean> result = this.dataQueryLogicService.delete(dataQuery);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}		
	}
	
	/**
	 * qcharts.queryDataAction.action
	 * 
	 * @return
	 * @throws Exception
	 */	
	@JSON(serialize=false)
	@ControllerMethodAuthority(programId="QCHARTS_PROG002D0001Q")
	public String execute() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.query();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;			
	}
	
	/**
	 * qcharts.queryDataSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false)
	@ControllerMethodAuthority(programId="QCHARTS_PROG002D0001A")	
	public String doSave() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			if (this.isNoSelectId( (String)this.getFields().get("oid")  )) {
				this.save();
			} else {
				this.update();
			}
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;			
	}
	
	/**
	 * qcharts.queryDataDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false)
	@ControllerMethodAuthority(programId="QCHARTS_PROG002D0001A")	
	public String doDelete() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.delete();
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
	public List<Map<String, Object>> getSearchDatas() {
		return searchDatas;
	}

	@JSON
	public String getGridContent() {
		return gridContent;
	}

	@JSON
	public List<Map<String, Object>> getPieDatas() {
		return pieDatas;
	}

	@JSON
	public List<String> getSeriesCategories() {
		return seriesCategories;
	}

	@JSON
	public List<Map<String, Object>> getSeriesData() {
		return seriesData;
	}
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
}
