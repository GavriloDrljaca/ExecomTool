package app.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Phrase;
import com.lowagie.text.rtf.RtfWriter2;

import app.generator.CVGenerator;
import app.model.Employee;
import app.model.Project;
import app.model.ProjectInfo;
import app.model.TagCloud;
import app.model.TagCloudEnum;
import app.repository.EmployeeRepository;
import app.repository.ProjectInfoRepository;
import app.repository.ProjectRepository;
import app.repository.TagCloudRepository;

@RestController
@RequestMapping("/test")
public class ReportRestController {
	
public static Employee getEmployee() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
		
		Employee emp = new Employee();
		emp.setNameEmployee("Petar Petrovic");
		emp.setGender("m");
		try {
			emp.setDateOfBirth(sdf.parse("1969-11-11"));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		emp.setAddress("Bulevar Oslobodjenja 25, Novi Sad");
		emp.setPhoneNumber("0691221225");
		emp.setEmail("ppetar@execom.eu");
		emp.setEmergencyPhoneNumber("021488599");

		try {
			emp.setStartDateFromBooklet(sdf.parse("1995-06-06"));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		emp.setUsername("ppetar");
		emp.setIdCardNumber("122122122");
		emp.setPassportNumber("500500500");
		emp.setYearsOfWorkingExpInExecom(10);
		emp.setYearsOfWorking(15);
		emp.setPlaceOfBirth("Novi Sad");
		emp.setTrainingLearningPriority("well organised, analytical thinker, hard-working, loyal and communicative.");
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
		
		Set<Employee> emps = new HashSet<>();
		emps.add(emp);
		Set<TagCloud> tagClouds = new HashSet<>();
		
		Set<Project> listaProj = new HashSet<Project>();
		
		TagCloud tc = new TagCloud();
		tc.setEmployees(emps);
		tc.setNameTagCloud("M.Sc. Faculty of Technical Sciences");
		tc.setTipTagCloud(TagCloudEnum.Education);
		tagClouds.add(tc);
		
		TagCloud tc2 = new TagCloud();
		tc2.setEmployees(emps);
		tc2.setNameTagCloud("PhD Faculty of Technical Sciences");
		tc2.setTipTagCloud(TagCloudEnum.Education);
		tagClouds.add(tc2);
		
		TagCloud tc3 = new TagCloud();
		tc3.setEmployees(emps);
		tc3.setNameTagCloud("English- fluent in both speaking and writing");
		tc3.setTipTagCloud(TagCloudEnum.ForeignLanguage);
		tagClouds.add(tc3);
		
		TagCloud tc4 = new TagCloud();
		tc4.setEmployees(emps);
		tc4.setNameTagCloud("Serbian - native");
		tc4.setTipTagCloud(TagCloudEnum.ForeignLanguage);
		tagClouds.add(tc4);
		
		emp.setTagClouds(tagClouds);
		Set<ProjectInfo> listaProjInfo = new HashSet<>();
		
		ProjectInfo p1 = new ProjectInfo();
		p1.setJobResponsibilities("Backend");
		p1.setProjectExp("project exp");

		p1.setEmployee(emp);
		listaProjInfo.add(p1);
		
		ProjectInfo p2 = new ProjectInfo();
		p2.setJobResponsibilities("Frontend");
		p2.setProjectExp("project exp");

		p2.setEmployee(emp);
		listaProjInfo.add(p1);
		
		emp.setProjectInfos(listaProjInfo);
		
		Set<TagCloud> tagClouds2 = new HashSet<>();
		
		Project p = new Project();
		p.setNameProject("HRTool");
		p.setDurationOfProject(5);
		p.setTagClouds(tagClouds2);
		listaProj.add(p);

		Project pr1 = new Project();
		pr1.setNameProject("SmartHouse");
		pr1.setDurationOfProject(10);
		listaProj.add(pr1);

		Project pr2 = new Project();
		pr2.setNameProject("Bicycle web shop");
		pr2.setDurationOfProject(10);
		listaProj.add(pr2);
		
		TagCloud tcc = new TagCloud();
		tcc.setProjects(listaProj);
		tcc.setNameTagCloud("Java");
		tcc.setTipTagCloud(TagCloudEnum.Technologie);
		tagClouds2.add(tcc);
		
		TagCloud tcc2 = new TagCloud();
		tcc2.setProjects(listaProj);
		tcc2.setNameTagCloud("C#");
		tcc2.setTipTagCloud(TagCloudEnum.Technologie);
		tagClouds2.add(tcc2);
		
		TagCloud tcdb = new TagCloud();
		tcdb.setProjects(listaProj);
		tcdb.setNameTagCloud("MySQL");
		tcdb.setTipTagCloud(TagCloudEnum.Database);
		tagClouds2.add(tcdb);
		
		TagCloud ide = new TagCloud();
		ide.setProjects(listaProj);
		ide.setNameTagCloud("Eclipse");
		ide.setTipTagCloud(TagCloudEnum.IDE);
		tagClouds2.add(ide);
		
		p1.setProject(pr1);
		p2.setProject(pr2);
		
		return emp;
	}
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	TagCloudRepository tagCloudRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	ProjectInfoRepository projectInfoRepository;

	@RequestMapping("/rtf")
	public ResponseEntity<byte[]> generateRtf(@RequestParam("idEmployee") int idEmployee) {
		Employee e = employeeRepository.getOne(idEmployee);
		List<TagCloud> education = tagCloudRepository.findByTipTagCloud(TagCloudEnum.Education);
		List<TagCloud> language = tagCloudRepository.findByTipTagCloud(TagCloudEnum.ForeignLanguage);
		Set<ProjectInfo> pinfos = e.getProjectInfos();
		List<TagCloud> databases = new ArrayList<>();
		List<TagCloud> ides = new ArrayList<>();
		List<TagCloud> technologies = new ArrayList<>();
		Map<Project, List<TagCloud>> projects = new HashMap<>();
		for(ProjectInfo pi : pinfos) {
			List<TagCloud> tempTech = new ArrayList<>(); 
			for(TagCloud tc : pi.getProject().getTagClouds()) {
				if(tc.getTipTagCloud().equals(TagCloudEnum.Technologie)) {
					tempTech.add(tc);
					technologies.add(tc);
				}
				else if(tc.getTipTagCloud().equals(TagCloudEnum.Database)) {
					databases.add(tc);
				}
				else if(tc.getTipTagCloud().equals(TagCloudEnum.IDE)) {
					ides.add(tc);
				}
			}
			projects.put(pi.getProject(),tempTech);
			
		}
		try {
			CVGenerator.generate(e,education,language,projects, technologies, databases, ides);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (DocumentException e2) {
			e2.printStackTrace();
		}
		
		String fileName = "CV.rtf";	
		Path path = Paths.get(fileName);
    	byte[] data = null;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.parseMediaType("application/rtf"));
    	headers.add("content-disposition", "inline; filename=" + fileName);
    	ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
        return response;
	}
}
