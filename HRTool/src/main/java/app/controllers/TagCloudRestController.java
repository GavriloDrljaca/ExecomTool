package app.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.TagCloud;
import app.repository.TagCloudRepository;


@RestController
@RequestMapping("/tagclouds")
public class TagCloudRestController {

	@Autowired
	TagCloudRepository tagCloudRepository;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<TagCloud> getTagClouds() {
		return this.tagCloudRepository.findAll();
	}
	
	
	@RequestMapping(value = "/{tiptagcloud}", method = RequestMethod.GET)
	public Iterable<TagCloud> getTagCloudByTip(@PathVariable("tiptagcloud") String tipTagCloud) {
		return this.tagCloudRepository.findByTipTagCloud(tipTagCloud);
	}
	

	@RequestMapping(value = "/savetagcloud", method = RequestMethod.GET)
	public TagCloud saveTagCloud(TagCloud tc) {
		return this.tagCloudRepository.save(tc);
	}
	
}
