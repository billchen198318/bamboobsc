package com.netsteadfast.greenstep.po.hbm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.netsteadfast.greenstep.base.model.BaseEntity;
import com.netsteadfast.greenstep.base.model.EntityPK;

@Entity
@Table(name="bb_strategy_map_conns")
public class BbStrategyMapConns extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = -2891129533707769455L;
	private String oid;	
	private String masterOid;
	private String connectionId;
	private String sourceId;
	private String targetId;	
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
	
	@Column(name="MASTER_OID")
	public String getMasterOid() {
		return masterOid;
	}
	
	public void setMasterOid(String masterOid) {
		this.masterOid = masterOid;
	}
	
	@Column(name="CONNECTION_ID")
	public String getConnectionId() {
		return connectionId;
	}
	
	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}
	
	@Column(name="SOURCE_ID")
	public String getSourceId() {
		return sourceId;
	}
	
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
	@Column(name="TARGET_ID")
	public String getTargetId() {
		return targetId;
	}
	
	public void setTargetId(String targetId) {
		this.targetId = targetId;
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
