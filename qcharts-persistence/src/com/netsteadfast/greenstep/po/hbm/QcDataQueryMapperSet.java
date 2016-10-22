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
		name="qc_data_query_mapper_set", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"MAPPER_OID", "LABEL_FIELD", "VALUE_FIELD"} ) 
		} 
)
public class QcDataQueryMapperSet extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 7913212921358070138L;
	private String oid;
	private String mapperOid;
	private String labelField;
	private String valueField;
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
	
	@EntityUK(name="mapperOid")
	@Column(name="MAPPER_OID")
	public String getMapperOid() {
		return mapperOid;
	}
	
	public void setMapperOid(String mapperOid) {
		this.mapperOid = mapperOid;
	}
	
	@EntityUK(name="labelField")
	@Column(name="LABEL_FIELD")
	public String getLabelField() {
		return labelField;
	}
	
	public void setLabelField(String labelField) {
		this.labelField = labelField;
	}
	
	@EntityUK(name="valueField")
	@Column(name="VALUE_FIELD")
	public String getValueField() {
		return valueField;
	}
	
	public void setValueField(String valueField) {
		this.valueField = valueField;
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
