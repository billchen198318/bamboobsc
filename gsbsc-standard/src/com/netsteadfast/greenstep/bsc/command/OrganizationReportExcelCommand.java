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

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.util.BscReportPropertyUtils;
import com.netsteadfast.greenstep.bsc.util.BscReportSupportUtils;
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.DateRangeScoreVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

public class OrganizationReportExcelCommand extends BaseChainCommandSupport implements Command {
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	
	@SuppressWarnings("unchecked")
	public OrganizationReportExcelCommand() {
		super();
		organizationService = (IOrganizationService<OrganizationVO, BbOrganization, String>)
				AppContext.getBean("bsc.service.OrganizationService");				
	}
	
	@Override
	public boolean execute(Context context) throws Exception {
		if (this.getResult(context)==null || !(this.getResult(context) instanceof BscStructTreeObj) ) {
			return false;
		}
		String uploadOid = this.createExcel(context);
		this.setResult(context, uploadOid);		
		return false;
	}
	
	private String createExcel(Context context) throws Exception {
		String visionOid = (String)context.get("visionOid");
		VisionVO vision = null;
		BscStructTreeObj treeObj = (BscStructTreeObj)this.getResult(context);
		for (VisionVO visionObj : treeObj.getVisions()) {
			if (visionObj.getOid().equals(visionOid)) {
				vision = visionObj;
			}
		}
		BscReportPropertyUtils.loadData();
		String fileName = SimpleUtils.getUUIDStr() + ".xlsx";
		String fileFullPath = Constants.getWorkTmpDir() + "/" + fileName;	
		int row = 0;
		XSSFWorkbook wb = new XSSFWorkbook();				
		XSSFSheet sh = wb.createSheet();
		
		row += this.createHead(wb, sh, row, vision, context);
		row = this.createMainBody(wb, sh, row, vision);
		
		this.putSignature(wb, sh, row+1, context);
		
        FileOutputStream out = new FileOutputStream(fileFullPath);
        wb.write(out);
        out.close();
        wb = null;
        
        File file = new File(fileFullPath);
		String oid = UploadSupportUtils.create(
				Constants.getSystem(), UploadTypes.IS_TEMP, false, file, "department-report.xlsx");
		file = null;
		return oid;		
	}
	
	private void putSignature(XSSFWorkbook wb, XSSFSheet sh, int row, Context context) throws Exception {
		String uploadOid = (String)context.get("uploadSignatureOid");
		if ( StringUtils.isBlank(uploadOid) ) {
			return;
		}
		byte[] imageBytes = UploadSupportUtils.getDataBytes( uploadOid );
		if ( null == imageBytes ) {
			return;
		}
		SimpleUtils.setCellPicture(wb, sh, imageBytes, row, 0);
	}		
	
