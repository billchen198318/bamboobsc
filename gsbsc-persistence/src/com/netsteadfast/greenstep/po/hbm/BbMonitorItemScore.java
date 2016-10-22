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
		name="bb_monitor_item_score", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"ITEM_TYPE", "ITEM_ID", "DATE_VAL", "FREQUENCY", "ORG_ID", "EMP_ID"} ) 
		} 
)
public class BbMonitorItemScore extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 7866996695580471206L;
	private String oid;
	private String itemType;
	private String itemId;
	private String dateVal;
	private String frequency;
	private String orgId;
	private String empId;
	private String score;
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

	@EntityUK(name="itemType")
	@Column(name="ITEM_TYPE")		
	public String getItemType() {
		return itemType;
	}
	
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	
	@EntityUK(name="itemId")
	@Column(name="ITEM_ID")	
	public String getItemId() {
		return itemId;
	}
	
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	@EntityUK(name="dateVal")
	@Column(name="DATE_VAL")	
	public String getDateVal() {
		return dateVal;
	}
	
	public void setDateVal(String dateVal) {
		this.dateVal = dateVal;
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
	
	@Column(name="SCORE")
	public String getScore() {
		return score;
	}
	
	public void setScore(String score) {
		this.score = score;
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
