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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.model.SystemFtpClientData;
import com.netsteadfast.greenstep.model.SystemFtpClientModel;
import com.netsteadfast.greenstep.model.SystemFtpClientResultObj;
import com.netsteadfast.greenstep.model.TransformSegment;
import com.netsteadfast.greenstep.po.hbm.TbSysFtp;
import com.netsteadfast.greenstep.po.hbm.TbSysFtpTran;
import com.netsteadfast.greenstep.po.hbm.TbSysFtpTranSegm;
import com.netsteadfast.greenstep.service.ISysFtpService;
import com.netsteadfast.greenstep.service.ISysFtpTranSegmService;
import com.netsteadfast.greenstep.service.ISysFtpTranService;
import com.netsteadfast.greenstep.vo.SysFtpTranSegmVO;
import com.netsteadfast.greenstep.vo.SysFtpTranVO;
import com.netsteadfast.greenstep.vo.SysFtpVO;

@SuppressWarnings("unchecked")
public class SystemFtpClientUtils {
	protected static Logger logger=Logger.getLogger(SystemFtpClientUtils.class);
	private static final int MAX_SIZE_KB = 128;
	private static ISysFtpService<SysFtpVO, TbSysFtp, String> sysFtpService;
	private static ISysFtpTranService<SysFtpTranVO, TbSysFtpTran, String> sysFtpTranService;
	private static ISysFtpTranSegmService<SysFtpTranSegmVO, TbSysFtpTranSegm, String> sysFtpTranSegmService;
	
	static {
		sysFtpService = (ISysFtpService<SysFtpVO, TbSysFtp, String>)AppContext.getBean("core.service.SysFtpService");
		sysFtpTranService = (ISysFtpTranService<SysFtpTranVO, TbSysFtpTran, String>)AppContext.getBean("core.service.SysFtpTranService");
		sysFtpTranSegmService = (ISysFtpTranSegmService<SysFtpTranSegmVO, TbSysFtpTranSegm, String>)
				AppContext.getBean("core.service.SysFtpTranSegmService");
	}	
	
	public static SysFtpTranVO findSysFtpTran(String tranId) throws ServiceException, Exception {
		SysFtpTranVO tran = new SysFtpTranVO();
		tran.setTranId( tranId );
		DefaultResult<SysFtpTranVO> result = sysFtpTranService.findByUK(tran);
		if ( result.getValue() == null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		tran = result.getValue();
		return tran;
	}
	
	public static SysFtpVO findSysFtp(String ftpId) throws ServiceException, Exception {
		SysFtpVO ftp = new SysFtpVO();
		ftp.setId( ftpId );
		DefaultResult<SysFtpVO> result = sysFtpService.findByUK(ftp);
		if ( result.getValue() == null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		ftp = result.getValue();
		return ftp;
	}
	
	public static List<TbSysFtpTranSegm> findSysFtpTranSegm(String ftpId, String tranId) throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ftpId", ftpId);
		paramMap.put("tranId", tranId);
		return sysFtpTranSegmService.findListByParams(paramMap);
	}
	
	/**
	 * 取出FTP或SFTP上的檔案
	 * 
	 * @param tranId	TB_SYS_FTP_TRAN.TRAN_ID
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public static SystemFtpClientResultObj getFileOnly(String tranId) throws ServiceException, Exception {		
		if ( StringUtils.isBlank(tranId) ) {
			throw new Exception( SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK) );
		}
		logger.info("getFileOnly begin...");
		SystemFtpClientResultObj resultObj = new SystemFtpClientResultObj();
		SysFtpTranVO tran = findSysFtpTran(tranId);
		SysFtpVO ftp = findSysFtp( tran.getFtpId() );
		List<TbSysFtpTranSegm> segms = findSysFtpTranSegm(tran.getFtpId(), tran.getTranId());
		resultObj.setSysFtp(ftp);
		resultObj.setSysFtpTran(tran);
		resultObj.setSysFtpTranSegms(segms);
		getFiles(resultObj);	
		logger.info("getFileOnly end...");
		return resultObj;
	}
	
	/**
	 * 取出FTP或SFTP上的檔案, 並將TXT文字檔切割成資料放至map中, XML則只將內容讀取至變數中
	 * 
	 * @param tranId			TB_SYS_FTP_TRAN.TRAN_ID
	 * @param classesToBeBound	將xml轉成Object ( 只有在TRAN_TYPE = 'GET-XML' 才需要 ) 
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public static SystemFtpClientResultObj getDatas(String tranId) throws ServiceException, Exception {
		logger.info("getDatas begin...");
		SystemFtpClientResultObj resultObj = getFileOnly(tranId);
		SysFtpTranVO tran = resultObj.getSysFtpTran();
		if (SystemFtpClientModel.TRAN_GET_TEXT.equals(tran.getTranType())) { // 分割 txt 檔案
			processText(resultObj);
		} else if (SystemFtpClientModel.TRAN_GET_XML.equals(tran.getTranType())) { // 處理 xml 檔案			
			processXml(resultObj);			
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(SystemFtpClientModel.RESULT_OBJ_VARIABLE, resultObj);
		ScriptExpressionUtils.execute(tran.getExprType(), tran.getHelpExpression(), null, paramMap);
		logger.info("getDatas end...");
		return resultObj;
	}
	
	/**
	 * 放檔案至FTP或SFTP上
	 * 
	 * @param tranId	TB_SYS_FTP_TRAN.TRAN_ID
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	public static boolean putFiles(String tranId) throws ServiceException, Exception {
		if ( StringUtils.isBlank(tranId) ) {
			throw new Exception( SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK) );
		}
		SysFtpTranVO tran = findSysFtpTran(tranId);
		SysFtpVO ftp = findSysFtp( tran.getFtpId() );
		if (!SystemFtpClientModel.TRAN_PUT.equals(tran.getTranType())) {
			logger.warn("Not a PUT mode TB_SYS_FTP_TRAN.TRAN_ID: " + tranId);
			return false;
		}
		/**
		 * 這裡的 NAME_EXPRESSION 回傳的 names 檔案文字路徑必須 如 : /var/upload/20150514.txt
		 * **不可** 是 20150514.txt
		 */
		List<String> fileFullPathNames = getFileNames(tran.getExprType(), tran.getNameExpression()); 
		if (SystemFtpClientModel.FTP.equals(ftp.getType())) { // FTP
			putFilesByFtp(ftp, tran, fileFullPathNames);
		} else { // SFTP
			putFileBySFtp(ftp, tran, fileFullPathNames);			
		}
		return true;
	}
	
