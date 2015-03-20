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
		name="tb_sys_prog", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"PROG_ID"} ) 
		} 
)
public class TbSysProg extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 5786624397126070948L;
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
	
	@Column(name="NAME")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="URL")
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name="EDIT_MODE")
	public String getEditMode() {
		return editMode;
	}
	
	public void setEditMode(String editMode) {
		this.editMode = editMode;
	}
	
	@Column(name="IS_DIALOG")
	public String getIsDialog() {
		return isDialog;
	}
	
	public void setIsDialog(String isDialog) {
		this.isDialog = isDialog;
	}
	
	@Column(name="IS_WINDOW")
	public String getIsWindow() {
		return isWindow;
	}
	
	public void setIsWindow(String isWindow) {
		this.isWindow = isWindow;
	}
	
	@Column(name="DIALOG_W")
	public int getDialogW() {
		return dialogW;
	}
	
	public void setDialogW(int dialogW) {
		this.dialogW = dialogW;
	}
	
	@Column(name="DIALOG_H")
	public int getDialogH() {
		return dialogH;
	}
	
	public void setDialogH(int dialogH) {
		this.dialogH = dialogH;
	}
	
	@Column(name="PROG_SYSTEM")
	public String getProgSystem() {
		return progSystem;
	}
	
	public void setProgSystem(String progSystem) {
		this.progSystem = progSystem;
	}
	
	@Column(name="ITEM_TYPE")
	public String getItemType() {
		return itemType;
	}
	
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	
	@Column(name="ICON")
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
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
