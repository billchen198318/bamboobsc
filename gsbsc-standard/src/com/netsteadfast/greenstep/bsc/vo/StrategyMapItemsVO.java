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
package com.netsteadfast.greenstep.bsc.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 策略地圖要用的元素
 */
public class StrategyMapItemsVO implements java.io.Serializable {
	private static final long serialVersionUID = -8640098819845042966L;
	
	private List<String> div = new ArrayList<String>(); // 放map上元素  		:	<div class="w" id="phone1">PHONE INTERVIEW 1<div class="ep"></div></div>
	private List<String> css = new ArrayList<String>(); // 放map上元素的css	:	#phone1 { left:35em; top:12em; width:7em; }
	private List<String> con = new ArrayList<String>(); // 放map上元素連線JS	:	instance.connect({ source:"opened", target:"phone1" });	放 { source:"opened", target:"phone1" } 這個部份
	
	public List<String> getDiv() {
		return div;
	}
	public void setDiv(List<String> div) {
		this.div = div;
	}
	public List<String> getCss() {
		return css;
	}
	public void setCss(List<String> css) {
		this.css = css;
	}
	public List<String> getCon() {
		return con;
	}
	public void setCon(List<String> con) {
		this.con = con;
	}
	
}
