<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/00-Catalog.md">⌂ Catalog</a><br/>
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/02-DaoAndService.md">⇦ Previous section 02-DAO and Service</a>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/04-ControllerAction.md">⇨ Next section 04-Controller</a>


# 03 - Logic service
# Introduction
Logic service is do many base-service with complicated transaction script bean service.<br>


***You must first understand the following framework***<br/>
1. Spring http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/<br/>
2. Hibernate http://hibernate.org/<br/>
3. Dozer https://github.com/DozerMapper/dozer<br/>
4. MyBatis https://github.com/mybatis/mybatis-3<br/>
5. Activiti BPMN http://activiti.org/<br/>

### BaseLogicService
BaseLogicService is minimum unit Logic Service, it main support CORE-SYSTEM used.


| Name | Return | description |
| --- | --- | --- |
| getAccountId() | String | get user account id |
| defaultString(String source) | String | null will return blank |
| isBlank(String source) | boolean | null/blank true, found value false |
| isNoSelectId(String value) | boolean | value not a select option item or `Please select` return true |
| setStringValueMaxLength(T obj, String fieldName, int maxLength) | `<T>` BaseValue | set value object variable field max string size, main used of `description` field |
| replaceSplit2Blank(T obj, String fieldName, String split) | `<T>` BaseValue | set value object variable, replace split value to blank |

reference example:<br/>
https://github.com/billchen198318/bamboobsc/blob/master/core-base/src/com/netsteadfast/greenstep/service/logic/IApplicationSystemLogicService.java<br/>
https://github.com/billchen198318/bamboobsc/blob/master/core-base/src/com/netsteadfast/greenstep/service/logic/impl/ApplicationSystemLogicServiceImpl.java<br/>

<br/>
<br/>
<br/>

### CoreBaseLogicService 
CoreBaseLogicService is extends BaseLogicService, it main support GSBSC-SYSTEM, QCHARTS-SYSTEM.

| Name | Return | description |
| --- | --- | --- |
| findAccountData() | AccountVO | get current user account object |
| findAccountData(String accountId) | AccountVO | get user account object by account-Id |
| findUserRoles() | List`<TbUserRole>` | get current user Role data |
| findUserRoles(String accountId) | List`<TbUserRole>` | get user Role data by account-Id |
| findUploadData(String oid) | SysUploadVO | find upload data log |
| findUploadDataForNoByteContent(String oid) | SysUploadVO | find upload data log, not blob content field value |

reference example:<br/>
https://github.com/billchen198318/bamboobsc/blob/master/qcharts-standard/src/com/netsteadfast/greenstep/qcharts/service/logic/IDataQueryLogicService.java<br/>
https://github.com/billchen198318/bamboobsc/blob/master/qcharts-standard/src/com/netsteadfast/greenstep/qcharts/service/logic/impl/DataQueryLogicServiceImpl.java<br/>

<br/>
<br/>
<br/>

### BscBaseLogicService 
BscBaseLogicService is extends CoreBaseLogicService, it main support GSBSC-SYSTEM.

| Name | Return | description |
| --- | --- | --- |
| findOrganizationData(String oid) | OrganizationVO | find organization data by OID |
| findEmployeeData(String oid) | EmployeeVO | find employee data by OID |
| findOrganizationDataByUK(String orgId) | OrganizationVO | find organization data by organization-ID |
| findEmployeeDataByUK(String accountId, String empId) | EmployeeVO | find employee data by employee UK / unique key |
| findEmployeeDataByAccountId(String accountId) | EmployeeVO | find employee data by account-id |
| findEmployeeDataByEmpId(String empId) | EmployeeVO | find employee data by employee-id / employee-no |

reference example:<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-standard/src/com/netsteadfast/greenstep/bsc/service/logic/IKpiLogicService.java<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-standard/src/com/netsteadfast/greenstep/bsc/service/logic/impl/KpiLogicServiceImpl.java<br/>


<br/>
<br/>
<br/>


### BscBaseBusinessProcessManagementLogicService 
BscBaseBusinessProcessManagementLogicService is extends CoreBaseLogicService and BusinessProcessManagementBaseLogicService.<br/> 
BscBaseBusinessProcessManagementLogicService it main support GSBSC-SYSTEM with BPMN.

