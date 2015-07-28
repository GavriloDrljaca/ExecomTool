package rest;

import java.util.Optional;

import http.response.UserNotFoundException;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Employee;
import app.repository.EmployeeRepository;


@RestController
@RequestMapping("/{username}")
public class EmployeeRestController {
		
	@Autowired
	private final EmployeeRepository employeeRepository;
	
	
	public EmployeeRestController(EmployeeRepository employeeRepository) {
		
		this.employeeRepository = employeeRepository;
	}
	

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
