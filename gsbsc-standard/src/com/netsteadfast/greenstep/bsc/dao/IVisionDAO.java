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
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.vo.VisionVO;

public interface IVisionDAO<T extends java.io.Serializable, PK extends java.io.Serializable> extends IBaseDAO<BbVision, String> {
	
	public String findForMaxVisId(String visId) throws Exception;
	
	public VisionVO findForSimple(String oid) throws Exception;
	
	public List<VisionVO> findForSimple() throws Exception;
	
	public VisionVO findForSimpleByVisId(String visId) throws Exception;
	
	public List<String> findForOidByKpiOrga(String orgId) throws Exception;
	
	public List<String> findForOidByPdcaOid(String pdcaOid) throws Exception;
	
}
