package com.netsteadfast.greenstep.po.hbm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.netsteadfast.greenstep.base.model.BaseEntity;
import com.netsteadfast.greenstep.base.model.EntityPK;

@Entity
@Table(
		name="bb_score_color", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"SCORE_MIN", "SCORE_MAX"} ) 
		} 
)
public class BbScoreColor extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = -6580593180170311045L;
	private String oid;	
	private int scoreMin;
	private int scoreMax;
	private String fontColor;
	private String bgColor;
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
	
	@Column(name="SCORE_MIN")
	public int getScoreMin() {
		return scoreMin;
	}
	
	public void setScoreMin(int scoreMin) {
		this.scoreMin = scoreMin;
	}
	
	@Column(name="SCORE_MAX")
	public int getScoreMax() {
		return scoreMax;
	}
	
	public void setScoreMax(int scoreMax) {
		this.scoreMax = scoreMax;
	}
	
	@Column(name="FONT_COLOR")
	public String getFontColor() {
		return fontColor;
	}
	
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}
	
	@Column(name="BG_COLOR")
	public String getBgColor() {
		return bgColor;
	}
	
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
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
