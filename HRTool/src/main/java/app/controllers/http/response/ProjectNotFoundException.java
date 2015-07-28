package app.controllers.http.response;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProjectNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProjectNotFoundException(String projectName)
	{
		super("could not find project '" + projectName + "'.");
	}
	
}
