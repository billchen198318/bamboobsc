<%@page import="com.netsteadfast.greenstep.base.Constants"%>
<%@page import="com.netsteadfast.greenstep.util.ApplicationSiteUtils"%>
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

#BSC_PROG001D0002Q_tree {
    /*height need calculate*/ 
    padding-left: 4px;
    padding-right: 4px;
    overflow-y: auto; 
    overflow-x: hidden;
    font-size: 12px;
}

</style>

<script type="text/javascript">

var BSC_PROG001D0002Q_fieldsId = new Object();
BSC_PROG001D0002Q_fieldsId['orgId'] 	= 'BSC_PROG001D0002Q_orgId';
BSC_PROG001D0002Q_fieldsId['name'] 		= 'BSC_PROG001D0002Q_name';
BSC_PROG001D0002Q_fieldsId['address'] 	= 'BSC_PROG001D0002Q_address';

function BSC_PROG001D0002Q_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(BSC_PROG001D0002Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG001D0002Q_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, BSC_PROG001D0002Q_fieldsId);		
		setFieldsNoticeMessageLabel(data.fieldsId, data.fieldsMessage, BSC_PROG001D0002Q_fieldsId);
		return;
	}	
	BSC_PROG001D0002Q_clear(true);
}

function BSC_PROG001D0002Q_clear(reloadTree) {
	setFieldsBackgroundDefault(BSC_PROG001D0002Q_fieldsId);
	setFieldsNoticeMessageLabelDefault(BSC_PROG001D0002Q_fieldsId);
	dijit.byId('BSC_PROG001D0002Q_orgId').set("value", "");
	dijit.byId("BSC_PROG001D0002Q_orgId").setAttribute('readOnly', false);
	dijit.byId('BSC_PROG001D0002Q_name').set("value", "");
	dijit.byId('BSC_PROG001D0002Q_address').set("value", "");
	dijit.byId('BSC_PROG001D0002Q_description').set("value", "");
	dojo.byId('BSC_PROG001D0002Q_oid').value = "";
	dojo.byId('BSC_PROG001D0002Q_lat').value = "${googleMapDefaultLat}";
	dojo.byId('BSC_PROG001D0002Q_lng').value = "${googleMapDefaultLng}";	
	var latlng = new google.maps.LatLng(${googleMapDefaultLat}, ${googleMapDefaultLng});
	${programId}_marker.setPosition(latlng);
    ${programId}_map.setCenter( ${programId}_marker.getPosition() );
    if (reloadTree) {
    	BSC_PROG001D0002Q_reOrganizationTree();
    }    
}


/* 產生部門TREE */
function BSC_PROG001D0002Q_getOrganizationTree() {
	
	var store = new dojo.data.ItemFileWriteStore({
		url: "${basePath}/bsc.getOrganizationTreeJson.action"
	});

	var treeModel = new dijit.tree.ForestStoreModel({
		store: store,
		query: {"type": "parent"},
		rootId: "root",
		rootLabel: "<s:property value="getText('BSC_PROG001D0002Q_getOrganizationTree_rootLabel')" escapeJavaScript="true"/>",
		childrenAttrs: ["children"]
	});

	var treeObject = new dijit.Tree({
		model: treeModel,
		dndController: "dijit.tree.dndSource",
		checkAcceptance: BSC_PROG001D0002Q_dragAccept,
		checkItemAcceptance: BSC_PROG001D0002Q_dropAccept,
        _createTreeNode: function(args) {
            var tnode = new dijit._TreeNode(args);
            tnode.labelNode.innerHTML = args.label;
            return tnode;
        }    		
	}, "BSC_PROG001D0002Q_tree");

	treeObject.on("click", function(object){	
		BSC_PROG001D0002Q_clear(false);
		var oid=object.id;
		if (oid!=null && ''!=oid && 'root'!=oid && !(typeof oid=='undefined') ) {
			BSC_PROG001D0002Q_getOrganizationData(oid);
		}
	}, true);
	
	dojo.connect(treeModel, 'pasteItem', function(childItem, oldParentItem, newParentItem, bCopy, insertIndex) {
		var entityOid = childItem.id[0]; 
	    var parentOid = newParentItem.id[0];
	    BSC_PROG001D0002Q_changeParent(entityOid, parentOid);	    
	});	
	
	treeObject.expandAll();
	
}

/* 重新產生部門TREE */
function BSC_PROG001D0002Q_reOrganizationTree() {			
	dijit.byId("BSC_PROG001D0002Q_tree").destroy(true);	
	BSC_PROG001D0002Q_getOrganizationTree();
}

/* 移動tree的item */
function BSC_PROG001D0002Q_dragAccept(source,nodes) {	
	return true;
}

