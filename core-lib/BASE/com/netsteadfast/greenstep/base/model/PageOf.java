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

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.netsteadfast.greenstep.base.Constants;

public class PageOf implements java.io.Serializable {
	private static final long serialVersionUID = -3060749245195776228L;
	public static final int Rows[]={10, 20, 30, 50, 75, 100}; // 要配合 ui.grid.htm.flt
	
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
		boolean c = false;
		int r = NumberUtils.toInt(this.showRow, 0);
		for (int i=0; !c && i<Rows.length; i++) { // 在 bambooCORE 中只允許這幾個Row數
			if (r == Rows[i]) {
				c = true;
			}
		}
		return ( c ? showRow : String.valueOf(Rows[0]) );
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
		if (NumberUtils.toInt(this.select, 0) < 1 || NumberUtils.toInt(this.select, 0) > 1000000) { // 在 bambooCORE 中不允許
			return "1";
		}
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public String getOrderBy() {
		if (null == orderBy) {
			orderBy = "";
		}
		orderBy = orderBy.replaceAll(" ", "").replaceAll("\r", "").replaceAll("\t", "")
				.replaceAll("\n", "").replaceAll(";", "").replaceAll("\"", "")
				.replaceAll("'", "").replaceAll("-", "");
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSortType() {
		if (!StringUtils.isBlank(sortType)) {
			sortType = sortType.toUpperCase().trim();
			if (!"ASC".equals(sortType) && !"DESC".equals(sortType)) {
				sortType = "ASC";
			}
		}
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
		if ( NumberUtils.toInt(this.select, 0) > _size ) { // 2017-06-30 bug fix add
			this.select = _size + "";
		}
	}
	
	/**
	 * 查詢 grid 頁面沒有再用
	 * @return
	 */
	@Deprecated
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
	
	/**
	 * 查詢 grid 頁面沒有再用
	 * @return
	 */
	@Deprecated
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
	
	/**
	 * 把要代入查grid的 hql 的 map 參數加上 order by 條件
	 * orderBy		如 kpi.id, kpi.name
	 * sortType		如 ASC 或 DESC
	 * @param queryParam
	 */
	public Map<String, Object> setQueryOrderSortParameter(Map<String, Object> queryParam) {
		if (queryParam == null) {
			return queryParam;
		}
		if (queryParam.get(Constants._RESERVED_PARAM_NAME_QUERY_ORDER_BY) == null) {
			if (!StringUtils.isBlank(this.getOrderBy())) {
				queryParam.put(Constants._RESERVED_PARAM_NAME_QUERY_ORDER_BY, this.getOrderBy());
			}
		}
		if (queryParam.get(Constants._RESERVED_PARAM_NAME_QUERY_SORT_TYPE) == null) {
			if (!StringUtils.isBlank(this.getSortType())) {
				queryParam.put(Constants._RESERVED_PARAM_NAME_QUERY_SORT_TYPE, this.getSortType());
			}
		}
		return queryParam;
	}
	
}
