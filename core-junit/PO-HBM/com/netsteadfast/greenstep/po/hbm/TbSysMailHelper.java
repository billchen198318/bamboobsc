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
		name="tb_sys_mail_helper", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"MAIL_ID"} ) 
		} 
)
public class TbSysMailHelper extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = -4894800472390993266L;
	private String oid;
	private String mailId;
	private String subject;
	private byte[] text;
	private String mailFrom;
	private String mailTo;
	private String mailCc;
	private String mailBcc;
	private String successFlag;
	private Date successTime;
	private String retainFlag;
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
	
	@EntityUK(name="mailId")
	@Column(name="MAIL_ID")
	public String getMailId() {
		return mailId;
	}
	
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	
	@Column(name="SUBJECT")
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@Column(name="TEXT")
	public byte[] getText() {
		return text;
	}
	
	public void setText(byte[] text) {
		this.text = text;
	}
	
	@Column(name="MAIL_FROM")
	public String getMailFrom() {
		return mailFrom;
	}
	
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}
	
	@Column(name="MAIL_TO")
	public String getMailTo() {
		return mailTo;
	}
	
	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}
	
	@Column(name="MAIL_CC")
	public String getMailCc() {
		return mailCc;
	}
	
	public void setMailCc(String mailCc) {
		this.mailCc = mailCc;
	}
	
	@Column(name="MAIL_BCC")
	public String getMailBcc() {
		return mailBcc;
	}
	
	public void setMailBcc(String mailBcc) {
		this.mailBcc = mailBcc;
	}
	
	@Column(name="SUCCESS_FLAG")
	public String getSuccessFlag() {
		return successFlag;
	}
	
	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}
	
	@Column(name="SUCCESS_TIME")
	public Date getSuccessTime() {
		return successTime;
	}
	
	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}
	
	@Column(name="RETAIN_FLAG")
	public String getRetainFlag() {
		return retainFlag;
	}
	
	public void setRetainFlag(String retainFlag) {
		this.retainFlag = retainFlag;
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
