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


// for BSC_PROG002D0007Q Strategy-MAP
function BSC_PROG002D0007Q_showObjectiveItem(idStr) {
	BSC_PROG002D0007Q_S00_DlgShow( idStr );
}

// for BSC common load OrgChart - Hierarchy structure view
function BSC_PROGCOMM0001Q_showOrgChart(type, oid) {
	var urlParam = type + _gscore_delimiter + oid;
	// DlgShow 畫面太小了, 改用 TabShow
	// BSC_PROGCOMM0001Q_DlgShow( urlParam );
	BSC_PROGCOMM0001Q_TabShow( urlParam );
}

//主要給 BSC/KPI/Employee/Department report 與 dashboard 頁面使用
//報表查詢日期欄位會用到, 頻率(6-年,5-半年,4-季,3-月,2-周,1-日) , 目前只提供, 當前年, 當前半年, 當前季, 當前月
function setReportQueryDateFieldForCurrent(frequency, dateField1, dateField2) {
	if ('6' == frequency || '5' == frequency || '4' == frequency) { // 6-年,5-半年,4-季
		dijit.byId( dateField1 ).set("displayedValue", viewPage.getCurrentYear()+"");
		dijit.byId( dateField2 ).set("displayedValue", viewPage.getCurrentYear()+"");
		return;
	}
	var dateStr1 = '';
	var dateStr2 = '';
	if ('3' == frequency) { // 3-月
		var y = viewPage.getCurrentYear();
		var m = viewPage.getCurrentMonth();
		var d = viewPage.getLastDayOfMonth(y, m);
		dateStr1 = y + '/' + ( m < 10 ? '0' + m : m ) + '/' + '01';
		dateStr2 = y + '/' + ( m < 10 ? '0' + m : m ) + '/' + ( d < 10 ? '0' + d : d);
	}
	dijit.byId( dateField1 ).set("displayedValue", dateStr1);
	dijit.byId( dateField2 ).set("displayedValue", dateStr2);
}
