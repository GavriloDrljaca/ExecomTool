package app.exception.handlers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GlobalControllerExceptionHandler {

	    @ResponseStatus(HttpStatus.CONFLICT)  // 409
	    @ExceptionHandler(DataIntegrityViolationException.class)
	    public void handleConflict() {
	       System.out.println("Exception handled");
	    }

	    

}
