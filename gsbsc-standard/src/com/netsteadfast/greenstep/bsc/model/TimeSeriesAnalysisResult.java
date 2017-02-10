/* 
 * Copyright 2012-2017 bambooCORE, greenstep of copyright Chen Xin Nien
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
package com.netsteadfast.greenstep.bsc.model;

import java.util.LinkedList;
import java.util.List;

import com.netsteadfast.greenstep.vo.KpiVO;

public class TimeSeriesAnalysisResult implements java.io.Serializable {
	private static final long serialVersionUID = 7291721227891892308L;
	private KpiVO kpi = null;
	private List<Double> forecastNext = new LinkedList<Double>();
	
	public TimeSeriesAnalysisResult() {
		super();
	}
	
	public TimeSeriesAnalysisResult(KpiVO kpi, double[] forecast) {
		this.kpi = kpi;
		for (int i=0; forecast != null && i < forecast.length; i++) {
			this.forecastNext.add(forecast[i]);
		}
	}	
	
	public TimeSeriesAnalysisResult(KpiVO kpi, List<Double> forecast) {
		this.kpi = kpi;
		this.forecastNext.addAll(forecast);
	}
	
	public KpiVO getKpi() {
		return kpi;
	}

	public void setKpi(KpiVO kpi) {
		this.kpi = kpi;
	}
	
	public List<Double> getForecastNext() {
		return forecastNext;
	}

	public void setForecastNext(List<Double> forecastNext) {
		this.forecastNext = forecastNext;
	}
	
}
