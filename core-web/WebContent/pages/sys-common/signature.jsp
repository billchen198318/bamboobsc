<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="gs" uri="http://www.gsweb.org/controller/tag" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!doctype html>
<html itemscope="itemscope" itemtype="http://schema.org/WebPage">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <base href="<%=basePath%>">
    
    <title>bambooCORE</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="bambooCORE">
	<meta http-equiv="description" content="bambooCORE">
	
<style type="text/css">

</style>

<script type="text/javascript">

var CORE_PROGCOMM0003Q_canvas = null;
var CORE_PROGCOMM0003Q_signaturePad = null;

function CORE_PROGCOMM0003Q_saveSuccess(data) {
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	dojo.byId('${uploadOidField}').value = '';
	if ('Y' != data.success) {						
		${callJsErrFunction};
	} else {
		dojo.byId('${uploadOidField}').value = data.uploadOid;
		${callJsFunction};
	}
}

function CORE_PROGCOMM0003Q_clear() {
	if (CORE_PROGCOMM0003Q_signaturePad==null) {
		alert('error SignaturePad not initialized!');
		return;
	}
	CORE_PROGCOMM0003Q_signaturePad.clear();
}

function CORE_PROGCOMM0003Q_getData() {	
	return viewPage.getStrToHex( CORE_PROGCOMM0003Q_signaturePad.toDataURL() );
}

function CORE_PROGCOMM0003Q_preview() {
	var uploadOid = dojo.byId('${uploadOidField}').value;
	if (null == uploadOid || 'null' == uploadOid || '' == uploadOid) {
		alertDialog(_getApplicationProgramNameById('${programId}'), 'No before save signature!', function(){}, 'Y');	
		return;
	}
	openCommonLoadUpload( 'view', uploadOid, { 'title' : 'Signature preview', 'width' : 480, 'height' : 220 } );	
}

function ${programId}_page_message() {
	var pageMessage='<s:property value="pageMessage" escapeJavaScript="true"/>';
	if (null!=pageMessage && ''!=pageMessage && ' '!=pageMessage) {
		alert(pageMessage);
		CORE_PROGCOMM0003Q_DlgHide();
	}		
}

</script>

</head>
<body class="claro" bgcolor="#ffffff">

<!-- CORE_PROGCOMM0003Q -->
<table border="0">
	<tr>
		<td align="center" bgcolor="#d7e3ed" >
			Signature you name<font size='2'>(use mouse click left not release, drawing name)</font>
		</td>
	</tr>
	<tr>
		<td align="center" bgcolor="#ffffff" >
			<canvas id="CORE_PROGCOMM0003Q_signature_canvas" height="200" width="460" style="border:1px solid #000000;" ></canvas>
		</td>
	</tr>	
	<tr>
		<td align="center" >
		
    			<gs:button name="CORE_PROGCOMM0003Q_save" id="CORE_PROGCOMM0003Q_save" onClick="CORE_PROGCOMM0003Q_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.signatureSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.system'			:	'${system}',
    						'fields.signatureData'	:	CORE_PROGCOMM0003Q_getData()
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROGCOMM0003Q_saveSuccess(data);" 
    				programId="${programId}"
    				label="Save" 
    				iconClass="dijitIconSave"></gs:button> 
    				   			
    			<gs:button name="CORE_PROGCOMM0003Q_clear" id="CORE_PROGCOMM0003Q_clear" onClick="CORE_PROGCOMM0003Q_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"></gs:button>    	
    				
    			<gs:button name="CORE_PROGCOMM0003Q_preview" id="CORE_PROGCOMM0003Q_preview" onClick="CORE_PROGCOMM0003Q_preview();" 
    				label="Preview" 
    				iconClass="dijitIconSearch"></gs:button>   
    				    								
		</td>
	</tr>	
</table>
<BR/>
<BR/>
<BR/>
<script>
CORE_PROGCOMM0003Q_canvas = document.getElementById("CORE_PROGCOMM0003Q_signature_canvas");
CORE_PROGCOMM0003Q_signaturePad = new SignaturePad(CORE_PROGCOMM0003Q_canvas);
</script>

<script type="text/javascript">${programId}_page_message();</script>	
</body>
</html>
