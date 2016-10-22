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
package com.netsteadfast.greenstep.bsc.command;

import java.util.List;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.BscConstants;
import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.bsc.service.IKpiService;
import com.netsteadfast.greenstep.bsc.vo.BscMixDataVO;
import com.netsteadfast.greenstep.po.hbm.BbKpi;
import com.netsteadfast.greenstep.vo.KpiVO;

public class LoadBscMixDataCommand extends BaseChainCommandSupport implements Command {
	private IKpiService<KpiVO, BbKpi, String> kpiService;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Context context) throws Exception {
		kpiService = (IKpiService<KpiVO, BbKpi, String>)AppContext.getBean("bsc.service.KpiService");		
		String orgId = (String)context.get("orgId");
		String empId = (String)context.get("empId");
		List<String> kpiIds = (List<String>)context.get("kpiIds");
		if (BscConstants.MEASURE_DATA_ORGANIZATION_FULL.equals(orgId)) {
			orgId = "";
		}
		if (BscConstants.MEASURE_DATA_EMPLOYEE_FULL.equals(empId)) {
			empId = "";
		}
		DefaultResult<List<BscMixDataVO>> result = kpiService.findForMixData( 
				(String)context.get("visionOid"), 
				orgId, 
				empId,
				StringUtils.defaultString( (String)context.get("nextType") ),
				StringUtils.defaultString( (String)context.get("nextId")),
				kpiIds);
		if (result.getValue()!=null) {
			this.setResult(context, result.getValue());
		} else {
			this.setMessage(context, result.getSystemMessage().getValue());
		}
		return false;
	}	

}
