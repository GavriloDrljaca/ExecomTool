package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.model.Project;
import app.repository.ProjectRepository;

@RestController
@RequestMapping("/projects")
public class ProjectRestController2 {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@RequestMapping(value = "/{idProject}", method = RequestMethod.POST)
	public void saveProject(@RequestParam("project") Project project) {
		projectRepository.save(project);
	}
	
	@RequestMapping(value = "/{idProject}", method = RequestMethod.DELETE)
	public void saveProject(@RequestParam("idProject") int idProject) {
		projectRepository.delete(idProject);
	}
	
	@RequestMapping(value = "/{idProject}", method = RequestMethod.PUT)
	public void updateProject(@RequestParam("project") Project project) {
		projectRepository.save(project);
	}
	
	@RequestMapping(value = "/{idProject}", method = RequestMethod.GET)
	public Project getProject(@RequestParam("idProject") int idProject) {
		return projectRepository.findOne(idProject);
	}
	@RequestMapping(value = "/all", method = RequestMethod.GET) 
	public Iterable<Project> findAll() {
		return projectRepository.findAll();
	}
}
