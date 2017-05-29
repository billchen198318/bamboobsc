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
import java.awt.Paint;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.util.JFreeChartDataMapperUtils;

@ControllerAuthority(check=false)
@Controller("core.web.controller.CommonBarChartAction")
@Scope
public class CommonBarChartAction extends BaseSupportAction {
	private static final long serialVersionUID = 6793725011684681103L;
	private JFreeChart chart = null;
	private String oid = "";
	private String width = "";
	private String height = "";
	
	public CommonBarChartAction() {
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
		String horizontal = (String) dataMap.get("horizontal");
		this.width = String.valueOf( (Integer) dataMap.get("width") );
		this.height = String.valueOf( (Integer) dataMap.get("height") );
		this.fillChart(
				(String)dataMap.get("title"), 
				(String)dataMap.get("categoryLabel"), 
				(String)dataMap.get("valueLabel"), 
				names, 
				values, 
				colors,
				(YesNo.YES.equals(horizontal) ? true : false) );
	}
	
	private void fillChart(String title, String categoryLabel, String valueLabel, 
			List<String> names, List<Float> values, List<String> colors, boolean horizontal) throws Exception {
		DefaultCategoryDataset data=new DefaultCategoryDataset();
		for (int ix=0; ix<names.size(); ix++) {			
			data.addValue(values.get(ix), "", names.get(ix));			
		}		
        this.chart=ChartFactory.createBarChart3D(
        		title, // title
        		categoryLabel,
        		valueLabel, 
        		data, 
        		( horizontal ? PlotOrientation.HORIZONTAL : PlotOrientation.VERTICAL ),
        		false,
        		false, 
        		false);		
        CategoryPlot plot=(CategoryPlot)this.chart.getPlot();
        CategoryAxis categoryAxis=plot.getDomainAxis();   
        categoryAxis.setLabelFont(new Font("", Font.TRUETYPE_FONT, 9) );
        categoryAxis.setTickLabelFont(new Font("", Font.TRUETYPE_FONT, 9) );
        NumberAxis numberAxis=(NumberAxis)plot.getRangeAxis();
        numberAxis.setLabelFont(new Font("", Font.TRUETYPE_FONT, 9) );
        this.setPlotColor(plot, names, colors);
        this.chart.setTitle(new TextTitle(title, new Font("", Font.TRUETYPE_FONT, 9) ) );		
	}	
	
	private void setPlotColor(CategoryPlot plot, List<String> names, List<String> colors) throws Exception {
    	Paint[] paints = new Paint[colors.size()];
    	for (int ix=0; ix<names.size(); ix++) {
    		paints[ix] = Color.decode(colors.get(ix));
    	}
    	CategoryItemRenderer renderer = new CustomRenderer(paints);
    	plot.setRenderer(renderer);
	}		
	
	/**
	 * core.commonBarChartAction.action
	 * bsc.commonBarChartAction.action
	 * charts.commonBarChartAction.action
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

	/**
	 * 
	 * http://www.java2s.com/Code/Java/Chart/JFreeChartBarChartDemo3differentcolorswithinaseries.htm
	 *
	 */
	class CustomRenderer extends BarRenderer {
		private static final long serialVersionUID = -478703865753319803L;
		
		/** The colors. */
        private Paint[] colors;

        /**
         * Creates a new renderer.
         *
         * @param colors  the colors.
         */
        public CustomRenderer(final Paint[] colors) {
            this.colors = colors;
        }

        /**
         * Returns the paint for an item.  Overrides the default behaviour inherited from
         * AbstractSeriesRenderer.
         *
         * @param row  the series.
         * @param column  the category.
         *
         * @return The item color.
         */
        public Paint getItemPaint(final int row, final int column) {
            return this.colors[column % this.colors.length];
        }		
	}	

}