/* 下降tree的item */
function BSC_PROG001D0002Q_dropAccept(node,source) {	
	return true;
}

/* 取回要編輯的部門資料 */
function BSC_PROG001D0002Q_getOrganizationData(oid) {
	xhrSendParameter(
			'${basePath}/bsc.getOrganizationDataAction.action', 
			{ 'fields.oid' : oid }, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				if ('Y' != data.success) {
					alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);
					return;
				}
				dijit.byId('BSC_PROG001D0002Q_orgId').set("value", data.organization.orgId);
				dijit.byId("BSC_PROG001D0002Q_orgId").setAttribute('readOnly', true);
				dijit.byId('BSC_PROG001D0002Q_name').set("value", data.organization.name);
				dijit.byId('BSC_PROG001D0002Q_address').set("value", data.organization.address);
				dijit.byId('BSC_PROG001D0002Q_description').set("value", data.organization.description);
				dojo.byId('BSC_PROG001D0002Q_oid').value = oid;
				dojo.byId('BSC_PROG001D0002Q_lat').value = data.organization.lat;
				dojo.byId('BSC_PROG001D0002Q_lng').value = data.organization.lng;
				var latlng = new google.maps.LatLng(data.organization.lat, data.organization.lng);
				${programId}_marker.setPosition(latlng);
			    ${programId}_map.setCenter( ${programId}_marker.getPosition() );				
			}, 
			function(error) {
				alert(error);
			}
	);		
}

/* 更改部門的 PAR_ID */
function BSC_PROG001D0002Q_changeParent(entityOid, parentOid) {
	xhrSendParameter(
			'${basePath}/bsc.organizationUpdateParentAction.action', 
			{ 
				'fields.oid' 		: entityOid,
				'fields.parentOid'	: parentOid
			}, 
			'json', 
			_gscore_dojo_ajax_timeout,
			_gscore_dojo_ajax_sync, 
			true, 
			function(data) {
				alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){ setTimeout("BSC_PROG001D0002Q_reOrganizationTree();", 500); }, data.success);				
			}, 
			function(error) {
				alert(error);
			}
	);
}

/* 刪除部門資料 */
function BSC_PROG001D0002Q_delete() {
	if ( dojo.byId("BSC_PROG001D0002Q_oid").value==null || dojo.byId("BSC_PROG001D0002Q_oid").value=="" ) {
		alertDialog(_getApplicationProgramNameById('${programId}'), "<s:property value="getText('BSC_PROG001D0002Q_oid')" escapeJavaScript="true"/>", function(){}, 'Y');
		return;
	}
	confirmDialog(
			"${programId}_managementDialogId000", 
			_getApplicationProgramNameById('${programId}'), 
			"${action.getText('BSC_PROG001D0002Q_deleteConfirm')}", 
			function(success) {
				if (!success) {
					return;
				}	
				xhrSendParameter(
						'${basePath}/bsc.organizationDeleteAction.action', 
						{ 'fields.oid' : dojo.byId("BSC_PROG001D0002Q_oid").value }, 
						'json', 
						_gscore_dojo_ajax_timeout,
						_gscore_dojo_ajax_sync, 
						true, 
						function(data) {
							alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){ BSC_PROG001D0002Q_clear(true); }, data.success);							
						}, 
						function(error) {
							alert(error);
						}
				);	
			}, 
			(window.event ? window.event : null) 
	);	
}

//------------------------------------------------------------------------------
//map
//------------------------------------------------------------------------------
var ${programId}_map = null;
var ${programId}_marker = null;
function ${programId}_map_initialize() {
	var geocoder = new google.maps.Geocoder();
	var lat = _gscore_googleMapDefaultLat;
	var lng = _gscore_googleMapDefaultLng;
	if ('Y' == _gscore_googleMapClientLocationEnable) {
		if (google.loader.ClientLocation) {
			lat = google.loader.ClientLocation.latitude;
			lng = google.loader.ClientLocation.longitude;
		}			
	}
	
	var mapOptions = {
			zoom: 10,
			center: new google.maps.LatLng(lat, lng),
			mapTypeId: google.maps.MapTypeId.ROADMAP
	};
	
	${programId}_map = new google.maps.Map(
			document.getElementById('${programId}_map_canvas'), 
			mapOptions);
	
	var ${programId}_infowindow = new google.maps.InfoWindow({
		content: '<font color="RED"><s:property value="getText('BSC_PROG001D0002Q_mapInfoWindowContent')" escapeJavaScript="true"/></font>',
		maxWidth: 400
	});		
	
	${programId}_marker = new google.maps.Marker({
		position: ${programId}_map.getCenter(),
	    map: ${programId}_map,
	    title: 'organization location',
	    draggable: true
	});
	
	google.maps.event.addListener(${programId}_marker, 'dragend', function(event) {		
		getGoogleMapAddressName(
				event.latLng.lat(), 
				event.latLng.lng(),
				function(data){
					dijit.byId('BSC_PROG001D0002Q_address').set("value", "");
		     		if (data!=null || data.results.length>0) {
		     			if (data.results[0]!=null && data.results[0].formatted_address!=null) {
		     				dijit.byId('BSC_PROG001D0002Q_address').set("value", data.results[0].formatted_address );
		     			}		     			
		     		}					
				}
		);
		dojo.byId('BSC_PROG001D0002Q_lat').value = event.latLng.lat();
		dojo.byId('BSC_PROG001D0002Q_lng').value = event.latLng.lng();		
	});	
	
	${programId}_infowindow.open(${programId}_map, ${programId}_marker);
	
	getGoogleMapAddressName(
			'${googleMapDefaultLat}', 
			'${googleMapDefaultLng}',
			function(data){
				dijit.byId('BSC_PROG001D0002Q_address').set("value", "");
	     		if (data!=null || data.results.length>0) {
	     			if (data.results[0]!=null && data.results[0].formatted_address!=null) {
	     				dijit.byId('BSC_PROG001D0002Q_address').set("value", data.results[0].formatted_address );
	     			}
	     		}					
			}
	);	
	
}

