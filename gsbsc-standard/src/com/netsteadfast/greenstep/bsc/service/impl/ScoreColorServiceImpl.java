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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.dao.IBaseDAO;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.service.BaseService;
import com.netsteadfast.greenstep.bsc.dao.IScoreColorDAO;
import com.netsteadfast.greenstep.po.hbm.BbScoreColor;
import com.netsteadfast.greenstep.bsc.service.IScoreColorService;
import com.netsteadfast.greenstep.vo.ScoreColorVO;

@Service("bsc.service.ScoreColorService")
@Scope("prototype")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class ScoreColorServiceImpl extends BaseService<ScoreColorVO, BbScoreColor, String> implements IScoreColorService<ScoreColorVO, BbScoreColor, String> {
	protected Logger logger=Logger.getLogger(ScoreColorServiceImpl.class);
	private IScoreColorDAO<BbScoreColor, String> scoreColorDAO;
	
	public ScoreColorServiceImpl() {
		super();
	}

	public IScoreColorDAO<BbScoreColor, String> getScoreColorDAO() {
		return scoreColorDAO;
	}

	@Autowired
	@Resource(name="bsc.dao.ScoreColorDAO")
	@Required		
	public void setScoreColorDAO(
			IScoreColorDAO<BbScoreColor, String> scoreColorDAO) {
		this.scoreColorDAO = scoreColorDAO;
	}

	@Override
	protected IBaseDAO<BbScoreColor, String> getBaseDataAccessObject() {
		return scoreColorDAO;
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
	public int findForMaxValue() throws ServiceException, Exception {
		Integer max = this.scoreColorDAO.findForMaxValue();
		if (max==null) {
			return BscConstants.SCORE_COLOR_MIN_VALUE;
		}
		return max.intValue();
	}

	// 2015-04-10 add
	@Cacheable(value="default")
	@Override
	public List<BbScoreColor> findListByParamsCacheable() throws Exception {
		return this.findListByParams(null);
	}

}
