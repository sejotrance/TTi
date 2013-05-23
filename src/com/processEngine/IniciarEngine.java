package com.processEngine;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;

public class IniciarEngine {
	public ProcessEngine processEngine;
	public RepositoryService repositoryService;
	public IniciarEngine() {
		processEngine = ProcessEngines.getDefaultProcessEngine();
		repositoryService = processEngine.getRepositoryService();
	}
	
}
