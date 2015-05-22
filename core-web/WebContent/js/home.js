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
dojo.require("dojo.html");
dojo.require("dojox.html._base");
dojo.require("dijit.dijit");
dojo.require("dijit.layout.BorderContainer");
dojo.require("dijit.layout.ContentPane");
dojo.require("dojox.layout.ContentPane");
dojo.require("dijit.MenuBar");
dojo.require("dijit.PopupMenuBarItem");
dojo.require("dijit.Menu");
dojo.require("dijit.MenuItem");
dojo.require("dijit.Tree");
dojo.require("dijit.Dialog");
dojo.require("dijit.form.Form");
dojo.require("dijit.form.ValidationTextBox");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.FilteringSelect");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.Select");
dojo.require("dijit.form.SimpleTextarea");
dojo.require("dijit.form.Textarea");
dojo.require("dijit.Editor");
dojo.require("dojox.grid.DataGrid");
dojo.require("dijit.tree.dndSource");
dojo.require("dijit.tree.TreeStoreModel");
dojo.require("dojo.data.ItemFileWriteStore");	
dojo.require("dijit.tree.ForestStoreModel");	
dojo.require("dojo.dnd.common");
dojo.require("dojo.dnd.Source");
dojo.require("dojo.dnd.Target");
dojo.require("dojox.widget.DialogSimple");
dojo.require("dojox.widget.Dialog");
dojo.require("dijit.TitlePane");
dojo.require("dojox.html.entities");
dojo.require("dijit.InlineEditBox");
dojo.require("dijit.ProgressBar"); 
dojo.require("dijit.form.NumberSpinner");
dojo.require("dojox.encoding.base64");
dojo.require("dojo.parser");
dojo.require("dojo.io.iframe");
dojo.require("dojox.form.Uploader");

dojo.require("dojox.form.YearTextBox");
dojo.require("dijit.form.DateTextBox");

dojo.require("dijit.form.MultiSelect");

dojo.require("dojox.calendar.Calendar");

//dojo.require("dojox.widget.Standby");


dojo.require("dijit.form.DropDownButton");
dojo.require("dijit.TooltipDialog");

dojo.require("dojox.image");
//dojo.require("dojox.image.Lightbox");


/*
dojo.require("dojox.charting.Chart2D");

//Retrieve the Legend, Tooltip, and Magnify classes
dojo.require("dojox.charting.widget.Legend");
dojo.require("dojox.charting.action2d.Tooltip");
dojo.require("dojox.charting.action2d.Magnify");

//Require the theme of our choosing
//"Claro", new in Dojo 1.6, will be used
dojo.require("dojox.charting.themes.Claro");
dojo.require("dojox.charting.themes.PlotKit.blue");
*/

dojo.require("dojox.widget.ColorPicker");
dojo.require("dojox.widget.Toaster");

dojo.require("dojox.socket");
dojo.require("dojox.io.xhrPlugins");

dojo.require("d3");
dojo.require("nv");

//-----------------------------------------------------------------------------------------------------

dojo.addOnLoad(function() {	
	
	//checkLoginState();	

});

require(["dojo/ready"], function(ready){
	ready(function(){
		if ( (typeof _coreIconsList!='undefined') && _coreIconsList!=null) {
			dojox.image.preload(_coreIconsList);
		}				
		dojo.addOnUnload(function() {
			
		});	
		
	});
});


//-----------------------------------------------------------------------------------------------------
// Dialog for alert
//-----------------------------------------------------------------------------------------------------
function alertDialog(txtTitle, txtContent, callbackFn, successFlag) {
	//viewPage.alertDialog(txtTitle, txtContent, callbackFn);
	var msgType='message';
	
	/*
	if ('Y' != successFlag) {
		msgType='warning';
		if (txtContent.indexOf('Exception')>-1 || txtContent.indexOf('Error')>-1
				|| txtContent.indexOf('exception')>-1 || txtContent.indexOf('error')>-1 ) {
			msgType='error';
		}
	}
	*/
	
	if ('N' == successFlag) {
		msgType='warning';
	}
	if ('E' == successFlag) {
		msgType='error';
	}
	
	viewPage.toasterShow('mainToaster', txtTitle, txtContent, callbackFn, msgType);
}


//-----------------------------------------------------------------------------------------------------
// Dialog for confirm
//-----------------------------------------------------------------------------------------------------
function confirmDialog(dialogId, title, question, callbackFn, e) {	
	viewPage.confirmDialog(dialogId, title, question, callbackFn, e);
}


