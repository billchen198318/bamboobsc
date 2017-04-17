<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/00-Catalog.md">⌂ Catalog</a><br/>
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/03-LogicService.md">⇦ Previous section 03-Logic service</a>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/05-ProgramRegistrationAndMenuSettings.md">⇨ Next section 05-Program registration and Menu settings</a>


# 04 - Controller
# Introduction
bambooBSC page view / controller is use JSP / Struts2 and DOJO.<br>


***You must first understand the following framework***<br/>
1. Spring http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/<br/>
2. Struts2 https://struts.apache.org/docs/home.html<br/>
3. DOJO javascript framework https://dojotoolkit.org/<br/>

# BaseSupportAction
BaseSupportAction is for default page action, usually use of Management page, Edit page, Create page.<br/>

| Name | Return | description |
| --- | --- | --- |
| getAccountObj() | AccountObj | get user account object |
| getAccountId() | String | get user account-id |
| getAccountOid() | String | get user account OID |
| getSysCurrentId() | String | get current-id, CORE-WEB, GSBSC-WEB, QCHARTS-WEB current-id is same value, it save in Cookie encryption with AES-128 |
| getIsSuperRole() | boolean | Administrator(admin, * role) true, other role false |
| getActionMethodProgramId() | String | get `@ControllerMethodAuthority` programId value |
| getServletContext() | ServletContext | get ServletContext |
| getHttpServletResponse() | HttpServletResponse | get HttpServletResponse |
| getHttpServletRequest() | HttpServletRequest | get HttpServletRequest |
| getPageOf() | PageOf | PageOf is for Page query grid use |
| getSearchValue() | SearchValue | SearchValue is for Page query grid use |
| getErrorMessage() | String | error msg |
| getErrorContent() | String | config of base.page.errorContent |
| getInterceptorActionInfoProvide() | ActionInfoProvide | action info |
| getDojoAjaxTimeout() | int | get config of base.page.dojoAjaxTimeout |
| getDojoAjaxSync() | String | get config of base.page.dojoAjaxSync |
| getDojoLocal() | String | base.page.dojoLocal |
| getVerMsg() | String | base.page.verMsg |
| getJsVerBuild() | String | base.page.jsVerBuild |
| getGoogleMapEnable() | String | googleMap.enable |
| getGoogleMapKey() | String | googleMap.key |
| getGoogleMapDefaultLat() | String | googleMap.defaultLat |
| getGoogleMapDefaultLng() | String | googleMap.defaultLng |
| getGoogleMapLanguage() | String | googleMap.language |
| getGoogleMapClientLocationEnable() | String | googleMap.clientLocationEnable |
| getTwitterEnable() | String | twitter.enable |
| getPageMessage() | String | page message |
| getNowDate() | String | get now date, format: 2016/05/26 |
| getNowDate2() | String | get now date, format: 2016-05-26 |
| getUuid() | String | get a UUID value |
| defaultString(String sourceString) | String | null return blank value |
| getBasePath() | String | get full URL |
| getDojoMainTabContainer() | String | get main DOJO tabContainer Id `gscoreTabContainer` |
| checkFields(String[] fieldsName, String[] msg, Class`<IActionFieldsCheckUtils>[]` checkUtilsClass)<br/><br/>checkFields(String[] fieldsName, String[] msg, Class`<IActionFieldsCheckUtils>`[] checkUtilsClass, List`<String>` fieldsId)<br/><br/>checkFields(String[] fieldsName, String[] msg, Class`<IActionFieldsCheckUtils>`[] checkUtilsClass, String[] methodsName, List`<String>` fieldsId) | void | check input field |
| transformFields2ValueObject(T valueObj, String... fieldsName) | `<T>` BaseValue | fill page fields Map variable to value object |
| transformFields2ValueObject(T valueObj, String[] objFieldsName , String[] fieldsName) | `<T>` BaseValue | fill page fields Map variable to value object |
| transformSearchGridList2JsonDataMapList(List<T> searchList, String... fields) | List`<Map<String, String>>` | fill List value object data to List map for query page grid need |
| transformList2JsonDataMapList(List<T> searchList, String[] objFields, String[] mapKeys) | List`<Map<String, String>>` | fill List value object data to List map for query page grid need |
| providedSelectZeroDataMap(boolean pleaseSelectItem) | Map`<String, String>` | provide zero Map for `gs:select` or `s:select` tag |
| isNoSelectId(String value) | boolean | if no select options item return true |
| transformAppendIds2ListByEncode(String appendIds)<br/><br/>transformAppendIds2List(String appendIds)<br/><br/>joinAppend2String(List`<String>` datas) | String | for text work |

