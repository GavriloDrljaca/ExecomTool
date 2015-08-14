package security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import app.model.Employee;

public class EmployeeUser extends User {

	
	private Employee employee;
	
	public EmployeeUser(String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		
		super(username, password, authorities);	
	}

}
