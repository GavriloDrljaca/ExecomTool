
package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.controllers.http.response.UserNotFoundException;
import app.model.Employee;
import app.model.TagCloud;
import app.repository.EmployeeRepository;
import app.repository.ProjectRepository;
import app.repository.TagCloudRepository;


@RestController
@RequestMapping("/employees")
@RepositoryEventHandler(Employee.class)
public class EmployeeRestController {

	 
}

