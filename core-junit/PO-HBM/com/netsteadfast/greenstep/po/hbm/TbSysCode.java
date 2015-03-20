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
		name="tb_sys_code", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"CODE"} ) 
		} 
)
public class TbSysCode extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = -656725534037482044L;
	
	private String oid;
	private String code;
	private String type;
	private String name;
	private String param1;
	private String param2;
	private String param3;
	private String param4;
	private String param5;
	private String cuserid;
	private Date cdate;
	private String uuserid;
	private Date udate;
	
	public TbSysCode() {
	}

	public TbSysCode(String oid, String code, String type, String name,
			String cuserid, Date cdate) {
		this.oid = oid;
		this.code = code;
		this.type = type;
		this.name = name;
		this.cuserid = cuserid;
		this.cdate = cdate;
	}

	public TbSysCode(String oid, String code, String type, String name,
			String param1, String param2, String param3, String param4,
			String param5, String cuserid, Date cdate, String uuserid,
			Date udate) {
		this.oid = oid;
		this.code = code;
		this.type = type;
		this.name = name;
		this.param1 = param1;
		this.param2 = param2;
		this.param3 = param3;
		this.param4 = param4;
		this.param5 = param5;
		this.cuserid = cuserid;
		this.cdate = cdate;
		this.uuserid = uuserid;
		this.udate = udate;
	}	
	
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
	
	@EntityUK(name="code")
	@Column(name="CODE")
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name="TYPE")
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name="NAME")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="PARAM1")
	public String getParam1() {
		return param1;
	}
	
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	
	@Column(name="PARAM2")
	public String getParam2() {
		return param2;
	}
	
	public void setParam2(String param2) {
		this.param2 = param2;
	}
	
	@Column(name="PARAM3")
	public String getParam3() {
		return param3;
	}
	
	public void setParam3(String param3) {
		this.param3 = param3;
	}
	
	@Column(name="PARAM4")
	public String getParam4() {
		return param4;
	}
	
	public void setParam4(String param4) {
		this.param4 = param4;
	}
	
	@Column(name="PARAM5")
	public String getParam5() {
		return param5;
	}
	
	public void setParam5(String param5) {
		this.param5 = param5;
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
