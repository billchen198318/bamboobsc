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

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
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
import com.netsteadfast.greenstep.bsc.model.PeriodTrendsData;
import com.netsteadfast.greenstep.bsc.util.BscReportSupportUtils;
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.KpiVO;

public class KpiPeriodTrendsExcelCommand extends BaseChainCommandSupport implements Command {
	
	public KpiPeriodTrendsExcelCommand() {
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
				Constants.getSystem(), UploadTypes.IS_TEMP, false, file, "kpis-period-trends.xlsx");
		file = null;
		return oid;
	}
	
	@SuppressWarnings("unchecked")
	private void putTables(XSSFWorkbook wb, XSSFSheet sh, Context context) throws Exception {
		
		XSSFCellStyle cellHeadStyle = wb.createCellStyle();
		cellHeadStyle.setFillForegroundColor( new XSSFColor( SimpleUtils.getColorRGB4POIColor( "#f5f5f5" ) ) );
		cellHeadStyle.setFillPattern( FillPatternType.SOLID_FOREGROUND );	
		cellHeadStyle.setBorderBottom( BorderStyle.THIN );
		cellHeadStyle.setBorderTop( BorderStyle.THIN );
		cellHeadStyle.setBorderRight( BorderStyle.THIN );
		cellHeadStyle.setBorderLeft( BorderStyle.THIN );		
		XSSFFont cellHeadFont = wb.createFont();
		cellHeadFont.setBold(true);		
		cellHeadStyle.setFont( cellHeadFont );
		
		sh.setColumnWidth(0, 12000);
		
		int row = 0;
		
		Row nowRow = sh.createRow(row);
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
		cell5.setCellValue( "Current score" );			
		Cell cell6 = nowRow.createCell(5);
		cell6.setCellStyle(cellHeadStyle);
		cell6.setCellValue( "Previous score" );	
		Cell cell7 = nowRow.createCell(6);
		cell7.setCellStyle(cellHeadStyle);
		cell7.setCellValue( "Change(%)" );	
		
		row++;
		
		List<PeriodTrendsData<KpiVO>> periodDatas = (List<PeriodTrendsData<KpiVO>>)context.get("periodDatas");
		for (PeriodTrendsData<KpiVO> periodData : periodDatas) {
			nowRow = sh.createRow(row);
	
			cell1 = nowRow.createCell(0);
			cell1.setCellValue( periodData.getCurrent().getName() );				
			cell2 = nowRow.createCell(1);
			cell2.setCellValue( periodData.getCurrent().getMax() );									
			cell3 = nowRow.createCell(2);
			cell3.setCellValue( periodData.getCurrent().getTarget() );	
			cell4 = nowRow.createCell(3);
			cell4.setCellValue( periodData.getCurrent().getMin() );								
			cell5 = nowRow.createCell(4);
			cell5.setCellValue( BscReportSupportUtils.parse2( periodData.getCurrent().getScore() ) );			
			cell6 = nowRow.createCell(5);
			cell6.setCellValue( BscReportSupportUtils.parse2( periodData.getPrevious().getScore() ) );	
			cell7 = nowRow.createCell(6);
			cell7.setCellValue( BscReportSupportUtils.parse2( periodData.getChange() ) );			
			
			row++;
		}
		
		nowRow = sh.createRow(row);
		
		cell1 = nowRow.createCell(0);
		cell1.setCellValue( "Current period: " + (String)context.get("currentPeriodDateRange") + " , Previous period: " + (String)context.get("previousPeriodDateRange") );				
		
	}

}