# IBaseAdditionalSupportAction interfaces
The IBaseAdditionalSupportAction interfaces support View Page( JSP ) to get action method program-id and program-name.
```JAVA
@Override
public String getProgramName() {
	try {
		return MenuSupportUtils.getProgramName(this.getProgramId());
	} catch (ServiceException e) {
		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return "";
}

@Override
public String getProgramId() {
	return super.getActionMethodProgramId();
}
```

JSP code for IBaseAdditionalSupportAction show head label.
```JSP
<jsp:include page="../header.jsp"></jsp:include>
```

<br/>
<br/>

# BaseJsonAction
BaseJsonAction is extends BaseSupportAction, it is for Save/Update/Delete controller action use.

| Name | Return | description |
| --- | --- | --- |
| allowJob() | boolean | usually only true, because before will check by UserLoginInterceptor, ControllerAuthorityCheckInterceptor |
| getNoAllowMessage() | String | get no allow message |
| getLogin() | String | Y/N |
| getIsAuthorize() | String | Y/N |
| getMessage() | String | message |
| getSuccess() | String | Y/N |
| getFieldsId() | List`<String>` | get page fields-id, when throw ControllerException, view page will RED-color input field background style |

<br/>
<br/>
# BaseQueryGridJsonAction
BaseQueryGridJsonAction is extends BaseJsonAction, it main support Grid query.

| Name | Return | description |
| --- | --- | --- |
| getItems() | List`<Map<String, String>>` | grid results list |
| getPageOfShowRow() | String | String-number of page show row size |
| getPageOfSelect() | String | String-number of page number of select |
| getPageOfCountSize() | String | String-number of grid record size |
| getPageOfSize() | String | String-number of page total size |

<br/>
<br/>
# Controller annotation

| Name | description |
| --- | --- |
| `@ControllerAuthority` | `check=true` , ControllerAuthorityCheckInterceptor will check |
| `@ControllerMethodAuthority` | programId value set controller action program-id |

Program id naming rules example:
<br/>

| Program-id | description |
| --- | --- |
| BSC_PROG002D | `BSC` is BSC-system, `CORE` is CORE-SYSTEM, `PROG002D` the `D` mean it is a menu item directory |
| BSC_PROG002D0001Q | 0001Q is for Query page, 0001 is serial-no, `Q` is mean Query page |
| BSC_PROG002D0001A | 0001A is for Create page, 0001 is serial-no, `A` is mean Create/Add page |
| BSC_PROG002D0001E | 0001E is for Edit page, 0001 is serial-no, `E` is mean Edit page |

<br/>
<br/>

# Program page show at DOJO-Tab javascript
Show at DOJO-Tab javascript function system auto generated when the program has registered.

| function-key | description |
| --- | --- |
| `_TabShow()` | Tab show |
| `_TabRefresh()` | Tab refresh |
| `_TabClose()` | Tab close |
| `TabShow(oid)` | Tab show with Edit mode |
| `_DlgShow()` | Dialog page show |
| `_DlgShow(oid)` | Dialog page show with Edit mode |
| `_DlgHide()` | Dialog page hide |


Example:

| Program-id | system auto generated javascript function | description |
| --- | --- | --- |
| BSC_PROG002D0001Q | BSC_PROG002D0001Q_TabShow() | BSC_PROG002D0001Q for query management page show |
| BSC_PROG002D0001Q | BSC_PROG002D0001Q_TabRefresh() | BSC_PROG002D0001Q for refresh query management page |
| BSC_PROG002D0001Q | BSC_PROG002D0001Q_TabClose() | BSC_PROG002D0001Q for close query management page |
| BSC_PROG002D0001E | BSC_PROG002D0001E_TabShow(oid) | BSC_PROG002D0001E for Edit mode page |
| CORE_PROGCOMM0001Q | CORE_PROGCOMM0001Q_DlgShow() | for Dialog mode page |
| BSC_PROG005D0001A_S03 | BSC_PROG005D0001A_S03_DlgShow(oid) | for Dialog page with Edit data mode |
| CORE_PROGCOMM0001Q | CORE_PROGCOMM0001Q_DlgHide() | for hide Dialog page |


<br/>
<br/>

