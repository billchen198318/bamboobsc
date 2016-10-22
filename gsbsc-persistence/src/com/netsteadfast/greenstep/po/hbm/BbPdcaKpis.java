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
		name="bb_pdca_kpis", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"PDCA_OID", "KPI_ID"} ) 
		} 
)
public class BbPdcaKpis extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = -1094245429648619758L;
	private String oid;
	private String pdcaOid;
	private String kpiId;
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

	@EntityUK(name="pdcaOid")
	@Column(name="PDCA_OID")
	public String getPdcaOid() {
		return pdcaOid;
	}
	
	public void setPdcaOid(String pdcaOid) {
		this.pdcaOid = pdcaOid;
	}
	
	@EntityUK(name="kpiId")
	@Column(name="KPI_ID")	
	public String getKpiId() {
		return kpiId;
	}
	
	public void setKpiId(String kpiId) {
		this.kpiId = kpiId;
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
