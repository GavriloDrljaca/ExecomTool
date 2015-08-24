package security;


import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import app.model.Employee;
import app.repository.EmployeeRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepo;

    
	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Employee user = employeeRepo.findByEmail(email);
        
        
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        
        
        System.out.println("UDSImpl: USER FOUND");

        
        
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