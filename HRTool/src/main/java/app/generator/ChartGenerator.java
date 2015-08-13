package app.generator;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import app.model.Employee;
import app.model.ProjectInfo;
import app.model.SeniorityEnum;
import app.model.TagCloud;
import app.model.TagCloudEnum;
import app.repository.ProjectInfoRepository;

import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class ChartGenerator {

	/**
	 * Generates seniority pie chart and saves it in a pdf.
	 * @param emps All employees
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 * @throws ParseException
	 */
	public static void generatePieChart(List<Employee> emps) throws FileNotFoundException, DocumentException, ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		Document doc = new Document();

		PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(new File("./seniority-chart.pdf")));

		doc.open();

		doc.add(new Phrase("Seniority:"));
		
		PdfContentByte cb = writer.getDirectContent();

		float width = PageSize.A4.getWidth();
		float height = PageSize.A4.getHeight();

		PdfTemplate pie = cb.createTemplate(width, height);
		Graphics2D gr = new PdfGraphics2D(pie, width, height);
		Rectangle2D rec = new Rectangle2D.Double(0, 0, width, height/2);

		DefaultPieDataset pieDataset = new DefaultPieDataset();

		List<ProjectInfo> seniority = new ArrayList<>();
		for (Employee e : emps) {
			Date lastDate = df.parse("1900-02-01");
			ProjectInfo last = new ProjectInfo();
			Set<ProjectInfo> projectInfoIt = e.getProjectInfos();
			if (projectInfoIt.size() == 0) {
				continue;
			}
			for (ProjectInfo pi : projectInfoIt) {
				if(pi.getProject().getStartDate()!= null && pi.getProject().getStartDate().after(lastDate) && pi.isActive()){
					lastDate = pi.getProject().getStartDate();
					lastDate = pi.getProject().getStartDate();
					last = pi;
				}
			}
			seniority.add(last);
		}
		int j = 0, m = 0, s = 0;
		if (seniority.size() > 0) {
			for (ProjectInfo pi : seniority) {
				if (pi.getSeniority() != null) {
					if (pi.getSeniority().equals(SeniorityEnum.Junior)) {
						j++;
					} else if (pi.getSeniority().equals(SeniorityEnum.Medior)) {
						m++;
					} else if (pi.getSeniority().equals(SeniorityEnum.Senior)) {
						s++;
					}
				}
			}
		}
		pieDataset.setValue(j + " - Juniors", j * 100 / 3);
		pieDataset.setValue(m + " - Mediors", m * 100 / 3);
		pieDataset.setValue(s + " - Seniors", s * 100 / 3);

		JFreeChart chart = ChartFactory.createPieChart("Seniority", pieDataset,
				true, true, false);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0}: {2}"));
		chart.draw(gr, rec);
		gr.dispose();
		cb.addTemplate(pie, 0, 0);

		doc.close();
	}
	/**
	 * Generates a @param tce pie chart and saves it in a pdf.
	 * @param projInfos
	 * @param tce
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 * @throws ParseException
	 */
	public static void generateTechnology(List<ProjectInfo> projInfos, TagCloudEnum tce) throws FileNotFoundException, DocumentException, ParseException {

		List<TagCloud> tagClouds = new ArrayList<>();

		for (ProjectInfo pi : projInfos) {
			if(pi.isActive()) {
				tagClouds.addAll(pi.getTagClouds());
			}
		}
		Document doc = new Document();
		PdfWriter writer = null;
		if (tce.equals(TagCloudEnum.Technologie)) {
			writer = PdfWriter.getInstance(doc, new FileOutputStream(new File("./technology-chart.pdf")));
		}
		else {
			writer = PdfWriter.getInstance(doc, new FileOutputStream(new File("./database-chart.pdf")));
		}
		doc.open();

		doc.add(new Phrase("Used " + tce.toString() + "s:"));

		PdfContentByte cb = writer.getDirectContent();

		float width = PageSize.A4.getWidth();
		float height = PageSize.A4.getHeight();

		PdfTemplate pie = cb.createTemplate(width, height);
		Graphics2D gr = new PdfGraphics2D(pie, width, height);
		Rectangle2D rec = new Rectangle2D.Double(0, 0, width, height / 2);

		DefaultPieDataset pieDataset = new DefaultPieDataset();

		Map<String, Integer> numberOfNames = new HashMap<>();
		for (TagCloud tc : tagClouds) {
			if (tc.getTipTagCloud().equals(tce)) {
				int count = numberOfNames.containsKey(tc.getNameTagCloud()) ? numberOfNames.get(tc.getNameTagCloud()) : 0;
				numberOfNames.put(tc.getNameTagCloud(), count + 1);
			}

		}
		Iterator it = numberOfNames.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Integer> pair = (Map.Entry) it.next();
			pieDataset.setValue(pair.getValue() + " - " + pair.getKey(), pair.getValue());
			it.remove();
		}

		JFreeChart chart = ChartFactory.createPieChart(tce.toString() + "s", pieDataset,
				true, true, false);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {2}"));
		chart.draw(gr, rec);
		gr.dispose();
		cb.addTemplate(pie, 0, 0);

		doc.close();
	}
}
