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

public class DateRangeScoreVO implements java.io.Serializable {	
	private static final long serialVersionUID = 2035356533737529565L;
	private String date;
	private float score;
	private float target;
	private float min;
	private String fontColor;
	private String bgColor;
	private String imgIcon;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public float getTarget() {
		return target;
	}
	public void setTarget(float target) {
		this.target = target;
	}
	public float getMin() {
		return min;
	}
	public void setMin(float min) {
		this.min = min;
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
	public String getImgIcon() {
		return imgIcon;
	}
	public void setImgIcon(String imgIcon) {
		this.imgIcon = imgIcon;
	}	
	
}
