<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/00-Catalog.md">⌂ Catalog</a><br/>
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/11-Mail.md">⇦ 
Previous section 11 - Mail</a>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/13-Template.md">⇨ 
Next section 13 - Template</a>


# 12 - Job scheduler
# Introduction
bambooBSC Job scheduler.<br>


***You must first understand the following framework***<br/>
1. Spring http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/<br/>
2. Quartz http://www.quartz-scheduler.org/

# BaseJob
BaseJob is support Job scheduler object.<br/>
Example:<br/>

```JAVA
public class TestJobImpl extends BaseJob implements Job {
	protected static Logger log = Logger.getLogger(ClearTempDataJobImpl.class);
	
	public ClearTempDataJobImpl() {
		super();
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
	  // do-something...
	}
	
}	
```

Use super role login for Job example:<br/>
If job need call Logic Service method, need use `loginForBackgroundProgram()` to login.

```JAVA

...

@Override
protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
  this.loginForBackgroundProgram();
  try {
    	
    	// do-something...
    	// call Logic Service event, Logic Service need login, because AOP will check LogicService ...
    	
	} catch (ServiceException e) {
		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			this.logoutForBackgroundProgram();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
  
}
	
...	
	
```

| Name | return |description |
| --- | --- | --- |
| loginForBackgroundProgram() | void | login( super-role ) for Job |
| logoutForBackgroundProgram() | void | logout for Job, when before has do `loginForBackgroundProgram()` |
| getAccountId() | String | get userId, when before has do `loginForBackgroundProgram()` |
| generateOid() | String | get a UUID |



# Config for Quartz with spring
**applicationContext-STANDARD-QUARTZ-JOB.xml**
<br/>
applicationContext-STANDARD-QUARTZ-JOB.xml is for Job Task config.
<br/><br/>
**applicationContext-STANDARD-QUARTZ.xml**
<br/>
applicationContext-STANDARD-QUARTZ.xml is for Job Task enable/disable and main config.
<br/><br/>
