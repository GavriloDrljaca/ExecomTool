package security;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
public class PreUserDetailService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
	private static final Logger log = Logger.getLogger(UserDetailsServiceImpl.class);
	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token)
			throws UsernameNotFoundException {
		
		log.info("!!!!!!!!!!!!!!!!!!!!!!!!PreUserDetailService : loadUserDetails");
		//System.out.println("PreUserDetailService : loadUserDetails");
		
		return null;
	}

}
