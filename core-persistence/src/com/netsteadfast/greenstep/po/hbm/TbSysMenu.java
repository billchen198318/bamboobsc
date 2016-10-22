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
		name="tb_sys_menu", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"PROG_ID", "PARENT_OID"} ) 
		} 
)
public class TbSysMenu extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = -8816686239016347008L;
	private String oid;
	private String progId;
	private String parentOid;
	private String enableFlag;
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
	
	@EntityUK(name="progId")
	@Column(name="PROG_ID")	
	public String getProgId() {
		return progId;
	}
	
	public void setProgId(String progId) {
		this.progId = progId;
	}
	
	@EntityUK(name="parentOid")
	@Column(name="PARENT_OID")
	public String getParentOid() {
		return parentOid;
	}
	
	public void setParentOid(String parentOid) {
		this.parentOid = parentOid;
	}
	
	@Column(name="ENABLE_FLAG")
	public String getEnableFlag() {
		return enableFlag;
	}
	
	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
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
