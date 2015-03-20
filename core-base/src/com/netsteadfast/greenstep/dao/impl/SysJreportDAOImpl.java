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

import org.springframework.stereotype.Repository;

import com.netsteadfast.greenstep.base.dao.BaseDAO;
import com.netsteadfast.greenstep.dao.ISysJreportDAO;
import com.netsteadfast.greenstep.po.hbm.TbSysJreport;
import com.netsteadfast.greenstep.vo.SysJreportVO;

@Repository("core.dao.SysJreportDAO")
public class SysJreportDAOImpl extends BaseDAO<TbSysJreport, String> implements ISysJreportDAO<TbSysJreport, String> {
	
	public SysJreportDAOImpl() {
		super();
	}

	@Override
	public SysJreportVO findForSimple(String oid) throws Exception {
		return (SysJreportVO)this.getCurrentSession().createQuery(
				"SELECT new com.netsteadfast.greenstep.vo.SysJreportVO(m.oid, m.reportId, m.file, m.isCompile, m.description) FROM TbSysJreport m WHERE m.oid = :oid ")
				.setString("oid", oid)
				.uniqueResult();
	}

	@Override
	public SysJreportVO findForSimpleByReportId(String reportId) throws Exception {
		return (SysJreportVO)this.getCurrentSession().createQuery(
				"SELECT new com.netsteadfast.greenstep.vo.SysJreportVO(m.oid, m.reportId, m.file, m.isCompile, m.description) FROM TbSysJreport m WHERE m.reportId = :reportId ")
				.setString("reportId", reportId)
				.uniqueResult();
	}
	
}
