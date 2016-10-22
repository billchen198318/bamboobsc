package com.netsteadfast.greenstep.po.hbm;

import java.math.BigDecimal;
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
		name="bb_kpi", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"ID"} ) 
		} 
)
public class BbKpi extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 5926471603516908131L;
	private String oid;
	private String id;
	private String objId;
	private String name;
	private String description;
	private BigDecimal weight;
	private String unit;
	private String forId;
	private String trendsForId;
	private float max;
	private float target;
	private float min;
	private String management;
	private String compareType;
	private String cal;
	private String dataType;
	private String orgaMeasureSeparate;
	private String userMeasureSeparate;	
	private int quasiRange;
	private String activate;
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
	
	@EntityUK(name="id")
	@Column(name="ID")
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="OBJ_ID")
	public String getObjId() {
		return objId;
	}
	
	public void setObjId(String objId) {
		this.objId = objId;
	}
	
	@Column(name="NAME")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="WEIGHT")
	public BigDecimal getWeight() {
		return weight;
	}
	
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	
	@Column(name="UNIT")
	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Column(name="FOR_ID")
	public String getForId() {
		return forId;
	}
	
	public void setForId(String forId) {
		this.forId = forId;
	}
	
	@Column(name="TRENDS_FOR_ID")
	public String getTrendsForId() {
		return trendsForId;
	}
	
	public void setTrendsForId(String trendsForId) {
		this.trendsForId = trendsForId;
	}
	
	@Column(name="MAX")
	public float getMax() {
		return max;
	}
	
	public void setMax(float max) {
		this.max = max;
	}
	
	@Column(name="TARGET")
	public float getTarget() {
		return target;
	}
	
	public void setTarget(float target) {
		this.target = target;
	}
	
	@Column(name="MIN")
	public float getMin() {
		return min;
	}
	
	public void setMin(float min) {
		this.min = min;
	}
	
	@Column(name="MANAGEMENT")
	public String getManagement() {
		return management;
	}
	
	public void setManagement(String management) {
		this.management = management;
	}
	
	@Column(name="COMPARE_TYPE")
	public String getCompareType() {
		return compareType;
	}
	
	public void setCompareType(String compareType) {
		this.compareType = compareType;
	}
	
	@Column(name="CAL")
	public String getCal() {
		return cal;
	}
	
	public void setCal(String cal) {
		this.cal = cal;
	}
	
	@Column(name="DATA_TYPE")
	public String getDataType() {
		return dataType;
	}
	
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	@Column(name="ORGA_MEASURE_SEPARATE")
	public String getOrgaMeasureSeparate() {
		return orgaMeasureSeparate;
	}
	
	public void setOrgaMeasureSeparate(String orgaMeasureSeparate) {
		this.orgaMeasureSeparate = orgaMeasureSeparate;
	}
	
	@Column(name="USER_MEASURE_SEPARATE")
	public String getUserMeasureSeparate() {
		return userMeasureSeparate;
	}
	
	public void setUserMeasureSeparate(String userMeasureSeparate) {
		this.userMeasureSeparate = userMeasureSeparate;
	}
	
	@Column(name="QUASI_RANGE")
	public int getQuasiRange() {
		return quasiRange;
	}
	
	public void setQuasiRange(int quasiRange) {
		this.quasiRange = quasiRange;
	}
	
	@Column(name="ACTIVATE")
	public String getActivate() {
		return activate;
	}
	
	public void setActivate(String activate) {
		this.activate = activate;
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
