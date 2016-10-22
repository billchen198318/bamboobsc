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
package com.netsteadfast.greenstep.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.model.TransformSegment;
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.po.hbm.TbSysUpload;
import com.netsteadfast.greenstep.po.hbm.TbSysUploadTran;
import com.netsteadfast.greenstep.po.hbm.TbSysUploadTranSegm;
import com.netsteadfast.greenstep.service.ISysUploadService;
import com.netsteadfast.greenstep.service.ISysUploadTranSegmService;
import com.netsteadfast.greenstep.service.ISysUploadTranService;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.SysUploadTranSegmVO;
import com.netsteadfast.greenstep.vo.SysUploadTranVO;
import com.netsteadfast.greenstep.vo.SysUploadVO;

@SuppressWarnings("unchecked")
public class UploadSupportUtils {
	protected static Logger logger=Logger.getLogger(UploadSupportUtils.class);
	public static final String HELP_EXPRESSION_VARIABLE = "datas";
	private static Properties props = new Properties();
	private static String VIEW_MODE_FILE_EXTENSION[] = null;	
	private static ISysUploadService<SysUploadVO, TbSysUpload, String> sysUploadService;
	private static ISysUploadTranService<SysUploadTranVO, TbSysUploadTran, String> sysUploadTranService;
	private static ISysUploadTranSegmService<SysUploadTranSegmVO, TbSysUploadTranSegm, String> sysUploadTranSegmService;
	
