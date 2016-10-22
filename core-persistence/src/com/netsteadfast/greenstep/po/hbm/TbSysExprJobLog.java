package com.netsteadfast.greenstep.po.hbm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.netsteadfast.greenstep.base.model.BaseEntity;
import com.netsteadfast.greenstep.base.model.EntityPK;

@Entity
@Table(name="tb_sys_expr_job_log")
public class TbSysExprJobLog extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 3420636675514562704L;
	private String oid;
	private String id;
	private String logStatus;
	private Date beginDatetime;
	private Date endDatetime;
	private String faultMsg;
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
	
	@Column(name="ID")
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="LOG_STATUS")
	public String getLogStatus() {
		return logStatus;
	}
	
	public void setLogStatus(String logStatus) {
		this.logStatus = logStatus;
	}
	
	@Column(name="BEGIN_DATETIME")
	public Date getBeginDatetime() {
		return beginDatetime;
	}
	
	public void setBeginDatetime(Date beginDatetime) {
		this.beginDatetime = beginDatetime;
	}
	
	@Column(name="END_DATETIME")
	public Date getEndDatetime() {
		return endDatetime;
	}
	
	public void setEndDatetime(Date endDatetime) {
		this.endDatetime = endDatetime;
	}
	
	@Column(name="FAULT_MSG")
	public String getFaultMsg() {
		return faultMsg;
	}
	
	public void setFaultMsg(String faultMsg) {
		this.faultMsg = faultMsg;
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