	private int createHead(XSSFWorkbook wb, XSSFSheet sh, int row, VisionVO vision, Context context) throws Exception {
		String dateType = (String)context.get("dateType");
		String year = (String)context.get("startYearDate");
		String orgId = (String)context.get("orgId");
		String departmentName = "";
		String dateTypeName = "Year";
		if ( "1".equals(dateType) ) {
			dateTypeName = "In the first half";
		}
		if ( "2".equals(dateType) ) {
			dateTypeName = "In the second half";
		}
		OrganizationVO organization = new OrganizationVO();
		organization.setOrgId(orgId);
		DefaultResult<OrganizationVO> result = this.organizationService.findByUK(organization);
		if ( result.getValue() != null ) {
			organization = result.getValue();
			departmentName = organization.getName();
		}		
		
		Row headRow = sh.createRow(row);
		headRow.setHeight( (short)700 );
		
		XSSFColor bgColor = new XSSFColor( SimpleUtils.getColorRGB4POIColor("#F2F2F2") );
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
		
		int cols = 6;
		for (int i=0; i<cols; i++) {
			sh.setColumnWidth(i, 6000);
			Cell headCell1 = headRow.createCell(i);	
			headCell1.setCellValue( "Personal Balance SourceCard" );
			headCell1.setCellStyle(cellHeadStyle);			
		}		
		sh.addMergedRegion( new CellRangeAddress(row, row, 0, cols-1) );
		
		row++;
		headRow = sh.createRow(row);
		for (int i=0; i<cols; i++) {
			sh.setColumnWidth(i, 6000);
			Cell headCell1 = headRow.createCell(i);	
			headCell1.setCellValue( vision.getTitle() );
			headCell1.setCellStyle(cellHeadStyle);			
		}		
		sh.addMergedRegion( new CellRangeAddress(row, row, 0, cols-1) );				
		
		
		row++;
		headRow = sh.createRow(row);
		
		Cell titleCell1 = headRow.createCell(0);	
		titleCell1.setCellValue( "Department" );
		titleCell1.setCellStyle(cellHeadStyle);			
		
		Cell titleCell2 = headRow.createCell(1);	
		titleCell2.setCellValue( departmentName );
		titleCell2.setCellStyle(cellHeadStyle);			
		
		Cell titleCell3 = headRow.createCell(2);	
		titleCell3.setCellValue( departmentName );
		titleCell3.setCellStyle(cellHeadStyle);		
		
		Cell titleCell4 = headRow.createCell(3);	
		titleCell4.setCellValue( departmentName );
		titleCell4.setCellStyle(cellHeadStyle);		
		
		Cell titleCell5 = headRow.createCell(4);	
		titleCell5.setCellValue( departmentName );
		titleCell5.setCellStyle(cellHeadStyle);		
		
		Cell titleCell6 = headRow.createCell(5);	
		titleCell6.setCellValue( year + " " + dateTypeName );
		titleCell6.setCellStyle(cellHeadStyle);				
		
		sh.addMergedRegion( new CellRangeAddress(row, row, 1, cols-2) );
		
		
		row++;
		headRow = sh.createRow(row);		
		
		titleCell1 = headRow.createCell(0);	
		titleCell1.setCellValue( BscReportPropertyUtils.getPerspectiveTitle() );
		titleCell1.setCellStyle(cellHeadStyle);		
		
		titleCell2 = headRow.createCell(1);	
		titleCell2.setCellValue( BscReportPropertyUtils.getObjectiveTitle() );
		titleCell2.setCellStyle(cellHeadStyle);		
		
		titleCell3 = headRow.createCell(2);	
		titleCell3.setCellValue( BscReportPropertyUtils.getKpiTitle() );
		titleCell3.setCellStyle(cellHeadStyle);		
		
		titleCell4 = headRow.createCell(3);	
		titleCell4.setCellValue( "Weight" );
		titleCell4.setCellStyle(cellHeadStyle);	
		
		titleCell5 = headRow.createCell(4);	
		titleCell5.setCellValue( "Maximum\nTarget\nMinimum" );
		titleCell5.setCellStyle(cellHeadStyle);			
		
		titleCell6 = headRow.createCell(5);	
		titleCell6.setCellValue( "Score" );
		titleCell6.setCellStyle(cellHeadStyle);			
		
		row = row + 1;
		return row;
	}
	