| Name | Return | description |
| --- | --- | --- |
| findOrganizationData(String oid) | OrganizationVO | find organization data by OID |
| findEmployeeData(String oid) | EmployeeVO | find employee data by OID |
| findOrganizationDataByUK(String orgId) | OrganizationVO | find organization data by organization-ID |
| findEmployeeDataByUK(String accountId, String empId) | EmployeeVO | find employee data by employee UK / unique key |
| findEmployeeDataByAccountId(String accountId) | EmployeeVO | find employee data by account-id |
| findEmployeeDataByEmpId(String empId) | EmployeeVO | find employee data by employee-id / employee-no |
| getBusinessProcessManagementResourceId() | String | get BO BPMN resource-Id |
| getBusinessProcessManagementResourceObject() | SysBpmnResourceVO | get CORE-SYSTEM BO BPMN resource object |
| startProcess(Map`<String, Object>` paramMap) | String | start process |
| completeTask(String taskId, Map`<String, Object>` paramMap) | void | complete process task |
| queryTask() | List`<Task>` | query process tasks |
| queryTaskPlus() | List`<BusinessProcessManagementTaskVO>` | query process tasks |
| queryTaskPlus(String variableKeyName, String variableKeyValue) | List`<BusinessProcessManagementTaskVO>` | query process tasks by args variable name and value found |
| queryTaskByVariable(String variableKeyName, String variableKeyValue) | List`<Task>` | query process tasks by args variable name and value found |
| isRoleAllowApproval(String taskName)<br/><br/>isRoleAllowApproval(String accountId, String taskName) | boolean | check approval task permission, true has permission, false is no permission |


reference example:<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-standard/src/com/netsteadfast/greenstep/bsc/service/logic/IPdcaLogicService.java<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-standard/src/com/netsteadfast/greenstep/bsc/service/logic/impl/PdcaLogicServiceImpl.java<br/>
config file reference:<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-standard/resource/applicationContext/bsc/standard/ext/applicationContext-ext-pdca.xml<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-standard/resource/applicationContext/applicationContext-BSC-STANDARD-EXT-BEANS.xml<br/>

<br/>
<br/>
<br/>

### Logic service Authority annotation
| Name | description |
| --- | --- |
| @ServiceAuthority | check=true this Logic service enable Authority check,  check=false disable |
| @ServiceMethodAuthority | method Authority type |

<br/>
<br/>

`@ServiceMethodAuthority` type list

| Name | description |
| --- | --- |
| SELECT | method select data mode |
| INSERT | method insert data mode |
| UPDATE | method update data mode |
| DELETE | method delete data mode |


# Logic service AOP scan package config
```JAVA
package com.netsteadfast.greenstep.aspect;


public class AspectConstants {
	
	// for DAO config
	public static final String DATA_ACCESS_OBJECT_PACKAGE = " execution(* com.netsteadfast.greenstep.dao..*.*(..) ) || execution(* com.netsteadfast.greenstep.bsc.dao..*.*(..) ) || execution(* com.netsteadfast.greenstep.qcharts.dao..*.*(..) ) ";
	
	// for Base Service config
	public static final String BASE_SERVICE_PACKAGE = " execution(* com.netsteadfast.greenstep.service.*.*(..) ) || execution(* com.netsteadfast.greenstep.bsc.service.*.*(..) ) || execution(* com.netsteadfast.greenstep.qcharts.service.*.*(..) ) ";
	
	// for Logic Service config
	public static final String LOGIC_SERVICE_PACKAGE = " execution(* com.netsteadfast.greenstep.service.logic..*.*(..) ) || execution(* com.netsteadfast.greenstep.bsc.service.logic..*.*(..) ) || execution(* com.netsteadfast.greenstep.qcharts.service.logic..*.*(..) ) ";
	
}	
	
```



# Add Logic service authority
Please use: 
Role's permitted settings -> `01 - Role` function to add permitted value

Example:<br/>
Logice bean id is `bsc.service.logic.KpiLogicService` and method ServiceMethodAuthority type is INSERT<br/>
permitted string value is: `bsc.service.logic.KpiLogicService#INSERT`


role permitted table:<br/>
`select * from tb_role_permission`







