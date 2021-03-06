package app.testData;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.model.Employee;
import app.model.Project;
import app.model.ProjectInfo;
import app.model.TagCloud;
import app.model.TagCloudEnum;
import app.repository.EmployeeRepository;
import app.repository.ProjectInfoRepository;
import app.repository.ProjectRepository;
import app.repository.TagCloudRepository;

@Component
public class ImportData {
	@Autowired
	TagCloudRepository tagRep;
	@Autowired
	EmployeeRepository empRep;
	@Autowired
	ProjectInfoRepository projInfoRep;
	@Autowired
	ProjectRepository projRep;

	public void addEmployees() {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");

		// EMPLOYEE PETAR
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
		emp.setTrainingLearningPriority("vrlo visoka");
		emp.setLicencesCertificates("Microsoft Licence");
		emp.setAwards("Best coder in the street");
		emp.setContractType("na neodredjeno");
		emp.setCommunication(3);
		emp.setFastLearning(4);
		emp.setOpenToChange(5);
		emp.setTeamPlayer(3);
		emp.setProactiveCommunication(4);
		emp.setInterpersonalSkills(5);
		emp.setKnowledgeSharing(3);
		emp.setJudgement(4);
		emp.setDecisionMaking(5);
		emp.setInfluencing(3);
		emp.setLeadership(4);
		emp.setCoaching(5);
		emp.setOrganizationalSkills(3);
		empRep.save(emp);

		// EMPLOYEE Snezana
		emp = new Employee();
		emp.setNameEmployee("Snezana Maric");
		emp.setGender("z");
		try {
			emp.setDateOfBirth(sdf.parse("1985-11-11"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		emp.setAddress("Brace Ribnikar 3, Novi Sad");
		emp.setPhoneNumber("0691221225");
		emp.setEmail("smaric@execom.eu");
		emp.setEmergencyPhoneNumber("021488599");


		try {
			emp.setStartDateFromBooklet(sdf.parse("2008-01-01"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		emp.setUsername("smaric");
		emp.setIdCardNumber("122133122");
		emp.setPassportNumber("504400500");
		emp.setYearsOfWorkingExpInExecom(5);
		emp.setYearsOfWorking(5);
		emp.setPlaceOfBirth("Zrenjanin");
		emp.setTrainingLearningPriority("srednja");
		emp.setLicencesCertificates("Microsoft Licence, Cisco licence");
		emp.setAwards("Best cook in Execom, xD");
		emp.setContractType("na neodredjeno");
		emp.setCommunication(3);
		emp.setFastLearning(4);
		emp.setOpenToChange(5);
		emp.setTeamPlayer(3);
		emp.setProactiveCommunication(4);
		emp.setInterpersonalSkills(5);
		emp.setKnowledgeSharing(3);
		emp.setJudgement(4);
		emp.setDecisionMaking(5);
		emp.setInfluencing(3);
		emp.setLeadership(4);
		emp.setCoaching(5);
		emp.setOrganizationalSkills(3);
		empRep.save(emp);

		// EMPLOYEE Milos
		emp = new Employee();
		emp.setNameEmployee("Milos Milosevic");
		emp.setGender("m");
		try {
			emp.setDateOfBirth(sdf.parse("1989-11-11"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		emp.setAddress("Danila Kisa, Novi Sad");
		emp.setPhoneNumber("0691221225");
		emp.setEmail("mmilosevic@execom.eu");
		emp.setEmergencyPhoneNumber("021488599");

		try {
			emp.setStartDateFromBooklet(sdf.parse("2010-01-01"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		emp.setUsername("mmilosevic");
		emp.setIdCardNumber("122133122");
		emp.setPassportNumber("504400500");
		emp.setYearsOfWorkingExpInExecom(5);
		emp.setYearsOfWorking(5);
		emp.setPlaceOfBirth("Zrenjanin");
		emp.setTrainingLearningPriority("srednja");
		emp.setLicencesCertificates("Microsoft Licence, Cisco licence");
		emp.setAwards("Best coder in the countrey");
		emp.setContractType("na neodredjeno");
		emp.setCommunication(3);
		emp.setFastLearning(4);
		emp.setOpenToChange(5);
		emp.setTeamPlayer(3);
		emp.setProactiveCommunication(4);
		emp.setInterpersonalSkills(5);
		emp.setKnowledgeSharing(3);
		emp.setJudgement(4);
		emp.setDecisionMaking(5);
		emp.setInfluencing(3);
		emp.setLeadership(4);
		emp.setCoaching(5);
		emp.setOrganizationalSkills(3);
		empRep.save(emp);
		
		
		// EMPLOYEE Nemanja
			emp = new Employee();
			emp.setNameEmployee("Nemanja Milutinovic");
			emp.setGender("m");
			try {
				emp.setDateOfBirth(sdf.parse("1992-05-21"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			emp.setAddress("Polgar Andrasa 42a, Novi Sad");
			emp.setPhoneNumber("0645555555");
			emp.setEmail("nemanja.milutinovicc@gmail.com");
			emp.setEmergencyPhoneNumber("021488599");


			try {
				emp.setStartDateFromBooklet(sdf.parse("2008-01-01"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			emp.setUsername("nemanja.milutinovicc");
			emp.setIdCardNumber("555555555");
			emp.setPassportNumber("504400500");
			emp.setYearsOfWorkingExpInExecom(5);
			emp.setYearsOfWorking(5);
			emp.setPlaceOfBirth("Zajecar");
			emp.setTrainingLearningPriority("srednja");
			emp.setLicencesCertificates("Microsoft Licence, Cisco licence");
			emp.setAwards("Inter in Execom");
			emp.setContractType("na neodredjeno");
			emp.setCommunication(3);
			emp.setFastLearning(3);
			emp.setOpenToChange(5);
			emp.setTeamPlayer(3);
			emp.setProactiveCommunication(4);
			emp.setInterpersonalSkills(5);
			emp.setKnowledgeSharing(3);
			emp.setJudgement(4);
			emp.setDecisionMaking(5);
			emp.setInfluencing(3);
			emp.setLeadership(4);
			emp.setCoaching(5);
			emp.setOrganizationalSkills(3);
			empRep.save(emp);

	}

	public void addProjects() {

		Project p = new Project();
		p.setNameProject("HRTool");
		p.setDurationOfProject(5);
		projRep.save(p);

		p = new Project();
		p.setNameProject("SmartHouse");
		p.setDurationOfProject(10);
		projRep.save(p);

		p = new Project();
		p.setNameProject("Bicycle web shop");
		p.setDurationOfProject(10);
		projRep.save(p);

	}

	public void addTagClouds() {
		TagCloud tc = new TagCloud();

		tc.setNameTagCloud("Java");
		tc.setTipTagCloud(TagCloudEnum.Technologie);
		tagRep.save(tc);

		tc = new TagCloud();

		tc.setNameTagCloud("C#");
		tc.setTipTagCloud(TagCloudEnum.Technologie);
		tagRep.save(tc);

		tc = new TagCloud();

		tc.setNameTagCloud("C++");
		tc.setTipTagCloud(TagCloudEnum.Technologie);
		tagRep.save(tc);

		tc = new TagCloud();

		tc.setNameTagCloud("mySQL");
		tc.setTipTagCloud(TagCloudEnum.Database);
		tagRep.save(tc);

		tc = new TagCloud();

		tc.setNameTagCloud("Oracle");
		tc.setTipTagCloud(TagCloudEnum.Database);
		tagRep.save(tc);

		tc = new TagCloud();

		tc.setNameTagCloud("Windows");
		tc.setTipTagCloud(TagCloudEnum.OS);
		tagRep.save(tc);

		tc = new TagCloud();

		tc.setNameTagCloud("UBUNTU");
		tc.setTipTagCloud(TagCloudEnum.OS);
		tagRep.save(tc);

		tc = new TagCloud();

		tc.setNameTagCloud("Server");
		tc.setTipTagCloud(TagCloudEnum.Platform);
		tagRep.save(tc);

		tc = new TagCloud();

		tc.setNameTagCloud("Desktop");
		tc.setTipTagCloud(TagCloudEnum.Platform);
		tagRep.save(tc);

		tc = new TagCloud();

		tc.setNameTagCloud("Web");
		tc.setTipTagCloud(TagCloudEnum.Platform);
		tagRep.save(tc);

		tc = new TagCloud();

		tc.setNameTagCloud("Eclipse");
		tc.setTipTagCloud(TagCloudEnum.IDE);
		tagRep.save(tc);
		tc = new TagCloud();

		tc.setNameTagCloud("Microsoft Visual Studio");
		tc.setTipTagCloud(TagCloudEnum.IDE);
		tagRep.save(tc);

		tc = new TagCloud();

		tc.setNameTagCloud("xml");
		tc.setTipTagCloud(TagCloudEnum.Technologie);
		tagRep.save(tc);

		tc = new TagCloud();

		tc.setNameTagCloud("Spring");
		tc.setTipTagCloud(TagCloudEnum.Technologie);
		tagRep.save(tc);

		tc = new TagCloud();

		tc.setNameTagCloud("Team leader");
		tc.setTipTagCloud(TagCloudEnum.Position);
		tagRep.save(tc);

		tc = new TagCloud();

		tc.setNameTagCloud("Software engineer");
		tc.setTipTagCloud(TagCloudEnum.Position);
		tagRep.save(tc);

		tc = new TagCloud();

		tc.setNameTagCloud("Army");
		tc.setTipTagCloud(TagCloudEnum.Industry);
		tagRep.save(tc);
		tc = new TagCloud();

		tc.setNameTagCloud("Comercial");
		tc.setTipTagCloud(TagCloudEnum.Industry);
		tagRep.save(tc);

		tc = new TagCloud();

		tc.setNameTagCloud("Naval");
		tc.setTipTagCloud(TagCloudEnum.Industry);
		tagRep.save(tc);

		tc = new TagCloud();

		tc.setNameTagCloud("High School");
		tc.setTipTagCloud(TagCloudEnum.Education);
		tagRep.save(tc);

		tc = new TagCloud();

		tc.setNameTagCloud("master");
		tc.setTipTagCloud(TagCloudEnum.Education);
		tagRep.save(tc);

		tc = new TagCloud();

		tc.setNameTagCloud("English");
		tc.setTipTagCloud(TagCloudEnum.ForeignLanguage);
		tagRep.save(tc);

		tc = new TagCloud();

		tc.setNameTagCloud("CEO");
		tc.setTipTagCloud(TagCloudEnum.ForeignLanguage);
		tagRep.save(tc);

		tc = new TagCloud();

		tc.setNameTagCloud("Project manager");
		tc.setTipTagCloud(TagCloudEnum.Position);
		tagRep.save(tc);

		tc = new TagCloud();

		tc.setNameTagCloud("HR manager");
		tc.setTipTagCloud(TagCloudEnum.Position);
		tagRep.save(tc);

	}

	public void addProjectInfos() {

		

	}
}
