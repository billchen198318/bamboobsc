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
		name="bb_formula", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"FOR_ID"} ) 
		} 
)
public class BbFormula extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = -1365282063211864592L;
	private String oid;
	private String forId;
	private String name;
	private String type;
	private String trendsFlag;
	private String returnMode;
	private String returnVar;
	private String expression;
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
	
	@EntityUK(name="forId")
	@Column(name="FOR_ID")
	public String getForId() {
		return forId;
	}
	
	public void setForId(String forId) {
		this.forId = forId;
	}
	
	@Column(name="NAME")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="TYPE")
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name="TRENDS_FLAG")
	public String getTrendsFlag() {
		return trendsFlag;
	}
	
	public void setTrendsFlag(String trendsFlag) {
		this.trendsFlag = trendsFlag;
	}
	
	@Column(name="RETURN_MODE")
	public String getReturnMode() {
		return returnMode;
	}
	
	public void setReturnMode(String returnMode) {
		this.returnMode = returnMode;
	}
	
	@Column(name="RETURN_VAR")
	public String getReturnVar() {
		return returnVar;
	}
	
	public void setReturnVar(String returnVar) {
		this.returnVar = returnVar;
	}
	
	@Column(name="EXPRESSION")
	public String getExpression() {
		return expression;
	}
	
	public void setExpression(String expression) {
		this.expression = expression;
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
