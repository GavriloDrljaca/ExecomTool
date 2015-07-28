package app.repository;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.model.Employee;
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

	Optional<Employee> findByUsername(String username);
	
}
