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
import com.netsteadfast.greenstep.bsc.dao.IPerspectiveDAO;
import com.netsteadfast.greenstep.po.hbm.BbPerspective;
import com.netsteadfast.greenstep.vo.PerspectiveVO;

@Repository("bsc.dao.PerspectiveDAO")
@Scope("prototype")
public class PerspectiveDAOImpl extends BaseDAO<BbPerspective, String> implements IPerspectiveDAO<BbPerspective, String> {
	
	public PerspectiveDAOImpl() {
		super();
	}

	@Override
	public String findForMaxPerId(String perId) throws Exception {
		return (String)this.getCurrentSession()
				.createQuery("SELECT max(m.perId) FROM BbPerspective m WHERE m.perId LIKE :perId ")
				.setString("perId", perId+"%")
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PerspectiveVO> findForListByVisionOid(String visionOid) throws Exception {
		return this.getCurrentSession()
				.createQuery("SELECT new com.netsteadfast.greenstep.vo.PerspectiveVO(m.oid, m.perId, m.visId, m.name) FROM BbPerspective m WHERE m.visId IN ( SELECT v.visId FROM BbVision v WHERE v.oid = :oid ) ORDER BY m.perId ASC")
				.setString("oid", visionOid)
				.list();
	}
	
}
