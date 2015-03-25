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
package com.netsteadfast.greenstep.bsc.dao;

import java.util.List;

import com.netsteadfast.greenstep.base.dao.IBaseDAO;
import com.netsteadfast.greenstep.bsc.vo.BscMixDataVO;
import com.netsteadfast.greenstep.po.hbm.BbKpi;

public interface IKpiDAO<T extends java.io.Serializable, PK extends java.io.Serializable> extends IBaseDAO<BbKpi, String> {
	
	/**
	 * select 
	 * k.OID, k.ID, k.NAME, k.DESCRIPTION, k.WEIGHT, k.UNIT, k.TARGET, k.MIN,
	 * k.MANAGEMENT, k.COMPARE_TYPE, k.CAL, k.DATA_TYPE, k.ORGA_MEASURE_SEPARATE, k.USER_MEASURE_SEPARATE,
	 * k.QUASI_RANGE,
	 * o.OID, o.OBJ_ID, o.NAME, o.WEIGHT, o.DESCRIPTION, o.TARGET, o.MIN,
	 * p.OID, p.PER_ID, p.NAME, p.WEIGHT, p.DESCRIPTION, p.TARGET, p.MIN,
	 * v.OID, v.VIS_ID, v.TITLE, 
	 * f.OID, f.FOR_ID, f.NAME, f.TYPE, f.RETURN_MODE, f.RETURN_VAR, f.EXPRESSION,
	 * aggr.OID, aggr.AGGR_ID, aggr.AGGR_NAME, aggr.TYPE, aggr.EXPRESSION1, aggr.EXPRESSION2
	 * from bb_kpi k, bb_objective o, bb_perspective p, bb_vision v, bb_formula f, bb_aggregation_method aggr
	 * where k.OBJ_ID = o.OBJ_ID
	 * and o.PER_ID = p.PER_ID
	 * and p.VIS_ID = v.VIS_ID
	 * and k.FOR_ID = f.FOR_ID
	 * and k.CAL = aggr.AGGR_ID
	 * ORDER BY v.VIS_ID, p.PER_ID, o.OBJ_ID, k.ID ASC
	 * ;
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<BscMixDataVO> findForMixData(String visionOid, String orgId, String empId, String nextType, String nextId) throws Exception;
	
	public int countForMixData(String visionOid, String orgId, String empId, String nextType, String nextId) throws Exception;
	
}
