package app.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Project {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idProject;

	private String nameProject;
	private Date startDate;
	private int durationOfProject;
	private boolean execom;
	private String companyName;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)  
	@JoinTable(name="TagClouds", joinColumns=@JoinColumn(name="idProject"), inverseJoinColumns=@JoinColumn(name="idTagCloud"))  
	private Set<TagCloud> tagClouds;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="project", cascade = CascadeType.ALL)
	private Set<ProjectInfo> projectInfo;
	
	public Set<TagCloud> getTagClouds() {
		return tagClouds;
	}
	public void setTagClouds(Set<TagCloud> tagClouds) {
		this.tagClouds = tagClouds;
	}
	public int getIdProject() {
		return idProject;
	}
	public void setIdProject(int idProject) {
		this.idProject = idProject;
	}
	public String getNameProject() {
		return nameProject;
	}
	public void setNameProject(String nameProject) {
		this.nameProject = nameProject;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public int getDurationOfProject() {
		return durationOfProject;
	}
	public void setDurationOfProject(int durationOfProject) {
		this.durationOfProject = durationOfProject;
	}
	public boolean isExecom() {
		return execom;
	}
	public void setExecom(boolean execom) {
		this.execom = execom;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Set<ProjectInfo> getProjectInfo() {
		return projectInfo;
	}

	public void setProjectInfo(Set<ProjectInfo> projectInfo) {
		this.projectInfo = projectInfo;
	}
	
}
