
package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.model.Employee;
import app.repository.EmployeeRepository;
import app.repository.ProjectRepository;


@RestController
@RequestMapping
@RepositoryEventHandler(Employee.class)
public class EmployeeRestController {
		
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	
	 
}