//-----------------------------------------------------------------------------------------------------
// please wait dlg
//-----------------------------------------------------------------------------------------------------
function loadingDlgShow() {
	dijit.byId("pleaseWaitDlg").show();
}

function loadingDlgHide() {
	dijit.byId("pleaseWaitDlg").hide();
}

function showPleaseWait() {
	dojo.style(dijit.byId("pleaseWaitDlg").closeButtonNode,"display","none");
	dijit.byId("pleaseWaitDlg").show();
}
function hidePleaseWait() {
	dijit.byId("pleaseWaitDlg").hide();
}

function getGuid() {
	return viewPage.generateGuid();
}

//-----------------------------------------------------------------------------------------------------
// XHR Post
//-----------------------------------------------------------------------------------------------------

function xhrSendForm(_urlAction, _formId, _handleAs, _timeout, _sync, _preventCache, _loadFunction, _errFunction) {
	try {
		viewPage.xhrSendForm(
				_urlAction, 
				_formId, 
				_handleAs, 
				_timeout, 
				_sync, 
				_preventCache, 
				_loadFunction, 
				_errFunction,
				true);	
	} catch (e) {		
		alert(e);
	} finally {		
	}
}

function xhrSendParameter(_urlAction, _parameter, _handleAs, _timeout, _sync, _preventCache, _loadFunction, _errFunction) {
	try {
		viewPage.xhrSendParameter(
				_urlAction, 
				_parameter, 
				_handleAs, 
				_timeout, 
				_sync, 
				_preventCache, 
				_loadFunction, 
				_errFunction,
				true);	
	} catch (e) {
		alert(e);
	} finally {				
	}
}

function xhrSendFormNoWatitDlg(_urlAction, _formId, _handleAs, _timeout, _sync, _preventCache, _loadFunction, _errFunction) {
	try {
		viewPage.xhrSendForm(
				_urlAction, 
				_formId, 
				_handleAs, 
				_timeout, 
				_sync, 
				_preventCache, 
				_loadFunction, 
				_errFunction,
				false);	
	} catch (e) {		
		alert(e);
	} finally {		
	}
}

function xhrSendParameterNoWatitDlg(_urlAction, _parameter, _handleAs, _timeout, _sync, _preventCache, _loadFunction, _errFunction) {
	try {
		viewPage.xhrSendParameter(
				_urlAction, 
				_parameter, 
				_handleAs, 
				_timeout, 
				_sync, 
				_preventCache, 
				_loadFunction, 
				_errFunction,
				false);	
	} catch (e) {
		alert(e);
	} finally {				
	}
}

//-----------------------------------------------------------------------------------------------------
// common
//-----------------------------------------------------------------------------------------------------
function logoutEvent(success) {	
	if (success) {
		window.location="./logout.action";
	}
}

function hideCommonUploadDialog() {
	dijit.byId(_gscore_common_upload_dialog_id).hide();	
}

/**
 * 打開上傳檔案Dialog
 * 
 * @param system
 * @param type
 * @param isFile
 * @param uploadOidField
 * @param callJsFunction
 * @param callJsErrFunction
 */
function openCommonUploadDialog(system, type, isFile, uploadOidField, callJsFunction, callJsErrFunction) {
	var uploadDlgUrl = './' + _gscore_common_upload_dialog_action 
		+ '?system=' + system 
		+ '&type=' + type 
		+ '&isFile=' + isFile
		+ '&uploadOidField=' + uploadOidField 
		+ '&callJsFunction=' + callJsFunction  
		+ '&callJsErrFunction=' + callJsErrFunction;
	dijit.byId(_gscore_common_upload_dialog_id).set('href', uploadDlgUrl);
	dijit.byId(_gscore_common_upload_dialog_id).show();		
}

