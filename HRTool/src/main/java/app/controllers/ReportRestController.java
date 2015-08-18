package app.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
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
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
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
	
	
	@RequestMapping("/seniority")
	/**
	 * Sends seniority pie chart as an array of bytes.
	 * @return Seniority pie chart
	 */
	public ResponseEntity<byte[]> generateChart() {
		try {
			ChartGenerator.generatePieChart(employeeRepository.findAll());
		} catch (FileNotFoundException | com.itextpdf.text.DocumentException | ParseException e) {
			e.printStackTrace();
		}
		String fileName = "seniority-chart.pdf";
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
    	ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
        return response;
	}
	@RequestMapping("/technology") 
	/**
	 * Sends technologies pie chart as an array of bytes.
	 * @return Technologies pie chart
	 */
	public ResponseEntity<byte[]> generatePieTech() {
		try {
			ChartGenerator.generateTechnology(projectInfoRepository.findAll(),TagCloudEnum.Technologie);
		} catch (FileNotFoundException | com.itextpdf.text.DocumentException | ParseException e) {
			e.printStackTrace();
		}
		String fileName = "technology-chart.pdf";
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
    	ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
        return response;
	}
	
	@RequestMapping("/database") 
	/**
	 * Sends databases pie chart as an array of bytes.
	 * @return Databases pie chart
	 */
	public ResponseEntity<byte[]> generatePieDB() {
		try {
			ChartGenerator.generateTechnology(projectInfoRepository.findAll(),TagCloudEnum.Database);
		} catch (FileNotFoundException | com.itextpdf.text.DocumentException | ParseException e) {
			e.printStackTrace();
		}
		String fileName = "database-chart.pdf";
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
    	ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
        return response;
	}
}
