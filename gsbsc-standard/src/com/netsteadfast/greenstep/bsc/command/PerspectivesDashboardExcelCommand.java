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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
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

public class PerspectivesDashboardExcelCommand extends BaseChainCommandSupport implements Command {
	
	public PerspectivesDashboardExcelCommand() {
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
		
		this.putCharts(wb, sh, context);
		
        FileOutputStream out = new FileOutputStream(fileFullPath);
        wb.write(out);
        out.close();
        wb = null;
        
        File file = new File(fileFullPath);
		String oid = UploadSupportUtils.create(
				Constants.getSystem(), UploadTypes.IS_TEMP, false, file, "perspectives-dashboard.xlsx");
		file = null;
		return oid;
	}
	
	@SuppressWarnings("unchecked")
	private int putCharts(XSSFWorkbook wb, XSSFSheet sh, Context context) throws Exception {
		String pieBase64Content = SimpleUtils.getPNGBase64Content( (String)context.get("pieCanvasToData") );
		String barBase64Content = SimpleUtils.getPNGBase64Content( (String)context.get("barCanvasToData") );
		BufferedImage pieImage = SimpleUtils.decodeToImage( pieBase64Content );
		BufferedImage barImage = SimpleUtils.decodeToImage( barBase64Content );
		ByteArrayOutputStream pieBos = new ByteArrayOutputStream();
		ImageIO.write( pieImage, "png", pieBos );
		pieBos.flush();
		ByteArrayOutputStream barBos = new ByteArrayOutputStream();
		ImageIO.write( barImage, "png", barBos );
		barBos.flush();		
		SimpleUtils.setCellPicture(wb, sh, pieBos.toByteArray(), 0, 0);		
		SimpleUtils.setCellPicture(wb, sh, barBos.toByteArray(), 0, 9);		
		int row = 21;
		
		List< Map<String, Object> > chartDatas = (List< Map<String, Object> >)context.get("chartDatas");
		String year = (String)context.get("year");
		
		
		XSSFCellStyle cellHeadStyle = wb.createCellStyle();
		cellHeadStyle.setFillForegroundColor( new XSSFColor( SimpleUtils.getColorRGB4POIColor( "#f5f5f5" ) ) );
		cellHeadStyle.setFillPattern( FillPatternType.SOLID_FOREGROUND  );				
		
		XSSFFont cellHeadFont = wb.createFont();
		cellHeadFont.setBold(true);
		//cellHeadFont.setColor( new XSSFColor( SimpleUtils.getColorRGB4POIColor( "#000000" ) ) );		
		cellHeadStyle.setFont( cellHeadFont );
		
		int titleRow = row - 1;
		int titleCellSize = 14;
		Row headRow = sh.createRow( titleRow );
		for (int i=0; i<titleCellSize; i++) {
			Cell headCell = headRow.createCell( i );
			headCell.setCellStyle(cellHeadStyle);
			headCell.setCellValue( "Perspectives metrics gauge ( " + year + " )" );					
		}
		sh.addMergedRegion( new CellRangeAddress(titleRow, titleRow, 0, titleCellSize-1) );
		
		int cellLeft = 10;
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
			cell2.setCellValue( "Target: " + String.valueOf( nodeData.get("target") ) );			
			
			nowRow = sh.createRow(row+2);
			Cell cell3 = nowRow.createCell(cellLeft);
			cell3.setCellValue( "Min: " + String.valueOf( nodeData.get("min") ) );				
			
			nowRow = sh.createRow(row+3);
			Cell cell4 = nowRow.createCell(cellLeft);
			cell4.setCellValue( "Score: " + String.valueOf( nodeData.get("score") ) );				
			
			row += rowSpace;			
		}
		
		return row;
	}	

}
