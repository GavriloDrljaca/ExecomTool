package app.controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
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

	private static final Logger log = Logger
			.getLogger(GoogleLoginVerifierController.class);

	NetHttpTransport transport = new NetHttpTransport();

	private GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
			transport, new JacksonFactory())
			.setAudience(
					Arrays.asList("282087479252-4v31a07nrjnmfganchk4i1btpvoprjro.apps.googleusercontent.com"))
			.build();

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public HttpStatus logIn(@RequestParam("idtoken") String id_Token,
			HttpServletRequest request, HttpServletResponse response) {

		log.info("Token granted from frontend: " + id_Token);

		GoogleIdToken idToken;
		Payload payload = null;
		UserDetails uDetails;

		try {
			idToken = verifier.verify(id_Token);

			if (idToken != null) {
				payload = idToken.getPayload();

				log.info("PAYLOAD: " + payload);
				log.info("DOMAIN: " + payload.getHostedDomain());
				log.info("AUTH PARTY: " + payload.getAuthorizedParty());

				if (
				// If multiple clients access the backend server:
				Arrays.asList(
						"282087479252-4v31a07nrjnmfganchk4i1btpvoprjro.apps.googleusercontent.com")
						.contains(payload.getAuthorizedParty())) {

					log.info("GLVC User ID: " + payload.getSubject());
					log.info("GLVC User email: " + payload.getEmail());

					Employee emp = null;
					if ((emp = employeeRepository.findByEmail(payload.getEmail())) != null) {
						log.info(emp);
						uDetails = userDetailsService
								.loadUserByUsername(payload.getEmail());

						request.getSession();

						Authentication auth = new UsernamePasswordAuthenticationToken(
								uDetails = userDetailsService.loadUserByUsername(payload
										.getEmail()), null,
								uDetails.getAuthorities());

						SecurityContextHolder.getContext().setAuthentication(
								auth);

						log.info("GLVC SCH username: "
								+ ((UserDetails) SecurityContextHolder
										.getContext().getAuthentication()
										.getPrincipal()).getUsername());
						log.info("GLVC SCH authorities: "
								+ ((UserDetails) SecurityContextHolder
										.getContext().getAuthentication()
										.getPrincipal()).getAuthorities());

						log.info("GLVC AUTHORITIES: " + auth.getAuthorities());

						log.info("GLVC principal: "
								+ SecurityContextHolder.getContext()
										.getAuthentication().getPrincipal());
						
						return HttpStatus.OK;

					} else {
						throw new UserNotFoundException(payload.getEmail());
					}

				} else {
					log.info("Invalid ID token.");
				}
			} else {
				log.info("Invalid ID token.");
			}

		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return HttpStatus.BAD_REQUEST;
	}


}
