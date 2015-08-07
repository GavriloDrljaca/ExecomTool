package app.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idEmployee;

	private EmployeeRole employeeRole;
	private String googleId;

	private String nameEmployee;
	private String gender;
	private Date dateOfBirth;
	private String address;
	private String phoneNumber;

	@Column(unique = true)
	private String email;
	private String image;

	private String emergencyPhoneNumber;
	private Date startDateFromBooklet;
	private String username;
	private String idCardNumber;
	private String passportNumber;
	private int yearsOfWorkingExpInExecom;
	private int yearsOfWorking;
	private String placeOfBirth;
	private String trainingLearningPriority;
	private String licencesCertificates;
	private String awards;
	private String contractType;
	
	private int communication;
	private int fastLearning;
	private int openToChange;
	private int teamPlayer;
	private int proactiveCommunication;
	private int interpersonalSkills;
	private int knowledgeSharing;
	private int judgement;
	private int decisionMaking;
	private int influencing;
	private int leadership;
	private int coaching;
	private int organizationalSkills;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "TagCloudEmp", joinColumns = @JoinColumn(name = "idEmployee"), inverseJoinColumns = @JoinColumn(name = "idTagCloud"))
	@JsonManagedReference
	private Set<TagCloud> tagClouds;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "employee", cascade = CascadeType.ALL)
	private Set<ProjectInfo> projectInfos;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "employee", cascade = CascadeType.ALL)
	private Set<EmploymentInfo> empInfos;

	public int getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(int idEmployee) {
		this.idEmployee = idEmployee;
	}

	public String getNameEmployee() {
		return nameEmployee;
	}

	public void setNameEmployee(String nameEmployee) {
		this.nameEmployee = nameEmployee;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getEmergencyPhoneNumber() {
		return emergencyPhoneNumber;
	}

	public void setEmergencyPhoneNumber(String emergencyPhoneNumber) {
		this.emergencyPhoneNumber = emergencyPhoneNumber;
	}

	public Date getStartDateFromBooklet() {
		return startDateFromBooklet;
	}

	public void setStartDateFromBooklet(Date startDateFromBooklet) {
		this.startDateFromBooklet = startDateFromBooklet;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public int getYearsOfWorkingExpInExecom() {
		return yearsOfWorkingExpInExecom;
	}

	public void setYearsOfWorkingExpInExecom(int yearsOfWorkingExpInExecom) {
		this.yearsOfWorkingExpInExecom = yearsOfWorkingExpInExecom;
	}

	public int getYearsOfWorking() {
		return yearsOfWorking;
	}

	public void setYearsOfWorking(int yearsOfWorking) {
		this.yearsOfWorking = yearsOfWorking;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getTrainingLearningPriority() {
		return trainingLearningPriority;
	}

	public void setTrainingLearningPriority(String trainingLearningPriority) {
		this.trainingLearningPriority = trainingLearningPriority;
	}

	public String getLicencesCertificates() {
		return licencesCertificates;
	}

	public void setLicencesCertificates(String licencesCertificates) {
		this.licencesCertificates = licencesCertificates;
	}

	public String getAwards() {
		return awards;
	}

	public void setAwards(String awards) {
		this.awards = awards;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public int getCommunication() {
		return communication;
	}

	public void setCommunication(int communication) {
		this.communication = communication;
	}

	public int getFastLearning() {
		return fastLearning;
	}

	public void setFastLearning(int fastLearning) {
		this.fastLearning = fastLearning;
	}

	public int getOpenToChange() {
		return openToChange;
	}

	public void setOpenToChange(int openToChange) {
		this.openToChange = openToChange;
	}

	public int getTeamPlayer() {
		return teamPlayer;
	}

	public void setTeamPlayer(int teamPlayer) {
		this.teamPlayer = teamPlayer;
	}

	public int getProactiveCommunication() {
		return proactiveCommunication;
	}

	public void setProactiveCommunication(int proactiveCommunication) {
		this.proactiveCommunication = proactiveCommunication;
	}

	public int getInterpersonalSkills() {
		return interpersonalSkills;
	}

	public void setInterpersonalSkills(int interpersonalSkills) {
		this.interpersonalSkills = interpersonalSkills;
	}

	public int getKnowledgeSharing() {
		return knowledgeSharing;
	}

	public void setKnowledgeSharing(int knowledgeSharing) {
		this.knowledgeSharing = knowledgeSharing;
	}

	public int getJudgement() {
		return judgement;
	}

	public void setJudgement(int judgement) {
		this.judgement = judgement;
	}

	public int getDecisionMaking() {
		return decisionMaking;
	}

	public void setDecisionMaking(int decisionMaking) {
		this.decisionMaking = decisionMaking;
	}

	public int getInfluencing() {
		return influencing;
	}

	public void setInfluencing(int influencing) {
		this.influencing = influencing;
	}

	public int getLeadership() {
		return leadership;
	}

	public void setLeadership(int leadership) {
		this.leadership = leadership;
	}

	public int getCoaching() {
		return coaching;
	}

	public void setCoaching(int coaching) {
		this.coaching = coaching;
	}

	public int getOrganizationalSkills() {
		return organizationalSkills;
	}

	public void setOrganizationalSkills(int organizationalSkills) {
		this.organizationalSkills = organizationalSkills;
	}

	public Set<TagCloud> getTagClouds() {
		return tagClouds;
	}

	public void setTagClouds(Set<TagCloud> tagClouds) {
		this.tagClouds = tagClouds;
	}

	public Set<ProjectInfo> getProjectInfos() {
		return projectInfos;
	}

	public void setProjectInfos(Set<ProjectInfo> projectInfos) {
		this.projectInfos = projectInfos;
	}

	public EmployeeRole getEmployeeRole() {
		return employeeRole;
	}

	public void setEmployeeRole(EmployeeRole employeeRole) {
		this.employeeRole = employeeRole;
	}

	public Set<EmploymentInfo> getEmpInfos() {
		return empInfos;
	}

	public void setEmpInfos(Set<EmploymentInfo> empInfos) {
		this.empInfos = empInfos;
	}

	@Override
	public String toString() {
		return "Employee [idEmployee=" + idEmployee + ", nameEmployee="
				+ nameEmployee + ", gender=" + gender + ", dateOfBirth="
				+ dateOfBirth + ", address=" + address + ", phoneNumber="
				+ phoneNumber + ", email=" + email + ", emergencyPhoneNumber="
				+ emergencyPhoneNumber + ", startDateFromBooklet="
				+ startDateFromBooklet + ", username=" + username
				+ ", idCardNumber=" + idCardNumber + ", passportNumber="
				+ passportNumber + ", yearsOfWorkingExpInExecom="
				+ yearsOfWorkingExpInExecom + ", yearsOfWorking="
				+ yearsOfWorking + ", placeOfBirth=" + placeOfBirth
				+ ", trainingLearningPriority=" + trainingLearningPriority
				+ ", licencesCertificates=" + licencesCertificates
				+ ", awards=" + awards + ", contractType=" + contractType
				+ ", communication=" + communication + ", fastLearning="
				+ fastLearning + ", openToChange=" + openToChange
				+ ", teamPlayer=" + teamPlayer + ", proactiveCommunication="
				+ proactiveCommunication + ", interpersonalSkills="
				+ interpersonalSkills + ", knowledgeSharing="
				+ knowledgeSharing + ", judgement=" + judgement
				+ ", decisionMaking=" + decisionMaking + ", influencing="
				+ influencing + ", leadership=" + leadership + ", coaching="
				+ coaching + ", organizationalSkills=" + organizationalSkills
				+ ", tagClouds=" + tagClouds + "]";
	}

	public String getGoogleId() {
		return googleId;
	}

	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}

}