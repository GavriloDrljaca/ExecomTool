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
import app.model.TagCloud;
import app.model.TagCloudEnum;
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
			if (projectInfoes.size()==0 && sr.getSeniority() == null){
				addEmployee = true;
			}
			else{
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
			}
			
			if (!addEmployee) continue;
			if (sr.getYearsOfExperiance() > 0){
				if (sr.getYearsOfExperianceRelation() == null){
					sr.setYearsOfExperianceRelation("Equals");
				}
				switch(sr.getYearsOfExperianceRelation()){
					case "Less":
						if (emp.getYearsOfWorking() < sr.getYearsOfExperiance()){
							addEmployee = true;
						}else {
							if (addEmployee){
								addEmployee = false;
							}
						}
						break;
					case "More":
						if (emp.getYearsOfWorking() > sr.getYearsOfExperiance()){
							addEmployee = true;
						}else {
							if (addEmployee){
								addEmployee = false;
							}
						}
						break;
					case "Equals":
						if (emp.getYearsOfWorking() == sr.getYearsOfExperiance()){
							addEmployee = true;
						}else {
							if (addEmployee){
								addEmployee = false;
							}
						}
						break;
				}
			}
			if (!addEmployee) continue;
			
			if (sr.getYearOfEmploymentRelation() == null){
				sr.setYearOfEmploymentRelation("After");
			}
			employmentInfoes = emp.getEmpInfos();
			if (employmentInfoes.size() == 0 && sr.getYearOfEmployment()!=null){
				addEmployee = false;
			}
			else if (sr.getYearOfEmployment() !=null){
				for (EmploymentInfo ei : employmentInfoes){
					if (ei.getCompanyName().toLowerCase().equals("execom")){
						switch (sr.getYearOfEmploymentRelation()){
							case "Before":
								if (ei.getStartDate().before(sr.getYearOfEmployment())){
									addEmployee = true;
									break;
								}else{
									if (addEmployee){
										addEmployee = false;
									}
								}
								break;
							case "After":
								if (ei.getStartDate().after(sr.getYearOfEmployment())){
									addEmployee = true;
									break;
								}else{
									if (addEmployee){
										addEmployee = false;
									}
								}
								break;
						}
					}
					if(!addEmployee){
						break;
					}
				}
			}
			if (!addEmployee) continue;

			int count = 0;
			boolean stop;
			for (String tech : sr.getTechnology()){
				stop = false;
				for(ProjectInfo pi : projectInfoes){
					for (TagCloud tg : pi.getTagClouds()){
						if (tg.getTipTagCloud().equals(TagCloudEnum.Technologie) && tech.equals(tg.getNameTagCloud())){
							count++;
							stop=true;
							break;
						}
					}
					if (stop){
						break;
					}
				}	
			}
			if (count == sr.getTechnology().size()){
				addEmployee = true;
			}else{
				addEmployee = false;
			}
			if (!addEmployee) continue;
			
			count = 0;
			for (String db : sr.getDatabase()){
				stop = false;
				for(ProjectInfo pi : projectInfoes){
					for (TagCloud tg : pi.getTagClouds()){
						if (tg.getTipTagCloud().equals(TagCloudEnum.Database) && db.equals(tg.getNameTagCloud())){
							count++;
							stop=true;
							break;
						}
					}
					if (stop){
						break;
					}
				}		
			}
			if (count == sr.getDatabase().size()){
				addEmployee = true;
			}else{
				addEmployee = false;
			}
			if (!addEmployee) continue;
			
			count = 0;
			for (String edu : sr.getEducation()){
				for (TagCloud tg : emp.getTagClouds()){
					if (tg.getTipTagCloud().equals(TagCloudEnum.Education) && edu.equals(tg.getNameTagCloud())){
						count++;
						break;
					}
				}
			}
			if (count == sr.getEducation().size()){
				addEmployee = true;
			}else{
				addEmployee = false;
			}
			if (!addEmployee) continue;
			
			count = 0;
			for (String lang : sr.getLanguage()){
				for (TagCloud tg : emp.getTagClouds()){
					if (tg.getTipTagCloud().equals(TagCloudEnum.ForeignLanguage) && lang.equals(tg.getNameTagCloud())){
						count++;
						break;
					}
				}
			}
			if (count == sr.getLanguage().size()){
				addEmployee = true;
			}else{
				addEmployee = false;
			}
			if (!addEmployee) continue;
			
			count = 0;
			for (String pos : sr.getPosition()){
				stop = false;
				for(EmploymentInfo ei : employmentInfoes){
					for (TagCloud tg : ei.getTagClouds()){
						if (tg.getTipTagCloud().equals(TagCloudEnum.Position) && pos.equals(tg.getNameTagCloud())){
							count++;
							stop=true;
							break;
						}
					}
					if (stop){
						break;
					}
				}		
			}
			if (count == sr.getPosition().size()){
				addEmployee = true;
			}else{
				addEmployee = false;
			}
			if (!addEmployee) continue;
			
			count = 0;
			for (String jr : sr.getJobRole()){
				stop = false;
				for(ProjectInfo pi : projectInfoes){
					for (TagCloud tg : pi.getTagClouds()){
						if (tg.getTipTagCloud().equals(TagCloudEnum.JobRole) && jr.equals(tg.getNameTagCloud())){
							count++;
							stop=true;
							break;
						}
					}
					if (stop){
						break;
					}
				}		
			}
			if (count == sr.getJobRole().size()){
				addEmployee = true;
			}else{
				addEmployee = false;
			}
			if (!addEmployee) continue;
			
			if (addEmployee){
				matchingEmployees.add(emp);
			}
		}
			
		return matchingEmployees;
	}
}
