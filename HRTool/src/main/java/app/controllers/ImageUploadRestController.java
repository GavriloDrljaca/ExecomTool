package app.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

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
	public @ResponseBody String uploadingPOST(@RequestParam("flowFilename") String fileName,
			@RequestParam("slika") MultipartFile file){
		if (!file.isEmpty()) {
            try {
            	
                byte[] bytes = ((MultipartFile) file).getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
                        		new File(servletContext.getRealPath("/images/")+fileName)));
                stream.write(bytes);
                stream.close();
                System.out.println("---------------FILE UPLOADED------------" + servletContext.getRealPath("/images"));
                return "images/"+fileName;
            } catch (Exception e) {
                return "You failed to upload " + e.getMessage();
            }
        } else {
            return "You failed to upload  because the file was empty.";
        }
	}
	

	public String returnPath(String path){
		System.out.println(servletContext.getRealPath("images"));
		return servletContext.getRealPath("images");
	}
	
}
