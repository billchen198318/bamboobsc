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
		name="tb_sys_expr_job", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"ID"} ) 
		} 
)
public class TbSysExprJob extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 4987474663834603482L;
	private String oid;
	private String system;
	private String id;
	private String name;
	private String active;
	private String description;
	private String runStatus;
	private String checkFault;
	private String exprId;
	private String runDayOfWeek;
	private String runHour;
	private String runMinute;
	private String contactMode;
	private String contact;
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
	
	@Column(name="SYSTEM")
	public String getSystem() {
		return system;
	}
	
	public void setSystem(String system) {
		this.system = system;
	}
	
	@EntityUK(name="id")
	@Column(name="ID")	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="NAME")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="ACTIVE")
	public String getActive() {
		return active;
	}
	
	public void setActive(String active) {
		this.active = active;
	}
	
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="RUN_STATUS")
	public String getRunStatus() {
		return runStatus;
	}
	
	public void setRunStatus(String runStatus) {
		this.runStatus = runStatus;
	}
	
	@Column(name="CHECK_FAULT")
	public String getCheckFault() {
		return checkFault;
	}
	
	public void setCheckFault(String checkFault) {
		this.checkFault = checkFault;
	}
	
	@Column(name="EXPR_ID")
	public String getExprId() {
		return exprId;
	}
	
	public void setExprId(String exprId) {
		this.exprId = exprId;
	}
	
	@Column(name="RUN_DAY_OF_WEEK")
	public String getRunDayOfWeek() {
		return runDayOfWeek;
	}
	
	public void setRunDayOfWeek(String runDayOfWeek) {
		this.runDayOfWeek = runDayOfWeek;
	}
	
	@Column(name="RUN_HOUR")
	public String getRunHour() {
		return runHour;
	}
	
	public void setRunHour(String runHour) {
		this.runHour = runHour;
	}
	
	@Column(name="RUN_MINUTE")
	public String getRunMinute() {
		return runMinute;
	}
	
	public void setRunMinute(String runMinute) {
		this.runMinute = runMinute;
	}
	
	@Column(name="CONTACT_MODE")
	public String getContactMode() {
		return contactMode;
	}
	
	public void setContactMode(String contactMode) {
		this.contactMode = contactMode;
	}
	
	@Column(name="CONTACT")
	public String getContact() {
		return contact;
	}
	
	public void setContact(String contact) {
		this.contact = contact;
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
