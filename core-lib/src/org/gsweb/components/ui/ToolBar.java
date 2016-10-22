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
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.jsp.PageContext;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.gsweb.components.util.ComponentResourceUtils;
import org.gsweb.components.util.UIComponent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("unchecked")
public class ToolBar implements UIComponent {
	private static final String _CONFIG = "META-INF/resource/toolbar/ui.toolbar.json";
	private static String _configDatas = " { } ";
	private static Map<String, Object> _configDataMap;
	private PageContext pageContext = null;	
	private String id="";
	private String createNewEnable="";
	private String saveEnabel="";
	private String refreshEnable="";
	private String cancelEnable="";
	private String exportEnable="";
	private String importEnable="";	
	private String createNewJsMethod="";
	private String saveJsMethod="";
	private String refreshJsMethod="";
	private String cancelJsMethod="";
	private String exportJsMethod="";
	private String importJsMethod="";	
	private StringBuilder htmlOut=new StringBuilder();	
	
	static {
		try {
			InputStream is = ToolBar.class.getClassLoader().getResource( _CONFIG ).openStream();
			_configDatas = IOUtils.toString(is, Constants.BASE_ENCODING);
			is.close();
			is = null;
			_configDataMap = (Map<String, Object>)new ObjectMapper().readValue( _configDatas, LinkedHashMap.class );
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null==_configDataMap) {
				_configDataMap = new HashMap<String, Object>();
			}			
		}
	}
	
	private Map<String, Object> getParameters(String type, String language) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", this.id);
		params.put("createNewEnable", this.createNewEnable);		
		params.put("saveEnabel", this.saveEnabel);
		params.put("refreshEnable", this.refreshEnable);		
		params.put("cancelEnable", this.cancelEnable);		
		params.put("exportEnable", this.exportEnable);		
		params.put("importEnable", this.importEnable);		
		params.put("createNewJsMethod", this.createNewJsMethod);		
		params.put("saveJsMethod", this.saveJsMethod);		
		params.put("refreshJsMethod", this.refreshJsMethod);		
		params.put("cancelJsMethod", this.cancelJsMethod);		
		params.put("exportJsMethod", this.exportJsMethod);
		params.put("importJsMethod", this.importJsMethod);
		
		// put default name
		params.put("createNewName", "New");
		params.put("saveName", "Save");	
		params.put("exportName", "Export");
		params.put("importName", "Import");
		params.put("refreshName", "Refresh");
		params.put("cancelName", "Cancel");
		params.put("fullscreenName", "FullScreen (Use only recommended when viewing data) / Exit FullScreen");
		this.setLabelNameFromProperties(params, language);		
		this.setExperienceMode(params);
		
		return params;		
	}
	
	private void setExperienceMode(Map<String, Object> params) {
		String experience = StringUtils.defaultString( (String)_configDataMap.get("experience") ).trim();
		params.put("experience", (YesNo.YES.equals(experience) ? YesNo.YES : YesNo.NO) );
	}
	
	private void setLabelNameFromProperties(Map<String, Object> params, String language) {
		String propFileName = "META-INF/resource/toolbar/ui.toolbar_" + language + ".properties";
		InputStream is = null;
		is = TextBox.class.getClassLoader().getResourceAsStream(propFileName);
		if (is != null) {
			Properties prop = new Properties();
			try {
				prop.load(is);
				params.put("createNewName", prop.get("createNewName"));
				params.put("saveName", prop.get("saveName"));	
				params.put("exportName", prop.get("exportName"));
				params.put("importName", prop.get("importName"));
				params.put("refreshName", prop.get("refreshName"));
				params.put("cancelName", prop.get("cancelName"));		
				params.put("fullscreenName", prop.get("fullscreenName"));
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
	
	private void generateHtml() {
		Locale locale = ActionContext.getContext().getLocale();
		try {
			htmlOut.append( ComponentResourceUtils.generatorResource(
					ToolBar.class, IS_HTML, "META-INF/resource/toolbar/ui.toolbar.htm.ftl", this.getParameters(IS_HTML, locale.getLanguage())) );
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

	@Override
	public String getScript() throws Exception {
		return "";
	}

	@Override
	public String getHtml() throws Exception {
		this.generateHtml();
		return this.htmlOut.toString();
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	public PageContext getPageContext() {
		return pageContext;
	}

	public String getCreateNewEnable() {
		return createNewEnable;
	}

	public void setCreateNewEnable(String createNewEnable) {
		this.createNewEnable = createNewEnable;
	}

	public String getSaveEnabel() {
		return saveEnabel;
	}

	public void setSaveEnabel(String saveEnabel) {
		this.saveEnabel = saveEnabel;
	}

	public String getRefreshEnable() {
		return refreshEnable;
	}

	public void setRefreshEnable(String refreshEnable) {
		this.refreshEnable = refreshEnable;
	}

	public String getCancelEnable() {
		return cancelEnable;
	}

	public void setCancelEnable(String cancelEnable) {
		this.cancelEnable = cancelEnable;
	}

	public String getExportEnable() {
		return exportEnable;
	}

	public void setExportEnable(String exportEnable) {
		this.exportEnable = exportEnable;
	}

	public String getImportEnable() {
		return importEnable;
	}

	public void setImportEnable(String importEnable) {
		this.importEnable = importEnable;
	}

	public String getCreateNewJsMethod() {
		return createNewJsMethod;
	}

	public void setCreateNewJsMethod(String createNewJsMethod) {
		this.createNewJsMethod = createNewJsMethod;
	}

	public String getSaveJsMethod() {
		return saveJsMethod;
	}

	public void setSaveJsMethod(String saveJsMethod) {
		this.saveJsMethod = saveJsMethod;
	}

	public String getRefreshJsMethod() {
		return refreshJsMethod;
	}

	public void setRefreshJsMethod(String refreshJsMethod) {
		this.refreshJsMethod = refreshJsMethod;
	}

	public String getCancelJsMethod() {
		return cancelJsMethod;
	}

	public void setCancelJsMethod(String cancelJsMethod) {
		this.cancelJsMethod = cancelJsMethod;
	}

	public String getExportJsMethod() {
		return exportJsMethod;
	}

	public void setExportJsMethod(String exportJsMethod) {
		this.exportJsMethod = exportJsMethod;
	}

	public String getImportJsMethod() {
		return importJsMethod;
	}

	public void setImportJsMethod(String importJsMethod) {
		this.importJsMethod = importJsMethod;
	}		

}
