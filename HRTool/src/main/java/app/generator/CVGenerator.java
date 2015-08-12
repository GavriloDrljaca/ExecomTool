package app.generator;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

import app.model.Employee;
import app.model.Project;
import app.model.TagCloud;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfWriter2;

public class CVGenerator {

	public static Document document = new Document();

	
	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	public static File generate(Employee e, List<TagCloud> education,
			List<TagCloud> language, Map<Project, List<TagCloud>> projects,
			Set<TagCloud> listTechnologies, Set<TagCloud> listDatabases,
			Set<TagCloud> listIdes) throws DocumentException,
			MalformedURLException, IOException {
		Document doc = new Document();
		String employeeName = e.getNameEmployee().replace(" ", "_");
		String fileName ="./src/main/webapp/temp/" + employeeName + "_CV.rtf";	
		File cv =new File(fileName);
		OutputStream os = new FileOutputStream(cv);
		RtfWriter2.getInstance(doc, os);

		doc.open();

		Font font1 = new Font(Font.TIMES_ROMAN, 25, Font.BOLD,
				Color.decode("0x170B2C"));
		Font font2 = new Font(Font.TIMES_ROMAN, 17, Font.BOLD,
				Color.decode("0x170B2C"));
		Font font3 = new Font(Font.TIMES_ROMAN, 12, Font.BOLD, Color.DARK_GRAY);

		Image execomImage = null;
		Image image = Image.getInstance("src/main/webapp/images/NoImage.jpg");
		try {
			execomImage = Image.getInstance("src/main/webapp/images/execom-logo.jpg");
			if(e.getImage() != null) {
				System.out.println(e.getImage());
				image = Image.getInstance("src/main/webapp/" + e.getImage());
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		execomImage.scaleAbsolute(250, 40);

		image.scaleAbsolute(70, 70);
		Phrase basic = new Phrase(e.getNameEmployee(), font1);
		Paragraph basicTitle = new Paragraph(basic);
		Table nameAndImage = new Table(2, 1);
		nameAndImage.setWidth(100);
		Cell name = new Cell();
		name.setBorder(Rectangle.NO_BORDER);
		name.enableBorderSide(Rectangle.BOTTOM);
		name.add(basicTitle);
		name.setBorderWidth(2f);
		name.setBorderColor(Color.decode("0x429312"));
		name.setVerticalAlignment(Element.ALIGN_BOTTOM);
		Cell img = new Cell();
		img.setHorizontalAlignment(Element.ALIGN_CENTER);
		img.add(image);
		img.setBorderWidth(2f);
		img.setBorder(Rectangle.NO_BORDER);
		img.enableBorderSide(Rectangle.BOTTOM);
		img.setBorderColor(Color.decode("0x429312"));
		nameAndImage.addCell(name);
		nameAndImage.addCell(img);

		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		Paragraph basicInfo = new Paragraph();
		basicInfo.setFont(font3);
		basicInfo.setSpacingAfter(5);
		basicInfo.add("\n");
		if(e.getDateOfBirth() != null) {
			basicInfo.add("Date of birth: " + df.format(e.getDateOfBirth()));
		}
		for (TagCloud tc : education) {
			basicInfo.add("\n");
			basicInfo.add(tc.getNameTagCloud());
		}

		Phrase workExp = new Phrase("Work experience", font1);
		Paragraph workExpTitle = new Paragraph(workExp);

		Paragraph workExpInfo = new Paragraph();
		workExpInfo.setFont(font3);
		workExpInfo.add("\n");
		workExpInfo.add(e.getNameEmployee() + " has " + e.getYearsOfWorking()
				+ " years of working experience of which "
				+ e.getYearsOfWorkingExpInExecom() + " are in Execom.");
		workExpInfo.add("\n");
		workExpInfo.add(e.getNameEmployee() + " is "
				+ e.getTrainingLearningPriority());
		workExpInfo.add("\n");

		Phrase langs = new Phrase("Languages", font1);
		Paragraph langsTitle = new Paragraph(langs);
		Paragraph langsInfo = new Paragraph();
		langsInfo.setFont(font3);
		for (TagCloud tc : language) {
			langsInfo.add("\n");
			langsInfo.add(tc.getNameTagCloud());
		}
		langsInfo.add("\n");

		Phrase p3 = new Phrase("HR Profiling Tool", font2);
		Paragraph title = new Paragraph(p3);
		title.setAlignment(Element.ALIGN_RIGHT);

		Table table = new Table(2);
		Cell cell = new Cell();
		cell.addElement(execomImage);
		cell.setBorderWidth(Rectangle.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		Cell cell2 = new Cell();
		cell2.setBorder(Rectangle.NO_BORDER);
		cell2.addElement(title);
		table.addCell(cell);
		table.addCell(cell2);
		table.setWidth(90);
		table.setBorder(Rectangle.NO_BORDER);

		Phrase project = new Phrase("Project experience", font1);
		Paragraph projectTitle = new Paragraph(project);

		Paragraph projectInfo = new Paragraph();
		projectInfo.setFont(font3);
		projectInfo.setSpacingAfter(5);

		for (Project p : projects.keySet()) {
			projectInfo.add("\n");
			projectInfo.add(p.getNameProject() + ": ");
			projectInfo.add("( ");
			for(int i=0;i<projects.get(p).size();i++) {
				if(i+1== projects.get(p).size()) {
					projectInfo.add(projects.get(p).get(i).getNameTagCloud());
				} 
				else {
					projectInfo.add(projects.get(p).get(i).getNameTagCloud() + ", ");
				}
			}
			projectInfo.add(")");
		}
		projectInfo.add("\n");

		Phrase techSkills = new Phrase("Technical skills and competences", font1);
		Paragraph techSkillsTitle = new Paragraph(techSkills);
		techSkillsTitle.setSpacingAfter(5);

		Phrase techTitle = new Phrase("Technologies:", font2);
		techTitle.add("\n");

		Paragraph techInfo = new Paragraph();
		techInfo.setFont(font3);
		
		for(TagCloud tc : listTechnologies) {
			techInfo.add(tc.getNameTagCloud());
			techInfo.add("\n");
		}
		
		Phrase databaseTitle = new Phrase("Databases:", font2);
		databaseTitle.add("\n");

		Paragraph databaseInfo = new Paragraph();
		databaseInfo.setFont(font3);

		for (TagCloud tc : listDatabases) {
			databaseInfo.add(tc.getNameTagCloud());
			databaseInfo.add("\n");
		}

		Phrase ideTitle = new Phrase("IDEs:", font2);
		ideTitle.add("\n");

		Paragraph ideInfo = new Paragraph();
		ideInfo.setFont(font3);

		for (TagCloud tag : listIdes) {
			ideInfo.add(tag.getNameTagCloud());
			ideInfo.add("\n");
		}
		doc.add(execomImage);
		doc.add(nameAndImage);
		doc.add(basicInfo);
		doc.add(workExpTitle);
		doc.add(workExpInfo);
		doc.add(langsTitle);
		doc.add(langsInfo);
		doc.add(projectTitle);
		doc.add(projectInfo);
		doc.add(techSkillsTitle);
		doc.add(new Phrase("\n"));
		doc.add(techTitle);
		doc.add(techInfo);
		doc.add(databaseTitle);
		doc.add(databaseInfo);
		doc.add(ideTitle);
		doc.add(ideInfo);
		doc.close();
		return cv;
	}

}
