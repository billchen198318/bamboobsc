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
package com.netsteadfast.greenstep.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.olap4j.OlapConnection;
import org.olap4j.OlapDataSource;
import org.pivot4j.PivotModel;
import org.pivot4j.datasource.SingleConnectionOlapDataSource;
import org.pivot4j.impl.PivotModelImpl;
import org.pivot4j.ui.html.HtmlRenderCallback;
import org.pivot4j.ui.poi.ExcelExporter;
import org.pivot4j.ui.poi.Format;
import org.pivot4j.ui.table.TableRenderer;

import com.netsteadfast.greenstep.base.Constants;

public class Pivot4JUtils {	
	public static final String PIVOT4J_HTML_CSS = "META-INF/pivot4j-html-table-css.css";
	private static final String _body_default = "<HTML><HEAD><meta http-equiv=\"Content-Type\" content=\"text/html; charset=" + Constants.BASE_ENCODING + "\"><style>${style}</style></HEAD><BODY>${body}</BODY></HTML>";
	private static String _htmlCss = "";
	
	public static String getHtmlCss() throws Exception {
		if ( !StringUtils.isBlank(_htmlCss) ) {
			return _htmlCss;
		}
		InputStream is = Pivot4JUtils.class.getClassLoader().getResource( PIVOT4J_HTML_CSS ).openStream();
		_htmlCss = IOUtils.toString(is, Constants.BASE_ENCODING);
		is.close();
		is = null;
		return _htmlCss;
	}
	
	private static PivotModel getPivotModel(OlapDataSource dataSource, String mdx) throws Exception {
		PivotModel model = new PivotModelImpl(dataSource);
		model.setMdx(mdx);
		model.initialize();
		return model;
	}
	
	private static TableRenderer getTableRenderer(boolean showDimensionTitle, boolean showParentMembers) {
		TableRenderer renderer = new TableRenderer();
		renderer.setShowDimensionTitle( showDimensionTitle );
		renderer.setShowParentMembers( showParentMembers );
		return renderer;
	}
	
	public static String rendererHtml(String mondrianUrl, String mdx, 
			boolean showDimensionTitle, boolean showParentMembers) throws Exception {
		if ( StringUtils.isBlank(mondrianUrl) || StringUtils.isBlank(mdx) ) {
			throw new java.lang.IllegalArgumentException("mondrian url and MDX cannot blank.");
		}
		String body = "";
		OlapConnection connection = OlapUtils.getConnection(mondrianUrl);
		OlapDataSource dataSource = new SingleConnectionOlapDataSource( connection );
		try {
			PivotModel model = getPivotModel(dataSource, mdx);			
			TableRenderer renderer = getTableRenderer(showDimensionTitle, showParentMembers);			
			StringWriter writer = new StringWriter();			
			renderer.render(model, new HtmlRenderCallback(writer));
			
			//writer.write(body);
			body = writer.toString();
			writer.flush();
			writer.close();			
		} catch (Exception e) {
			throw e;
		} finally {
			OlapUtils.nullConnection(connection);
			connection = null;
			dataSource = null;
		}
		return body;
	}
	
	public static String wrapRendererHtml(String content) throws Exception {
		String str = org.apache.commons.lang3.StringUtils.replaceOnce(_body_default, "${style}", getHtmlCss());
		str = org.apache.commons.lang3.StringUtils.replaceOnce(str, "${body}", content);
		return str;
	}
	
	public static byte[] exportExcelToBytes(String mondrianUrl, String mdx, 
			boolean showDimensionTitle, boolean showParentMembers) throws Exception {
		byte[] datas = null;
		File file = null;
		try {
			file = exportExcelFile(mondrianUrl, mdx, showDimensionTitle, showParentMembers);
			datas = FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
			throw e;
		} finally {
			file = null;
		}
		return datas;
	}
	
	public static File exportExcelFile(String mondrianUrl, String mdx, 
			boolean showDimensionTitle, boolean showParentMembers) throws Exception {
		if ( StringUtils.isBlank(mondrianUrl) || StringUtils.isBlank(mdx) ) {
			throw new java.lang.IllegalArgumentException("mondrian url and MDX cannot blank.");
		}
		File file = new File( Constants.getWorkTmpDir() + "/" + SimpleUtils.getUUIDStr() + ".xlsx" );
		OutputStream os = new FileOutputStream( file );
		OlapConnection connection = OlapUtils.getConnection(mondrianUrl);
		OlapDataSource dataSource = new SingleConnectionOlapDataSource( connection );
		try {
			PivotModel model = getPivotModel(dataSource, mdx);			
			TableRenderer renderer = getTableRenderer(showDimensionTitle, showParentMembers);		
			ExcelExporter exporter = new ExcelExporter( os );
			exporter.setFormat(Format.XSSF);
			renderer.render(model, exporter);
		} catch (Exception e) {
			file = null;
			throw e;
		} finally {
			if (os!=null) {
				IOUtils.closeQuietly(os);
				os = null;
			}
			OlapUtils.nullConnection(connection);
			connection = null;
			dataSource = null;			
		}
		return file;		
	}
	
//  public static void main(String args[]) throws Exception { // 產EXCEL範例
//		String url = "jdbc:mondrian:";
//		url += "Jdbc='jdbc:mysql://localhost:3306/bbcore?user=root&password=password&useUnicode=true&characterEncoding=utf8';";
//		url += "Catalog='file://Measure.xml';";
//		url += "JdbcDrivers=com.mysql.jdbc.Driver;";
//		OlapConnection connection = getConnection(url);
//		
//		OlapDataSource dataSource = new SingleConnectionOlapDataSource( connection );
//		
//		
//		PivotModel model = new PivotModelImpl(dataSource);
//		model.setMdx(" SELECT ActualDim.Children ON ROWS,  KpiDim.Children ON COLUMNS FROM [MeasureCube] ");
//		model.initialize();
//		
//		TableRenderer renderer = new TableRenderer();
//		renderer.setShowDimensionTitle(true);
//		renderer.setShowParentMembers(true);				
//		
//		OutputStream os = new FileOutputStream( new File("/tmp/test.xlsx") );
//		ExcelExporter exporter = new ExcelExporter( os );
//		exporter.setFormat(Format.XSSF);
//		
//		renderer.render(model, exporter);
//		
//		os.flush();
//		os.close();
//		os = null;    	
//  }	
	
}