	/**
	 * 取出FTP或SFTP上的檔案, 然後將檔案產出成 TB_SYS_UPLOAD, 回傳產完的 UPLOAD OID
	 * 
	 * @param system
	 * @param uploadType
	 * @param isFileMode
	 * @param tranId		TB_SYS_FTP_TRAN.TRAN_ID
	 * @return
	 * @throws ServiceException
	 * @throws IOException
	 * @throws Exception
	 */
	public static List<String> copyFileToUpload(String system, String uploadType, boolean isFileMode, 
			String tranId) throws ServiceException, IOException, Exception {
		SystemFtpClientResultObj resultObj = getFileOnly(tranId);
		return copyFileToUpload(system, uploadType, isFileMode, resultObj);
	}
	
	/**
	 * 將檔案產出成 TB_SYS_UPLOAD, 回傳產完的 UPLOAD OID
	 * 
	 * @param system
	 * @param uploadType
	 * @param isFileMode
	 * @param resultObj
	 * @return
	 * @throws ServiceException
	 * @throws IOException
	 * @throws Exception
	 */
	public static List<String> copyFileToUpload(String system, String uploadType, boolean isFileMode,
			SystemFtpClientResultObj resultObj) throws ServiceException, IOException, Exception {
		List<String> oids = new ArrayList<String>();
		if (resultObj==null || resultObj.getFiles()==null || resultObj.getFiles().size()<1) {
			return oids;
		}
		for (File file : resultObj.getFiles()) {
			oids.add(
					UploadSupportUtils.create(system, uploadType, isFileMode, file, file.getName()) 
			);
		}
		return oids;
	}
	
