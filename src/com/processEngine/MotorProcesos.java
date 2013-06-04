package com.processEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;

public class MotorProcesos {
	public ProcessEngine processEngine;
	public RepositoryService repositoryService;
	public MotorProcesos() {
		processEngine = ProcessEngines.getDefaultProcessEngine();
		repositoryService = processEngine.getRepositoryService();
//		String barFileName = "../WebContent/WEB-INF/procesos/AgendarReunion.bar";
//		ZipInputStream inputStream = new ZipInputStream(new FileInputStream(barFileName));
		    
//		repositoryService.createDeployment()
//		    .name("AgendarReunion.bar")
//		    .addZipInputStream(inputStream)
//		    .deploy();
	}
	
}
