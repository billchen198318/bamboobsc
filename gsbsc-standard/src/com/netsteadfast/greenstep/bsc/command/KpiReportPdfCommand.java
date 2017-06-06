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
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.lang3.StringUtils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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

public class KpiReportPdfCommand extends BaseChainCommandSupport implements Command {
	private final int MAX_COLSPAN = 3;
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;	
	
	@SuppressWarnings("unchecked")
	public KpiReportPdfCommand() {
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
		String uploadOid = this.createPdf(context);
		this.setResult(context, uploadOid);
		return false;
	}
	
	private String createPdf(Context context) throws Exception {
		BscReportPropertyUtils.loadData();
		BscReportSupportUtils.loadExpression(); // 2015-04-18 add
		String visionOid = (String)context.get("visionOid");
		VisionVO vision = null;
		BscStructTreeObj treeObj = (BscStructTreeObj)this.getResult(context);
		for (VisionVO visionObj : treeObj.getVisions()) {
			if (visionObj.getOid().equals(visionOid)) {
				vision = visionObj;
			}
		}		
		FontFactory.register(BscConstants.PDF_ITEXT_FONT);
		String fileName = UUID.randomUUID().toString() + ".pdf";
		String fileFullPath = Constants.getWorkTmpDir() + "/" + fileName;
		OutputStream os = new FileOutputStream(fileFullPath);
		//Document document = new Document(PageSize.A4.rotate(), 10, 10, 10, 10);
		Document document = new Document(PageSize.A4, 10, 10, 10, 10);		
		document.left(100f);
		document.top(150f);
		PdfWriter writer = PdfWriter.getInstance(document, os);
		document.open();  
		
		int dateRangeRows = 4 + vision.getPerspectives().get(0).getObjectives().get(0).getKpis().get(0).getDateRangeScores().size();
		PdfPTable table = new PdfPTable(MAX_COLSPAN);
		PdfPTable dateRangeTable = new PdfPTable( dateRangeRows );
		PdfPTable chartsTable = new PdfPTable( 2 );
		PdfPTable signTable = new PdfPTable( 1 );
		table.setWidthPercentage(100f);	
		dateRangeTable.setWidthPercentage(100f);
		chartsTable.setWidthPercentage(100f);
		signTable.setWidthPercentage(100f);
		
		this.createHead(table, vision);
		this.createBody(table, vision);
		this.createDateRange(dateRangeTable, vision, context, dateRangeRows);		
		this.putCharts(chartsTable, context);
		this.putSignature(signTable, context);
		
		document.add(chartsTable);
		document.add(table);  
		document.add(dateRangeTable);  
		document.add(signTable);
		document.close();
		writer.close();			
		
		os.flush();
		os.close();
		os = null;
		
		File file = new File(fileFullPath);
		String oid = UploadSupportUtils.create(
				Constants.getSystem(), UploadTypes.IS_TEMP, false, file, "kpi-report.pdf");
		file = null;
		return oid;
	}
	
	private void createHead(PdfPTable table, VisionVO vision) throws Exception {
		PdfPCell cell = new PdfPCell();
		cell.addElement( new Phrase(vision.getTitle() + "\n" + BscReportSupportUtils.parse2( vision.getScore() ), this.getFont(vision.getFontColor(), true)) );
		this.setCellBackgroundColor(cell, vision.getBgColor());
		cell.setColspan(MAX_COLSPAN);
		table.addCell(cell);		
		
		cell = new PdfPCell();
		cell.addElement( new Phrase(BscReportPropertyUtils.getPerspectiveTitle(), this.getFont(BscReportPropertyUtils.getFontColor(), true)) );
		this.setCellBackgroundColor(cell, BscReportPropertyUtils.getBackgroundColor());
		table.addCell(cell);		
		
		cell = new PdfPCell();
		cell.addElement( new Phrase(BscReportPropertyUtils.getObjectiveTitle(), this.getFont(BscReportPropertyUtils.getFontColor(), true)) );
		this.setCellBackgroundColor(cell, BscReportPropertyUtils.getBackgroundColor());
		table.addCell(cell);	
		
		cell = new PdfPCell();
		cell.addElement( new Phrase(BscReportPropertyUtils.getKpiTitle(), this.getFont(BscReportPropertyUtils.getFontColor(), true)) );
		this.setCellBackgroundColor(cell, BscReportPropertyUtils.getBackgroundColor());
		table.addCell(cell);					
	}
	
