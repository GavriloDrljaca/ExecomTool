package app.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;

import app.generator.CVGenerator;
import app.generator.ChartGenerator;
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
@RequestMapping("/report")
public class ReportRestController {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	TagCloudRepository tagCloudRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	ProjectInfoRepository projectInfoRepository;

	@Autowired
    private ServletContext servletContext;
	
	private Logger logger = Logger.getLogger(ReportRestController.class.getName());

	private void getTagClouds(Set<TagCloud> extracted, Employee employee, TagCloudEnum tagCloudType) {
		employee.getTagClouds().stream().filter(tc -> tc.getTipTagCloud().equals(tagCloudType)).forEach(tc -> extracted.add(tc));;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/cv")
	public ResponseEntity generateRtf(@RequestParam("id") int id) throws IOException {
		Employee employee = employeeRepository.findOne(id);
		Set<TagCloud> education = new HashSet<>();
		getTagClouds(education, employee, TagCloudEnum.Education);
		
		Set<TagCloud> language = new HashSet<>();
		getTagClouds(language, employee, TagCloudEnum.ForeignLanguage);

		Set<ProjectInfo> projectInfoes = employee.getProjectInfos();
		Set<TagCloud> databases = new HashSet<>();
		Set<TagCloud> ides = new HashSet<>();
		Set<TagCloud> technologies = new HashSet<>();
		Map<Project, List<TagCloud>> projects = new HashMap<>();
		for(ProjectInfo pi : projectInfoes) {
			List<TagCloud> tempTech = new ArrayList<>(); 
			for(TagCloud tc : pi.getTagClouds()) {
				switch(tc.getTipTagCloud()){
					case Technologie:
						tempTech.add(tc);
						technologies.add(tc);
						break;
					case Database:
						databases.add(tc); break;
					case IDE:
						ides.add(tc); break;
					default:
				}
			}
			projects.put(pi.getProject(),tempTech);
			
		}
		File file = null;
		try {
			file = CVGenerator.generate(servletContext,employee,education,language,projects, technologies, databases, ides);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (DocumentException e2) {
			e2.printStackTrace();
		}
		String employeeName = employee.getNameEmployee().trim().replace(" ", "_");
		String fileName = employeeName + "_CV.rtf";	
		Path path = Paths.get(servletContext.getRealPath("/temp/")+ fileName);
    	byte[] data = null;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e1) {
			logger.log(Level.SEVERE, e1.getMessage());
			return new ResponseEntity("",HttpStatus.INTERNAL_SERVER_ERROR);
		}
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.parseMediaType("application/rtf"));
    	headers.add("content-disposition", "inline; filename=" + fileName);
    	if(file != null) {
    		file.delete();
    	}
    	ResponseEntity response = new ResponseEntity(data, headers, HttpStatus.OK);
        return response;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/seniority")
	public ResponseEntity generateChart() {
		File file = null;
		file = ChartGenerator.generatePieChart(employeeRepository.findAll());
		
		String fileName = "seniority-chart_" + ChartGenerator.sdf.format(ChartGenerator.currentDate) + ".pdf";
		Path path = Paths.get("./" + fileName);
    	byte[] data = null;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e1) {
			logger.log(Level.SEVERE, e1.getMessage());
			return new ResponseEntity("",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.parseMediaType("application/pdf"));
    	headers.add("content-disposition", "inline; filename=" + fileName);
    	if(file != null) {
    		file.delete();
    	}
    	ResponseEntity response = new ResponseEntity(data, headers, HttpStatus.OK);
        return response;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/technology") 
	public ResponseEntity generatePieTech() {
		File file = null;
		file = ChartGenerator.generateTechnologyOrDatabase(projectInfoRepository.findAll(),TagCloudEnum.Technologie);
		
		String fileName = "technology-chart_" + ChartGenerator.sdf.format(ChartGenerator.currentDate) + ".pdf";
		Path path = Paths.get("./" + fileName);
    	byte[] data = null;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e1) {
			logger.log(Level.SEVERE, e1.getMessage());
			return new ResponseEntity("",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.parseMediaType("application/pdf"));
    	headers.add("content-disposition", "inline; filename=" + fileName);
    	if(file != null) {
    		file.delete();
    	}
    	ResponseEntity response = new ResponseEntity(data, headers, HttpStatus.OK);
        return response;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/database") 
	public ResponseEntity generatePieDB() {
		File file = null;
		file = ChartGenerator.generateTechnologyOrDatabase(projectInfoRepository.findAll(),TagCloudEnum.Database);
		String fileName = "database-chart_" + ChartGenerator.sdf.format(ChartGenerator.currentDate) + ".pdf";
		Path path = Paths.get("./" + fileName);
    	byte[] data = null;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e1) {
			logger.log(Level.SEVERE, e1.getMessage());
			return new ResponseEntity("",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.parseMediaType("application/pdf"));
    	headers.add("content-disposition", "inline; filename=" + fileName);
    	ResponseEntity response = new ResponseEntity(data, headers, HttpStatus.OK);
    	if(file != null) {
    		file.delete();
    	}
        return response;
	}
}
