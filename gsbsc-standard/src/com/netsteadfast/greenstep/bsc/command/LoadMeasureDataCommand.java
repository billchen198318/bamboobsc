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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.base.model.CustomeOperational;
import com.netsteadfast.greenstep.bsc.model.BscMeasureDataFrequency;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.bsc.service.IMeasureDataService;
import com.netsteadfast.greenstep.po.hbm.BbMeasureData;
import com.netsteadfast.greenstep.util.SimpleUtils;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.MeasureDataVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

public class LoadMeasureDataCommand extends BaseChainCommandSupport implements Command {
	private IMeasureDataService<MeasureDataVO, BbMeasureData, String> measureDataService;

	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Context context) throws Exception {
		measureDataService = (IMeasureDataService<MeasureDataVO, BbMeasureData, String>)
				AppContext.getBean("bsc.service.MeasureDataService");
		String frequency = (String)context.get("frequency");
		String startYearDate = StringUtils.defaultString( (String)context.get("startYearDate") ).trim();
		String endYearDate = StringUtils.defaultString( (String)context.get("endYearDate") ).trim();
		String startDate = StringUtils.defaultString( (String)context.get("startDate") ).trim();
		String endDate = StringUtils.defaultString( (String)context.get("endDate") ).trim();
		startDate = startDate.replaceAll("/", "").replaceAll("-", "");
		endDate = endDate.replaceAll("/", "").replaceAll("-", "");
		String date1 = startDate;
		String date2 = endDate;		
		if (BscMeasureDataFrequency.FREQUENCY_QUARTER.equals(frequency) 
				|| BscMeasureDataFrequency.FREQUENCY_HALF_OF_YEAR.equals(frequency)
				|| BscMeasureDataFrequency.FREQUENCY_YEAR.equals(frequency) ) {
			date1 = startYearDate + "0101";
			date2 = endYearDate + "12" + SimpleUtils.getMaxDayOfMonth(Integer.parseInt(endYearDate), 12);			
		}		
		String measureDataOrgaId = (String)context.get("orgId");
		String measureDataEmplId = (String)context.get("empId");
		if (this.getResult(context)==null || !(this.getResult(context) instanceof BscStructTreeObj) ) {
			return false;
		}
		BscStructTreeObj treeObj = (BscStructTreeObj)this.getResult(context);		
		for (VisionVO vision : treeObj.getVisions()) {
			for (PerspectiveVO perspective : vision.getPerspectives()) {
				for (ObjectiveVO objective : perspective.getObjectives()) {
					for (KpiVO kpi : objective.getKpis()) {
						this.fillMeasureData(kpi, frequency, date1, date2, measureDataOrgaId, measureDataEmplId);
					}
				}
			}
		}
		return false;
	}
	
	private void fillMeasureData(KpiVO kpi, String frequency, String startDate, String endDate, 
			String orgId, String empId) throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, CustomeOperational> customOperParams = new HashMap<String, CustomeOperational>();
		params.put("kpiId", kpi.getId());
		params.put("frequency", frequency);
		params.put("orgId", orgId);
		params.put("empId", empId);
		CustomeOperational op1 = new CustomeOperational();
		op1.setField("date");
		op1.setOp(">=");
		op1.setValue(startDate);
		CustomeOperational op2 = new CustomeOperational();
		op2.setField("date");
		op2.setOp("<=");
		op2.setValue(endDate);
		customOperParams.put("op1", op1);
		customOperParams.put("op2", op2);
		List<BbMeasureData> measureDatas = this.measureDataService.findListByParams2(params, customOperParams);
		kpi.setMeasureDatas(measureDatas);		
	}
	
}
