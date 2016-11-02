<script type="text/javascript">

var grid${id}=null;
var store${id}=null;
var grid${id}_before_select_page = 1; // for select change to textbox

// ============================================================================================
// json-grid 換頁 Change Page	
// ============================================================================================

/**
 * #gridQuery_table#
 * #gridQuery_pageOf_rowCount#		span-id 顯示用
 * #gridQuery_pageOf_size#			hidden欄位
 * #gridQuery_pageOf_select#		select下拉
 * #gridQuery_pageOf_showRow#		select下拉
 *
 *
 */
function submitQueryGrid${id}() {
	//hiddenGridQueryToolsTable();
	getQueryGrid${id}(); 
}

/**
 * 下拉頁次改變
 */
function changeGridQueryPageOfSelect${id}() {
	
	// ----------------------------------------------------------------------------
	// for select change to textbox
	// ----------------------------------------------------------------------------
	if ( !( /^\+?(0|[1-9]\d*)$/.test(dijit.byId('gridQuery_pageOf_select${id}').value) ) ) { // not a page number
		dijit.byId('gridQuery_pageOf_select${id}').set('value', '1', false);
	}
	var page = parseInt(dijit.byId('gridQuery_pageOf_select${id}').value);
	if ( isNaN(page) || page <= 0 ) { // not a page number
		dijit.byId('gridQuery_pageOf_select${id}').set('value', '1', false);
	}
	if (page>( parseInt(dojo.byId('gridQuery_pageOf_size${id}').value, 10) || 1 ) ) { // 頁面最小要是1
		page=( parseInt(dojo.byId('gridQuery_pageOf_size${id}').value, 10) || 1 );
		dijit.byId('gridQuery_pageOf_select${id}').set('value', page+'', false);
	}
	// ----------------------------------------------------------------------------
	
	if ( grid${id}_before_select_page != page ) {
		submitQueryGrid${id}();
	}	
	
}

/**
 * 下拉顯示row改變
 */
function changeGridQueryPageOfShowRow${id}() {
	dijit.byId('gridQuery_pageOf_select${id}').set('value', '1', false);
	submitQueryGrid${id}();
}

/**
 * 到第1頁icon click
 */
function changeGridQueryToFirst${id}() {	
	dijit.byId('gridQuery_pageOf_select${id}').set('value', '1', false);
	submitQueryGrid${id}();
}

/**
 * 到最後1頁icon click
 */
function changeGridQueryToLast${id}() {
	dijit.byId('gridQuery_pageOf_select${id}').set('value', dojo.byId('gridQuery_pageOf_size${id}').value, false);
	submitQueryGrid${id}();
}

/**
 * 到上1頁icon click
 */
function changeGridQueryToPrev${id}() {
	var page=( parseInt( dijit.byId('gridQuery_pageOf_select${id}').value, 10 ) || 0 )-1;
	if (page<=0) {
		page=1;
	}
	dijit.byId('gridQuery_pageOf_select${id}').set('value', page+'', false);
	submitQueryGrid${id}();
}

/**
 * 到下1頁icon click
 */
function changeGridQueryToNext${id}() {
	var page=( parseInt(dijit.byId('gridQuery_pageOf_select${id}').value, 10) || 0 )+1;
	if (page>( parseInt(dojo.byId('gridQuery_pageOf_size${id}').value, 10) || 1 ) ) { // 頁面最小要是1
		page=( parseInt(dojo.byId('gridQuery_pageOf_size${id}').value, 10) || 1 );
	}
	dijit.byId('gridQuery_pageOf_select${id}').set('value', page+'', false);
	submitQueryGrid${id}();
}

/**
 * 初始pageOf設定
 */
function initGridQueryPageOf${id}() {	
	document.getElementById('gridQuery_pageOf_rowCount${id}').innerHTML='0';
	dijit.byId('gridQuery_pageOf_select${id}').set('value', '1', false);
	dijit.byId('gridQuery_pageOf_showRow${id}').set('value', _gscore_default_pageRowSize+'', false);
	grid${id}_before_select_page = 1; // for select change to textbox
}

/**
 * 不顯示換頁TABLE
 */
function hiddenGridQueryToolsTable${id}() {
	dojo.setStyle("gridQuery_table${id}", {"display": "none"}); 
}

/**
 * 顯示換頁TABLE
 */
function showGridQueryToolsTable${id}() {
	dojo.setStyle("gridQuery_table${id}", {"display": ""}); 
}

function getGridQueryPageOfSize${id}() {
	return dojo.byId('gridQuery_pageOf_size${id}').value;
}

function getGridQueryPageOfSelect${id}() {
	return dijit.byId('gridQuery_pageOf_select${id}').value;
}

function getGridQueryPageOfShowRow${id}() {
	return dijit.byId('gridQuery_pageOf_showRow${id}').value;
}

/**
 * 回傳回來的json資料, 將頁次pageOfSize, 總頁數pageOfSize, 顯示資料row筆數pageOfSize 設定到換頁元件中
 */
