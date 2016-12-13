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
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.util.BscReportPropertyUtils;
import com.netsteadfast.greenstep.bsc.util.BscReportSupportUtils;
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.po.hbm.BbEmployee;
import com.netsteadfast.greenstep.po.hbm.BbOrganization;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.util.UploadSupportUtils;
import com.netsteadfast.greenstep.vo.DateRangeScoreVO;
import com.netsteadfast.greenstep.vo.EmployeeVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

public class PersonalReportExcelCommand extends BaseChainCommandSupport implements Command {
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;
	
	@SuppressWarnings("unchecked")
	public PersonalReportExcelCommand() {
		super();
		organizationService = (IOrganizationService<OrganizationVO, BbOrganization, String>)
				AppContext.getBean("bsc.service.OrganizationService");
		employeeService = (IEmployeeService<EmployeeVO, BbEmployee, String>)
				AppContext.getBean("bsc.service.EmployeeService");				
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
		row = this.createMainBody(wb, sh, row, vision, context);
		this.createFoot(wb, sh, row, vision, context);
		
		this.putSignature(wb, sh, row+2, context);
		
        FileOutputStream out = new FileOutputStream(fileFullPath);
        wb.write(out);
        out.close();
        wb = null;
        
        File file = new File(fileFullPath);
		String oid = UploadSupportUtils.create(
				Constants.getSystem(), UploadTypes.IS_TEMP, false, file, "personal-report.xlsx");
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
		String empId = (String)context.get("empId");
		String account = (String)context.get("account");	
		String fullName = "";
		String jobTitle = "";
		String departmentName = "";
		String dateTypeName = "Year";
		if ( "1".equals(dateType) ) {
			dateTypeName = "In the first half";
		}
		if ( "2".equals(dateType) ) {
			dateTypeName = "In the second half";
		}
		EmployeeVO employee = new EmployeeVO();
		employee.setEmpId(empId);
		employee.setAccount(account);
		DefaultResult<EmployeeVO> result = this.employeeService.findByUK(employee);
		if (result.getValue()!=null) {
			fullName = result.getValue().getEmpId() + " - " + result.getValue().getFullName();
			jobTitle = result.getValue().getEmpId() + " - " + result.getValue().getFullName();
			List<String> appendIds = this.organizationService.findForAppendOrganizationOids(
					result.getValue().getEmpId());
			List<String> appendNames = this.organizationService.findForAppendNames(appendIds);
			StringBuilder sb = new StringBuilder();
			for (int i=0; appendNames!=null && i<appendNames.size(); i++) {
				sb.append(appendNames.get(i)).append(Constants.ID_DELIMITER);
			}
			departmentName = sb.toString();
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
		headRow.setHeight( (short)700 );
		
		Cell titleCell1 = headRow.createCell(0);	
		titleCell1.setCellValue( "Job Title" );
		titleCell1.setCellStyle(cellHeadStyle);					
		
		Cell titleCell2 = headRow.createCell(1);	
		titleCell2.setCellValue( jobTitle );
		titleCell2.setCellStyle(cellHeadStyle);		
		
		Cell titleCell3 = headRow.createCell(2);	
		titleCell3.setCellValue( "Department" );
		titleCell3.setCellStyle(cellHeadStyle);			
		
		Cell titleCell4 = headRow.createCell(3);	
		titleCell4.setCellValue( departmentName );
		titleCell4.setCellStyle(cellHeadStyle);				
		
		Cell titleCell5 = headRow.createCell(4);	
		titleCell5.setCellValue( "name: " + fullName );
		titleCell5.setCellStyle(cellHeadStyle);			
		
		Cell titleCell6 = headRow.createCell(5);	
		titleCell6.setCellValue( "Annual assessment: " + year );
		titleCell6.setCellStyle(cellHeadStyle);				
		
		
		row++;
		headRow = sh.createRow(row);
		
		titleCell1 = headRow.createCell(0);	
		titleCell1.setCellValue( BscReportPropertyUtils.getObjectiveTitle() );
		titleCell1.setCellStyle(cellHeadStyle);		
		
		titleCell2 = headRow.createCell(1);	
		titleCell2.setCellValue( BscReportPropertyUtils.getKpiTitle() );
		titleCell2.setCellStyle(cellHeadStyle);		
		
		titleCell3 = headRow.createCell(2);	
		titleCell3.setCellValue( "Maximum\nTarget\nMinimum" );
		titleCell3.setCellStyle(cellHeadStyle);		
		
		titleCell4 = headRow.createCell(3);	
		titleCell4.setCellValue( "Weight" );
		titleCell4.setCellStyle(cellHeadStyle);	
		
		titleCell5 = headRow.createCell(4);	
		titleCell5.setCellValue( "Formula" );
		titleCell5.setCellStyle(cellHeadStyle);	
		
		titleCell6 = headRow.createCell(5);	
		titleCell6.setCellValue( "Score" );
		titleCell6.setCellStyle(cellHeadStyle);	
		
		
		row++;
		headRow = sh.createRow(row);
		headRow.setHeight( (short)1000 );
		
		titleCell1 = headRow.createCell(0);	
		titleCell1.setCellValue( "Objective of Strategy" );
		titleCell1.setCellStyle(cellHeadStyle);		
		
		titleCell2 = headRow.createCell(1);	
		titleCell2.setCellValue( "KPI" );
		titleCell2.setCellStyle(cellHeadStyle);		
		
		titleCell3 = headRow.createCell(2);	
		titleCell3.setCellValue( "Target" );
		titleCell3.setCellStyle(cellHeadStyle);		
		
		titleCell4 = headRow.createCell(3);	
		titleCell4.setCellValue( "Weight" );
		titleCell4.setCellStyle(cellHeadStyle);	
		
		titleCell5 = headRow.createCell(4);	
		titleCell5.setCellValue( "Formula" );
		titleCell5.setCellStyle(cellHeadStyle);	
		
		XSSFCellStyle titleStyle = wb.createCellStyle();
		titleStyle.setFillForegroundColor( bgColor );
		titleStyle.setFillPattern( FillPatternType.SOLID_FOREGROUND );		
		titleStyle.setFillForegroundColor(  new XSSFColor(SimpleUtils.getColorRGB4POIColor("#F5F4F4")) );
		titleStyle.setFont(cellHeadFont);		
		titleStyle.setBorderBottom(BorderStyle.THIN);
		titleStyle.setBorderTop(BorderStyle.THIN);
		titleStyle.setBorderRight(BorderStyle.THIN);
		titleStyle.setBorderLeft(BorderStyle.THIN);
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		titleStyle.setWrapText(true);				
		
		titleCell6 = headRow.createCell(5);	
		titleCell6.setCellValue( dateTypeName );
		titleCell6.setCellStyle(titleStyle);			
		
		for (int i=0; i<5; i++) {
			sh.addMergedRegion( new CellRangeAddress(row-1, row, i, i) );
		}
		
		return 5;
	}
	
	private int createMainBody(XSSFWorkbook wb, XSSFSheet sh, int row, VisionVO vision, Context context) throws Exception {
		int mrRow = row;
		
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
		cellStyle.setWrapText(true);		
		
		for (PerspectiveVO perspective : vision.getPerspectives()) {
			for (ObjectiveVO objective : perspective.getObjectives()) {
				for (KpiVO kpi : objective.getKpis()) {
					int kCol = 0;
					Row contentRow = sh.createRow(row++);
					contentRow.setHeight( (short)1000 );				
					
					Cell contentCell1 = contentRow.createCell(kCol++);
					contentCell1.setCellValue( objective.getName() );
					contentCell1.setCellStyle(cellStyle);
					
					Cell contentCell2 = contentRow.createCell(kCol++);
					contentCell2.setCellValue( kpi.getName() );
					contentCell2.setCellStyle(cellStyle);
					
					Cell contentCell3 = contentRow.createCell(kCol++);
					contentCell3.setCellValue(
							"max: " + kpi.getMax() + "\n" +
							"target: " + kpi.getTarget() + "\n" +
							"min: " + kpi.getMin() + "\n" + 
							"unit: " + kpi.getUnit());
					contentCell3.setCellStyle(cellStyle);					
					
					Cell contentCell4 = contentRow.createCell(kCol++);
					contentCell4.setCellValue( kpi.getWeight() + "%" );
					contentCell4.setCellStyle(cellStyle);					
					
					Cell contentCell5 = contentRow.createCell(kCol++);
					contentCell5.setCellValue( kpi.getFormula().getName() );
					contentCell5.setCellStyle(cellStyle);						
					
					DateRangeScoreVO dateRangeScore = kpi.getDateRangeScores().get(0); // 只顯示一筆日期分數資料
					
					XSSFCellStyle cellStyleScore=wb.createCellStyle();
					cellStyleScore.setFillForegroundColor( new XSSFColor(SimpleUtils.getColorRGB4POIColor(dateRangeScore.getBgColor())) );
					cellStyleScore.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					
					XSSFFont cellScoreFont=wb.createFont();
					cellScoreFont.setBold(false);
					cellScoreFont.setColor( new XSSFColor(SimpleUtils.getColorRGB4POIColor(dateRangeScore.getFontColor())) );
					cellStyleScore.setFont(cellScoreFont);				
					cellStyleScore.setWrapText(true);
					cellStyleScore.setVerticalAlignment(VerticalAlignment.CENTER);
					cellStyleScore.setBorderBottom(BorderStyle.THIN);
					cellStyleScore.setBorderTop(BorderStyle.THIN);
					cellStyleScore.setBorderRight(BorderStyle.THIN);
					cellStyleScore.setBorderLeft(BorderStyle.THIN);					
					
					Cell contentCell6 = contentRow.createCell(kCol++);
					contentCell6.setCellValue( BscReportSupportUtils.parse2(dateRangeScore.getScore()) );
					contentCell6.setCellStyle(cellStyleScore);					
					
				}
			}
		}
		
		for (PerspectiveVO perspective : vision.getPerspectives()) {
			for (ObjectiveVO objective : perspective.getObjectives()) {
				int rowspan = objective.getRow();
				if ( objective.getRow() > 1 ) {
					sh.addMergedRegion(new CellRangeAddress(mrRow, mrRow+rowspan-1, 0, 0));
				}
				mrRow += rowspan;
			}
		}	
		
		return row;
	}
	
	private void createFoot(XSSFWorkbook wb, XSSFSheet sh, int row, VisionVO vision, Context context) throws Exception {
		
		Row footRow=sh.createRow(row);
		Row footRowB=sh.createRow(row+1);
		XSSFCellStyle cellStyle=wb.createCellStyle();
		
		cellStyle.setFillForegroundColor( new XSSFColor(SimpleUtils.getColorRGB4POIColor("#FFFFFF")) );
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);		
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);					
		XSSFFont cellFont=wb.createFont();
		cellFont.setBold(true);
		cellStyle.setFont(cellFont);
		cellStyle.setWrapText(true);		
		
