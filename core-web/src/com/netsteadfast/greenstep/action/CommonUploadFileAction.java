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

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.action.BaseJsonAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.po.hbm.TbSysUpload;
import com.netsteadfast.greenstep.service.ISysUploadService;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.SysUploadVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.CommonUploadFileAction")
@Scope
public class CommonUploadFileAction extends BaseJsonAction {
	private static final long serialVersionUID = -6201813020831620469L;
	protected Logger logger=Logger.getLogger(CommonUploadFileAction.class);
	private ISysUploadService<SysUploadVO, TbSysUpload, String> sysUploadService;
	private String message = "";
	private String success = IS_NO;
	private String system = ""; // 需要存在 TB_SYS.SYS_ID 的id
	private String type = "";  // UploadTypes.TYPES[]
	private String uploadOid = ""; // 產生的 TB_SYS_UPLOAD.OID
	private String isFile = YesNo.YES; // 當 'N' 時資料存到 blob 欄位, 'Y' 以檔案方式
	private File upload = null;
	private String uploadContentType=""; //The content type of the file
	private String uploadFileName=""; //The uploaded file name	
	
	private String showName = ""; // 給  getFileNames 用的
	private String fileName = ""; // 給  getFileNames 用的
	private String fileExist = YesNo.NO; // 給  getFileNames 用的
	
	public CommonUploadFileAction() {
		super();
	}
	
	@JSON(serialize=false)
	public ISysUploadService<SysUploadVO, TbSysUpload, String> getSysUploadService() {
		return sysUploadService;
	}

	@Autowired
	@Resource(name="core.service.SysUploadService")		
	public void setSysUploadService(
			ISysUploadService<SysUploadVO, TbSysUpload, String> sysUploadService) {
		this.sysUploadService = sysUploadService;
	}

	private String copy2UploadDir() throws IOException, Exception {
		if (!YesNo.YES.equals(this.isFile)) {
			return "";
		}
		File uploadDir = UploadSupportUtils.mkdirUploadFileDir(system, type);
		String realFileName = UploadSupportUtils.generateRealFileName(this.uploadFileName);
		File realFile = new File( uploadDir.getPath() + "/" + realFileName );
		try {
			FileUtils.copyFile(this.getUpload(), realFile);
		} catch (IOException e) {
			throw e;
		} finally {
			realFile = null;
			uploadDir = null;
		}
		return realFileName;
	}
	
	private void upload() throws ControllerException, AuthorityException, ServiceException, IOException, Exception {
		if (this.getUpload() == null) {
			throw new ControllerException( SysMessageUtil.get(GreenStepSysMsgConstants.UPLOAD_FILE_NO_SELECT) );
		}
		if (StringUtils.isBlank(this.system) || StringUtils.isBlank(this.type)
				|| !UploadTypes.check(this.type) ) {
			throw new ControllerException("System and type is required!");
		}
		String fileName = this.copy2UploadDir();
		SysUploadVO uploadObj = new SysUploadVO();
		uploadObj.setSystem(this.system);
		uploadObj.setSubDir(UploadSupportUtils.getSubDir());
		uploadObj.setType(this.type);
		uploadObj.setFileName(fileName);
		uploadObj.setShowName( this.uploadFileName );
		if (uploadObj.getShowName().length()>255) {
			uploadObj.setShowName( uploadObj.getShowName().substring(0, 255) );
		}
		uploadObj.setIsFile(this.isFile);
		if (!YesNo.YES.equals(this.isFile)) {
			uploadObj.setContent( FileUtils.readFileToByteArray(this.getUpload()) );
		}
		DefaultResult<SysUploadVO> result=this.sysUploadService.saveObject(uploadObj);
		this.message = result.getSystemMessage().getValue();
		if (result.getValue()==null) {
			return;
		}
		this.message = this.message + "upload file success!";
		this.success = IS_YES;
		this.uploadOid = result.getValue().getOid();
	}
	
	private void loadNames() throws ControllerException, AuthorityException, ServiceException, IOException, Exception {
		if (StringUtils.isBlank(this.uploadOid)) {
			throw new ControllerException("Upload oid is required!");
		}
		DefaultResult<SysUploadVO> result = this.sysUploadService.findForNoByteContent(this.uploadOid);
		if (result.getValue()==null) {
			this.message = result.getSystemMessage().getValue();
			return;
		}
		SysUploadVO uploadData = result.getValue();
		this.fileName = super.defaultString( uploadData.getFileName() ).trim();
		this.showName = super.defaultString( uploadData.getShowName() ).trim();
		//this.showName = StringEscapeUtils.escapeEcmaScript( this.showName );
		//this.showName = StringEscapeUtils.escapeHtml4( this.showName );
		this.showName = this.showName.replaceAll("'", "’").replaceAll("\"", "＂").replaceAll("<", "＜").replaceAll(">", "＞");		
		this.success = IS_YES;
		this.message = "load success!";
		if (YesNo.YES.equals(uploadData.getIsFile())) {
			@SuppressWarnings("unused")
			File dataFile = UploadSupportUtils.getRealFile(uploadData.getOid());
			this.fileExist = YesNo.YES;
			dataFile = null;
		} else {
			this.fileExist = YesNo.YES;
		}
	}
	
	/**
	 * core.commonUploadFileAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROGCOMM0002Q")
	public String execute() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}			
			this.upload();
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.message = e.getMessage().toString();
		} catch (Exception e) {
			this.message = this.logException(e);
			this.success = IS_EXCEPTION;
		}
		return SUCCESS;		
	}
	
	/**
	 * core.commonLoadUploadFileNamesAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false)
	@ControllerMethodAuthority(programId="CORE_PROGCOMM0002Q")
	public String getFileNames() throws Exception {
		this.fileExist = YesNo.NO;
		this.success = IS_NO;
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}			
			this.loadNames();
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
	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	@JSON
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JSON
	public String getUploadOid() {
		return uploadOid;
	}

	public void setUploadOid(String uploadOid) {
		this.uploadOid = uploadOid;
	}

	@JSON
	public String getIsFile() {
		return isFile;
	}

	public void setIsFile(String isFile) {
		this.isFile = isFile;
	}

	@JSON(serialize=false)
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	@JSON(serialize=false)
	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	@JSON(serialize=false)
	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileExist() {
		return fileExist;
	}

	public void setFileExist(String fileExist) {
		this.fileExist = fileExist;
	}
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
}
