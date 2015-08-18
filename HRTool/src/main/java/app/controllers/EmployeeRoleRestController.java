package app.controllers;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/employeeRole")
public class EmployeeRoleRestController {
	private static final Logger log = Logger.getLogger(EmployeeRoleRestController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getRole(){

		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null){
			UserDetails ud = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ArrayList<SimpleGrantedAuthority> sga = (ArrayList<SimpleGrantedAuthority>) ud.getAuthorities();
			
			log.info("SGA : "+sga.get(0).getAuthority());
			return new ResponseEntity<String>("{\"role\": \""+sga.get(0).getAuthority()+"\"}", HttpStatus.OK);
		}
		return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
	}

}
