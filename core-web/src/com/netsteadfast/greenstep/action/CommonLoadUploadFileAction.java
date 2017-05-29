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
package com.netsteadfast.greenstep.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.po.hbm.TbSysUpload;
import com.netsteadfast.greenstep.service.ISysUploadService;
import com.netsteadfast.greenstep.util.FSUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.SysUploadVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.CommonLoadUploadFileAction")
@Scope
public class CommonLoadUploadFileAction extends BaseSupportAction {
	private static final long serialVersionUID = 582037391631119994L;
	protected Logger logger=Logger.getLogger(CommonLoadUploadFileAction.class);
	private ISysUploadService<SysUploadVO, TbSysUpload, String> sysUploadService;
	private InputStream inputStream = null;
	private String filename = "";
	private String contentType = "";
	private String type = "view"; // view or download
	private String oid = ""; // TB_UPLOAD.OID
	
	public CommonLoadUploadFileAction() {
		super();
	}
	
	public ISysUploadService<SysUploadVO, TbSysUpload, String> getSysUploadService() {
		return sysUploadService;
	}

	@Autowired
	@Resource(name="core.service.SysUploadService")
	@Required			
	public void setSysUploadService(
			ISysUploadService<SysUploadVO, TbSysUpload, String> sysUploadService) {
		this.sysUploadService = sysUploadService;
	}

	private void loadData(File file) throws ServiceException, Exception {
		if (StringUtils.isBlank(this.oid)) {
			return;
		}
		DefaultResult<SysUploadVO> result = this.sysUploadService.findForNoByteContent(this.oid);
		if (result.getValue()==null) {
			throw new ControllerException(result.getSystemMessage().getValue());
		}		
		SysUploadVO uploadData = result.getValue();
		if (YesNo.YES.equals(uploadData.getIsFile())) {
			file = UploadSupportUtils.getRealFile(this.oid);
			if (!file.exists()) {
				return;
			}
			this.inputStream = new ByteArrayInputStream( FileUtils.readFileToByteArray(file) );
			this.filename = file.getName();
		} else {
			this.inputStream = new ByteArrayInputStream( UploadSupportUtils.getDataBytes(this.oid) );			
			this.filename = UploadSupportUtils.generateRealFileName(uploadData.getShowName());
		}
		if ("view".equals(this.type)) {
			if (file!=null) {
				try {
					this.contentType = FSUtils.getMimeType(file);
				} catch (Exception e) {
					logger.warn( e.getMessage().toString() );
				}				
			} else {
				this.contentType = FSUtils.getMimeType(uploadData.getShowName());
			}
		}		
		if (!StringUtils.isAsciiPrintable(result.getValue().getShowName())) {		
			String userAgent = super.defaultString(super.getHttpServletRequest().getHeader("User-Agent")).toLowerCase();
			if (userAgent.indexOf("firefox")==-1) { // for chrome or other browser.
				this.filename = URLEncoder.encode(result.getValue().getShowName(), Constants.BASE_ENCODING);				
			}
			return;
		}
		this.filename = result.getValue().getShowName();		
	}
	
	/**
	 * core.commonLoadUploadFileAction.action
	 * 
	 * @return
	 * @throws Exception
	 */	
	@ControllerMethodAuthority(programId="CORE_PROGCOMM0002Q")	
	public String execute() throws Exception {
		File file = null;
		try {
			this.loadData(file);
			if ("download".equals(this.type)) {
				this.contentType = "application/octet-stream";
			}
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			this.exceptionPage(e);
		} finally {
			if (file!=null) {
				file = null;
			}
		}
		return SUCCESS;				
	}

	public InputStream getInputStream() {
		if ( this.inputStream == null ) {
			this.inputStream = new ByteArrayInputStream( "".getBytes() );
		}
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

}
