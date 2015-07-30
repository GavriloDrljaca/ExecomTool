package app.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.model.Project;
import app.repository.ProjectRepository;


@RestController
@RequestMapping
@RepositoryEventHandler(Project.class)
public class ProjectRestController {

	@Autowired
	private ProjectRepository projectRepository;
	

	
}
