package app.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.model.Employee;
import app.model.Project;
import app.model.ProjectInfo;
import app.repository.ProjectInfoRepository;
import app.repository.ProjectRepository;

@RestController
@RequestMapping("/projectinfo")
public class ProjectInfoRestController {

	@Autowired
	ProjectInfoRepository projectInfoRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@RequestMapping("/projectinfoprojectemployee")
	public ProjectInfo getProjectInfosByProjectAndEmployee(Project p, Employee e) {
		return projectInfoRepository.findByProjectAndEmployee(p, e);
	}
	
	@RequestMapping("/projectemployee")
	public List<Employee> getAllEmployeesByProject(@RequestParam("idProject") int idProject) {
		List<Employee> employees= new ArrayList<Employee>();
		Iterable<ProjectInfo> projectInfos = projectInfoRepository.findAll();
		for(ProjectInfo pi : projectInfos) {
			if(pi.getProject().getIdProject() == idProject) {
				employees.add(pi.getEmployee());
			}
		}
		return employees;
	}
} 