		Cell footCell1 = footRow.createCell(0);
		footCell1.setCellValue("assess:");
		footCell1.setCellStyle(cellStyle);			
		Cell footCell1B = footRowB.createCell(0);
		footCell1B.setCellValue("assess:");
		footCell1B.setCellStyle(cellStyle);		
		sh.addMergedRegion(new CellRangeAddress(row, row+1, 0, 0));					
		
		Cell footCell2 = footRow.createCell(1);
		footCell2.setCellValue( BscReportPropertyUtils.getPersonalReportClassLevel() );
		footCell2.setCellStyle(cellStyle);					
		Cell footCell3 = footRow.createCell(2);
		footCell3.setCellValue( BscReportPropertyUtils.getPersonalReportClassLevel() );
		footCell3.setCellStyle(cellStyle);			
		Cell footCell4 = footRow.createCell(3);
		footCell4.setCellValue( BscReportPropertyUtils.getPersonalReportClassLevel() );
		footCell4.setCellStyle(cellStyle);			
		Cell footCell2B = footRowB.createCell(1);
		footCell2B.setCellValue( BscReportPropertyUtils.getPersonalReportClassLevel() );
		footCell2B.setCellStyle(cellStyle);					
		Cell footCell3B = footRowB.createCell(2);
		footCell3B.setCellValue( BscReportPropertyUtils.getPersonalReportClassLevel() );
		footCell3B.setCellStyle(cellStyle);			
		Cell footCell4B = footRowB.createCell(3);
		footCell4B.setCellValue( BscReportPropertyUtils.getPersonalReportClassLevel() );
		footCell4B.setCellStyle(cellStyle);					
		sh.addMergedRegion(new CellRangeAddress(row, row+1, 1, 3));	
		
		Cell footCell5 = footRow.createCell(4);
		footCell5.setCellValue("Total");
		footCell5.setCellStyle(cellStyle);	
		
		float total = 0.0f;
		if ( context.get("total")!=null && context.get("total") instanceof Float ) {
			total = (Float)context.get("total");
		}
		
		Cell footCell6 = footRow.createCell(5);
		footCell6.setCellValue( BscReportSupportUtils.parse2(total) );
		footCell6.setCellStyle(cellStyle);			
		
		Cell footCell5b = footRowB.createCell(4);
		footCell5b.setCellValue("Class");
		footCell5b.setCellStyle(cellStyle);			
		
		Cell footCell6b = footRowB.createCell(5);
		footCell6b.setCellValue( "" );
		footCell6b.setCellStyle(cellStyle);				
		
	}

}
