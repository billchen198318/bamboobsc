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
		name="qc_data_query", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"NAME"} ) 
		} 
)
public class QcDataQuery extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = -6651120779272270714L;
	private String oid;
	private String name;
	private String conf;
	private String queryExpression;
	private String mapperOid;
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
	
	@EntityUK(name="name")
	@Column(name="NAME")		
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="CONF")
	public String getConf() {
		return conf;
	}
	
	public void setConf(String conf) {
		this.conf = conf;
	}
	
	@Column(name="QUERY_EXPRESSION")
	public String getQueryExpression() {
		return queryExpression;
	}
	
	public void setQueryExpression(String queryExpression) {
		this.queryExpression = queryExpression;
	}
	
	@Column(name="MAPPER_OID")
	public String getMapperOid() {
		return mapperOid;
	}
	
	public void setMapperOid(String mapperOid) {
		this.mapperOid = mapperOid;
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
