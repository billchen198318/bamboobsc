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
		name="bb_tsa_ma_coefficients", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"TSA_OID", "SEQ"} ) 
		} 
)
public class BbTsaMaCoefficients extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 5953908416818848050L;
	private String oid;
	private String tsaOid;
	private int seq;
	private float seqValue;
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
	
	@EntityUK(name="tsaOid")
	@Column(name="TSA_OID")
	public String getTsaOid() {
		return tsaOid;
	}
	
	public void setTsaOid(String tsaOid) {
		this.tsaOid = tsaOid;
	}
	
	@EntityUK(name="seq")
	@Column(name="SEQ")
	public int getSeq() {
		return seq;
	}
	
	public void setSeq(int seq) {
		this.seq = seq;
	}
	
	@Column(name="SEQ_VALUE")
	public float getSeqValue() {
		return seqValue;
	}
	
	public void setSeqValue(float seqValue) {
		this.seqValue = seqValue;
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
