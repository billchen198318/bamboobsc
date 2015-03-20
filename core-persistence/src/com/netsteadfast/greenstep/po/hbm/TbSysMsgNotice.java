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
		name="tb_sys_msg_notice", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"NOTICE_ID", "MSG_ID"} ) 
		} 
)
public class TbSysMsgNotice extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = -19671951415819335L;
	private String oid;
	private String noticeId;
	private String msgId;
	private String title;
	private String message;
	private String date;
	private String time;
	private String isGlobal;
	private String toAccount;
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
	
	@EntityUK(name="noticeId")
	@Column(name="NOTICE_ID")
	public String getNoticeId() {
		return noticeId;
	}
	
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
	
	@EntityUK(name="msgId")
	@Column(name="MSG_ID")	
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	@Column(name="TITLE")
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name="MESSAGE")
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Column(name="DATE")
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	@Column(name="TIME")
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	@Column(name="IS_GLOBAL")
	public String getIsGlobal() {
		return isGlobal;
	}
	
	public void setIsGlobal(String isGlobal) {
		this.isGlobal = isGlobal;
	}
	
	@Column(name="TO_ACCOUNT")
	public String getToAccount() {
		return toAccount;
	}
	
	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
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