/**
* 上傳檔案用
* 
* @param formId  上傳檔案的form id
* @param uploadId  上傳完成後, 要放 TB_UPLOAD.OID 的 html hidden 欄位 id
* @param callbackFn  上傳完成後, 要跑的function
*/
function doUpload(formId, uploadId, callbackFn, errFn) {	
	dojo.byId(uploadId).value = '';
	dojo.io.iframe.send({
		url: './core.commonUploadFileAction.action',
		form: formId,
		method: "post",
		handleAs: "json",
		timeout: _gscore_dojo_ajax_timeout,
		sync: _gscore_dojo_ajax_sync,		
		preventCache: true,
		load: function(response, ioArgs) {
			alertDialog("Upload", response.message, function(){}, response.success);
			if (response.uploadOid!=null && typeof(response.uploadOid) != 'undefined' && '' != response.uploadOid ) {
				dojo.byId(uploadId).value = response.uploadOid;
				if (null!=callbackFn && eval("typeof " + callbackFn + "=='function'") ) {	
					callbackFn();
				}
			} else {
				if (null!=errFn && eval("typeof " + errFn + "=='function'") ) {
					errFn();
				}
			}
		},
		error: function(response, ioArgs) {
			alert(response);
			if (null!=errFn && eval("typeof " + errFn + "=='function'") ) {
				errFn();
			}
		}
	});	
}

/**
 * 打開簽名Dialog
 * 
 * @param system
 * @param uploadOidField
 * @param callJsFunction
 * @param callJsErrFunction
 */
function openCommonSignatureDialog(system, uploadOidField, callJsFunction, callJsErrFunction) {
	var dlgUrl = './' + _gscore_common_signature_dialog_action 
		+ '?system=' + system 
		+ '&uploadOidField=' + uploadOidField 
		+ '&callJsFunction=' + callJsFunction  
		+ '&callJsErrFunction=' + callJsErrFunction;
	dijit.byId(_gscore_common_signature_dialog_id).set('href', dlgUrl);
	dijit.byId(_gscore_common_signature_dialog_id).show();		
}

/**
 * 打開JASPER Report 的 OpwnWindow
 * 
 * @param title			openWindow的title
 * @param jreportId		報表id
 * @param format		報表format 目前請帶入 'PDF'
 * @param paramData		報表url要的參數, 如 { oid : '0123456789', type : 'A' }
 */
function openCommonJasperReportLoadWindow( title, jreportId, format, paramData ) {
	var url = "./" + _gscore_common_jasperreport_load_action + "?jreportId=" + jreportId + "&format=" + format;
	for (var key in paramData) {
		url += "&" + key + "=" + paramData[key];
	}
	window.open(url, title, "resizable=yes,scrollbars=yes,status=yes");		
}

/**
 * 打開 code 編輯器 OpenWindow 模式
 * 
 * @param uploadOid
 * @param valueFieldId
 * @param okFn
 * @param lang ( java or jsp )
 */
function openCommonCodeEditorWindow( uploadOid, valueFieldId, okFn, lang ) {
	var url = "./core.commonCodeEditorAction.action?cbMode=Y&valueFieldId=" + valueFieldId + "&okFn=" + okFn + '&lang=' + lang;
	if ( null != uploadOid && '' != uploadOid ) {
		url += '&oid=' + uploadOid;
	}
	window.open(url, 'Common code editor', "resizable=yes,scrollbars=yes,status=yes");
}

/**
 * 下載檔案 or 檢視圖片
 * 
 * @param type
 * @param uploadOid
 * @param paramData
 */
function openCommonLoadUpload( type, uploadOid, paramData ) {
	var downloadIFrameId = '_gs_downloadIFrame';
	if ( 'view' == type ) {		
		window.open(
				"./core.commonLoadUploadFileAction.action?type=view&oid=" + uploadOid,
				paramData["title"],
	            "resizable=yes,scrollbars=yes,status=yes,width=" + paramData["width"] + ",height=" + paramData["height"] ); 
	} else { // download	
		// 用  window.location在FireFox或Chrome會產生殘影
		// window.location = "./core.commonLoadUploadFileAction.action?type=download&oid=" + uploadOid;		
		var downloadIframe = dojo.byId(downloadIFrameId);
		var url = "./core.commonLoadUploadFileAction.action?type=download&oid=" + uploadOid;
		if ( downloadIframe == null ) {
			downloadIframe = dojo.io.iframe.create(downloadIFrameId);  
		}
		if ( paramData["url"] != null ) { // 自定下載的url
			url = paramData["url"];
		}
		dojo.io.iframe.setSrc(downloadIframe, url, true);		
	}
}

/**
 * 主要是把輸入條件不合的欄位背景變顏色
 * 
 * @param fieldsId
 * @param viewFieldsId
 */