	private void createBody(PdfPTable table, VisionVO vision) throws Exception {
		Map<String, String> managementMap = BscKpiCode.getManagementMap(false);
		//Map<String, String> calculationMap = BscKpiCode.getCalculationMap(false);		
		PdfPCell cell = null;
		for (PerspectiveVO perspective : vision.getPerspectives()) {
			
			Image pImage = Image.getInstance( 
					BscReportSupportUtils.getByteIconBase(
							"PERSPECTIVES", 
							perspective.getTarget(), 
							perspective.getMin(), 
							perspective.getScore(), 
							"", 
							"", 
							0) );
			pImage.setWidthPercentage(10f);			
			
			String content = this.getItemsContent(
					perspective.getName(), 
					perspective.getScore(), 
					perspective.getWeight(), 
					perspective.getTarget(), 
					perspective.getMin());
			cell = new PdfPCell();
			cell.addElement(pImage);
			cell.addElement( new Phrase("\n"+content, this.getFont(perspective.getFontColor(), false)) );
			this.setCellBackgroundColor(cell, perspective.getBgColor());
			cell.setRowspan(perspective.getRow());
			table.addCell(cell);				
			
			for (ObjectiveVO objective : perspective.getObjectives()) {
				
				Image oImage = Image.getInstance( 
						BscReportSupportUtils.getByteIconBase(
								"OBJECTIVES", 
								objective.getTarget(), 
								objective.getMin(), 
								objective.getScore(), 
								"", 
								"", 
								0) );
				oImage.setWidthPercentage(10f);					
				
				content = this.getItemsContent(
						objective.getName(), 
						objective.getScore(), 
						objective.getWeight(), 
						objective.getTarget(), 
						objective.getMin());				
				cell = new PdfPCell();
				cell.addElement(oImage);
				cell.addElement( new Phrase("\n"+content, this.getFont(objective.getFontColor(), false)) );
				this.setCellBackgroundColor(cell, objective.getBgColor());
				cell.setRowspan(objective.getRow());
				table.addCell(cell);	
				
				for (KpiVO kpi : objective.getKpis()) {
					/*
					content = this.getKpisContent(
							kpi, 
							managementMap, 
							calculationMap);	
					*/
					
					Image kImage = Image.getInstance( 
							BscReportSupportUtils.getByteIconBase(
									"KPI", 
									kpi.getTarget(), 
									kpi.getMin(), 
									kpi.getScore(), 
									kpi.getCompareType(), 
									kpi.getManagement(), 
									kpi.getQuasiRange()) );
					kImage.setWidthPercentage(10f);						
					
					content = this.getKpisContent(
							kpi, 
							managementMap);						
					cell = new PdfPCell();
					cell.addElement(kImage);
					cell.addElement( new Phrase("\n"+content, this.getFont(kpi.getFontColor(), false)) );
					this.setCellBackgroundColor(cell, kpi.getBgColor());					
					table.addCell(cell);					
				}
				
			}
			
		}
		
	}
	
