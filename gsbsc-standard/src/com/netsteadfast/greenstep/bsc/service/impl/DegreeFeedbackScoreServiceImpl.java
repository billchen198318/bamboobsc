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
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.base.model.SystemMessage;
import com.netsteadfast.greenstep.base.service.BaseService;
import com.netsteadfast.greenstep.bsc.dao.IDegreeFeedbackScoreDAO;
import com.netsteadfast.greenstep.po.hbm.BbDegreeFeedbackScore;
import com.netsteadfast.greenstep.bsc.service.IDegreeFeedbackScoreService;
import com.netsteadfast.greenstep.vo.DegreeFeedbackScoreVO;

@Service("bsc.service.DegreeFeedbackScoreService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class DegreeFeedbackScoreServiceImpl extends BaseService<DegreeFeedbackScoreVO, BbDegreeFeedbackScore, String> implements IDegreeFeedbackScoreService<DegreeFeedbackScoreVO, BbDegreeFeedbackScore, String> {
	protected Logger logger=Logger.getLogger(DegreeFeedbackScoreServiceImpl.class);
	private IDegreeFeedbackScoreDAO<BbDegreeFeedbackScore, String> degreeFeedbackScoreDAO;
	
	public DegreeFeedbackScoreServiceImpl() {
		super();
	}

	public IDegreeFeedbackScoreDAO<BbDegreeFeedbackScore, String> getDegreeFeedbackScoreDAO() {
		return degreeFeedbackScoreDAO;
	}

	@Autowired
	@Resource(name="bsc.dao.DegreeFeedbackScoreDAO")
	@Required		
	public void setDegreeFeedbackScoreDAO(
			IDegreeFeedbackScoreDAO<BbDegreeFeedbackScore, String> degreeFeedbackScoreDAO) {
		this.degreeFeedbackScoreDAO = degreeFeedbackScoreDAO;
	}

	@Override
	protected IBaseDAO<BbDegreeFeedbackScore, String> getBaseDataAccessObject() {
		return degreeFeedbackScoreDAO;
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
	public DefaultResult<List<BbDegreeFeedbackScore>> findResultsByProjectAndOwner(String projectOid, String ownerId) throws Exception {
		if (StringUtils.isBlank(projectOid) || StringUtils.isBlank(ownerId)) {
			throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
		}
		List<BbDegreeFeedbackScore> searchList = this.degreeFeedbackScoreDAO.findForListByProjectAndOwner(projectOid, ownerId);
		DefaultResult<List<BbDegreeFeedbackScore>> result = new DefaultResult<List<BbDegreeFeedbackScore>>();
		if (null!=searchList && searchList.size()>0) {
			result.setValue(searchList);
		} else {
			result.setSystemMessage( new SystemMessage(SysMessageUtil.get(GreenStepSysMsgConstants.SEARCH_NO_DATA)) );
		}
		return result;
	}

}
