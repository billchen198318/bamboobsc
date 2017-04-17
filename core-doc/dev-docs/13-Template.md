<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/00-Catalog.md">⌂ Catalog</a><br/>
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/12-Job.md">⇦ 
Previous section 12 - Job Scheduler</a>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/14-WebContextBean.md">⇨ 
Next section 14 - Web Context bean</a>


# 13 - Template
# Introduction
bambooBSC template model.<br>


***You must first understand the following framework***<br/>
1. Spring http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/<br/>
2. Apache FreeMarker http://freemarker.org/

# Create template resource
create a template resource.
![Image of BPMN-res-mgr1](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/13-001.jpeg)
<br/>
<br/>
![Image of BPMN-res-mgr1](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/13-002.jpeg)
<br/>
<br/>
Settings variable mapper.<br/>
![Image of BPMN-res-mgr1](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/13-003.jpeg)
<br/>
<br/>

**Example java code:**
```JAVA
TemplateResultObj tplResultObj = TemplateUtils.getResult("TPL-MSG-77", vision);
```

Full code:
```JAVA
@ServiceMethodAuthority(type={ServiceMethodType.INSERT})
@Transactional(
		propagation=Propagation.REQUIRED, 
		readOnly=false,
		rollbackFor={RuntimeException.class, IOException.class, Exception.class} )			
@Override
public DefaultResult<VisionVO> create(VisionVO vision) throws ServiceException, Exception {
	if (null == vision) {
		throw new ServiceException(SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK));
	}
	if ( !SimpleUtils.checkBeTrueOf_azAZ09(4, 14, vision.getVisId()) ) { // for import-mode from csv file VIS_ID is old(before id).			
		vision.setVisId( this.findForMaxVisId(SimpleUtils.getStrYMD("")) );
	}				
		
		
	/**
	 * TEST Template : TPL-MSG-77
	 */
	TemplateResultObj tplResultObj = TemplateUtils.getResult("TPL-MSG-77", vision);
	
	MailClientUtils.send(
			"root@localhost", 
			"chen.xin.nien@gmail.com", 
			tplResultObj.getTitle(), 
			tplResultObj.getContent());
		
		
	return this.visionService.saveObject(vision);
}
```

# Test result
The test use sendmail to send template result content.
![Image of BPMN-res-mgr1](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/13-004.jpeg)
<br/>
<br/>

# TemplateUtils
TemplateUtils is bambooBSC template util.


| Name | return |description |
| --- | --- | --- |
| getResult(String templateId, Object dataObj) | TemplateResultObj | do template from resource, `templateId` is template-Id, `dataObj` is value object  |
| getResourceSrc(ClassLoader classLoader, String metaInfFile) | String | get FreeMarker ftl resource content, `classLoader` ClassLoader, `metaInfFile` resource on META-INF fullpath |
| processTemplate(String name, ClassLoader classLoader, String templateResource, Map`<String, Object>` parameter) | String | do template from resource, `name` this event name, `classLoader` ClassLoader, `templateResource` resource on META-INF fullpath, `parameter` is Map`<String, Object>` param put template need variable's value  |

<br/>
<br/>
`processTemplate(String name, ClassLoader classLoader, String templateResource, Map<String, Object> parameter)` Example:
```JAVA
Map<String, Object> paramMap = new HashMap<String, Object>();
paramMap.put("labels", labels);
paramMap.put("searchDatas", this.searchDatas);
this.gridContent = TemplateUtils.processTemplate(
		"resourceTemplate", 
		QueryDataUtils.class.getClassLoader(), 
		"META-INF/resource/data-query-grid.ftl", 
		paramMap);
```
