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
package com.netsteadfast.greenstep.qcharts.dao.impl;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.netsteadfast.greenstep.base.dao.BaseDAO;
import com.netsteadfast.greenstep.qcharts.dao.IDataQueryDAO;
import com.netsteadfast.greenstep.vo.DataQueryVO;
import com.netsteadfast.greenstep.po.hbm.QcDataQuery;

@Repository("qcharts.dao.DataQueryDAO")
@Scope("prototype")
public class DataQueryDAOImpl extends BaseDAO<QcDataQuery, String> implements IDataQueryDAO<QcDataQuery, String> {
	
	public DataQueryDAOImpl() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DataQueryVO> findForSimple() throws Exception {
		
		return this.getCurrentSession()
				.createQuery("select new com.netsteadfast.greenstep.vo.DataQueryVO(m.oid, m.name) from QcDataQuery m ")
				.list();
	}
	
}
