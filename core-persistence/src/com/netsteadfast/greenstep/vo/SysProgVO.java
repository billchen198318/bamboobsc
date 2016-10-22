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

public class SysProgVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = -4139346357769636092L;
	private String oid;
	private String progId;
	private String name;
	private String url;
	private String editMode;
	private String isDialog;
	private String isWindow;
	private int dialogW;
	private int dialogH;	
	private String progSystem;
	private String itemType;
	private String icon;
	
	public SysProgVO() {
		
	}
	
	public SysProgVO(String oid, String progId, String name, String progSystem, String icon) {
		this.oid = oid;
		this.progId = progId;
		this.name = name;
		this.progSystem = progSystem;
		this.icon = icon;
	}
	
	public SysProgVO(String oid, String progId, String name, String url, String editMode, 
			String progSystem, String itemType, String icon) {
		this.oid = oid;
		this.progId = progId;
		this.name = name;
		this.url = url;
		this.editMode = editMode;
		this.progSystem = progSystem;
		this.itemType = itemType;
		this.icon = icon;
	}
	
	/**
	 * 為了只查程式名稱用
	 * 
	 * @param name
	 */
	public SysProgVO(String name) {
		this.name = name;
	}
	
	@Override
	public String getOid() {
		return this.oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}	
	
	public String getProgId() {
		return progId;
	}

	public void setProgId(String progId) {
		this.progId = progId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEditMode() {
		return editMode;
	}

	public void setEditMode(String editMode) {
		this.editMode = editMode;
	}

	public String getIsDialog() {
		return isDialog;
	}

	public void setIsDialog(String isDialog) {
		this.isDialog = isDialog;
	}

	public String getIsWindow() {
		return isWindow;
	}

	public void setIsWindow(String isWindow) {
		this.isWindow = isWindow;
	}

	public int getDialogW() {
		return dialogW;
	}

	public void setDialogW(int dialogW) {
		this.dialogW = dialogW;
	}

	public int getDialogH() {
		return dialogH;
	}

	public void setDialogH(int dialogH) {
		this.dialogH = dialogH;
	}

	public String getProgSystem() {
		return progSystem;
	}

	public void setProgSystem(String progSystem) {
		this.progSystem = progSystem;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
