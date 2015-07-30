package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.model.Employee;
import app.model.Project;
import app.model.ProjectInfo;
import app.repository.ProjectInfoRepository;

@RestController
public class ProjectInfoRestController {

	@Autowired
	ProjectInfoRepository projectInfoRepository;
	
	@RequestMapping("/projectinfo")
	public ProjectInfo getProjectInfosByProjectAndEmployee(Project p, Employee e) {
		return projectInfoRepository.findByProjectAndEmployee(p, e);
	}
	
	
}
