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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.bsc.vo.BscApiServiceResponse;
import com.netsteadfast.greenstep.bsc.webservice.ApiWebService;
import com.thoughtworks.xstream.XStream;

/**
 * 測試:
 * 
 * http://127.0.0.1:8080/gsbsc-web/camel/api?format=json&visionOid=1089abb5-3faf-445d-88ff-cd7690ac6743&startDate=&endDate=&startYearDate=2015&endYearDate=2016&frequency=6&dataFor=all&measureDataOrganizationOid=&measureDataEmployeeOid=&contentFlag=
 * http://127.0.0.1:8080/gsbsc-web/camel/api?format=xml&visionOid=1089abb5-3faf-445d-88ff-cd7690ac6743&startDate=&endDate=&startYearDate=2015&endYearDate=2016&frequency=6&dataFor=all&measureDataOrganizationOid=&measureDataEmployeeOid=&contentFlag=
 * 
 */
public class ApiRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		this.from("servlet:///api")
		.process( new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				String format = StringUtils.defaultString(exchange.getIn().getHeader("format", String.class)).trim().toLowerCase();
				String visionOid = StringUtils.defaultString(exchange.getIn().getHeader("visionOid", String.class)).trim().toLowerCase();
				String startDate = StringUtils.defaultString(exchange.getIn().getHeader("startDate", String.class)).trim().toLowerCase();
				String endDate = StringUtils.defaultString(exchange.getIn().getHeader("endDate", String.class)).trim().toLowerCase();
				String startYearDate = StringUtils.defaultString(exchange.getIn().getHeader("startYearDate", String.class)).trim().toLowerCase();
				String endYearDate = StringUtils.defaultString(exchange.getIn().getHeader("endYearDate", String.class)).trim().toLowerCase();
				String frequency = StringUtils.defaultString(exchange.getIn().getHeader("frequency", String.class)).trim().toLowerCase();
				String dataFor = StringUtils.defaultString(exchange.getIn().getHeader("dataFor", String.class)).trim().toLowerCase();
				String measureDataOrganizationOid = StringUtils.defaultString(exchange.getIn().getHeader("measureDataOrganizationOid", String.class)).trim().toLowerCase();
				String measureDataEmployeeOid = StringUtils.defaultString(exchange.getIn().getHeader("measureDataEmployeeOid", String.class)).trim().toLowerCase();
				String contentFlag = StringUtils.defaultString(exchange.getIn().getHeader("contentFlag", String.class)).trim().toUpperCase();
				String responseContent = "";
				// 請參考 gsbsc-web applicationContext-STANDARD-CXF.xml
				ApiWebService apiWebService = (ApiWebService) AppContext.getBean("bsc.webservice.ApiWebService");
				BscApiServiceResponse responseObj = apiWebService.getScorecard1(
						visionOid, startDate, endDate, startYearDate, endYearDate, frequency, dataFor, measureDataOrganizationOid, measureDataEmployeeOid, contentFlag);
				if ("xml".equals(format)) { // xml
					XStream xstream = new XStream();
					xstream.setMode(XStream.NO_REFERENCES);
					xstream.alias("response", BscApiServiceResponse.class);
					responseContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + xstream.toXML(responseObj);
				} else { // json
					ObjectMapper mapper = new ObjectMapper();
					responseContent = mapper.writeValueAsString( responseObj );
				}
				exchange.getOut().setBody( responseContent );
			}
			
		})
		.to( "stream:out" );
	}

}
