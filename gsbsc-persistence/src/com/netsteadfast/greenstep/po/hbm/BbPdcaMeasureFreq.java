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
		name="bb_pdca_measure_freq", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"PDCA_OID"} ) 
		} 
)
public class BbPdcaMeasureFreq extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = -3844755466561336600L;
	private String oid;
	private String pdcaOid;
	private String freq;
	private String startDate;
	private String endDate;
	private String dataType;
	private String orgId;
	private String empId;
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
	
	@Column(name="FREQ")
	public String getFreq() {
		return freq;
	}
	
	public void setFreq(String freq) {
		this.freq = freq;
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
	
	@Column(name="DATA_TYPE")
	public String getDataType() {
		return dataType;
	}
	
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	@Column(name="ORG_ID")
	public String getOrgId() {
		return orgId;
	}
	
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	@Column(name="EMP_ID")
	public String getEmpId() {
		return empId;
	}
	
	public void setEmpId(String empId) {
		this.empId = empId;
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
