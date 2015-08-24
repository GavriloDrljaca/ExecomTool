package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.EmploymentInfo;
import app.repository.EmploymentInfoRepository;

@RestController
@RepositoryEventHandler(EmploymentInfo.class)
@RequestMapping("/resources/employmentinfoes")
public class EmploymentInfoRestController {
	
	@Autowired
	EmploymentInfoRepository employmentInfoRepo;
	
	/**
	 * 
	 * @param id - {EmploymentInfo} id
	 * @return {Employee} for given {EmploymentInfo} id wrapped in {ResponseEntity}
	 */
	@RequestMapping(value = "/{id}/employee", method = RequestMethod.GET)
	public ResponseEntity getEmployee(@PathVariable("id") int id){
		
		return new ResponseEntity(employmentInfoRepo.findOne(id).getEmployee(), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param id - {EmploymentInfo} id
	 * @return {Set} of {TagCloud}s for given {EmploymentInfo} id
	 */
	@RequestMapping(value = "/{id}/tagclouds", method = RequestMethod.GET)
	public ResponseEntity getTagClouds(@PathVariable("id") int id){
		
		return new ResponseEntity(employmentInfoRepo.findOne(id).getTagClouds(), HttpStatus.OK);
	}
}
