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
		name="bb_measure_data", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"KPI_ID", "DATE", "FREQUENCY", "ORG_ID", "EMP_ID"} ) 
		} 
)
public class BbMeasureData extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 369325701449424541L;
	private String oid;
	private String kpiId;
	private String date;
	private float target;
	private float actual;
	private String frequency;
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
	
	@EntityUK(name="kpiId")
	@Column(name="KPI_ID")	
	public String getKpiId() {
		return kpiId;
	}
	
	public void setKpiId(String kpiId) {
		this.kpiId = kpiId;
	}
	
	@EntityUK(name="date")
	@Column(name="DATE")		
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	@Column(name="TARGET")
	public float getTarget() {
		return target;
	}
	
	public void setTarget(float target) {
		this.target = target;
	}
	
	@Column(name="ACTUAL")
	public float getActual() {
		return actual;
	}
	
	public void setActual(float actual) {
		this.actual = actual;
	}
	
	@EntityUK(name="frequency")
	@Column(name="FREQUENCY")		
	public String getFrequency() {
		return frequency;
	}
	
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	@EntityUK(name="orgId")
	@Column(name="ORG_ID")		
	public String getOrgId() {
		return orgId;
	}
	
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	@EntityUK(name="empId")
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
