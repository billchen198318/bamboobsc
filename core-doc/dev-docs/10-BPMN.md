<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/00-Catalog.md">⌂ Catalog</a><br/>
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/09-JasperReport.md"> ⇦ Previous section 09 - JasperReport</a>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/11-Mail.md"> ⇨ Next section 11- Mail</a>


# 10 - BPMN resource
# Introduction
bambooBSC deploy BPMN resource.<br>


***You must first understand the following framework***<br/>
1. Spring http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/<br/>
2. Activiti BPMN http://activiti.org/<br/>


# Package resource file
Example for `PDCAProjectProcess` <br/>
PDCAProjectProcess files:<br/>
|--PDCAProjectProcess.bpmn<br/>
|--PDCAProjectProcess.png<br/>

zip compression PDCAProjectProcess.bpmn and PDCAProjectProcess.png to PDCAProjectProcess.zip

# Upload resource and deploy
click `04 - BPMN Resource` to management
![Image of BPMN-res-mgr1](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/10-001.png)
<br/>
<br/>
![Image of BPMN-res-mgr2](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/10-002.png)
<br/>
<br/>


# Settings resource audit roles
click `05 - BPMN Resource role` to management
![Image of BPMN-res-mgr3](https://raw.githubusercontent.com/billchen198318/bamboobsc/master/core-doc/dev-docs/pics/10-003.png)
<br/>
<br/>

# Logic Service integrate BPMN resource example
The example is PDCA Project. `getBusinessProcessManagementResourceId()` return value is deploy BPMN resource id.
```JAVA
@ServiceAuthority(check=true)
@Service("bsc.service.logic.PdcaLogicService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class PdcaLogicServiceImpl extends BscBaseBusinessProcessManagementLogicService implements IPdcaLogicService {
  
  ...
  
	@Override
	public String getBusinessProcessManagementResourceId() {
		return "PDCAProjectProcess";
	}	  
  
  ...
  
}
```

**BscBaseBusinessProcessManagementLogicService**<br/>
integrate BPMN resource service object need extends BscBaseBusinessProcessManagementLogicService<br/>
reference:<br/>
https://github.com/billchen198318/bamboobsc/blob/master/core-doc/dev-docs/03-LogicService.md#bscbasebusinessprocessmanagementlogicservice
<br/>
<br/>
**Full code reference:**
<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-standard/src/com/netsteadfast/greenstep/bsc/service/logic/IPdcaLogicService.java<br/>
https://github.com/billchen198318/bamboobsc/blob/master/gsbsc-standard/src/com/netsteadfast/greenstep/bsc/service/logic/impl/PdcaLogicServiceImpl.java

# Force delete a work resource
Example:<br/>
BusinessProcessManagementDeleteTools [true/false] [resource-Id]<br/>
UNIX command: `delete_bpmn_res.sh true TestResourceId`<br/>
Windows command: `delete_bpmn_res.bat true TestResourceId`
<br/>
<br/>
UNIX:<br/>
https://github.com/billchen198318/bamboobsc/blob/master/core-web/resource/delete_bpmn_res.sh<br/>
Windows:<br/>
https://github.com/billchen198318/bamboobsc/blob/master/core-web/resource/delete_bpmn_res.bat<br/>
