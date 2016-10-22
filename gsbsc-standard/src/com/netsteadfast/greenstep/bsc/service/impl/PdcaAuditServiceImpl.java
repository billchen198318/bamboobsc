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
package com.netsteadfast.greenstep.bsc.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.dao.IBaseDAO;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.service.BaseService;
import com.netsteadfast.greenstep.bsc.dao.IPdcaAuditDAO;
import com.netsteadfast.greenstep.po.hbm.BbPdcaAudit;
import com.netsteadfast.greenstep.bsc.service.IPdcaAuditService;
import com.netsteadfast.greenstep.vo.PdcaAuditVO;

@Service("bsc.service.PdcaAuditService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class PdcaAuditServiceImpl extends BaseService<PdcaAuditVO, BbPdcaAudit, String> implements IPdcaAuditService<PdcaAuditVO, BbPdcaAudit, String> {
	protected Logger logger=Logger.getLogger(PdcaAuditServiceImpl.class);
	private IPdcaAuditDAO<BbPdcaAudit, String> pdcaAuditDAO;
	
	public PdcaAuditServiceImpl() {
		super();
	}

	public IPdcaAuditDAO<BbPdcaAudit, String> getPdcaAuditDAO() {
		return pdcaAuditDAO;
	}

	@Autowired
	@Resource(name="bsc.dao.PdcaAuditDAO")
	@Required		
	public void setPdcaAuditDAO(
			IPdcaAuditDAO<BbPdcaAudit, String> pdcaAuditDAO) {
		this.pdcaAuditDAO = pdcaAuditDAO;
	}

	@Override
	protected IBaseDAO<BbPdcaAudit, String> getBaseDataAccessObject() {
		return pdcaAuditDAO;
	}

	@Override
	public String getMapperIdPo2Vo() {		
		return MAPPER_ID_PO2VO;
	}

	@Override
	public String getMapperIdVo2Po() {
		return MAPPER_ID_VO2PO;
	}

	@Override
	public int findForMaxConfirmSeq(String pdcaOid) throws ServiceException, Exception {
		if (StringUtils.isBlank(pdcaOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		Integer maxSeq = this.pdcaAuditDAO.findForMaxConfirmSeq(pdcaOid);
		if (maxSeq == null) {
			maxSeq = 0;
		}
		return maxSeq;
	}

	@Override
	public BbPdcaAudit findForLast(String pdcaOid, String type) throws ServiceException, Exception {
		if (StringUtils.isBlank(pdcaOid) || StringUtils.isBlank(type)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}		
		return this.pdcaAuditDAO.findForLast(pdcaOid, type);
	}

}
