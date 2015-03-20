<script type="text/javascript">

var grid${id}=null;
var store${id}=null;


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
	submitQueryGrid${id}();
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
	var page=parseInt( dijit.byId('gridQuery_pageOf_select${id}').value, _gscore_default_pageRowSize )-1;
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
	var page=parseInt(dijit.byId('gridQuery_pageOf_select${id}').value, _gscore_default_pageRowSize)+1;
	if (page>parseInt(dojo.byId('gridQuery_pageOf_size${id}').value, _gscore_default_pageRowSize)) {
		page=parseInt(dojo.byId('gridQuery_pageOf_size${id}').value, _gscore_default_pageRowSize);
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
	document.getElementById('gridQuery_pageOf_rowCount${id}').innerHTML='';
	document.getElementById('gridQuery_pageOf_rowCount${id}').innerHTML=postData.pageOfCountSize;
	dojo.byId('gridQuery_pageOf_size${id}').value=postData.pageOfSize;
	showGridQueryToolsTable${id}();
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

//============================================================================================	
</script>