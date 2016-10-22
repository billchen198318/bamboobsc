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
		name="bb_kpi_attac", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"KPI_ID", "UPLOAD_OID"} ) 
		} 
)
public class BbKpiAttac extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 1058092314587425723L;
	private String oid;
	private String kpiId;	
	private String uploadOid;
	private String viewMode;	
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

	@EntityUK(name="kpiId")
	@Column(name="KPI_ID")	
	public String getKpiId() {
		return kpiId;
	}
	
	public void setKpiId(String kpiId) {
		this.kpiId = kpiId;
	}		

	@EntityUK(name="uploadOid")
	@Column(name="UPLOAD_OID")		
	public String getUploadOid() {
		return uploadOid;
	}
	
	public void setUploadOid(String uploadOid) {
		this.uploadOid = uploadOid;
	}
	
	@Column(name="VIEW_MODE")
	public String getViewMode() {
		return viewMode;
	}
	
	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
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