	private static void processText(SystemFtpClientResultObj resultObj) throws Exception {
		SysFtpTranVO tran = resultObj.getSysFtpTran();
		List<SystemFtpClientData> datas = new LinkedList<SystemFtpClientData>();
		List<TbSysFtpTranSegm> segms = resultObj.getSysFtpTranSegms();		
		for (File file : resultObj.getFiles()) {
			SystemFtpClientData ftpData = new SystemFtpClientData();
			List<Map<String, String>> fillDataList = new LinkedList<Map<String, String>>();
			logWarnFileSize(file);
			List<String> strLines = FileUtils.readLines(file, resultObj.getSysFtpTran().getEncoding());
			if (YesNo.YES.equals(resultObj.getSysFtpTran().getUseSegm())) {
				for (int i=0; i<strLines.size(); i++) { 
					String strData = strLines.get(i);
					if ( strData.length() < 1 ) {
						logger.warn( "The file: " + file.getPath() + " found zero line." );					
						continue;
					}
					if (i<tran.getBeginLen()) { // not begin line.
						continue;
					}					
					Map<String, String> dataMap = new HashMap<String, String>();
					fillStrLine2Map(resultObj.getSysFtpTran(), segms, dataMap, strData);				
					fillDataList.add(dataMap);
				}				
			}			
			ftpData.setContent( getContent(strLines) );			
			ftpData.setDatas( fillDataList );
			ftpData.setFile( file );			
			datas.add(ftpData);
		}
		resultObj.setDatas(datas);
	}
	
	private static void fillStrLine2Map(SysFtpTranVO tran, List<TbSysFtpTranSegm> segms, Map<String, String> dataMap, 
			String strLine) throws Exception {
		if (TransformSegment.TEXT_MODE.equals(tran.getSegmMode())) { // 用字串切割
			for (TbSysFtpTranSegm segm : segms) {
				if ( strLine.length() < segm.getEnd() ) {
					throw new Exception("data format error.");
				}				
				dataMap.put(segm.getName(), strLine.substring(segm.getBegin(), segm.getEnd()) );
			}			
		} else if (TransformSegment.BYTE_MODE.equals(tran.getSegmMode())) { // 用 byte 切割
			byte[] dataBytes = strLine.getBytes( tran.getEncoding() );
			for (TbSysFtpTranSegm segm : segms) {
				if ( dataBytes.length < segm.getEnd() ) {
					throw new Exception("data format error.");
				}				
				String dataStr = new String(
						ArrayUtils.subarray(dataBytes, segm.getBegin(), segm.getEnd()), tran.getEncoding());
				dataMap.put(segm.getName(), dataStr);
			}
		} else { // 用符號來分成array
			String tmpStr[] = strLine.split(tran.getSegmSymbol());
			for (TbSysFtpTranSegm segm : segms) {
				if (segm.getBegin() != segm.getEnd()) {
					throw new Exception("segment config begin/end error.");
				}				
				if (segm.getBegin() != segm.getEnd()) {
					throw new Exception("segment config begin/end error.");
				}
				dataMap.put(segm.getName(), tmpStr[segm.getBegin()]);
			}			
		}
	}

	private static void processXml(SystemFtpClientResultObj resultObj) throws Exception {
		SysFtpTranVO tran = resultObj.getSysFtpTran();
		List<SystemFtpClientData> datas = new LinkedList<SystemFtpClientData>();
		JAXBContext jaxbContext = null;
		Unmarshaller jaxbUnmarshaller = null;		
		if (!StringUtils.isBlank(tran.getXmlClassName())) {
			Class<?> xmlBeanClazz = Class.forName( tran.getXmlClassName() );			
			jaxbContext = JAXBContext.newInstance(xmlBeanClazz);
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();			
		}
		for (File file : resultObj.getFiles()) {
			SystemFtpClientData ftpData = new SystemFtpClientData();
			logWarnFileSize(file);
			String content = FileUtils.readFileToString(file, Constants.BASE_ENCODING);	// xml 原則上都是用utf-8
			ftpData.setContent( content );			
			ftpData.setDatas( null );
			ftpData.setFile( file );
			if (jaxbUnmarshaller!=null) {				
				Object obj = jaxbUnmarshaller.unmarshal( 
						new ByteArrayInputStream(content.getBytes(Constants.BASE_ENCODING)) ); // xml 原則上都是用utf-8
				ftpData.setXmlBean(obj);
			}
			datas.add(ftpData);
		}		
		resultObj.setDatas(datas);
	}
	
	private static String getContent(List<String> strLines) {
		StringBuilder sb = new StringBuilder();
		for (String str : strLines) {
			sb.append( str );
		}
		return sb.toString();
	}
	
