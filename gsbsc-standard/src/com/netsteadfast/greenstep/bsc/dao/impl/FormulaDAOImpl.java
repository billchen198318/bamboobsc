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
package com.netsteadfast.greenstep.bsc.dao.impl;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.netsteadfast.greenstep.base.dao.BaseDAO;
import com.netsteadfast.greenstep.bsc.dao.IFormulaDAO;
import com.netsteadfast.greenstep.po.hbm.BbFormula;
import com.netsteadfast.greenstep.vo.FormulaVO;

@Repository("bsc.dao.FormulaDAO")
@Scope("prototype")
public class FormulaDAOImpl extends BaseDAO<BbFormula, String> implements IFormulaDAO<BbFormula, String> {
	
	public FormulaDAOImpl() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FormulaVO> findForSimple(String trendsFlag) throws Exception {
		return this.getCurrentSession()
				.createQuery("SELECT new com.netsteadfast.greenstep.vo.FormulaVO(m.oid, m.forId, m.name, m.type, m.trendsFlag) FROM BbFormula m WHERE trendsFlag = :trendsFlag ORDER BY m.forId ASC")
				.setString("trendsFlag", trendsFlag)
				.list();			
	}
	
}
