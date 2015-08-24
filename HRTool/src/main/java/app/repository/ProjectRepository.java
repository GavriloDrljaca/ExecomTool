package app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.model.Project;
@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

	Project findByNameProject(@Param("projectName") String projectName); 
}
