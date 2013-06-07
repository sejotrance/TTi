package com.processEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.mortbay.log.Log;

import com.vaadin.ui.Label;

public class MotorProcesos {
	public ProcessEngine processEngine;
	public RepositoryService repositoryService;
	private String instancias;
	
	public MotorProcesos() throws FileNotFoundException {
		instancias = "";
		ProcessEngines.init();
		processEngine = ProcessEngineConfiguration
			    .createStandaloneProcessEngineConfiguration()
			    .buildProcessEngine();
		repositoryService = processEngine.getRepositoryService();
//		String barFileName = "AgendarReunion.bar";
//		ZipInputStream inputStream = new ZipInputStream(new FileInputStream(barFileName));
//		repositoryService.createDeployment()
//		    .name("AgendarReunion.bar")
//		    .addZipInputStream(inputStream)
//		    .deploy();
	}
	
	public String getInstancias(){
		List<ProcessDefinition> processDefinitions = repositoryService
			      .createProcessDefinitionQuery()
			      .orderByProcessDefinitionName().asc()
			      .orderByProcessDefinitionVersion().asc()
			      .list();
		for (ProcessDefinition processDefinition : processDefinitions) {
		      instancias += processDefinition.getId();
		      String name = processDefinition.getName() + " (v" + processDefinition.getVersion() + ")";
		      instancias += name;
		    }
		return instancias;
	}
	
}
