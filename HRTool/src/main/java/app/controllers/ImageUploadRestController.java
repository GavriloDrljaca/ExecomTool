package app.controllers;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageUploadRestController {

	@Autowired
	private ServletContext servletContext;

	private static String imagePath = "/temp/";

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<String> uploadingPOST(
			@RequestParam("picture") MultipartFile image) {
		if (!image.isEmpty()) {
			try {
				image.transferTo(new File(servletContext.getRealPath(imagePath)
						+ image.getOriginalFilename()));
				return new ResponseEntity<String>(imagePath
						+ image.getOriginalFilename(), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
	}
}
