package app.controllers;


import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Employee;
import app.model.Project;
import app.model.ProjectInfo;
import app.repository.ProjectInfoRepository;
import app.repository.ProjectRepository;


@RestController
@RequestMapping("/resources/projects")
@RepositoryEventHandler(Project.class)
public class ProjectRestController {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ProjectInfoRepository projectInfoRepository;
	
	
	/**
	 * 
	 * @param {Project} id
	 * @return {ResponseEntity} of {Project} infoes for given {Project} id
	 *
	 */
	@RequestMapping(value="/{id}/projectInfoes", method = RequestMethod.GET)
	public ResponseEntity getProjectInfoes(@PathVariable("id") int id){
		
		return new ResponseEntity(projectRepository.findOne(id).getProjectInfo(), HttpStatus.OK);
	}
	
	
	/**
	 * 
	 * @param emp - {Project} for save
	 * @return {ResponseEntity} with {HttpStatus.OK}
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity put(@RequestBody Project proj){
		
		projectRepository.save(proj);
		return new ResponseEntity(HttpStatus.OK);
	}

	
	/**
	 * Deletes project with given id
	 * @param id - {Project} id for deletion
	 * @return {HttpStatus.NOT_FOUND} or {HttpStatus.OK}
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity deleteProject(@PathVariable("id") int id){
		
		if(projectRepository.findOne(id) == null) 
			return new ResponseEntity(HttpStatus.NOT_FOUND);

		List<ProjectInfo> projectInfoes = projectInfoRepository.findAll();
		for (ProjectInfo pi : projectInfoes){
			if (pi.getProject().getIdProject() == id){
				pi.setTagClouds(null);
				projectInfoRepository.delete(pi.getIdProjectInfo());
			}
		}
		
		projectRepository.delete(id);
		return new ResponseEntity(HttpStatus.OK);
	}

	

	
}
