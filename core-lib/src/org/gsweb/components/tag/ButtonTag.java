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
package org.gsweb.components.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.gsweb.components.base.GSTag;
import org.gsweb.components.ui.Button;

import com.netsteadfast.greenstep.base.model.YesNo;

public class ButtonTag implements GSTag {
	private PageContext pageContext=null;
	private Tag parent=null;
	private String id="";
	private String name="";
	private String label="";
	private String showLabel=YesNo.YES;
	private String onClick="";
	private String iconClass="";
	private String cssClass = "";
	private String xhrUrl="";
	private String xhrParameter=""; // 如 { name : dojo("name").value }  或是 form 的 ID
	private String sync=YesNo.YES;
	private String handleAs="json";
	private String timeout=String.valueOf(TIMEOUT); 
	private String preventCache=YesNo.YES;		
	private String loadFn = "";
	private String errorFn = "";
	private String parameterType = "postData";  // form 或 postData
	private String programId = "Message"; // 提供給 alertDialog 呼叫 _getApplicationProgramNameById(progId) 取程式名稱用
	private String confirmDialogMode = "";
	private String confirmDialogTitle = "";
	private String confirmDialogMsg = "";
	
	private Button handler() {
		Button button = new Button();
		button.setId(id);
		button.setName(name);
		button.setLabel(label);
		button.setShowLabel( (YesNo.YES.equals(this.showLabel) ? true : false) );
		button.setOnClick(onClick);
		button.setIconClass(iconClass);
		button.setCssClass(cssClass);
		button.setXhrUrl(xhrUrl);
		button.setXhrParameter(xhrParameter);
		button.setSync( (YesNo.YES.equals(this.sync) ? true : false) );
		button.setHandleAs(handleAs);
		button.setTimeout( Long.parseLong(timeout) );
		button.setPreventCache( (YesNo.YES.equals(this.preventCache) ? true : false) );
		button.setParameterType(this.parameterType);
		button.setLoadFn(loadFn);
		button.setErrorFn(errorFn);
		button.setProgramId(this.programId);
		button.setConfirmDialogMode(this.confirmDialogMode);
		button.setConfirmDialogTitle(this.confirmDialogTitle);
		button.setConfirmDialogMsg(this.confirmDialogMsg);
		return button;
	}
	
	@Override
	public int doEndTag() throws JspException {		
		Button button = this.handler();
		try {
			this.pageContext.getOut().write(button.getHtml());
			this.pageContext.getOut().write(button.getScript());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		button = null;
		return 0;
	}

	@Override
	public int doStartTag() throws JspException {
		return 0;
	}

	@Override
	public Tag getParent() {
		return this.parent;
	}

	@Override
	public void release() {
		
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	@Override
	public void setParent(Tag parent) {
		this.parent = parent;		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getShowLabel() {
		return showLabel;
	}

	public void setShowLabel(String showLabel) {
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

	public String getSync() {
		return sync;
	}

	public void setSync(String sync) {
		this.sync = sync;
	}

	public String getHandleAs() {
		return handleAs;
	}

	public void setHandleAs(String handleAs) {
		this.handleAs = handleAs;
	}
	
	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getPreventCache() {
		return preventCache;
	}

	public void setPreventCache(String preventCache) {
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

	public PageContext getPageContext() {
		return pageContext;
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
