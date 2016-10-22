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
package com.netsteadfast.greenstep.model;

import com.netsteadfast.greenstep.vo.SysExprJobLogVO;
import com.netsteadfast.greenstep.vo.SysExprJobVO;
import com.netsteadfast.greenstep.vo.SysExpressionVO;

public class ExpressionJobObj implements java.io.Serializable {
	private static final long serialVersionUID = 7251297482415387237L;
	private SysExpressionVO sysExpression;
	private SysExprJobVO sysExprJob;
	private SysExprJobLogVO sysExprJobLog;
	
	public ExpressionJobObj() {
		
	}
	
	public ExpressionJobObj(SysExpressionVO sysExpression, SysExprJobVO sysExprJob, SysExprJobLogVO sysExprJobLog) {
		super();
		this.sysExpression = sysExpression;
		this.sysExprJob = sysExprJob;
		this.sysExprJobLog = sysExprJobLog;
	}

	public SysExpressionVO getSysExpression() {
		return sysExpression;
	}

	public void setSysExpression(SysExpressionVO sysExpression) {
		this.sysExpression = sysExpression;
	}

	public SysExprJobVO getSysExprJob() {
		return sysExprJob;
	}

	public void setSysExprJob(SysExprJobVO sysExprJob) {
		this.sysExprJob = sysExprJob;
	}

	public SysExprJobLogVO getSysExprJobLog() {
		return sysExprJobLog;
	}

	public void setSysExprJobLog(SysExprJobLogVO sysExprJobLog) {
		this.sysExprJobLog = sysExprJobLog;
	}

}
