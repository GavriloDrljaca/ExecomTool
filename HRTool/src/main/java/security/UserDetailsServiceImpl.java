package security;


import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.model.Employee;
import app.repository.EmployeeRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private static final Logger log = Logger.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    private EmployeeRepository employeeRepository;
	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Employee user = employeeRepository.findByEmail(email);
        
        
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        
        
        log.info("UDSImpl: USER FOUND");
        //System.out.println("UDSImpl: USER FOUND");

        
        
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                authorities.add(new SimpleGrantedAuthority(user.getEmployeeRole().name()));
                return authorities;
            }

            @Override
            public String getUsername() {
                return user.getEmail();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }
            
            
            @Override
            public boolean isEnabled() {
                //return user.isEnabled();
            	return true;
            }

			@Override
			public String getPassword() {
				return null;
			}
        };

    }


}