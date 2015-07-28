package app.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.controllers.http.response.UserNotFoundException;
import app.model.Employee;
import app.repository.EmployeeRepository;


@RestController
@RequestMapping("/employees/{username}")
public class EmployeeRestController {
		
	@Autowired
	private  EmployeeRepository employeeRepository;
	

	@RequestMapping(method = RequestMethod.GET)
	Optional<Employee> readEmployee(@PathVariable String username)
	{
		this.validateEmployee(username);
		return this.employeeRepository.findByUsername(username);
	}
	
	
	private void validateEmployee(String username) {
		this.employeeRepository.findByUsername(username).orElseThrow(
				() -> new UserNotFoundException(username));
	}
	
}
