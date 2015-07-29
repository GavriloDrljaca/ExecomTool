package app.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.TagCloud;
import app.repository.TagCloudRepository;


@RestController
public class TagCloudRestController {

	@Autowired
	TagCloudRepository tagCloudRepository;
	
	@RequestMapping(value = "/tiptagcloud", method = RequestMethod.GET)
	public List<TagCloud> getTagCloudByTip(String tipTagCloud) {
		Iterable<TagCloud> tagClouds = this.tagCloudRepository.findAll();
		List<TagCloud> listTagCloud = new ArrayList<TagCloud>();
		for(TagCloud tc : tagClouds) {
			if(tc.getTipTagCloud().equals(tipTagCloud))
				listTagCloud.add(tc);
		}
		return listTagCloud;
	}
	
	@RequestMapping(value = "/tagclouds", method = RequestMethod.GET)
	public Iterable<TagCloud> getTagClouds() {
		return this.tagCloudRepository.findAll();
	}
	
	@RequestMapping(value = "/savetagcloud", method = RequestMethod.GET)
	public TagCloud saveTagCloud(TagCloud tc) {
		return this.tagCloudRepository.save(tc);
	}
	
}
