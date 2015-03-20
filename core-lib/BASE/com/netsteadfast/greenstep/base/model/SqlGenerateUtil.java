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
package com.netsteadfast.greenstep.base.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

/**
 * 產生基本的SQL 使用jdbctemplate時用到,不能用再太複雜的情況下
 *
 */
public class SqlGenerateUtil implements SqlGenerate {
	
	public static Map<String, Object> getInsert(Object entityObject) throws Exception {
		String tableName=getTableName(entityObject);
		Map<String, Object> queryMap=new HashMap<String, Object>();
		Map<String, Object> fieldMap=getField(entityObject);
		if ("".equals(tableName.trim()) || fieldMap==null || fieldMap.size()<1 ) {
			throw new java.lang.IllegalArgumentException(NOT_ENTITY_BEAN);		
		}
		int field=0;
		StringBuilder sql=new StringBuilder();
		Object params[]=new Object[fieldMap.size()];
		sql.append(" insert into ").append(tableName).append(" ( ");
		for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
			params[field]=entry.getValue();
			field++;
			sql.append(" ").append(entry.getKey());						
			if (field<fieldMap.size()) {
				sql.append(", ");
			}
		}			
		sql.append(" ) ");
		sql.append(" values ( ");
		for (int ix=1; ix<=params.length; ix++) {
			sql.append(" ").append("?");					
			if (ix<fieldMap.size()) {
				sql.append(", ");
			}			
		}
		sql.append(" ) ");
		queryMap.put(RETURN_SQL, sql.toString());
		queryMap.put(RETURN_PARAMS, params);
		return queryMap;
	}
	
	public static Map<String, Object> getCountByPK(Object entityObject) throws Exception {
		return getByPK("count(*)", entityObject);
	}	
	
	public static Map<String, Object> getFindByPK(Object entityObject, String... fields) throws Exception {
		return getByPK(getSelectFields(fields), entityObject);
	}
	
	public static Map<String, Object> getCountByUK(Object entityObject) throws Exception {	
		return getByUK("count(*)", entityObject);		
	}
	
	public static Map<String, Object> getFindByUK(Object entityObject, String... fields) throws Exception {		
		return getByUK(getSelectFields(fields), entityObject);
	}	
	
	private static String getSelectFields(String... fields) {
		if (fields==null || fields.length<1) {
			return "*";
		}		
		StringBuilder f=new StringBuilder();
		for (int ix=0; ix<fields.length; ix++) {
			if (ix>0) {
				f.append(", ");
			}
			f.append(fields[ix]);
		}
		return f.toString();
	}
	
	public static Map<String, Object> getFindByParams(Object entityObject) throws Exception {
		String tableName=getTableName(entityObject);
		Map<String, Object> fieldMap=getNewFieldMap(getField(entityObject));
		if (!checkValueParams(fieldMap)) {
			throw new java.lang.IllegalArgumentException(NOT_ENTITY_BEAN);		
		}	
		Map<String, Object> queryMap=new HashMap<String, Object>();
		int field=0;
		StringBuilder sql=new StringBuilder();
		Object params[]=new Object[fieldMap.size()];
		sql.append(" select * from ").append(tableName).append(" where 1=1 ");
		for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {	
			params[field]=entry.getValue();
			sql.append(" and ").append(entry.getKey()).append("=? ");
			field++;
		}
		queryMap.put(RETURN_SQL, sql.toString());
		queryMap.put(RETURN_PARAMS, params);		
		return queryMap;
	}
	
	public static Map<String, Object> getDeleteByPK(Object entityObject) throws Exception {
		String tableName=getTableName(entityObject);
		String pk=getPKname(entityObject);
		Object value=getPKvalue(entityObject);
		if ("".equals(tableName.trim()) || null==pk 
				|| "".equals(pk) || null==value) {
			throw new java.lang.IllegalArgumentException(NOT_ENTITY_BEAN);	
		}
		String sql=" delete from " + tableName + " where " + pk + "=?";
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put(RETURN_SQL, sql.toString());
		queryMap.put(RETURN_PARAMS, new Object[]{value});
		return queryMap;		
	}
	
	public static Map<String, Object> getUpdateByPK(boolean allField, Object entityObject) throws Exception {
		String tableName=getTableName(entityObject);
		String pk=getPKname(entityObject);
		Object value=getPKvalue(entityObject);
		if ("".equals(tableName.trim()) || null==pk 
				|| "".equals(pk) || null==value) {
			throw new java.lang.IllegalArgumentException(NOT_ENTITY_BEAN);	
		}
		Map<String, Object> fieldMap=getField(entityObject);
		if (!allField) {
			fieldMap=getNewFieldMap(fieldMap);
		}		
		fieldMap.remove(pk);	
		if (!checkValueParams(fieldMap)) {
			throw new java.lang.IllegalArgumentException(NOT_ENTITY_BEAN);		
		}	
		Map<String, Object> queryMap=new HashMap<String, Object>();
		int field=0;
		StringBuilder sql=new StringBuilder();
		Object params[]=new Object[fieldMap.size()+1];
		sql.append(" update ").append(tableName).append(" set ");
		for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {	
			params[field]=entry.getValue();
			field++;
			sql.append(" ").append(entry.getKey()).append("=?");			
			if (field<fieldMap.size()) {
				sql.append(", ");
			}
		}			
		sql.append(" where ").append(pk).append("=?");
		params[field]=value;
		queryMap.put(RETURN_SQL, sql.toString());
		queryMap.put(RETURN_PARAMS, params);
		return queryMap;		
	}
	
	private static Map<String, Object> getNewFieldMap(Map<String, Object> fieldMap) throws Exception {
		Map<String, Object> newFieldMap=new HashMap<String, Object>();
		if (fieldMap==null || fieldMap.size()<1) {
			return newFieldMap;
		}
		for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {	
			if (entry.getValue()!=null) {
				newFieldMap.put(entry.getKey(), entry.getValue());
			}
		}
		return newFieldMap;
	}
	
	private static boolean checkValueParams(Map<String, Object> params) throws Exception {
		boolean status=false;
		if (params==null || params.size()<1) {
			return status;
		}
		for (Map.Entry<String, Object> entry : params.entrySet()) {	
			if (entry.getValue()!=null) {
				status=true;
			}
		}
		return status;
	}
	
	private static Map<String, Object> getByPK(String fieldName, Object entityObject) throws Exception {
		String tableName=getTableName(entityObject);
		String pk=getPKname(entityObject);
		Object value=getPKvalue(entityObject);
		if ("".equals(tableName.trim()) || null==pk 
				|| "".equals(pk) || null==value) {
			throw new java.lang.IllegalArgumentException(NOT_ENTITY_BEAN);	
		}
		String sql=" select " + fieldName + " from " + tableName + " where " + pk + "=?";
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put(RETURN_SQL, sql.toString());
		queryMap.put(RETURN_PARAMS, new Object[]{value});
		return queryMap;
	}	
	
	private static Map<String, Object> getByUK(String fieldName, Object entityObject) throws Exception {	
		String tableName=getTableName(entityObject);
		Map<String, Object> ukMap=getUKParameter(entityObject);
		if ("".equals(tableName.trim()) || ukMap==null || ukMap.size()<1) {
			throw new java.lang.IllegalArgumentException(NOT_ENTITY_BEAN);	
		}
		int field=0;
		StringBuilder sql=new StringBuilder();
		Object params[]=new Object[ukMap.size()];
		sql.append(" select " + fieldName + " from " + tableName + " where 1=1 ");
		for (Map.Entry<String, Object> entry : ukMap.entrySet()) {
			params[field]=entry.getValue();
			sql.append(" and ").append(entry.getKey()).append("=? ");
			field++;	
		}
		Map<String, Object> queryMap=new HashMap<String, Object>();
		queryMap.put(RETURN_SQL, sql.toString());
		queryMap.put(RETURN_PARAMS, params);
		return queryMap;			
	}
	
	private static Map<String, Object> getUKParameter(Object entityObject) {
		Method[] methods=entityObject.getClass().getMethods();
		if (methods==null) {
			return null;
		}
		Map<String, Object> ukMap=new HashMap<String, Object>();
		for (int ix=0; ix<methods.length; ix++) {
			Annotation[] annotations=methods[ix].getDeclaredAnnotations();
			if (annotations==null) {
				continue;
			}
			for(Annotation annotation : annotations) {
				if(annotation instanceof EntityUK) {
					if (methods[ix].getName().indexOf("get")==0) {
						for (int nx=0; nx<annotations.length; nx++) {						
							if (annotations[nx] instanceof Column) {
								try {
									ukMap.put(((Column)annotations[nx]).name(), methods[ix].invoke(entityObject));
									nx=annotations.length;
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}
							}
						}					
					}					
				}
			}
		}		
		return ukMap;
	}	
	
	private static Object getPKvalue(Object entityObject) {		
		Method[] methods=entityObject.getClass().getMethods();	
		if (methods==null) {
			return null;
		}
		Object value=null;
		for (int ix=0; ix<methods.length && value==null; ix++) {
			Annotation[] annotations=methods[ix].getDeclaredAnnotations();
			if (annotations==null) {
				continue;
			}
			for(Annotation annotation : annotations) {
				if(annotation instanceof Id) {			
					if (methods[ix].getName().indexOf("get")==0) {
						try {
							value=methods[ix].invoke(entityObject);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return value;
	}
	
	private static String getPKname(Object entityObject) {
		Method[] methods=entityObject.getClass().getMethods();
		Field[] fields=entityObject.getClass().getDeclaredFields();
		if (methods==null || fields==null) {
			return null;
		}
		String name=null;
		for (int ix=0; ix<methods.length && name==null; ix++) {
			Annotation[] annotations=methods[ix].getDeclaredAnnotations();
			if (annotations==null) {
				continue;
			}
			for (int ax=0; ax<annotations.length && name==null; ax++) {				
				if(annotations[ax] instanceof Id) {
					for (int nx=0; nx<annotations.length && name==null; nx++) {						
						if (annotations[nx] instanceof Column) {
							if (methods[ix].getName().indexOf("get")==0) {
								name=((Column)annotations[nx]).name();
							}
						}
					}
				}
			}
		}
		return name;
	}	
	
	private static Map<String, Object> getField(Object entityObject) throws Exception {
		
		Map<String, Object> fieldMap=new HashMap<String, Object>();
		Method[] methods=entityObject.getClass().getMethods();
		for (int ix=0; ix<methods.length; ix++) {
			Annotation[] annotations=methods[ix].getDeclaredAnnotations();
			if (annotations==null) {
				continue;
			}
			for(Annotation annotation : annotations) {
				if (annotation instanceof Column) {
					if (methods[ix].getName().indexOf("get")!=0) {
						continue;
					}
					String column=StringUtils.defaultString(((Column)annotation).name());
					if ("".equals(column.trim())) {
						continue;
					}
					Object value=methods[ix].invoke(entityObject);
					fieldMap.put(column, value);
				}
			}
		}
		return fieldMap;
	}
	
	private static String getTableName(Object entityObject) throws Exception {
		String tableName="";
		Annotation annotations[]=entityObject.getClass().getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof Table) {
				tableName=StringUtils.defaultString(((Table)annotation).name());
			}
		}
		return tableName;
	}
}
