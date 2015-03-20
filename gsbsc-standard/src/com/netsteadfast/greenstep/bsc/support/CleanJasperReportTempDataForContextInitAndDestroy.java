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
package com.netsteadfast.greenstep.bsc.support;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.model.ContextInitializedAndDestroyedBean;

/**
 * 清除 BB_SWOT_REPORT_MST, BB_SWOT_REPORT_DTL 資料
 *
 */
public class CleanJasperReportTempDataForContextInitAndDestroy extends ContextInitializedAndDestroyedBean {
	private static final long serialVersionUID = 51610240659491697L;
	protected static Logger logger = Logger.getLogger(CleanJasperReportTempDataForContextInitAndDestroy.class);

	@Override
	public void execute(ServletContextEvent event) throws Exception {
		logger.info("begin clean temp data....");
		this.clean();
		logger.info("end....");
	}
	
	private void clean() throws Exception {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = (NamedParameterJdbcTemplate)
				AppContext.getBean("namedParameterJdbcTemplate");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		namedParameterJdbcTemplate.update("delete from bb_swot_report_mst", paramMap);
		namedParameterJdbcTemplate.update("delete from bb_swot_report_dtl", paramMap);
	}

}
