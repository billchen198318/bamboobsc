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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.model.SystemFtpData;
import com.netsteadfast.greenstep.model.SystemFtpModel;
import com.netsteadfast.greenstep.model.SystemFtpResultObj;
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
public class SystemFtpUtils {
	protected static Logger logger=Logger.getLogger(SystemFtpUtils.class);
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
	
	public static SystemFtpResultObj getFileOnly(String tranId) throws ServiceException, Exception {
		if ( StringUtils.isBlank(tranId) ) {
			throw new Exception( SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK) );
		}
		SystemFtpResultObj resultObj = new SystemFtpResultObj();
		SysFtpTranVO tran = findSysFtpTran(tranId);
		SysFtpVO ftp = findSysFtp( tran.getFtpId() );
		List<TbSysFtpTranSegm> segms = findSysFtpTranSegm(tran.getFtpId(), tran.getTranId());
		resultObj.setSysFtp(ftp);
		resultObj.setSysFtpTran(tran);
		resultObj.setSysFtpTranSegms(segms);
		getFiles(resultObj);		
		return resultObj;
	}
	
	public static SystemFtpResultObj getDatas(String tranId) throws ServiceException, Exception {
		SystemFtpResultObj resultObj = getFileOnly(tranId);
		SysFtpTranVO tran = resultObj.getSysFtpTran();
		if (SystemFtpModel.TRAN_GET_TEXT.equals(tran.getTranType())) { // 分割 txt 檔案
			processText(resultObj);
		} else if (SystemFtpModel.TRAN_GET_XML.equals(tran.getTranType())) { // 處理 xml 檔案			
			processXml(resultObj);			
		}
		return resultObj;
	}
	
	public static boolean putFiles(String tranId) throws ServiceException, Exception {
		if ( StringUtils.isBlank(tranId) ) {
			throw new Exception( SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK) );
		}
		SysFtpTranVO tran = findSysFtpTran(tranId);
		SysFtpVO ftp = findSysFtp( tran.getFtpId() );
		if (!SystemFtpModel.TRAN_PUT.equals(tran.getTranType())) {
			return false;
		}
		/**
		 * 這編 NAME_EXPRESSION 回傳的 names 檔案文字路境必須 如 : /var/upload/20150514.txt
		 * **不可** 是 20150514.txt
		 */
		List<String> fileFullPathNames = getFileNames(tran.getExprType(), tran.getNameExpression()); 
		if (SystemFtpModel.FTP.equals(ftp.getType())) { // FTP
			putFilesByFtp(ftp, tran, fileFullPathNames);
		} else { // SFTP
			putFileBySFtp(ftp, tran, fileFullPathNames);			
		}
		return true;
	}
	
	private static void processText(SystemFtpResultObj resultObj) throws Exception {
		List<SystemFtpData> datas = new LinkedList<SystemFtpData>();
		List<TbSysFtpTranSegm> segms = resultObj.getSysFtpTranSegms();		
		for (File file : resultObj.getFiles()) {
			SystemFtpData ftpData = new SystemFtpData();
			List<Map<String, String>> fillDataList = new LinkedList<Map<String, String>>();
			List<String> strLines = FileUtils.readLines(file);
			for (String strData : strLines) {
				Map<String, String> dataMap = new HashMap<String, String>();
				if ( strData.length() < 1 ) {
					continue;
				}
				for (TbSysFtpTranSegm segm : segms) {
					dataMap.put(segm.getName(), strData.substring(segm.getBegin(), segm.getEnd()) );
				}	
				fillDataList.add(dataMap);
			}
			ftpData.setContent( getContent(strLines) );			
			ftpData.setDatas( fillDataList );
			ftpData.setFile( file );			
			datas.add(ftpData);
		}
	}

	private static void processXml(SystemFtpResultObj resultObj) throws Exception {
		List<SystemFtpData> datas = new LinkedList<SystemFtpData>();			
		for (File file : resultObj.getFiles()) {
			SystemFtpData ftpData = new SystemFtpData();
			String content = FileUtils.readFileToString(file);			
			ftpData.setContent( content );			
			ftpData.setDatas( null );
			ftpData.setFile( file );
			datas.add(ftpData);
		}		
	}
	
	private static String getContent(List<String> strLines) {
		StringBuilder sb = new StringBuilder();
		for (String str : strLines) {
			sb.append( str );
		}
		return sb.toString();
	}
	
	private static void getFiles(SystemFtpResultObj resultObj) throws Exception {
		List<String> names = getFileNames(resultObj.getSysFtpTran().getExprType(), 
				resultObj.getSysFtpTran().getNameExpression());
		if ( names == null || names.size() < 1 ) {
			throw new Exception( "No file name, Please settings name expression ( TB_SYS_FTP_TRAN.NAME_EXPRESSION ) " );
		}
		resultObj.setNames(names);		
		if (SystemFtpModel.FTP.equals(resultObj.getSysFtp().getType())) { // SFTP
			getFilesByFtp(resultObj);
		} else { // FTP
			getFilesBySFtp(resultObj);
		}
	}
	
	private static void getFilesByFtp(SystemFtpResultObj resultObj) throws Exception {
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
	
	private static void getFilesBySFtp(SystemFtpResultObj resultObj) throws Exception {
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
		dataMap.put( SystemFtpModel.RETURN_FILE_VARIABLE , names );
		dataMap = ScriptExpressionUtils.execute(expressionType, nameExpression, dataMap, dataMap);
		return names;
	}
	
	private static File getStoreDir() {
		File storeDir = new File( Constants.getWorkTmpDir() + "/" + SystemFtpUtils.class.getSimpleName() + 
				"/" + System.currentTimeMillis() );
		return storeDir;
	}
	
	private static void fillStoreDirFiles(File storeDir, SystemFtpResultObj resultObj) throws Exception {
		File[] localFiles = storeDir.listFiles();
		List<File> files = new ArrayList<File>();
		for (File file : localFiles) {
			files.add(file);
		}
		resultObj.setFiles(files);		
	}

}
