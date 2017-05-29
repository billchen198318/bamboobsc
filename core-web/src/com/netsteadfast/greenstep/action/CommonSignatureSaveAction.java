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

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

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
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.po.hbm.TbSysUpload;
import com.netsteadfast.greenstep.service.ISysUploadService;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.SysUploadVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.CommonSignatureSaveAction")
@Scope
public class CommonSignatureSaveAction extends BaseJsonAction {
	private static final long serialVersionUID = -280753668830112169L;
	protected Logger logger=Logger.getLogger(CommonSignatureSaveAction.class);
	private ISysUploadService<SysUploadVO, TbSysUpload, String> sysUploadService;
	private String message = "";
	private String success = IS_NO;
	private String uploadOid = "";
	
	public CommonSignatureSaveAction() {
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
	
	private byte[] getImageBytes(String signatureData) throws Exception {
		BufferedImage bufferImage = SimpleUtils.decodeToImage( SimpleUtils.getPNGBase64Content(signatureData) );
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( bufferImage, "png", baos );
		baos.flush();
		byte[] imageInBytes = baos.toByteArray();
		baos.close();
		return imageInBytes;
	}
	
	private void saveData() throws ServiceException, Exception {
		String system = super.defaultString( this.getFields().get("system") );
		String signatureData = super.defaultString( this.getFields().get("signatureData") );
		signatureData = SimpleUtils.deHex( signatureData );
		if (!SimpleUtils.isPNGBase64Content( signatureData )) {
			super.throwMessage( "Signature data error!" );
		}		
		SysUploadVO upload = new SysUploadVO();
		upload.setContent( this.getImageBytes(signatureData) );
		upload.setIsFile(YesNo.NO);
		upload.setFileName( SimpleUtils.getUUIDStr()+".png" );
		upload.setShowName( "signature" + "-" + System.currentTimeMillis() + ".png" );
		upload.setSystem(system);
		upload.setSubDir( UploadSupportUtils.getSubDir() );
		upload.setType(UploadTypes.IS_TEMP);
		DefaultResult<SysUploadVO> result = this.sysUploadService.saveObject(upload);
		this.message = result.getSystemMessage().getValue();
		if ( result.getValue()!=null ) {
			this.success = IS_YES;
			this.uploadOid = result.getValue().getOid();
		}
	}
	
	/**
	 * core.signatureSaveAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@JSON(serialize=false)
	@ControllerMethodAuthority(programId="CORE_PROGCOMM0003Q")
	public String execute() throws Exception {
		try {
			if (!this.allowJob()) {
				this.message = this.getNoAllowMessage();
				return SUCCESS;
			}
			this.saveData();
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
	public String getUploadOid() {
		return uploadOid;
	}
	
	@JSON
	@Override
	public Map<String, String> getFieldsMessage() {
		return this.fieldsMessage;
	}
	
}
