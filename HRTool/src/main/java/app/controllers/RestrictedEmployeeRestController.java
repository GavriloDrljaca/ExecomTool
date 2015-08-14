package app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Employee;
import app.repository.EmployeeRepository;

@RestController
@RequestMapping("/restrictedEmployees")
public class RestrictedEmployeeRestController {

	@Autowired
	EmployeeRepository empRep;
	
	@RequestMapping(value="/officeManager", method = RequestMethod.GET)
	public List<Employee> listForOfficeManager(){
		List<Employee> emps = empRep.findAll();
		 
		for(Employee emp : emps){
			emp = restrictForOfficeManager(emp);
		}
		 
		return emps;
	}

	@RequestMapping(value="/employee", method = RequestMethod.GET)
	public List<Employee> listForEmployee(){
		List<Employee> emps = empRep.findAll();
		 
		for(Employee emp : emps){
			emp = restrictForEmployee(emp);
		}
		 
		return emps;
	}
	
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public void updateEmployee(@RequestBody Employee employee){
		Employee emp = empRep.findOne(employee.getIdEmployee());
		emp.setNameEmployee(employee.getNameEmployee());
		emp.setGender(employee.getGender());
		emp.setDateOfBirth(employee.getDateOfBirth());
		emp.setAddress(employee.getAddress());
		emp.setPhoneNumber(employee.getPhoneNumber());
		emp.setEmail(employee.getEmail());
		emp.setEmergencyPhoneNumber(employee.getEmergencyPhoneNumber());
		empRep.save(emp);
	}
	 
	 private Employee restrictForEmployee(Employee emp){
		 emp.setIdEmployee(-1);
		 emp.setDateOfBirth(null);
		 emp.setAwards(null);
		 emp.setCoaching(-1);
		 emp.setCommunication(-1);
		 emp.setCommunication(-1);
		 emp.setContractType(null);
		 emp.setDecisionMaking(-1);
		 emp.setEmpInfos(null);
		 emp.setFastLearning(-1);
		 emp.setGender(null);
		 emp.setGoogleId(null);
		 emp.setIdCardNumber(null);
		 emp.setInfluencing(-1);
		 emp.setInterpersonalSkills(-1);
		 emp.setJudgement(-1);
		 emp.setKnowledgeSharing(-1);
		 emp.setLeadership(-1);
		 emp.setLicencesCertificates(null);
		 emp.setOpenToChange(-1);
		 emp.setOrganizationalSkills(-1);
		 emp.setPassportNumber(null);
		 emp.setPlaceOfBirth(null);
		 emp.setProactiveCommunication(-1);
		 emp.setProjectInfos(null);
		 emp.setStartDateFromBooklet(null);
		 emp.setTagClouds(null);
		 emp.setTeamPlayer(-1);
		 emp.setTrainingLearningPriority(null);
		 emp.setUsername(null);
		 emp.setYearsOfWorking(-1);
		 emp.setYearsOfWorkingExpInExecom(-1);
		 return emp;
	 }

	private Employee restrictForOfficeManager(Employee emp) {
			 emp.setAwards(null);
			 emp.setCoaching(-1);
			 emp.setCommunication(-1);
			 emp.setCommunication(-1);
			 emp.setContractType(null);
			 emp.setDecisionMaking(-1);
			 emp.setEmpInfos(null);
			 emp.setFastLearning(-1);
			 emp.setGoogleId(null);
			 emp.setIdCardNumber(null);
			 emp.setInfluencing(-1);
			 emp.setInterpersonalSkills(-1);
			 emp.setJudgement(-1);
			 emp.setKnowledgeSharing(-1);
			 emp.setLeadership(-1);
			 emp.setLicencesCertificates(null);
			 emp.setOpenToChange(-1);
			 emp.setOrganizationalSkills(-1);
			 emp.setPassportNumber(null);
			 emp.setPlaceOfBirth(null);
			 emp.setProactiveCommunication(-1);
			 emp.setProjectInfos(null);
			 emp.setStartDateFromBooklet(null);
			 emp.setTagClouds(null);
			 emp.setTeamPlayer(-1);
			 emp.setTrainingLearningPriority(null);
			 emp.setUsername(null);
			 emp.setYearsOfWorking(-1);
			 emp.setYearsOfWorkingExpInExecom(-1);
			 return emp;
	}
}
