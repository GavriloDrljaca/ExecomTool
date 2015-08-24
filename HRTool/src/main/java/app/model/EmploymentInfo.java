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
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
@Entity
public class EmploymentInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idEmploymentInfo;
	
	private String companyName;
	private Date startDate;
	private Date endDate;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	//@JsonManagedReference
	@JoinTable(name="TagCloudPos", joinColumns=@JoinColumn(name="idEmploymentInfo"), inverseJoinColumns=@JoinColumn(name="idTagCloud"))
	private Set<TagCloud> tagClouds;

	@ManyToOne
	@JsonIgnore
	//@JsonBackReference
	@JoinColumn(name="idEmployee")
	private Employee employee;

	public int getIdEmploymentInfo() {
		return idEmploymentInfo;
	}

	public void setIdEmploymentInfo(int idEmploymentInfo) {
		this.idEmploymentInfo = idEmploymentInfo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Set<TagCloud> getTagClouds() {
		return tagClouds;
	}

	public void setTagClouds(Set<TagCloud> tagClouds) {
		this.tagClouds = tagClouds;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
}
