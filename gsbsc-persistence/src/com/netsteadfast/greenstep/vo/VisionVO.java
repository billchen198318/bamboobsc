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

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.model.BaseValueObj;

public class VisionVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = -123372948944307162L;
	private String oid;	
	private String visId;
	private String title;
	private byte[] content;
	
	private int row; // TD要用的row
	private String fontColor; // 報表要用的
	private String bgColor; // 報表要用的
	private float score; // 報表要用的
	
	private List<PerspectiveVO> perspectives = new LinkedList<PerspectiveVO>(); // 給 StructTreeObj 用的
	
	private List<DateRangeScoreVO> dateRangeScores = new LinkedList<DateRangeScoreVO>(); // 報表要用的日期區間分數 , 2017-06-06
	
	@JsonIgnore // ObjectMapper writeValueAsString 不要輸出這個欄位, 因為造成 mobile 版報表顯示 出錯
	public String getContentString() {
		if (null == this.content) {
			return "";
		}
		try {
			return new String(this.content, Constants.BASE_ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public VisionVO() {
		
	}
	
	public VisionVO(String oid, String visId, String title, byte[] content) {
		this.oid = oid;
		this.visId = visId;
		this.title = title;
		this.content = content;
	}
	
	public VisionVO(String oid, String visId, String title) {
		this.oid = oid;
		this.visId = visId;
		this.title = title;
	}	
	
	@Override
	public String getOid() {
		return this.oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getVisId() {
		return visId;
	}

	public void setVisId(String visId) {
		this.visId = visId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public List<PerspectiveVO> getPerspectives() {
		return perspectives;
	}

	public void setPerspectives(List<PerspectiveVO> perspectives) {
		this.perspectives = perspectives;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getFontColor() {
		return fontColor;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public List<DateRangeScoreVO> getDateRangeScores() {
		return dateRangeScores;
	}

	public void setDateRangeScores(List<DateRangeScoreVO> dateRangeScores) {
		this.dateRangeScores = dateRangeScores;
	}			

}
