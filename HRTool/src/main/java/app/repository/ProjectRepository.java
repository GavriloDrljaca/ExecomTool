package app.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.model.Project;
@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer> {

	Optional<Project> findByNameProject(String projectName);
}
