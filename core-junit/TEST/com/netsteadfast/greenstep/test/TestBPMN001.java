package com.netsteadfast.greenstep.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.netsteadfast.greenstep.base.AppContext;

public class TestBPMN001 {
	
	@Test
	public void deployment() throws Exception {	
		
		InputStream inputStreamBpmn = new FileInputStream( "/home/git/bamboobsc/core-doc/DFProjectPublishProcess.bpmn" );
		InputStream inputStreamPng = new FileInputStream( "/home/git/bamboobsc/core-doc/DFProjectPublishProcess.png" );		
		RepositoryService repositoryService = (RepositoryService) AppContext.getBean("repositoryService");
		Deployment deployment = repositoryService
				.createDeployment()
				.name("DFProjectPublishProcess-flow")
				.addInputStream("DFProjectPublishProcess.bpmn", inputStreamBpmn)
				.addInputStream("DFProjectPublishProcess.png", inputStreamPng)				
				.deploy();
		System.out.println("ID: " + deployment.getId() + " , Name: " + deployment.getName());
		
	}
	
	@Test
	public void startProcess() throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("confirmPublish", "N");
		
		String processId = "Employee360DegreeFeedbackProjectPublishProcess";
		
		RuntimeService runtimeService = (RuntimeService) AppContext.getBean("runtimeService");
		ProcessInstance process = runtimeService.startProcessInstanceByKey(processId, paramMap);
		System.out.println("DeploymentId: " + process.getDeploymentId() );
		System.out.println("ActivityId: " + process.getActivityId() );
		System.out.println("Name: " + process.getName() );
		System.out.println("ProcessDefinitionId: " + process.getProcessDefinitionId());
		System.out.println("ProcessDefinitionKey: " + process.getProcessDefinitionKey());
		System.out.println("ProcessDefinitionName: " + process.getProcessDefinitionName());
		System.out.println("BusinessKey: " + process.getBusinessKey());
		
	}
	
	@Test
	public void deleteProcess() throws Exception {
		
		String deploymentId = "10001";
		
		RepositoryService repositoryService = (RepositoryService) AppContext.getBean("repositoryService");
		repositoryService.deleteDeployment(deploymentId);
		//repositoryService.deleteDeployment(processId, true); no need it
		
	}
	
	@Test
	public void queryTask() throws Exception {
		
		String assignee = "張三";
		
		TaskService taskService = (TaskService) AppContext.getBean("taskService");
		List<Task> tasks = taskService.createTaskQuery().taskAssignee( assignee ).list();
		if (tasks == null || tasks.size()<1 ) {
			System.out.println("no task.");
			return;
		}
		for (Task task : tasks) {
			this.printTask(task);
		}
		
	}
	
	@Test
	public void queryProcessDefinition() throws Exception {
		
		RepositoryService repositoryService = (RepositoryService) AppContext.getBean("repositoryService");
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		List<ProcessDefinition> processDefs = processDefinitionQuery
			.processDefinitionKey("Employee360DegreeFeedbackProjectPublishProcess")
			.orderByProcessDefinitionVersion()
			.desc()
			.list();
		for (ProcessDefinition pd : processDefs) {
			System.out.println( pd.getId() + " , " + pd.getName() + " , " 
					+ pd.getKey() + " , " + pd.getVersion() );
			ProcessDefinitionImpl pdObj = (ProcessDefinitionImpl)repositoryService.getProcessDefinition(pd.getId());
			System.out.println(pdObj.getActivities());
		}
		
	}
	
	@Test
	public void queryHistory() throws Exception {
		
		HistoryService historyService = (HistoryService) AppContext.getBean("historyService");
		List<HistoricTaskInstance> taskInstances = historyService
				.createHistoricTaskInstanceQuery()
				.finished()
				.list();
		for (HistoricTaskInstance taskInst : taskInstances) {
			System.out.println(taskInst.getId() + " , " + taskInst.getName() + " , " 
					+ taskInst.getFormKey() + " , " + taskInst.getAssignee() );
		}
	}
	
	@Test
	public void queryFlowImage() throws Exception {
		
		String deploymentId = "10001";
		RepositoryService repositoryService = (RepositoryService) AppContext.getBean("repositoryService");
		List<String> names = repositoryService.getDeploymentResourceNames(deploymentId);
		for (String name : names) {
			System.out.println( name );
			if (name.endsWith(".png") || name.endsWith(".bmp")) {
				InputStream is = repositoryService.getResourceAsStream(deploymentId, name);
				FileUtils.copyInputStreamToFile(is, new File("/tmp/" + name));
			}
		}
		
	}
	
	@Test
	public void completeTask() throws Exception {
		
		String assignee = "張三";
		//String assignee = "王經理";
				
		TaskService taskService = (TaskService) AppContext.getBean("taskService");
		List<Task> tasks = taskService.createTaskQuery().list();
		if (tasks == null || tasks.size()<1 ) {
			System.out.println("no task.");
			return;
		}
		for (Task task : tasks) {
			taskService.setAssignee(task.getId(), assignee);
			taskService.complete(task.getId());
			this.printTask(task);
		}
		
	}
	
	private void printTask(Task task) throws Exception {
		System.out.println("ID: " + task.getId() + " , Name: " + task.getName() 
				+ " , FORM_KEY: " + task.getFormKey() 
				+ " , Assignee: " + task.getAssignee()
				+ " , ProcessInstanceId: " + task.getProcessInstanceId() 
				+ " , ProcessInstanceId: " + task.getProcessInstanceId());
	}
	

}
