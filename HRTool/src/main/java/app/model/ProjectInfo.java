package app.model;

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
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
 
@Entity
public class ProjectInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProjectInfo;
	
	@JsonIgnore
	@ManyToOne 
	@JoinColumn(name="idProject")
	private Project project;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="idEmployee")
	private Employee employee;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
	@JsonIgnore
	@JoinTable(name="TagCloudsPrInfo", joinColumns=@JoinColumn(name="idProjectInfo"), inverseJoinColumns=@JoinColumn(name="idTagCloud"))
	private Set<TagCloud> tagClouds;
	
	private SeniorityEnum seniority;
	private String jobResponsibilities;
	private String projectExp;
	private int durationOnProject;
	private boolean active;
	
	
	public int getIdProjectInfo() {
		return idProjectInfo;
	}
	public void setIdProjectInfo(int idProjectInfo) {
		this.idProjectInfo = idProjectInfo;
	}
	public int getDurationOnProject() {
		return durationOnProject;
	}
	public void setDurationOnProject(int durationOnProject) {
		this.durationOnProject = durationOnProject;
	}
	
	
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Set<TagCloud> getTagClouds() {
		return tagClouds;
	}
	public void setTagClouds(Set<TagCloud> tagClouds) {
		this.tagClouds = tagClouds;
	}
	public String getJobResponsibilities() {
		return jobResponsibilities;
	}
	public void setJobResponsibilities(String jobResponsibilities) {
		this.jobResponsibilities = jobResponsibilities;
	}
	public String getProjectExp() {
		return projectExp;
	}
	public void setProjectExp(String projectExp) {
		this.projectExp = projectExp;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public SeniorityEnum getSeniority() {
		return seniority;
	}
	public void setSeniority(SeniorityEnum seniority) {
		this.seniority = seniority;
	}
	
	
}
