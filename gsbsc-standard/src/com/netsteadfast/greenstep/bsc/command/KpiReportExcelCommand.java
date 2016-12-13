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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

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

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.bsc.model.BscKpiCode;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.bsc.service.IEmployeeService;
import com.netsteadfast.greenstep.bsc.service.IOrganizationService;
import com.netsteadfast.greenstep.bsc.util.AggregationMethodUtils;
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

public class KpiReportExcelCommand extends BaseChainCommandSupport implements Command {
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;	
	
	@SuppressWarnings("unchecked")
	public KpiReportExcelCommand() {
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
		BscReportSupportUtils.loadExpression(); // 2015-04-18 add
		String fileName = SimpleUtils.getUUIDStr() + ".xlsx";
		String fileFullPath = Constants.getWorkTmpDir() + "/" + fileName;	
		int row = 24;
		if (context.get("pieCanvasToData") == null || context.get("barCanvasToData") == null) {
			row = 0;
		}
		XSSFWorkbook wb = new XSSFWorkbook();				
		XSSFSheet sh = wb.createSheet();
		
		row += this.createHead(wb, sh, row, vision);
		row = this.createMainBody(wb, sh, row, vision);
		
		row = row + 1; // 空一列
		row = this.createDateRange(wb, sh, row, vision, context);
		if (context.get("pieCanvasToData") != null && context.get("barCanvasToData") != null) {
			this.putCharts(wb, sh, context);
		}
		this.putSignature(wb, sh, row+1, context);
		
        FileOutputStream out = new FileOutputStream(fileFullPath);
        wb.write(out);
        out.close();
        wb = null;
        
        File file = new File(fileFullPath);
		String oid = UploadSupportUtils.create(
				Constants.getSystem(), UploadTypes.IS_TEMP, false, file, "kpi-report.xlsx");
		file = null;
		return oid;
	}
	
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
		SimpleUtils.setCellPicture(wb, sh, barBos.toByteArray(), 0, 6);		
		return 25;
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
	
	private int createHead(XSSFWorkbook wb, XSSFSheet sh, int row, VisionVO vision) throws Exception {
		Row headRow = sh.createRow(row);
		headRow.setHeight( (short)700 );
		
		int cell=0;
		
		XSSFColor bgColor = new XSSFColor( SimpleUtils.getColorRGB4POIColor(vision.getBgColor()) );
		XSSFColor fnColor = new XSSFColor( SimpleUtils.getColorRGB4POIColor(vision.getFontColor()) );
		
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
		
		int cols = 12;
		for (int i=0; i<cols; i++) {
			sh.setColumnWidth(i, 4000);
			Cell headCell1 = headRow.createCell(cell++);	
			headCell1.setCellValue( vision.getTitle() + "\nscore: " + BscReportSupportUtils.parse2(vision.getScore()) );
			headCell1.setCellStyle(cellHeadStyle);						
		}
		
		sh.addMergedRegion( new CellRangeAddress(row, row, 0, cols-1) );
		
		
		
		// ------------------------------------------------------------------------
		bgColor = new XSSFColor( SimpleUtils.getColorRGB4POIColor(BscReportPropertyUtils.getBackgroundColor()) );
		fnColor = new XSSFColor( SimpleUtils.getColorRGB4POIColor(BscReportPropertyUtils.getFontColor()) );
		
		cellHeadStyle = wb.createCellStyle();
		cellHeadStyle.setFillForegroundColor( bgColor );
		cellHeadStyle.setFillPattern( FillPatternType.SOLID_FOREGROUND );		
		
		cellHeadFont = wb.createFont();
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
		
		row++;
		headRow = sh.createRow(row);
		cell = 0;
		int titleCols = 4;
		for (int i=0; i<titleCols; i++) {
			Cell headCell1 = headRow.createCell(cell++);	
			headCell1.setCellValue( BscReportPropertyUtils.getPerspectiveTitle() );
			headCell1.setCellStyle(cellHeadStyle);						
		}
		for (int i=0; i<titleCols; i++) {
			Cell headCell1 = headRow.createCell(cell++);	
			headCell1.setCellValue( BscReportPropertyUtils.getObjectiveTitle() );
			headCell1.setCellStyle(cellHeadStyle);						
		}
		for (int i=0; i<titleCols; i++) {
			Cell headCell1 = headRow.createCell(cell++);	
			headCell1.setCellValue( BscReportPropertyUtils.getKpiTitle() );
			headCell1.setCellStyle(cellHeadStyle);						
		}
		
		sh.addMergedRegion( new CellRangeAddress(row, row, 0, 3) );
		sh.addMergedRegion( new CellRangeAddress(row, row, 4, 7) );
		sh.addMergedRegion( new CellRangeAddress(row, row, 8, 11) );
		
		// ------------------------------------------------------------------------
				
		return 2;
	}
	
