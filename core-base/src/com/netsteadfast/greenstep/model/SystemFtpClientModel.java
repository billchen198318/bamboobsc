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
package com.netsteadfast.greenstep.model;

public class SystemFtpClientModel {
	
	public static final String FTP = "FTP"; // ftp
	public static final String SFTP = "SFTP"; // sftp
	
	public static final String TRAN_GET_TEXT = "GET-TXT"; // 取檔for txt
	public static final String TRAN_GET_XML = "GET-XML"; // 取檔for xml
	public static final String TRAN_GET = "GET"; // 取檔
	public static final String TRAN_PUT = "PUT"; // 放檔	
	
	public static final String RETURN_FILE_VARIABLE = "fileName"; // 取出的檔名 tb_sys_ftp_tran.name_expression 固定的變數名稱
	public static final String RESULT_OBJ_VARIABLE = "resultObj"; // 幫助 tb_sys_ftp_tran.help_expression 放入expression的變數名稱

}