	private void createDateRange(PdfPTable table, VisionVO vision, Context context, int maxRows) throws Exception {
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
		String content = "Frequency: " + BscMeasureDataFrequency.getFrequencyMap(false).get(frequency) + 
				" Date range: " + date1 + " ~ " + date2 + "\n" +
				StringUtils.defaultString((String)headContentMap.get("headContent"));
		
		PdfPCell cell = null;
		
		cell = new PdfPCell();
		cell.addElement( new Phrase(content, this.getFont(BscReportPropertyUtils.getFontColor(), false)) );
		this.setCellBackgroundColor(cell, BscReportPropertyUtils.getBackgroundColor());
		cell.setColspan(maxRows);
		table.addCell(cell);				
		
		
		// Vision date range , 2017-06-06 add
		cell = new PdfPCell();
		cell.addElement( new Phrase("Vision date range", this.getFont(BscReportPropertyUtils.getFontColor(), false)) );
		this.setCellBackgroundColor(cell, BscReportPropertyUtils.getBackgroundColor());
		cell.setColspan(maxRows);
		table.addCell(cell);
		cell = new PdfPCell();
		cell.addElement( new Phrase(vision.getTitle(), this.getFont(vision.getFontColor(), false)) );
		this.setCellBackgroundColor(cell, vision.getBgColor());
		cell.setColspan(4);
		cell.setRowspan(2);
		table.addCell(cell);
		for (DateRangeScoreVO dateScore : vision.getDateRangeScores()) {
			cell = new PdfPCell();
			cell.addElement( new Phrase(dateScore.getDate(), this.getFont(dateScore.getFontColor(), false)) );
			this.setCellBackgroundColor(cell, dateScore.getBgColor());
			table.addCell(cell);							
		}
		for (DateRangeScoreVO dateScore : vision.getDateRangeScores()) {
			cell = new PdfPCell();
			cell.addElement( new Phrase( BscReportSupportUtils.parse2(dateScore.getScore()), this.getFont(dateScore.getFontColor(), false)) );
			this.setCellBackgroundColor(cell, dateScore.getBgColor());
			table.addCell(cell);								
		}
		
		
		// Perspective date range , 2017-06-06 add
		cell = new PdfPCell();
		cell.addElement( new Phrase( BscReportPropertyUtils.getPerspectiveTitle() + " date range", this.getFont(BscReportPropertyUtils.getFontColor(), false)) );
		this.setCellBackgroundColor(cell, BscReportPropertyUtils.getBackgroundColor());
		cell.setColspan(maxRows);
		table.addCell(cell);
		for (PerspectiveVO perspective : vision.getPerspectives()) {
			cell = new PdfPCell();
			cell.addElement( new Phrase(perspective.getName(), this.getFont(perspective.getFontColor(), false)) );
			this.setCellBackgroundColor(cell, perspective.getBgColor());
			cell.setColspan(4);
			cell.setRowspan(2);
			table.addCell(cell);
			for (DateRangeScoreVO dateScore : perspective.getDateRangeScores()) {
				cell = new PdfPCell();
				cell.addElement( new Phrase(dateScore.getDate(), this.getFont(dateScore.getFontColor(), false)) );
				this.setCellBackgroundColor(cell, dateScore.getBgColor());
				table.addCell(cell);							
			}
			for (DateRangeScoreVO dateScore : perspective.getDateRangeScores()) {
				cell = new PdfPCell();
				cell.addElement( new Phrase( BscReportSupportUtils.parse2(dateScore.getScore()), this.getFont(dateScore.getFontColor(), false)) );
				this.setCellBackgroundColor(cell, dateScore.getBgColor());
				table.addCell(cell);								
			}			
		}
		
		
		// Strategy objectives date range , 2017-06-06 add
		cell = new PdfPCell();
		cell.addElement( new Phrase( BscReportPropertyUtils.getObjectiveTitle() + " date range", this.getFont(BscReportPropertyUtils.getFontColor(), false)) );
		this.setCellBackgroundColor(cell, BscReportPropertyUtils.getBackgroundColor());
		cell.setColspan(maxRows);
		table.addCell(cell);		
		for (PerspectiveVO perspective : vision.getPerspectives()) {
			for (ObjectiveVO objective : perspective.getObjectives()) {
				cell = new PdfPCell();
				cell.addElement( new Phrase(objective.getName(), this.getFont(objective.getFontColor(), false)) );
				this.setCellBackgroundColor(cell, objective.getBgColor());
				cell.setColspan(4);
				cell.setRowspan(2);
				table.addCell(cell);
				for (DateRangeScoreVO dateScore : objective.getDateRangeScores()) {
					cell = new PdfPCell();
					cell.addElement( new Phrase(dateScore.getDate(), this.getFont(dateScore.getFontColor(), false)) );
					this.setCellBackgroundColor(cell, dateScore.getBgColor());
					table.addCell(cell);							
				}
				for (DateRangeScoreVO dateScore : objective.getDateRangeScores()) {
					cell = new PdfPCell();
					cell.addElement( new Phrase( BscReportSupportUtils.parse2(dateScore.getScore()), this.getFont(dateScore.getFontColor(), false)) );
					this.setCellBackgroundColor(cell, dateScore.getBgColor());
					table.addCell(cell);								
				}				
			}
		}
		
		
		// KPIs date range , 2017-06-06 add title label
		cell = new PdfPCell();
		cell.addElement( new Phrase( BscReportPropertyUtils.getKpiTitle() + " date range", this.getFont(BscReportPropertyUtils.getFontColor(), false)) );
		this.setCellBackgroundColor(cell, BscReportPropertyUtils.getBackgroundColor());
		cell.setColspan(maxRows);
		table.addCell(cell);		
		// KPIs date range
		for (PerspectiveVO perspective : vision.getPerspectives()) {
			for (ObjectiveVO objective : perspective.getObjectives()) {
				for (KpiVO kpi : objective.getKpis()) {
					cell = new PdfPCell();
					cell.addElement( new Phrase(kpi.getName(), this.getFont(kpi.getFontColor(), false)) );
					this.setCellBackgroundColor(cell, kpi.getBgColor());
					cell.setColspan(4);
					cell.setRowspan(2);
					table.addCell(cell);						
					
					for (DateRangeScoreVO dateScore : kpi.getDateRangeScores()) {
						cell = new PdfPCell();
						cell.addElement( new Phrase(dateScore.getDate(), this.getFont(dateScore.getFontColor(), false)) );
						this.setCellBackgroundColor(cell, dateScore.getBgColor());
						table.addCell(cell);							
					}
					for (DateRangeScoreVO dateScore : kpi.getDateRangeScores()) {
						Image image = Image.getInstance( BscReportSupportUtils.getByteIcon(kpi, dateScore.getScore()) );
						image.setWidthPercentage(20f);
						cell = new PdfPCell();
						cell.addElement( new Phrase( BscReportSupportUtils.parse2(dateScore.getScore()), this.getFont(dateScore.getFontColor(), false)) );
						cell.addElement(image);
						this.setCellBackgroundColor(cell, dateScore.getBgColor());
						table.addCell(cell);								
					}
					
				}
			}
		}
		
		
	}
	
