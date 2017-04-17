<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/00-Catalog.md">⌂ Catalog</a><br/>
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/07-ExpressionSupportLogicService.md"> ⇦ Previous section 07 - Expression support Logic Service</a>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/09-JasperReport.md"> ⇨ Next section 09 -  JasperReport</a>


# 08 - WebService
# Introduction
bambooBSC public a SOAP / REST.<br>


***You must first understand the following framework***<br/>
1. Spring http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/<br/>
2. Apache CXF http://cxf.apache.org/<br/>
3. Apache Camel http://camel.apache.org/examples.html

### SOAP
Example for SOAP:

**Service interfaces**
```JAVA
package com.netsteadfast.greenstep.bsc.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding
public interface KpiWebService {
	
	@WebMethod
	public String outputKpisResult(@WebParam(name="format") String format) throws Exception;

}
```

**Service bean**
```JAVA
package com.netsteadfast.greenstep.bsc.webservice.impl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.springframework.stereotype.Service;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.bsc.service.logic.IKpiLogicService;
import com.netsteadfast.greenstep.bsc.webservice.KpiWebService;

@Service("bsc.webservice.KpiWebService")
@WebService
@SOAPBinding
public class KpiWebServiceImpl implements KpiWebService {

	@WebMethod
	@Override
	public String outputKpisResult(@WebParam(name="format") String format) throws Exception {
		IKpiLogicService kpiLogicService = (IKpiLogicService)AppContext.getBean("bsc.service.logic.KpiLogicService");		
		return kpiLogicService.findKpis(format);
	}

}
```

**Config applicationContext-STANDARD-CXF.xml**

```XML
<bean id="bsc.webservice.KpiWebService" class="com.netsteadfast.greenstep.bsc.webservice.impl.KpiWebServiceImpl" />
```

# REST
Example for REST:

```JAVA
@ServiceAuthority(check=true)
@Service("bsc.service.logic.KpiLogicService")
@WebService
@Path("/")
@Produces("application/json")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class KpiLogicServiceImpl extends BscBaseLogicService implements IKpiLogicService {
	
	...
	
	@WebMethod
	@GET
	@Path("/kpis/{format}")
	@Override
	public String findKpis(@WebParam(name="format") @PathParam("format") String format) throws ServiceException, Exception {				
		List<KpiVO> kpis = null;
		try {
			kpis = this.kpiService.findListVOByParams( null );
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null == kpis) {
				kpis = new ArrayList<KpiVO>();
			}
		}
		if ("json".equals(format)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("KPIS", kpis);
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString( paramMap );
		}		
		XStream xstream = new XStream();
		xstream.setMode(XStream.NO_REFERENCES);		
		xstream.alias("KPIS", List.class);
		xstream.alias("KPI", KpiVO.class);
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + xstream.toXML(kpis);
	}	
	
	...
	
}
```

# Public the service

click `01 - WebService registration` to management
![Image of ws-mgr1](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/08-001.jpg)
<br>
<br>

![Image of ws-mgr2](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/08-002.jpg)
<br>
<br>

***Note: need restart tomcat server to take effect***

![Image of ws-mgr3](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/08-003.jpg)
<br>
<br>

# ESB service
Example for result out of servlet:

```JAVA
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

```

***Config applicationContext-STANDARD-ESB.xml***

```XML

    <camel:camelContext id="esb.BSC-SystemCamelContext">
    	<camel:package>com.netsteadfast.greenstep.bsc.esb.router</camel:package>
    </camel:camelContext>    
    
```

# Hessian webService
client & remote service settings

## 1. config "web.xml"
<br>
Enable hessian servlet:
```XML
	<servlet>  
    	<servlet-name>Hessian</servlet-name>  
    	<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>  
		<load-on-startup>1</load-on-startup>  
	</servlet>      
	<servlet-mapping>  
		<servlet-name>Hessian</servlet-name>  
		<url-pattern>/hessian/*</url-pattern>  
	</servlet-mapping> 	
```	

## 2. config "Hessian-servlet.xml"
<br>
Example publish a service:

```XML
<bean name="/core.service.TestHelloWorldService.hessian" class="com.netsteadfast.greenstep.sys.GreenStepHessianServiceExporter">
	<property name="service" ref="core.service.TestHelloWorldService"/>
	<property name="serviceInterface" value="com.netsteadfast.greenstep.service.ITestHelloWorldService"/>
</bean>
```

## 3. config "applicationContext-hessian.xml"
```XML
	<bean id="hessian.config" class="java.util.HashMap" scope="singleton" >
		<constructor-arg>
			<map>
				<!-- 預設要檢查http request header 中的 check value 資料 -->
				<entry key="configHessianHeaderCheckValueModeEnable"		value="Y" />
				<!-- 預設的 hession 服務子位址, 必須與 web.xml 設定一致 -->
				<entry key="configHessianUrlPattern"	value="/hessian/" />
				<!-- 預設的 hession 服務擴展名, 必須與 Hessian-servlet.xml 設定一致 -->
				<entry key="configHessianExtensionName"	value=".hessian" />
				<entry key="enable" 					value="${hessian.enable}" />
				<entry key="serverUrl" 					value="${hessian.serverUrl}" />
				<entry key="checkValue" 				value="${hessian.checkValue}" />
				<!-- This is a Example , The SYSTEM no bean named PlayHelloWorldService and TestHelloWorldService -->
			    <entry key="proxyServiceId">
			        <value>
			        
			        core.service.PlayHelloWorldService, 
			        core.service.TestHelloWorldService
			        
			        </value>
			    </entry>
			    <!-- This is a Example , cannot use Hessian remote service proxy user account -->
			    <entry key="proxyBlockedAccountId">
			    	<value>
			    	
			    	admin,
			    	system
			    	
			    	</value>
			    </entry>
			</map>
		</constructor-arg>
    </bean>
```    

## 4. config "applicationContext-appSettings.properties"
```
#######################################################
#	for applicationContext-hessian.xml
#######################################################
# =====================================================
# Hessian for client call remote-server settings
# Remote-Server settings hessian.enable=N and enable web.xml Hessian servlet
# Client settings hessian.enable=Y and disable web.xml Hessian servlet
# =====================================================
hessian.enable=N
hessian.serverUrl=http://localhost:8888/
hessian.checkValue=PUT_HESSIAN_HTTP_CHECK_VALUE
```
