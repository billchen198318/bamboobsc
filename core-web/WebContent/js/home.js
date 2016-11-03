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
		dojox.image.preload( ["./images/sort-up.png", "./images/sort-down.png"] ); // customized grid cell sort button need
		
		// for bambooBSC patch flat-themes-1.11.0
		dojox.image.preload( ["./patch_flat_themes_1_11_icons/expando_opened.png", "./patch_flat_themes_1_11_icons/expando_closed.png", "./patch_flat_themes_1_11_icons/loadingAnimation.gif", "./patch_flat_themes_1_11_icons/error.png"] );
		
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
	viewPage.loadingDlgShow();
}

function loadingDlgHide() {
	viewPage.loadingDlgHide();
}

function showPleaseWait() {
	viewPage.showPleaseWait();
}
function hidePleaseWait() {
	viewPage.hidePleaseWait();
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
 * @param isFile 如果type是會狀態變更的資料, isFile只能使用'N', 除非自己補寫更新 tb_sys_upload.type 狀態時, 也會搬移檔案至新type目錄之下.
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
	showPleaseWait();
	dojo.io.iframe.send({
		url: './core.commonUploadFileAction.action',
		form: formId,
		method: "post",
		handleAs: "json",
		timeout: _gscore_dojo_ajax_timeout,
		sync: _gscore_dojo_ajax_sync,		
		preventCache: true,
		load: function(response, ioArgs) {
			setTimeout(function(){
				hidePleaseWait();
			}, 350);			
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
			setTimeout(function(){
				hidePleaseWait();
			}, 350);			
			alert(response);
			if (null!=errFn && eval("typeof " + errFn + "=='function'") ) {
				errFn();
			}
		}
	});	
}

function getUploadFileNames(uploadOid) {
	var names = [];
	try {
		xhrSendParameterNoWatitDlg(
				'./core.commonLoadUploadFileNamesAction.action', 
				{
					'uploadOid'	: uploadOid 
				},
				'json', 
				_gscore_dojo_ajax_timeout, 
				true, // not change to false(async)
				true, 
				function(data){
	    			if ('Y'==data.success) {
	    				names.push({
	    					'oid'		: uploadOid,
	    					'showName'	: data.showName,
	    					'fileName'	: data.fileName,
	    					'fileExist'	: data.fileExist
	    				});	    				
	    			}
				}, 
				function(error){
					alert(error);					
				}
		);		
	} catch (e) {				
	}
	return names;
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
	// 2015-09-18 rem
	//window.open(url, title, "resizable=yes,scrollbars=yes,status=yes");
	
	// 2015-09-18 add
	var width = 1280;
	var height = 768;
	var iconUrl = _getSystemIconUrl('APPLICATION_PDF');
	if ( 'PDF' != format ) {
		iconUrl = _getSystemIconUrl('EXCEL');
	}
	url += '&isIframeMode=Y'; // 有用iframe 時 url 加上 isIframeMode 參數  , 這樣 session 清除時, 頁面不是導向login-page, 而是導向警告error-page 
	var bodyStr = '';
	bodyStr += '<table border="0" width="100%" cellpadding="0" cellspacing="0"><tr valign="top"><td align="left" valign="middle" bgcolor="#F5F5F5"><img src="./images/head_logo.jpg" border="0" alt="logo" style="vertical-align:middle;margin-top:0.25em"/><b><font size="2" color="#394045" style="vertical-align:middle;margin-top:0.25em">&nbsp;View report</font></b><br/><hr color="#3794E5" size="2"></td></tr></table>';
	bodyStr += '<table width="100%" bgcolor="#ffffff" border="0"><tr><td align="center"><iframe src="' + url + '" align="left" scrolling="yes" frameborder="0" border="0" width="100%" height="' + height + '"></iframe></td></tr></table>';
	var viewDialog = new dojox.widget.DialogSimple({
		title			: 	'<img src="' + iconUrl + '" border="0" />' + '&nbsp;View report | ' + title,
		executeScripts	:	true,
		style			: 	'width: ' + width + 'px; height: ' + height + 'px;',
		content			: 	bodyStr
	});
	viewDialog.startup();
	viewDialog.show();	
	
}

/**
 * 打開 code 編輯器 OpenWindow 模式
 * 
 * @param uploadOid
 * @param valueFieldId
 * @param okFn
 * @param lang ( java or jsp )
 */
