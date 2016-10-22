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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.netsteadfast.greenstep.base.dao.BaseDAO;
import com.netsteadfast.greenstep.bsc.dao.IScoreColorDAO;
import com.netsteadfast.greenstep.po.hbm.BbScoreColor;

@Repository("bsc.dao.ScoreColorDAO")
@Scope("prototype")
public class ScoreColorDAOImpl extends BaseDAO<BbScoreColor, String> implements IScoreColorDAO<BbScoreColor, String> {
	
	public ScoreColorDAOImpl() {
		super();
	}

	@Override
	public Integer findForMaxValue() throws Exception {		
		return (Integer)this.getCurrentSession()
				.createQuery("SELECT max(m.scoreMax) FROM BbScoreColor m")
				.uniqueResult();
	}
	
}
