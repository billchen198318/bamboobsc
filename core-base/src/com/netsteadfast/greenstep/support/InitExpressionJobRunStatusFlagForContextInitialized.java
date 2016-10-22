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
package com.netsteadfast.greenstep.support;

import javax.servlet.ServletContextEvent;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.model.ContextInitializedAndDestroyedBean;
import com.netsteadfast.greenstep.util.SystemExpressionJobUtils;

/**
 * 因為有的 ExpressionJob 還沒跑完, ap-server 就被停掉了( 如再開發環境中 )
 * 這個 ContextInitializedAndDestroyedBean 會把 RUN_STATUS = 'R' 的資料, 更新為初始值 'Y'
 * 因為 server 重啟時, 不因該會有 RUN_STATUS = 'R' 的 Expression job,
 * 但是如果有再多台 AP-server 上部屬相同的 core-web, gsbsc-web, qcharts-web, 請用  "09 - Web Context bean" 把這個設定停掉
 *
 */
public class InitExpressionJobRunStatusFlagForContextInitialized extends ContextInitializedAndDestroyedBean {
	private static final long serialVersionUID = -3702058108258404176L;

	@Override
	public void execute(ServletContextEvent event) throws Exception {
		SystemExpressionJobUtils.initRunStatusFlag(Constants.getSystem());
	}
	
}
