package app.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class TagCloud {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idTagCloud;

	private String nameTagCloud;

	private TagCloudEnum tipTagCloud;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "tagClouds")
	private List<Project> projects;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "tagClouds")
	private List<Employee> employees;

	public TagCloud() {

	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public int getIdTagCloud() {
		return idTagCloud;
	}

	public void setIdTagCloud(int idTagCloud) {
		this.idTagCloud = idTagCloud;
	}

	public String getNameTagCloud() {
		return nameTagCloud;
	}

	public void setNameTagCloud(String nameTagCloud) {
		this.nameTagCloud = nameTagCloud;
	}

	public TagCloudEnum getTipTagCloud() {
		return tipTagCloud;
	}

	public void setTipTagCloud(TagCloudEnum tipTagCloud) {
		this.tipTagCloud = tipTagCloud;
	}
	// public List<TagClouds> getTagClouds() {
	// return tagClouds;
	// }
	// public void setTagClouds(List<TagClouds> tagClouds) {
	// this.tagClouds = tagClouds;
	// }
	// public List<TagCloudEmp> getTagCloudEmps() {
	// return tagCloudEmps;
	// }
	// public void setTagCloudEmps(List<TagCloudEmp> tagCloudEmps) {
	// this.tagCloudEmps = tagCloudEmps;
	// }
}
