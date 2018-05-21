package swe645.ass3.model;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import swe645.ass3.service.StudentService;

/*
 *This class handles the search results and delete actions on search.
 **/
@ManagedBean(name="SearchBean")
@SessionScoped
public class SearchBean {

	private String firstName;
	private String lastName;
	private String city;
	private String state;
	
	private List<StudentDTO> surveyList = new ArrayList<StudentDTO>();
	
	private String idToDel;
	
	public String getIdToDel() {
		return idToDel;
	}

	public void setIdToDel(String idToDel) {
		this.idToDel = idToDel;
	}
	
	public List<StudentDTO> getSurveyList() {
		return surveyList;
	}

	public void setSurveyList(List<StudentDTO> surveyList) {
		this.surveyList = surveyList;
	}
	
	public SearchBean() {
		System.out.println("Survey List Intialized!");
		StudentService studentService = new StudentService();
		this.surveyList = studentService.getSurveys(this.getFirstName(), this.getLastName(), this.getCity(), this.getState());
		System.out.println("Initializing Search Bean: "+ this.surveyList.size());
	}
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String searchSurvey() {
		System.out.println("CALLING SEARCH SURVEY in Search Bean and redirecting to SurveyList");
		StudentService studentService = new StudentService();
		this.surveyList = studentService.getSurveys(this.getFirstName(), this.getLastName(), this.getCity(), this.getState());
		System.out.println("*************************"+ this.surveyList.size());
		return "SearchResults";
	}
	
	public String deleteSurvey() {
		System.out.println("To Del: "+getIdToDel());
		StudentService studentService = new StudentService();
		studentService.deleteStudent(Long.parseLong(getIdToDel()));
		this.surveyList = studentService.getSurveys(this.getFirstName(), this.getLastName(), this.getCity(), this.getState());
		return null;
	}
	
}
