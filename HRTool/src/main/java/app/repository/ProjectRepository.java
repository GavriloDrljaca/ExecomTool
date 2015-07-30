package app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.model.Project;
@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

	Project findByNameProject(String projectName);
}
