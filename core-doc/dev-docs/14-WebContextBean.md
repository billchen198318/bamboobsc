<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/00-Catalog.md">⌂ Catalog</a><br/>
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/13-Template.md">⇦ 
Previous section 13 - Template</a>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/15-Formula.md">⇨ 
Next section 15 - Formula</a>


# 14 - Web Context bean
# Introduction
server web context start or shutdown will run java class bean.<br>
WebSystemCtxBeanSupportListener will auto create bean( ContextInitializedAndDestroyedBean ) and call `execute(ServletContextEvent event)` method for INITIALIZE or DESTROY.


***You must first understand the following framework***<br/>
1. ServletContextListener https://tomcat.apache.org/tomcat-8.0-doc/servletapi/javax/servlet/ServletContextListener.html<br/>

# Create a event bean
The class need extends ContextInitializedAndDestroyedBean<br/>
Example:
```JAVA
public class CleanTempUploadForContextInitAndDestroy extends ContextInitializedAndDestroyedBean {
	private static final long serialVersionUID = 5017459384696932226L;

	@Override
	public void execute(ServletContextEvent event) throws Exception {
	  
	  // do something ...
	  
	}
	
}
```

# Create config
Add config item.
![Image of WEB-CTX-BEAN-mgr1](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/14-001.jpeg)
<br>
<br>

![Image of WEB-CTX-BEAN-mgr2](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/14-002.jpeg)
<br>
<br>

**And need restart tomcat server to effective**

# ContextInitializedAndDestroyedBean

| Name | return |description |
| --- | --- | --- |
| execute(ServletContextEvent event) | void | do event function, WebSystemCtxBeanSupportListener will call it |
