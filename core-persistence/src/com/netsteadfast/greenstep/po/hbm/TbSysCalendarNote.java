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
		name="tb_sys_calendar_note", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"CALENDAR_ID"} ) 
		} 
)
public class TbSysCalendarNote extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 6538312036838889280L;
	private String oid;	
	private String account;
	private String calendarId;
	private String title;
	private String note;
	private String date;
	private String time;
	private String alert;
	private String contact;
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
	
	@EntityUK(name="account")
	@Column(name="ACCOUNT")
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	@EntityUK(name="calendarId")
	@Column(name="CALENDAR_ID")
	public String getCalendarId() {
		return calendarId;
	}
	
	public void setCalendarId(String calendarId) {
		this.calendarId = calendarId;
	}
	
	@Column(name="TITLE")
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name="NOTE")
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
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
	
	@Column(name="ALERT")
	public String getAlert() {
		return alert;
	}
	
	public void setAlert(String alert) {
		this.alert = alert;
	}
	
	@Column(name="CONTACT")
	public String getContact() {
		return contact;
	}
	
	public void setContact(String contact) {
		this.contact = contact;
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
