package app.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.model.Employee;
import app.model.Project;
import app.model.ProjectInfo;
@Repository
public interface ProjectInfoRepository extends JpaRepository<ProjectInfo, Integer> {
	
	ProjectInfo findByProjectAndEmployee(@Param("project") Project project, @Param("employee") Employee employee);
	List<ProjectInfo> findByProject(@Param("project") Project project);
	Set<ProjectInfo> findByEmployee(@Param("employee") Employee employee);
}
