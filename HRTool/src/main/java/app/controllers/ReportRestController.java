package app.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
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
@RequestMapping("/test")
public class ReportRestController {
	
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
	
	@RequestMapping("/seniority")
	public ResponseEntity<byte[]> generateChart() {
		try {
			ChartGenerator.generatePieChart(employeeRepository.findAll());
		} catch (FileNotFoundException | com.itextpdf.text.DocumentException | ParseException e) {
			e.printStackTrace();
		}
		String fileName = "chart.pdf";
		Path path = Paths.get(fileName);
    	byte[] data = null;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.parseMediaType("application/pdf"));
    	headers.add("content-disposition", "inline; filename=" + fileName);
    	ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
        return response;
	}
	@RequestMapping("/technology") 
	public ResponseEntity<byte[]> generatePieTech() {
		try {
			ChartGenerator.generateTechnology(projectInfoRepository.findAll(),TagCloudEnum.Technologie);
		} catch (FileNotFoundException | com.itextpdf.text.DocumentException | ParseException e) {
			e.printStackTrace();
		}
		String fileName = "chart.pdf";
		Path path = Paths.get(fileName);
    	byte[] data = null;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.parseMediaType("application/pdf"));
    	headers.add("content-disposition", "inline; filename=" + fileName);
    	ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
        return response;
	}
	
	@RequestMapping("/database") 
	public ResponseEntity<byte[]> generatePieDB() {
		try {
			ChartGenerator.generateTechnology(projectInfoRepository.findAll(),TagCloudEnum.Database);
		} catch (FileNotFoundException | com.itextpdf.text.DocumentException | ParseException e) {
			e.printStackTrace();
		}
		String fileName = "chart.pdf";
		Path path = Paths.get(fileName);
    	byte[] data = null;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.parseMediaType("application/pdf"));
    	headers.add("content-disposition", "inline; filename=" + fileName);
    	ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
        return response;
	}
}
