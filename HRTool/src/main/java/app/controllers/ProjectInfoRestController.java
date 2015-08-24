package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.ProjectInfo;
import app.repository.ProjectInfoRepository;
import app.repository.ProjectRepository;

@RestController
@RepositoryEventHandler(ProjectInfo.class)
@RequestMapping("/resources/projectinfoes")
public class ProjectInfoRestController {

	@Autowired
	ProjectInfoRepository projectInfoRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	/**
	 * 
	 * @return {Set} of {ProjectInfo}es wrapped in {ResponseEntity}
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(){
		return new ResponseEntity(projectInfoRepository.findAll(), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param id - {ProjectInfo} id
	 * @return {Employee} wrapped in {ResponseEntity}
	 */
	@RequestMapping(value = "/{id}/employee", method = RequestMethod.GET)
	public ResponseEntity getEmployee(@PathVariable("id") int id){
		
		return new ResponseEntity(projectInfoRepository.findOne(id).getEmployee(), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param id - {ProjectInfo} id
	 * @return {Set} of {TagCloud}s wrapped in {ResponseEntity}
	 */
	@RequestMapping(value = "/{id}/tagclouds", method = RequestMethod.GET)
	public ResponseEntity getTagClouds(@PathVariable("id") int id){
		
		return new ResponseEntity(projectInfoRepository.findOne(id).getTagClouds(), HttpStatus.OK);
	}
} 
