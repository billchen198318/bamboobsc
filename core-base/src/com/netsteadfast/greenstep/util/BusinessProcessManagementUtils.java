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
package com.netsteadfast.greenstep.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.lingala.zip4j.core.ZipFile;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.netsteadfast.greenstep.base.AppContext;
import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.SysMessageUtil;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.DefaultResult;
import com.netsteadfast.greenstep.base.model.GreenStepSysMsgConstants;
import com.netsteadfast.greenstep.model.UploadTypes;
import com.netsteadfast.greenstep.po.hbm.TbSysBpmnResource;
import com.netsteadfast.greenstep.po.hbm.TbSysBpmnResourceRole;
import com.netsteadfast.greenstep.service.ISysBpmnResourceRoleService;
import com.netsteadfast.greenstep.service.ISysBpmnResourceService;
import com.netsteadfast.greenstep.vo.SysBpmnResourceRoleVO;
import com.netsteadfast.greenstep.vo.SysBpmnResourceVO;

@SuppressWarnings("unchecked")
public class BusinessProcessManagementUtils {
	protected static Logger logger=Logger.getLogger(BusinessProcessManagementUtils.class); 
	private static final String _SUB_NAME = ".bpmn";
	private static ISysBpmnResourceService<SysBpmnResourceVO, TbSysBpmnResource, String> sysBpmnResourceService;
	private static ISysBpmnResourceRoleService<SysBpmnResourceRoleVO, TbSysBpmnResourceRole, String> sysBpmnResourceRoleService;
	private static RepositoryService repositoryService;
	private static RuntimeService runtimeService;
	private static TaskService taskService;
	
	static {
		sysBpmnResourceService = (ISysBpmnResourceService<SysBpmnResourceVO, TbSysBpmnResource, String>)
				AppContext.getBean("core.service.SysBpmnResourceService");
		sysBpmnResourceRoleService = (ISysBpmnResourceRoleService<SysBpmnResourceRoleVO, TbSysBpmnResourceRole, String>)
				AppContext.getBean("core.service.SysBpmnResourceRoleService");
		repositoryService = (RepositoryService) AppContext.getBean("repositoryService");
		runtimeService = (RuntimeService) AppContext.getBean("runtimeService");
		taskService = (TaskService) AppContext.getBean("taskService");
	}
	
