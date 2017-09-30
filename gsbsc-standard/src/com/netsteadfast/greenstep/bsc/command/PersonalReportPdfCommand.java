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
import java.io.OutputStream;
import java.util.List;

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

public class PersonalReportPdfCommand extends BaseChainCommandSupport implements Command {
	private static final int MAX_COLSPAN = 6;
	private IOrganizationService<OrganizationVO, BbOrganization, String> organizationService;
	private IEmployeeService<EmployeeVO, BbEmployee, String> employeeService;
	
	@SuppressWarnings("unchecked")
	public PersonalReportPdfCommand() {
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
		String visionOid = (String)context.get("visionOid");
		VisionVO vision = null;
		BscStructTreeObj treeObj = (BscStructTreeObj)this.getResult(context);
		for (VisionVO visionObj : treeObj.getVisions()) {
			if (visionObj.getOid().equals(visionOid)) {
				vision = visionObj;
			}
		}		
		FontFactory.register(BscConstants.PDF_ITEXT_FONT);
		String fileName = SimpleUtils.getUUIDStr() + ".pdf";
		String fileFullPath = Constants.getWorkTmpDir() + "/" + fileName;
		OutputStream os = new FileOutputStream(fileFullPath);
		Document document = new Document(PageSize.A4.rotate(), 10, 10, 10, 10);	
		document.left(100f);
		document.top(150f);
		PdfWriter writer = PdfWriter.getInstance(document, os);
		document.open();  
		
		PdfPTable table = new PdfPTable(MAX_COLSPAN);
		table.setWidthPercentage(100f);	
		PdfPTable signTable = new PdfPTable( 1 );
		signTable.setWidthPercentage(100f);
		
		this.createHead(table, vision, context);
		this.createBody(table, vision);
		this.createFoot(table, context);
		this.putSignature(signTable, context);
		
		document.add(table);
		document.add(signTable);
		document.close();
		writer.close();			
		
		os.flush();
		os.close();
		os = null;
		
		File file = new File(fileFullPath);
		String oid = UploadSupportUtils.create(
				Constants.getSystem(), UploadTypes.IS_TEMP, false, file, "personal-report.pdf");
		file = null;
		return oid;
	}
	
	private void createHead(PdfPTable table, VisionVO vision, Context context) throws Exception {
		String bgColor = "#F2F2F2";
		String fnColor = "#000000";
		PdfPCell cell = new PdfPCell();
		cell.addElement( new Phrase("Personal Balance SourceCard", this.getFont(fnColor, true)) );
		this.setCellBackgroundColor(cell, bgColor);
		cell.setColspan(MAX_COLSPAN);
		table.addCell(cell);			
		
		cell = new PdfPCell();
		cell.addElement( new Phrase(vision.getTitle(), this.getFont(fnColor, true)) );
		this.setCellBackgroundColor(cell, bgColor);
		cell.setColspan(MAX_COLSPAN);
		table.addCell(cell);	
		
		
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
		
		cell = new PdfPCell();
		cell.addElement( new Phrase("Job Title", this.getFont(fnColor, true)) );
		this.setCellBackgroundColor(cell, bgColor);
		cell.setColspan(1);
		table.addCell(cell);		
		
		cell = new PdfPCell();
		cell.addElement( new Phrase(jobTitle, this.getFont(fnColor, true)) );
		this.setCellBackgroundColor(cell, bgColor);
		cell.setColspan(1);
		table.addCell(cell);	
		
		cell = new PdfPCell();
		cell.addElement( new Phrase("Department", this.getFont(fnColor, true)) );
		this.setCellBackgroundColor(cell, bgColor);
		cell.setColspan(1);
		table.addCell(cell);
		
		cell = new PdfPCell();
		cell.addElement( new Phrase(departmentName, this.getFont(fnColor, true)) );
		this.setCellBackgroundColor(cell, bgColor);
		cell.setColspan(1);
		table.addCell(cell);
		
		cell = new PdfPCell();
		cell.addElement( new Phrase("name: " + fullName, this.getFont(fnColor, true)) );
		this.setCellBackgroundColor(cell, bgColor);
		cell.setColspan(1);
		table.addCell(cell);
		
		cell = new PdfPCell();
		cell.addElement( new Phrase("Annual assessment: " + year, this.getFont(fnColor, true)) );
		this.setCellBackgroundColor(cell, bgColor);
		cell.setColspan(1);
		table.addCell(cell);
		
		
		cell = new PdfPCell();
		cell.addElement( new Phrase(BscReportPropertyUtils.getObjectiveTitle(), this.getFont(fnColor, true)) );
		this.setCellBackgroundColor(cell, bgColor);
		cell.setColspan(1);
		cell.setRowspan(2);
		table.addCell(cell);			
		
		cell = new PdfPCell();
		cell.addElement( new Phrase(BscReportPropertyUtils.getKpiTitle(), this.getFont(fnColor, true)) );
		this.setCellBackgroundColor(cell, bgColor);
		cell.setColspan(1);
		cell.setRowspan(2);
		table.addCell(cell);	
		
		cell = new PdfPCell();
		cell.addElement( new Phrase("Maximum\nTarget\nMinimum", this.getFont(fnColor, true)) );
		this.setCellBackgroundColor(cell, bgColor);
		cell.setColspan(1);
		cell.setRowspan(2);
		table.addCell(cell);			
		
		cell = new PdfPCell();
		cell.addElement( new Phrase("Weight", this.getFont(fnColor, true)) );
		this.setCellBackgroundColor(cell, bgColor);
		cell.setColspan(1);
		cell.setRowspan(2);
		table.addCell(cell);
		
		cell = new PdfPCell();
		cell.addElement( new Phrase("Formula", this.getFont(fnColor, true)) );
		this.setCellBackgroundColor(cell, bgColor);
		cell.setColspan(1);
		cell.setRowspan(2);
		table.addCell(cell);
				
		cell = new PdfPCell();
		cell.addElement( new Phrase("Score", this.getFont(fnColor, true)) );
		this.setCellBackgroundColor(cell, bgColor);
		cell.setColspan(1);
		cell.setRowspan(1);
		table.addCell(cell);
		
		cell = new PdfPCell();
		cell.addElement( new Phrase(dateTypeName, this.getFont(fnColor, true)) );
		this.setCellBackgroundColor(cell, bgColor);
		cell.setColspan(1);
		cell.setRowspan(1);
		table.addCell(cell);
		
	}
	
