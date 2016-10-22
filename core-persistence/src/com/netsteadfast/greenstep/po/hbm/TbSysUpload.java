package com.netsteadfast.greenstep.po.hbm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.netsteadfast.greenstep.base.model.BaseEntity;
import com.netsteadfast.greenstep.base.model.EntityPK;

@Entity
@Table(
		name="tb_sys_upload"
)
public class TbSysUpload extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 2940918063514857155L;
	private String oid;
	private String system;
	private String subDir;
	private String type;
	private String fileName;
	private String showName;
	private String isFile;
	private byte[] content;
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
	
	@Column(name="SYSTEM")
	public String getSystem() {
		return system;
	}
	
	public void setSystem(String system) {
		this.system = system;
	}
	
	@Column(name="SUB_DIR")
	public String getSubDir() {
		return subDir;
	}
	
	public void setSubDir(String subDir) {
		this.subDir = subDir;
	}
	
	@Column(name="TYPE")
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name="FILE_NAME")
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Column(name="SHOW_NAME")
	public String getShowName() {
		return showName;
	}
	
	public void setShowName(String showName) {
		this.showName = showName;
	}
	
	@Column(name="IS_FILE")
	public String getIsFile() {
		return isFile;
	}
	
	public void setIsFile(String isFile) {
		this.isFile = isFile;
	}
	
	@Column(name="CONTENT")
	public byte[] getContent() {
		return content;
	}
	
	public void setContent(byte[] content) {
		this.content = content;
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
