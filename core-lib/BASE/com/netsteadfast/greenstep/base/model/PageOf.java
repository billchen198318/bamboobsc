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
package com.netsteadfast.greenstep.base.model;

public class PageOf implements java.io.Serializable {
	private static final long serialVersionUID = -3060749245195776228L;
	public static final int Rows[]={10, 20, 30};
	
	private String countSize="0"; // count record 頁面grid資料count的筆數
	private String showRow=Rows[0]+""; // a page show size 頁面要顯示grid row比數
	private String size="1"; // page size 總共有幾頁
	private String select="1"; // select page of 目前所在的頁面 , 下拉選到的頁數
	private String orderBy = ""; // 欄位
	private String sortType = ""; // ASC , DESC
	
	public PageOf() {
		
	}
	
	public PageOf(String select, String size, String showRow, String countSize) {
		this.select=select;
		this.size=size;
		this.showRow=showRow;
		this.countSize=countSize;
	}

	public String getCountSize() {
		return countSize;
	}

	public void setCountSize(String countSize) {
		this.countSize = countSize;
	}

	public String getShowRow() {
		return showRow;
	}

	public void setShowRow(String showRow) {
		this.showRow = showRow;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public int getIntegerValue(final String str) {
		if (str==null )
			return 0;		
		return org.apache.commons.lang3.math.NumberUtils.toInt(str);
	}
	
	public void toCalculateSize() {		
		int _size=1;
		int _showRow=this.getIntegerValue(this.getShowRow() );
		int _countSize=this.getIntegerValue(this.getCountSize() );
		if (_countSize>0 && _showRow>0 ) {
			_size=_countSize/_showRow;
			if (_countSize%_showRow>0 ) {
				_size+=1;
			}
		}
		this.setSize(_size+"");	
	}
	
	public String getHtmlSelectOptions() {
		StringBuilder out=new StringBuilder();
		String sel="";
		for (int ix=1; ix<=this.getIntegerValue(this.size); ix++ ) {
			sel="";
			if (this.select.equals(ix+"") )
				sel=" SELECTED ";
			out.append("<option value=\"").append(ix).append("\" ").append(sel).append(" >").append(ix).append("</option>");
		}
		return out.toString();
	}
	
	public String getHtmlRowShowSelectOptions() {
		StringBuilder out=new StringBuilder();
		String sel="";
		for (int ix=0; Rows!=null && ix<Rows.length; ix++ ) {
			sel="";
			if (this.showRow.equals(Rows[ix]+"") )
				sel=" SELECTED ";
			out.append("<option value=\"").append(Rows[ix]).append("\" ").append(sel).append(" >").append(Rows[ix]).append("</option>");
		}
		if (out.length()==0 ) {
			out.append("<option value=\"10\"").append(" >").append("10").append("</option>");
		}
		return out.toString();
	}
	
}
