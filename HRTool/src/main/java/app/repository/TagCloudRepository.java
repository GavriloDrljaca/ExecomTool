package app.repository;

import java.util.Optional;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.model.TagCloud;


@Repository
public interface TagCloudRepository extends CrudRepository<TagCloud, Integer> {
	
	Optional<TagCloud> findByNameTagCloud(String tagCloudName);

}
