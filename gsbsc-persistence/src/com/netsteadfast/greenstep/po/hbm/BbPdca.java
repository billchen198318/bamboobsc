package com.netsteadfast.greenstep.po.hbm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.netsteadfast.greenstep.base.model.BaseEntity;
import com.netsteadfast.greenstep.base.model.EntityPK;
import com.netsteadfast.greenstep.base.model.EntityUK;

@Entity
@Table(
		name="bb_pdca", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"TITLE"} ) 
		} 
)
public class BbPdca extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 8909806991659943111L;
	private String oid;
	private String title;
	private String description;
	private String startDate;
	private String endDate;
	private String parentOid;
	private String confirmDate;
	private String confirmFlag;
	private String confirmEmpId;
	private String cuserid;
	private Date cdate;
	private String uuserid;
	private Date udate;	
	
	@Override
	@Id
	@EntityPK(name="oid")
	@Column(name="OID")
	public String getOid() {
		return oid;
	}
	@Override
	public void setOid(String oid) {
		this.oid = oid;
	}	
	
	@EntityUK(name="title")
	@Column(name="TITLE")
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="START_DATE")
	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	@Column(name="END_DATE")
	public String getEndDate() {
		return endDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	@Column(name="PARENT_OID")
	public String getParentOid() {
		return parentOid;
	}
	
	public void setParentOid(String parentOid) {
		this.parentOid = parentOid;
	}
	
	@Column(name="CONFIRM_DATE")
	public String getConfirmDate() {
		return confirmDate;
	}
	
	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}
	
	@Column(name="CONFIRM_FLAG")
	public String getConfirmFlag() {
		return confirmFlag;
	}
	
	public void setConfirmFlag(String confirmFlag) {
		this.confirmFlag = confirmFlag;
	}
	
	@Column(name="CONFIRM_EMP_ID")
	public String getConfirmEmpId() {
		return confirmEmpId;
	}
	
	public void setConfirmEmpId(String confirmEmpId) {
		this.confirmEmpId = confirmEmpId;
	}
	
	@Override
	@Column(name="CUSERID")
	public String getCuserid() {
		return this.cuserid;
	}
	@Override
	public void setCuserid(String cuserid) {
		this.cuserid = cuserid;
	}
	@Override
	@Column(name="CDATE")
	public Date getCdate() {
		return this.cdate;
	}
	@Override
	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
	@Override
	@Column(name="UUSERID")
	public String getUuserid() {
		return this.uuserid;
	}
	@Override
	public void setUuserid(String uuserid) {
		this.uuserid = uuserid;
	}
	@Override
	@Column(name="UDATE")
	public Date getUdate() {
		return this.udate;
	}
	@Override
	public void setUdate(Date udate) {
		this.udate = udate;
	}

}
