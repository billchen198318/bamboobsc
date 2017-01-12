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
		name="bb_employee_hier", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"EMP_OID", "SUP_OID"} ) 
		} 
)
public class BbEmployeeHier extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 4436698670880697973L;
	private String oid;	
	private String empOid;
	private String supOid;
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
	
	@EntityUK(name="empOid")
	@Column(name="EMP_OID")
	public String getEmpOid() {
		return empOid;
	}
	
	public void setEmpOid(String empOid) {
		this.empOid = empOid;
	}
	
	@EntityUK(name="supOid")
	@Column(name="SUP_OID")
	public String getSupOid() {
		return supOid;
	}
	
	public void setSupOid(String supOid) {
		this.supOid = supOid;
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
