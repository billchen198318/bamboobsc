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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang3.StringUtils;
import org.gsweb.components.util.ComponentResourceUtils;
import org.gsweb.components.util.UIComponent;

import com.netsteadfast.greenstep.base.model.YesNo;

public class Button implements UIComponent {
	private PageContext pageContext = null;
	private String id="";
	private String name="";
	private String label="";
	private boolean showLabel=true;
	private String onClick="";
	private String iconClass="";
	private String cssClass = "";
	private String xhrUrl="";
	private String xhrParameter=""; // 如 { name : dojo("name").value }  或是 form 的 ID
	private boolean sync=true;
	private String handleAs="json";
	private long timeout=TIMEOUT; 
	private boolean preventCache=true;		
	private String loadFn = "";
	private String errorFn = "";
	private String parameterType = "postData";  // form 或 postData
	private String programId = "Message"; // 提供給 alertDialog 呼叫 _getApplicationProgramNameById(progId) 取程式名稱用
	private String confirmDialogMode = YesNo.NO;
	private String confirmDialogTitle = "";
	private String confirmDialogMsg = "";
	private StringBuilder script=new StringBuilder();
	private StringBuilder htmlOut=new StringBuilder();	
	
	private Map<String, Object> getParameters(String type) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (IS_SCRIPT.equals(type)) { // javascript
			params.put("id", this.id);
			params.put("name", this.name);
			params.put("onClick", ComponentResourceUtils.getScriptFunctionNameForXhrMain(this.onClick) );
			params.put("xhrUrl", this.xhrUrl);
			params.put("xhrParameter", this.xhrParameter);
			params.put("handleAs", this.handleAs);
			params.put("timeout", String.valueOf(this.timeout));
			params.put("sync", String.valueOf(this.sync));
			params.put("preventCache", String.valueOf(this.preventCache));
			params.put("loadFn", ComponentResourceUtils.getScriptFunctionNameForInCall(this.loadFn) );
			params.put("errorFn", ComponentResourceUtils.getScriptFunctionNameForInCall(this.errorFn) );
			params.put("parameterType", this.parameterType);
			params.put("programId", this.programId);
			if (!StringUtils.isBlank(this.loadFn) && !this.loadFn.endsWith(";")) {
				params.put("loadFn", this.loadFn+";");
			}
			if (!StringUtils.isBlank(this.errorFn) && !this.errorFn.endsWith(";")) {
				params.put("errorFn", this.errorFn+";");
			}
			params.put("confirmDialogMode", confirmDialogMode);
			params.put("confirmDialogTitle", StringUtils.defaultString(confirmDialogTitle).trim());
			params.put("confirmDialogMsg", confirmDialogMsg);
		} else { // html
			params.put("id", this.id);
			params.put("name", this.name);
			params.put("showLabel", String.valueOf(this.showLabel));
			params.put("iconClass", this.iconClass);
			params.put("cssClass", this.cssClass);
			params.put("onClick", this.onClick );
			params.put("label", this.label);			
		}
		return params;
	}
	
	private void generateXhrScript() {		
		try {
			script.append( ComponentResourceUtils.generatorResource(
					Button.class, IS_SCRIPT, "META-INF/resource/button/ui.button.js.ftl", this.getParameters(IS_SCRIPT)) );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void generateHtml() {
		try {
			htmlOut.append( ComponentResourceUtils.generatorResource(
					Button.class, IS_HTML, "META-INF/resource/button/ui.button.htm.ftl", this.getParameters(IS_HTML)) );
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
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getScript() throws Exception {
		if (StringUtils.isBlank(this.xhrUrl)) {
			this.script.setLength(0);
		} else {
			this.generateXhrScript();
		}
		return script.toString();
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isShowLabel() {
		return showLabel;
	}

	public void setShowLabel(boolean showLabel) {
		this.showLabel = showLabel;
	}

	public String getOnClick() {
		return onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getXhrUrl() {
		return xhrUrl;
	}

	public void setXhrUrl(String xhrUrl) {
		this.xhrUrl = xhrUrl;
	}

	public String getXhrParameter() {
		return xhrParameter;
	}

	public void setXhrParameter(String xhrParameter) {
		this.xhrParameter = xhrParameter;
	}

	public boolean isSync() {
		return sync;
	}

	public void setSync(boolean sync) {
		this.sync = sync;
	}

	public String getHandleAs() {
		return handleAs;
	}

	public void setHandleAs(String handleAs) {
		this.handleAs = handleAs;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public boolean isPreventCache() {
		return preventCache;
	}

	public void setPreventCache(boolean preventCache) {
		this.preventCache = preventCache;
	}

	public String getLoadFn() {
		return loadFn;
	}

	public void setLoadFn(String loadFn) {
		this.loadFn = loadFn;
	}

	public String getErrorFn() {
		return errorFn;
	}

	public void setErrorFn(String errorFn) {
		this.errorFn = errorFn;
	}

	public StringBuilder getHtmlOut() {
		return htmlOut;
	}

	public void setHtmlOut(StringBuilder htmlOut) {
		this.htmlOut = htmlOut;
	}

	public PageContext getPageContext() {
		return pageContext;
	}

	public void setScript(StringBuilder script) {
		this.script = script;
	}

	public String getParameterType() {
		return parameterType;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getConfirmDialogMode() {
		return confirmDialogMode;
	}

	public void setConfirmDialogMode(String confirmDialogMode) {
		this.confirmDialogMode = confirmDialogMode;
	}

	public String getConfirmDialogTitle() {
		return confirmDialogTitle;
	}

	public void setConfirmDialogTitle(String confirmDialogTitle) {
		this.confirmDialogTitle = confirmDialogTitle;
	}

	public String getConfirmDialogMsg() {
		return confirmDialogMsg;
	}

	public void setConfirmDialogMsg(String confirmDialogMsg) {
		this.confirmDialogMsg = confirmDialogMsg;
	}
	
}
