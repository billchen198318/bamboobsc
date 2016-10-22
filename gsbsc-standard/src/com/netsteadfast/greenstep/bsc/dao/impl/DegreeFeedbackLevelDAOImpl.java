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
import com.netsteadfast.greenstep.bsc.dao.IDegreeFeedbackLevelDAO;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackLevel;

@Repository("bsc.dao.DegreeFeedbackLevelDAO")
@Scope("prototype")
public class DegreeFeedbackLevelDAOImpl extends BaseDAO<BbDegreeFeedbackLevel, String> implements IDegreeFeedbackLevelDAO<BbDegreeFeedbackLevel, String> {
	
	public DegreeFeedbackLevelDAOImpl() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbDegreeFeedbackLevel> findForMinByProject(String projectOid) throws Exception {
		return this.getCurrentSession()
				.createQuery("FROM BbDegreeFeedbackLevel WHERE projectOid = :projectOid ORDER BY value ASC")
				.setString("projectOid", projectOid)
				.setMaxResults(1)				
				.list();
	}
	
}
