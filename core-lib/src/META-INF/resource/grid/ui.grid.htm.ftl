<table id="gridQuery_table${id}" width="${width}" border="0" cellspacing="0" cellpadding="1" style="display:none; border:1px #ebeadb solid; background: linear-gradient(to top, #f1eee5 , #fafafa);">
  <tr>
  	<td width="20px" style="background: linear-gradient(to top, #f1eee5 , #fafafa);" align="center" >
  		<a href="javascript:doGridQueryShowPreviewHtml${id}();"><img src="./icons/view-list-icons.png" border="0" alt="G" title="HTML content"/></a>
  	</td>
  	<td width="100px" style="background: linear-gradient(to top, #f1eee5 , #fafafa);" align="center" >
  		<font size="2">${totalName}:&nbsp;<span id="gridQuery_pageOf_rowCount${id}"/></font>  	
  	</td>
  	<td width="10px" style="background: linear-gradient(to top, #f1eee5 , #fafafa);" align="center">&nbsp;</td>        
    <td width="20px" style="background: linear-gradient(to top, #f1eee5 , #fafafa);" align="right"><a href="javascript:changeGridQueryToFirst${id}();"><img src="./icons/stock_first.png" border="0" alt="F" title="First page"/></a></td>
    <td width="20px" style="background: linear-gradient(to top, #f1eee5 , #fafafa);" align="right"><a href="javascript:changeGridQueryToPrev${id}();"><img src="./icons/stock_left.png" border="0" alt="P" title="Previous"/></a></td>
    <td width="200px" style="background: linear-gradient(to top, #f1eee5 , #fafafa);" align="center">
    <!-- pageOf.size -->
    <input type="hidden" name="gridQuery_pageOf_size${id}" id="gridQuery_pageOf_size${id}" value="1"/>
    	<font size="2">${pageName}</font>
    	<!-- 
    	<select name="pageOf.select" id="gridQuery_pageOf_select${id}" 
    			data-dojo-id="gridQuery_pageOf_select${id}" data-dojo-type="dijit.form.Select" data-dojo-props='style:"width: 75px;" ' 
    			onChange="changeGridQueryPageOfSelect${id}();" >
    			<option value="1">1</option>
    	</select>    
    	-->
    	<!-- pageOf.select -->
		<input id="gridQuery_pageOf_select${id}" name="gridQuery_pageOf_select${id}" maxlength="6" type="text" 
			data-dojo-type="dijit/form/TextBox" 
			data-dojo-props='style:"width: 60px;"' value="1" style="width: 60px;" onChange="changeGridQueryPageOfSelect${id}();"></input>	
			
    	&nbsp;/&nbsp;<span id="gridQuery_pageOf_size_show${id}"/>
    </td>    
    <td width="20px" style="background: linear-gradient(to top, #f1eee5 , #fafafa);" align="left"><a href="javascript:changeGridQueryToNext${id}();"><img src="./icons/stock_right.png" border="0" alt="N" title="Next"/></a></td>
    <td width="20px" style="background: linear-gradient(to top, #f1eee5 , #fafafa);" align="left"><a href="javascript:changeGridQueryToLast${id}();"><img src="./icons/stock_last.png" border="0" alt="L" title="Last page"/></a></td>
    <td width="10px" style="background: linear-gradient(to top, #f1eee5 , #fafafa);" align="center">&nbsp;</td>
    <td width="200px" style="background: linear-gradient(to top, #f1eee5 , #fafafa);" align="center">
    	<font size="2">${rowSizeName}</font>
    	<!-- pageOf.showRow -->
    	<select name="gridQuery_pageOf_showRow${id}" id="gridQuery_pageOf_showRow${id}" 
    			data-dojo-id="gridQuery_pageOf_showRow${id}" data-dojo-type="dijit.form.Select" data-dojo-props='style:"width: 75px;" ' 
    			onChange="changeGridQueryPageOfShowRow${id}();" >
    			<option value="10">10</option>
    			<option value="20">20</option>
    			<option value="30">30</option>
    			<option value="50">50</option>
    			<option value="75">75</option>
    			<option value="100">100</option>
    	</select> 
		    	    	
    </td>    
    <td style="background: linear-gradient(to top, #f1eee5 , #fafafa);">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
  </tr>
</table> 

<div id="${id}"></div>
