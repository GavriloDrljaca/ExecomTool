package app.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.TagCloud;
import app.repository.EmployeeRepository;
import app.repository.EmploymentInfoRepository;
import app.repository.ProjectRepository;
import app.repository.TagCloudRepository;

@RepositoryEventHandler(TagCloud.class)
@RestController
@RequestMapping("/resources/tagclouds")
public class TagCloudRestController {

	@Autowired
	TagCloudRepository tagCloudRepository;

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	EmploymentInfoRepository employmentInfoRepository;
	
	/**
	 * 
	 * @return	{Set} of {TagCloud}s wrapped in {ResponseEntity}
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity getTagClouds(){
		
		return new ResponseEntity(tagCloudRepository.findAll(), HttpStatus.OK);
	}
	
	
	/**
	 * 
	 * @param tc - {TagCloud}
	 * @return	{HttpStatus.OK}
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity put(@RequestBody TagCloud tc){
		tagCloudRepository.save(tc);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	
	/**
	 * 
	 * @param id - {TagCloud} id
	 * @return {ResponseEntity} with {HttpStatus.OK} or {HttpStatus.NOT_FOUND}
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable("id") int id){
		
		if(tagCloudRepository.findOne(id) == null)
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		
		tagCloudRepository.delete(id);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	
	
	/**
	 * 
	 * @param id - {TagCloud} id
	 * @return {Set} of {Employee}s wrapped in {ResponseEntity}
	 */
	@RequestMapping(value = "/{id}/employees",method = RequestMethod.GET)
	public ResponseEntity getEmployees(@PathVariable("id") int id){
		
		return new ResponseEntity(tagCloudRepository.findOne(id).getEmployees(), HttpStatus.OK);
	} 
	
	
	/**
	 * 
	 * @param id - {TagCloud} id
	 * @return {Set} of {ProjectInfo}es wrapped in {ResponseEntity}
	 */
	@RequestMapping(value = "/{id}/projectinfoes", method = RequestMethod.GET)
	public ResponseEntity getTagCluds(@PathVariable("id") int id){
		
		return new ResponseEntity(tagCloudRepository.findOne(id).getProjectInfos(), HttpStatus.OK);
	}
	
}
