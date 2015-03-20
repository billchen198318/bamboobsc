/* 
 * Copyright 2012-2016 bambooCORE, greenstep of copyright Chen Xin Nien
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * -----------------------------------------------------------------------
 * 
 * author: 	Chen Xin Nien
 * contact: chen.xin.nien@gmail.com
 * 
 */
package com.netsteadfast.greenstep.vo;

import com.netsteadfast.greenstep.base.model.BaseValueObj;

public class SysCalendarNoteVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = -108688519853933218L;
	private String oid;	
	private String account;
	private String calendarId;
	private String title;
	private String note;
	private String date;
	private String time;
	private String alert;
	private String contact;
	
	public SysCalendarNoteVO() {
		
	}
	
	public SysCalendarNoteVO(String oid, String account, String calendarId, String title, String date) {
		this.oid = oid;
		this.account = account;
		this.calendarId = calendarId;
		this.title = title;
		this.date = date;
	}	
	
	@Override
	public String getOid() {	
		return this.oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCalendarId() {
		return calendarId;
	}

	public void setCalendarId(String calendarId) {
		this.calendarId = calendarId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

}
