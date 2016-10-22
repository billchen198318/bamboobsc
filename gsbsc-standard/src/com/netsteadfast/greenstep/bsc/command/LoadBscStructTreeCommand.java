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
import java.util.Map;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import com.netsteadfast.greenstep.base.BaseChainCommandSupport;
import com.netsteadfast.greenstep.bsc.model.BscKpiCode;
import com.netsteadfast.greenstep.bsc.model.BscStructTreeObj;
import com.netsteadfast.greenstep.bsc.util.AggregationMethodUtils;
import com.netsteadfast.greenstep.bsc.vo.BscMixDataVO;
import com.netsteadfast.greenstep.vo.AggregationMethodVO;
import com.netsteadfast.greenstep.vo.FormulaVO;
import com.netsteadfast.greenstep.vo.KpiVO;
import com.netsteadfast.greenstep.vo.ObjectiveVO;
import com.netsteadfast.greenstep.vo.PerspectiveVO;
import com.netsteadfast.greenstep.vo.VisionVO;

public class LoadBscStructTreeCommand extends BaseChainCommandSupport implements Command {

	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Context context) throws Exception {		
		if (this.getResult(context)==null || !(this.getResult(context) instanceof List) ) {
			return false;
		}
		List<BscMixDataVO> mixDatas = (List<BscMixDataVO>)this.getResult(context);
		BscStructTreeObj treeObj = this.processTree(mixDatas);
		this.calculateRowspan(treeObj);
		this.setResult(context, treeObj);		
		context.put("treeObj", treeObj);
		return false;
	}
	
	private void calculateRowspan(BscStructTreeObj treeObj) throws Exception {		
		for (VisionVO vision : treeObj.getVisions()) {
			int vRow = 0;			
			for (PerspectiveVO perspective : vision.getPerspectives() ) {
				int pRow = 0;				
				for (ObjectiveVO objective : perspective.getObjectives() ) {
					vRow += objective.getKpis().size();
					pRow += objective.getKpis().size();
					objective.setRow( objective.getKpis().size() );
				}				
				perspective.setRow(pRow);
			}			
			vision.setRow(vRow);
		}		
	}
	
	private BscStructTreeObj processTree(List<BscMixDataVO> mixDatas) throws Exception {
		BscStructTreeObj treeObj = new BscStructTreeObj();
		treeObj.setBscMixDatas(mixDatas);
		this.processVision(treeObj, mixDatas);
		this.processPerspective(treeObj, mixDatas);
		this.processObjective(treeObj, mixDatas);		
		this.processKpi(treeObj, mixDatas);		
		return treeObj;
	}
	
	private void processVision(BscStructTreeObj treeObj, List<BscMixDataVO> mixDatas) throws Exception {
		for (BscMixDataVO mixData : mixDatas) {
			boolean found = false;
			for (int i=0; i<treeObj.getVisions().size(); i++) {
				if (treeObj.getVisions().get(i).getVisId().equals(mixData.getVisId()) ) {
					found = true;
				}
			}
			if (!found) {
				VisionVO vision = new VisionVO();
				vision.setOid(mixData.getVisOid());
				vision.setVisId(mixData.getVisId());
				vision.setTitle(mixData.getVisTitle());
				treeObj.getVisions().add(vision);
			}			
		}			
	}
	
	private void processPerspective(BscStructTreeObj treeObj, List<BscMixDataVO> mixDatas) throws Exception {
		for (VisionVO vision : treeObj.getVisions()) {
			for (BscMixDataVO mixData : mixDatas) {
				if (!vision.getVisId().equals(mixData.getVisId()) ) {
					continue;
				}					
				boolean found = false;
				for (int i=0; i<vision.getPerspectives().size(); i++) {
					if (vision.getPerspectives().get(i).getPerId().equals(mixData.getPerId()) ) {
						found = true;
					}
				}
				if (!found) {
					PerspectiveVO perspective = new PerspectiveVO();
					perspective.setOid( mixData.getPerOid() );
					perspective.setPerId( mixData.getPerId() );
					perspective.setVisId( mixData.getVisId() );
					perspective.setName( mixData.getPerName() );
					perspective.setWeight( mixData.getPerWeight() );
					perspective.setTarget( mixData.getPerTarget() );
					perspective.setMin( mixData.getPerMin() );
					perspective.setDescription( mixData.getPerDescription() );
					vision.getPerspectives().add(perspective);
				}
			}			
		}
	}
	
	private void processObjective(BscStructTreeObj treeObj, List<BscMixDataVO> mixDatas) throws Exception {
		for (VisionVO vision : treeObj.getVisions()) {
			for (PerspectiveVO perspective : vision.getPerspectives()) {
				for (BscMixDataVO mixData : mixDatas) {
					if (!vision.getVisId().equals(mixData.getVisId()) || !perspective.getPerId().equals(mixData.getPerId()) ) {
						continue;
					}
					boolean found = false;
					for (int i=0; i<perspective.getObjectives().size(); i++) {
						if (perspective.getObjectives().get(i).getObjId().equals(mixData.getObjId()) ) {
							found = true;
						}
					}
					if (!found) {
						ObjectiveVO objective = new ObjectiveVO();
						objective.setOid( mixData.getObjOid() );
						objective.setObjId( mixData.getObjId() );
						objective.setPerId( mixData.getPerId() );
						objective.setName( mixData.getObjName() );
						objective.setWeight( mixData.getObjWeight() );
						objective.setTarget( mixData.getObjTarget() );
						objective.setMin( mixData.getObjMin() );
						objective.setDescription( mixData.getObjDescription() );
						perspective.getObjectives().add(objective);
					}
				}
			}
		}
	}
	
	private void processKpi(BscStructTreeObj treeObj, List<BscMixDataVO> mixDatas) throws Exception {
		//Map<String, String> calculationMap = BscKpiCode.getCalculationMap(false);
		Map<String, String> managementMap = BscKpiCode.getManagementMap(false);
		for (VisionVO vision : treeObj.getVisions()) {
			for (PerspectiveVO perspective : vision.getPerspectives()) {
				for (ObjectiveVO objective : perspective.getObjectives()) {
					for (BscMixDataVO mixData : mixDatas) {
						if (!vision.getVisId().equals(mixData.getVisId()) 
								|| !perspective.getPerId().equals(mixData.getPerId()) 
								|| !objective.getObjId().equals(mixData.getObjId()) ) {
							continue;
						}
						boolean found = false;
						for (int i=0; i<objective.getKpis().size(); i++) {
							if (objective.getKpis().get(i).getId().equals(mixData.getKpiId()) ) {
								found = true;
							}
						}
						if (!found) {
							KpiVO kpi = new KpiVO();
							kpi.setOid( mixData.getKpiOid() );
							kpi.setId( mixData.getKpiId() );
							kpi.setCal( mixData.getKpiCal() );
							kpi.setCompareType( mixData.getKpiCompareType() );
							kpi.setDataType( mixData.getKpiDataType() );
							kpi.setDescription( mixData.getKpiDescription() );
							kpi.setForId( mixData.getForId() );
							kpi.setManagement( mixData.getKpiManagement() );
							kpi.setMax( mixData.getKpiMax() );
							kpi.setMin( mixData.getKpiMin() );
							kpi.setName( mixData.getKpiName() );
							kpi.setObjId( mixData.getObjId() );
							kpi.setOrgaMeasureSeparate( mixData.getKpiOrgaMeasureSeparate() );
							kpi.setTarget( mixData.getKpiTarget() );
							kpi.setUnit( mixData.getKpiUnit() );
							kpi.setUserMeasureSeparate( mixData.getKpiUserMeasureSeparate() );
							kpi.setWeight( mixData.getKpiWeight() );						
							kpi.setManagementName( managementMap.get(kpi.getManagement()) );
							//kpi.setCalculationName( calculationMap.get(kpi.getCal()) );
							kpi.setCalculationName( AggregationMethodUtils.getNameByAggrId(kpi.getCal()) );
							kpi.setQuasiRange( mixData.getKpiQuasiRange() );	
							kpi.setActivate( mixData.getKpiActivate() );
							FormulaVO formula = new FormulaVO();
							formula.setOid( mixData.getForOid() );
							formula.setForId( mixData.getForId() );
							formula.setName( mixData.getForName() );
							formula.setType( mixData.getForType() );
							formula.setReturnMode( mixData.getForReturnMode() );
							formula.setReturnVar( mixData.getForReturnVar() );							
							formula.setExpression( mixData.getForExpression() );
							kpi.setFormula(formula);
							AggregationMethodVO aggr = new AggregationMethodVO();
							aggr.setOid( mixData.getAggrOid() );
							aggr.setAggrId( mixData.getAggrId() );
							aggr.setName( mixData.getAggrName() );
							aggr.setType( mixData.getAggrType() );
							aggr.setExpression1( mixData.getAggrExpression1() );
							aggr.setExpression2( mixData.getAggrExpression2() );
							kpi.setAggregationMethod(aggr);							
							FormulaVO trendsFormula = new FormulaVO();
							trendsFormula.setOid( mixData.getTrendsForOid() );
							trendsFormula.setForId( mixData.getTrendsForId() );
							trendsFormula.setName( mixData.getTrendsForName() );
							trendsFormula.setType( mixData.getTrendsForType() );
							trendsFormula.setReturnMode( mixData.getTrendsForReturnMode() );
							trendsFormula.setReturnVar( mixData.getTrendsForReturnVar() );							
							trendsFormula.setExpression( mixData.getTrendsForExpression() );							
							kpi.setTrendsFormula(trendsFormula);							
							objective.getKpis().add(kpi);
						}
					}					
				}
			}
		}
	}

}
