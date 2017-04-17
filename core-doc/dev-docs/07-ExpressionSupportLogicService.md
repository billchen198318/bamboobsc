<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/00-Catalog.md">⌂ Catalog</a><br/>
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/06-RoleAndAuthoritySettings.md">⇦ 
Previous section 06 - Role and authority settings</a>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/08-WebService.md">⇨ 
Next section 08 - WebService</a>

# 07 - Expression support Logic Service
# Introduction
bambooBSC expression.<br>
我想也不會有人想看吧，所以直接用中文寫好了。<br/>
**注意: 錯誤的 expression 都有可能造成系統崩潰，所以必須要有良好的經驗，在來使用這個功能**<br/>

有時後有些不是很複雜的需求，但是每個使用方所要的方法又不一樣。
或是沒有一定規則的需求，這些特別的需要如果寫成JAVA code 在 LogicService 中，會照成要常常改code又要重新包版( package WAR )與佈署( re-deploy )，非常的不方便，所以才會有這個功能來輔助 Logic Service。


***You must first understand the following framework***<br/>
1. GROOVY http://www.groovy-lang.org/documentation.html<br/>
2. BeanShell http://www.beanshell.org/intro.html


# Create Expression for Logic Service bean
使用 `02 - Expression` 去創建一個expression，這個 expression 必須能與被輔助的 Service Logic bean's method 配合<br/>
<br/>
![Image of expr-mgr1](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/07-001.jpg)
<br/>
<br/>
<br/>
新增 expression
![Image of expr-mgr2](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/07-002.jpg)
<br/>
<br/>

# Create expression support Logic Service settings
創建設定<br/>
![Image of expr-mgr3](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/07-003.jpg)
<br/>
<br/>
![Image of expr-mgr4](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/07-004.jpg)
<br/>
<br/>

設定 Logic service method 被執行前 / 後 ，執行 expression
![Image of expr-mgr5](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/07-005.jpg)
<br/>
<br/>

設定 expression 需要的變數
![Image of expr-mgr6](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/07-006.jpg)
<br/>
<br/>

**以上步驟設定完後，每次被設的的 LogicService method 觸發時，AOP ( ServiceScriptExpressionProcessAspect ) 都會去調用執行設定的 Expression**


### ServiceScriptExpressionUtils only for ServiceScriptExpressionProcessAspect 

| Name | return |description |
| --- | --- | --- |
| needProcess(String beanId, String methodName, String system) | boolean | true this LogicService method need expression |
| processBefore(String beanId, Method method, String system, ProceedingJoinPoint pjp) | void | before process expression with ServiceLogic method |
| processAfter(String beanId, Method method, String system, Object resultObj, ProceedingJoinPoint pjp) | void | after process expression with ServiceLogic method |


<br/>
<br/>

### ScriptExpressionUtils
| Name | return |description |
| --- | --- | --- |
| execute(String type, String scriptExpression, `Map<String, Object>` results, `Map<String, Object>` parameters) | `Map<String, Object>` | run expression, type has BSH, GROOVY, PYTHON |
| executeBsh(String scriptExpression, `Map<String, Object>` results, `Map<String, Object>` parameters) | void | run BSH expression |
| executeGroovy(String scriptExpression, `Map<String, Object>` results, `Map<String, Object>` parameters) | void | run GROOVY expression |
| executeJython(String scriptExpression, `Map<String, Object>` results, `Map<String, Object>` parameters) | void | run JYTHON expression |


