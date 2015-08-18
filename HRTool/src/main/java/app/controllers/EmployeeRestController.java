
package app.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.generator.CVGenerator;
import app.model.Employee;
import app.model.Project;
import app.model.ProjectInfo;
import app.model.TagCloud;
import app.model.TagCloudEnum;
import app.repository.EmployeeRepository;
import app.repository.TagCloudRepository;

import com.lowagie.text.DocumentException;


@RestController
@RequestMapping("/resources/employees")
@RepositoryEventHandler(Employee.class)
public class EmployeeRestController {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
    private ServletContext servletContext;
	
	@Autowired
	TagCloudRepository tagCloudRepository;
	

	/**
	 * 
	 * @return all {Employee}s from repository
	 */
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(){
		
		return new ResponseEntity(employeeRepository.findAll(), HttpStatus.OK);
	}
	
	
	/**
	 * 
	 * @param emp - {Employee} for save
	 * @return {ResponseEntity} with {HttpStatus.OK}
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity put(@RequestBody Employee emp){
		
		employeeRepository.save(emp);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param id - {Employee} id for deletion
	 * @return {HttpStatus.NOT_FOUND} or {HttpStatus.OK}
	 */
	@RequestMapping(value = "/{id}" , method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable("id") int id){
		
		if(employeeRepository.findOne(id) == null) 
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		
		employeeRepository.delete(id);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	/**
	 * 
	 * 
	 * @param id - {Employee} id
	 * @return {Employee} from repository wrapped in {ResponseEntity}
	 */
	
	@RequestMapping(value = "/{id}")
	public ResponseEntity getEmployee(@PathVariable("id") int id){
		
		return new ResponseEntity(employeeRepository.findOne(id), HttpStatus.FOUND);

	}

	/**
	 * 
	 * 
	 * @param id - {Employee} id
	 * @return {Set} of {ProjectInfo}s wrapped in {ResponseEntity}
	 */
	
	@RequestMapping(value = "/{id}/projectinfoes", method = RequestMethod.GET)
	public ResponseEntity getEmployeeProjects(@PathVariable("id") int id){
		
		return new ResponseEntity(employeeRepository.findOne(id).getProjectInfos(), HttpStatus.OK);
	}
	
	
	
	/**
	 * 
	 * @param id - {Employee}'s id
	 * @return {Set} of {EmploymentInfo} wrapped in {ResponseEntity}
	 */
	@RequestMapping(value="/{id}/employmentinfoes", method = RequestMethod.GET)
	public ResponseEntity getEmploymentInfoes(@PathVariable("id") int id){
		
		return new ResponseEntity(employeeRepository.findOne(id).getEmpInfos(), HttpStatus.OK);
	}
	
	
	
	/**
	 * Sends generates cv file to response.
	 * @param id employee's id
	 * @return File as an array of bytes.
	 * @throws IOException
	 */
	@RequestMapping("/{id}/cv")
	public ResponseEntity generateRtf(@PathVariable("id") int id) throws IOException {
		Employee e = employeeRepository.findOne(id);
		List<TagCloud> allEducation = tagCloudRepository.findByTipTagCloud(TagCloudEnum.Education);
		Set<TagCloud> education = new HashSet<>();
		for (TagCloud tc : allEducation) {
			if (tc.getEmployees().contains(e)) {
				education.add(tc);
			}
		}
		List<TagCloud> allLanguage = tagCloudRepository.findByTipTagCloud(TagCloudEnum.ForeignLanguage);
		Set<TagCloud> language = new HashSet<>();
		for (TagCloud tc : allLanguage) {
			if (tc.getEmployees().contains(e)) {
				language.add(tc);
			}
		}
		Set<ProjectInfo> pinfos = e.getProjectInfos();
		Set<TagCloud> databases = new HashSet<>();
		Set<TagCloud> ides = new HashSet<>();
		Set<TagCloud> technologies = new HashSet<>();
		Map<Project, List<TagCloud>> projects = new HashMap<>();
		for(ProjectInfo pi : pinfos) {
			List<TagCloud> tempTech = new ArrayList<>(); 
			for(TagCloud tc : pi.getTagClouds()) {
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
		File file = null;
		try {
			file = CVGenerator.generate(servletContext,e,education,language,projects, technologies, databases, ides);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (DocumentException e2) {
			e2.printStackTrace();
		}
		String employeeName = e.getNameEmployee().replace(" ", "_");
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
    	ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
        return response;
	}
	
}

