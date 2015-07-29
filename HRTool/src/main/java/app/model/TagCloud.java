package app.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class TagCloud {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTagCloud;
	
	private String nameTagCloud;
	
	private String tipTagCloud;
	
	@ManyToMany(cascade=CascadeType.ALL, mappedBy="tagClouds")
	private List<Project> projects;
	
	@ManyToMany(cascade=CascadeType.ALL, mappedBy="tagClouds")
	private List<Employee> employees;
	
	public TagCloud(){
		
	}
//	@OneToMany(mappedBy="tagCloud")
//	private List<TagClouds> tagClouds;
//	
//	@OneToMany(mappedBy="tagCloud")
//	private List<TagCloudEmp> tagCloudEmps;
	
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
	public String getTipTagCloud() {
		return tipTagCloud;
	}
	public void setTipTagCloud(String tipTagCloud) {
		this.tipTagCloud = tipTagCloud;
	}
//	public List<TagClouds> getTagClouds() {
//		return tagClouds;
//	}
//	public void setTagClouds(List<TagClouds> tagClouds) {
//		this.tagClouds = tagClouds;
//	}
//	public List<TagCloudEmp> getTagCloudEmps() {
//		return tagCloudEmps;
//	}
//	public void setTagCloudEmps(List<TagCloudEmp> tagCloudEmps) {
//		this.tagCloudEmps = tagCloudEmps;
//	}
}
