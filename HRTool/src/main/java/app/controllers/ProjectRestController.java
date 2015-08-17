package app.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public Project deleteProject(@RequestParam("id") String idProject){
		int id = Integer.parseInt(idProject);
		List<ProjectInfo> projectInfoes = projectInfoRepository.findAll();
		for (ProjectInfo pi : projectInfoes){
			if (pi.getProject().getIdProject() == id){
				System.out.println("Pre nasao");
			//	pi.setEmployee(null);
			//	pi.setProject(null);
				pi.setTagClouds(null);
				System.out.println(pi.getIdProjectInfo());
				projectInfoRepository.delete(pi.getIdProjectInfo());
				System.out.println(projectInfoRepository.getOne(pi.getIdProjectInfo()));
				System.out.println("Posle nasao");
			}
		}
		projectRepository.delete(id);
 		return null;
	}
	
}
