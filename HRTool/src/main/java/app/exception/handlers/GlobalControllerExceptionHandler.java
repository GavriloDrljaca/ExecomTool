package app.exception.handlers;

import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;


//@ControllerAdvice
public class GlobalControllerExceptionHandler {

	    @ResponseStatus(HttpStatus.CONFLICT)  // 409
	    @ExceptionHandler(DataIntegrityViolationException.class)
	    public void handleConflict() {
	       System.out.println("Exception handled");
	    }


        @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
        @ExceptionHandler(MissingServletRequestParameterException.class)
        public void handleConflictBadRequest() {
        	System.out.println("Exception handled: MissingServletRequestParameterException");
        }
        
        
        @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
        @ExceptionHandler(ServletRequestBindingException.class)
        public void handleConflictBadRequest2() {
        	System.out.println("Exception handled: ServletRequestBindingException");
        }
        
        @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
        @ExceptionHandler(TypeMismatchException.class)
        public void handleConflictBadRequest3() {
        	System.out.println("Exception handled: TypeMismatchException");
        }

        @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
        @ExceptionHandler(HttpMessageNotReadableException.class)
        public void handleConflictBadRequest4() {
        	System.out.println("Exception handled: HttpMessageNotReadableException");
        }
        
        @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public void handleConflictBadRequest5() {
        	System.out.println("Exception handled: MethodArgumentNotValidException");
        }
        
        @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
        @ExceptionHandler(MissingServletRequestPartException.class)
        public void handleConflictBadRequest6() {
        	System.out.println("Exception handled: MissingServletRequestPartException");
        }
        
}
