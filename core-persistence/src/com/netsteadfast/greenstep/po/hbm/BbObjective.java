package com.netsteadfast.greenstep.po.hbm;

import java.math.BigDecimal;
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
		name="bb_objective", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"OBJ_ID"} ) 
		} 
)
public class BbObjective extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 1684555063626166315L;
	private String oid;	
	private String objId;
	private String perId;
	private String name;
	private BigDecimal weight;
	private float target;
	private float min;
	private String description;	
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
	
	@EntityUK(name="objId")
	@Column(name="OBJ_ID")
	public String getObjId() {
		return objId;
	}
	
	public void setObjId(String objId) {
		this.objId = objId;
	}
	
	@Column(name="PER_ID")
	public String getPerId() {
		return perId;
	}
	
	public void setPerId(String perId) {
		this.perId = perId;
	}
	
	@Column(name="NAME")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="WEIGHT")
	public BigDecimal getWeight() {
		return weight;
	}
	
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	
	@Column(name="TARGET")
	public float getTarget() {
		return target;
	}
	
	public void setTarget(float target) {
		this.target = target;
	}
	
	@Column(name="MIN")
	public float getMin() {
		return min;
	}
	
	public void setMin(float min) {
		this.min = min;
	}
	
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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
