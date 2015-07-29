package app.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.controllers.http.response.ProjectNotFoundException;
import app.model.Project;
import app.repository.ProjectRepository;


@RestController
@RequestMapping("/projects/{nameProject}")
public class ProjectRestController {

	@Autowired
	private ProjectRepository projectRepository;
	

	@RequestMapping(method = RequestMethod.GET)
	Optional<Project> readProject(@PathVariable String projectName)
	{
		this.validateProject(projectName);
		return this.projectRepository.findByNameProject(projectName);
	}
	
	
	private void validateProject(String projectName)
	{
		this.projectRepository.findByNameProject(projectName).orElseThrow(
				() -> new ProjectNotFoundException(projectName));

	}

}