	public static SysBpmnResourceVO loadResource(String resourceId) throws ServiceException, Exception {
		if (StringUtils.isBlank(resourceId)) {
			throw new Exception( SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK) );
		}
		SysBpmnResourceVO sysBpmnResource = new SysBpmnResourceVO();
		sysBpmnResource.setId(resourceId);		
		DefaultResult<SysBpmnResourceVO> result = sysBpmnResourceService.findByUK(sysBpmnResource);
		if (result.getValue()==null) {
			throw new ServiceException( result.getSystemMessage().getValue() );
		}
		return result.getValue();
	}
	
	private static byte[] getDiagramByte(ProcessInstance pi) throws Exception {
		byte[] data = null;
		ProcessDiagramGenerator processDiagramGenerator = new DefaultProcessDiagramGenerator();
		BpmnModel model = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
		InputStream is = processDiagramGenerator.generateDiagram(
				model, 
				"png", 
				runtimeService.getActiveActivityIds(pi.getId()));
		data = IOUtils.toByteArray(is);
		is.close();
		is = null;	
		return data;
	}
	
	public static String getProcessDefinitionDiagramById2Upload(String processDefinitionId) throws Exception {
		byte[] data = getProcessDefinitionDiagramById(processDefinitionId);
		if (null == data) {
			return "";
		}
		return UploadSupportUtils.create(
				Constants.getSystem(), 
				UploadTypes.IS_TEMP, 
				false, 
				data, 
				SimpleUtils.getUUIDStr() + ".png");			
	}
	
	public static byte[] getProcessDefinitionDiagramById(String processDefinitionId) throws Exception {
		ProcessDefinition pd = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionId( processDefinitionId )
				.singleResult();
		if (pd == null) {
			return null;
		}
		byte data[] = null;
		ProcessDefinitionEntity pde = (ProcessDefinitionEntity)pd;
		InputStream is = repositoryService.getResourceAsStream(pde.getDeploymentId(), pde.getDiagramResourceName());
		data = IOUtils.toByteArray(is);
		is.close();
		is = null;
		return data;
	}	
	
	public static String getProcessInstanceDiagramById2Upload(String processInstanceId) throws Exception {
		byte[] data = getProcessInstanceDiagramById(processInstanceId);
		if (null == data) {
			return "";
		}
		return UploadSupportUtils.create(
				Constants.getSystem(), 
				UploadTypes.IS_TEMP, 
				false, 
				data, 
				SimpleUtils.getUUIDStr() + ".png");		
	}
	
	public static byte[] getProcessInstanceDiagramById(String processInstanceId) throws Exception {
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId)
				.singleResult();
		if (pi == null) {
			return null;
		}		
		return getDiagramByte(pi);
	}
	
	public static String getTaskDiagramById2Upload(String resourceId, String taskId) throws Exception {
		byte[] data = getTaskDiagramById(resourceId, taskId);
		if (null == data) {
			return "";
		}
		return UploadSupportUtils.create(
				Constants.getSystem(), 
				UploadTypes.IS_TEMP, 
				false, 
				data, 
				SimpleUtils.getUUIDStr() + ".png");
	}
	
	public static byte[] getTaskDiagramById(String resourceId, String taskId) throws Exception {
		SysBpmnResourceVO resouce = loadResource(resourceId);
		Task task = getTaskById(taskId);
		if (null == task) {
			return null;
		}
		ProcessDefinitionQuery pdQuery = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionKey(resourceId);
		List<ProcessDefinition> pdList = pdQuery.deploymentId( resouce.getDeploymentId() ).list();
		byte data[] = null;
		for (ProcessDefinition pd : pdList) {
			List<ProcessInstance> piList = runtimeService.createProcessInstanceQuery()
					.processDefinitionId( pd.getId() )
					.list();
			for (ProcessInstance pi : piList) {
				if (pi.getProcessInstanceId().equals(task.getProcessInstanceId())) {					
					data = getDiagramByte(pi);
				}
			}
		}
		return data;
	}
	
	public static boolean isRoleAllowApproval(String resourceId, String roleId, String taskName) throws ServiceException, Exception {
		if (StringUtils.isBlank(resourceId) || StringUtils.isBlank(roleId) || StringUtils.isBlank(taskName)) {
			throw new Exception( SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK) );
		}		
		TbSysBpmnResourceRole resourceRole = new TbSysBpmnResourceRole();
		resourceRole.setId(resourceId);
		resourceRole.setRole(roleId);
		resourceRole.setTaskName(taskName);
		if (sysBpmnResourceRoleService.countByEntityUK(resourceRole)>0) {
			return true;
		}
		return false;
	}
	
	public static void deleteTaskById(String taskId) throws Exception {
		if (StringUtils.isBlank(taskId)) {
			throw new Exception( SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK) );
		}		
		logger.warn( "Delete task Id: " + taskId );		
		taskService.deleteTask(taskId);
	}
	
	public static void completeTask(String taskId, Map<String, Object> paramMap) throws Exception {
		if (StringUtils.isBlank(taskId)) {
			throw new Exception( SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK) );
		}		
		if (null!=paramMap) {
			taskService.complete(taskId, paramMap);
		} else {
			taskService.complete(taskId);
		}
	}
	
	public static Task getTaskById(String taskId) throws Exception {
		if (StringUtils.isBlank(taskId)) {
			throw new Exception( SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK) );
		}		
		return taskService.createTaskQuery().taskId(taskId).singleResult();
	}
	
	public static Map<String, Object> getTaskVariables(Task task) throws Exception {
		return taskService.getVariables(task.getId());
	}
	
	public static List<Task> queryTask(String resourceId) throws ServiceException, Exception {
		return queryTask( loadResource(resourceId) );
	}
	
	public static List<Task> queryTask(SysBpmnResourceVO sysBpmnResource) throws ServiceException, Exception {
		return taskService.createTaskQuery().deploymentId( sysBpmnResource.getDeploymentId() ).list();
	}
	
	public static List<ProcessDefinition> queryProcessDefinition(String resourceId) throws ServiceException, Exception {
		if (StringUtils.isBlank(resourceId)) {
			throw new Exception( SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK) );
		}
		SysBpmnResourceVO sysBpmnResource = new SysBpmnResourceVO();
		sysBpmnResource.setId(resourceId);		
		return queryProcessDefinition(sysBpmnResource);
	}
	
	public static List<ProcessDefinition> queryProcessDefinition(SysBpmnResourceVO sysBpmnResource) throws ServiceException, Exception {
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		return processDefinitionQuery
				.processDefinitionKey( sysBpmnResource.getId() )
				.orderByProcessDefinitionVersion()
				.desc()
				.list();
	}
	
	public static List<ProcessInstance> queryProcessInstance(String resourceId) throws ServiceException, Exception {
		return queryProcessInstance( loadResource(resourceId) );
	}
	
	public static List<ProcessInstance> queryProcessInstance(SysBpmnResourceVO sysBpmnResource) throws ServiceException, Exception {
		if (StringUtils.isBlank(sysBpmnResource.getDeploymentId())) {
			throw new Exception( "No deploymentId!" );
		}
		return runtimeService
				.createProcessInstanceQuery()
				.deploymentId( sysBpmnResource.getDeploymentId() )
				.list();
	}
	
	public static String startProcess(String resourceId, Map<String, Object> paramMap) throws ServiceException, Exception {
		if (StringUtils.isBlank(resourceId)) {
			throw new Exception( SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK) );
		}
		SysBpmnResourceVO sysBpmnResource = new SysBpmnResourceVO();
		sysBpmnResource.setId(resourceId);
		return startProcess(sysBpmnResource, paramMap);
	}
	
	public static String startProcess(SysBpmnResourceVO sysBpmnResource, Map<String, Object> paramMap) throws ServiceException, Exception {
		String processDefinitionId = "";
		ProcessInstance process = null;
		if (paramMap!=null) {
			process = runtimeService.startProcessInstanceByKey(sysBpmnResource.getId(), paramMap);
		} else {
			process = runtimeService.startProcessInstanceByKey(sysBpmnResource.getId());
		}
		processDefinitionId = process.getProcessDefinitionId();
		logger.info( "start Process definitionId: " + processDefinitionId );		
		return processDefinitionId;
	}
	
	public static String deployment(String resourceId) throws ServiceException, Exception {
		if (StringUtils.isBlank(resourceId)) {
			throw new Exception( SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK) );
		}
		SysBpmnResourceVO sysBpmnResource = new SysBpmnResourceVO();
		sysBpmnResource.setId(resourceId);
		return deployment(sysBpmnResource);
	}
	
	public static String deployment(SysBpmnResourceVO sysBpmnResource) throws ServiceException, Exception {
		DefaultResult<SysBpmnResourceVO> result = sysBpmnResourceService.findByUK(sysBpmnResource);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		ZipInputStream zip = new ZipInputStream( new ByteArrayInputStream(result.getValue().getContent()) );
		Deployment deployment = null;
		try {
			deployment = repositoryService
					.createDeployment()
					.name( result.getValue().getName() )
					.addZipInputStream( zip )
					.deploy();	
			result.getValue().setDeploymentId(deployment.getId());
			byte[] content = result.getValue().getContent();
			result.getValue().setContent( null ); // 先清掉content
			sysBpmnResourceService.updateObject(result.getValue()); // 先清掉content
			result.getValue().setContent(content); // 填回content
			sysBpmnResourceService.updateObject(result.getValue());	// 填回content	
			logger.info("deployment Id: " + deployment.getId() + " , name: " + deployment.getName());			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error( e.getMessage().toString() );
			throw e;
		} finally {
			zip.close();
			zip = null;				
		}		
		return deployment.getId();
	}
	
	public static void deleteDeployment(String resourceId, boolean force) throws ServiceException, Exception {
		if (StringUtils.isBlank(resourceId)) {
			throw new Exception( SysMessageUtil.get(GreenStepSysMsgConstants.PARAMS_BLANK) );
		}
		SysBpmnResourceVO sysBpmnResource = new SysBpmnResourceVO();
		sysBpmnResource.setId(resourceId);
		deleteDeployment(sysBpmnResource, force);
	}
	
	public static void deleteDeployment(SysBpmnResourceVO sysBpmnResource, boolean force) throws ServiceException, Exception {
		DefaultResult<SysBpmnResourceVO> result = sysBpmnResourceService.findByUK(sysBpmnResource);
		if (result.getValue()==null) {
			throw new ServiceException(result.getSystemMessage().getValue());
		}
		if (StringUtils.isBlank(result.getValue().getDeploymentId())) {
			throw new Exception( "No deployment!" );
		}
		logger.warn( "delete deployment Id:" + result.getValue().getDeploymentId() + " , force: " + force );
		repositoryService.deleteDeployment(result.getValue().getDeploymentId(), force);
		List<String> names = repositoryService.getDeploymentResourceNames( result.getValue().getDeploymentId() );
		if (names==null || names.size()<1) {			
			result.getValue().setDeploymentId(null);
			sysBpmnResourceService.updateObject(result.getValue());
		}
	}
	
	public static String getResourceProcessId(File activitiBpmnFile) throws Exception {
		if (null == activitiBpmnFile || !activitiBpmnFile.exists()) {
			throw new Exception("file no exists!");
		}
		if (!activitiBpmnFile.getName().endsWith(_SUB_NAME)) {
			throw new Exception("not resource file.");
		}
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(activitiBpmnFile);
		Element element = doc.getDocumentElement();
		if (!"definitions".equals(element.getNodeName())) {
			throw new Exception("not resource file.");
		}
		String processId = activitiBpmnFile.getName();
		if (processId.endsWith(_SUB_NAME)) {
			processId = processId.substring(0, processId.length()-_SUB_NAME.length());
		}
		NodeList nodes = element.getChildNodes();
		for (int i=0; i<nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if ("process".equals(node.getNodeName())) {
				NamedNodeMap nodeMap = node.getAttributes();
				for (int attr=0; attr<nodeMap.getLength(); attr++) {
					Node processAttr = nodeMap.item(attr);
					if ("id".equals(processAttr.getNodeName())) {
						processId = processAttr.getNodeValue();
					}
				}
			}
		}
		return processId;
	}
	
	public static String getResourceProcessId4Upload(String uploadOid) throws Exception {
		File files[] = selfTestDecompress4Upload(uploadOid);
		if (files==null) {
			throw new Exception("file no exists!");
		}
		File activitiBpmnFile = null;
		for (File file : files) {
			if (file.getName().endsWith(_SUB_NAME)) {
				activitiBpmnFile = file;
			}
		}
		return getResourceProcessId(activitiBpmnFile);
	}
	
	public static File[] decompressResource(File resourceZipFile) throws Exception {
		String extractDir = Constants.getWorkTmpDir() + "/" + BusinessProcessManagementUtils.class.getSimpleName() + "/" + SimpleUtils.getUUIDStr() + "/";
		ZipFile zipFile = new ZipFile(resourceZipFile);
		zipFile.extractAll( extractDir );
		File dir = new File(extractDir);
		return dir.listFiles();
	}
	
	public static File[] selfTestDecompress4Upload(String uploadOid) throws Exception {
		File realFile = UploadSupportUtils.getRealFile(uploadOid);
		return decompressResource(realFile);
	}

}
