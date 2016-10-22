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

import java.util.List;

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
import com.netsteadfast.greenstep.bsc.dao.IDegreeFeedbackLevelDAO;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackLevel;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackLevelService;
import com.netsteadfast.greenstep.vo.DegreeFeedbackLevelVO;

@Service("bsc.service.DegreeFeedbackLevelService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class DegreeFeedbackLevelServiceImpl extends BaseService<DegreeFeedbackLevelVO, BbDegreeFeedbackLevel, String> implements IDegreeFeedbackLevelService<DegreeFeedbackLevelVO, BbDegreeFeedbackLevel, String> {
	protected Logger logger=Logger.getLogger(DegreeFeedbackLevelServiceImpl.class);
	private IDegreeFeedbackLevelDAO<BbDegreeFeedbackLevel, String> degreeFeedbackLevelDAO;
	
	public DegreeFeedbackLevelServiceImpl() {
		super();
	}

	public IDegreeFeedbackLevelDAO<BbDegreeFeedbackLevel, String> getDegreeFeedbackLevelDAO() {
		return degreeFeedbackLevelDAO;
	}

	@Autowired
	@Resource(name="bsc.dao.DegreeFeedbackLevelDAO")
	@Required		
	public void setDegreeFeedbackLevelDAO(
			IDegreeFeedbackLevelDAO<BbDegreeFeedbackLevel, String> degreeFeedbackLevelDAO) {
		this.degreeFeedbackLevelDAO = degreeFeedbackLevelDAO;
	}

	@Override
	protected IBaseDAO<BbDegreeFeedbackLevel, String> getBaseDataAccessObject() {
		return degreeFeedbackLevelDAO;
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
	public BbDegreeFeedbackLevel findForMinByProject(String projectOid) throws ServiceException, Exception {
		if (StringUtils.isBlank(projectOid)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		List<BbDegreeFeedbackLevel> list = this.degreeFeedbackLevelDAO.findForMinByProject(projectOid);
		if (list==null || list.size()!=1) {
			return null;
		} 
		return list.get(0);
	}

}