	private void createBody(PdfPTable table, VisionVO vision) throws Exception {
		
		String bgColor = "#ffffff";
		String fnColor = "#000000";
		for (PerspectiveVO perspective : vision.getPerspectives()) {
			for (ObjectiveVO objective : perspective.getObjectives()) {
				int kx = 0;
				for (KpiVO kpi : objective.getKpis()) {
					PdfPCell cell = new PdfPCell();
					if (kx == 0) {						
						cell.addElement( new Phrase(objective.getName(), this.getFont(fnColor, true)) );
						this.setCellBackgroundColor(cell, bgColor);
						cell.setColspan(1);
						cell.setRowspan(objective.getRow());
						table.addCell(cell);						
					}		
					
					cell = new PdfPCell();
					cell.addElement( new Phrase(kpi.getName(), this.getFont(fnColor, true)) );
					this.setCellBackgroundColor(cell, bgColor);
					cell.setColspan(1);
					table.addCell(cell);	
					
					cell = new PdfPCell();
					cell.addElement( new Phrase("max: " + kpi.getMax() + "\n" +
							"target: " + kpi.getTarget() + "\n" +
							"min: " + kpi.getMin() + "\n" + 
							"unit: " + kpi.getUnit(), this.getFont(fnColor, true)) );
					this.setCellBackgroundColor(cell, bgColor);
					cell.setColspan(1);
					table.addCell(cell);			
					
					cell = new PdfPCell();
					cell.addElement( new Phrase(kpi.getWeight() + "%", this.getFont(fnColor, true)) );
					this.setCellBackgroundColor(cell, bgColor);
					cell.setColspan(1);
					table.addCell(cell);
					
					cell = new PdfPCell();
					cell.addElement( new Phrase(kpi.getFormula().getName(), this.getFont(fnColor, true)) );
					this.setCellBackgroundColor(cell, bgColor);
					cell.setColspan(1);
					table.addCell(cell);
							
					DateRangeScoreVO dateRangeScore = kpi.getDateRangeScores().get(0); // 只顯示一筆日期分數資料
					
					cell = new PdfPCell();
					cell.addElement( new Phrase( BscReportSupportUtils.parse2(dateRangeScore.getScore()), 
							this.getFont( dateRangeScore.getFontColor() , true)) );
					this.setCellBackgroundColor(cell, dateRangeScore.getBgColor() );
					cell.setColspan(1);
					table.addCell(cell);					
					
					kx++;
				}
			}
		}
		
	}
	
	private void createFoot(PdfPTable table, Context context) throws Exception {
		String bgColor = "#ffffff";
		String fnColor = "#000000";		
		PdfPCell cell = new PdfPCell();
		cell.addElement( new Phrase("assess:", this.getFont(fnColor, true)) );
		cell.setColspan(1);
		cell.setRowspan(2);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		this.setCellBackgroundColor(cell, bgColor);
		table.addCell(cell); 		
		
		cell = new PdfPCell();
		cell.addElement( new Phrase( BscReportPropertyUtils.getPersonalReportClassLevel() , this.getFont(fnColor, true)) );
		cell.setColspan(3);
		cell.setRowspan(2);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		this.setCellBackgroundColor(cell, bgColor);
		table.addCell(cell);		
		
		cell = new PdfPCell();
		cell.addElement( new Phrase("Total", this.getFont(fnColor, true)) );
		cell.setColspan(1);
		cell.setRowspan(1);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		this.setCellBackgroundColor(cell, bgColor);
		table.addCell(cell);			
		
		float total = 0.0f;
		if ( context.get("total")!=null && context.get("total") instanceof Float ) {
			total = (Float)context.get("total");
		}		
		
		cell = new PdfPCell();
		cell.addElement( new Phrase( BscReportSupportUtils.parse2(total), this.getFont(fnColor, true)) );
		cell.setColspan(1);
		cell.setRowspan(1);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		this.setCellBackgroundColor(cell, bgColor);
		table.addCell(cell);
		
		cell = new PdfPCell();
		cell.addElement( new Phrase("Class", this.getFont(fnColor, true)) );
		cell.setColspan(1);
		cell.setRowspan(1);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		this.setCellBackgroundColor(cell, bgColor);
		table.addCell(cell);				
		
		cell = new PdfPCell();
		cell.addElement( new Phrase(" ", this.getFont(fnColor, true)) );
		cell.setColspan(1);
		cell.setRowspan(1);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		this.setCellBackgroundColor(cell, bgColor);
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
		font.setColor(baseColor);
		return font;
	}

	private void setCellBackgroundColor(PdfPCell cell, String color) throws Exception {
		int rgb[] = SimpleUtils.getColorRGB2(color);
		cell.setBackgroundColor(new BaseColor(rgb[0], rgb[1], rgb[2]));
	}		

}
