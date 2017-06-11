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
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
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
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.bsc.util.BscReportPropertyUtils;
import com.netsteadfast.greenstep.bsc.util.BscReportSupportUtils;
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.DateRangeScoreVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

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
		
		BscReportPropertyUtils.loadData();
		
		int row = this.putDateRange(wb, sh, context);
		this.putCharts(wb, sh, context, row);
		
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
	
	private int putDateRange(XSSFWorkbook wb, XSSFSheet sh, Context context) throws Exception {
		BscStructTreeObj treeObj = (BscStructTreeObj)this.getResult(context);
		VisionVO vision = treeObj.getVisions().get(0);
		
		XSSFCellStyle cellHeadStyle = wb.createCellStyle();
		cellHeadStyle.setFillForegroundColor( new XSSFColor( SimpleUtils.getColorRGB4POIColor( BscReportPropertyUtils.getBackgroundColor() ) ) );
		cellHeadStyle.setFillPattern( FillPatternType.SOLID_FOREGROUND  );	
		cellHeadStyle.setBorderBottom(BorderStyle.THIN);
		cellHeadStyle.setBorderTop(BorderStyle.THIN);
		cellHeadStyle.setBorderRight(BorderStyle.THIN);
		cellHeadStyle.setBorderLeft(BorderStyle.THIN);		
		XSSFFont cellHeadFont = wb.createFont();
		cellHeadFont.setBold(true);		
		cellHeadFont.setColor( new XSSFColor( SimpleUtils.getColorRGB4POIColor( BscReportPropertyUtils.getFontColor() ) ) ); 
		cellHeadStyle.setFont( cellHeadFont );
		
		sh.setColumnWidth(0, 12000);	
		
		int drSize = vision.getPerspectives().get(0).getObjectives().get(0).getDateRangeScores().size();
		
		int dCol = 5;
		int left = 0;
		int row = 0;	
		
		Row nowRow = sh.createRow( row );
		for (int i = 0; i<dCol+drSize; i++) {
			Cell cell1 = nowRow.createCell(i);
			cell1.setCellStyle(cellHeadStyle);
			cell1.setCellValue( vision.getTitle() );
		}
		sh.addMergedRegion( new CellRangeAddress(row, row, 0, dCol+drSize-1) );
		row++;
		
		nowRow = sh.createRow( row++ );
		Cell cell1 = nowRow.createCell(0);
		cell1.setCellStyle(cellHeadStyle);
		cell1.setCellValue( BscReportPropertyUtils.getKpiTitle() );	
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
		// date range cell
		for (DateRangeScoreVO dateRangeScore : vision.getPerspectives().get(0).getObjectives().get(0).getKpis().get(0).getDateRangeScores()) {
			Cell cell = nowRow.createCell( dCol + left );
			cell.setCellStyle(cellHeadStyle);
			cell.setCellValue( dateRangeScore.getDate() );	
			left++;			
		}
		
		// objectives and date range score
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor( new XSSFColor( SimpleUtils.getColorRGB4POIColor( "#ffffff" ) ) );
		cellStyle.setFillPattern( FillPatternType.SOLID_FOREGROUND  );	
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);		
		XSSFFont cellFont = wb.createFont();
		cellFont.setBold( false );		
		cellFont.setColor( new XSSFColor( SimpleUtils.getColorRGB4POIColor( "#000000" ) ) );
		cellStyle.setFont( cellFont );		
		for (int p = 0; p < vision.getPerspectives().size(); p++) {
			PerspectiveVO perspective = vision.getPerspectives().get(p);
			for (int o = 0; o < perspective.getObjectives().size(); o++) {
				ObjectiveVO objective = perspective.getObjectives().get(o);
				for (int k = 0; k < objective.getKpis().size(); k++) {
					left = 0;
					KpiVO kpi = objective.getKpis().get(k);
					
					nowRow = sh.createRow( row++ );
					cell1 = nowRow.createCell(0);
					cell1.setCellStyle(cellStyle);
					cell1.setCellValue( kpi.getName() );						
					cell2 = nowRow.createCell(1);
					cell2.setCellStyle(cellStyle);
					cell2.setCellValue( kpi.getMax() );						
					cell3 = nowRow.createCell(2);
					cell3.setCellStyle(cellStyle);
					cell3.setCellValue( kpi.getTarget() );									
					cell4 = nowRow.createCell(3);
					cell4.setCellStyle(cellStyle);
					cell4.setCellValue( kpi.getMin() );	
					cell5 = nowRow.createCell(4);
					
					XSSFCellStyle s_cellStyle = wb.createCellStyle();
					s_cellStyle.setFillForegroundColor( new XSSFColor( SimpleUtils.getColorRGB4POIColor( kpi.getBgColor() ) ) );
					s_cellStyle.setFillPattern( FillPatternType.SOLID_FOREGROUND  );	
					s_cellStyle.setBorderBottom(BorderStyle.THIN);
					s_cellStyle.setBorderTop(BorderStyle.THIN);
					s_cellStyle.setBorderRight(BorderStyle.THIN);
					s_cellStyle.setBorderLeft(BorderStyle.THIN);		
					XSSFFont s_cellFont = wb.createFont();
					s_cellFont.setBold( false );		
					s_cellFont.setColor( new XSSFColor( SimpleUtils.getColorRGB4POIColor( kpi.getFontColor() ) ) );
					s_cellStyle.setFont( s_cellFont );	
					
					cell5.setCellStyle(s_cellStyle);
					cell5.setCellValue( BscReportSupportUtils.parse2( kpi.getScore() ) );					
					
					for (DateRangeScoreVO dateRangeScore : kpi.getDateRangeScores()) {
						
						XSSFCellStyle drs_cellStyle = wb.createCellStyle();
						drs_cellStyle.setFillForegroundColor( new XSSFColor( SimpleUtils.getColorRGB4POIColor( dateRangeScore.getBgColor() ) ) );
						drs_cellStyle.setFillPattern( FillPatternType.SOLID_FOREGROUND  );	
						drs_cellStyle.setBorderBottom(BorderStyle.THIN);
						drs_cellStyle.setBorderTop(BorderStyle.THIN);
						drs_cellStyle.setBorderRight(BorderStyle.THIN);
						drs_cellStyle.setBorderLeft(BorderStyle.THIN);		
						XSSFFont drs_cellFont = wb.createFont();
						drs_cellFont.setBold( false );		
						drs_cellFont.setColor( new XSSFColor( SimpleUtils.getColorRGB4POIColor( dateRangeScore.getFontColor() ) ) );
						drs_cellStyle.setFont( drs_cellFont );				
						
						Cell cell = nowRow.createCell( dCol + left );
						cell.setCellStyle(drs_cellStyle);
						cell.setCellValue( BscReportSupportUtils.parse2(dateRangeScore.getScore()) );	
						left++;					
					}					
					
					
				}				
				
			}		
			
		}
		
		// foot row/cell
		String frequency = (String) context.get("frequency");
		String footContent = "";
		footContent += "Frequency : " + BscMeasureDataFrequency.getFrequencyMap(false).get(frequency) + "  ";
		footContent += "date range : ";
		if (!BscMeasureDataFrequency.FREQUENCY_WEEK.equals(frequency) && !BscMeasureDataFrequency.FREQUENCY_MONTH.equals(frequency) ) {
			footContent += context.get("startYearDate") + " ~ " + context.get("endYearDate");
		} else {
			footContent += context.get("startDate") + " ~ " + context.get("endDate");
		}
		String dataType = "";
		if (!StringUtils.isBlank( (String) context.get("organizationName") )) {
			dataType = (String) context.get("organizationName");
		}
		if (!StringUtils.isBlank( (String) context.get("employeeName") )) {
			dataType = (String) context.get("employeeName");
		}		
		if (StringUtils.isBlank(dataType)) {
			dataType = "All";
		}
		footContent += "  Measure data type : " + dataType;		
		nowRow = sh.createRow( row );
		for (int i=0; i < dCol+drSize; i++) {
			Cell cell = nowRow.createCell(0);
			cell.setCellStyle( cellHeadStyle );
			cell.setCellValue( footContent );					
		}
		sh.addMergedRegion( new CellRangeAddress(row, row, 0, dCol+drSize-1) );
		row++;
		return row;
	}
	
	@SuppressWarnings("unchecked")
	private int putCharts(XSSFWorkbook wb, XSSFSheet sh, Context context, int row) throws Exception {
		
		int chart_need_row_size = 12;
		
		// objectives gauge chart
		row = row + 1;
		int c = 0;
		Map<String, Object> gaugeDatas = (Map<String, Object>) context.get("gaugeDatas");
		List< Map<String, Object> > gaugeMapList = (List<Map<String, Object>>) gaugeDatas.get("gaugeMapList");
		for (int i=0; i<gaugeMapList.size(); i++) {
			
			// 每row 只放2個 gauge chart
			if (c>1) {
				c = 0;
			}
			if (i>0 && i % 2 == 0) {
				row += chart_need_row_size;
			}
			
			Map<String, Object> gaugeMap = gaugeMapList.get(i);
			String imageDataStr = SimpleUtils.getPNGBase64Content( (String) gaugeMap.get("data") );
			BufferedImage image = SimpleUtils.decodeToImage( imageDataStr );
			ByteArrayOutputStream imgBos = new ByteArrayOutputStream();
			ImageIO.write( image, "png", imgBos );
			imgBos.flush();
			
			SimpleUtils.setCellPicture(wb, sh, imgBos.toByteArray(), row, c++);
			
		}
		
		row = row + chart_need_row_size;
		
		// objectives date range line chart
		String dateRangeChartPngData = (String) context.get("dateRangeChartPngData");
		if (StringUtils.isBlank(dateRangeChartPngData)) {
			return row;
		}
		String imageDataStr = SimpleUtils.getPNGBase64Content( dateRangeChartPngData );
		BufferedImage image = SimpleUtils.decodeToImage( imageDataStr );
		ByteArrayOutputStream imgBos = new ByteArrayOutputStream();
		ImageIO.write( image, "png", imgBos );
		imgBos.flush();
		
		SimpleUtils.setCellPicture(wb, sh, imgBos.toByteArray(), row, 0);
		
		return row;
	}		

}
