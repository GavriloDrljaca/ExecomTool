package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.model.TagCloud;
import app.model.TagCloudEnum;
import java.util.List;

@Repository
public interface TagCloudRepository extends JpaRepository<TagCloud, Integer> {

	TagCloud findByNameTagCloud(String tagCloudName);

	List<TagCloud> findByTipTagCloud(TagCloudEnum tiptagcloud);
}
