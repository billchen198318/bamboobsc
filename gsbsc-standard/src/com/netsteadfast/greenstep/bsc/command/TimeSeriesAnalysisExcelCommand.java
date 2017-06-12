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
package com.netsteadfast.greenstep.bsc.command;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.bsc.model.TimeSeriesAnalysisResult;
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.po.hbm.BbTsaMaCoefficients;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.DateRangeScoreVO;
import com.netsteadfast.greenstep.vo.TsaVO;

public class TimeSeriesAnalysisExcelCommand extends BaseChainCommandSupport implements Command {
	
	public TimeSeriesAnalysisExcelCommand() {
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
		
		this.putTables(wb, sh, context);
		
        FileOutputStream out = new FileOutputStream(fileFullPath);
        wb.write(out);
        out.close();
        wb = null;
        
        File file = new File(fileFullPath);
		String oid = UploadSupportUtils.create(
				Constants.getSystem(), UploadTypes.IS_TEMP, false, file, "forecast-analysis.xlsx");
		file = null;
		return oid;
	}
	
	private void putTables(XSSFWorkbook wb, XSSFSheet sh, Context context) throws Exception {
		
		TsaVO tsa = (TsaVO) context.get("tsa");
		@SuppressWarnings("unchecked")
		List<BbTsaMaCoefficients> coefficients = (List<BbTsaMaCoefficients>) context.get("coefficients");
		@SuppressWarnings("unchecked")
		List<TimeSeriesAnalysisResult> tsaResults = (List<TimeSeriesAnalysisResult>) context.get("tsaResults");
		
		XSSFFont cellHeadFont = wb.createFont();
		cellHeadFont.setBold(true);				
		
		XSSFCellStyle cellHeadStyle = wb.createCellStyle();
		cellHeadStyle.setFillForegroundColor( new XSSFColor( SimpleUtils.getColorRGB4POIColor( "#f5f5f5" ) ) );
		cellHeadStyle.setFillPattern( FillPatternType.SOLID_FOREGROUND );	
		cellHeadStyle.setBorderBottom( BorderStyle.THIN );
		cellHeadStyle.setBorderTop( BorderStyle.THIN );
		cellHeadStyle.setBorderRight( BorderStyle.THIN );
		cellHeadStyle.setBorderLeft( BorderStyle.THIN );		
		cellHeadStyle.setFont( cellHeadFont );
		
		XSSFCellStyle cellHeadStyleBlank = wb.createCellStyle();
		cellHeadStyleBlank.setFillForegroundColor( new XSSFColor( SimpleUtils.getColorRGB4POIColor( "#ffffff" ) ) );		
		cellHeadStyleBlank.setFont( cellHeadFont );		
		
		XSSFCellStyle cellHeadStyle2 = wb.createCellStyle();
		cellHeadStyle2.setFillForegroundColor( new XSSFColor( SimpleUtils.getColorRGB4POIColor( "#ffffff" ) ) );
		cellHeadStyle2.setFillPattern( FillPatternType.SOLID_FOREGROUND );	
		cellHeadStyle2.setBorderBottom( BorderStyle.THIN );
		cellHeadStyle2.setBorderTop( BorderStyle.THIN );
		cellHeadStyle2.setBorderRight( BorderStyle.THIN );
		cellHeadStyle2.setBorderLeft( BorderStyle.THIN );		
		
		
		sh.setColumnWidth(0, 12000);
		
		int row = 0;
		
		// ==============================================================
		Row nowRow = sh.createRow(row);
		Cell cellTitle = nowRow.createCell(0);
		cellTitle.setCellStyle( cellHeadStyleBlank );
		cellTitle.setCellValue( "Forecast analysis - " + context.get("visionName") );				
		
		row++;
		
		
		// ==============================================================
		nowRow = sh.createRow(row);
		Cell cell0a = nowRow.createCell(0);
		cell0a.setCellStyle( cellHeadStyleBlank );
		cell0a.setCellValue( 
				"Frequency: " + context.get("frequencyName") + " , Date range: " + context.get("date1") + " - " + context.get("date2") + " , " + 
				"Measure data type for: " + context.get("dataFor") + " , " + context.get("organizationName") + context.get("employeeName") );				
		
		row++;		
		
		// ==============================================================
		nowRow = sh.createRow(row);
		Cell cell0b = nowRow.createCell(0);
		cell0b.setCellStyle( cellHeadStyleBlank );
		cell0b.setCellValue( "" );				
		
		row++;		
		
		
		// ==============================================================
		nowRow = sh.createRow(row);
		Cell cell1 = nowRow.createCell(0);
		cell1.setCellStyle( cellHeadStyleBlank );
		cell1.setCellValue( "Param infornation" );				
		
		row++;
		
		
		// ==============================================================
		nowRow = sh.createRow(row);
		Cell cell2_a = nowRow.createCell(0);
		cell2_a.setCellStyle(cellHeadStyle);
		cell2_a.setCellValue( "Item" );				
		Cell cell2_b = nowRow.createCell(1);
		cell2_b.setCellStyle(cellHeadStyle);
		cell2_b.setCellValue( "Value" );		
		
		row++;
		
		
		// ==============================================================	
		nowRow = sh.createRow(row);
		Cell cell3_a = nowRow.createCell(0);
		cell3_a.setCellStyle(cellHeadStyle2);
		cell3_a.setCellValue( "Param name" );				
		Cell cell3_b = nowRow.createCell(1);
		cell3_b.setCellStyle(cellHeadStyle2);
		cell3_b.setCellValue( tsa.getName() );		
		
		row++;		
		
		
		// ==============================================================	
		nowRow = sh.createRow(row);
		Cell cell4_a = nowRow.createCell(0);
		cell4_a.setCellStyle(cellHeadStyle2);
		cell4_a.setCellValue( "Integration order" );				
		Cell cell4_b = nowRow.createCell(1);
		cell4_b.setCellStyle(cellHeadStyle2);
		cell4_b.setCellValue( tsa.getIntegrationOrder() );		
		
		row++;	
		
		
		// ==============================================================	
		nowRow = sh.createRow(row);
		Cell cell5_a = nowRow.createCell(0);
		cell5_a.setCellStyle(cellHeadStyle2);
		cell5_a.setCellValue( "Forecast next" );				
		Cell cell5_b = nowRow.createCell(1);
		cell5_b.setCellStyle(cellHeadStyle2);
		cell5_b.setCellValue( tsa.getForecastNext() );		
		
		row++;	
		
		
		// ==============================================================	
		nowRow = sh.createRow(row);
		Cell cell6_a = nowRow.createCell(0);
		cell6_a.setCellStyle(cellHeadStyle2);
		cell6_a.setCellValue( "Description" );				
		Cell cell6_b = nowRow.createCell(1);
		cell6_b.setCellStyle(cellHeadStyle2);
		cell6_b.setCellValue( StringUtils.defaultString(tsa.getDescription()).trim() );		
		
		row++;	
		
		
		// ==============================================================
		for (int i=0; coefficients != null && i<coefficients.size(); i++) {
			BbTsaMaCoefficients coefficient = coefficients.get(i);
			nowRow = sh.createRow(row);
			Cell cell7x_a = nowRow.createCell(0);
			cell7x_a.setCellStyle(cellHeadStyle2);
			cell7x_a.setCellValue( "Coefficient (" + (i+1) + ")" );				
			Cell cell7x_b = nowRow.createCell(1);
			cell7x_b.setCellStyle(cellHeadStyle2);
			cell7x_b.setCellValue( String.valueOf( coefficient.getSeqValue() ) );		
			
			row++;				
		}
		
		// ==============================================================
		
		nowRow = sh.createRow(row);
		Cell cellTitle3a = nowRow.createCell(0);
		cellTitle3a.setCellStyle( cellHeadStyleBlank );
		cellTitle3a.setCellValue( "" );	
		
		row++;
		
		// 填寫標題
		nowRow = sh.createRow(row);
		Cell cellTitle2a = nowRow.createCell(0);
		cellTitle2a.setCellStyle(cellHeadStyle);
		cellTitle2a.setCellValue( "KPIs" );			
		
		int j = 1;
		TimeSeriesAnalysisResult firstResult = tsaResults.get(0);
		for (int i=0; i<firstResult.getKpi().getDateRangeScores().size(); i++) {
			DateRangeScoreVO dateRangeScore = firstResult.getKpi().getDateRangeScores().get(i);
			Cell cellTitle2a_dateRange = nowRow.createCell( j );
			j++;
			cellTitle2a_dateRange.setCellStyle(cellHeadStyle);
			cellTitle2a_dateRange.setCellValue( dateRangeScore.getDate() );				
		}
		for (int i=0; i<firstResult.getForecastNext().size(); i++) {
			Cell cellTitle2a_dateRange = nowRow.createCell( j );
			j++;
			cellTitle2a_dateRange.setCellStyle(cellHeadStyle);
			cellTitle2a_dateRange.setCellValue( "next("+ (i+1) + ")" );				
		}
		
		row++;
		
		// 填寫 Date Range score 與 Forecast next score
		for (int i=0; i<tsaResults.size(); i++) {
			
			nowRow = sh.createRow(row);
			
			j = 0;
			TimeSeriesAnalysisResult resultModel = tsaResults.get(i);
			
			Cell cell_kpi = nowRow.createCell( j );
			cell_kpi.setCellStyle(cellHeadStyle);
			cell_kpi.setCellValue( resultModel.getKpi().getName() );
			j++;
			
			for (int n=0; n<resultModel.getKpi().getDateRangeScores().size(); n++) {
				DateRangeScoreVO dateRangeScore = resultModel.getKpi().getDateRangeScores().get(n);
				Cell cell_dateRangeScore = nowRow.createCell( j );
				cell_dateRangeScore.setCellStyle(cellHeadStyle2);
				cell_dateRangeScore.setCellValue( dateRangeScore.getScore() );
				j++;				
			}
			for (int n=0; n<resultModel.getForecastNext().size(); n++) {
				double forecastScore = resultModel.getForecastNext().get(n);
				Cell cell_forecastScore = nowRow.createCell( j );
				cell_forecastScore.setCellStyle(cellHeadStyle2);
				cell_forecastScore.setCellValue( forecastScore );
				j++;
			}
			
			row++;
		}
		
		row = row + 2; // 區格空間
		
		// 放 date range line 圖表
		String dateRangeChartPngData = (String) context.get("dateRangeChartPngData");
		if (StringUtils.isBlank(dateRangeChartPngData)) {
			return;
		}
		String imageDataStr = SimpleUtils.getPNGBase64Content( dateRangeChartPngData );
		BufferedImage image = SimpleUtils.decodeToImage( imageDataStr );
		ByteArrayOutputStream imgBos = new ByteArrayOutputStream();
		ImageIO.write( image, "png", imgBos );
		imgBos.flush();
		
		SimpleUtils.setCellPicture(wb, sh, imgBos.toByteArray(), row, 0);
		
	}

}
