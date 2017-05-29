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

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
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
import com.netsteadfast.greenstep.base.model.CheckFieldHandler;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.po.hbm.QcOlapCatalog;
import com.netsteadfast.greenstep.po.hbm.QcOlapConf;
import com.netsteadfast.greenstep.po.hbm.QcOlapMdx;
import com.netsteadfast.greenstep.qcharts.action.utils.NotBlankFieldCheckUtils;
import com.netsteadfast.greenstep.qcharts.action.utils.SelectItemFieldCheckUtils;
import com.netsteadfast.greenstep.qcharts.service.IOlapCatalogService;
import com.netsteadfast.greenstep.qcharts.service.IOlapConfService;
import com.netsteadfast.greenstep.qcharts.service.IOlapMdxService;
import com.netsteadfast.greenstep.qcharts.service.logic.IAnalyticsMDXLogicService;
import com.netsteadfast.greenstep.util.OlapUtils;
import com.netsteadfast.greenstep.util.Pivot4JUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.OlapCatalogVO;
import com.netsteadfast.greenstep.vo.OlapConfVO;
import com.netsteadfast.greenstep.vo.OlapMdxVO;

@ControllerAuthority(check=true)
@Controller("qcharts.web.controller.AnalyticsProcessAction")
@Scope
public class AnalyticsProcessAction extends BaseJsonAction {
	private static final long serialVersionUID = 2745726472517370576L;
	protected Logger logger=Logger.getLogger(AnalyticsProcessAction.class);
	private IOlapConfService<OlapConfVO, QcOlapConf, String> olapConfService;
	private IOlapCatalogService<OlapCatalogVO, QcOlapCatalog, String> olapCatalogService;
	private IOlapMdxService<OlapMdxVO, QcOlapMdx, String> olapMdxService;
	private IAnalyticsMDXLogicService analyticsMDXLogicService;
	private String message = "";
	private String success = IS_NO;
	private String content = "";
	private String oid = "";
	
	public AnalyticsProcessAction() {
		super();
	}
	
	@JSON(serialize=false)
	public IOlapConfService<OlapConfVO, QcOlapConf, String> getOlapConfService() {
		return olapConfService;
	}

	@Autowired
	@Resource(name="qcharts.service.OlapConfService")		
	public void setOlapConfService(
			IOlapConfService<OlapConfVO, QcOlapConf, String> olapConfService) {
		this.olapConfService = olapConfService;
	}

	@JSON(serialize=false)
	public IOlapCatalogService<OlapCatalogVO, QcOlapCatalog, String> getOlapCatalogService() {
		return olapCatalogService;
	}

