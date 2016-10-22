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
import com.netsteadfast.greenstep.bsc.dao.ISwotDAO;
import com.netsteadfast.greenstep.po.hbm.BbSwot;

@Repository("bsc.dao.SwotDAO")
@Scope("prototype")
public class SwotDAOImpl extends BaseDAO<BbSwot, String> implements ISwotDAO<BbSwot, String> {
	
	public SwotDAOImpl() {
		super();
	}

	@Override
	public int deleteForVisId(String visId) throws Exception {
		return this.getCurrentSession().createQuery("delete BbSwot WHERE visId = :visId ").setString("visId", visId).executeUpdate();
	}

	@Override
	public int deleteForPerId(String perId) throws Exception {
		return this.getCurrentSession().createQuery("delete BbSwot WHERE perId = :perId ").setString("perId", perId).executeUpdate();
	}

	@Override
	public int deleteForOrgId(String orgId) throws Exception {
		return this.getCurrentSession().createQuery("delete BbSwot WHERE orgId = :orgId ").setString("orgId", orgId).executeUpdate();
	}
	
}
