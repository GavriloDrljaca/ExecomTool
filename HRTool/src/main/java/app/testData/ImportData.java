package app.testData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import app.model.Employee;
import app.repository.EmployeeRepository;
import app.repository.ProjectInfoRepository;
import app.repository.ProjectRepository;
import app.repository.TagCloudRepository;

public class ImportData {
	@Autowired
	public static TagCloudRepository tagRep;
	@Autowired
	public static EmployeeRepository empRep;
	@Autowired
	public static ProjectInfoRepository projInfoRep;
	@Autowired
	public static ProjectRepository projRep;
	
	
	public static void addEmployees(){
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
		Employee emp = new Employee();
		emp.setNameEmployee("Petar Petrovic");
		emp.setGender("m");
		try {
			emp.setDateOfBirth(sdf.parse("1969-11-11"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		emp.setAddress("Bulevar Oslobodjenja 25, Novi Sad");
		emp.setPhoneNumber("0691221225");
		emp.setEmail("ppetar@execom.eu");
		emp.setEmergencyPhoneNumber("021488599");
		try {
			emp.setStartDate(sdf.parse("2000-01-01"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			emp.setEndDate(sdf.parse("2010-05-05"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			emp.setStartDateFromBooklet(sdf.parse("1995-06-06"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		emp.setUsername("ppetar");
		emp.setIdCardNumber("122122122");
		emp.setPassportNumber("500500500");
		emp.setYearsOfWorkingExpInExecom(10);
		emp.setYearsOfWorking(15);
		emp.setPlaceOfBirth("Novi Sad");
		
		
		
	}
	
	

}
