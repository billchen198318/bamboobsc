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
		name="tb_sys_jreport_param", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"REPORT_ID", "RPT_PARAM"} ) 
		} 
)
public class TbSysJreportParam extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 5597941550133038745L;
	private String oid;	
	private String reportId;
	private String urlParam;
	private String rptParam;
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
	
	@EntityUK(name="reportId")
	@Column(name="REPORT_ID")
	public String getReportId() {
		return reportId;
	}
	
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	
	@Column(name="URL_PARAM")
	public String getUrlParam() {
		return urlParam;
	}
	
	public void setUrlParam(String urlParam) {
		this.urlParam = urlParam;
	}
	
	@EntityUK(name="rptParam")
	@Column(name="RPT_PARAM")
	public String getRptParam() {
		return rptParam;
	}
	
	public void setRptParam(String rptParam) {
		this.rptParam = rptParam;
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
