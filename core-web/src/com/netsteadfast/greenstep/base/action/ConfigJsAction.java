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
package com.netsteadfast.greenstep.base.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.PleaseSelect;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.po.hbm.TbSysIcon;
import com.netsteadfast.greenstep.service.ISysIconService;
import com.netsteadfast.greenstep.vo.SysIconVO;

@ControllerAuthority(check=false)
@Controller("core.web.controller.ConfigJsAction")
@Scope
public class ConfigJsAction extends BaseSupportAction {
	private static final long serialVersionUID = -8970012046041448665L;
	protected Logger logger=Logger.getLogger(ConfigJsAction.class);
	private ISysIconService<SysIconVO, TbSysIcon, String> sysIconService;
	private InputStream inputStream;
	
	public ConfigJsAction() {
		super();
	}
	
	public ISysIconService<SysIconVO, TbSysIcon, String> getSysIconService() {
		return sysIconService;
	}

	@Autowired
	@Resource(name="core.service.SysIconService")
	@Required	
	public void setSysIconService(
			ISysIconService<SysIconVO, TbSysIcon, String> sysIconService) {
		this.sysIconService = sysIconService;
	}

	private void initConfigJs() throws Exception {
		StringBuilder sb=new StringBuilder();
		sb.append("var _gscore_dojo_ajax_sync=").append(super.getDojoAjaxSync()).append(";\n");
		sb.append("var _gscore_dojo_ajax_timeout=").append(super.getDojoAjaxTimeout()).append(";\n");
		sb.append("var _gscore_jquery_ajax_async='").append( (YesNo.YES.equals(super.getDojoAjaxSync()) ? YesNo.NO : YesNo.YES) ).append("';\n");
		sb.append("var _gscore_jquery_ajax_timeout=").append(super.getDojoAjaxTimeout()).append(";\n");		
		sb.append("var _gscore_default_pageRowSize=").append(DefaultPageRowSize).append(";\n");
		sb.append("var _gscore_basePath='").append( super.getBasePath() ).append("';\n");
		sb.append("var _gscore_mainTabContainer='").append(super.getDojoMainTabContainer()).append("';\n");
		sb.append("var _gscore_inputfieldNoticeMsgLabelIdName='").append(Constants.INPUTFIELD_NOTICE_MESSAGE_LABEL).append("';\n");
		sb.append("var _gscore_please_select_id='").append(Constants.HTML_SELECT_NO_SELECT_ID).append("';\n");
		//sb.append("var _gscore_please_select_name='").append(Constants.HTML_SELECT_NO_SELECT_NAME).append("';\n");
		sb.append("var _gscore_please_select_name='").append( PleaseSelect.getLabel(this.getLocaleLang()) ).append("';\n");
		sb.append("var _gscore_delimiter='").append(Constants.ID_DELIMITER).append("';\n");
		sb.append("var _gscore_datetime_delimiter='").append(Constants.DATETIME_DELIMITER).append("';\n");
		sb.append("var _gscore_success_flag='").append(YesNo.YES).append("';\n");
		sb.append("var _gscore_is_super_role='").append(super.getIsSuperRole()).append("';\n");		
		sb.append("var _gscore_googleMapUrl='").append(super.getGoogleMapUrl()).append("';\n");
		sb.append("var _gscore_googleMapDefaultLat=").append(super.getGoogleMapDefaultLat()).append(";\n");
		sb.append("var _gscore_googleMapDefaultLng=").append(super.getGoogleMapDefaultLng()).append(";\n");
		sb.append("var _gscore_googleMapLanguage='").append(super.getGoogleMapLanguage()).append("';\n");	
		sb.append("var _gscore_googleMapClientLocationEnable='").append(super.getGoogleMapClientLocationEnable()).append("';\n");
		sb.append("var _gscore_common_upload_dialog_action='core.commonUploadAction.action';\n");
		sb.append("var _gscore_common_upload_dialog_id='CORE_PROGCOMM0002Q_Dlg';\n");
		sb.append("var _gscore_common_signature_dialog_action='core.commonSignatureAction.action';\n");
		sb.append("var _gscore_common_signature_dialog_id='CORE_PROGCOMM0003Q_Dlg';\n");
		sb.append("var _gscore_common_jasperreport_load_action='core.commonJasperReportAction.action';\n");
		sb.append("var _gscore_common_codeeditor_dialog_id='CORE_PROGCOMM0004Q_Dlg';\n");
		this.putImagePreload(sb);
		this.inputStream=new ByteArrayInputStream(sb.toString().getBytes());
	}
	
	private void putImagePreload(StringBuilder sb) throws Exception {
		List<TbSysIcon> searchList = sysIconService.findListByParams(null);		
		sb.append("var _coreIconsList = [");
		for (int i=0; searchList!=null && i<searchList.size(); i++) {
			TbSysIcon sysIcon = searchList.get(i);
			sb.append("\"./icons/").append(sysIcon.getFileName()).append("\"");
			if ( (i+1) < searchList.size() ) {
				sb.append(",");
			}
		}
		sb.append("];");
		sb.append("\n");
		sb.append("var _coreIconsIdList = [");
		for (int i=0; searchList!=null && i<searchList.size(); i++) {
			TbSysIcon sysIcon = searchList.get(i);
			sb.append("\"").append(sysIcon.getIconId()).append("\"");
			if ( (i+1) < searchList.size() ) {
				sb.append(",");
			}
		}
		sb.append("];");		
		sb.append("\n");
	}
	
	/**
	 * core.configJsAction.action
	 */
	public String execute() throws Exception {
		try {
			this.initConfigJs();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}	
	
	public InputStream getInputStream() {
		if (this.inputStream==null) {
			this.inputStream=new ByteArrayInputStream("/* error com.netsteadfast.greenstep.base.action.ConfigJsAction */".getBytes());
		}		
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}	

}
