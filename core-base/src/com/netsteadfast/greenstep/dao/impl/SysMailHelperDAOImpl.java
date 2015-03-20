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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.netsteadfast.greenstep.base.dao.BaseDAO;
import com.netsteadfast.greenstep.dao.ISysMailHelperDAO;
import com.netsteadfast.greenstep.po.hbm.TbSysMailHelper;

@Repository("core.dao.SysMailHelperDAO")
@Scope("prototype")
public class SysMailHelperDAOImpl extends BaseDAO<TbSysMailHelper, String> implements ISysMailHelperDAO<TbSysMailHelper, String> {
	
	public SysMailHelperDAOImpl() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TbSysMailHelper> findForJobList(String mailId, String successFlag) throws Exception {
		return this.getCurrentSession()
				.createQuery("from TbSysMailHelper WHERE mailId LIKE :mailId AND successFlag = :successFlag ")
				.setString("mailId", mailId+"%")
				.setString("successFlag", successFlag)
				.setMaxResults(100)
				.list();
	}

	@Override
	public String findForMaxMailId(String mailId) throws Exception {
		return (String)this.getCurrentSession()
				.createQuery("SELECT max(m.mailId) FROM TbSysMailHelper m WHERE m.mailId LIKE :mailId ")
				.setString("mailId", mailId+"%")
				.uniqueResult();
	}
	
}