	static {
		sysUploadService = (ISysUploadService<SysUploadVO, TbSysUpload, String>)AppContext.getBean("core.service.SysUploadService");
		sysUploadTranService = (ISysUploadTranService<SysUploadTranVO, TbSysUploadTran, String>)
				AppContext.getBean("core.service.SysUploadTranService");
		sysUploadTranSegmService = (ISysUploadTranSegmService<SysUploadTranSegmVO, TbSysUploadTranSegm, String>)
				AppContext.getBean("core.service.SysUploadTranSegmService");
		try {
			props.load(UploadSupportUtils.class.getClassLoader().getResource("META-INF/view-mode-files.properties").openStream());
			VIEW_MODE_FILE_EXTENSION = SimpleUtils.getStr(props.getProperty("FILE_EXTENSION")).trim().split(",");
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public static String getViewMode(String fileShowName) throws Exception {
		String viewMode = YesNo.NO;
		String fileExtensionName = StringUtils.defaultString( getFileExtensionName(fileShowName) ).trim().toLowerCase();
		for (int i=0; VIEW_MODE_FILE_EXTENSION!=null && i<VIEW_MODE_FILE_EXTENSION.length; i++) {
			if (VIEW_MODE_FILE_EXTENSION[i].toLowerCase().equals(fileExtensionName)) {
				viewMode = YesNo.YES;
			}
		}
		return viewMode;
	}
	
	public static SysUploadTranVO findSysUploadTran(String tranId) throws ServiceException, Exception {
		SysUploadTranVO tran = new SysUploadTranVO();
		tran.setTranId( tranId );
		DefaultResult<SysUploadTranVO> result = sysUploadTranService.findByUK(tran);
		if ( result.getValue()==null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		tran = result.getValue();
		return tran;
	}
	
	public static List<TbSysUploadTranSegm> findSysUploadTranSegm(String tranId) throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tranId", tranId);		
		return sysUploadTranSegmService.findListByParams(paramMap);
	}
	
	/**
	 * 把 上傳(upload) 的文字檔切割成 map 
	 * 
	 * @param uploadOid
	 * @param tranId
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public static List<Map<String, String>> getTransformSegmentData(String uploadOid, String tranId) 
			throws ServiceException, Exception {
		List<Map<String, String>> datas = new LinkedList<Map<String, String>>();
		File file = getRealFile(uploadOid);
		SysUploadTranVO tran = findSysUploadTran(tranId);
		List<TbSysUploadTranSegm> segms = findSysUploadTranSegm(tran.getTranId());
		List<String> txtLines = FileUtils.readLines(file, tran.getEncoding());
		for (int i=0; i<txtLines.size(); i++) {
			String str = txtLines.get(i);
			if ( str.length()<1 ) {
				logger.warn( "The file: " + file.getPath() + " found zero line." );
				continue;
			}
			if (i<tran.getBeginLen()) { // not begin line.
				continue;
			}		
			datas.add( fillDataMap(tran, segms, str) );
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(HELP_EXPRESSION_VARIABLE, datas);
		ScriptExpressionUtils.execute(tran.getExprType(), tran.getHelpExpression(), null, paramMap);
		return datas;
	}
	
	/**
	 * 把 上傳(upload) 的XML檔轉成Object 
	 * 
	 * @param uploadOid
	 * @param classesToBeBound
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public Object getTransformObjectData(String uploadOid, Class<?> classesToBeBound) throws ServiceException, Exception {
		Object result = null;
		byte[] xmlBytes = getDataBytes(uploadOid);
		JAXBContext jaxbContext = JAXBContext.newInstance(classesToBeBound);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		result = jaxbUnmarshaller.unmarshal( new ByteArrayInputStream(xmlBytes) );		
		return result;
	}
	
	public static void cleanTempUpload() throws ServiceException, Exception {		
		cleanTempUpload(Constants.getSystem());
	}
	
	public static void cleanTempUpload(String system) throws ServiceException, Exception {
		logger.info("clean upload(" + system + ") temp begin...");
		sysUploadService.deleteTmpContent(system);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("system", system);
		params.put("type", UploadTypes.IS_TEMP);
		params.put("isFile", YesNo.YES);		
		List<TbSysUpload> searchList = sysUploadService.findListByParams(params);		
		for (int i=0; searchList!=null && i<searchList.size(); i++) {
			TbSysUpload entity = searchList.get(i);
			String dir = getUploadFileDir(entity.getSystem(), entity.getSubDir(), entity.getType());
			String fileFullPath = dir + "/" + entity.getFileName();
			File file = new File(fileFullPath);
			if (!file.exists()) {
				file = null;
				continue;
			}
			try {
				logger.warn("delete : " + file.getPath());
				FileUtils.forceDelete(file);
				sysUploadService.delete(entity);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
		logger.info("end...");
	}	
	
	public static String getSubDir() {
		return SimpleUtils.getStrYMD(SimpleUtils.IS_YEAR);
	}
	
	public static String getUploadFileDir(String system, String type) {
		return getUploadFileDir(system, getSubDir(), type);
	}
	
	public static String getUploadFileDir(String system, String subDir, String type) {
		if (StringUtils.isBlank(system) || StringUtils.isBlank(subDir) || StringUtils.isBlank(type)) {
			throw new java.lang.IllegalArgumentException("system, sub-dir and type cann't blank!");
		}
		return Constants.getUploadDir() + "/" + system + "/" + type + "/" + subDir + "/";
	}	
	
	public static File mkdirUploadFileDir(String system, String type) throws IOException, Exception {
		return mkdirUploadFileDir(system, getSubDir(), type);
	}
	
	public static File mkdirUploadFileDir(String system, String subDir, String type) throws IOException, Exception {
		String uploadDir = getUploadFileDir(system, subDir, type);
		File dir = new File(uploadDir);
		if (dir.exists() && dir.isDirectory()) {
			return dir;
		}
		FileUtils.forceMkdir(dir);
		return dir;
	}
	
	public static String generateRealFileName(String showName) {
		if (StringUtils.isBlank(showName)) {
			throw new java.lang.IllegalArgumentException("name is blank!");
		}
		String extension = getFileExtensionName(showName);
		if (StringUtils.isBlank(extension)) {
			return SimpleUtils.getUUIDStr();
		}
		if (extension.length()>13) { // uuid 加上 點 "." 後是 37 字元 , TB_SYS_UPLOAD.FILE_NAME 最大為 50 , 50-37 = 13
			extension = extension.substring(0, 13);
		}
		return SimpleUtils.getUUIDStr() + "." + extension;
	}	
	
	public static String generateRealFileName(File file) {
		if (file==null) {
			throw new java.lang.IllegalArgumentException("file is null!");
		}
		return generateRealFileName( file.getName() );
	}
	
	public static File getRealFile(String uploadOid) throws ServiceException, IOException, Exception {
		if (StringUtils.isBlank(uploadOid)) {
			throw new Exception("parameter is blank!");
		}
		SysUploadVO uploadObj = findUpload(uploadOid);
		File packageFile = null;
		if (!YesNo.YES.equals(uploadObj.getIsFile())) {
			File dir = new File( Constants.getWorkTmpDir() + "/" + UploadSupportUtils.class.getSimpleName() );
			if (!dir.exists() || !dir.isDirectory()) {
				FileUtils.forceMkdir(dir);
			}			
			String tmpFileName = dir.getPath() + "/" + SimpleUtils.getUUIDStr() + "." + getFileExtensionName(uploadObj.getShowName());
			dir = null;
			OutputStream fos = null;
			try {
				packageFile = new File( tmpFileName );
				fos = new FileOutputStream(packageFile);
				IOUtils.write(uploadObj.getContent(), fos);		
				fos.flush();
			} catch (IOException e) {
				throw e;
			} finally {
				if (fos!=null) {
					fos.close();
				}
				fos = null;
			}			
		} else {
			String uploadDir = getUploadFileDir(uploadObj.getSystem(), uploadObj.getSubDir(), uploadObj.getType());
			packageFile = new File( uploadDir + "/" + uploadObj.getFileName() );			
		}		
		if (!packageFile.exists()) {
			throw new Exception("File is missing: " + uploadObj.getFileName() );
		}
		return packageFile;
	}
	
	public static byte[] getDataBytes(String uploadOid) throws ServiceException, IOException, Exception {
		if (StringUtils.isBlank(uploadOid)) {
			throw new Exception("parameter is blank!");
		}
		byte datas[] = null;
		SysUploadVO uploadObj = findUpload(uploadOid);
		datas = uploadObj.getContent();
		if (YesNo.YES.equals(uploadObj.getIsFile())) {
			String uploadDir = getUploadFileDir(uploadObj.getSystem(), uploadObj.getSubDir(), uploadObj.getType());
			File file = new File( uploadDir + "/" + uploadObj.getFileName() );
			datas = FileUtils.readFileToByteArray(file);
			file = null;
		}
		return datas;
	}
	
	public static DefaultResult<Boolean> updateType(String oid, String type) throws ServiceException, IOException, Exception {
		DefaultResult<SysUploadVO> uploadResult = sysUploadService.findForNoByteContent(oid);
		if (uploadResult.getValue()==null) {
			throw new ServiceException(uploadResult.getSystemMessage().getValue());
		}
		SysUploadVO upload = uploadResult.getValue();
		if (!YesNo.YES.equals(upload.getIsFile())) {
			return sysUploadService.updateTypeOnly(oid, type);
		}
		DefaultResult<Boolean> result = sysUploadService.updateTypeOnly(oid, type);
		if (result.getValue()==null || !result.getValue()) {
			return result;
		}
		// move file to new dir.
		String oldFullPath = getUploadFileDir(upload.getSystem(), upload.getSubDir(), upload.getType()) + upload.getFileName();
		String newFullPath = getUploadFileDir(upload.getSystem(), upload.getSubDir(), type) + upload.getFileName();
		File newFile = new File(newFullPath);
		if (newFile.isFile() && newFile.exists()) {
			newFile = null;
			throw new Exception("error. file exists, cannot operate!");
		}
		newFile = null;
		File oldFile = new File(oldFullPath);
		if (!oldFile.exists()) {
			oldFile = null;
			throw new Exception("error. file no exists: " + oldFullPath);
		}
		oldFile = null;
		FSUtils.mv(oldFullPath, newFullPath);
		/*
		FSUtils.cp(oldFullPath, newFullPath);
		FSUtils.rm(oldFullPath);
		*/
		return result;
	}
	
	public static String create(String system, String type, boolean isFile, File file, 
			String showName) throws ServiceException, IOException, Exception {
		if (StringUtils.isBlank(type) || null == file || StringUtils.isBlank(showName)) {
			throw new Exception("parameter is blank!");
		}				
		if (!file.exists()) {
			throw new Exception("file no exists!");
		}
		SysUploadVO upload = new SysUploadVO();		
		upload.setIsFile( ( isFile ? YesNo.YES : YesNo.NO ) );
		upload.setShowName(showName);
		upload.setSystem(system);
		upload.setType(type);
		upload.setSubDir( getSubDir() );		
		if (isFile) {
			String uploadDir = getUploadFileDir(system, type);
			String uploadFileName = generateRealFileName(file);
			mkdirUploadFileDir(system, type);
			FSUtils.cp( file.getPath(), uploadDir + "/" + uploadFileName );
			upload.setFileName( uploadFileName );			
		} else {
			upload.setContent( FileUtils.readFileToByteArray(file) );
			upload.setFileName( " " );			
		}		
		DefaultResult<SysUploadVO> result =  sysUploadService.saveObject(upload);
		if ( result.getValue() == null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}		
		return result.getValue().getOid();
	}
	
	public static String create(String system, String type, boolean isFile, byte[] datas, 
			String showName) throws ServiceException, IOException, Exception {
		if (StringUtils.isBlank(type) || null == datas || StringUtils.isBlank(showName)) {
			throw new Exception("parameter is blank!");
		}				
		SysUploadVO upload = new SysUploadVO();		
		upload.setIsFile( ( isFile ? YesNo.YES : YesNo.NO ) );
		upload.setShowName(showName);
		upload.setSystem(system);
		upload.setType(type);
		upload.setSubDir( getSubDir() );		
		if (isFile) {
			String uploadDir = getUploadFileDir(system, type);
			String uploadFileName = generateRealFileName(showName);
			mkdirUploadFileDir(system, type);
			File file = null;
			try {
				file = new File( uploadDir + "/" + uploadFileName );
				FileUtils.writeByteArrayToFile(file, datas);
			} catch (Exception e) {
				throw e;
			} finally {
				file = null;
			}
			upload.setFileName( uploadFileName );			
		} else {
			upload.setContent( datas );
			upload.setFileName( " " );			
		}		
		DefaultResult<SysUploadVO> result =  sysUploadService.saveObject(upload);
		if ( result.getValue() == null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}		
		return result.getValue().getOid();
	}	
	
	public static String getFileExtensionName(String fileFullName) {
		if (StringUtils.isBlank(fileFullName)) {
			return "";
		}
		String extension = "";
		String[] tmp = fileFullName.split("[.]");
		for (int i=1; tmp!=null && i<tmp.length; i++) {
			extension = tmp[i];
		}
		return extension;
	}
	
	public static SysUploadVO findUpload(String uploadOid) throws ServiceException, Exception {
		if (StringUtils.isBlank(uploadOid)) {
			throw new Exception("Upload OID parameter is blank!");
		}		
		SysUploadVO uploadObj = new SysUploadVO();
		uploadObj.setOid(uploadOid);
		DefaultResult<SysUploadVO> result = sysUploadService.findObjectByOid(uploadObj);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		uploadObj = result.getValue();
		return uploadObj;
	}
	
	public static SysUploadVO findUploadNoByteContent(String uploadOid) throws ServiceException, Exception {
		if (StringUtils.isBlank(uploadOid)) {
			throw new Exception("Upload OID parameter is blank!");
		}
		DefaultResult<SysUploadVO> result = sysUploadService.findForNoByteContent(uploadOid);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().toString() );
		}
		return result.getValue();
	}
	
	private static Map<String, String> fillDataMap(SysUploadTranVO tran, List<TbSysUploadTranSegm> segms, 
			String strLine) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		if (TransformSegment.TEXT_MODE.equals(tran.getSegmMode())) { // 用字串切割
			for (TbSysUploadTranSegm segm : segms) {
				if ( strLine.length() < segm.getEnd() ) {
					throw new Exception("data format error.");
				}
				dataMap.put(segm.getName(), strLine.substring(segm.getBegin(), segm.getEnd()) );
			}			
		} else if (TransformSegment.BYTE_MODE.equals(tran.getSegmMode())) { // 用 byte 切割
			byte[] dataBytes = strLine.getBytes( tran.getEncoding() );
			for (TbSysUploadTranSegm segm : segms) {
				if ( dataBytes.length < segm.getEnd() ) {
					throw new Exception("data format error.");
				}
				String dataStr = new String(
						ArrayUtils.subarray(dataBytes, segm.getBegin(), segm.getEnd()), tran.getEncoding());
				dataMap.put(segm.getName(), dataStr);
			}
		} else { // 用符號來分成array
			String tmpStr[] = strLine.split(tran.getSegmSymbol());			
			for (TbSysUploadTranSegm segm : segms) {
				if (segm.getBegin() != segm.getEnd()) {
					throw new Exception("segment config begin/end error.");
				}
				if ( tmpStr == null || tmpStr.length < segm.getBegin() ) {
					throw new Exception("data format error.");
				}
				dataMap.put(segm.getName(), tmpStr[segm.getBegin()]);
			}
		}
		return dataMap;
	}
	
}
