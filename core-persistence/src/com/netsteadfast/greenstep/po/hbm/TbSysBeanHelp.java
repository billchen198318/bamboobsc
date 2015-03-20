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
		name="tb_sys_bean_help", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"BEAN_ID", "METHOD", "SYSTEM"} ) 
		} 
)
public class TbSysBeanHelp extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 2544890666561272264L;
	private String oid;	
	private String beanId;
	private String method;
	private String system;
	private String enableFlag;
	private String description;
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
	
	@EntityUK(name="beanId")
	@Column(name="BEAN_ID")
	public String getBeanId() {
		return beanId;
	}
	
	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}
	
	@EntityUK(name="method")
	@Column(name="METHOD")
	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
	
	@EntityUK(name="system")
	@Column(name="SYSTEM")
	public String getSystem() {
		return system;
	}
	
	public void setSystem(String system) {
		this.system = system;
	}
	
	@Column(name="ENABLE_FLAG")
	public String getEnableFlag() {
		return enableFlag;
	}
	
	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}
	
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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
