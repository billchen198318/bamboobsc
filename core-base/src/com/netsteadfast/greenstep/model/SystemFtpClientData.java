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

import java.io.File;
import java.util.List;
import java.util.Map;

public class SystemFtpClientData implements java.io.Serializable {	
	private static final long serialVersionUID = -1584520229910952899L;
	private List<Map<String, String>> datas; // 切割文字檔後的資料 (只有TXT會填入此變數)
	private String content; // 文字檔 or XML 內容
	private File file; // 檔案
	private Object xmlBean; 
	
	public List<Map<String, String>> getDatas() {
		return datas;
	}
	public void setDatas(List<Map<String, String>> datas) {
		this.datas = datas;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public Object getXmlBean() {
		return xmlBean;
	}
	public void setXmlBean(Object xmlBean) {
		this.xmlBean = xmlBean;
	}
	
}
