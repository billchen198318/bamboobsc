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
		name="tb_sys_template_param", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"TEMPLATE_ID", "IS_TITLE", "TEMPLATE_VAR"} ) 
		} 
)
public class TbSysTemplateParam extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 7705671482840744507L;
	private String oid;
	private String templateId;
	private String isTitle;
	private String templateVar;
	private String objectVar;	
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

	@EntityUK(name="templateId")
	@Column(name="TEMPLATE_ID")
	public String getTemplateId() {
		return templateId;
	}
	
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	@EntityUK(name="isTitle")
	@Column(name="IS_TITLE")
	public String getIsTitle() {
		return isTitle;
	}
	
	public void setIsTitle(String isTitle) {
		this.isTitle = isTitle;
	}
	
	@EntityUK(name="templateVar")
	@Column(name="TEMPLATE_VAR")
	public String getTemplateVar() {
		return templateVar;
	}
	
	public void setTemplateVar(String templateVar) {
		this.templateVar = templateVar;
	}
	
	@Column(name="OBJECT_VAR")
	public String getObjectVar() {
		return objectVar;
	}
	
	public void setObjectVar(String objectVar) {
		this.objectVar = objectVar;
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
