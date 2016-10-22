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
		name="bb_degree_feedback_score", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"PROJECT_OID", "ITEM_OID", "ASSIGN_OID"} ) 
		} 
)
public class BbDegreeFeedbackScore extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = -5311283323822413298L;
	private String oid;
	private String projectOid;
	private String itemOid;
	private String assignOid;
	private int score;
	private String memo;
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
	
	@EntityUK(name="projectOid")
	@Column(name="PROJECT_OID")	
	public String getProjectOid() {
		return projectOid;
	}
	
	public void setProjectOid(String projectOid) {
		this.projectOid = projectOid;
	}
	
	@EntityUK(name="itemOid")
	@Column(name="ITEM_OID")	
	public String getItemOid() {
		return itemOid;
	}
	
	public void setItemOid(String itemOid) {
		this.itemOid = itemOid;
	}
	
	@EntityUK(name="assignOid")
	@Column(name="ASSIGN_OID")	
	public String getAssignOid() {
		return assignOid;
	}
	
	public void setAssignOid(String assignOid) {
		this.assignOid = assignOid;
	}
	
	@Column(name="SCORE")	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	@Column(name="MEMO")
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
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