function setFieldsBackgroundAlert(fieldsId, viewFieldsId) {
	if (null == fieldsId || null == viewFieldsId) {
		return;
	}
	for (var i=0; i<fieldsId.length; i++) {
		var idName = '';
		for (var d in viewFieldsId) {			
			if (fieldsId[i] == d) {
				idName = viewFieldsId[d];
			}
		}
		if (idName=='') {
			continue;
		}
		if ( dijit.byId(idName) == null ) {
			continue;
		}
		var domNode = dijit.byId(idName).domNode;
		dojo.style(domNode,"background","#F8E0E0"); //set bg to like red		
	}
}

/**
 * 把輸入條件不合的欄位背景言色回復成正常
 * 
 * @param viewFieldsId
 */
function setFieldsBackgroundDefault(viewFieldsId) {
	if (null == viewFieldsId) {
		return;
	}
	for (var d in viewFieldsId) {			
		idName = viewFieldsId[d];
		if ( dijit.byId(idName) == null ) {
			continue;
		}		
		var domNode = dijit.byId(idName).domNode;
		dojo.style(domNode,"background","#FFFFFF"); //set bg to white			
	}	
}

/**
 * 設定下拉項目資料
 * 
 * @param data			下拉項目json資料
 * @param destSelectId	下拉id
 */
function setSelectItems(data, destSelectId) {
	var destSelect=dijit.byId(destSelectId);
	if (destSelect==null) {
		return;
	}
	destSelect.removeOption(destSelect.getOptions());	
	for (var i=0; data.items!=null && i<data.items.length; i++) {
		destSelect.addOption( 
				{ 
					label: data.items[i].value, 
					value: data.items[i].key 
				} 
		);		
	}
}
/**
 * 清除下拉項目資料
 * 
 * @param pleaseSelect	是否要出現請選擇項目
 * @param destSelectId	下拉id
 */
function clearSelectItems(pleaseSelect, destSelectId) {
	var destSelect=dijit.byId(destSelectId);
	if (destSelect==null) {
		return;
	}
	destSelect.removeOption(destSelect.getOptions());
	if (!pleaseSelect) {
		return;
	}
	destSelect.addOption( 
			{ 
				label: _gscore_please_select_name, 
				value: _gscore_please_select_id 
			} 
	);
}

/**
 * 來源 Select 改變後 觸發 目的Select 改變資料項目
 * 
 * @param srcSelectId		來源Select
 * @param destSelectId		目的Select
 * @param action			json的action
 * @param parameters		送出的參數如 { 'id' : '123' }
 */
function selectChangeTriggerRefreshTargetSelectItems(srcSelectId, destSelectId, action, parameters) {
	xhrSendParameter(
			action, 
			parameters, 
			'json', 
			_gscore_dojo_ajax_timeout, 
			_gscore_dojo_ajax_sync, 
			true, 
			function(data){
    			if ('Y'==data.success) {
    				setSelectItems(data, destSelectId);
    			} else {
    				clearSelectItems(destSelectId);
    			}			
			}, 
			function(error){
				alert(error);
				clearSelectItems(destSelectId);
			}
	);
}

function _getSystemIconUrl(iconId) {
	var icon = '';
	for (var i=0; i<_coreIconsIdList.length; i++) {
		if ( _coreIconsIdList[i] == iconId ) {
			icon = viewPage.getBasePath() + "/" + _coreIconsList[i];
		}
	}
	return icon;
}


/**
 * 取出 google map response 的地址資料
 * 
 * @param lat	緯度
 * @param lng	經度
 * @param _setAddressFn	處理地址資料json-data的function
 */
function getGoogleMapAddressName(lat, lng, _setAddressFn) {
	var queryUrl="http://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lng + "&sensor=false&language=" + _gscore_googleMapLanguage;
	showPleaseWait();
    var xhrArgs={
    		url			: 	queryUrl,
    		postData	: 	dojo.objectToQuery({}), 
    		handleAs	: 	'json',
    		timeout		: 	_gscore_dojo_ajax_timeout,
    		sync		: 	_gscore_dojo_ajax_sync,
    		preventCache: 	true,
    		headers		: {
    			"X-Requested-With": null
    		},
    		load: function(data) {	
				setTimeout(function(){
					hidePleaseWait();
				}, 350);     			
    			if (data==null || (typeof data=='undefined') ) {
    				alert('Unexpected error!');
    				return;
    			}    			  			
    			_setAddressFn( data );
    		},
    		error: function(error) {
				setTimeout(function(){
					hidePleaseWait();
				}, 350);    			
				alert(error);
    		}
    };
    var deferred = dojo.xhrGet(xhrArgs);
}

