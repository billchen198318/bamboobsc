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
package com.netsteadfast.greenstep.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.netsteadfast.greenstep.base.dao.BaseDAO;
import com.netsteadfast.greenstep.dao.ISysExpressionDAO;
import com.netsteadfast.greenstep.po.hbm.TbSysExpression;
import com.netsteadfast.greenstep.vo.SysExpressionVO;

@Repository("core.dao.SysExpressionDAO")
public class SysExpressionDAOImpl extends BaseDAO<TbSysExpression, String> implements ISysExpressionDAO<TbSysExpression, String> {
	
	public SysExpressionDAOImpl() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysExpressionVO> findListForSimple() throws Exception {
		return this.getCurrentSession().createQuery(
				"SELECT new com.netsteadfast.greenstep.vo.SysExpressionVO(m.oid, m.exprId, m.type, m.name) FROM TbSysExpression m ORDER BY m.exprId ASC ")
				.list();
	}
	
}
