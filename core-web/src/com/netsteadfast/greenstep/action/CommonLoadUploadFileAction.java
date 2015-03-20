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

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.util.FSUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;

@ControllerAuthority(check=true)
@Controller("core.web.controller.CommonLoadUploadFileAction")
@Scope
public class CommonLoadUploadFileAction extends BaseSupportAction {
	private static final long serialVersionUID = 582037391631119994L;
	private InputStream inputStream = null;
	private String filename = "";
	private String contentType = "";
	private String type = "view"; // view or download
	private String oid = ""; // TB_UPLOAD.OID
	
	public CommonLoadUploadFileAction() {
		super();
	}
	
	private void loadData(File file) throws ServiceException, Exception {
		if (StringUtils.isBlank(this.oid)) {
			return;
		}
		file = UploadSupportUtils.getRealFile(this.oid);
		if (!file.exists()) {
			return;
		}
		this.inputStream = new ByteArrayInputStream( FileUtils.readFileToByteArray(file) );
		if ("view".equals(this.type)) {
			this.contentType = FSUtils.getMimeType4URL(file);
		}
		this.filename = file.getName();
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
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
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
