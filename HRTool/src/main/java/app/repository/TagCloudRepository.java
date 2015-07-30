package app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.model.TagCloud;


@Repository
public interface TagCloudRepository extends CrudRepository<TagCloud, Integer> {
	
	TagCloud findByNameTagCloud(String tagCloudName);

}
