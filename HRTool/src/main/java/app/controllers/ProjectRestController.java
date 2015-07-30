package app.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping("/getAll")
	public Iterable<Project> findAll() {
		return projectRepository.findAll();
	}
	
	@RequestMapping("/getProject")
	public Project getProject(@RequestParam("id") int id) {
		return projectRepository.findOne(id);
	}
	
	@RequestMapping("/saveProject")
	public Project saveProject(@RequestParam("project") Project p) {
		return projectRepository.save(p);
	}
	@RequestMapping("/deleteProject")
	public void deleteProject(@RequestParam("idProject") int idProject) {
		projectRepository.delete(idProject);
	}
}
