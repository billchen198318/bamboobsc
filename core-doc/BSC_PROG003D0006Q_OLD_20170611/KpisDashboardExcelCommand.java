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
package com.netsteadfast.greenstep.bsc.command;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;

public class KpisDashboardExcelCommand extends BaseChainCommandSupport implements Command {
	
	public KpisDashboardExcelCommand() {
		super();
	}

	@Override
	public boolean execute(Context context) throws Exception {
		String uploadOid = this.createExcel(context);
		this.setResult(context, uploadOid);		
		return false;
	}
	
	private String createExcel(Context context) throws Exception {
		String fileName = SimpleUtils.getUUIDStr() + ".xlsx";
		String fileFullPath = Constants.getWorkTmpDir() + "/" + fileName;	
		XSSFWorkbook wb = new XSSFWorkbook();				
		XSSFSheet sh = wb.createSheet();
		
		int row = this.putTables(wb, sh, context);
		row = this.putCharts(wb, sh, context, row);
		
        FileOutputStream out = new FileOutputStream(fileFullPath);
        wb.write(out);
        out.close();
        wb = null;
        
        File file = new File(fileFullPath);
		String oid = UploadSupportUtils.create(
				Constants.getSystem(), UploadTypes.IS_TEMP, false, file, "kpis-dashboard.xlsx");
		file = null;
		return oid;
	}	
	
	@SuppressWarnings("unchecked")
	private int putTables(XSSFWorkbook wb, XSSFSheet sh, Context context) throws Exception {
		
		XSSFCellStyle cellHeadStyle = wb.createCellStyle();
		cellHeadStyle.setFillForegroundColor( new XSSFColor( SimpleUtils.getColorRGB4POIColor( "#f5f5f5" ) ) );
		cellHeadStyle.setFillPattern( FillPatternType.SOLID_FOREGROUND  );	
		cellHeadStyle.setBorderBottom(BorderStyle.THIN);
		cellHeadStyle.setBorderTop(BorderStyle.THIN);
		cellHeadStyle.setBorderRight(BorderStyle.THIN);
		cellHeadStyle.setBorderLeft(BorderStyle.THIN);		
		XSSFFont cellHeadFont = wb.createFont();
		cellHeadFont.setBold(true);		
		cellHeadStyle.setFont( cellHeadFont );
		
		sh.setColumnWidth(0, 12000);
		
		int left = 0;
		int row = 0;		
		List< Map<String, Object> > chartDatas = (List< Map<String, Object> >)context.get("chartDatas");
		for (Map<String, Object> data : chartDatas) {
			Row nowRow = sh.createRow(row);
			Map<String, Object> nodeData = (Map<String, Object>) ( (List<Object>)data.get("datas") ).get(0);
						
			if ( row == 0 ) {
				Cell cell1 = nowRow.createCell(0);
				cell1.setCellStyle(cellHeadStyle);
				cell1.setCellValue( "KPI" );				
				Cell cell2 = nowRow.createCell(1);
				cell2.setCellStyle(cellHeadStyle);
				cell2.setCellValue( "Maximum" );									
				Cell cell3 = nowRow.createCell(2);
				cell3.setCellStyle(cellHeadStyle);
				cell3.setCellValue( "Target" );	
				Cell cell4 = nowRow.createCell(3);
				cell4.setCellStyle(cellHeadStyle);
				cell4.setCellValue( "Minimum" );								
				Cell cell5 = nowRow.createCell(4);
				cell5.setCellStyle(cellHeadStyle);
				cell5.setCellValue( "Score" );	
				
				List<Map<String, Object>> dateRangeScores = (List<Map<String, Object>>)nodeData.get("dateRangeScores");
				for ( Map<String, Object> rangeScore : dateRangeScores ) {
					Cell cell = nowRow.createCell( 5+left );
					cell.setCellStyle(cellHeadStyle);
					cell.setCellValue( String.valueOf( rangeScore.get("date") ) );	
					left++;
				}
												
				row++;
			}
			
			left = 0;
			nowRow = sh.createRow(row);
			
			XSSFColor bgColor = new XSSFColor( SimpleUtils.getColorRGB4POIColor( (String)nodeData.get("bgColor") ) );
			XSSFColor fnColor = new XSSFColor( SimpleUtils.getColorRGB4POIColor( (String)nodeData.get("fontColor") ) );	
			XSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setFillForegroundColor( bgColor );
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);	
			XSSFFont cellFont = wb.createFont();
			cellFont.setBold(false);
			cellFont.setColor( fnColor );
			cellStyle.setFont(cellFont);
			cellStyle.setWrapText(true);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setBorderRight(BorderStyle.THIN);
			cellStyle.setBorderLeft(BorderStyle.THIN);
			
			Cell cell1 = nowRow.createCell(0);
			cell1.setCellValue( String.valueOf( nodeData.get("name") ) );				
			Cell cell2 = nowRow.createCell(1);
			cell2.setCellValue( String.valueOf( nodeData.get("max") ) );								
			Cell cell3 = nowRow.createCell(2);
			cell3.setCellValue( String.valueOf( nodeData.get("target") ) );				
			Cell cell4 = nowRow.createCell(3);
			cell4.setCellValue( String.valueOf( nodeData.get("min") ) );								
			Cell cell5 = nowRow.createCell(4);
			cell5.setCellValue( String.valueOf( nodeData.get("score") ) );
			cell5.setCellStyle( cellStyle );			
	
			
			List<Map<String, Object>> dateRangeScores = (List<Map<String, Object>>)nodeData.get("dateRangeScores");
			for ( Map<String, Object> rangeScore : dateRangeScores ) {
				bgColor = new XSSFColor( SimpleUtils.getColorRGB4POIColor( (String)rangeScore.get("bgColor") ) );
				fnColor = new XSSFColor( SimpleUtils.getColorRGB4POIColor( (String)rangeScore.get("fontColor") ) );				
				cellStyle = wb.createCellStyle();
				cellStyle.setFillForegroundColor( bgColor );
				cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);	
				cellFont = wb.createFont();
				cellFont.setBold(false);
				cellFont.setColor( fnColor );
				cellStyle.setFont(cellFont);
				cellStyle.setWrapText(true);
				cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyle.setAlignment(HorizontalAlignment.CENTER);
				cellStyle.setBorderBottom(BorderStyle.THIN);
				cellStyle.setBorderTop(BorderStyle.THIN);
				cellStyle.setBorderRight(BorderStyle.THIN);
				cellStyle.setBorderLeft(BorderStyle.THIN);				
				
				Cell cell = nowRow.createCell( 5+left );
				cell.setCellStyle(cellHeadStyle);
				cell.setCellValue( String.valueOf( rangeScore.get("score") ) );	
				cell.setCellStyle( cellStyle );
				
				left++;
			}			
			
