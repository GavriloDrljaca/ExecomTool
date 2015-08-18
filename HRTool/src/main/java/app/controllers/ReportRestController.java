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
	
	/**
	 * Returns only the tag clouds that contain the given employee
	 * @param extracted
	 * @param toExtract
	 * @param employee
	 * @return
	 */
	private Set<TagCloud> extractClouds(Set<TagCloud> extracted, List<TagCloud> toExtract, Employee employee){
		for (TagCloud tc : toExtract) {
			if (tc.getEmployees().contains(employee)) {
				extracted.add(tc);
			}
		}
		return extracted;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/cv")
	/**
	 * Sends generates cv file to response.
	 * @param id employee's id
	 * @return File as an array of bytes.
	 * @throws IOException
	 */
	public ResponseEntity generateRtf(@RequestParam("id") int id) throws IOException {
		Employee e = employeeRepository.findOne(id);
		List<TagCloud> allEducation = tagCloudRepository.findByTipTagCloud(TagCloudEnum.Education);
		Set<TagCloud> education = new HashSet<>();
		education = extractClouds(education, allEducation, e);
		
		List<TagCloud> allLanguage = tagCloudRepository.findByTipTagCloud(TagCloudEnum.ForeignLanguage);
		Set<TagCloud> language = new HashSet<>();
		language = extractClouds(language, allLanguage, e);
		
		Set<ProjectInfo> projectInfoes = e.getProjectInfos();
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
			file = CVGenerator.generate(servletContext,e,education,language,projects, technologies, databases, ides);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (DocumentException e2) {
			e2.printStackTrace();
		}
		String employeeName = e.getNameEmployee().trim().replace(" ", "_");
		String fileName = employeeName + "_CV.rtf";	
		Path path = Paths.get(servletContext.getRealPath("/temp/")+ fileName);
    	byte[] data = null;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e1) {
			e1.printStackTrace();
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
	/**
	 * Sends seniority pie chart as an array of bytes.
	 * @return Seniority pie chart
	 */
	public ResponseEntity generateChart() {
		File file = null;
		try {
			file = ChartGenerator.generatePieChart(employeeRepository.findAll());
		} catch (FileNotFoundException | com.itextpdf.text.DocumentException | ParseException e) {
			e.printStackTrace();
		}
		String fileName = "seniority-chart_" + ChartGenerator.sdf.format(ChartGenerator.currentDate) + ".pdf";
		Path path = Paths.get("./" + fileName);
    	byte[] data = null;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e1) {
			e1.printStackTrace();
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
	@RequestMapping("/technology") 
	/**
	 * Sends technologies pie chart as an array of bytes.
	 * @return Technologies pie chart
	 */
	public ResponseEntity generatePieTech() {
		File file = null;
		try {
			file = ChartGenerator.generateTechnologyOrDatabase(projectInfoRepository.findAll(),TagCloudEnum.Technologie);
		} catch (FileNotFoundException | com.itextpdf.text.DocumentException | ParseException e) {
			e.printStackTrace();
		}
		String fileName = "technology-chart_" + ChartGenerator.sdf.format(ChartGenerator.currentDate) + ".pdf";
		Path path = Paths.get("./" + fileName);
    	byte[] data = null;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e1) {
			e1.printStackTrace();
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
	/**
	 * Sends databases pie chart as an array of bytes.
	 * @return Databases pie chart
	 */
	public ResponseEntity generatePieDB() {
		File file = null;
		try {
			file = ChartGenerator.generateTechnologyOrDatabase(projectInfoRepository.findAll(),TagCloudEnum.Database);
		} catch (FileNotFoundException | com.itextpdf.text.DocumentException | ParseException e) {
			e.printStackTrace();
		}
		String fileName = "database-chart_" + ChartGenerator.sdf.format(ChartGenerator.currentDate) + ".pdf";
		Path path = Paths.get("./" + fileName);
    	byte[] data = null;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e1) {
			e1.printStackTrace();
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
