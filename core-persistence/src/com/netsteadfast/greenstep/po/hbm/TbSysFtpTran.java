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
		name="tb_sys_ftp_tran", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"TRAN_ID"} ) 
		} 
)
public class TbSysFtpTran extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = -2337654821868342225L;
	private String oid;		
	private String ftpId;
	private String tranId;
	private String tranType;
	private String cwd;
	private String xmlClassName;
	private String useSegm;
	private String segmMode;
	private String segmSymbol;
	private String encoding;
	private String exprType;
	private String nameExpression;
	private String helpExpression;
	private int beginLen;
	private String description;	
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
	
	@Column(name="FTP_ID")
	public String getFtpId() {
		return ftpId;
	}
	
	public void setFtpId(String ftpId) {
		this.ftpId = ftpId;
	}
	
	@EntityUK(name="tranId")
	@Column(name="TRAN_ID")
	public String getTranId() {
		return tranId;
	}
	
	public void setTranId(String tranId) {
		this.tranId = tranId;
	}
	
	@Column(name="TRAN_TYPE")
	public String getTranType() {
		return tranType;
	}
	
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
	
	@Column(name="CWD")
	public String getCwd() {
		return cwd;
	}
	
	public void setCwd(String cwd) {
		this.cwd = cwd;
	}
	
	@Column(name="XML_CLASS_NAME")
	public String getXmlClassName() {
		return xmlClassName;
	}
	
	public void setXmlClassName(String xmlClassName) {
		this.xmlClassName = xmlClassName;
	}
	
	@Column(name="USE_SEGM")
	public String getUseSegm() {
		return useSegm;
	}
	
	public void setUseSegm(String useSegm) {
		this.useSegm = useSegm;
	}
	
	@Column(name="SEGM_MODE")
	public String getSegmMode() {
		return segmMode;
	}
	
	public void setSegmMode(String segmMode) {
		this.segmMode = segmMode;
	}
	
	@Column(name="SEGM_SYMBOL")
	public String getSegmSymbol() {
		return segmSymbol;
	}
	
	public void setSegmSymbol(String segmSymbol) {
		this.segmSymbol = segmSymbol;
	}
	
	@Column(name="ENCODING")
	public String getEncoding() {
		return encoding;
	}
	
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	@Column(name="EXPR_TYPE")
	public String getExprType() {
		return exprType;
	}
	
	public void setExprType(String exprType) {
		this.exprType = exprType;
	}
	
	@Column(name="NAME_EXPRESSION")
	public String getNameExpression() {
		return nameExpression;
	}
	
	public void setNameExpression(String nameExpression) {
		this.nameExpression = nameExpression;
	}
	
	@Column(name="HELP_EXPRESSION")
	public String getHelpExpression() {
		return helpExpression;
	}
	
	public void setHelpExpression(String helpExpression) {
		this.helpExpression = helpExpression;
	}
	
	@Column(name="BEGIN_LEN")
	public int getBeginLen() {
		return beginLen;
	}
	
	public void setBeginLen(int beginLen) {
		this.beginLen = beginLen;
	}
	
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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
