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
import com.netsteadfast.greenstep.bsc.dao.IPdcaAuditDAO;
import com.netsteadfast.greenstep.po.hbm.BbPdcaAudit;

@Repository("bsc.dao.PdcaAuditDAO")
@Scope("prototype")
public class PdcaAuditDAOImpl extends BaseDAO<BbPdcaAudit, String> implements IPdcaAuditDAO<BbPdcaAudit, String> {
	
	public PdcaAuditDAOImpl() {
		super();
	}

	@Override
	public Integer findForMaxConfirmSeq(String pdcaOid) throws Exception {
		return (Integer)this.getCurrentSession()
				.createQuery("SELECT max(m.confirmSeq) FROM BbPdcaAudit m WHERE m.pdcaOid = :pdcaOid")
				.setString("pdcaOid", pdcaOid)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public BbPdcaAudit findForLast(String pdcaOid, String type) throws Exception {
		List<BbPdcaAudit> searchList = this.getCurrentSession()
				.createQuery("FROM BbPdcaAudit m WHERE m.pdcaOid = :pdcaOid AND m.type = :type ORDER BY m.confirmSeq DESC")
				.setString("pdcaOid", pdcaOid)
				.setString("type", type)
				.setMaxResults(1)
				.list();
		if (searchList != null && searchList.size() > 0) {
			return searchList.get(0);
		}
		return null;
	}
	
}
