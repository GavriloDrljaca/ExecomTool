package security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;



public class ResponseFilter implements Filter {

	
	private static final Logger logger = Logger.getLogger(ResponseFilter.class);
	
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);

	    chain.doFilter(request, responseWrapper);
	    
	    
	    String responseContent = new String (responseWrapper.getDataStream());
	    System.out.println(response);
	    
	    
//	    System.out.println(responseContent);
//
//	    RestResponse fullResponse = new RestResponse(/*status*/, /*message*/,responseContent);
//
//	    byte[] responseToSend = restResponseBytes(fullResponse);

	    response.getOutputStream().write(responseContent.getBytes());
	}

	@Override
	public void destroy() {
		

	}
	
	
	
	
	/*private byte[] restResponseBytes(RestResponse response) throws IOException {
	    String serialized = new ObjectMapper().writeValueAsString(response);
	    return serialized.getBytes();
	}*/

}
