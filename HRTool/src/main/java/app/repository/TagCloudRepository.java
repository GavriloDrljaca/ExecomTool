package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.model.TagCloud;
import app.model.TagCloudEnum;

import java.util.List;

@Repository
public interface TagCloudRepository extends JpaRepository<TagCloud, Integer> {

	TagCloud findByNameTagCloud(@Param("tagCloudName") String tagCloudName);

	List<TagCloud> findByTipTagCloud(@Param("tipTagCloud") TagCloudEnum tiptagcloud);
}
