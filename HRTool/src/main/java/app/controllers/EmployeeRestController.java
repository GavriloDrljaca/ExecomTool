
package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.model.Employee;
import app.model.TagCloud;
import app.repository.EmployeeRepository;
import app.repository.ProjectRepository;
import app.repository.TagCloudRepository;


@RestController
@RequestMapping
@RepositoryEventHandler(Employee.class)
public class EmployeeRestController {
		
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	
	@Autowired
	TagCloudRepository tagCloudsRepository;
	
	
	@RequestMapping(value = "/{id}/tagClouds/update", method = RequestMethod.POST)
	public void updateTagClouds(@RequestParam("tipTagCloud") String tipTagCloud,
			@RequestParam("nameTagCloud") String nameTagCloud, @PathVariable("id") String employeeId){
		
		Employee empl = employeeRepository.findOne(new Integer(employeeId));
		
		if(empl == null) throw new UsernameNotFoundException("Employee not found");
		
		TagCloud tc = tagCloudsRepository.findByNameTagCloud(nameTagCloud);
		if(tc == null) return;
		
		boolean okToAdd = true;
		
		for(TagCloud tci : empl.getTagClouds()){
			
			if(tci.getTipTagCloud().name().equals(tipTagCloud) && tci.getNameTagCloud().equals(nameTagCloud))
				okToAdd = false;
		}
		
		if(okToAdd)
		{
			TagCloud ntc = new TagCloud();
			ntc.setNameTagCloud(nameTagCloud);
			ntc.setTipTagCloud(tc.getTipTagCloud());
			empl.getTagClouds().add(ntc);
			employeeRepository.save(empl);
		}
		
		
	}
	 
}

