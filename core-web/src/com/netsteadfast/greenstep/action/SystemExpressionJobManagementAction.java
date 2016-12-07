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
package com.netsteadfast.greenstep.action;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.model.ExpressionJobConstants;
import com.netsteadfast.greenstep.po.hbm.TbSys;
import com.netsteadfast.greenstep.po.hbm.TbSysExprJob;
import com.netsteadfast.greenstep.po.hbm.TbSysExpression;
import com.netsteadfast.greenstep.service.ISysExprJobService;
import com.netsteadfast.greenstep.service.ISysExpressionService;
import com.netsteadfast.greenstep.service.ISysService;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.SysExprJobVO;
import com.netsteadfast.greenstep.vo.SysExpressionVO;
import com.netsteadfast.greenstep.vo.SysVO;

@ControllerAuthority(check=true)
@Controller("core.web.controller.SystemExpressionJobManagementAction")
@Scope
public class SystemExpressionJobManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = 4726814777651844185L;
	private ISysService<SysVO, TbSys, String> sysService;
	private ISysExpressionService<SysExpressionVO, TbSysExpression, String> sysExpressionService;
	private ISysExprJobService<SysExprJobVO, TbSysExprJob, String> sysExprJobService;
	private Map<String, String> sysExprMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> systemMap = this.providedSelectZeroDataMap(true);
	private Map<String, String> runDayOfWeekMap = new LinkedHashMap<String, String>();
	private Map<String, String> runHourMap = new LinkedHashMap<String, String>();
	private Map<String, String> runMinuteMap = new LinkedHashMap<String, String>();
	private SysExprJobVO sysExprJob = new SysExprJobVO();
	
	public SystemExpressionJobManagementAction() {
		super();
	}
	
	public ISysService<SysVO, TbSys, String> getSysService() {
		return sysService;
	}

	@Autowired
	@Resource(name="core.service.SysService")
	@Required		
	public void setSysService(ISysService<SysVO, TbSys, String> sysService) {
		this.sysService = sysService;
	}	
	
	public ISysExpressionService<SysExpressionVO, TbSysExpression, String> getSysExpressionService() {
		return sysExpressionService;
	}
	
	@Autowired
	@Resource(name="core.service.SysExpressionService")
	@Required		
	public void setSysExpressionService(
			ISysExpressionService<SysExpressionVO, TbSysExpression, String> sysExpressionService) {
		this.sysExpressionService = sysExpressionService;
	}
	
	public ISysExprJobService<SysExprJobVO, TbSysExprJob, String> getSysExprJobService() {
		return sysExprJobService;
	}

	@Autowired
	@Resource(name="core.service.SysExprJobService")
	@Required			
	public void setSysExprJobService(ISysExprJobService<SysExprJobVO, TbSysExprJob, String> sysExprJobService) {
		this.sysExprJobService = sysExprJobService;
	}	
	
	private void initData(String type) throws ServiceException, Exception {
		if ("create".equals(type) || "edit".equals(type)) {
			this.systemMap = this.sysService.findSysMap(super.getBasePath(), true);
			this.sysExprMap = this.sysExpressionService.findExpressionMap(true);
			
			this.runDayOfWeekMap.put(ExpressionJobConstants.DATEOFWEEK_HOUR_MINUTE_ALL, ExpressionJobConstants.DATEOFWEEK_HOUR_MINUTE_ALL);
			for (int day=1; day<=7; day++) {
				this.runDayOfWeekMap.put(String.valueOf(day), String.valueOf(day));
			}
			
			this.runHourMap.put(ExpressionJobConstants.DATEOFWEEK_HOUR_MINUTE_ALL, ExpressionJobConstants.DATEOFWEEK_HOUR_MINUTE_ALL);
			for (int hour=0; hour<=23; hour++) {
				this.runHourMap.put(String.valueOf(hour), String.valueOf(hour));
			}
			
			this.runMinuteMap.put(ExpressionJobConstants.DATEOFWEEK_HOUR_MINUTE_ALL, ExpressionJobConstants.DATEOFWEEK_HOUR_MINUTE_ALL);
			for (int minute=0; minute<=59; minute++) {
				this.runMinuteMap.put(String.valueOf(minute), String.valueOf(minute));
			}
			
		}
	}
	
	private void loadSysExprJobData() throws ServiceException, Exception {
		this.transformFields2ValueObject(this.sysExprJob, new String[]{"oid"});
		DefaultResult<SysExprJobVO> result = this.sysExprJobService.findObjectByOid(this.sysExprJob);
		if ( result.getValue() == null ) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		this.sysExprJob = result.getValue();
		
		SysVO sys = new SysVO();
		sys.setSysId( this.sysExprJob.getSystem() );
		DefaultResult<SysVO> sysResult = this.sysService.findByUK(sys);
		if (sysResult.getValue() == null) {
			throw new ServiceException(sysResult.getSystemMessage().getValue());
		}
		sys = sysResult.getValue();
		this.getFields().put("systemOid", sys.getOid());
		
		SysExpressionVO expression = new SysExpressionVO();
		expression.setExprId( this.sysExprJob.getExprId() );
		DefaultResult<SysExpressionVO> expressionResult = this.sysExpressionService.findByUK(expression);
		if (expressionResult.getValue() == null) {
			throw new ServiceException(expressionResult.getSystemMessage().getValue());
		}
		expression = expressionResult.getValue();
		this.getFields().put("expressionOid", expression.getOid());
	}
	
	/**
	 * core.systemExpressionJobManagementAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0006Q")
	public String execute() throws Exception {
		try {
			this.initData("execute");
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return SUCCESS;
	}	
	
	/**
	 * core.systemExpressionJobCreateAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0006A")
	public String create() throws Exception {
		try {
			this.initData("create");
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return SUCCESS;		
	}
	
	/**
	 * core.systemExpressionJobEditAction.action
	 * 
	 * @return
	 * @throws Exception
	 */
	@ControllerMethodAuthority(programId="CORE_PROG003D0006E")
	public String edit() throws Exception {
		String forward = RESULT_SEARCH_NO_DATA;
		try {
			this.initData("edit");
			this.loadSysExprJobData();
			forward = SUCCESS;
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return forward;		
	}		
	
	@Override
	public String getProgramName() {
		return MenuSupportUtils.getProgramName(this.getProgramId(), this.getLocaleLang());
	}

	@Override
	public String getProgramId() {
		return super.getActionMethodProgramId();
	}

	public Map<String, String> getSystemMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.systemMap);
		return systemMap;
	}

	public void setSystemMap(Map<String, String> systemMap) {
		this.systemMap = systemMap;
	}

	public Map<String, String> getSysExprMap() {
		this.resetPleaseSelectDataMapFromLocaleLang(this.sysExprMap);
		return sysExprMap;
	}

	public void setSysExprMap(Map<String, String> sysExprMap) {
		this.sysExprMap = sysExprMap;
	}

	public SysExprJobVO getSysExprJob() {
		return sysExprJob;
	}

	public void setSysExprJob(SysExprJobVO sysExprJob) {
		this.sysExprJob = sysExprJob;
	}

	public Map<String, String> getRunDayOfWeekMap() {
		return runDayOfWeekMap;
	}

	public void setRunDayOfWeekMap(Map<String, String> runDayOfWeekMap) {
		this.runDayOfWeekMap = runDayOfWeekMap;
	}

	public Map<String, String> getRunHourMap() {
		return runHourMap;
	}

	public void setRunHourMap(Map<String, String> runHourMap) {
		this.runHourMap = runHourMap;
	}

	public Map<String, String> getRunMinuteMap() {
		return runMinuteMap;
	}

	public void setRunMinuteMap(Map<String, String> runMinuteMap) {
		this.runMinuteMap = runMinuteMap;
	}
	
}
