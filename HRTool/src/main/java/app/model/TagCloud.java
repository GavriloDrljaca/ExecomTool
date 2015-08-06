package app.model;

import java.util.Set;

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
	private Set<Project> projects;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "tagClouds")
	private Set<Employee> employees;
	
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "tagClouds")
	private Set<EmploymentInfo> employmentInfos;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "tagClouds")
	private Set<Project> projectInfos;

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
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

	public Set<EmploymentInfo> getEmploymentInfos() {
		return employmentInfos;
	}

	public void setEmploymentInfos(Set<EmploymentInfo> employmentInfos) {
		this.employmentInfos = employmentInfos;
	}

	public Set<Project> getProjectInfos() {
		return projectInfos;
	}

	public void setProjectInfos(Set<Project> projectInfos) {
		this.projectInfos = projectInfos;
	}
}
