package app.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.model.TagCloud;
import app.repository.EmployeeRepository;
import app.repository.EmploymentInfoRepository;
import app.repository.ProjectRepository;
import app.repository.TagCloudRepository;

@RepositoryEventHandler(TagCloud.class)
@RestController
@RequestMapping
public class TagCloudRestController {

	@Autowired
	TagCloudRepository tagCloudRepository;

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	EmploymentInfoRepository employmentInfoRepository;
	
	
	
}