	private static void getFiles(SystemFtpClientResultObj resultObj) throws Exception {
		List<String> names = getFileNames(resultObj.getSysFtpTran().getExprType(), 
				resultObj.getSysFtpTran().getNameExpression());
		if ( names == null || names.size() < 1 ) {
			throw new Exception( "No file name, Please settings name expression ( TB_SYS_FTP_TRAN.NAME_EXPRESSION ) " );
		}
		resultObj.setNames(names);		
		if (SystemFtpClientModel.FTP.equals(resultObj.getSysFtp().getType())) { // SFTP
			getFilesByFtp(resultObj);
		} else { // FTP
			getFilesBySFtp(resultObj);
		}
	}
	
	private static void getFilesByFtp(SystemFtpClientResultObj resultObj) throws Exception {
		SysFtpVO ftp = resultObj.getSysFtp();
		SysFtpTranVO tran = resultObj.getSysFtpTran();		
		FtpClientUtils ftpClient = new FtpClientUtils();
		File storeDir = getStoreDir();
		try {
			ftpClient.login(ftp.getAddress(), ftp.getUser(), ftp.getPass());
			for (String name : resultObj.getNames()) {
				ftpClient.get(tran.getCwd(), storeDir, name);
			}			
			fillStoreDirFiles(storeDir, resultObj);		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			storeDir = null;
			ftpClient.close();
			ftpClient = null;			
		}
	}
	
	private static void getFilesBySFtp(SystemFtpClientResultObj resultObj) throws Exception {
		SysFtpVO ftp = resultObj.getSysFtp();
		File storeDir = getStoreDir();
		List<String> localFile = new ArrayList<String>();
		for (String name : resultObj.getNames()) {
			localFile.add( storeDir.getPath() + "/" + name );
		}
		try {
			SFtpClientUtils.get(ftp.getUser(), ftp.getPass(), ftp.getAddress(), 
					ftp.getPort(), resultObj.getNames(), localFile);
			fillStoreDirFiles(storeDir, resultObj);			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			storeDir = null;			
		}		
	}	
	
	private static void putFilesByFtp(SysFtpVO ftp, SysFtpTranVO tran, List<String> fileFullPathNames) throws Exception {
		List<File> files = new ArrayList<File>();
		for (String name : fileFullPathNames) {
			File file = new File( name );
			files.add(file);
		}
		FtpClientUtils ftpClient = new FtpClientUtils();
		try {
			ftpClient.login(ftp.getAddress(), ftp.getUser(), ftp.getPass());
			for (File file : files) {
				ftpClient.put(tran.getCwd(), file.getName(), file);				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			ftpClient.close();
			ftpClient = null;
		}
	}
	
	private static void putFileBySFtp(SysFtpVO ftp, SysFtpTranVO tran, List<String> fileFullPathNames) throws Exception {
		List<String> remoteFile = new ArrayList<String>();
		for (String name : fileFullPathNames) {
			File file = new File( name );
			remoteFile.add( file.getName() );
		}
		try {
			SFtpClientUtils.put(ftp.getUser(), ftp.getPass(), ftp.getAddress(), ftp.getPort(), 
					fileFullPathNames, remoteFile);			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	private static List<String> getFileNames(String expressionType, String nameExpression) throws Exception {
		List<String> names = new ArrayList<String>();	
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put( SystemFtpClientModel.RETURN_FILE_VARIABLE , names );
		dataMap = ScriptExpressionUtils.execute(expressionType, nameExpression, dataMap, dataMap);
		return names;
	}
	
	private static File getStoreDir() throws IOException {
		File storeDir = new File( Constants.getWorkTmpDir() + "/" + SystemFtpClientUtils.class.getSimpleName() + 
				"/" + System.currentTimeMillis() );
		FileUtils.forceMkdir( storeDir );		
		return storeDir;
	}
	
	private static void fillStoreDirFiles(File storeDir, SystemFtpClientResultObj resultObj) throws Exception {
		File[] localFiles = storeDir.listFiles();
		List<File> files = new ArrayList<File>();
		for (File file : localFiles) {
			files.add(file);
		}
		resultObj.setFiles(files);		
	}
	
	private static void logWarnFileSize(File file) throws Exception {
		long bytes = file.length();
		if ( bytes > 0 && (bytes/1024)>MAX_SIZE_KB ) {
			logger.warn("The file: " + file.getPath() + " is bigger then " + MAX_SIZE_KB + 
					" kb size. please change SYS_FTP.TYPE type to " + SystemFtpClientModel.TRAN_GET );
		}
	}

}
