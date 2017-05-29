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
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialCap;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialPointer.Pointer;
import org.jfree.chart.plot.dial.DialTextAnnotation;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialRange;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.exception.AuthorityException;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.util.JFreeChartDataMapperUtils;

// copy from http://thecodesample.com/?p=1727
@ControllerAuthority(check=false)
@Controller("core.web.controller.CommonMeterChartAction")
@Scope
public class CommonMeterChartAction extends BaseSupportAction {
	private static final long serialVersionUID = 1058965576843823374L;
	private JFreeChart chart = null;
	private String oid = "";
	private String width = "";
	private String height = "";
	
	public CommonMeterChartAction() {
		super();
	}
	
	private void createChart() throws Exception {
		Map<String, Object> dataMap = JFreeChartDataMapperUtils.getChartData2Map(oid);
		if ( dataMap == null ) {
			return;
		}
		String title = String.valueOf(dataMap.get("title"));
		float value = NumberUtils.toFloat( String.valueOf(dataMap.get("value")) );
		Integer lowerBound = (Integer)dataMap.get("lowerBound");
		Integer upperBound = (Integer)dataMap.get("upperBound");
		this.width = String.valueOf( (Integer) dataMap.get("width") );
		this.height = String.valueOf( (Integer) dataMap.get("height") );
		this.fillChart(title, value, lowerBound, upperBound);		
	}
	
	/**
	 * core.commonMeterChartAction.action
	 * bsc.commonMeterChartAction.action
	 * charts.commonMeterChartAction.action
	 * bsc.mobile.commonMeterChartAction.action
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
	
	private void fillChart(String title, float value, int lowerBound, int upperBound) throws Exception {
		DefaultValueDataset dataset = new DefaultValueDataset();
		
		dataset.setValue(value);
 
		DialPlot plot = new DialPlot();
		plot.setView(0.0d, 0.0d, 1.0d, 1.0d);
		plot.setDataset(0, dataset);
		
		StandardDialFrame frame = new StandardDialFrame();
		plot.setDialFrame(frame);
		DialBackground dialBackground = new DialBackground();
		dialBackground.setGradientPaintTransformer(
				new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));
		plot.setBackground(dialBackground);
		DialTextAnnotation textAnnotation = new DialTextAnnotation( title );
		textAnnotation.setRadius(0.555555555555555555D);
		plot.addLayer(textAnnotation);
		
		DialValueIndicator valueIndicator = new DialValueIndicator(0);
		plot.addLayer(valueIndicator);
		
		StandardDialScale scale1 = new StandardDialScale();
		scale1.setLowerBound( lowerBound );
		scale1.setUpperBound( upperBound );
		scale1.setStartAngle( -140 ); // -120
		scale1.setExtent( -260D ); // -300D 
		scale1.setTickRadius(0.88D);
		scale1.setTickLabelOffset(0.14999999999999999D); 
		scale1.setTickLabelFont(new Font("", Font.TRUETYPE_FONT, 14)); 
		plot.addScale(0, scale1);
		
		StandardDialRange standarddialrange0 = new StandardDialRange( lowerBound, (upperBound*0.6), Color.red);
		standarddialrange0.setInnerRadius(0.52000000000000002D);
		standarddialrange0.setOuterRadius(0.55000000000000004D);
		plot.addLayer(standarddialrange0);
		
		StandardDialRange standarddialrange1 = new StandardDialRange( (upperBound*0.6), (upperBound*0.8), Color.orange);
		standarddialrange1.setInnerRadius(0.52000000000000002D);
		standarddialrange1.setOuterRadius(0.55000000000000004D);
		plot.addLayer(standarddialrange1);
		
		StandardDialRange standarddialrange2 = new StandardDialRange( (upperBound*0.8), upperBound, Color.green);
		standarddialrange2.setInnerRadius(0.52000000000000002D);
		standarddialrange2.setOuterRadius(0.55000000000000004D);
		plot.addLayer(standarddialrange2);
		
		Pointer pointer = new Pointer(0); 
		pointer.setFillPaint(new Color(144, 196, 246));
		plot.addPointer(pointer);
		plot.mapDatasetToScale(0, 0);
		DialCap dialcap = new DialCap();
		dialcap.setRadius(0.0700000000000001D);
		plot.setCap(dialcap);
		
		this.chart = new JFreeChart(plot);
		//this.chart.setBackgroundPaint(new Color(234, 244, 253));
		this.chart.setBackgroundPaint( Color.white );
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
