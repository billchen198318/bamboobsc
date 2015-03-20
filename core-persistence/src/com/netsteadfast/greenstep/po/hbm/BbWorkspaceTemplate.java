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
		name="bb_workspace_template", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"TEMPLATE_ID"} ) 
		} 
)
public class BbWorkspaceTemplate extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 8472224734469698669L;
	private String oid;
	private String templateId;
	private String name;
	private String resource;
	private String resourceConf;
	private int positionSize;
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
	
	@EntityUK(name="templateId")
	@Column(name="TEMPLATE_ID")
	public String getTemplateId() {
		return templateId;
	}
	
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	@Column(name="NAME")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="RESOURCE")
	public String getResource() {
		return resource;
	}
	
	public void setResource(String resource) {
		this.resource = resource;
	}
	
	@Column(name="RESOURCE_CONF")
	public String getResourceConf() {
		return resourceConf;
	}
	
	public void setResourceConf(String resourceConf) {
		this.resourceConf = resourceConf;
	}
	
	@Column(name="POSITION_SIZE")
	public int getPositionSize() {
		return positionSize;
	}
	
	public void setPositionSize(int positionSize) {
		this.positionSize = positionSize;
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
