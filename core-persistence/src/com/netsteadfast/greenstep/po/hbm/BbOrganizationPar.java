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
		name="bb_organization_par", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"ORG_ID", "PAR_ID"} ) 
		} 
)
public class BbOrganizationPar extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 1779488299702391363L;
	private String oid;	
	private String orgId;
	private String parId;
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
	
	@EntityUK(name="orgId")
	@Column(name="ORG_ID")
	public String getOrgId() {
		return orgId;
	}
	
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}		
	
	@EntityUK(name="parId")
	@Column(name="PAR_ID")	
	public String getParId() {
		return parId;
	}
	
	public void setParId(String parId) {
		this.parId = parId;
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
