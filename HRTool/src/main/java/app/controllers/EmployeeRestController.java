
package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Employee;
import app.repository.EmployeeRepository;


@RestController
@RequestMapping("/employees")
@RepositoryEventHandler(Employee.class)
public class EmployeeRestController {
	 @Autowired
	 EmployeeRepository empRep;
	 @RequestMapping(method = RequestMethod.GET)
	 public ResponseEntity list(){
		 
		 return new ResponseEntity<>(empRep.findAll(), HttpStatus.OK);
	 }
}

