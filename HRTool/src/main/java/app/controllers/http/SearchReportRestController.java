package app.controllers.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.model.Employee;
import app.model.ProjectInfo;
import app.model.SearchReport;
import app.model.SeniorityEnum;
import app.repository.EmployeeRepository;
import app.repository.ProjectInfoRepository;
import app.repository.ProjectRepository;
import app.repository.TagCloudRepository;

@RestController
public class SearchReportRestController {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	TagCloudRepository tagCloudRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	ProjectInfoRepository projectInfoRepository;

	@RequestMapping("/report")
	public List<Employee> generateReport(@RequestBody SearchReport sr){
		List<Employee> allEmployees = employeeRepository.findAll();
		List<Employee> matchingEmployees = new ArrayList<Employee>();
		Set<ProjectInfo> projectInfoes;
		for (Employee emp : allEmployees){
			projectInfoes = emp.getProjectInfos();
			for (ProjectInfo pi : projectInfoes){
				if (SeniorityEnum.valueOf(sr.getSeniority()) == pi.getSeniority()){
					matchingEmployees.add(emp);
					break;
				}
			}
			
			
		}
			
		return matchingEmployees;
	}
}
