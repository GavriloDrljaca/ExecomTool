
package app.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app.controllers.http.response.UserNotFoundException;
import app.model.Employee;
import app.model.Project;
import app.model.ProjectInfo;
import app.repository.EmployeeRepository;
import app.repository.ProjectRepository;


@RestController
@RequestMapping("/employees")
public class EmployeeRestController {
		
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	
	@RequestMapping(method = RequestMethod.GET)
	Iterable<Employee> readEmployee() {
		return this.employeeRepository.findAll();
	}

	@RequestMapping(value = "/{username:.+}", method = RequestMethod.GET)
	@ResponseBody
	Employee readEmployee(@PathVariable("username") String username){
		this.validateEmployee(username);
		return this.employeeRepository.findByUsername(username);
	}
	
	
	private void validateEmployee(String username) {
		if(this.employeeRepository.findByUsername(username) == null) throw
				 new UserNotFoundException(username);
	}
	
	
	@RequestMapping(value = "/{username:.+}/projectinfos", method = RequestMethod.GET)
	public Iterable<ProjectInfo> getProjectInfos(@PathVariable("username") String username) {
		return this.employeeRepository.findByUsername(username).getProjectInfos();
	}
	
	@RequestMapping(value = "/{username:.+}/projects", method = RequestMethod.GET)
	public Iterable<Project> getProjects(@PathVariable("username") String  username) {
		List<ProjectInfo> projectInfos = this.employeeRepository.findByUsername(username).getProjectInfos();
		List<Integer> idProjects = new ArrayList<Integer>();
		for(ProjectInfo pi : projectInfos) {
			idProjects.add(pi.getProject().getIdProject());
		}
		return this.projectRepository.findAll(idProjects);
	}
	
}

