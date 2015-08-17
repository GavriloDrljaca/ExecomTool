package app.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Project;
import app.model.ProjectInfo;
import app.repository.ProjectInfoRepository;
import app.repository.ProjectRepository;


@RestController
@RequestMapping("projects")
@RepositoryEventHandler(Project.class)
public class ProjectRestController {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ProjectInfoRepository projectInfoRepository;
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public Project deleteProject(@PathVariable("id") int id){
		List<ProjectInfo> projectInfoes = projectInfoRepository.findAll();
		for (ProjectInfo pi : projectInfoes){
			if (pi.getProject().getIdProject() == id){
			//	pi.setEmployee(null);
			//	pi.setProject(null);
				pi.setTagClouds(null);
				projectInfoRepository.delete(pi.getIdProjectInfo());
			}
		}
		projectRepository.delete(id);
 		return null;
	}
	
}
