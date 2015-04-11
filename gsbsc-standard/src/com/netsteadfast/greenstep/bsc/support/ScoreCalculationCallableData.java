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
package com.netsteadfast.greenstep.bsc.support;

import com.netsteadfast.greenstep.vo.KpiVO;

public class ScoreCalculationCallableData implements java.io.Serializable {
	private static final long serialVersionUID = 8899030423482416656L;
	private boolean defaultMode = true;
	private KpiVO kpi;
	private String frequency;
	private String date1;
	private String date2;
	
	public ScoreCalculationCallableData() {
		super();
	}

	public ScoreCalculationCallableData(boolean defaultMode, KpiVO kpi,
			String frequency, String date1, String date2) {
		super();
		this.defaultMode = defaultMode;
		this.kpi = kpi;
		this.frequency = frequency;
		this.date1 = date1;
		this.date2 = date2;
	}

	public boolean isDefaultMode() {
		return defaultMode;
	}
	
	public void setDefaultMode(boolean defaultMode) {
		this.defaultMode = defaultMode;
	}
	
	public KpiVO getKpi() {
		return kpi;
	}
	
	public void setKpi(KpiVO kpi) {
		this.kpi = kpi;
	}
	
	public String getFrequency() {
		return frequency;
	}
	
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	public String getDate1() {
		return date1;
	}
	
	public void setDate1(String date1) {
		this.date1 = date1;
	}
	
	public String getDate2() {
		return date2;
	}
	
	public void setDate2(String date2) {
		this.date2 = date2;
	}

}
