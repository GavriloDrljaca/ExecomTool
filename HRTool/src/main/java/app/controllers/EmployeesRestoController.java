package app.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Employee;
import app.repository.EmployeeRepository;



@RestController
@RequestMapping("/employees")
public class EmployeesRestoController {

	@Autowired
	EmployeeRepository employeeRepository;
	
	
	@RequestMapping(method = RequestMethod.GET)
	Iterable<Employee> readEmployee()
	{
		return this.employeeRepository.findAll();
	}
	
}
