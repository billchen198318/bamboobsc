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
		name="tb_sys_bean_help_expr", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"HELP_OID", "EXPR_ID", "RUN_TYPE"} ) 
		} 
)
public class TbSysBeanHelpExpr extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 5107611359863799814L;
	private String oid;
	private String helpOid;
	private String exprId;
	private String exprSeq;
	private String runType;
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
	
	@EntityUK(name="helpOid")
	@Column(name="HELP_OID")
	public String getHelpOid() {
		return helpOid;
	}
	
	public void setHelpOid(String helpOid) {
		this.helpOid = helpOid;
	}
	
	@EntityUK(name="exprId")
	@Column(name="EXPR_ID")
	public String getExprId() {
		return exprId;
	}
	
	public void setExprId(String exprId) {
		this.exprId = exprId;
	}
	
	@Column(name="EXPR_SEQ")
	public String getExprSeq() {
		return exprSeq;
	}
	
	public void setExprSeq(String exprSeq) {
		this.exprSeq = exprSeq;
	}
	
	@EntityUK(name="runType")
	@Column(name="RUN_TYPE")
	public String getRunType() {
		return runType;
	}
	
	public void setRunType(String runType) {
		this.runType = runType;
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
