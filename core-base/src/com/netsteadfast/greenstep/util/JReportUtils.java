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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import net.lingala.zip4j.core.ZipFile;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.po.hbm.TbSysJreport;
import com.netsteadfast.greenstep.po.hbm.TbSysJreportParam;
import com.netsteadfast.greenstep.service.ISysJreportParamService;
import com.netsteadfast.greenstep.service.ISysJreportService;
import com.netsteadfast.greenstep.vo.SysJreportParamVO;
import com.netsteadfast.greenstep.vo.SysJreportVO;

@SuppressWarnings("unchecked")
public class JReportUtils {
	protected static Logger logger=Logger.getLogger(JReportUtils.class); 
	private static ISysJreportService<SysJreportVO, TbSysJreport, String> sysJreportService;
	private static ISysJreportParamService<SysJreportParamVO, TbSysJreportParam, String> sysJreportParamService;
	
	static {
		sysJreportService = (ISysJreportService<SysJreportVO, TbSysJreport, String>)AppContext.getBean("core.service.SysJreportService");
		sysJreportParamService = (ISysJreportParamService<SysJreportParamVO, TbSysJreportParam, String>)AppContext.getBean("core.service.SysJreportParamService");
	}
	
	public static void deployReport(SysJreportVO report) throws Exception {
		TbSysJreport destReportObj = new TbSysJreport();
		BeanUtils.copyProperties(destReportObj, report);
		deployReport( destReportObj );
	}
	
