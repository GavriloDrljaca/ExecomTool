package app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled = true)
public class Security extends WebSecurityConfigurerAdapter{
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable()
		.formLogin().disable()
		.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/index.html").permitAll()
			.antMatchers("/signin/login").permitAll()
			.antMatchers("/signin/logout").permitAll()
			.antMatchers("/logout").permitAll()
			.antMatchers("/startPage.html").authenticated()
			.antMatchers("/app/**").permitAll()
			.antMatchers("/projects/**").hasAuthority("HRM")
			.antMatchers("/employees/**").hasAuthority("HRM")
			.antMatchers("/tagClouds/**").hasAuthority("HRM")
			.antMatchers("/projectInfoes/**").hasAuthority("HRM")
			.antMatchers("/employmentInfoes/**").hasAuthority("HRM")
			.antMatchers("/restrictedEmployees/employee").hasAnyAuthority("EMP")
			.antMatchers("/restrictedEmployees/officeManager", "/restrictedEmployees/update").hasAuthority("OFF")
			.antMatchers("/report/**").hasAuthority("HRM")
			.anyRequest().authenticated()
		.and()
        .csrf()
            .disable();
	}
    
    @Bean
    public PreUserDetailService preUserDetailService(){
    	PreUserDetailService ser = new PreUserDetailService();
    	return ser;
    }
    
    @Bean
    public PreAuthenticatedProcessingFilter preFilter() throws Exception {
    	PreAuthenticatedProcessingFilter filter = new PreAuthenticatedProcessingFilter();
    	filter.setAuthenticationManager(authenticationManager());
    	return filter;
    }
}
