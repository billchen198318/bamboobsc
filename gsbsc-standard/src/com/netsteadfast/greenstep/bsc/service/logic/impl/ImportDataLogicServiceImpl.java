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
package com.netsteadfast.greenstep.bsc.service.logic.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.ServiceAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodAuthority;
import com.netsteadfast.greenstep.base.model.ServiceMethodType;
import com.netsteadfast.greenstep.base.model.SystemMessage;
import com.netsteadfast.greenstep.base.service.logic.BaseLogicService;
import com.netsteadfast.greenstep.bsc.service.IPerspectiveService;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.bsc.service.logic.IImportDataLogicService;
import com.netsteadfast.greenstep.bsc.service.logic.IPerspectiveLogicService;
import com.netsteadfast.greenstep.bsc.service.logic.IVisionLogicService;
import com.netsteadfast.greenstep.po.hbm.BbPerspective;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

@ServiceAuthority(check=true)
@Service("bsc.service.logic.ImportDataLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class ImportDataLogicServiceImpl extends BaseLogicService implements IImportDataLogicService {
	protected Logger logger=Logger.getLogger(ImportDataLogicServiceImpl.class);
	private IVisionLogicService visionLogicService;
	private IVisionService<VisionVO, BbVision, String> visionService; 
	private IPerspectiveLogicService perspectiveLogicService;
	private IPerspectiveService<PerspectiveVO, BbPerspective, String> perspectiveService;
	
	public ImportDataLogicServiceImpl() {
		super();
	}
	
	public IVisionLogicService getVisionLogicService() {
		return visionLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.VisionLogicService")
	@Required		
	public void setVisionLogicService(IVisionLogicService visionLogicService) {
		this.visionLogicService = visionLogicService;
	}

	public IVisionService<VisionVO, BbVision, String> getVisionService() {
		return visionService;
	}

	@Autowired
	@Resource(name="bsc.service.VisionService")
	@Required		
	public void setVisionService(
			IVisionService<VisionVO, BbVision, String> visionService) {
		this.visionService = visionService;
	}

	public IPerspectiveLogicService getPerspectiveLogicService() {
		return perspectiveLogicService;
	}

	@Autowired
	@Resource(name="bsc.service.logic.PerspectiveLogicService")
	@Required		
	public void setPerspectiveLogicService(
			IPerspectiveLogicService perspectiveLogicService) {
		this.perspectiveLogicService = perspectiveLogicService;
	}

	public IPerspectiveService<PerspectiveVO, BbPerspective, String> getPerspectiveService() {
		return perspectiveService;
	}

	@Autowired
	@Resource(name="bsc.service.PerspectiveService")
	@Required	
	public void setPerspectiveService(
			IPerspectiveService<PerspectiveVO, BbPerspective, String> perspectiveService) {
		this.perspectiveService = perspectiveService;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT, ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
	@Override
	public DefaultResult<Boolean> importVisionCsv(String uploadOid) throws ServiceException, Exception {
		List<Map<String, String>> csvResults = UploadSupportUtils.getTransformSegmentData(uploadOid, "TRAN001");
		if (csvResults.size()<1) {
			throw new ServiceException( SysMessageUtil.get(GreenStepSysMsgConstants.DATA_NO_EXIST) );
		}
		boolean success = false;
		DefaultResult<Boolean> result = new DefaultResult<Boolean>();		
		StringBuilder msg = new StringBuilder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (int i=0; i<csvResults.size(); i++) {
			int row = i+1;
			Map<String, String> data = csvResults.get(i);
			String visId = data.get("VIS_ID");
			String title = data.get("TITLE");
			String content = data.get("CONTENT");
			if ( super.isBlank(visId) ) {
				msg.append("row: " + row + " id is blank.\n");
				continue;
			}
			if ( super.isBlank(title) ) {
				msg.append("row: " + row + " title is blank.\n");
				continue;
			}			
			if ( super.isBlank(content) ) {
				msg.append("row: " + row + " content is blank.\n");
				continue;
			}
			/*
			 * call unEscapeCsv2 write in HELP_EXPRESSION
			 * 
			visId = SimpleUtils.unEscapeCsv2(visId);
			title = SimpleUtils.unEscapeCsv2(title);
			content = SimpleUtils.unEscapeCsv2(content);
			*/
			VisionVO vision = new VisionVO();
			vision.setVisId(visId);
			vision.setTitle(title);			
			vision.setContent( content.getBytes(Constants.BASE_ENCODING) );			
			paramMap.put("visId", visId);
			if ( this.visionService.countByParams(paramMap) > 0 ) { // update
				DefaultResult<VisionVO> oldResult = this.visionService.findByUK(vision);
				vision.setOid( oldResult.getValue().getOid() );
				this.visionLogicService.update(vision);
			} else { // insert
				this.visionLogicService.create(vision);
			}
			success = true; 
		}
		if ( msg.length() > 0 ) {
			result.setSystemMessage( new SystemMessage(msg.toString()) );
 		} else {
 			result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_SUCCESS)) ); 			
 		}
		result.setValue(success);
		return result;
	}

	@ServiceMethodAuthority(type={ServiceMethodType.INSERT, ServiceMethodType.UPDATE})
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false,
			rollbackFor={RuntimeException.class, IOException.class, Exception.class} )	
	@Override
	public DefaultResult<Boolean> importPerspectivesCsv(String uploadOid) throws ServiceException, Exception {		
		List<Map<String, String>> csvResults = UploadSupportUtils.getTransformSegmentData(uploadOid, "TRAN002");
		if (csvResults.size()<1) {
			throw new ServiceException( SysMessageUtil.get(GreenStepSysMsgConstants.DATA_NO_EXIST) );
		}
		boolean success = false;
		DefaultResult<Boolean> result = new DefaultResult<Boolean>();		
		StringBuilder msg = new StringBuilder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (int i=0; i<csvResults.size(); i++) {
			int row = i+1;
			Map<String, String> data = csvResults.get(i);
			String perId = data.get("PER_ID");
			String visId = data.get("VIS_ID");
			String name = data.get("NAME");
			String weight = data.get("WEIGHT");
			String target = data.get("TARGET");
			String min = data.get("MIN");
			String description = data.get("DESCRIPTION");	
			if ( super.isBlank(perId) ) {
				msg.append("row: " + row + " perspective-id is blank.\n");
				continue;
			}
			if ( super.isBlank(visId) ) {
				msg.append("row: " + row + " vision-id is blank.\n");
				continue;
			}		
			if ( super.isBlank(name) ) {
				msg.append("row: " + row + " name is blank.\n");
				continue;
			}			
			if ( super.isBlank(weight) ) {
				msg.append("row: " + row + " weight is blank.\n");
				continue;				
			}
			if ( super.isBlank(target) ) {
				msg.append("row: " + row + " target is blank.\n");
				continue;				
			}
			if ( super.isBlank(min) ) {
				msg.append("row: " + row + " min is blank.\n");
				continue;				
			}
			/*
			 * call unEscapeCsv2 write in HELP_EXPRESSION
			 * 
			perId = SimpleUtils.unEscapeCsv2(perId);
			visId = SimpleUtils.unEscapeCsv2(visId);
			name = SimpleUtils.unEscapeCsv2(name);
			weight = SimpleUtils.unEscapeCsv2(weight);
			target = SimpleUtils.unEscapeCsv2(target);
			min = SimpleUtils.unEscapeCsv2(min);
			description = SimpleUtils.unEscapeCsv2(description);
			*/				
			if ( !NumberUtils.isNumber(weight) ) {
				msg.append("row: " + row + " weight is not number.\n");
				continue;					
			}
			if ( !NumberUtils.isNumber(target) ) {
				msg.append("row: " + row + " target is not number.\n");
				continue;					
			}
			if ( !NumberUtils.isNumber(min) ) {
				msg.append("row: " + row + " min is not number.\n");
				continue;					
			}		
			paramMap.clear();
			paramMap.put("visId", visId);
			if ( this.visionService.countByParams(paramMap) < 1 ) {
				throw new ServiceException( "row: " + row + " vision is not found " + visId );
			}
			DefaultResult<VisionVO> visionResult = this.visionService.findForSimpleByVisId(visId);
			if ( visionResult.getValue()==null) {
				throw new ServiceException( visionResult.getSystemMessage().getValue() );
			}
			PerspectiveVO perspective = new PerspectiveVO();
			perspective.setPerId(perId);
			perspective.setVisId(visId);
			perspective.setName(name);
			perspective.setWeight( new BigDecimal(weight) );
			perspective.setTarget( Float.valueOf(target) );
			perspective.setMin( Float.valueOf(min) );
			perspective.setDescription(description);		
			paramMap.clear();
			paramMap.put("perId", perId);			
			if ( this.perspectiveService.countByParams(paramMap) > 0 ) { // update
				DefaultResult<PerspectiveVO> oldResult = this.perspectiveService.findByUK(perspective);
				perspective.setOid( oldResult.getValue().getOid() );				
				this.perspectiveLogicService.update(perspective, visionResult.getValue().getOid() );
			} else { // insert
				this.perspectiveLogicService.create(perspective, visionResult.getValue().getOid() );
			}
			success = true; 
		}
		if ( msg.length() > 0 ) {
			result.setSystemMessage( new SystemMessage(msg.toString()) );
 		} else {
 			result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.UPDATE_SUCCESS)) ); 			
 		}
		result.setValue(success);
		return result;
	}

}
