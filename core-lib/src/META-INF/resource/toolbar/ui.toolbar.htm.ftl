<div id="${id}" data-dojo-type="dijit/Toolbar" style="background: linear-gradient(to top, #F5F5F5 , #FFFFFF);">
<#if createNewEnable == "Y" >	
	<div data-dojo-type="dijit/form/Button" id="${id}.new"
		data-dojo-props="iconClass:'dijitIconFile', showLabel:false,
			onClick:function(){ 
				${createNewJsMethod}
			} 
		">${createNewName}</div>
</#if>
<#if saveEnabel == "Y" >				
	<div data-dojo-type="dijit/form/Button" id="${id}.save"
		data-dojo-props="iconClass:'dijitEditorIcon dijitEditorIconSave', showLabel:false,
			onClick:function(){ 
				${saveJsMethod}
			} 
		">${saveName}</div>
</#if>
<#if exportEnable == "Y" >
	<div data-dojo-type="dijit/form/Button" id="${id}.export"
		data-dojo-props="iconClass:'myExportIcon', showLabel:false,
		onClick:function(){ 
			${exportJsMethod}
		} 
		">${exportName}</div>			
</#if>		
<#if importEnable == "Y" >							
	<div data-dojo-type="dijit/form/Button" id="${id}.import"
		data-dojo-props="iconClass:'myImportIcon', showLabel:false,
		onClick:function(){ 
			${importJsMethod}
		} 
		">${importName}</div>			
</#if>
<#if refreshEnable == "Y" >		
	<div data-dojo-type="dijit/form/Button" id="${id}.refresh"
		data-dojo-props="iconClass:'dijitEditorIconUndo', showLabel:false,
		onClick:function(){ 
			${refreshJsMethod}
		} 
		">${refreshName}</div>		
	<div data-dojo-type="dijit/form/Button" id="${id}.fullscreen"
		data-dojo-props="iconClass:'dijitEditorIconFullScreen', showLabel:false,
		onClick:function(){ 
			
			if ( dojo.byId('${id}_ChildTab')!= null && '${experience}' == 'Y' ) {
				viewPage.toggleFullscreen( dojo.byId('${id}_ChildTab') );
				var childTab = dijit.byId('${id}_ChildTab');
				childTab.style.height = '100%';
				childTab.style.width = '100%';
				childTab.resize();
			} else {
				viewPage.toggleFullscreen( document.documentElement );
			}
			
		} 
		">${fullscreenName}</div>			
</#if>	
<#if cancelEnable == "Y" >		
	<span data-dojo-type="dijit/ToolbarSeparator"></span>
	<div data-dojo-type="dijit/form/Button" id="${id}.cancel"
		data-dojo-props="iconClass:'dijitEditorIcon dijitEditorIconCancel', showLabel:false,
		onClick:function(){ 
			${cancelJsMethod}
		} 
		">${cancelName}</div>
</#if>
</div>