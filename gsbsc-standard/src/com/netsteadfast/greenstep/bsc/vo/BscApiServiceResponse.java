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
package com.netsteadfast.greenstep.bsc.vo;

import java.util.LinkedList;
import java.util.List;

import com.netsteadfast.greenstep.base.model.YesNo;

public class BscApiServiceResponse implements java.io.Serializable {
	private static final long serialVersionUID = 3784147952012520482L;
	private String success = YesNo.NO;
	private String message = "";	
	private String outJsonData = "{}";
	private String outXmlData = "";
	private String htmlBodyUrl = ""; // 產生一組 url , 顯示HTML報表內容
	private List<String> perspectivesMeterChartUrl = new LinkedList<String>(); // 險示 Perspective Meter Chart 的 url, 會有多筆資料
	private List<String> objectivesMeterChartUrl = new LinkedList<String>(); // 險示 Objective Meter Chart 的 url, 會有多筆資料
	private List<String> kpisMeterChartUrl = new LinkedList<String>(); // 險示 KPI Meter Chart 的 url, 會有多筆資料
	private String pieChartUrl = ""; // 顯示 perspectives PIE chart
	private String barChartUrl = ""; // 顯示 perspectives BAR chart
	
	public String getSuccess() {
		return success;
	}
	
	public void setSuccess(String success) {
		this.success = success;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getOutJsonData() {
		return outJsonData;
	}
	
	public void setOutJsonData(String outJsonData) {
		this.outJsonData = outJsonData;
	}
	
	public String getOutXmlData() {
		return outXmlData;
	}

	public void setOutXmlData(String outXmlData) {
		this.outXmlData = outXmlData;
	}

	public String getHtmlBodyUrl() {
		return htmlBodyUrl;
	}
	
	public void setHtmlBodyUrl(String htmlBodyUrl) {
		this.htmlBodyUrl = htmlBodyUrl;
	}

	public List<String> getPerspectivesMeterChartUrl() {
		return perspectivesMeterChartUrl;
	}

	public void setPerspectivesMeterChartUrl(List<String> perspectivesMeterChartUrl) {
		this.perspectivesMeterChartUrl = perspectivesMeterChartUrl;
	}

	public List<String> getObjectivesMeterChartUrl() {
		return objectivesMeterChartUrl;
	}

	public void setObjectivesMeterChartUrl(List<String> objectivesMeterChartUrl) {
		this.objectivesMeterChartUrl = objectivesMeterChartUrl;
	}

	public List<String> getKpisMeterChartUrl() {
		return kpisMeterChartUrl;
	}

	public void setKpisMeterChartUrl(List<String> kpisMeterChartUrl) {
		this.kpisMeterChartUrl = kpisMeterChartUrl;
	}

	public String getPieChartUrl() {
		return pieChartUrl;
	}

	public void setPieChartUrl(String pieChartUrl) {
		this.pieChartUrl = pieChartUrl;
	}

	public String getBarChartUrl() {
		return barChartUrl;
	}

	public void setBarChartUrl(String barChartUrl) {
		this.barChartUrl = barChartUrl;
	}
	
}
