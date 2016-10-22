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
package com.netsteadfast.greenstep.vo;

import java.util.ArrayList;
import java.util.List;

import com.netsteadfast.greenstep.base.model.BaseValueObj;

public class PdcaVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = -7203090330513353633L;
	private String oid;
	private String title;
	private String description;
	private String startDate;
	private String endDate;
	private String parentOid;
	private String confirmDate;
	private String confirmFlag;
	private String confirmEmpId;
	
	// ------------------------------------------------------
	// report need
	private String responsibilityAppendNames = "";
	private String confirmEmployeeName = "";
	private String organizationAppendNames = "";
	private String kpisAppendNames = "";
	private String parentName = "";
	private List<PdcaDocVO> docs = new ArrayList<PdcaDocVO>();
	private List<PdcaItemVO> itemPlan = null;
	private List<PdcaItemVO> itemDo = null;
	private List<PdcaItemVO> itemCheck = null;
	private List<PdcaItemVO> itemAction = null;
	private PdcaAuditVO auditPlan = null;
	private PdcaAuditVO auditDo = null;
	private PdcaAuditVO auditCheck = null;
	private PdcaAuditVO auditAction = null;
	
	public PdcaVO() {
		
	}
	
	public PdcaVO(String oid, String title, String startDate, String endDate, String parentOid, String confirmDate,
			String confirmFlag, String confirmEmpId) {
		super();
		this.oid = oid;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.parentOid = parentOid;
		this.confirmDate = confirmDate;
		this.confirmFlag = confirmFlag;
		this.confirmEmpId = confirmEmpId;
	}

	public PdcaVO(String oid, String title, String description, String startDate, String endDate, String parentOid,
			String confirmDate, String confirmFlag, String confirmEmpId) {
		super();
		this.oid = oid;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.parentOid = parentOid;
		this.confirmDate = confirmDate;
		this.confirmFlag = confirmFlag;
		this.confirmEmpId = confirmEmpId;
	}
	
	public String getStartDateDisplayValue() {
		return super.getDateDisplayValue(this.startDate, "/");
	}
	
	public String getEndDateDisplayValue() {
		return super.getDateDisplayValue(this.endDate, "/");
	}
	
	public String getConfirmDateDisplayValue() {
		return super.getDateDisplayValue(this.confirmDate, "/");
	}
	
	public String getStartDateTextBoxValue() {
		return super.getDateDisplayValue(this.startDate, "-");
	}
	
	public String getEndDateTextBoxValue() {
		return super.getDateDisplayValue(this.endDate, "-");
	}
	
	public String getConfirmDateTextBoxValue() {
		return super.getDateDisplayValue(this.confirmDate, "-");
	}	
	
	@Override
	public String getOid() {
		return oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getParentOid() {
		return parentOid;
	}

	public void setParentOid(String parentOid) {
		this.parentOid = parentOid;
	}

	public String getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getConfirmFlag() {
		return confirmFlag;
	}

	public void setConfirmFlag(String confirmFlag) {
		this.confirmFlag = confirmFlag;
	}

	public String getConfirmEmpId() {
		return confirmEmpId;
	}

	public void setConfirmEmpId(String confirmEmpId) {
		this.confirmEmpId = confirmEmpId;
	}
	
	// ------------------------------------------------------
	// report need
	
	public String getResponsibilityAppendNames() {
		return responsibilityAppendNames;
	}

	public void setResponsibilityAppendNames(String responsibilityAppendNames) {
		this.responsibilityAppendNames = responsibilityAppendNames;
	}

	public String getConfirmEmployeeName() {
		return confirmEmployeeName;
	}

	public void setConfirmEmployeeName(String confirmEmployeeName) {
		this.confirmEmployeeName = confirmEmployeeName;
	}

	public String getOrganizationAppendNames() {
		return organizationAppendNames;
	}

	public void setOrganizationAppendNames(String organizationAppendNames) {
		this.organizationAppendNames = organizationAppendNames;
	}

	public String getKpisAppendNames() {
		return kpisAppendNames;
	}

	public void setKpisAppendNames(String kpisAppendNames) {
		this.kpisAppendNames = kpisAppendNames;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public List<PdcaDocVO> getDocs() {
		return docs;
	}

	public void setDocs(List<PdcaDocVO> docs) {
		this.docs = docs;
	}

	public List<PdcaItemVO> getItemPlan() {
		return itemPlan;
	}

	public void setItemPlan(List<PdcaItemVO> itemPlan) {
		this.itemPlan = itemPlan;
	}

	public List<PdcaItemVO> getItemDo() {
		return itemDo;
	}

	public void setItemDo(List<PdcaItemVO> itemDo) {
		this.itemDo = itemDo;
	}

	public List<PdcaItemVO> getItemCheck() {
		return itemCheck;
	}

	public void setItemCheck(List<PdcaItemVO> itemCheck) {
		this.itemCheck = itemCheck;
	}

	public List<PdcaItemVO> getItemAction() {
		return itemAction;
	}

	public void setItemAction(List<PdcaItemVO> itemAction) {
		this.itemAction = itemAction;
	}

	public PdcaAuditVO getAuditPlan() {
		return auditPlan;
	}

	public void setAuditPlan(PdcaAuditVO auditPlan) {
		this.auditPlan = auditPlan;
	}

	public PdcaAuditVO getAuditDo() {
		return auditDo;
	}

	public void setAuditDo(PdcaAuditVO auditDo) {
		this.auditDo = auditDo;
	}

	public PdcaAuditVO getAuditCheck() {
		return auditCheck;
	}

	public void setAuditCheck(PdcaAuditVO auditCheck) {
		this.auditCheck = auditCheck;
	}

	public PdcaAuditVO getAuditAction() {
		return auditAction;
	}

	public void setAuditAction(PdcaAuditVO auditAction) {
		this.auditAction = auditAction;
	}	

}