	@Autowired
	@Resource(name="qcharts.service.OlapCatalogService")			
	public void setOlapCatalogService(
			IOlapCatalogService<OlapCatalogVO, QcOlapCatalog, String> olapCatalogService) {
		this.olapCatalogService = olapCatalogService;
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

	@JSON(serialize=false)
	public IAnalyticsMDXLogicService getAnalyticsMDXLogicService() {
		return analyticsMDXLogicService;
	}

	@Autowired
	@Resource(name="qcharts.service.logic.AnalyticsMDXLogicService")		
	public void setAnalyticsMDXLogicService(
			IAnalyticsMDXLogicService analyticsMDXLogicService) {
		this.analyticsMDXLogicService = analyticsMDXLogicService;
	}

	private OlapConfVO loadOlapConfig(String oid) throws ServiceException, Exception {
		OlapConfVO conf = new OlapConfVO();
		conf.setOid(oid);
		DefaultResult<OlapConfVO> result = this.olapConfService.findObjectByOid(conf);
		if ( result.getValue()==null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		conf = result.getValue();
		return conf;
	}
	
	private OlapCatalogVO loadOlapCatalog(String oid) throws ServiceException, Exception {
		OlapCatalogVO catalog = new OlapCatalogVO();
		catalog.setOid(oid);
		DefaultResult<OlapCatalogVO> result = this.olapCatalogService.findObjectByOid(catalog);
		if ( result.getValue()==null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		catalog = result.getValue();		
		return catalog;
	}
	
	private void checkFields(String type) throws ControllerException {
		CheckFieldHandler fieldHandler = this.getCheckFieldHandler()
		.add("configOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.QCHARTS_PROG002D0002Q_olapConfigOid") )
		.add("catalogOid", SelectItemFieldCheckUtils.class, this.getText("MESSAGE.QCHARTS_PROG002D0002Q_olapCatalogOid") )
		.add("expression", NotBlankFieldCheckUtils.class, this.getText("MESSAGE.QCHARTS_PROG002D0002Q_expression") );
		if ("save".equals(type) || "update".equals(type)) {
			fieldHandler.add("name", NotBlankFieldCheckUtils.class, "Name is required!");
		}
		fieldHandler.process().throwMessage();
	}		
	
	private void rendererHtml(File catalogFile) throws ControllerException, AuthorityException, ServiceException, Exception {
		this.content = "";
		this.checkFields("query");
		boolean showDimensionTitle = true;
		boolean showParentMembers = true;
		if ( !"true".equals( this.getFields().get("showDimensionTitle") ) ) {
			showDimensionTitle = false;
		}
		if ( !"true".equals( this.getFields().get("showParentMembers") ) ) {
			showParentMembers = false;
		}				
		OlapCatalogVO catalog = this.loadOlapCatalog(this.getFields().get("catalogOid"));
		OlapConfVO config = this.loadOlapConfig(this.getFields().get("configOid"));
		catalogFile = OlapUtils.writeCatalogContentToFile( catalog.getId()+"_"+super.getAccountOid(), 
				new String(catalog.getContent(), Constants.BASE_ENCODING) );
		String mondrianUrl = OlapUtils.getMondrianUrl(config.getJdbcUrl(), config.getJdbcDrivers(), catalogFile.getPath());
		this.content = Pivot4JUtils.rendererHtml(
				mondrianUrl, this.getFields().get("expression"), showDimensionTitle, showParentMembers);	
		this.message = this.getText("MESSAGE.QCHARTS_PROG002D0002Q_msg1") + super.getHtmlBr();			
		this.success = IS_YES;
	}
	
	private void rendererHtmlExport(File catalogFile) throws ControllerException, AuthorityException, ServiceException, Exception {
		this.rendererHtml(catalogFile);
		String datas = Pivot4JUtils.wrapRendererHtml(this.content);
		File file = null;
		try {
			file = new File( Constants.getWorkTmpDir() + "/" + super.getUuid() + ".htm" );
			FileUtils.writeStringToFile(file, datas, Constants.BASE_ENCODING);
			this.oid = UploadSupportUtils.create(Constants.getSystem(), UploadTypes.IS_TEMP, true, file, "analytics-export.htm");
		} catch (Exception e) {
			throw e;
		} finally {
			file = null;
		}		
	}	
	
	private void exportExcel(File catalogFile) throws ControllerException, AuthorityException, ServiceException, Exception {
		this.oid = "";
		this.checkFields("query");
		boolean showDimensionTitle = true;
		boolean showParentMembers = true;
		if ( !"true".equals( this.getFields().get("showDimensionTitle") ) ) {
			showDimensionTitle = false;
		}
		if ( !"true".equals( this.getFields().get("showParentMembers") ) ) {
			showParentMembers = false;
		}		
		OlapCatalogVO catalog = this.loadOlapCatalog(this.getFields().get("catalogOid"));
		OlapConfVO config = this.loadOlapConfig(this.getFields().get("configOid"));
		catalogFile = OlapUtils.writeCatalogContentToFile( catalog.getId()+"_"+super.getAccountOid(), 
				new String(catalog.getContent(), Constants.BASE_ENCODING) );
		String mondrianUrl = OlapUtils.getMondrianUrl(config.getJdbcUrl(), config.getJdbcDrivers(), catalogFile.getPath());
		File file = null;
		try {
			file = Pivot4JUtils.exportExcelFile(
					mondrianUrl, this.getFields().get("expression"), showDimensionTitle, showParentMembers);
			this.oid = UploadSupportUtils.create(Constants.getSystem(), UploadTypes.IS_TEMP, true, file, "analytics-export.xlsx");
		} catch (Exception e) {
			throw e;
		} finally {
			file = null;
		}
		this.message = this.getText("MESSAGE.QCHARTS_PROG002D0002Q_msg1") + super.getHtmlBr();			
		this.success = IS_YES;		
	}
	
	private void save() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields("save");
		OlapMdxVO olapMdx = new OlapMdxVO();
		this.transformFields2ValueObject(olapMdx, new String[]{"name"});
		olapMdx.setExpression( this.getFields().get("expression").getBytes() );
		DefaultResult<OlapMdxVO> result = this.analyticsMDXLogicService.create(
				olapMdx, this.getFields().get("configOid"), this.getFields().get("catalogOid") );
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}
	}
	
	private void update() throws ControllerException, AuthorityException, ServiceException, Exception {
		this.checkFields("update");
		OlapMdxVO olapMdx = new OlapMdxVO();
		this.transformFields2ValueObject(olapMdx, new String[]{"oid", "name"});
		olapMdx.setExpression( this.getFields().get("expression").getBytes() );
		DefaultResult<OlapMdxVO> result = this.analyticsMDXLogicService.update(
				olapMdx, this.getFields().get("configOid"), this.getFields().get("catalogOid") );
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null) {
			this.success = IS_YES;
		}		
	}
	
	private void delete() throws ControllerException, AuthorityException, ServiceException, Exception {
		OlapMdxVO olapMdx = new OlapMdxVO();
		this.transformFields2ValueObject(olapMdx, new String[]{"oid"});
		DefaultResult<Boolean> result = this.analyticsMDXLogicService.delete(olapMdx);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()!=null && result.getValue()) {
			this.success = IS_YES;
		}		
	}
	
	/**
	 * qcharts.analyticsHtmlAction.action
	 * 
	 * @return
	 * @throws Exception
	 */	
	@JSON(serialize=false)
	@ControllerMethodAuthority(programId="QCHARTS_PROG002D0002Q")
	public String doHtml() throws Exception {
		File catalogFile = null;
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.rendererHtml(catalogFile);
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		catalogFile = null;
		return SUCCESS;			
	}	
	
	/**
	 * qcharts.analyticsHtmlExportAction.action
	 * 
	 * @return
	 * @throws Exception
	 */	
	@JSON(serialize=false)
	@ControllerMethodAuthority(programId="QCHARTS_PROG002D0002Q")
	public String doHtmlExport() throws Exception {
		File catalogFile = null;
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.rendererHtmlExport(catalogFile);
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		catalogFile = null;
		return SUCCESS;			
	}	
	
	/**
	 * qcharts.analyticsExcelAction.action
	 * 
	 * @return
	 * @throws Exception
	 */	
	@JSON(serialize=false)
	@ControllerMethodAuthority(programId="QCHARTS_PROG002D0002Q")
	public String doExcel() throws Exception {
		File catalogFile = null;
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.exportExcel(catalogFile);
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		catalogFile = null;
		return SUCCESS;			
	}		
	
	/**
	 * qcharts.analyticsSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false)
	@ControllerMethodAuthority(programId="QCHARTS_PROG002D0002A")	
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
	 * qcharts.analyticsDeleteAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false)
	@ControllerMethodAuthority(programId="QCHARTS_PROG002D0002A")	
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
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JSON
	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
}
