<!-- BSC_PROG002D0008Q -->
<input type="hidden" name="BSC_PROG002D0008Q_visionOid" value="${vision.oid}" />
<input type="hidden" name="BSC_PROG002D0008Q_organizationOid" value="${organization.oid}" />
<center>
<table width="1200px" border="0" cellspacing="1" cellpadding="1" bgcolor="#d8d8d8">		
	<tr>
		<td bgcolor="#F2F2F2" align="center" width="192px">
			&nbsp;
		</td>
		<td bgcolor="#F2F2F2" align="center" width="252px">
			<b><font color="#000000" size="+1">${strengthsName}</font></b>
		</td>
		<td bgcolor="#F2F2F2" align="center" width="252px">
			<b><font color="#000000" size="+1">${weaknessesName}</font></b>
		</td>
		<td bgcolor="#F2F2F2" align="center" width="252px">
			<b><font color="#000000" size="+1">${opportunitiesName}</font></b>
		</td>
		<td bgcolor="#F2F2F2" align="center" width="252px">
			<b><font color="#000000" size="+1">${threatsName}</font></b>
		</td>								
	</tr>	
	
<#list issues as data >
	<tr>
		<td bgcolor="#F5F5F5" align="left" width="192px" height="120px" >
			<b><font color="#000000">${data.perspectiveName}</font></b>
		</td>
		<td bgcolor="#ffffff" align="left" width="252px" height="120px" >
			<div name="${data.strengthsTextId}" data-dojo-type="dijit/InlineEditBox" data-dojo-props="editor:'dijit/form/SimpleTextarea',autoSave:true,renderAsHtml:false" editorParams="{cols:'15', rows:'5', name:'${data.strengthsTextId}' }" autoSave="true" width="252px" height="120px" title="editor issue!">${data.strengths}</div>			
		</td>
		<td bgcolor="#ffffff" align="left" width="252px" height="120px" >
			<div name="${data.weaknessesTextId}" data-dojo-type="dijit/InlineEditBox" data-dojo-props="editor:'dijit/form/SimpleTextarea',autoSave:true,renderAsHtml:false" editorParams="{cols:'15', rows:'5', name:'${data.weaknessesTextId}' }" autoSave="true" width="252px" height="120px" title="editor issue!">${data.weaknesses}</div>
		</td>
		<td bgcolor="#ffffff" align="left" width="252px" height="120px" >
			<div name="${data.opportunitiesTextId}" data-dojo-type="dijit/InlineEditBox" data-dojo-props="editor:'dijit/form/SimpleTextarea',autoSave:true,renderAsHtml:false" editorParams="{cols:'15', rows:'5', name:'${data.opportunitiesTextId}' }" autoSave="true" width="252px" height="120px" title="editor issue!">${data.opportunities}</div>
		</td>
		<td bgcolor="#ffffff" align="left" width="252px" height="120px" >
			<div name="${data.threatsTextId}" data-dojo-type="dijit/InlineEditBox" data-dojo-props="editor:'dijit/form/SimpleTextarea',autoSave:true,renderAsHtml:false" editorParams="{cols:'15', rows:'5', name:'${data.threatsTextId}' }" autoSave="true" width="252px" height="120px" title="editor issue!">${data.threats}</div>
		</td>								
	</tr>		
</#list>
	
</table>		
</center>