package app.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

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
	private Long uniqueDate = new Date().getTime();
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String uploadingPOST(@RequestParam("flowFilename") String fileName,
			@RequestParam("flowTotalChunks") int totalChunks, @RequestParam("flowChunkSize") int chunkSize,
			@RequestParam("flowChunkNumber") int chunkNumber,
			@RequestParam("slika") MultipartFile file){
		System.out.println("----------------POST-------------");
		if (!file.isEmpty()) {
			System.out.println("nije prazan  "+ fileName+ "  totalchunks: "+totalChunks+"  chunkSize: "+ chunkSize + "  chunkNumber: "+ chunkNumber);
            try {
            	
                byte[] bytes = ((MultipartFile) file).getBytes();
               
                FileOutputStream output = new FileOutputStream(
                		new File(servletContext.getRealPath("/images/")+uniqueDate+"_"+fileName), true);
                output.write(bytes);
                output.close();
                System.out.println("---------------FILE UPLOADED------------" + servletContext.getRealPath("/images"));
                return "images/"+uniqueDate+"_"+fileName;
            } catch (Exception e) {
            	e.printStackTrace();
                return "You failed to upload " + e.getMessage();
            }
        } else {
            return "You failed to upload  because the file was empty.";
        }
	}

}
