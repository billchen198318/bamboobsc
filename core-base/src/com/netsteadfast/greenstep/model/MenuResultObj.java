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

public class MenuResultObj implements java.io.Serializable {
	private static final long serialVersionUID = 7145199918153525624L;
	
	/**
	 * 選單的 html
	 */
	private String htmlData = "";
	
	/**
	 * 選單的 javascript
	 */
	private String javascriptData = "";
	
	/**
	 * Dialog 的 html 資料
	 */
	private String dialogHtmlData = "";
	
	public String getHtmlData() {
		return htmlData;
	}
	
	public void setHtmlData(String htmlData) {
		this.htmlData = htmlData;
	}
	
	public String getJavascriptData() {
		return javascriptData;
	}
	
	public void setJavascriptData(String javascriptData) {
		this.javascriptData = javascriptData;
	}

	public String getDialogHtmlData() {
		return dialogHtmlData;
	}

	public void setDialogHtmlData(String dialogHtmlData) {
		this.dialogHtmlData = dialogHtmlData;
	}

}
