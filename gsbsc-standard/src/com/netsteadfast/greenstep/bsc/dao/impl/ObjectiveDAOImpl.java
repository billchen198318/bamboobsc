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
import com.netsteadfast.greenstep.bsc.dao.IObjectiveDAO;
import com.netsteadfast.greenstep.po.hbm.BbObjective;
import com.netsteadfast.greenstep.vo.ObjectiveVO;

@Repository("bsc.dao.ObjectiveDAO")
@Scope("prototype")
public class ObjectiveDAOImpl extends BaseDAO<BbObjective, String> implements IObjectiveDAO<BbObjective, String> {
	
	public ObjectiveDAOImpl() {
		super();
	}

	@Override
	public String findForMaxObjId(String objId) throws Exception {
		return (String)this.getCurrentSession()
				.createQuery("SELECT max(m.objId) FROM BbObjective m WHERE m.objId LIKE :objId ")
				.setString("objId", objId+"%")
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ObjectiveVO> findForListByPerspectiveOid(String perspectiveOid) throws Exception {
		return this.getCurrentSession()
				.createQuery("SELECT new com.netsteadfast.greenstep.vo.ObjectiveVO(m.oid, m.objId, m.perId, m.name) FROM BbObjective m WHERE m.perId IN ( SELECT p.perId FROM BbPerspective p WHERE p.oid = :oid ) ORDER BY m.objId ASC")
				.setString("oid", perspectiveOid)
				.list();
	}
	
}
