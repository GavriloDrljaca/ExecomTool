package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.model.Employee;
import app.model.Project;
import app.model.ProjectInfo;
@Repository
public interface ProjectInfoRepository extends JpaRepository<ProjectInfo, Integer> {
	
	ProjectInfo findByProjectAndEmployee(Project project, Employee employee);
	List<ProjectInfo> findByProject(Project project);
}
