package security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class PreAuthProvider extends PreAuthenticatedAuthenticationProvider {

	@Override
	public void afterPropertiesSet() {
		System.out.println("1");
		super.afterPropertiesSet();
		
	}

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		
		System.out.println("2");
		return super.authenticate(authentication);
		
	}

	@Override
	public void setPreAuthenticatedUserDetailsService(
			AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> uds) {
		// TODO Auto-generated method stub
		System.out.println("3");
		super.setPreAuthenticatedUserDetailsService(uds);
	}

	@Override
	public void setThrowExceptionWhenTokenRejected(
			boolean throwExceptionWhenTokenRejected) {
		// TODO Auto-generated method stub
		System.out.println("4");
		super.setThrowExceptionWhenTokenRejected(throwExceptionWhenTokenRejected);
	}

	@Override
	public void setUserDetailsChecker(UserDetailsChecker userDetailsChecker) {
		// TODO Auto-generated method stub
		System.out.println("5");
		super.setUserDetailsChecker(userDetailsChecker);
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		System.out.println("6");
		return super.getOrder();
	}

	@Override
	public void setOrder(int i) {
		// TODO Auto-generated method stub
		System.out.println("7");
		super.setOrder(i);
	}
	
	

}
