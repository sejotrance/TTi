package com.processEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLClassLoader;
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

import com.vaadin.ui.Label;

public class MotorProcesos {
	public ProcessEngine processEngine;
	public RepositoryService repositoryService;
	private String instancias;
	
	public MotorProcesos(){

		instancias = "";
//		String barFileName = "AgendarReunion.bar";
//		ZipInputStream inputStream = new ZipInputStream(new FileInputStream(barFileName));
//		repositoryService.createDeployment()
//		    .name("AgendarReunion.bar")
//		    .addZipInputStream(inputStream)
//		    .deploy();
	}
	
	public void Crear(){
		//ProcessEngines.init();
		processEngine = ProcessEngineConfiguration
				.createStandaloneProcessEngineConfiguration()
			    .buildProcessEngine();
		repositoryService = processEngine.getRepositoryService();
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
	
	public Long getDefiniciones(){
		processEngine = ProcessEngines.getDefaultProcessEngine();
		repositoryService = processEngine.getRepositoryService();
		long def = repositoryService.createDeploymentQuery().count();
		System.out.println("Number of process definitions: " + def);
		return def;
	}
	
	public void makeDeployment() throws FileNotFoundException{
		processEngine = ProcessEngines.getDefaultProcessEngine();
		repositoryService = processEngine.getRepositoryService();
		URL url = this.getClass().getResource("AgendarReunion.bar");
//		String barFileName = url.toString();
		String barFileName = "C:\\Users\\SEJO\\Documents\\registro_de_alumno.bar";
		File archivo = new File(barFileName);
		System.out.println(barFileName);
		ZipInputStream inputStream = new ZipInputStream(new FileInputStream(archivo));
//		repositoryService.createDeployment()
//		    .name("AgendarReunion.bar")
//		    .addZipInputStream(inputStream)
//		    .deploy();
		repositoryService.createDeployment()
		  .addClasspathResource("com/processEngine/AgendarReunion.bar")
		  .deploy();
		    
	}
	
}
