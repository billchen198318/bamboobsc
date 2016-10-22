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
		name="tb_sys_ws_config", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"WS_ID"} ) 
		} 
)
public class TbSysWsConfig extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = -5138718620087117748L;
	private String oid;
	private String wsId;
	private String system;
	private String type;
	private String beanId;
	private String publishAddress;
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

	@EntityUK(name="wsId")
	@Column(name="WS_ID")
	public String getWsId() {
		return wsId;
	}
	
	public void setWsId(String wsId) {
		this.wsId = wsId;
	}
	
	@Column(name="SYSTEM")
	public String getSystem() {
		return system;
	}
	
	public void setSystem(String system) {
		this.system = system;
	}
	
	@Column(name="TYPE")
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name="BEAN_ID")
	public String getBeanId() {
		return beanId;
	}
	
	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}
	
	@Column(name="PUBLISH_ADDRESS")
	public String getPublishAddress() {
		return publishAddress;
	}
	
	public void setPublishAddress(String publishAddress) {
		this.publishAddress = publishAddress;
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