	private int createMainBody(XSSFWorkbook wb, XSSFSheet sh, int row, VisionVO vision) throws Exception {
		
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor( new XSSFColor(SimpleUtils.getColorRGB4POIColor("#ffffff")) );
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);	
		XSSFFont cellFont = wb.createFont();
		cellFont.setBold(false);
		cellFont.setColor( new XSSFColor(SimpleUtils.getColorRGB4POIColor("#000000")) );
		cellStyle.setFont(cellFont);
		cellStyle.setWrapText(true);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		
		int mrRow = row;	
		for (int px=0; px<vision.getPerspectives().size(); px++) {
			PerspectiveVO perspective = vision.getPerspectives().get(px);
			
			for (int ox=0; ox<perspective.getObjectives().size(); ox++) {
				ObjectiveVO objective = perspective.getObjectives().get(ox);
				
				for (int kx=0; kx<objective.getKpis().size(); kx++) {
					KpiVO kpi = objective.getKpis().get(kx);
					Row contentRow = sh.createRow(row++);
					
					Cell cell1 = contentRow.createCell(0);	
					cell1.setCellValue( perspective.getName() );
					cell1.setCellStyle(cellStyle);		
					
					Cell titleCell2 = contentRow.createCell(1);	
					titleCell2.setCellValue( objective.getName() );
					titleCell2.setCellStyle(cellStyle);		
					
					Cell titleCell3 = contentRow.createCell(2);	
					titleCell3.setCellValue( kpi.getName() );
					titleCell3.setCellStyle(cellStyle);		
					
					Cell titleCell4 = contentRow.createCell(3);	
					titleCell4.setCellValue( kpi.getWeight() + "%" );
					titleCell4.setCellStyle(cellStyle);	
					
					Cell titleCell5 = contentRow.createCell(4);	
					titleCell5.setCellValue( 
							"max: " + kpi.getMax() + "\n" +
							"target: " + kpi.getTarget() + "\n" +
							"min: " + kpi.getMin() + "\n" + 
							"unit: " + kpi.getUnit() );
					titleCell5.setCellStyle(cellStyle);			
					
					DateRangeScoreVO dateRangeScore = kpi.getDateRangeScores().get(0);
					
					XSSFCellStyle cellStyle2 = wb.createCellStyle();
					cellStyle2.setFillForegroundColor( new XSSFColor(SimpleUtils.getColorRGB4POIColor(dateRangeScore.getBgColor())) );
					cellStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);	
					XSSFFont cellFont2 = wb.createFont();
					cellFont2.setBold(false);
					cellFont2.setColor( new XSSFColor(SimpleUtils.getColorRGB4POIColor(dateRangeScore.getFontColor())) );
					cellStyle2.setFont(cellFont2);
					cellStyle2.setWrapText(true);
					cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
					cellStyle2.setBorderBottom(BorderStyle.THIN);
					cellStyle2.setBorderTop(BorderStyle.THIN);
					cellStyle2.setBorderRight(BorderStyle.THIN);
					cellStyle2.setBorderLeft(BorderStyle.THIN);					
					
					Cell titleCell6 = contentRow.createCell(5);	
					titleCell6.setCellValue( BscReportSupportUtils.parse2(dateRangeScore.getScore()) );
					titleCell6.setCellStyle(cellStyle2);	
					
				}
			}
		}
		
		for (int px=0; px<vision.getPerspectives().size(); px++) {
			PerspectiveVO perspective = vision.getPerspectives().get(px);
			// 2016-12-13 old work with POI 3.12
			//sh.addMergedRegion(new CellRangeAddress(mrRow, mrRow + perspective.getRow()-1, 0, 0));
			
			// 2016-12-13 new work with POI 3.15
			int mrRow1 = mrRow + perspective.getRow()-1;
			if (mrRow1 > mrRow) {
				sh.addMergedRegion(new CellRangeAddress(mrRow, mrRow1, 0, 0));
			}
			
			for (int ox=0; ox<perspective.getObjectives().size(); ox++) {
				ObjectiveVO objective = perspective.getObjectives().get(ox);
				// 2016-12-13 old work with POI 3.12
				//sh.addMergedRegion(new CellRangeAddress(mrRow, mrRow + objective.getRow()-1, 1, 1));
				
				// 2016-12-13 new work with POI 3.15
				int mrRow2 = mrRow + objective.getRow()-1;
				if (mrRow2 > mrRow) {
					sh.addMergedRegion(new CellRangeAddress(mrRow, mrRow2, 1, 1));
				}
				
				mrRow += objective.getKpis().size();
			}
			
		}		
		
		return row++;
	}

}