	private void putCharts(PdfPTable table, Context context) throws Exception {
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
		
		PdfPCell cell =null;
		
		Image pieImgObj = Image.getInstance( pieBos.toByteArray() );
		pieImgObj.setWidthPercentage(100f);
		cell = new PdfPCell();
		cell.setBorder( Rectangle.NO_BORDER );
		cell.addElement(pieImgObj);
		table.addCell(cell);
		
		Image barImgObj = Image.getInstance( barBos.toByteArray() );
		barImgObj.setWidthPercentage(100f);
		cell = new PdfPCell();
		cell.setBorder( Rectangle.NO_BORDER );
		cell.addElement(barImgObj);
		table.addCell(cell);		
		
	}
	
	private void putSignature(PdfPTable table, Context context) throws Exception {
		String uploadOid = (String)context.get("uploadSignatureOid");
		if ( StringUtils.isBlank(uploadOid) ) {
			return;
		}
		byte[] imageBytes = UploadSupportUtils.getDataBytes( uploadOid );
		if ( null == imageBytes ) {
			return;
		}
		Image signatureImgObj = Image.getInstance( imageBytes );
		signatureImgObj.setWidthPercentage(40f);
		PdfPCell cell = new PdfPCell();
		cell.setBorder( Rectangle.NO_BORDER );
		cell.addElement(signatureImgObj);
		table.addCell(cell);		
	}
	
	private Font getFont(String color, boolean bold) throws Exception {
		Font font = FontFactory.getFont(BscConstants.PDF_ITEXT_FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		int rgb[] = SimpleUtils.getColorRGB2(color);
		BaseColor baseColor = new BaseColor(rgb[0], rgb[1], rgb[2]);
		font.setSize(9);
		if (bold) {
			font.setSize(14);
			font.setStyle(Font.BOLD);
		}		
		font.setColor(baseColor);
		return font;
	}

	private void setCellBackgroundColor(PdfPCell cell, String color) throws Exception {
		int rgb[] = SimpleUtils.getColorRGB2(color);
		cell.setBackgroundColor(new BaseColor(rgb[0], rgb[1], rgb[2]));
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
