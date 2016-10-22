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
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.olap4j.CellSet;
import org.olap4j.OlapConnection;
import org.olap4j.OlapException;
import org.olap4j.OlapStatement;

import com.netsteadfast.greenstep.base.Constants;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class OlapUtils {
	public static final String DEFAULT_DRIVER_NAME = "mondrian.olap4j.MondrianOlap4jDriver";
	
	/**
	 * 取出連線
	 * 
	 * @param driver
	 * @param url
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static OlapConnection getConnection(String driver, String url) throws ClassNotFoundException, 
		SQLException, Exception {
		
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(url);
		return connection.unwrap(OlapConnection.class);		
	}
	
	/**
	 * 取出連線
	 * 
	 * @param url
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static OlapConnection getConnection(String url) throws ClassNotFoundException, 
		SQLException, Exception {
		
		return getConnection(DEFAULT_DRIVER_NAME, url);
	}
	
	/**
	 * 取CellSet
	 * 
	 * @param connection
	 * @param mdx
	 * @return
	 * @throws OlapException
	 * @throws Exception
	 */
	public static CellSet getResultSet(OlapConnection connection, String mdx) throws OlapException, Exception {
		OlapStatement olapStatement = connection.createStatement();
		CellSet cellSet = olapStatement.executeOlapQuery(mdx);
		return cellSet;
	}
	
	public static void closeConnection(OlapConnection connection) throws Exception {
		if (connection==null) {
			return;
		}
		if (connection.isClosed()) {
			return;
		}
		connection.close();
	}
	
	public static void nullConnection(OlapConnection connection) throws Exception {
		closeConnection(connection);
		connection = null;
	}
	
	public static void closeResultSet(CellSet cellSet) throws Exception {
		if (cellSet==null) {
			return;
		}
		if (cellSet.isClosed()) {
			return;
		}
		cellSet.close();
	}
	
	public static void nullResultSet(CellSet cellSet) throws Exception {
		closeResultSet(cellSet);		
		cellSet = null;
	}
	
	public static void testMondrianCatalog(String schemaContent) throws Exception {
		if ( StringUtils.isBlank(schemaContent) ) {
			throw new java.lang.IllegalArgumentException("Mondrian Catalog is null.");
		}
		if ( schemaContent.indexOf("<") == -1 || schemaContent.indexOf(">") == -1 || schemaContent.length() > 100000 ) {
			throw new java.lang.IllegalArgumentException("not a Mondrian Catalog.");
		}
		XStream xStream = new XStream();
		xStream.registerConverter( new MapEntryConverter() );
		xStream.alias("Schema", java.util.Map.class);
		Object resultObj = xStream.fromXML(schemaContent);
		System.out.println( resultObj );		
	}
	
	public static File writeCatalogContentToFile(String catalogId, String catalogContent) throws IOException, Exception {
		if ( StringUtils.isBlank(catalogContent) ) {
			throw new java.lang.IllegalArgumentException("null catalog.");
		}
		String fileFullPath = Constants.getWorkTmpDir() + "/catalog-" + catalogId + ".xml";
		if ( !FSUtils.writeStr2(fileFullPath, catalogContent) ) {
			throw new Exception("error, write catalog to file failure.");
		}
		return new File(fileFullPath);
	}
	
	public static String getMondrianUrl(String traditionJdbcUrl, String jdbcDrivers, String catalogFileFullPath) throws Exception {
		String url = "jdbc:mondrian:";
		url += "Jdbc='" + traditionJdbcUrl + "';";
		url += "Catalog='file://" + catalogFileFullPath + "';";
		url += "JdbcDrivers=" + jdbcDrivers + ";";
		return url;
	}
	
	/**
	 * copy from :
	 * 
	 * http://stackoverflow.com/questions/1537207/how-to-convert-xml-to-java-util-map-and-vice-versa
	 *
	 */
    public static class MapEntryConverter implements Converter {
    	
        @SuppressWarnings("rawtypes")
		public boolean canConvert(Class clazz) {
            return AbstractMap.class.isAssignableFrom(clazz);
        }
        
        @SuppressWarnings("rawtypes")
		public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
        	
            AbstractMap map = (AbstractMap) value;
            for (Object obj : map.entrySet()) {
                Map.Entry entry = (Map.Entry) obj;
                writer.startNode(entry.getKey().toString());
                Object val = entry.getValue();
                if ( null != val ) {
                    writer.setValue(val.toString());
                }
                writer.endNode();
            }

        }
        
        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        	
            Map<String, String> map = new HashMap<String, String>();
            
            while(reader.hasMoreChildren()) {
                reader.moveDown();

                String key = reader.getNodeName(); // nodeName aka element's name
                String value = reader.getValue();
                map.put(key, value);

                reader.moveUp();
            }

            return map;
        }

    }	
    
    
//    public static void main(String args[]) throws Exception { // 產EXCEL範例
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
//    }
    
    
    /*
	public static void main(String args[]) throws Exception {
		testMondrianCatalog( FSUtils.readStr("/tmp/work/Measure.xml") );
	}   
	*/
	
//	public static void main(String args[]) throws Exception {
//		
//		String url = "jdbc:mondrian:";
//		url += "Jdbc='jdbc:mysql://localhost:3306/bbcore?user=root&password=password&useUnicode=true&characterEncoding=utf8';";
//		url += "Catalog='file://tmp/work/Measure.xml';";
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
//		renderer.setShowDimensionTitle(false);
//		renderer.setShowParentMembers(true);
//		
//		PrintWriter writer = new PrintWriter(System.out);
//		
//		renderer.render(model, new HtmlRenderCallback(writer));
//		
//		writer.flush();
//		writer.close();
//		
//		dataSource = null;
//		nullConnection(connection);
//	}
	
	
//	public static void main(String args[]) throws Exception {
//		
//		String url = "jdbc:mondrian:";
//		url += "Jdbc='jdbc:mysql://localhost:3306/bbcore?user=root&password=password&useUnicode=true&characterEncoding=utf8';";
//		url += "Catalog='file:///Measure.xml';";
//		url += "JdbcDrivers=com.mysql.jdbc.Driver;";
//		OlapConnection connection = getConnection(url);
//		
//		/**
//		 * 目前有問題
//		 */
//		String mdx = " SELECT ActualDim.Children ON 0, TargetDim.Children ON 1, DateDim.Children ON 2, KpiDim.Children ON 3 FROM [MeasureCube]";		
//		CellSet cs = getResultSet(connection, mdx);
//		
//		System.out.println("size=" + cs.getAxes().size() );
//		
//		for (int i=0; cs.getAxes()!=null && i<cs.getAxes().size(); i++) {
//			CellSetAxis csAxis = cs.getAxes().get(i);
//			//System.out.println( csAxis.getPositionCount() );
//			List<Position> positions = csAxis.getPositions();
//			for (int p=0; p<positions.size(); p++) {
//				Position position = positions.get(p);
//				
//				List<Member> members = position.getMembers();
//				
//				//System.out.println( position.getMembers() );
//				
//				for (Member member : members) {
//					System.out.println( member.getName() );
//				}
//				
//			}
//		}
//		
//		nullResultSet(cs);		
//		nullConnection(connection);
//		
//	}
    
}