	public static void deployReport(TbSysJreport report) throws Exception {		
		String reportDeployDirName = Constants.getDeployJasperReportDir() + "/";
		File reportDeployDir = new File(reportDeployDirName);
		try {
			if (!reportDeployDir.exists()) {
				logger.warn("no exists dir, force mkdir " + reportDeployDirName);
				FileUtils.forceMkdir(reportDeployDir);
			}								
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage().toString());
		}
		logger.info("REPORT-ID : " + report.getReportId());
		File reportFile = null;
		File reportZipFile = null;			
		OutputStream os = null;
		try {
			String reportFileFullPath = reportDeployDirName + report.getReportId() + "/" + report.getFile();				
			String reportZipFileFullPath = reportDeployDirName + report.getReportId() + ".zip";		
			reportZipFile = new File(reportZipFileFullPath);
			if (reportZipFile.exists()) {
				logger.warn("delete " + reportZipFileFullPath);
				FileUtils.forceDelete(reportZipFile);					
			}
			os = new FileOutputStream(reportZipFile);
			IOUtils.write(report.getContent(), os);
			os.flush();
			ZipFile zipFile = new ZipFile( reportZipFileFullPath );
			zipFile.extractAll( reportDeployDirName );
			reportFile = new File( reportFileFullPath );
			if (!reportFile.exists()) {
				logger.warn("report file is missing : " + reportFileFullPath);
				return;
			}
			if (YesNo.YES.equals(report.getIsCompile()) && report.getFile().endsWith("jrxml")) {
				logger.info("compile report...");
				File d = new File( reportDeployDirName + report.getReportId() );
				String outJasper = compileReportToJasperFile(d.listFiles(), reportDeployDirName + report.getReportId() + "/");
				logger.info("out first : " + outJasper);
			}
		} catch (JRException re) {
			re.printStackTrace();
			logger.error(re.getMessage().toString());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage().toString());
		} finally {
			if (os!=null) {
				os.close();
			}
			os = null;
			reportFile = null;
			reportZipFile = null;
		}
		reportDeployDir = null;		
	}
	
	public static void deploy() throws ServiceException, Exception {
		logger.info("begin deploy...");
		List<TbSysJreport> reports = sysJreportService.findListByParams(null);
		String reportDeployDirName = Constants.getDeployJasperReportDir() + "/";
		File reportDeployDir = new File(reportDeployDirName);
		try {
			if (reportDeployDir.exists()) {
				logger.warn("delete " + reportDeployDirName);
				FileUtils.forceDelete(reportDeployDir);
			}	
			logger.warn("mkdir " + reportDeployDirName);
			FileUtils.forceMkdir(reportDeployDir);					
			for (TbSysJreport report : reports) {
				deployReport(report);
			}							
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage().toString());
		}
		reportDeployDir = null;			
		logger.info("end deploy...");
	}
	
	/**
	 * jasperreport compile jrxml 成 jasper
	 * 
	 * @param sourceFile		如: File[]
	 * @param destDir			如: C:/report/     產生一個 test.jasper 到 C:/report/ 中
	 * @return
	 * @throws JRException
	 */
	public static String compileReportToJasperFile(File sourceFile[], String destDir) throws JRException {
		String jasperFirst = "";
		for (int ix=0; sourceFile!=null && ix<sourceFile.length; ix++) {
			File srcFile = sourceFile[ix];
			if (!srcFile.exists() || srcFile.getName().indexOf(".jrxml")==-1) {
				srcFile=null;
				continue;
			}
			//String destFileName=srcFile.getName().replaceAll(".jrxml", ".jasper");
			String destFileName=srcFile.getPath().replaceAll(".jrxml", ".jasper");
			if ("".equals(jasperFirst)) {
				jasperFirst = destFileName;
			}
			JasperCompileManager.compileReportToFile(srcFile.getPath(), destFileName);
			logger.info("out process : " + destFileName);
		}
		return jasperFirst;
	}	
	
	/**
	 * jasperreport compile jrxml 成 jasper
	 * 
	 * @param sourceFileName	如: new String[]{ "C:/report-source/test.jrxml" }
	 * @param destDir			如: C:/report/     產生一個 test.jasper 到 C:/report/ 中
	 * @return
	 * @throws JRException
	 */
	public static String compileReportToJasperFile(String sourceFileName[], String destDir) throws JRException {
		String jasperFirst = "";
		for (int ix=0; sourceFileName!=null && ix<sourceFileName.length; ix++) {
			File srcFile = new File(sourceFileName[ix]);
			if (!srcFile.exists() || srcFile.getName().indexOf(".jrxml")==-1) {
				srcFile=null;
				continue;
			}
			//String destFileName=srcFile.getName().replaceAll(".jrxml", ".jasper");
			String destFileName=srcFile.getPath().replaceAll(".jrxml", ".jasper");
			if ("".equals(jasperFirst)) {
				jasperFirst = destFileName;
			}
			JasperCompileManager.compileReportToFile(srcFile.getPath(), destFileName);
			logger.info("out process : " + destFileName);
		}
		return jasperFirst;
	}
	
	public static String selfTestDecompress4Upload(String uploadOid) throws ServiceException, IOException, Exception {
		String extractDir = Constants.getWorkTmpDir() + "/" + JReportUtils.class.getSimpleName() + "/" + SimpleUtils.getUUIDStr() + "/";
		File realFile = UploadSupportUtils.getRealFile(uploadOid);
		try {
			ZipFile zipFile = new ZipFile(realFile);
			zipFile.extractAll( extractDir );
		} catch (Exception e) {
			throw e;
		} finally {
			realFile = null;
		}		
		return extractDir;
	}
	
	public static Map<String, Object> getParameter(String reportId, HttpServletRequest request) throws ServiceException, Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (StringUtils.isBlank(reportId)) {
			return paramMap;
		}
		paramMap.put("reportId", reportId);
		List<TbSysJreportParam> paramList = sysJreportParamService.findListByParams(paramMap);
		paramMap.clear();
		for (int i=0; paramList!=null && i<paramList.size(); i++) {
			TbSysJreportParam sysJreportParam = paramList.get(i);
			Enumeration<String> urlParams = request.getParameterNames();
			while (urlParams.hasMoreElements()) {
				String p = urlParams.nextElement();
				if (p.equals(sysJreportParam.getUrlParam())) {
					String value = request.getParameter(p);
					paramMap.put(sysJreportParam.getRptParam(), value);
				}
			}
		}
		String reportFolder = Constants.getDeployJasperReportDir() + File.separator + reportId + File.separator;
		paramMap.put("REPORT_FOLDER", reportFolder);
		paramMap.put("SUBREPORT_DIR", reportFolder);
		return paramMap;
	}
	
	public static void fillReportToResponse(String reportId, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		Map<String, Object> paramMap = getParameter(reportId, request);
		fillReportToResponse(reportId, paramMap, response);
	}
	
	public static void fillReportToResponse(String reportId, Map<String, Object> paramMap, HttpServletResponse response) throws ServiceException, Exception {
		if (StringUtils.isBlank(reportId)) {
			throw new java.lang.IllegalArgumentException("error, reportId is blank");
		}
		TbSysJreport sysJreport = new TbSysJreport();
		sysJreport.setReportId(reportId);
		DefaultResult<TbSysJreport> result = sysJreportService.findEntityByUK(sysJreport);
		if ( result.getValue() == null ) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		sysJreport = result.getValue();
		String jasperFileFullPath = Constants.getDeployJasperReportDir() + "/" + sysJreport.getReportId() + "/" + sysJreport.getReportId() + ".jasper";
		File jasperFile = new File(jasperFileFullPath);
		if (!jasperFile.exists()) {
			jasperFile = null;
			throw new Exception("error, Files are missing : " + jasperFileFullPath);
		}
		InputStream reportSource = new FileInputStream( jasperFile );
		Connection conn = null;
		try {
			conn = DataUtils.getConnection();
			ServletOutputStream ouputStream = response.getOutputStream();
		    JasperPrint jasperPrint = JasperFillManager.fillReport(
		            reportSource,
		            paramMap,
		            conn);
		    response.setContentType("application/pdf");
		    response.setHeader("Content-disposition", "inline; filename=" + sysJreport.getReportId() + ".pdf");
		    JRPdfExporter jrPdfExporter=new JRPdfExporter();
		    jrPdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		    jrPdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(ouputStream));		    
		    SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		    jrPdfExporter.setConfiguration(configuration);
		    configuration.setOwnerPassword(Constants.getEncryptorKey1());
		    jrPdfExporter.exportReport();
		    ouputStream.flush();
		    ouputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataUtils.doReleaseConnection(conn);
			if (null != reportSource) {
				try {
					reportSource.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			reportSource = null;
			jasperFile = null;
		}
	}
	
}
