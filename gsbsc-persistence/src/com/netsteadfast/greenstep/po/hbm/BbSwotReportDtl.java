package com.netsteadfast.greenstep.po.hbm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.netsteadfast.greenstep.base.model.BaseEntity;
import com.netsteadfast.greenstep.base.model.EntityPK;

@Entity
@Table(name="bb_swot_report_dtl")
public class BbSwotReportDtl extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = -3115902908590673207L;
	private String oid;
	private String reportId;
	private int seq;
	private String label;
	private String issues1;
	private String issues2;
	private String issues3;
	private String issues4;
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
	
	@Column(name="REPORT_ID")
	public String getReportId() {
		return reportId;
	}
	
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	
	@Column(name="SEQ")
	public int getSeq() {
		return seq;
	}
	
	public void setSeq(int seq) {
		this.seq = seq;
	}
	
	@Column(name="LABEL")
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	@Column(name="ISSUES1")
	public String getIssues1() {
		return issues1;
	}
	
	public void setIssues1(String issues1) {
		this.issues1 = issues1;
	}
	
	@Column(name="ISSUES2")
	public String getIssues2() {
		return issues2;
	}
	
	public void setIssues2(String issues2) {
		this.issues2 = issues2;
	}
	
	@Column(name="ISSUES3")
	public String getIssues3() {
		return issues3;
	}
	
	public void setIssues3(String issues3) {
		this.issues3 = issues3;
	}
	
	@Column(name="ISSUES4")
	public String getIssues4() {
		return issues4;
	}
	
	public void setIssues4(String issues4) {
		this.issues4 = issues4;
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