//------------------------------------------------------------------------------
function ${programId}_page_message() {
	var pageMessage='<s:property value="pageMessage" escapeJavaScript="true"/>';
	if (null!=pageMessage && ''!=pageMessage && ' '!=pageMessage) {
		alert(pageMessage);
	}	
}
//------------------------------------------------------------------------------

</script>

</head>

<body class="flat">

	<gs:toolBar
		id="${programId}" 
		cancelEnable="Y" 
		cancelJsMethod="${programId}_TabClose();" 
		createNewEnable="N"
		createNewJsMethod=""		 
		saveEnabel="Y" 
		saveJsMethod="${programId}_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>		

<input type="hidden" name="BSC_PROG001D0002Q_oid" id="BSC_PROG001D0002Q_oid" value="" />
<input type="hidden" name="BSC_PROG001D0002Q_lat" id="BSC_PROG001D0002Q_lat" value="${googleMapDefaultLat}" />
<input type="hidden" name="BSC_PROG001D0002Q_lng" id="BSC_PROG001D0002Q_lng" value="${googleMapDefaultLng}" />	
<table border="0" width="100%" height="600px" cellpadding="1" cellspacing="0" >
	<tr>
		<td width="25%" valign="top">
			<div dojoType="dijit.layout.ContentPane" region="left" splitter="false" style="width:250px;height:1200px">
				<div data-dojo-type="dijit.TitlePane" data-dojo-props="title: '<s:property value="getText('BSC_PROG001D0002Q_architecture')" escapeJavaScript="true"/>' "   >
	    			<div id="BSC_PROG001D0002Q_tree" ></div>
	  			</div>			 							
			</div>	
		</td>
		<td width="75%" valign="top">
			<table border="0" width="100%" height="625px" cellpadding="1" cellspacing="0" >
				<tr>
		    		<td height="50px" width="100%" align="left">
		    			<gs:label text="${action.getText('BSC_PROG001D0002Q_orgId')}" id="BSC_PROG001D0002Q_orgId" requiredFlag="Y"></gs:label>
		    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0002Q_orgId"></gs:inputfieldNoticeMsgLabel>
		    			<br/>
		    			<gs:textBox name="BSC_PROG001D0002Q_orgId" id="BSC_PROG001D0002Q_orgId" value="" width="200" maxlength="10"></gs:textBox>
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0002Q_orgId'">
		    				Input Id, only allow normal characters.
						</div>  		    			
		    		</td>
		    	</tr>	
				<tr>
		    		<td height="50px" width="100%" align="left">
		    			<gs:label text="${action.getText('BSC_PROG001D0002Q_name')}" id="BSC_PROG001D0002Q_name" requiredFlag="Y"></gs:label>
		    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0002Q_name"></gs:inputfieldNoticeMsgLabel>
		    			<br/>
		    			<gs:textBox name="BSC_PROG001D0002Q_name" id="BSC_PROG001D0002Q_name" value="" width="400" maxlength="200"></gs:textBox>
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0002Q_name'">
		    				Input name.
						</div> 		    			
		    		</td>
		    	</tr>    
				<tr>
		    		<td height="325px" width="100%" align="left" >
		    			<gs:label text="${action.getText('BSC_PROG001D0002Q_location')}" id="BSC_PROG001D0002Q_location"></gs:label>
		    			&nbsp;<s:property value="getText('BSC_PROG001D0002Q_locationMemo')"/>
						<div dojoType="dijit.layout.BorderContainer" style="height:290px">
							<div dojoType="dijit.layout.ContentPane" region="center" style="overflow:hidden">
								<div id="${programId}_map_canvas" style="height:100%; width:100%"></div>
							</div>
						</div>    		
		    		</td>
		    	</tr>       
				<tr>
		    		<td height="50px" width="100%" align="left">
		    			<gs:label text="${action.getText('BSC_PROG001D0002Q_address')}" id="BSC_PROG001D0002Q_address"></gs:label>
		    			<gs:inputfieldNoticeMsgLabel id="BSC_PROG001D0002Q_address"></gs:inputfieldNoticeMsgLabel>
		    			<br/>
		    			<gs:textBox name="BSC_PROG001D0002Q_address" id="BSC_PROG001D0002Q_address" value="" width="600" maxlength="500"></gs:textBox>
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0002Q_address'">
		    				Input address.
						</div> 			    			
		    		</td>
		    	</tr>  		    			
				<tr>
		    		<td height="125px" width="100%" align="left">
		    			<gs:label text="${action.getText('BSC_PROG001D0002Q_description')}" id="BSC_PROG001D0002Q_description"></gs:label>
		    			<br/>
		    			<textarea id="BSC_PROG001D0002Q_description" name="BSC_PROG001D0002Q_description" data-dojo-type="dijit/form/Textarea" rows="4" cols="50" style="width:300px;height:90px;max-height:100px"></textarea>
						<div data-dojo-type="dijit/Tooltip" data-dojo-props="connectId:'BSC_PROG001D0002Q_description'">
		    				Input description, the maximum allowed 500 characters.
						</div> 			    				
		    		</td>
		    	</tr>   
		    	<tr>
		    		<td height="25px" width="100%" align="left">
		    			<gs:button name="BSC_PROG001D0002Q_save" id="BSC_PROG001D0002Q_save" onClick="BSC_PROG001D0002Q_save();"
		    				handleAs="json"
		    				sync="N"
		    				xhrUrl="${basePath}/bsc.organizationSaveAction.action"
		    				parameterType="postData"
		    				xhrParameter=" 
		    					{ 
		    						'fields.oid'			: dojo.byId('BSC_PROG001D0002Q_oid').value, 
		    						'fields.orgId'			: dijit.byId('BSC_PROG001D0002Q_orgId').get('value'),
		    						'fields.name'			: dijit.byId('BSC_PROG001D0002Q_name').get('value'),
		    						'fields.address'		: dijit.byId('BSC_PROG001D0002Q_address').get('value'),
		    						'fields.lat'			: dojo.byId('BSC_PROG001D0002Q_lat').value, 
		    						'fields.lng'			: dojo.byId('BSC_PROG001D0002Q_lng').value, 
		    						'fields.description'	: dijit.byId('BSC_PROG001D0002Q_description').get('value')
		    					} 
		    				"
		    				errorFn=""
		    				loadFn="BSC_PROG001D0002Q_saveSuccess(data);" 
		    				programId="${programId}"
		    				label="${action.getText('BSC_PROG001D0002Q_save')}" 
		    				iconClass="dijitIconSave"
		    				cssClass="alt-primary"></gs:button>    			 				
		    			<gs:button name="BSC_PROG001D0002Q_clear" id="BSC_PROG001D0002Q_clear" onClick="BSC_PROG001D0002Q_clear(true);" 
		    				label="${action.getText('BSC_PROG001D0002Q_clear')}" 
		    				iconClass="dijitIconClear"
		    				cssClass="alt-primary"></gs:button>
		    			<gs:button name="BSC_PROG001D0002Q_orgHierView" id="BSC_PROG001D0002Q_orgHierView" onClick="BSC_PROGCOMM0001Q_showOrgChart('organization', '');" 
		    				label="Hierarchy view" 
		    				iconClass="dijitIconSearch"
		    				cssClass="alt-primary"></gs:button>   		   		    				
		    			&nbsp;&nbsp;&nbsp;&nbsp;
		    			<gs:button name="BSC_PROG001D0002Q_delete" id="BSC_PROG001D0002Q_delete" onClick="BSC_PROG001D0002Q_delete();" 
		    				label="${action.getText('BSC_PROG001D0002Q_delete')}" 
		    				iconClass="dijitIconDelete"
		    				cssClass="alt-warning"></gs:button>       				 			
		    		</td>
		    	</tr>    	   	
		    </table>		
		</td>
	</tr>	
</table>	
				
	
<script type="text/javascript">
${programId}_page_message(); 
setTimeout("${programId}_map_initialize();", 500);
setTimeout("BSC_PROG001D0002Q_getOrganizationTree();", 500);
</script>	
</body>
</html>
