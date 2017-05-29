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
package com.netsteadfast.greenstep.action;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.util.JFreeChartDataMapperUtils;

@ControllerAuthority(check=false)
@Controller("core.web.controller.CommonPieChartAction")
@Scope
public class CommonPieChartAction extends BaseSupportAction {
	private static final long serialVersionUID = 8032407128517025737L;
	private JFreeChart chart = null;
	private String oid = "";
	private String width = "";
	private String height = "";
	
	public CommonPieChartAction() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	private void createChart() throws Exception {
		Map<String, Object> dataMap = JFreeChartDataMapperUtils.getChartData2Map(oid);
		if ( dataMap == null ) {
			return;
		}
		List<String> names = (List<String>) dataMap.get("names");
		List<Float> values = (List<Float>) dataMap.get("values");
		List<String> colors = (List<String>) dataMap.get("colors");
		this.width = String.valueOf( (Integer) dataMap.get("width") );
		this.height = String.valueOf( (Integer) dataMap.get("height") );
		this.fillChart(
				(String)dataMap.get("title"), 
				names, 
				colors, 
				values);	
	}
	
	private void fillChart(String title, List<String> names, 
			List<String> colors, List<Float> values) throws Exception {
		DefaultPieDataset data=new DefaultPieDataset();
		for (int ix=0; ix<names.size(); ix++) {
			data.setValue( names.get(ix), values.get(ix) );
		}
        this.chart=ChartFactory.createPieChart3D(
        		title, 
        		data, 
        		true,
        		true, 
        		false);
        LegendTitle legend=this.chart.getLegend();
        legend.setItemFont(new Font("", Font.TRUETYPE_FONT, 9) );
        PiePlot plot=(PiePlot)this.chart.getPlot();
        plot.setCircular(true);
        plot.setBackgroundAlpha(0.9f);       
        plot.setForegroundAlpha(0.5f);
        plot.setLabelFont(new Font("", Font.TRUETYPE_FONT, 9) );
        this.setPlotColor( plot, names, colors );
        this.chart.setTitle(new TextTitle(title, new Font("", Font.TRUETYPE_FONT, 9) ) ); 		
	}	
	
	private void setPlotColor(PiePlot plot, List<String> names, List<String> colors) throws Exception {
		for (int ix=0; ix<names.size(); ix++) {
			plot.setSectionPaint( names.get(ix), Color.decode(colors.get(ix)) );
		}
	}	
	
	/**
	 * core.commonPieChartAction.action
	 * bsc.commonPieChartAction.action
	 * charts.commonPieChartAction.action
	 * 
	 */
	public String execute() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.createChart();
			forward = SUCCESS;
		} catch (AuthorityException | ControllerException | ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			this.exceptionPage(e);
		}
		return forward;	
	}	
	
	public JFreeChart getChart() {
		return chart;
	}
	
	public String getOid() {
		return oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
	
}
