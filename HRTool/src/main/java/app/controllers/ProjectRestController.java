package app.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.controllers.http.response.ProjectNotFoundException;
import app.model.Project;
import app.repository.ProjectRepository;


@RestController
@RequestMapping("/projects")
public class ProjectRestController {

	@Autowired
	private ProjectRepository projectRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	Iterable<Project> readProjects(){
		return this.projectRepository.findAll();
	}
	
	
	@RequestMapping(value = "/{projectName}", method = RequestMethod.GET)
	Project readProject(@PathVariable String projectName)
	{
		this.validateProject(projectName);
		return this.projectRepository.findByNameProject(projectName);
	}
	
	
	private void validateProject(String projectName)
	{
		if(this.projectRepository.findByNameProject(projectName)==null)
				throw new ProjectNotFoundException(projectName);

	}
	
}