			row++;
		}
		return row+1;
	}
	
	@SuppressWarnings("unchecked")
	private int putCharts(XSSFWorkbook wb, XSSFSheet sh, Context context, int row) throws Exception {
		
		String barBase64Content = SimpleUtils.getPNGBase64Content( (String)context.get("barChartsData") );
		BufferedImage barImage = SimpleUtils.decodeToImage( barBase64Content );
		ByteArrayOutputStream barBos = new ByteArrayOutputStream();
		ImageIO.write( barImage, "png", barBos );
		barBos.flush();	
		SimpleUtils.setCellPicture(wb, sh, barBos.toByteArray(), row, 0);	
		
		//int row = 28;
		row = row + 32;
		
		List< Map<String, Object> > chartDatas = (List< Map<String, Object> >)context.get("chartDatas");
		String year = (String)context.get("dateRangeLabel");
		
		
		XSSFCellStyle cellHeadStyle = wb.createCellStyle();
		cellHeadStyle.setFillForegroundColor( new XSSFColor( SimpleUtils.getColorRGB4POIColor( "#f5f5f5" ) ) );
		cellHeadStyle.setFillPattern( FillPatternType.SOLID_FOREGROUND  );				
		
		XSSFFont cellHeadFont = wb.createFont();
		cellHeadFont.setBold(true);		
		cellHeadStyle.setFont( cellHeadFont );
		
		int titleCellSize = 9;
		Row headRow = sh.createRow( row );
		for (int i=0; i<titleCellSize; i++) {
			Cell headCell = headRow.createCell( i );
			headCell.setCellStyle(cellHeadStyle);
			headCell.setCellValue( "KPIs metrics gauge ( " + year + " )" );					
		}
		sh.addMergedRegion( new CellRangeAddress(row, row, 0, titleCellSize-1) );
		
		row = row+1;
		int cellLeft = 5;
		int rowSpace = 17;
		for (Map<String, Object> data : chartDatas) {							
			Map<String, Object> nodeData = (Map<String, Object>) ( (List<Object>)data.get("datas") ).get(0); 
			String pngImageData = SimpleUtils.getPNGBase64Content( (String)nodeData.get("outerHTML") );			
			BufferedImage imageData = SimpleUtils.decodeToImage( pngImageData );
			ByteArrayOutputStream imgBos = new ByteArrayOutputStream();
			ImageIO.write( imageData, "png", imgBos );
			imgBos.flush();		
			SimpleUtils.setCellPicture(wb, sh, imgBos.toByteArray(), row, 0);
			
			XSSFColor bgColor = new XSSFColor( SimpleUtils.getColorRGB4POIColor( (String)nodeData.get("bgColor") ) );
			XSSFColor fnColor = new XSSFColor( SimpleUtils.getColorRGB4POIColor( (String)nodeData.get("fontColor") ) );			
			
			XSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setFillForegroundColor( bgColor );
			cellStyle.setFillPattern( FillPatternType.SOLID_FOREGROUND  );				
			
			XSSFFont cellFont = wb.createFont();
			cellFont.setBold(true);
			cellFont.setColor(fnColor);			
			
			cellStyle.setFont(cellFont);
			
			int perTitleCellSize = 4;
			Row nowRow = sh.createRow(row);
			for (int i=0; i<perTitleCellSize; i++) {
				Cell cell1 = nowRow.createCell(cellLeft);
				cell1.setCellStyle(cellStyle);
				cell1.setCellValue( (String)nodeData.get("name") );				
			}
			sh.addMergedRegion( new CellRangeAddress(row, row, cellLeft, cellLeft+perTitleCellSize-1) );
			
			nowRow = sh.createRow(row+1);
			Cell cell2 = nowRow.createCell(cellLeft);
			cell2.setCellValue( "Maximum: " + String.valueOf( nodeData.get("max") ) );				
			
			nowRow = sh.createRow(row+2);
			Cell cell3 = nowRow.createCell(cellLeft);
			cell3.setCellValue( "Target: " + String.valueOf( nodeData.get("target") ) );			
			
			nowRow = sh.createRow(row+3);
			Cell cell4 = nowRow.createCell(cellLeft);
			cell4.setCellValue( "Min: " + String.valueOf( nodeData.get("min") ) );				
			
			nowRow = sh.createRow(row+4);
			Cell cell5 = nowRow.createCell(cellLeft);
			cell5.setCellValue( "Score: " + String.valueOf( nodeData.get("score") ) );				
			
			row += rowSpace;			
		}
		
		return row;
	}		

}
