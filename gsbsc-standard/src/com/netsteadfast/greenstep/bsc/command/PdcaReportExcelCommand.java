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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.lang3.StringUtils;
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
import com.netsteadfast.greenstep.bsc.model.PdcaType;
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.PdcaAuditVO;
import com.netsteadfast.greenstep.vo.PdcaItemVO;
import com.netsteadfast.greenstep.vo.PdcaVO;
import com.netsteadfast.greenstep.vo.SysUploadVO;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class PdcaReportExcelCommand extends BaseChainCommandSupport implements Command {
	
	public PdcaReportExcelCommand() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Context context) throws Exception {
		if (this.getResult(context)==null || !(this.getResult(context) instanceof PdcaVO) ) {
			return false;
		}
		List<SysUploadVO> reportUploads = new ArrayList<SysUploadVO>();
		String pdcaXlsxFileOid = this.createExcel(context);
		String uploadOid = "";
		List<String> bscExcelFileUploadOids = (List<String>) context.get("bscExcelFileUploadOids");
		for (int i=0; bscExcelFileUploadOids != null && i < bscExcelFileUploadOids.size(); i++) {
			String bscXlsxFileOid = bscExcelFileUploadOids.get(i);
			reportUploads.add( UploadSupportUtils.findUpload(bscXlsxFileOid) );
		}
		reportUploads.add( UploadSupportUtils.findUpload(pdcaXlsxFileOid) );
		if (reportUploads.size() == 1) { // only PDCA report
			uploadOid = reportUploads.get(0).getOid();
		} else { // with BSC report, create a ZIP file.
			uploadOid = this.createZipFile(reportUploads);
		}
		this.setResult(context, uploadOid);		
		reportUploads.clear();
		reportUploads = null;
		return false;
	}
	
	private String createExcel(Context context) throws Exception {
		
		String fileName = SimpleUtils.getUUIDStr() + ".xlsx";
		String fileFullPath = Constants.getWorkTmpDir() + "/" + fileName;	
		int row = 0;
		XSSFWorkbook wb = new XSSFWorkbook();				
		XSSFSheet sh1 = wb.createSheet( "PDCA Report" );
		
		row += this.createPdca(wb, sh1, row, context);
		
        FileOutputStream out = new FileOutputStream(fileFullPath);
        wb.write(out);
        out.close();
        wb = null;
        
        File file = new File(fileFullPath);
		String oid = UploadSupportUtils.create(
				Constants.getSystem(), UploadTypes.IS_TEMP, false, file, "pdca-report.xlsx");
		file = null;
		return oid;
	}
	
	private int createPdca(XSSFWorkbook wb, XSSFSheet sh, int row, Context context) throws Exception {
		
		PdcaVO pdca = (PdcaVO) this.getResult(context);
		
		Row headRow = sh.createRow(row);
		headRow.setHeight( (short)700 );
		
		// --------------------------------------------------------------------------------------
		
		XSSFColor bgColor = new XSSFColor( SimpleUtils.getColorRGB4POIColor("#d8d8d8") );
		XSSFColor fnColor = new XSSFColor( SimpleUtils.getColorRGB4POIColor("#000000") );
		
		XSSFCellStyle cellHeadStyle = wb.createCellStyle();
		cellHeadStyle.setFillForegroundColor( bgColor );
		cellHeadStyle.setFillPattern( FillPatternType.SOLID_FOREGROUND );			
		
		XSSFFont cellHeadFont = wb.createFont();
		cellHeadFont.setBold(true);
		cellHeadFont.setColor(fnColor);
		cellHeadStyle.setFont(cellHeadFont);		
		cellHeadStyle.setBorderBottom(BorderStyle.THIN);
		cellHeadStyle.setBorderTop(BorderStyle.THIN);
		cellHeadStyle.setBorderRight(BorderStyle.THIN);
		cellHeadStyle.setBorderLeft(BorderStyle.THIN);
		cellHeadStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellHeadStyle.setAlignment(HorizontalAlignment.CENTER);
		cellHeadStyle.setWrapText(true);		
		
		// --------------------------------------------------------------------------------------
		
		int cols = 6;
		for (int i=0; i<cols; i++) {
			sh.setColumnWidth(i, 6000);
			Cell headCell1 = headRow.createCell(i);	
			headCell1.setCellValue( pdca.getTitle() );
			headCell1.setCellStyle(cellHeadStyle);			
		}		
		sh.addMergedRegion( new CellRangeAddress(row, row, 0, cols-1) );	
		
		// --------------------------------------------------------------------------------------
		
		XSSFColor bgLabelColor = new XSSFColor( SimpleUtils.getColorRGB4POIColor("#F2F2F2") );
		
		XSSFCellStyle cellLabelStyle = wb.createCellStyle();
		cellLabelStyle.setFillForegroundColor( bgLabelColor );
		cellLabelStyle.setFillPattern( FillPatternType.SOLID_FOREGROUND );				
		
		XSSFFont cellLabelFont = wb.createFont();
		cellLabelFont.setBold(true);
		cellLabelFont.setColor(fnColor);
		cellLabelStyle.setFont(cellLabelFont);		
		cellLabelStyle.setBorderBottom(BorderStyle.THIN);
		cellLabelStyle.setBorderTop(BorderStyle.THIN);
		cellLabelStyle.setBorderRight(BorderStyle.THIN);
		cellLabelStyle.setBorderLeft(BorderStyle.THIN);
		cellLabelStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellLabelStyle.setAlignment(HorizontalAlignment.LEFT);
		cellLabelStyle.setWrapText(true);		
		
		// --------------------------------------------------------------------------------------

		XSSFColor bgNormalColor = new XSSFColor( SimpleUtils.getColorRGB4POIColor("#ffffff") );
		
		XSSFCellStyle cellNormalStyle = wb.createCellStyle();
		cellNormalStyle.setFillForegroundColor( bgNormalColor );
		cellNormalStyle.setFillPattern( FillPatternType.SOLID_FOREGROUND );				
		
		XSSFFont cellNormalFont = wb.createFont();
		cellNormalFont.setBold(false);
		cellNormalFont.setColor(fnColor);
		cellNormalStyle.setFont(cellNormalFont);		
		cellNormalStyle.setBorderBottom(BorderStyle.THIN);
		cellNormalStyle.setBorderTop(BorderStyle.THIN);
		cellNormalStyle.setBorderRight(BorderStyle.THIN);
		cellNormalStyle.setBorderLeft(BorderStyle.THIN);
		cellNormalStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellNormalStyle.setAlignment(HorizontalAlignment.LEFT);
		cellNormalStyle.setWrapText(true);		
		
		// --------------------------------------------------------------------------------------
		
		row++;
		
		Row labelRow = sh.createRow(row);
		Cell labelCell_0_1 = labelRow.createCell(0);	
		labelCell_0_1.setCellValue( "Responsibility" );
		labelCell_0_1.setCellStyle(cellLabelStyle);			
		
		Cell labelCell_0_2 = labelRow.createCell(1);	
		labelCell_0_2.setCellValue( pdca.getResponsibilityAppendNames() );
		labelCell_0_2.setCellStyle(cellNormalStyle);			
		
		Cell labelCell_0_3 = labelRow.createCell(2);	
		labelCell_0_3.setCellValue( pdca.getResponsibilityAppendNames() );
		labelCell_0_3.setCellStyle(cellNormalStyle);				
		
		sh.addMergedRegion( new CellRangeAddress(row, row, 1, 2) );
		
		Cell labelCell_0_4 = labelRow.createCell(3);	
		labelCell_0_4.setCellValue( "Date range" );
		labelCell_0_4.setCellStyle(cellLabelStyle);
		
		Cell labelCell_0_5 = labelRow.createCell(4);	
		labelCell_0_5.setCellValue( pdca.getStartDateDisplayValue() + " ~ " + pdca.getEndDateDisplayValue() );
		labelCell_0_5.setCellStyle(cellNormalStyle);				
		
		Cell labelCell_0_6 = labelRow.createCell(5);	
		labelCell_0_6.setCellValue( pdca.getStartDateDisplayValue() + " ~ " + pdca.getEndDateDisplayValue() );
		labelCell_0_6.setCellStyle(cellNormalStyle);		
		
		sh.addMergedRegion( new CellRangeAddress(row, row, 4, 5) );
		
		// --------------------------------------------------------------------------------------
		
		row++;
		
		labelRow = sh.createRow(row);
		Cell labelCell_1_1 = labelRow.createCell(0);	
		labelCell_1_1.setCellValue( "Confirm" );
		labelCell_1_1.setCellStyle(cellLabelStyle);				
		
		Cell labelCell_1_2 = labelRow.createCell(1);	
		labelCell_1_2.setCellValue( pdca.getConfirmEmployeeName() );
		labelCell_1_2.setCellStyle(cellNormalStyle);
		
		Cell labelCell_1_3 = labelRow.createCell(2);	
		labelCell_1_3.setCellValue( pdca.getConfirmEmployeeName() );
		labelCell_1_3.setCellStyle(cellNormalStyle);			
		
		sh.addMergedRegion( new CellRangeAddress(row, row, 1, 2) );
		
		Cell labelCell_1_4 = labelRow.createCell(3);	
		labelCell_1_4.setCellValue( "Confirm date" );
		labelCell_1_4.setCellStyle(cellLabelStyle);	
		
		Cell labelCell_1_5 = labelRow.createCell(4);	
		labelCell_1_5.setCellValue( pdca.getConfirmDateDisplayValue() );
		labelCell_1_5.setCellStyle(cellNormalStyle);	
		
		Cell labelCell_1_6 = labelRow.createCell(5);	
		labelCell_1_6.setCellValue( pdca.getConfirmDateDisplayValue() );
		labelCell_1_6.setCellStyle(cellNormalStyle);			
		
		sh.addMergedRegion( new CellRangeAddress(row, row, 4, 5) );
		
		// --------------------------------------------------------------------------------------
		
		row++;
		
		labelRow = sh.createRow(row);
		Cell labelCell_2_1 = labelRow.createCell(0);	
		labelCell_2_1.setCellValue( "Organization\nDepartment" );
		labelCell_2_1.setCellStyle(cellLabelStyle);				
		
		Cell labelCell_2_2 = labelRow.createCell(1);	
		labelCell_2_2.setCellValue( pdca.getOrganizationAppendNames() );
		labelCell_2_2.setCellStyle(cellNormalStyle);	
		
		Cell labelCell_2_3 = labelRow.createCell(2);	
		labelCell_2_3.setCellValue( pdca.getOrganizationAppendNames() );
		labelCell_2_3.setCellStyle(cellNormalStyle);
		
		Cell labelCell_2_4 = labelRow.createCell(3);	
		labelCell_2_4.setCellValue( pdca.getOrganizationAppendNames() );
		labelCell_2_4.setCellStyle(cellNormalStyle);	
		
		Cell labelCell_2_5 = labelRow.createCell(4);	
		labelCell_2_5.setCellValue( pdca.getOrganizationAppendNames() );
		labelCell_2_5.setCellStyle(cellNormalStyle);	
		
		Cell labelCell_2_6 = labelRow.createCell(5);	
		labelCell_2_6.setCellValue( pdca.getOrganizationAppendNames() );
		labelCell_2_6.setCellStyle(cellNormalStyle);			
		
		sh.addMergedRegion( new CellRangeAddress(row, row, 1, 5) );		
		
		// --------------------------------------------------------------------------------------
		
		row++;
		
		labelRow = sh.createRow(row);
		Cell labelCell_3_1 = labelRow.createCell(0);	
		labelCell_3_1.setCellValue( "KPIs" );
		labelCell_3_1.setCellStyle(cellLabelStyle);				
		
		Cell labelCell_3_2 = labelRow.createCell(1);	
		labelCell_3_2.setCellValue( pdca.getKpisAppendNames() );
		labelCell_3_2.setCellStyle(cellNormalStyle);	
		
		Cell labelCell_3_3 = labelRow.createCell(2);	
		labelCell_3_3.setCellValue( pdca.getKpisAppendNames() );
		labelCell_3_3.setCellStyle(cellNormalStyle);
		
		Cell labelCell_3_4 = labelRow.createCell(3);	
		labelCell_3_4.setCellValue( pdca.getKpisAppendNames() );
		labelCell_3_4.setCellStyle(cellNormalStyle);	
		
		Cell labelCell_3_5 = labelRow.createCell(4);	
		labelCell_3_5.setCellValue( pdca.getKpisAppendNames() );
		labelCell_3_5.setCellStyle(cellNormalStyle);	
		
		Cell labelCell_3_6 = labelRow.createCell(5);	
		labelCell_3_6.setCellValue( pdca.getKpisAppendNames() );
		labelCell_3_6.setCellStyle(cellNormalStyle);			
		
		sh.addMergedRegion( new CellRangeAddress(row, row, 1, 5) );			
		
		// --------------------------------------------------------------------------------------
		
		row++;
		
		labelRow = sh.createRow(row);
		Cell labelCell_4_1 = labelRow.createCell(0);	
		labelCell_4_1.setCellValue( "Parent PDCA" );
		labelCell_4_1.setCellStyle(cellLabelStyle);				
		
		Cell labelCell_4_2 = labelRow.createCell(1);	
		labelCell_4_2.setCellValue( pdca.getParentName() );
		labelCell_4_2.setCellStyle(cellNormalStyle);	
		
		Cell labelCell_4_3 = labelRow.createCell(2);	
		labelCell_4_3.setCellValue( pdca.getParentName() );
		labelCell_4_3.setCellStyle(cellNormalStyle);
		
		Cell labelCell_4_4 = labelRow.createCell(3);	
		labelCell_4_4.setCellValue( pdca.getParentName() );
		labelCell_4_4.setCellStyle(cellNormalStyle);	
		
		Cell labelCell_4_5 = labelRow.createCell(4);	
		labelCell_4_5.setCellValue( pdca.getParentName() );
		labelCell_4_5.setCellStyle(cellNormalStyle);	
		
		Cell labelCell_4_6 = labelRow.createCell(5);	
		labelCell_4_6.setCellValue( pdca.getParentName() );
		labelCell_4_6.setCellStyle(cellNormalStyle);			
		
		sh.addMergedRegion( new CellRangeAddress(row, row, 1, 5) );	
		
		
		// --------------------------------------------------------------------------------------
		
		row++;
		
		labelRow = sh.createRow(row);
		Cell labelCell_6_1 = labelRow.createCell(0);	
		labelCell_6_1.setCellValue( "TYPE" );
		labelCell_6_1.setCellStyle(cellLabelStyle);				
		
		Cell labelCell_6_2 = labelRow.createCell(1);	
		labelCell_6_2.setCellValue( "Title" );
		labelCell_6_2.setCellStyle(cellLabelStyle);	
		
		Cell labelCell_6_3 = labelRow.createCell(2);	
		labelCell_6_3.setCellValue( "Responsibility" );
		labelCell_6_3.setCellStyle(cellLabelStyle);
		
		Cell labelCell_6_4 = labelRow.createCell(3);	
		labelCell_6_4.setCellValue( "Date range" );
		labelCell_6_4.setCellStyle(cellLabelStyle);	
		
		Cell labelCell_6_5 = labelRow.createCell(4);	
		labelCell_6_5.setCellValue( "Audit" );
		labelCell_6_5.setCellStyle(cellLabelStyle);	
		
		Cell labelCell_6_6 = labelRow.createCell(5);	
		labelCell_6_6.setCellValue( "Audit date" );
		labelCell_6_6.setCellStyle(cellLabelStyle);			
		
	
		// --------------------------------------------------------------------------------------
		
		row++;
		
		int nRow = row;
		
		row = this.createPdcaItem(wb, sh, row, cellNormalStyle, pdca.getItemPlan(), pdca.getAuditPlan());
		row = this.createPdcaItem(wb, sh, row, cellNormalStyle, pdca.getItemDo(), pdca.getAuditDo());
		row = this.createPdcaItem(wb, sh, row, cellNormalStyle, pdca.getItemCheck(), pdca.getAuditCheck());
		row = this.createPdcaItem(wb, sh, row, cellNormalStyle, pdca.getItemAction(), pdca.getAuditAction());
		
		nRow = this.mergedRegionForItemsRow(wb, sh, nRow, pdca.getItemPlan() );
		nRow = this.mergedRegionForItemsRow(wb, sh, nRow, pdca.getItemDo() );
		nRow = this.mergedRegionForItemsRow(wb, sh, nRow, pdca.getItemCheck() );
		nRow = this.mergedRegionForItemsRow(wb, sh, nRow, pdca.getItemAction() );
		
		return row;
	}
	
	private int createPdcaItem(XSSFWorkbook wb, XSSFSheet sh, int row, XSSFCellStyle cellNormalStyle, List<PdcaItemVO> items, PdcaAuditVO audit) throws Exception {
		
		XSSFColor fnColor = new XSSFColor( SimpleUtils.getColorRGB4POIColor("#000000") );		
		XSSFColor bgLabelColor = new XSSFColor( SimpleUtils.getColorRGB4POIColor("#F2F2F2") );
		
		XSSFCellStyle cellLabelStyle = wb.createCellStyle();
		cellLabelStyle.setFillForegroundColor( bgLabelColor );
		cellLabelStyle.setFillPattern( FillPatternType.SOLID_FOREGROUND );				
		
		XSSFFont cellLabelFont = wb.createFont();
		cellLabelFont.setBold(true);
		cellLabelFont.setColor(fnColor);
		cellLabelStyle.setFont(cellLabelFont);		
		cellLabelStyle.setBorderBottom(BorderStyle.THIN);
		cellLabelStyle.setBorderTop(BorderStyle.THIN);
		cellLabelStyle.setBorderRight(BorderStyle.THIN);
		cellLabelStyle.setBorderLeft(BorderStyle.THIN);
		cellLabelStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellLabelStyle.setAlignment(HorizontalAlignment.CENTER);
		cellLabelStyle.setWrapText(true);			
		
		Map<String, String> pdcaTypeMap = PdcaType.getDataMap(false);
		
		for (PdcaItemVO item : items) {
			
			Row labelRow = sh.createRow(row);
			Cell labelCell_6_1 = labelRow.createCell(0);	
			labelCell_6_1.setCellValue( pdcaTypeMap.get(item.getType()) );
			labelCell_6_1.setCellStyle(cellLabelStyle);				
			
			Cell labelCell_6_2 = labelRow.createCell(1);	
			labelCell_6_2.setCellValue( item.getTitle() + ( !StringUtils.isBlank(item.getDescription()) ? "\n\n" + item.getDescription() : "" ) );
			labelCell_6_2.setCellStyle(cellNormalStyle);	
			
			Cell labelCell_6_3 = labelRow.createCell(2);	
			labelCell_6_3.setCellValue( item.getEmployeeAppendNames() );
			labelCell_6_3.setCellStyle(cellNormalStyle);
			
			Cell labelCell_6_4 = labelRow.createCell(3);	
			labelCell_6_4.setCellValue( item.getStartDateDisplayValue() + " ~ " + item.getEndDateDisplayValue() );
			labelCell_6_4.setCellStyle(cellNormalStyle);	
			
			Cell labelCell_6_5 = labelRow.createCell(4);	
			labelCell_6_5.setCellValue( (audit != null ? audit.getEmpId() : " ") );
			labelCell_6_5.setCellStyle(cellNormalStyle);	
			
			Cell labelCell_6_6 = labelRow.createCell(5);	
			labelCell_6_6.setCellValue( (audit != null ? audit.getConfirmDateDisplayValue() : " ") );
			labelCell_6_6.setCellStyle(cellNormalStyle);
			
			
			row++;
			
		}
		
		return row;
	}
	
	private int mergedRegionForItemsRow(XSSFWorkbook wb, XSSFSheet sh, int row, List<PdcaItemVO> items) throws Exception {
		sh.addMergedRegion( new CellRangeAddress(row, row+items.size()-1, 0, 0) );
		sh.addMergedRegion( new CellRangeAddress(row, row+items.size()-1, 4, 4) );
		sh.addMergedRegion( new CellRangeAddress(row, row+items.size()-1, 5, 5) );
		return row + items.size();
	}
	
	private String createZipFile(List<SysUploadVO> uploads) throws Exception {
		ZipFile zip = new ZipFile( Constants.getWorkTmpDir() + SimpleUtils.getUUIDStr() + ".zip" );
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		for (SysUploadVO reportUpload : uploads) {
			zip.addFile(UploadSupportUtils.getRealFile(reportUpload.getOid()), parameters);
		}
		return UploadSupportUtils.create(Constants.getSystem(), UploadTypes.IS_TEMP, false, zip.getFile(), "pdca-report.zip");
	}

}
