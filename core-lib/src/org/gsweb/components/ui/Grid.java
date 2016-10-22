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
package org.gsweb.components.ui;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.jsp.PageContext;

import org.gsweb.components.util.ComponentResourceUtils;
import org.gsweb.components.util.UIComponent;

import com.opensymphony.xwork2.ActionContext;

public class Grid implements UIComponent {
	private PageContext pageContext = null;
	private String id;
	private String gridFieldStructure="";
	private String clearQueryFn="";	
	private int width=100;
	private String programId = "Query"; // 提供給 alertDialog 呼叫 _getApplicationProgramNameById(progId) 取程式名稱用
	private String disableOnHeaderCellClick = "N";
	private StringBuilder script=new StringBuilder();
	private StringBuilder htmlOut=new StringBuilder();	
	
	private Map<String, Object> getParameters(String type, String language) {		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", this.id);
		if (IS_SCRIPT.equals(type)) { // javascript
			params.put("gridFieldStructure", ComponentResourceUtils.getScriptFunctionNameForGridParams(gridFieldStructure) );
			params.put("clearQueryFn", ComponentResourceUtils.getScriptFunctionNameForInCall(clearQueryFn) );
			params.put("programId", this.programId);
			params.put("disableOnHeaderCellClick", this.disableOnHeaderCellClick);
		} else { // html
			params.put("width", String.valueOf(this.width)+"%" );			
			
			// put default name
			params.put("totalName", "total");
			params.put("pageName", "page");	
			params.put("rowSizeName", "row size");
			this.setLabelNameFromProperties(params, language);			
		}
		return params;
	}
	
	private void setLabelNameFromProperties(Map<String, Object> params, String language) {
		String propFileName = "META-INF/resource/grid/ui.grid_" + language + ".properties";
		InputStream is = null;
		is = TextBox.class.getClassLoader().getResourceAsStream(propFileName);
		if (is != null) {
			Properties prop = new Properties();
			try {
				prop.load(is);
				params.put("totalName", prop.get("totalName"));
				params.put("pageName", prop.get("pageName"));	
				params.put("rowSizeName", prop.get("rowSizeName"));	
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}				
			prop.clear();
			prop = null;					
		}	
		is = null;			
	}
	
	private void generateScript() {	
		try {
			script.append( ComponentResourceUtils.generatorResource(
					Grid.class, IS_SCRIPT, "META-INF/resource/grid/ui.grid.js.ftl", this.getParameters(IS_SCRIPT, null)) );
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private void generateHtml() {
		Locale locale = ActionContext.getContext().getLocale();
		try {
			htmlOut.append( ComponentResourceUtils.generatorResource(
					Grid.class, IS_HTML, "META-INF/resource/grid/ui.grid.htm.ftl", this.getParameters(IS_HTML, locale.getLanguage())) );
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setName(String name) {
	}

	@Override
	public String getName() {
		return null;
	}

	public String getGridFieldStructure() {
		return gridFieldStructure;
	}

	public void setGridFieldStructure(String gridFieldStructure) {
		this.gridFieldStructure = gridFieldStructure;
	}

	public String getClearQueryFn() {
		return clearQueryFn;
	}

	public void setClearQueryFn(String clearQueryFn) {
		this.clearQueryFn = clearQueryFn;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public String getScript() throws Exception {
		this.generateScript();
		return this.script.toString();
	}

	@Override
	public String getHtml() throws Exception {
		this.generateHtml();
		return this.htmlOut.toString();
	}

	public PageContext getPageContext() {
		return pageContext;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getDisableOnHeaderCellClick() {
		return disableOnHeaderCellClick;
	}

	public void setDisableOnHeaderCellClick(String disableOnHeaderCellClick) {
		this.disableOnHeaderCellClick = disableOnHeaderCellClick;
	}

}
