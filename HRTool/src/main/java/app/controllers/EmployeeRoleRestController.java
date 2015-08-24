package app.controllers;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/employeeRole")
public class EmployeeRoleRestController {
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity getRole(){
			UserDetails ud = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			ArrayList<SimpleGrantedAuthority> sga = (ArrayList<SimpleGrantedAuthority>) ud.getAuthorities();
			return new ResponseEntity(sga.get(0).getAuthority(), HttpStatus.OK);
	}

}
