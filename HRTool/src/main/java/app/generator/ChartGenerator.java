package app.generator;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import app.controllers.ReportRestController;
import app.model.Employee;
import app.model.ProjectInfo;
import app.model.TagCloud;
import app.model.TagCloudEnum;

public class ChartGenerator {

	public static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	public static Date currentDate = new Date();
	private static Logger logger = Logger.getLogger(ChartGenerator.class.getName());
	/**
	 * Generates seniority pie chart and saves it in a pdf.
	 * 
	 * @param employees
	 *            All employees
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 * @throws ParseException
	 */
	public static File generatePieChart(List<Employee> employees){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		Document doc = new Document();
		currentDate = new Date();
		String fileName = "./seniority-chart_" + sdf.format(currentDate) + ".pdf";
		File pc = new File(fileName);
		PdfWriter writer = null;
		try {
			writer = PdfWriter.getInstance(doc, new FileOutputStream(pc));
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage());
		} catch (DocumentException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}

		doc.open();
		try {
			doc.add(new Phrase("Seniority:"));
		} catch (DocumentException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}

		PdfContentByte cb = writer.getDirectContent();

		float width = PageSize.A4.getWidth();
		float height = PageSize.A4.getHeight();

		PdfTemplate pie = cb.createTemplate(width, height);
		Graphics2D gr = new PdfGraphics2D(pie, width, height);
		Rectangle2D rec = new Rectangle2D.Double(0, 0, width, height / 2);

		DefaultPieDataset pieDataset = new DefaultPieDataset();

		List<ProjectInfo> seniority = new ArrayList<>();
		for (Employee employee : employees) {
			Date lastDate = null;
			try {
				lastDate = df.parse("1900-02-01");
			} catch (ParseException e) {
				logger.log(Level.SEVERE, e.getMessage());
			}
			ProjectInfo last = new ProjectInfo();
			Set<ProjectInfo> projectInfoIt = employee.getProjectInfos();
			if (projectInfoIt.size() == 0) {
				continue;
			}
			for (ProjectInfo pi : projectInfoIt) {
				if (pi.getProject().getStartDate() != null && pi.getProject().getStartDate().after(lastDate)
						&& pi.isActive()) {
					lastDate = pi.getProject().getStartDate();
					last = pi;
				}
			}
			seniority.add(last);
		}
		int juniors = 0, mediors = 0, seniors = 0;
		if (seniority.size() > 0) {
			for (ProjectInfo pi : seniority) {
				if (pi.getSeniority() != null) {
					switch (pi.getSeniority()) {
					case Junior:
						juniors++;
						break;
					case Medior:
						mediors++;
						break;
					case Senior:
						seniors++;
					}
				}

			}
		}
		pieDataset.setValue(juniors + " - Juniors", juniors * 100 / 3);
		pieDataset.setValue(mediors + " - Mediors", mediors * 100 / 3);
		pieDataset.setValue(seniors + " - Seniors", seniors * 100 / 3);

		JFreeChart chart = ChartFactory.createPieChart("Seniority", pieDataset, true, true, false);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {2}"));
		chart.draw(gr, rec);
		gr.dispose();
		cb.addTemplate(pie, 0, 0);

		doc.close();
		return pc;
	}

	/**
	 * Generates a @param tce pie chart and saves it in a pdf.
	 * 
	 * @param projInfos
	 * @param tagCloudEnum
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 * @throws ParseException
	 */
	public static File generateTechnologyOrDatabase(List<ProjectInfo> projInfos, TagCloudEnum tagCloudEnum) {

		List<TagCloud> tagClouds = new ArrayList<>();

		for (ProjectInfo pi : projInfos) {
			if (pi.isActive()) {
				tagClouds.addAll(pi.getTagClouds());
			}
		}
		Document doc = new Document();
		PdfWriter writer = null;
		String fileName = null;
		File pc = null;
		switch (tagCloudEnum) {
		case Technologie:
			currentDate = new Date();
			fileName = "./technology-chart_" + sdf.format(currentDate) + ".pdf";
			pc = new File(fileName);
			try {
				writer = PdfWriter.getInstance(doc, new FileOutputStream(pc));
			} catch (FileNotFoundException e) {
				logger.log(Level.SEVERE, e.getMessage());
			} catch (DocumentException e) {
				logger.log(Level.SEVERE, e.getMessage());
			}
			break;
		case Database:
			currentDate = new Date();
			fileName = "./database-chart_" + sdf.format(currentDate) + ".pdf";
			pc = new File(fileName);
			try {
				writer = PdfWriter.getInstance(doc, new FileOutputStream(pc));
			} catch (FileNotFoundException e) {
				logger.log(Level.SEVERE, e.getMessage());
			} catch (DocumentException e) {
				logger.log(Level.SEVERE, e.getMessage());
			}
			break;
		default:
			break;
		}

		doc.open();

		try {
			doc.add(new Phrase("Used " + tagCloudEnum.toString() + "s:"));
		} catch (DocumentException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}

		PdfContentByte cb = writer.getDirectContent();

		float width = PageSize.A4.getWidth();
		float height = PageSize.A4.getHeight();

		PdfTemplate pie = cb.createTemplate(width, height);
		Graphics2D gr = new PdfGraphics2D(pie, width, height);
		Rectangle2D rec = new Rectangle2D.Double(0, 0, width, height / 2);

		DefaultPieDataset pieDataset = new DefaultPieDataset();

		Map<String, Integer> numberOfNames = new HashMap<>();
		for (TagCloud tc : tagClouds) {
			if (tc.getTipTagCloud().equals(tagCloudEnum)) {
				int count = numberOfNames.containsKey(tc.getNameTagCloud()) ? numberOfNames.get(tc.getNameTagCloud())
						: 0;
				numberOfNames.put(tc.getNameTagCloud(), count + 1);
			}

		}
		Iterator it = numberOfNames.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Integer> pair = (Map.Entry) it.next();
			pieDataset.setValue(pair.getValue() + " - " + pair.getKey(), pair.getValue());
			it.remove();
		}

		JFreeChart chart = ChartFactory.createPieChart(tagCloudEnum.toString() + "s", pieDataset, true, true, false);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {2}"));
		chart.draw(gr, rec);
		gr.dispose();
		cb.addTemplate(pie, 0, 0);

		doc.close();
		return pc;
	}
}
