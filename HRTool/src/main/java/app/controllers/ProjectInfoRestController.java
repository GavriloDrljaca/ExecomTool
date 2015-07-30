package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.model.Project;
import app.model.ProjectInfo;
import app.repository.ProjectInfoRepository;
import app.repository.ProjectRepository;

@RestController
@RepositoryEventHandler(ProjectInfo.class)
@RequestMapping
public class ProjectInfoRestController {

	@Autowired
	ProjectInfoRepository projectInfoRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@RequestMapping("/prInfosForProject")
	public Iterable<ProjectInfo> projectInfosForProject(@RequestParam("id") int idProject) {
		Project p = projectRepository.findOne(idProject);
		return projectInfoRepository.findByProject(p);
	}
} 
