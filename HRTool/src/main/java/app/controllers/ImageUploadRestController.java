package app.controllers;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;



@Controller
public class ImageUploadRestController {

	@Autowired
    private ServletContext servletContext;
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String uploadingPOST(@RequestParam("picture") MultipartFile file){
		if (!file.isEmpty()) {
            try {
                file.transferTo(new File(servletContext.getRealPath("/images") + file.getOriginalFilename()));
                return "images/" + file.getOriginalFilename();
            } catch (Exception e) {
                return "You failed to upload " + e.getMessage();
            }
        } else {
            return "You failed to upload  because the file was empty.";
        }
	}
	
}
