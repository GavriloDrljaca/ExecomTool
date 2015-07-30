package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.model.TagCloud;


@Repository
public interface TagCloudRepository extends JpaRepository<TagCloud, Integer> {
	
	TagCloud findByNameTagCloud(String tagCloudName); 

}