function openCommonCodeEditorWindow( uploadOid, valueFieldId, okFn, lang ) { // 重新修改了 CORE_PROGCOMM0004Q_Dlg , 不使用原本的 CORE_PROGCOMM0004Q_DlgShow function
	var url = "./core.commonCodeEditorAction.action?cbMode=Y&valueFieldId=" + valueFieldId + "&okFn=" + okFn + '&lang=' + lang;
	if ( null != uploadOid && '' != uploadOid ) {
		url += '&oid=' + uploadOid;
	}
	var width = 1280;
	var height = 700;
	url += '&isIframeMode=Y'; // 有用iframe 時 url 加上 isIframeMode 參數  , 這樣 session 清除時, 頁面不是導向login-page, 而是導向警告error-page
	var bodyStr = '';
	bodyStr += '<table border="0" width="' + width + 'px" cellpadding="0" cellspacing="0"><tr valign="top"><td align="left" valign="middle" bgcolor="#F5F5F5"><img src="./images/head_logo.jpg" border="0" alt="logo" style="vertical-align:middle;margin-top:0.25em"/><b><font size="2" color="#394045" style="vertical-align:middle;margin-top:0.25em">&nbsp;Expression editor</font></b><br/><hr color="#3794E5" size="2"></td></tr></table>';
	bodyStr += '<table width="' + width + 'px" bgcolor="#ffffff" border="0"><tr><td align="center"><iframe src="' + url + '" align="left" scrolling="yes" frameborder="0" border="0" width="' + (width-25) + 'px" height="' + height + '"></iframe></td></tr></table>';	
	dijit.byId(_gscore_common_codeeditor_dialog_id).set('content', bodyStr);
	dijit.byId(_gscore_common_codeeditor_dialog_id).show();	
}

/**
 * 下載檔案 or 檢視圖片, 會先檢查檔案是否存在
 * 
 * @param type
 * @param uploadOid
 * @param paramData
 */
function openCommonLoadUpload( type, uploadOid, paramData ) {
	if (null != paramData && null != paramData["url"] && '' != paramData["url"]) { // 自定義 url 讀取資料模式, 直接呼叫 openCommonLoadUploadDataAction
		openCommonLoadUploadDataAction( type, uploadOid, paramData );
		return;
	}
	xhrSendParameterNoWatitDlg(
			'./core.commonLoadUploadFileNamesAction.action', 
			{
				'uploadOid'	: uploadOid 
			},
			'json', 
			_gscore_dojo_ajax_timeout, 
			true, // not change to false(async)
			true, 
			function(data){
    			if ('Y'!=data.success || 'Y'!=data.fileExist) {
    				alert( data.message );
    				return;
    			}
    			openCommonLoadUploadDataAction( type, uploadOid, paramData );
			}, 
			function(error){
				alert(error);					
			}
	);			
}

/**
 * 下載檔案 or 檢視圖片
 * 
 * @param type
 * @param uploadOid
 * @param paramData
 */
function openCommonLoadUploadDataAction( type, uploadOid, paramData ) {
	var downloadIFrameId = '_gs_downloadIFrame';
	if ( 'view' == type ) {
		var isDialog = paramData["isDialog"];
		var width = paramData["width"];
		var height = paramData["height"];
		var title = paramData["title"];
		if (null == title || '' == title) {
			title = 'bambooBSC';
		}
		var url = "./core.commonLoadUploadFileAction.action?type=view&oid=" + uploadOid;
		if (null != paramData["url"] && '' != paramData["url"]) {
			url = paramData["url"];
		}
		if ( "Y" != isDialog ) {
			window.open(
					urld,
					title,
		            "resizable=yes,scrollbars=yes,status=yes,width=" + width + ",height=" + height ); 			
		} else {			
			url += '&isIframeMode=Y'; // 有用iframe 時 url 加上 isIframeMode 參數  , 這樣 session 清除時, 頁面不是導向login-page, 而是導向警告error-page 
			var bodyStr = '';
			bodyStr += '<table border="0" width="100%" cellpadding="0" cellspacing="0"><tr valign="top"><td align="left" valign="middle" bgcolor="#F5F5F5"><img src="./images/head_logo.jpg" border="0" alt="logo" style="vertical-align:middle;margin-top:0.25em"/><b><font size="2" color="#394045" style="vertical-align:middle;margin-top:0.25em">&nbsp;View content</font></b><br/><hr color="#3794E5" size="2"></td></tr></table>';
			bodyStr += '<table width="100%" bgcolor="#ffffff" border="0"><tr><td align="center"><iframe src="' + url + '" align="left" scrolling="yes" frameborder="0" border="0" width="100%" height="' + height + '"></iframe></td></tr></table>';
			var viewDialog = new dojox.widget.DialogSimple({
				title			: 	'<img src="' + _getSystemIconUrl('GWENVIEW') + '" border="0" />' + '&nbsp;View content | ' + title,
				executeScripts	:	true,
				style			: 	'width: ' + width + 'px; height: ' + height + 'px;',
				content			: 	bodyStr
			});
			viewDialog.startup();
			viewDialog.show();				
		}
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
		//dojo.style(domNode,"background","#FFF0BC"); // OLD-ver: set bg to like red #F8E0E0
		dojo.style(domNode,"background","#faffbd");
	}
}