# GreenStep page Tag
The gs tag is work with Struts2 and DOJO javascript.

| Tag | description |
| --- | --- |
| gs:button | page DOJO button compoment, can easy do send data to action no need write many javascript |
| gs:grid | page query grid, show DOJO grid |
| gs:select | page DOJO select compoment |
| gs:textbox | page DOJO input compoment |
| gs:toolbar | page head DOJO label compoment |


Use GS tag example:
```JSP
<%@ taglib prefix="gs" uri="http://www.gsweb.org/controller/tag" %>
```

***gs:button***

| Args | required | description |
| --- | --- | --- |
| id | true | compoment-id |
| name | true | compoment-name |
| programId | false | action-method program-id `${programId}` |
| label | false | button label text |
| showLabel | false | Y show label, N no show |
| onClick | true | click event function |
| iconClass | false | dojo-themes icon css class |
| cssClass | false | dojo-themes css class |
| xhrUrl | false | json action url |
| parameterType | false | postData or form |
| xhrParameter | false | send json data, parameterType = postData need xhrParameter |
| sync | false | Y or N |
| handleAs | false | dojo xhr handleAs type, default use `json`  |
| timeout | false | xhr timeout-time |
| preventCache | false | Y or N |
| loadFn | false | xhr load success function |
| errorFn | false | xhr load error function |
| confirmDialogMode | false | Y or N , Y when click button will show confirm dialog |
| confirmDialogTitle | false | confirm dialog title |
| confirmDialogMsg | false | confirm dialog message |

***gs:button example:***
```JSP

function BSC_PROG002D0001A_saveSuccess(data) {
	setFieldsBackgroundDefault(BSC_PROG002D0001A_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG002D0001A_fieldsId);		
		return;
	}	
	BSC_PROG002D0001A_clear();
}

function BSC_PROG002D0001A_clear() {
	setFieldsBackgroundDefault(BSC_PROG002D0001A_fieldsId);	
	dijit.byId('BSC_PROG002D0001A_title').set("value", "");	
	dijit.byId('BSC_PROG002D0001A_content').set("value", "");		
}

...

<gs:button name="BSC_PROG002D0001A_save" id="BSC_PROG002D0001A_save" onClick="BSC_PROG002D0001A_save();"
	handleAs="json"
	sync="N"
	xhrUrl="${basePath}/bsc.visionSaveAction.action"
	parameterType="postData"
	xhrParameter=" 
		{ 
			'fields.title'		: dijit.byId('BSC_PROG002D0001A_title').get('value'), 
			'fields.content'	: dijit.byId('BSC_PROG002D0001A_content').get('value')
		} 
	"
	errorFn=""
	loadFn="BSC_PROG002D0001A_saveSuccess(data);" 
	programId="${programId}"
	label="Save" 
	iconClass="dijitIconSave"
	cssClass="alt-primary"></gs:button>    			
<gs:button name="BSC_PROG002D0001A_clear" id="BSC_PROG002D0001A_clear" onClick="BSC_PROG002D0001A_clear();" 
	label="Clear" 
	iconClass="dijitIconClear"
	cssClass="alt-primary"></gs:button>

```

<br/>
<br/>

***gs:grid***

| Args | required | description |
| --- | --- | --- |
| id | true | compoment-id |
| programId | false | action-method program-id `${programId}` |
| gridFieldStructure | true | DOJO - grid field structure |
| clearQueryFn | true | clear grid |
| width | false | grid width |
| disableOnHeaderCellClick | false | Y / N , Y disable header cell click event |