function setChangePageComponentValue${id}(postData) {
	document.getElementById('gridQuery_pageOf_size_show${id}').innerHTML='0';
	document.getElementById('gridQuery_pageOf_rowCount${id}').innerHTML='';
	document.getElementById('gridQuery_pageOf_rowCount${id}').innerHTML=postData.pageOfCountSize;
	dojo.byId('gridQuery_pageOf_size${id}').value=postData.pageOfSize;
	showGridQueryToolsTable${id}();
	
	
	// ----------------------------------------------------------------------------
	// for select change to textbox
	// ----------------------------------------------------------------------------	
	/*
	var pageSelect=dijit.byId('gridQuery_pageOf_select${id}');
	var rowSelect=dijit.byId('gridQuery_pageOf_showRow${id}');
	pageSelect.removeOption(pageSelect.getOptions());
	for (var i=1; i<=postData.pageOfSize; i++) {
		pageSelect.addOption( { label: i+'', value: i+'' } );
		if (i+''==postData.pageOfSelect) {
			pageSelect.set('value', i+'', false);
		}
	}
	rowSelect.set('value', postData.pageOfShowRow, false);
	*/
	
	dijit.byId('gridQuery_pageOf_select${id}').set('value', postData.pageOfSelect+'', false);
	
	grid${id}_before_select_page = postData.pageOfSelect;
	
	// ----------------------------------------------------------------------------	
	
	
	document.getElementById('gridQuery_pageOf_size_show${id}').innerHTML=postData.pageOfSize;
	if (postData.pageOfCountSize==null
			|| postData.pageOfCountSize=="" 
			|| postData.pageOfCountSize=="0") {
		hiddenGridQueryToolsTable${id}();
	} 
}

/**
 * 檢查回來的json
 */
function checkGridQueryPostData${id}(postData) {
	if ("Y" != postData.login) {
		alert('timeout, login again!');
		//window.location=_gsbsc_basePath;
	}
	if (""!=postData.message && null!=postData.message) { // 查尋只要有 message 訊息就顯示出來
		alertDialog(_getApplicationProgramNameById('${programId}'), postData.message, function(){}, postData.success);		
	}
}

function clearQuery${id}() {
	if (grid${id}!=null) {
		var dummy = {items: []};
		grid${id}.setStore(new dojo.data.ItemFileWriteStore({data: dummy}));		
	}
	initGridQueryPageOf${id}();
	hiddenGridQueryToolsTable${id}();	
	${clearQueryFn}
}

function dataGrid${id}(postData) {
	checkGridQueryPostData${id}(postData);
	setChangePageComponentValue${id}(postData);
	if (store${id}==null) {
		store${id} = new dojo.data.ItemFileReadStore({ data: postData });
	} else {
		store${id}.close();
		store${id}=null;
		store${id} = new dojo.data.ItemFileReadStore({ data: postData });
	}
	if (grid${id}==null) {
		grid${id} = new dojox.grid.DataGrid({
			store: store${id},
			structure: ${gridFieldStructure}
			<#if disableOnHeaderCellClick == "Y" >
			,
			onHeaderCellClick: function(e) {
				// nothing
				// alert(e.cell.field);				
				// dojo-release-1.10.1-src/dojox/grid/enhanced/_Events.js				
			}
			</#if>
		}, "${id}");
		grid${id}.startup();			
	} else {
		grid${id}.setStore(store${id});
		grid${id}._refresh();
	}		
}

function doGridQueryShowPreviewHtml${id}() {
	if (store${id}==null) {
		return;
	}
	var structureFields = ${gridFieldStructure};
	var body = '<a href="javascript:window.close();" style="text-decoration: none; font-weight: bold; color:#3794E5" >Close</a>';
	body += '<table width="100%" border="0" cellspacing="1" cellpadding="1" style="border:1px #d5cdb5 solid; background: #d5cdb5; border-radius: 5px;">';
	body += '<tr>';	
	for (var f=0; f<structureFields.length; f++) {
		var title = structureFields[f].name.split('<br/>')[0];
		if ( "oid" == structureFields[f].field ) {
			title = "Primary Key";
		}
		body += '<td bgcolor="#f1eee5"><b>' + title + '</b></td>';
	}
	body += '</tr>';
	store${id}.fetch({
		onComplete:function(items, request) {
			for (var i=0; i<items.length; i++) {
				body += '<tr>';
				for (var f=0; f<structureFields.length; f++) {
					var valStr = eval('items[' + i + '].' + structureFields[f].field );
					if ( valStr == null ) {
						valStr = '&nbsp;';
					}
					valStr += '';
					valStr = valStr.replace(/</g, "〈").replace(/>/g, "〉");
					var nowBgcolor = '#fffdf3';
					if (i%2 == 0) {
						nowBgcolor = '#ffffff';
					}
					body += '<td bgcolor="' + nowBgcolor + '">' + valStr + '</td>';
				}
				body += '</tr>';
			}
		}
	});
	body += '</table>';
	body += '<a href="javascript:window.close();" style="text-decoration: none; font-weight: bold; color:#3794E5" >Close</a>';
	
	var newWindow = window.open();
	newWindow.document.write( body );
	newWindow.document.close();
	
}

//============================================================================================	
</script>