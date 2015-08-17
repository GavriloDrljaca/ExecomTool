package app.controllers;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import security.UserDetailsServiceImpl;
import app.controllers.http.response.UserNotFoundException;
import app.model.Employee;
import app.repository.EmployeeRepository;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

@RestController
@Configuration
@ComponentScan("security")
@RequestMapping("/signin")
public class GoogleLoginVerifierController {
	
	
	NetHttpTransport transport = new NetHttpTransport();
	
	private GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport
	, new JacksonFactory())
    .setAudience(Arrays.asList("282087479252-h14kfr8t4von3g7e4j1i2nop1tu6ai4l.apps.googleusercontent.com"))
    .build();

	@Autowired
	private EmployeeRepository employeeRepo;
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String logIn(@RequestParam("idtoken") String id_Token, HttpServletRequest request,
			HttpServletResponse response){
		
		System.out.println("Token granted from frontend: " + id_Token);
		
		GoogleIdToken idToken;
		Payload payload = null;
		UserDetails uDetails;
		  
		try {
			idToken = verifier.verify(id_Token);
			
			if (idToken != null) {
				  payload = idToken.getPayload();
				  
				  System.out.println("PAYLOAD: " +payload);
				  System.out.println("DOMAIN: "+ payload.getHostedDomain());
				  System.out.println("AUTH PARTY: "+payload.getAuthorizedParty());
				  
				  
				  if (
				      // If multiple clients access the backend server:
				    Arrays.asList("282087479252-h14kfr8t4von3g7e4j1i2nop1tu6ai4l.apps.googleusercontent.com").contains(payload.getAuthorizedParty())) {
					  
					  
					    System.out.println("GLVC User ID: " + payload.getSubject());
					    System.out.println("GLVC User email: "+payload.getEmail());
					    
					    
					    Employee emp = null;
				    if((emp = employeeRepo.findByEmail(payload.getEmail())) != null){
				    	System.out.println(emp);
				    	uDetails = userDetailsService.loadUserByUsername(payload.getEmail());
				    	
				    	request.getSession();

				    	Authentication auth = new UsernamePasswordAuthenticationToken(uDetails = userDetailsService.loadUserByUsername(payload.getEmail()),
				    			null, uDetails.getAuthorities());				    	

				    	SecurityContextHolder.getContext().setAuthentication(auth);
				    	
				    	System.out.println("GLVC SCH username: "+((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
				    	System.out.println("GLVC SCH authorities: "+((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAuthorities());
				    	
				    	System.out.println("GLVC AUTHORITIES: "+auth.getAuthorities());
				    	
				    	System.out.println("GLVC principal: "+SecurityContextHolder.getContext().getAuthentication().getPrincipal());
				    	
				    	return "redirect:/startPage.html";
				    	
				    }else{
				    	throw new UserNotFoundException(payload.getEmail());
				    }
				    
				   
				  } else {
				    System.out.println("Invalid ID token.");
				  }
				} else {
				  System.out.println("Invalid ID token.");
				}

		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  	
	
		return "";
	}
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logOut(){
		SecurityContextHolder.getContext().setAuthentication(null);
		
		return "redirect:/index.html";
	}
	

	
}
