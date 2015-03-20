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
		name="qc_olap_mdx", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"NAME"} ) 
		} 
)
public class QcOlapMdx extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = -6361802135575151576L;
	private String oid;	
	private String name;
	private byte[] expression;
	private String confOid;
	private String catalogOid;
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
	
	@EntityUK(name="NAME")
	@Column(name="NAME")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="EXPRESSION")
	public byte[] getExpression() {
		return expression;
	}
	
	public void setExpression(byte[] expression) {
		this.expression = expression;
	}
	
	@Column(name="CONF_OID")
	public String getConfOid() {
		return confOid;
	}
	
	public void setConfOid(String confOid) {
		this.confOid = confOid;
	}
	
	@Column(name="CATALOG_OID")
	public String getCatalogOid() {
		return catalogOid;
	}
	
	public void setCatalogOid(String catalogOid) {
		this.catalogOid = catalogOid;
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
