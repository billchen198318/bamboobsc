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
import com.netsteadfast.greenstep.bsc.dao.IDegreeFeedbackProjectDAO;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackProject;

@Repository("bsc.dao.DegreeFeedbackProjectDAO")
@Scope("prototype")
public class DegreeFeedbackProjectDAOImpl extends BaseDAO<BbDegreeFeedbackProject, String> implements IDegreeFeedbackProjectDAO<BbDegreeFeedbackProject, String> {
	
	public DegreeFeedbackProjectDAOImpl() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BbDegreeFeedbackProject> findByPublishFlag(String publishFlag, String raterId) throws Exception {
		return this.getCurrentSession()
				.createQuery("FROM BbDegreeFeedbackProject m WHERE m.publishFlag = :publishFlag AND m.oid IN ( SELECT DISTINCT b.projectOid FROM BbDegreeFeedbackAssign b WHERE b.raterId = :raterId ) ORDER BY m.year DESC, m.name ASC")
				.setString("publishFlag", publishFlag)
				.setString("raterId", raterId)
				.setMaxResults(100)
				.list();
	}
	
}
