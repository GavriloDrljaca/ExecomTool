package app.controllers;

import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.model.EmploymentInfo;

@RestController
@RequestMapping("/employmentInfoes")
@RepositoryEventHandler(EmploymentInfo.class)
public class EmploymentInfoRestController {

}
