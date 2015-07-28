package app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.model.Employee;
import app.model.Project;
import app.model.ProjectInfo;
@Repository
public interface ProjectInfoRepository extends CrudRepository<ProjectInfo, Integer> {
	
	ProjectInfo findByProjectAndEmployee(Project project, Employee employee);

}
