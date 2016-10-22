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
		name="bb_pdca_audit", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"PDCA_OID", "CONFIRM_SEQ"} ) 
		} 
)
public class BbPdcaAudit extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = -4984755977520353207L;
	private String oid;
	private String pdcaOid;
	private String type;
	private String empId;
	private String confirmDate;
	private int confirmSeq; 
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

	@EntityUK(name="pdcaOid")
	@Column(name="PDCA_OID")	
	public String getPdcaOid() {
		return pdcaOid;
	}
	
	public void setPdcaOid(String pdcaOid) {
		this.pdcaOid = pdcaOid;
	}
	
	@Column(name="TYPE")	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name="EMP_ID")	
	public String getEmpId() {
		return empId;
	}
	
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	
	@Column(name="CONFIRM_DATE")
	public String getConfirmDate() {
		return confirmDate;
	}
	
	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}
	
	@EntityUK(name="confirmSeq")
	@Column(name="CONFIRM_SEQ")
	public int getConfirmSeq() {
		return confirmSeq;
	}
	
	public void setConfirmSeq(int confirmSeq) {
		this.confirmSeq = confirmSeq;
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
