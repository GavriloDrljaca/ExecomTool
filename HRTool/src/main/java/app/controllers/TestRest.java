package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.model.TagCloud;
import app.repository.ProjectRepository;
import app.repository.TagCloudRepository;
import app.testData.ImportData;

@RestController
public class TestRest {
	
	@Autowired
	private ProjectRepository projRep;
	
	@Autowired
	private TagCloudRepository tagRep;
	@RequestMapping("/greeting")
	public String greet() {
		return tagRep.findOne(1).getNameTagCloud();
	}

	@RequestMapping("/greeting2")
	public Iterable<TagCloud> greet2() {
		TagCloud tc = tagRep.findOne(1);
		tc.setNameTagCloud("test");
		tagRep.save(tc);
		return tagRep.findAll();
	}
}
