package app.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.model.Employee;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	Employee findByUsername(@Param("username") String username); 
	Employee findByEmail(@Param("email") String email);
}
