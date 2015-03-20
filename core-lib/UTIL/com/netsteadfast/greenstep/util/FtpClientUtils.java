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
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

public class FtpClientUtils {
	public static final int TIME_OUT = 30000; 
    protected Logger logger = Logger.getLogger(FtpClientUtils.class);
    protected FTPClient ftpClient;
    private String ftpserver="";
    private String ftpuser="";
    private String ftppass="";
    private File storeDir;
    
    public String getFtpserver() {
        return ftpserver;
    }
    public void setFtpserver(String ftpserver) {
        this.ftpserver = ftpserver;
    }
    public String getFtpuser() {
        return ftpuser;
    }
    public void setFtpuser(String ftpuser) {
        this.ftpuser = ftpuser;
    }
    public String getFtppass() {
        return ftppass;
    }
    public void setFtppass(String ftppass) {
        this.ftppass = ftppass;
    }
    public File getStoreDir() {
        return storeDir;
    }
    public void setStoreDir(File storeDir) {
        this.storeDir = storeDir;
    }    
   
    public FtpClientUtils() {
        ftpClient = new FTPClient();
    }
   
    public void get() throws SocketException, IOException, Exception {
        this.get(ftpserver, ftpuser, ftppass, "", storeDir, false);
    }
    
    public void get(
            String ftpserver, String ftpuser, String ftppass,
            String cwdDirectory, File storeDir, boolean deleteFtpFile) throws SocketException, IOException, Exception {
       
        if (!this.ftpClient.isConnected()) {
            this.login(ftpserver, ftpuser, ftppass);
        }           
        this.get(cwdDirectory, storeDir, null, deleteFtpFile);
    }   
    
    public void get(
            String cwdDirectory, File storeDir, String head, boolean deleteFtpFile) throws SocketException, IOException, Exception {
       
        if (!this.ftpClient.isConnected() ) {
            this.logger.error("FTP not connection...");
            throw new Exception("FTP not connection...");
        }       
        if (!this.ftpClient.sendNoOp()) {
        	this.login(this.ftpserver, this.ftpuser, this.ftppass);
        }        
        this.ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
        if (cwdDirectory!=null && !"".equals(cwdDirectory)) {
            this.ftpClient.cwd(cwdDirectory);
        }
        FTPFile ftpFiles[] = this.ftpClient.listFiles();       
        for (int ix=0; ftpFiles!=null && ix<ftpFiles.length; ix++) {
            if (head!=null && !"".equals(head)) {
                if (ftpFiles[ix].getName().indexOf(head)!=0) {
                    logger.info("not get : " + ftpFiles[ix].getName());
                    continue;
                }               
            }
            System.out.println(ftpFiles[ix]);
            if (ftpFiles[ix].isFile()) {
                File downloadFile = new File(storeDir.getPath() + "/" + ftpFiles[ix].getName() );
                FileOutputStream fos = new FileOutputStream(downloadFile);
                if (this.ftpClient.retrieveFile(ftpFiles[ix].getName(), fos) ) {
                    logger.info(
                            "ftp GET : " + storeDir.getPath() + "/" + ftpFiles[ix].getName());
                    if (deleteFtpFile) {
                        this.delete(ftpFiles[ix].getName());
                    }                   
                }
                downloadFile = null;
                fos.close();
                fos = null;
            }
        }       
    }
    
    public boolean delete(String remoteFileName) throws SocketException, IOException, Exception {
        if (!this.ftpClient.isConnected() ) {
            this.logger.error("FTP not connection...");
            throw new Exception("FTP not connection...");
        }   
        if (!this.ftpClient.sendNoOp()) {
        	this.login(this.ftpserver, this.ftpuser, this.ftppass);
        }
        boolean delStatus = this.ftpClient.deleteFile(remoteFileName);
        logger.warn(
                "ftp DELETE : " + remoteFileName + " del-status : " + delStatus);
        return delStatus;
    }
    
    public void put(
            String cwdDirectory, String remoteFileName, File localFile) throws SocketException, IOException, Exception {
       
        if (!this.ftpClient.isConnected() ) {
            this.logger.error("FTP not connection...");
            throw new Exception("FTP not connection...");
        }       
        if (!this.ftpClient.sendNoOp()) {
        	this.login(this.ftpserver, this.ftpuser, this.ftppass);
        }        
        FileInputStream fis = new FileInputStream(localFile);
        this.ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
        if (cwdDirectory!=null && !"".equals(cwdDirectory)) {
            this.ftpClient.cwd(cwdDirectory);
        }
        this.ftpClient.storeFile(remoteFileName, fis);
        this.logger.warn("ftp PUT : " + cwdDirectory + "/" + remoteFileName);
        fis.close();
        fis = null;
    }
    
    public void login(String ftpserver, String ftpuser, String ftppass) throws SocketException, IOException {
        this.ftpserver = ftpserver;
        this.ftpuser = ftpuser;
        this.ftppass = ftppass;
        this.ftpClient.setConnectTimeout(TIME_OUT);
        this.ftpClient.connect(this.ftpserver);
        this.ftpClient.login(this.ftpuser, this.ftppass);           
    }
    
    public void close() {
        try {
            this.ftpClient.logout();
            this.ftpClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
