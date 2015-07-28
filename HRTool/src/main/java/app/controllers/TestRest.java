package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.repository.TagCloudRepository;

@RestController
public class TestRest {
	
	@Autowired
	private TagCloudRepository tagRep;
	@RequestMapping("/greeting")
	public String greet() {
		return tagRep.findOne(1).getNameTagCloud();
	}
	
}