***gs:grid example:***
```JSP

function BSC_PROG002D0001Q_GridFieldStructure() {
	return [
			{ name: "*", field: "oid", formatter: BSC_PROG002D0001Q_GridButtonClick, width: "15%" },  
			{ name: "ID", field: "visId", width: "25%" },
			{ name: "Title", field: "title", width: "60%" }			
		];
}

function BSC_PROG002D0001Q_GridButtonClick(itemOid) {
	var rd="";
	rd += "<img src=\"" + _getSystemIconUrl('PROPERTIES') + "\" border=\"0\" alt=\"edit\" onclick=\"BSC_PROG002D0001Q_edit('" + itemOid + "');\" />";
	rd += "&nbsp;&nbsp;&nbsp;&nbsp;";
	rd += "<img src=\"" + _getSystemIconUrl('APPLICATION_PDF') + "\" border=\"0\" alt=\"edit\" onclick=\"BSC_PROG002D0001Q_pdf('" + itemOid + "');\" />";
	rd += "&nbsp;&nbsp;&nbsp;&nbsp;";
	rd += "<img src=\"" + _getSystemIconUrl('REMOVE') + "\" border=\"0\" alt=\"delete\" onclick=\"BSC_PROG002D0001Q_confirmDelete('" + itemOid + "');\" />";
	return rd;	
}

...

<gs:button name="BSC_PROG002D0001Q_query" id="BSC_PROG002D0001Q_query" onClick="getQueryGrid_${programId}_grid();"
	handleAs="json"
	sync="N"
	xhrUrl="${basePath}/bsc.visionManagementGridQueryAction.action"
	parameterType="postData"
	xhrParameter=" 
		{ 
			'searchValue.parameter.visId'		: dijit.byId('BSC_PROG002D0001Q_visId').get('value'), 
			'searchValue.parameter.title'		: dijit.byId('BSC_PROG002D0001Q_title').get('value'),
			'pageOf.size'				: getGridQueryPageOfSize_${programId}_grid(),
			'pageOf.select'				: getGridQueryPageOfSelect_${programId}_grid(),
			'pageOf.showRow'			: getGridQueryPageOfShowRow_${programId}_grid()
		} 
	"
	errorFn="clearQuery_${programId}_grid();"
	loadFn="dataGrid_${programId}_grid(data);" 
	programId="${programId}"
	label="Query" 
	iconClass="dijitIconSearch"
	cssClass="alt-primary"></gs:button>
	
<gs:grid gridFieldStructure="BSC_PROG002D0001Q_GridFieldStructure()" 
	clearQueryFn="" 
	id="_${programId}_grid" 
	programId="${programId}"></gs:grid>	
	
```


<br/>
<br/>


***gs:select***

| Args | required | description |
| --- | --- | --- |
| id | true | compoment-id |
| name | true | component-name |
| width | false | component width |
| dataSource | true | can put action Map`<String, String>` variable, or json data |
| value | false | default select value |
| onChange | false | ocChange event function |
| readonly | false | Y / N |

***gs:select example:***
```JSP
<gs:select name="BSC_PROG002D0002Q_visionOid" dataSource="visionMap" id="BSC_PROG002D0002Q_visionOid"></gs:select>

...

<gs:select name="BSC_PROG006D0001E_S00_confirm" 
	dataSource="{\"Y\" : \"Yes\", \"N\" : \"Reject\"}" 
	id="BSC_PROG006D0001E_S00_confirm" 
	value="Y"></gs:select>

```



<br/>
<br/>

***gs:textbox***

| Args | required | description |
| --- | --- | --- |
| id | true | compoment-id |
| name | true | component-name |
| maxlength | false | input field maxlength |
| placeHolder | false | place holder text |
| width | false | field width |
| value | false | field value, can put action variable |
| readonly | false | Y / N |

***gs:textbox example:***
```JSP
<gs:textBox name="BSC_PROG006D0001E_title" id="BSC_PROG006D0001E_title" 
	value="pdca.title" 
	width="400" maxlength="100"></gs:textBox>
```

<br/>
<br/>

# Page common javascript function

