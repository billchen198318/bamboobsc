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
package com.netsteadfast.greenstep.vo;

import com.netsteadfast.greenstep.base.model.BaseValueObj;

public class DegreeFeedbackScoreVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = -3812922702316183876L;
	private String oid;
	private String projectOid;
	private String itemOid;
	private String assignOid;
	private int score;
	private String memo;
	
	public DegreeFeedbackScoreVO() {
		
	}		
	
	public DegreeFeedbackScoreVO(String oid, String projectOid, String itemOid,
			String assignOid, int score, String memo) {
		super();
		this.oid = oid;
		this.projectOid = projectOid;
		this.itemOid = itemOid;
		this.assignOid = assignOid;
		this.score = score;
		this.memo = memo;
	}
	
	@Override
	public String getOid() {
		return this.oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getProjectOid() {
		return projectOid;
	}

	public void setProjectOid(String projectOid) {
		this.projectOid = projectOid;
	}

	public String getItemOid() {
		return itemOid;
	}

	public void setItemOid(String itemOid) {
		this.itemOid = itemOid;
	}

	public String getAssignOid() {
		return assignOid;
	}

	public void setAssignOid(String assignOid) {
		this.assignOid = assignOid;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
