package app.controllers.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.model.Employee;
import app.model.EmploymentInfo;
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
		Set<EmploymentInfo> employmentInfoes;
		for (Employee emp : allEmployees){
			boolean addEmployee = false;
			projectInfoes = emp.getProjectInfos();
			for (ProjectInfo pi : projectInfoes){
				if (sr.getSeniority() != null){
					if (SeniorityEnum.valueOf(sr.getSeniority()) == pi.getSeniority()){
						addEmployee = true;
						break;
					}else {
						if (addEmployee){
							addEmployee = false;
						}
					}
				}else {
					addEmployee = true;
					break;
				}
			}
			
			if (!addEmployee) continue;
			if(sr.getYearsOfExperianceRelation() == null){
				sr.setYearsOfExperianceRelation("More");
			}
			if (addEmployee && sr.getYearsOfExperianceRelation().equals("Less")){
				if (emp.getYearsOfWorking() < sr.getYearsOfExperiance()){
					addEmployee = true;
				}else {
					if (addEmployee){
						addEmployee = false;
					}
				}
			}else if (addEmployee && sr.getYearsOfExperianceRelation().equals("More")){
				if (emp.getYearsOfWorking() > sr.getYearsOfExperiance()){
					addEmployee = true;
				}else {
					if (addEmployee){
						addEmployee = false;
					}
				}
			}else if (addEmployee && sr.getYearsOfExperianceRelation().equals("Equals")){
				if (emp.getYearsOfWorking() == sr.getYearsOfExperiance()){
					addEmployee = true;
				}else {
					if (addEmployee){
						addEmployee = false;
					}
				}
			}
			if (!addEmployee) continue;
			employmentInfoes = emp.getEmpInfos();
			if (addEmployee){
				for (EmploymentInfo ei : employmentInfoes){
					if (ei.getCompanyName().toLowerCase().equals("execom") && sr.getYearOfEmployment() != null){
						if (ei.getStartDate().after(sr.getYearOfEmployment())){
							addEmployee = true;
							break;
						}else{
							if (addEmployee){
								addEmployee = false;
							}
						}
					}
				}	
			}
			if (!addEmployee) continue;
			
			if (addEmployee){
				matchingEmployees.add(emp);
			}
		}
			
		return matchingEmployees;
	}
}
