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

import org.apache.commons.lang3.StringUtils;
import org.gsweb.components.base.GSTag;
import org.gsweb.components.ui.Grid;

public class GridTag implements GSTag {
	private PageContext pageContext=null;
	private Tag parent=null;
	private String id="";
	private String gridFieldStructure="";
	private String clearQueryFn="";	
	private String width="100"; // "100%"
	private String programId = "Query"; // 提供給 alertDialog 呼叫 _getApplicationProgramNameById(progId) 取程式名稱用
	private String disableOnHeaderCellClick = "N";
	
	private Grid handler() {
		this.width = this.width.toLowerCase();
		if (this.width.endsWith("px")) {
			this.width = StringUtils.replaceOnce(this.width, "px", "");
		}		
		Grid grid = new Grid();
		grid.setId(this.id);
		grid.setGridFieldStructure(this.gridFieldStructure);
		grid.setClearQueryFn(this.clearQueryFn);
		grid.setWidth(Integer.parseInt(this.width));
		grid.setProgramId(this.programId);
		grid.setDisableOnHeaderCellClick(this.disableOnHeaderCellClick);
		return grid;
	}
	
	@Override
	public int doEndTag() throws JspException {
		Grid grid = this.handler();
		try {
			this.pageContext.getOut().write(grid.getHtml());
			this.pageContext.getOut().write(grid.getScript());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		grid = null;
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

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
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
