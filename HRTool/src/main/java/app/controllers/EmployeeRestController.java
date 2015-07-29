package app.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.controllers.http.response.UserNotFoundException;
import app.model.Employee;
import app.model.Project;
import app.model.ProjectInfo;
import app.repository.EmployeeRepository;
import app.repository.ProjectRepository;


@RestController
//@RequestMapping("/employees/{username}")
public class EmployeeRestController {
		
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	

	//@RequestMapping(method = RequestMethod.GET)
	Optional<Employee> readEmployee(@PathVariable String username)
	{
		this.validateEmployee(username);
		return this.employeeRepository.findByUsername(username);
	}
	
	
	private void validateEmployee(String username) {
		this.employeeRepository.findByUsername(username).orElseThrow(
				() -> new UserNotFoundException(username));
	}
	
	@RequestMapping("/employees")
	private Iterable<Employee> returnEmployees(){
		return employeeRepository.findAll();
	}
	
	@RequestMapping(value = "/projectinfos", method = RequestMethod.GET)
	public List<ProjectInfo> getProjectInfos(int idEmployee) {
		return this.employeeRepository.findOne(idEmployee).getProjectInfos();
	}
	
	@RequestMapping(value = "/projects", method = RequestMethod.GET)
	public Iterable<Project> getProjects(int idEmployee) {
		List<ProjectInfo> projectInfos = this.employeeRepository.findOne(idEmployee).getProjectInfos();
		List<Integer> idProjects = new ArrayList<Integer>();
		for(ProjectInfo pi : projectInfos) {
			idProjects.add(pi.getProject().getIdProject());
		}
		return this.projectRepository.findAll(idProjects);
	}
	
}
