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

public class QueryResult<T extends Object> implements QueryResultProvide<T>, java.io.Serializable {
	private static final long serialVersionUID = 5960828181609418300L;
	private SystemMessage systemMessage;
	private long count=0;
	private T value;
	private int offset=0;
	private int limit=0;
	private String findHQL;
	private String countHQL;
	private String findSQL;
	private String countSQL;
	
	@Override
	public void setValue(T value) {
		this.value=value;
	}

	@Override
	public T getValue() {
		return this.value;
	}
	
	@Override
	public long getRowCount() {
		return this.count;
	}

	@Override
	public void setRowCount(long rowCount) {
		this.count=rowCount;
	}	

	@Override
	public SystemMessage getSystemMessage() {
		return this.systemMessage;
	}

	@Override
	public void setSystemMessage(SystemMessage systemMessage) {
		this.systemMessage=systemMessage;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getFindHQL() {
		return findHQL;
	}

	public void setFindHQL(String findHQL) {
		this.findHQL = findHQL;
	}

	public String getCountHQL() {
		return countHQL;
	}

	public void setCountHQL(String countHQL) {
		this.countHQL = countHQL;
	}

	public String getFindSQL() {
		return findSQL;
	}

	public void setFindSQL(String findSQL) {
		this.findSQL = findSQL;
	}

	public String getCountSQL() {
		return countSQL;
	}

	public void setCountSQL(String countSQL) {
		this.countSQL = countSQL;
	}
	
}
