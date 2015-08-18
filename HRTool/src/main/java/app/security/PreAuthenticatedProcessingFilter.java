package app.security;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import app.controllers.http.response.UserNotFoundException;
import app.repository.EmployeeRepository;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;


public class PreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter{
	private static final Logger log = Logger.getLogger(PreAuthenticatedProcessingFilter.class);
	NetHttpTransport transport = new NetHttpTransport();
	
	private GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport
	, new JacksonFactory())
    .setAudience(Arrays.asList("282087479252-b8d869knnsqb3k7dtuhe5dlasl9e9orf.apps.googleusercontent.com"))
    .build();
	
	@Autowired 
	EmployeeRepository employeeRepo;
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		String token = request.getParameter("idtoken");
		log.warn("PREAUTH [idtoken]: "+token);
		GoogleIdToken idToken;
		Payload payload = null;
		
		if(token!=null)
		{
		try {
			idToken = verifier.verify(token);
			if (idToken != null) {	//1
				payload = idToken.getPayload();
				if (Arrays.asList("282087479252-b8d869knnsqb3k7dtuhe5dlasl9e9orf.apps.googleusercontent.com")
						.contains(payload.getAuthorizedParty())){ //2
					
					log.info("FILTER User ID: " + payload.getSubject());
					log.info("FILTER User ID: " + payload.getSubject());

				    if(employeeRepo.findByEmail(payload.getEmail()) != null){ //3
				    	log.info(employeeRepo.findByEmail(payload.getEmail()));
				    	Authentication auth = new UsernamePasswordAuthenticationToken(userDetailsService.loadUserByUsername(payload.getEmail()),
				    			null, userDetailsService.loadUserByUsername(payload.getEmail()).getAuthorities());
				    	
				    	return userDetailsService.loadUserByUsername(payload.getEmail());
				    }//3
				    else throw new UserNotFoundException(payload.getEmail());
				}//2
			}//1
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return true;
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return null;
	}


}