/**
 * 主要是把輸入條件不合的欄位訊息label 顯示訊息
 * 
 * @param fieldsId
 * @param fieldsMessage
 * @param viewFieldsId
 */
function setFieldsNoticeMessageLabel(fieldsId, fieldsMessage, viewFieldsId) {
	if (null == fieldsId || null == fieldsMessage || null == viewFieldsId ) {
		return;
	}
	for (var i=0; i<fieldsId.length; i++) {
		var idName = '';
		var msg = '';
		for (var d in viewFieldsId) {		
			if (fieldsId[i] == d) {
				idName = viewFieldsId[d];
				msg = fieldsMessage[d];
			}
		}
		if (idName=='') {
			continue;
		}
		idName = idName + _gscore_inputfieldNoticeMsgLabelIdName;
		if ( dojo.byId(idName) == null ) {
			continue;
		}
		//dojo.byId(idName).innerHTML = "<font style='border-radius: 5px; background-color: #B40404;' color='#fafafa'>&nbsp;" + msg + "&nbsp;</font>";
		dojo.byId(idName).innerHTML = "<span style='border-radius: 5px; background-color: #fcdede; border: 1px #d2b2b2 solid; color: #b11157; height: 12px; display: inline-block; overflow: hidden; margin: 0; vertical-align:top; line-height: 12px;'>&nbsp;" + msg + "&nbsp;</span>";
	}	
}

/**
 * 顯示notice message label
 * 
 * @param noticeLabelId	要自己組出正確的 id, 要自己組成  'label的id' + _gscore_inputfieldNoticeMsgLabelIdName
 * @param message
 */
function showFieldsNoticeMessageLabel(noticeLabelId, message) {
	if (dojo.byId(noticeLabelId) == null) {
		return;
	}
	//dojo.byId(noticeLabelId).innerHTML = "<font style='border-radius: 5px; background-color: #B40404;' color='#fafafa'>&nbsp;" + message + "&nbsp;</font>";
	dojo.byId(noticeLabelId).innerHTML = "<span style='border-radius: 5px; background-color: #fcdede; border: 1px #d2b2b2 solid; color: #b11157; height: 12px; display: inline-block; overflow: hidden; margin: 0; vertical-align:top; line-height: 12px;'>&nbsp;" + message + "&nbsp;</span>";
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
 * 主要是把輸入條件不合的欄位訊息label 顯示訊息清除掉
 * 
 * @param viewFieldsId
 */
function setFieldsNoticeMessageLabelDefault(viewFieldsId) {
	if (null == viewFieldsId) {
		return;
	}
	for (var d in viewFieldsId) {			
		idName = viewFieldsId[d] + _gscore_inputfieldNoticeMsgLabelIdName;
		if ( dojo.byId(idName) == null ) {
			continue;
		}		
		dojo.byId(idName).innerHTML = "&nbsp;";	
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

function setPageOfSortAsc(_id) {
	if (dojo.byId(_id)==null) {
		return;
	}
	dojo.byId(_id).value = "ASC";
}

function setPageOfSortDesc(_id) {
	if (dojo.byId(_id)==null) {
		return;
	}
	dojo.byId(_id).value = "DESC";	
}

function setPageOfOrderBy(_id, queryFieldName) {
	if (dojo.byId(_id)==null) {
		return;
	}
	dojo.byId(_id).value = queryFieldName;	
}

/**
 * 取出 google map response 的地址資料
 * 
 * @param lat	緯度
 * @param lng	經度
 * @param _setAddressFn	處理地址資料json-data的function
 */
function getGoogleMapAddressName(lat, lng, _setAddressFn) {
	var queryUrl = _gscore_googleMapUrl + "/maps/api/geocode/json?latlng=" + lat + "," + lng + "&sensor=false&language=" + _gscore_googleMapLanguage;
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

