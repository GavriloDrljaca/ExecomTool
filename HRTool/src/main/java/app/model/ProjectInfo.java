package app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
 
@Entity
public class ProjectInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProjectInfo;
	
	
	  @ManyToOne 
	@JoinColumn(name="idProject")
	private Project project;
	
	@ManyToOne
	@JoinColumn(name="idEmployee")
	private Employee employee;
	
	private String jobResponsibilities;
	private String projectExp;
	
	public ProjectInfo(){
		
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
	
	
}
