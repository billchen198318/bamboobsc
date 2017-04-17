<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/00-Catalog.md">⌂ Catalog</a><br/>
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/02-DaoAndService.md">⇨ Next section 02-DAO and Service</a>

# 01 - Entitiy

# Introduction
BaseValue recommend for Page view(VO) or transform (DTO) and for MyBatis custom query mapper, or page grid query.<br>
BaseEntity recommend only for hibernate do save/update/delete.<br>

***You must first understand the following framework***<br/>
1. Spring http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/<br/>
2. Hibernate http://hibernate.org/<br/>
3. Dozer https://github.com/DozerMapper/dozer<br/>
4. MyBatis https://github.com/mybatis/mybatis-3<br/>

# Persistence entity

<br/>

a database table name `bb_test`

| Name | Type | description |
| --- | --- | --- |
| oid | PK, char(36) | PK-key |
| id | UK, varchar(10) | UK, product no |
| title | varchar(100) | product title |
| cuserid | varchar(24) | create account-id |
| cdate | datetime | create date-time |
| uuserid | varchar(24) | update account-id |
| udate | datetime | update date-time |

<br/>

a persistence object extends BaseEntity
```java
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
		name="bb_test", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"ID"} ) 
		} 
)
public class BbTest extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = 1015124515729620253L;
	private String oid;	
	private String id;
	private String title;
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
	
	@Column(name="TITLE")
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
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
```

<br/>
<br/>

### Persistence object (BaseEntity) must found variable field

| Name | Type |
| --- | --- |
| oid | This is PK, String |
| cuserid | create user id |
| cdate | create date-time |
| uuserid | update user id |
| udate | update date-time |

<br/>
<br/>

### Persistence object (BaseEntity) annotation

| Name | description | example |
| --- | --- | --- |
| @EntityPK | for PK(primary key) variable | @EntityPK(name="oid") |
| @EntityUK | for UK(unique key) variable | @EntityUK(name="id") |

the annotation name="" value is object variable name, not table field name.<br/>
if PO add the `@EntityPK` annotation, BaseService will check PK(primary key) when call saveObject method.<br/>
if PO add the `@EntityUK` annotation, BaseService will check UK(unique key) when call saveObject method.<br/>


# Value object
a Value object extends BaseValue
```java
package com.netsteadfast.greenstep.vo;

import java.util.LinkedList;
import java.util.List;

import com.netsteadfast.greenstep.base.model.BaseValueObj;

public class TestVO extends BaseValueObj implements java.io.Serializable {
	private static final long serialVersionUID = -113541848926304161L;
	private String oid;	
	private String productId;
	private String productTitle;
	
	public TestVO() {
		
	}
	
	public TestVO(String oid, String productId, String productTitle) {
		this.oid = oid;
		this.productId = productId;
		this.productTitle = productTitle;
		this.content = content;
	}
	
	@Override
	public String getOid() {
		return this.oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}
	
}
```	

### Value object (BaseValue) must found variable field

| Name | Type |
| --- | --- |
| oid | This is PK, String |

BaseValue no need `cuserid, cdate, uuserid, udate` <br/>
BaseService will convert VO(BaseValue) to PO(BaseEntity) and auto fill `cuserid,cdate,uuserid,udate` . when call saveObject method.<br/>
BaseService will auto fill `oid` variable(PK) when call saveObject method.

# Settings Mapper VO(value object) to PO(Persistence object) config xml ( Dozer )
add dozerBeanMapping-test.xml into resource/dozer/

```xml
<?xml version="1.0" encoding="UTF-8"?>
<mappings xmlns="http://dozer.sourceforge.net"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://dozer.sourceforge.net
	http://dozer.sourceforge.net/schema/beanmapping.xsd">
	
	<mapping map-id="test.po2vo" >
		<class-a>com.netsteadfast.greenstep.po.hbm.BbTest</class-a>
		<class-b>com.netsteadfast.greenstep.vo.TestVO</class-b>
		<field>
			<a>oid</a>
			<b>oid</b>
		</field>
		<field>
			<a>id</a>
			<b>productId</b>			
		</field>
		<field>
			<a>title</a>
			<b>productTitle</b>			
		</field>					
	</mapping>

	<mapping map-id="test.vo2po" >
		<class-a>com.netsteadfast.greenstep.vo.TestVO</class-a>
		<class-b>com.netsteadfast.greenstep.po.hbm.BbTest</class-b>
		<field>
			<a>oid</a>
			<b>oid</b>
		</field>
		<field>
			<a>productId</a>
			<b>id</b>			
		</field>
		<field>
			<a>productTitle</a>
			<b>title</b>			
		</field>			
	</mapping>
	
</mappings>
```

### Config /core-base/resource/applicationContext/standard/applicationContext-dozer.xml
```xml
<value>classpath*:dozer/dozerBeanMapping-test.xml</value>
```

<br/>
<br/>

# Options for MyBatis-3
### Add BbTest.xml into /resource/mappers

```xml
<!DOCTYPE mapper
	PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
	"http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="BbTest">

	<resultMap type="com.netsteadfast.greenstep.po.hbm.BbTest" id="baseResultMap">
		<id column="OID" property="oid"/>						
		<result column="ID" property="id"/>		
		<result column="TITLE" property="title"/>		
		<result column="CUSERID" property="cuserid"/>
		<result column="CDATE" property="cdate"/>
		<result column="UUSERID" property="uuserid"/>		
		<result column="UDATE" property="udate"/>		
	</resultMap>
	
	<select id="selectByParams" resultMap="baseResultMap" >
		select * from bb_test where 1=1
		<if test="oid != null">
			AND OID = #{oid}
		</if>		
		<if test="id != null">
			AND ID = #{id}
		</if>
		<if test="title != null">
			AND TITLE like #{title}
		</if>						
	</select>
	
	<select id="selectByValue" resultMap="baseResultMap" >
		select * from bb_test where 1=1
		<if test="oid != null">
			AND OID = #{oid}
		</if>
		<if test="id != null">
			AND ID = #{id}
		</if>
	</select>	
	
</mapper>
```

### Config /resource/applicationContext/core/applicationContext-mybatis.xml
```xml
<value>classpath*:mappers/BbTest.xml</value>
```

<br/>
<br/>
<br/>

### Dynamic HQL from XML config
reference:<br/>
for KPI page grid query:<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-persistence/resource/dynamichql/BbKpi-dynamic-hql.xml
<br/>
for Vision page grid query:<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-persistence/resource/dynamichql/BbVision-dynamic-hql.xml