	private int createMainBody(XSSFWorkbook wb, XSSFSheet sh, int row, VisionVO vision) throws Exception {
		Map<String, String> managementMap = BscKpiCode.getManagementMap(false);
		//Map<String, String> calculationMap = BscKpiCode.getCalculationMap(false);
		int itemCols = 4;
		int mrRow = row;	
		for (int px=0; px<vision.getPerspectives().size(); px++) {
			PerspectiveVO perspective = vision.getPerspectives().get(px);
			
			for (int ox=0; ox<perspective.getObjectives().size(); ox++) {
				ObjectiveVO objective = perspective.getObjectives().get(ox);
				
				for (int kx=0; kx<objective.getKpis().size(); kx++) {
					KpiVO kpi = objective.getKpis().get(kx);
					
					Row contentRow = sh.createRow(row++);
					contentRow.setHeight((short)4000);					
					
					int cell = 0;
					
					for (int i=0; i<itemCols; i++) {
						String content = this.getItemsContent(
								perspective.getName(), 
								perspective.getScore(), 
								perspective.getWeight(), 
								perspective.getTarget(), 
								perspective.getMin());
						XSSFCellStyle cellStyle = wb.createCellStyle();
						cellStyle.setFillForegroundColor( new XSSFColor(SimpleUtils.getColorRGB4POIColor(perspective.getBgColor())) );
						cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);	
						XSSFFont cellFont = wb.createFont();
						cellFont.setBold(false);
						cellFont.setColor( new XSSFColor(SimpleUtils.getColorRGB4POIColor(perspective.getFontColor())) );
						cellStyle.setFont(cellFont);
						cellStyle.setWrapText(true);
						cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
						cellStyle.setBorderBottom(BorderStyle.THIN);
						cellStyle.setBorderTop(BorderStyle.THIN);
						cellStyle.setBorderRight(BorderStyle.THIN);
						cellStyle.setBorderLeft(BorderStyle.THIN);
						Cell contentCell1 = contentRow.createCell(cell++);
						contentCell1.setCellValue( "\n" + content );
						contentCell1.setCellStyle(cellStyle);	
						
						if (i==0 && ox==0) {
							byte[] imgBytes = BscReportSupportUtils.getByteIconBase(
									"PERSPECTIVES", 
									perspective.getTarget(), 
									perspective.getMin(), 
									perspective.getScore(), 
									"", 
									"", 
									0);
							if (null != imgBytes) {
								SimpleUtils.setCellPicture(wb, sh, imgBytes, contentCell1.getRowIndex(), contentCell1.getColumnIndex());
							}									
						}					
						
					}
					for (int i=0; i<itemCols; i++) {
						String content = this.getItemsContent(
								objective.getName(), 
								objective.getScore(), 
								objective.getWeight(), 
								objective.getTarget(), 
								objective.getMin());						
						XSSFCellStyle cellStyle = wb.createCellStyle();
						cellStyle.setFillForegroundColor( new XSSFColor(SimpleUtils.getColorRGB4POIColor(objective.getBgColor())) );
						cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);	
						XSSFFont cellFont = wb.createFont();
						cellFont.setBold(false);
						cellFont.setColor( new XSSFColor(SimpleUtils.getColorRGB4POIColor(objective.getFontColor())) );
						cellStyle.setFont(cellFont);
						cellStyle.setWrapText(true);
						cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
						cellStyle.setBorderBottom(BorderStyle.THIN);
						cellStyle.setBorderTop(BorderStyle.THIN);
						cellStyle.setBorderRight(BorderStyle.THIN);
						cellStyle.setBorderLeft(BorderStyle.THIN);
						Cell contentCell1 = contentRow.createCell(cell++);
						contentCell1.setCellValue( "\n" + content );
						contentCell1.setCellStyle(cellStyle);
						
						if (i==0 && kx==0) {
							byte[] imgBytes = BscReportSupportUtils.getByteIconBase(
									"OBJECTIVES", 
									objective.getTarget(), 
									objective.getMin(), 
									objective.getScore(), 
									"", 
									"", 
									0);
							if (null != imgBytes) {
								SimpleUtils.setCellPicture(wb, sh, imgBytes, contentCell1.getRowIndex(), contentCell1.getColumnIndex());
							}							
						}	
						
					}
					for (int i=0; i<itemCols; i++) {
						//String content = this.getKpisContent(kpi, managementMap, calculationMap);
						String content = this.getKpisContent(kpi, managementMap);
						XSSFCellStyle cellStyle = wb.createCellStyle();
						cellStyle.setFillForegroundColor( new XSSFColor(SimpleUtils.getColorRGB4POIColor(kpi.getBgColor())) );
						cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);	
						XSSFFont cellFont = wb.createFont();
						cellFont.setBold(false);
						cellFont.setColor( new XSSFColor(SimpleUtils.getColorRGB4POIColor(kpi.getFontColor())) );
						cellStyle.setFont(cellFont);
						cellStyle.setWrapText(true);
						cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
						cellStyle.setBorderBottom(BorderStyle.THIN);
						cellStyle.setBorderTop(BorderStyle.THIN);
						cellStyle.setBorderRight(BorderStyle.THIN);
						cellStyle.setBorderLeft(BorderStyle.THIN);
						Cell contentCell1 = contentRow.createCell(cell++);
						contentCell1.setCellValue( "\n" + content );
						contentCell1.setCellStyle(cellStyle);			
						
						if (i==0) {
							byte[] imgBytes = BscReportSupportUtils.getByteIconBase(
									"KPI", 
									kpi.getTarget(), 
									kpi.getMin(), 
									kpi.getScore(), 
									kpi.getCompareType(), 
									kpi.getManagement(), 
									kpi.getQuasiRange());
							if (null != imgBytes) {
								SimpleUtils.setCellPicture(wb, sh, imgBytes, contentCell1.getRowIndex(), contentCell1.getColumnIndex());
							}								
						}					
						
					}
					
				}
				
			}
			
		}
		
		for (int px=0; px<vision.getPerspectives().size(); px++) {
			PerspectiveVO perspective = vision.getPerspectives().get(px);
			sh.addMergedRegion(new CellRangeAddress(mrRow, mrRow + perspective.getRow()-1, 0, 3));
			
			for (int ox=0; ox<perspective.getObjectives().size(); ox++) {
				ObjectiveVO objective = perspective.getObjectives().get(ox);
				sh.addMergedRegion(new CellRangeAddress(mrRow, mrRow + objective.getRow()-1, 4, 7));
				
				for (int kx=0; kx<objective.getKpis().size(); kx++) {
					sh.addMergedRegion(new CellRangeAddress(mrRow+kx, mrRow+kx, 8, 11));
				}
				
				mrRow += objective.getKpis().size();
			}
			
		}
		
		return row++;
	}
	
	private int createDateRange(XSSFWorkbook wb, XSSFSheet sh, int row, VisionVO vision, Context context) throws Exception {
		String frequency = (String)context.get("frequency");
		String startYearDate = StringUtils.defaultString( (String)context.get("startYearDate") ).trim();
		String endYearDate = StringUtils.defaultString( (String)context.get("endYearDate") ).trim();
		String startDate = StringUtils.defaultString( (String)context.get("startDate") ).trim();
		String endDate = StringUtils.defaultString( (String)context.get("endDate") ).trim();
		String date1 = startDate;
		String date2 = endDate;		
		if (BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency)
				|| BscMeasureDataFrequency.FREQUENCY_YEAR.equals(frequency) ) {
			date1 = startYearDate + "/01/01";
			date2 = endYearDate + "/12/" + SimpleUtils.getMaxDayOfMonth(Integer.parseInt(endYearDate), 12);			
		}						
		Map<String, Object> headContentMap = new HashMap<String, Object>();
		this.fillHeadContent(context, headContentMap);
		
		XSSFCellStyle cellStyleLabel = wb.createCellStyle();
		cellStyleLabel.setFillForegroundColor( new XSSFColor(SimpleUtils.getColorRGB4POIColor(BscReportPropertyUtils.getBackgroundColor())) );
		cellStyleLabel.setFillPattern(FillPatternType.SOLID_FOREGROUND);	
		XSSFFont cellFontLabel = wb.createFont();
		cellFontLabel.setBold(false);
		cellFontLabel.setColor( new XSSFColor(SimpleUtils.getColorRGB4POIColor(BscReportPropertyUtils.getFontColor())) );
		cellStyleLabel.setFont(cellFontLabel);
		cellStyleLabel.setWrapText(true);
		//cellStyleLabel.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleLabel.setBorderBottom(BorderStyle.THIN);
		cellStyleLabel.setBorderTop(BorderStyle.THIN);
		cellStyleLabel.setBorderRight(BorderStyle.THIN);
		cellStyleLabel.setBorderLeft(BorderStyle.THIN);
		
		int cols = 4 + vision.getPerspectives().get(0).getObjectives().get(0).getKpis().get(0).getDateRangeScores().size();
		int cell = 0;
		for (int i=0; i<cols; i++) {
			String content = "Frequency: " + BscMeasureDataFrequency.getFrequencyMap(false).get(frequency) + 
					" Date range: " + date1 + " ~ " + date2 + "\n" +
					StringUtils.defaultString((String)headContentMap.get("headContent"));
			Row headRow = sh.createRow(row);
			headRow.setHeight( (short)700 );
			Cell headCell1 = headRow.createCell(cell);	
			headCell1.setCellValue( content );
			headCell1.setCellStyle(cellStyleLabel);						
		}
		
		sh.addMergedRegion( new CellRangeAddress(row, row, 0, cols-1) );	
		
		row++;
		
		int kpiCols = 4;
		int kpiRows = 2;
		for (PerspectiveVO perspective : vision.getPerspectives()) {
			for (ObjectiveVO objective : perspective.getObjectives()) {
				for (KpiVO kpi : objective.getKpis()) {
					cell = 0;
					
					for (int r=0; r<kpiRows; r++) {
						Row contentRow = sh.createRow(row++);
						contentRow.setHeight((short)400);		
						
						for (int c=0; c<kpiCols; c++) {							
							XSSFCellStyle cellStyle = wb.createCellStyle();
							cellStyle.setFillForegroundColor( new XSSFColor(SimpleUtils.getColorRGB4POIColor(kpi.getBgColor())) );
							cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);	
							XSSFFont cellFont = wb.createFont();
							cellFont.setBold(false);
							cellFont.setColor( new XSSFColor(SimpleUtils.getColorRGB4POIColor(kpi.getFontColor())) );
							cellStyle.setFont(cellFont);
							cellStyle.setWrapText(true);
							cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
							cellStyle.setBorderBottom(BorderStyle.THIN);
							cellStyle.setBorderTop(BorderStyle.THIN);
							cellStyle.setBorderRight(BorderStyle.THIN);
							cellStyle.setBorderLeft(BorderStyle.THIN);
							Cell contentCell1 = contentRow.createCell(c);
							contentCell1.setCellValue( kpi.getName() );
							contentCell1.setCellStyle(cellStyle);
							
						}
						
						cell = 4;
						if (r == 0) { // date
							
							for (int d=0; d<kpi.getDateRangeScores().size(); d++) {
								DateRangeScoreVO dateRangeScore = kpi.getDateRangeScores().get(d);
								XSSFCellStyle cellStyle = wb.createCellStyle();
								cellStyle.setFillForegroundColor( new XSSFColor(SimpleUtils.getColorRGB4POIColor(dateRangeScore.getBgColor())) );
								cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);	
								XSSFFont cellFont = wb.createFont();
								cellFont.setBold(false);
								cellFont.setColor( new XSSFColor(SimpleUtils.getColorRGB4POIColor(dateRangeScore.getFontColor())) );
								cellStyle.setFont(cellFont);
								cellStyle.setWrapText(true);
								cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
								cellStyle.setBorderBottom(BorderStyle.THIN);
								cellStyle.setBorderTop(BorderStyle.THIN);
								cellStyle.setBorderRight(BorderStyle.THIN);
								cellStyle.setBorderLeft(BorderStyle.THIN);
								Cell contentCell1 = contentRow.createCell(cell++);
								contentCell1.setCellValue( dateRangeScore.getDate() );
								contentCell1.setCellStyle(cellStyle);								
							}
														
						}
						if (r == 1) { // score

							for (int d=0; d<kpi.getDateRangeScores().size(); d++) {
								DateRangeScoreVO dateRangeScore = kpi.getDateRangeScores().get(d);
								XSSFCellStyle cellStyle = wb.createCellStyle();
								cellStyle.setFillForegroundColor( new XSSFColor(SimpleUtils.getColorRGB4POIColor(dateRangeScore.getBgColor())) );
								cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);	
								XSSFFont cellFont = wb.createFont();
								cellFont.setBold(false);
								cellFont.setColor( new XSSFColor(SimpleUtils.getColorRGB4POIColor(dateRangeScore.getFontColor())) );
								cellStyle.setFont(cellFont);
								cellStyle.setWrapText(true);
								cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
								cellStyle.setBorderBottom(BorderStyle.THIN);
								cellStyle.setBorderTop(BorderStyle.THIN);
								cellStyle.setBorderRight(BorderStyle.THIN);
								cellStyle.setBorderLeft(BorderStyle.THIN);
								Cell contentCell1 = contentRow.createCell(cell++);
								contentCell1.setCellValue( "      " + BscReportSupportUtils.parse2(dateRangeScore.getScore()) );
								contentCell1.setCellStyle(cellStyle);		
								
								byte[] imgBytes = BscReportSupportUtils.getByteIcon(kpi, dateRangeScore.getScore());
								if (null != imgBytes) {
									SimpleUtils.setCellPicture(wb, sh, imgBytes, contentCell1.getRowIndex(), contentCell1.getColumnIndex());
								}
								
							}
							
						}
						
					}
										
					sh.addMergedRegion( new CellRangeAddress(row-2, row-1, 0, kpiCols-1) );
										
				}
			}
		}
		
		return row++;
	}
	
	private void fillHeadContent(Context context, Map<String, Object> parameter) throws ServiceException, Exception {
		String headContent = "";
		String orgId = (String)context.get("orgId");
		String empId = (String)context.get("empId");
		String account = (String)context.get("account");
		if (!BscConstants.MEASURE_DATA_ORGANIZATION_FULL.equals(orgId) && !StringUtils.isBlank(orgId) ) {
			OrganizationVO organization = new OrganizationVO();
			organization.setOrgId(orgId);
			DefaultResult<OrganizationVO> result = this.organizationService.findByUK(organization);
			if (result.getValue()!=null) {
				organization = result.getValue();
				headContent += "\nMeasure data for: " 
						+ organization.getOrgId() + " - " + organization.getName();
			}
		}
		if (!BscConstants.MEASURE_DATA_EMPLOYEE_FULL.equals(empId) 
				&& !StringUtils.isBlank(empId) && !StringUtils.isBlank(account) ) {
			EmployeeVO employee = new EmployeeVO();
			employee.setEmpId(empId);
			employee.setAccount(account);
			DefaultResult<EmployeeVO> result = this.employeeService.findByUK(employee);
			if (result.getValue()!=null) {
				employee = result.getValue();
				headContent += "\nMeasure data for: " 
						+ employee.getEmpId() + " - " + employee.getFullName();
				if (!StringUtils.isBlank(employee.getJobTitle())) {
					headContent += " ( " + employee.getJobTitle() + " ) ";
				}
			}
		}		
		parameter.put("headContent", headContent);
	}	
	
	private String getItemsContent(String name, float score, BigDecimal weight, float target, float min) {
		String str = "";
		str = name + "\n" + BscReportPropertyUtils.getScoreLabel() + " " + BscReportSupportUtils.parse2(score) + "\n" + BscReportPropertyUtils.getWeightLabel() + " " + weight.toString() + "%" + "\n" +
				BscReportPropertyUtils.getTargetLabel() + " " + target + "\n" + BscReportPropertyUtils.getMinLabel() + " " + min;
		return str;
	}
	
	private String getItemsContent(String name, float score, BigDecimal weight, float max, float target, float min) {
		String str = "";
		str = name + "\n" + BscReportPropertyUtils.getScoreLabel() + " " + BscReportSupportUtils.parse2(score) + "\n" + BscReportPropertyUtils.getWeightLabel() + " " + weight.toString() + "%" + "\n" +
				BscReportPropertyUtils.getMaxLabel() + " " + max + "\n" + BscReportPropertyUtils.getTargetLabel() + " " + target + "\n" + BscReportPropertyUtils.getMinLabel() + " " + min;
		return str;
	}	
	
	/*
	private String getKpisContent(KpiVO kpi, Map<String, String> managementMap, Map<String, String> calculationMap) {
		String str = this.getItemsContent(kpi.getName(), kpi.getScore(), kpi.getWeight(), kpi.getTarget(), kpi.getMin());
		str += "\n" + "management: " + managementMap.get(kpi.getManagement()) + "\n" + 
				"Calculation: " + calculationMap.get(kpi.getCal()) + "\n" + 
				"Unit: " + kpi.getUnit() + "\n" + 
				"Formula: " + kpi.getFormula().getName() + "\n" +
				StringUtils.defaultString( kpi.getDescription() );
		return str;
	}
	*/

	private String getKpisContent(KpiVO kpi, Map<String, String> managementMap) throws Exception {
		String str = this.getItemsContent(kpi.getName(), kpi.getScore(), kpi.getWeight(), kpi.getMax(), kpi.getTarget(), kpi.getMin());
		str += "\n" + BscReportPropertyUtils.getManagementLabel() + " " + managementMap.get(kpi.getManagement()) + "\n" + 
				BscReportPropertyUtils.getCalculationLabel() + " " + AggregationMethodUtils.getNameByAggrId(kpi.getCal()) + "\n" + 
				BscReportPropertyUtils.getUnitLabel() + " " + kpi.getUnit() + "\n" + 
				BscReportPropertyUtils.getFormulaLabel() + " " + kpi.getFormula().getName() + "\n" +
				StringUtils.defaultString( kpi.getDescription() );
		return str;
	}	
	
}
