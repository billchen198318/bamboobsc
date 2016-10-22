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
package com.netsteadfast.greenstep.bsc.esb.router;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.bsc.service.logic.IKpiLogicService;

/**
 * The is test for provide KPIs data output, use apache-camel(ESB) 
 * 
 * 這是測試用 , 將 KPIs 主檔暴露輸出資料, 使用 apache-camel(ESB)
 * 
 * http://127.0.0.1:8080/gsbsc-web/camel/kpis
 * 
 * http://127.0.0.1:8080/gsbsc-web/camel/kpis?format=json
 * http://127.0.0.1:8080/gsbsc-web/camel/kpis?format=xml
 * 
 */
public class KPIsRouteBuilder extends RouteBuilder {
	
	@Override
	public void configure() throws Exception {		
		from( "servlet:///kpis" )
		.process( new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				String format = StringUtils.defaultString(exchange.getIn().getHeader("format", String.class))
						.trim().toLowerCase();							
				IKpiLogicService kpiLogicService = 
						(IKpiLogicService)AppContext.getBean( "bsc.service.logic.KpiLogicService" );
				exchange.getOut().setBody( kpiLogicService.findKpis(format) );
			}
			
		})		
		.to( "stream:out" );		
	}

}
