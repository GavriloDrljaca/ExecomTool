package app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import app.model.Employee;
import app.model.EmployeeRole;
import app.model.EmploymentInfo;
import app.model.Project;
import app.model.ProjectInfo;
import app.model.SeniorityEnum;
import app.model.TagCloud;
import app.model.TagCloudEnum;
import app.repository.EmployeeRepository;
import app.repository.EmploymentInfoRepository;
import app.repository.ProjectInfoRepository;
import app.repository.ProjectRepository;
import app.repository.TagCloudRepository;

@SpringBootApplication

public class Application extends SpringBootServletInitializer implements CommandLineRunner {

	@Autowired
	TagCloudRepository tagRep;
	@Autowired
	EmployeeRepository empRep;
	@Autowired
	ProjectInfoRepository projInfoRep;
	@Autowired
	ProjectRepository projRep;
	@Autowired

	EmploymentInfoRepository empInfoRep;
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

    private static Class<Application> applicationClass = Application.class;
    
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }
	
	//aaaaaaaaaaaaaaaaaaaaaaaaaa
	@Override
	public void run(String... strings) throws Exception {
		addEmployees();
		addProjects();
		addTagClouds();
		addProjectInfos();
		addTagsToEmployee();
		addEmploymentInfos();
		addTagsToEmpInfo();
		addTagsToProject();
		addTagsToProjectInfos();
		//add position into employmentInfoes
		
		/*empInfoRep.findOne(1).getTagClouds().add(tagRep.findOne(24));
		empInfoRep.findOne(2).getTagClouds().add(tagRep.findOne(24));
		empInfoRep.findOne(2).getTagClouds().add(tagRep.findOne(25));
		*/

	}
	
	@Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
     
       return (container -> {
            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/app/errorpages/401.html");
            ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/app/errorpages/403.html");
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/app/errorpages/404.html");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/app/errorpages/500.html");

            container.addErrorPages(error401Page, error404Page, error500Page, error403Page);
       });
    }

	public void addEmployees() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		// EMPLOYEE PETAR
		Employee emp = new Employee();
		emp.setNameEmployee("Petar Petrovic");
		emp.setGender("Male");
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
		emp.setEmployeeRole(EmployeeRole.EMP);
		empRep.save(emp);

		// EMPLOYEE Snezana
		emp = new Employee();
		emp.setNameEmployee("Snezana Maric");
		emp.setGender("Female");
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
		emp.setEmployeeRole(EmployeeRole.EMP);
		empRep.save(emp);

		// EMPLOYEE Milos
		emp = new Employee();
		emp.setNameEmployee("Milos Milosevic");
		emp.setGender("Male");
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
		emp.setEmployeeRole(EmployeeRole.EMP);
		empRep.save(emp);

		// EMPLOYEE Nemanja
		emp = new Employee();
		emp.setNameEmployee("Nemanja Milutinovic");
		emp.setGender("Male");
		try {
			emp.setDateOfBirth(sdf.parse("1992-05-21"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		emp.setAddress("Polgar Andrasa 42a, Novi Sad");
		emp.setPhoneNumber("0645555555");
		emp.setEmail("nemanja.milutinovicc@gmail.com");
		//emp.setEmail("");
		emp.setEmergencyPhoneNumber("0000000000");
		

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
		
		emp.setEmployeeRole(EmployeeRole.HRM);
		empRep.save(emp);

	}

	public void addProjects() {

		Project p = new Project();
		p.setNameProject("SmartHouse");
		p.setDurationOfProject(10);
		p.setStartDate(new Date());
		p.setExecom(true);
		projRep.save(p);
		
		p = new Project();
		p.setNameProject("HRTool");
		p.setDurationOfProject(5);
		p.setStartDate(new Date());
		p.getStartDate().setTime(p.getStartDate().getTime() + 1000000);
		p.setExecom(true);
		projRep.save(p);

		p = new Project();
		p.setNameProject("Bicycle web shop");
		p.setDurationOfProject(10);
		p.setStartDate(new Date());
		p.setExecom(true);
		projRep.save(p);
		
		p = new Project();
		p.setNameProject("Glorified calculator");
		p.setDurationOfProject(20);
		p.setStartDate(new Date());
		p.setExecom(true);
		projRep.save(p);

	}

	public void addTagClouds() {
		TagCloud tc = new TagCloud();

		tc.setNameTagCloud("Java");
		tc.setTipTagCloud(TagCloudEnum.Technologie);

		/*
		 * Employee e = (Employee) empRep.findOne(1);
		 * 
		 * List<Employee> employees = new ArrayList<>(); employees.add(e);
		 * tc.setEmployees(employees);
		 */
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
		
		tc = new TagCloud();

		tc.setNameTagCloud("UI designer");
		tc.setTipTagCloud(TagCloudEnum.JobRole);
		tagRep.save(tc);
		
		tc = new TagCloud();

		tc.setNameTagCloud("Software architect");
		tc.setTipTagCloud(TagCloudEnum.JobRole);
		tagRep.save(tc);

	}

	public void addProjectInfos() {

		ProjectInfo pi = new ProjectInfo();

		pi.setEmployee(empRep.findOne(1));
		pi.setProject(projRep.findOne(2));
		pi.setProjectExp("Learned a lot about frontend");
		pi.setSeniority(SeniorityEnum.Senior);
		pi.setDurationOnProject(9);
		pi.setJobResponsibilities("frontend");
		pi.setActive(true);
		/*
		 * Employee emp = empRep.findOne(1); emp.getProjectInfos().add(pi);
		 * empRep.save(emp); Project p = projRep.findOne(2);
		 * p.getProjectInfo().add(pi); projRep.save(p);
		 */
		projInfoRep.save(pi);

		pi = new ProjectInfo();

		pi.setEmployee(empRep.findOne(1));
		pi.setProject(projRep.findOne(1));
		pi.setJobResponsibilities("backend");
		pi.setDurationOnProject(5);
		pi.setSeniority(SeniorityEnum.Junior);
		pi.setProjectExp("Learned a lot about backend");
		pi.setActive(true);

		projInfoRep.save(pi);
		pi = new ProjectInfo();

		pi.setEmployee(empRep.findOne(1));
		pi.setProject(projRep.findOne(3));
		pi.setJobResponsibilities("Frontend, user interface");
		pi.setSeniority(SeniorityEnum.Junior);
		pi.setProjectExp("Designed UI");
		pi.setActive(true);

		projInfoRep.save(pi);

	}

	public void addTagsToEmployee() {
		  Employee e = (Employee) empRep.findOne(1);
		  e.getTagClouds().add(tagRep.findOne(1));
		  e.getTagClouds().add(tagRep.findOne(2));
		  e.getTagClouds().add(tagRep.findOne(3));
		  e.getTagClouds().add(tagRep.findOne(4));
		 

		 empRep.save(e);
	}
	public void addEmploymentInfos(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		EmploymentInfo empInfo = new EmploymentInfo();
		
		empInfo.setCompanyName("DmMS");
		try {
			empInfo.setStartDate(sdf.parse("2005-01-01"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			empInfo.setEndDate(sdf.parse("2008-01-01"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		empInfo.setEmployee(empRep.findOne(1));
		
		empInfoRep.save(empInfo);
		
		empInfo = new EmploymentInfo();
		
		empInfo.setCompanyName("Execom");
		try {
			empInfo.setStartDate(sdf.parse("2008-01-04"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		empInfo.setEmployee(empRep.findOne(1));
		
		empInfoRep.save(empInfo);
		
		Set<TagCloud> bla = empInfoRep.findOne(1).getTagClouds();
		System.out.println();
		
	}
	public void addTagsToEmpInfo(){
		System.out.println("EMP: "+ empInfoRep.findOne(1).getCompanyName() + "   TAG: "+tagRep.findOne(24).getNameTagCloud());
		System.out.println(empInfoRep.findOne(1).getTagClouds());
		
		EmploymentInfo ei =empInfoRep.findOne(1); 
		ei.getTagClouds().add(tagRep.findOne(24));
		ei.getTagClouds().add(tagRep.findOne(25));
		empInfoRep.save(ei);
		
		ei =empInfoRep.findOne(2);
		ei.getTagClouds().add(tagRep.findOne(24));
		empInfoRep.save(ei);
		System.out.println(empInfoRep.findOne(1).getTagClouds());
	}
	public void addTagsToProject(){
		Project p = projRep.findOne(1);
		p.getTagClouds().add(tagRep.findOne(6));
		p.getTagClouds().add(tagRep.findOne(8));
		p.getTagClouds().add(tagRep.findOne(17));
		projRep.save(p);
	}
	public void addTagsToProjectInfos(){
		ProjectInfo pi = projInfoRep.findOne(1);
		pi.getTagClouds().add(tagRep.findOne(1));
		pi.getTagClouds().add(tagRep.findOne(2));
		pi.getTagClouds().add(tagRep.findOne(4));
		pi.getTagClouds().add(tagRep.findOne(11));
		pi.getTagClouds().add(tagRep.findOne(26));
		
		projInfoRep.save(pi);
		pi = projInfoRep.findOne(2);
		pi.getTagClouds().add(tagRep.findOne(1));
		pi.getTagClouds().add(tagRep.findOne(4));
		pi.getTagClouds().add(tagRep.findOne(11));
		pi.getTagClouds().add(tagRep.findOne(27));
		
		projInfoRep.save(pi);
		pi = projInfoRep.findOne(3);
		pi.getTagClouds().add(tagRep.findOne(1));
		
		projInfoRep.save(pi);
	}
}