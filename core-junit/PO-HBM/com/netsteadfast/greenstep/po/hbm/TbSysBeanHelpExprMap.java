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
		name="tb_sys_bean_help_expr_map", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"HELP_EXPR_OID", "VAR_NAME"} ) 
		} 
)
public class TbSysBeanHelpExprMap extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = -9052273514029083252L;
	private String oid;	
	private String helpExprOid;
	private String methodResultFlag;
	private String methodParamClass;
	private int methodParamIndex;
	private String varName;	
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

	@EntityUK(name="helpExprOid")
	@Column(name="HELP_EXPR_OID")
	public String getHelpExprOid() {
		return helpExprOid;
	}
	
	public void setHelpExprOid(String helpExprOid) {
		this.helpExprOid = helpExprOid;
	}
	
	@Column(name="METHOD_RESULT_FLAG")
	public String getMethodResultFlag() {
		return methodResultFlag;
	}
	
	public void setMethodResultFlag(String methodResultFlag) {
		this.methodResultFlag = methodResultFlag;
	}
	
	@Column(name="METHOD_PARAM_CLASS")
	public String getMethodParamClass() {
		return methodParamClass;
	}
	
	public void setMethodParamClass(String methodParamClass) {
		this.methodParamClass = methodParamClass;
	}
	
	@Column(name="METHOD_PARAM_INDEX")
	public int getMethodParamIndex() {
		return methodParamIndex;
	}
	
	public void setMethodParamIndex(int methodParamIndex) {
		this.methodParamIndex = methodParamIndex;
	}
	
	@EntityUK(name="varName")
	@Column(name="VAR_NAME")
	public String getVarName() {
		return varName;
	}
	
	public void setVarName(String varName) {
		this.varName = varName;
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