| Name | return | description |
| --- | --- | --- |
| alertDialog(txtTitle, txtContent, callbackFn, successFlag) | void | show DOJO toaster, `txtTitle` is title, `txtContent` is message content, `callbackFn` callback function, `successFlag` flag Y - success, N - work fail, E - error or exception |
| confirmDialog(dialogId, title, question, callbackFn, e) | void | show confirm dialog, `dialogId` dialog-id, `title` dialog title, `question` dialog message content, `callbackFn` click OK,YES button do event, `e` mouse event `(window.event ? window.event : null)` |
| showPleaseWait() | void | show Please wait dialog |
| hidePleaseWait() | void | hide Please wait dialog |
| getGuid() | string | get a non-RFC GUID |
| xhrSendForm(_urlAction, _formId, _handleAs, _timeout, _sync, _preventCache, _loadFunction, _errFunction) | void | send form XHR mode.<br/> `_urlAction` action url<br/> `_formId` form id<br/> `_handleAs` use `json`<br/>`_loadFunction` function for load success<br/> `_errFunction` function for error-load |
| xhrSendParameter(_urlAction, _parameter, _handleAs, _timeout, _sync, _preventCache, _loadFunction, _errFunction) | void | send input-field XHR mode.<br/> `_urlAction` action url<br/> `_parameter` json parameter<br/> `_handleAs` use `json`<br/>`_loadFunction` function for load success<br/> `_errFunction` function for error-load |
| xhrSendFormNoWatitDlg(_urlAction, _formId, _handleAs, _timeout, _sync, _preventCache, _loadFunction, _errFunction) | void | send form XHR mode with no show please wait dialog |
| xhrSendParameterNoWatitDlg(_urlAction, _parameter, _handleAs, _timeout, _sync, _preventCache, _loadFunction, _errFunction) | void | send input-field XHR mode with no show please wait dialog |
| logoutEvent(success) | void | do logout, `success` true or false |
| openCommonUploadDialog(system, type, isFile, uploadOidField, callJsFunction, callJsErrFunction) | void | open upload file dialog<br/> `system` CORE,BSC,QCHARTS<br/> `type` tmp,image,common,kpi-doc,pdca-doc ( com.netsteadfast.greenstep.model.UploadTypes.java )<br/> `isFile` Y,N Y-use real file mode, N-use byte-blob mode<br/>`uploadOidField` the input-field id for upload success will set fill upload OID value<br/>`callJsFunction` success event, `callJsErrFunction` fail event |
| hideCommonUploadDialog() | void | hide upload file dialog |
| doUpload(formId, uploadId, callbackFn, errFn) | void | no need use openCommonUploadDialog to upload file, manual mode upload |
| getUploadFileNames(uploadOid) | string| get upload file show-name, `uploadOid` upload OID |
| openCommonSignatureDialog(system, uploadOidField, callJsFunction, callJsErrFunction) | void | signature dialog<br>`uploadOidField` input-field for signature-image upload OID ( `tb_sys_upload.OID` ) |
| openCommonJasperReportLoadWindow( title, jreportId, format, paramData ) | void | open jasperreport dialog |
| openCommonCodeEditorWindow( uploadOid, valueFieldId, okFn, lang ) | void | open common expression edit dialog, first need write expression to `tb_sys_upload` |
| openCommonLoadUpload( type, uploadOid, paramData ) | void | open view dialog or download for upload-file ( `tb_sys_upload` ) |
| openCommonLoadUploadDataAction( type, uploadOid, paramData ) | void | open view dialog or download for upload-file ( `tb_sys_upload` ) |
| setFieldsBackgroundAlert(fieldsId, viewFieldsId) | void | alert color for fields |
| setFieldsBackgroundDefault(viewFieldsId) | void | default color for fields |

other:<br/>
`setSelectItems(data, destSelectId)`<br/>
`clearSelectItems(pleaseSelect, destSelectId)`<br/>
`selectChangeTriggerRefreshTargetSelectItems(srcSelectId, destSelectId, action, parameters)`<br/>
`_getSystemIconUrl(iconId)`<br/>
`setPageOfSortAsc(_id)`<br/>
`setPageOfSortDesc(_id)`<br/>
`setPageOfOrderBy(_id, queryFieldName)`<br/>
`getGoogleMapAddressName(lat, lng, _setAddressFn)`<br/>

# Add controller to menu item and config authority
Please use:
<br/> 
1. Registration use `02 - Program registration` to add.<br/> 
2. Config menu use `03 - Menu settings` settings.<br/>
3. Role's permitted settings -> `01 - Role` function to add permitted value<br/>

<br/>
<br/>

# reference example:
Management query action:<br>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-web/src/com/netsteadfast/greenstep/bsc/action/VisionManagementAction.java<br/><br>
Grid query action:<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-web/src/com/netsteadfast/greenstep/bsc/action/VisionManagementGridQueryAction.java<br/><br>
Save data or update data action:<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-web/src/com/netsteadfast/greenstep/bsc/action/VisionSaveOrUpdateAction.java<br/><br>
View page / JSP:<br>
Management query view page:<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-web/WebContent/pages/vision/vision-management.jsp<br/><br/>
Create view page:<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-web/WebContent/pages/vision/vision-create.jsp<br/><br/>
Edit view page:<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-web/WebContent/pages/vision/vision-edit.jsp<br/><br/>
<br/><br/>
Spring config:<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-web/resource/applicationContext/controller/applicationContext-vision-web.xml<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-web/resource/applicationContext/applicationContext-STANDARD-WEB.xml<br/>
<br/>
Struts config:<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-web/resource/struts/struts-bsc-vision.xml
