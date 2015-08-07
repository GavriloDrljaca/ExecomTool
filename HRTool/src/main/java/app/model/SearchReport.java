package app.model;

import java.util.ArrayList;
import java.util.Date;

public class SearchReport {

	private String seniority;
	private int yearsOfExperiance;
	private String yearsOfExperianceRelation;
	private Date yearOfEmployment;
	private String yearOfEmploymentRelation;
	private ArrayList<String> education;
	private ArrayList<String> technology;
	private ArrayList<String> position;
	private ArrayList<String> database;
	private ArrayList<String> language;
	
	public String getSeniority() {
		return seniority;
	}
	public void setSeniority(String seniority) {
		this.seniority = seniority;
	}
	public int getYearsOfExperiance() {
		return yearsOfExperiance;
	}
	public void setYearsOfExperiance(int yearsOfExperiance) {
		this.yearsOfExperiance = yearsOfExperiance;
	}
	public String getYearsOfExperianceRelation() {
		return yearsOfExperianceRelation;
	}
	public void setYearsOfExperianceRelation(String yearsOfExperianceRelation) {
		this.yearsOfExperianceRelation = yearsOfExperianceRelation;
	}
	public Date getYearOfEmployment() {
		return yearOfEmployment;
	}
	public void setYearOfEmployment(Date yearOfEmployment) {
		this.yearOfEmployment = yearOfEmployment;
	}
	public String getYearOfEmploymentRelation() {
		return yearOfEmploymentRelation;
	}
	public void setYearOfEmploymentRelation(String yearOfEmploymentRelation) {
		this.yearOfEmploymentRelation = yearOfEmploymentRelation;
	}
	public ArrayList<String> getEducation() {
		return education;
	}
	public void setEducation(ArrayList<String> education) {
		this.education = education;
	}
	public ArrayList<String> getTechnology() {
		return technology;
	}
	public void setTechnology(ArrayList<String> technology) {
		this.technology = technology;
	}
	public ArrayList<String> getPosition() {
		return position;
	}
	public void setPosition(ArrayList<String> position) {
		this.position = position;
	}
	public ArrayList<String> getDatabase() {
		return database;
	}
	public void setDatabase(ArrayList<String> database) {
		this.database = database;
	}
	public ArrayList<String> getLanguage() {
		return language;
	}
	public void setLanguage(ArrayList<String> language) {
		this.language = language;
	}
}
