package swe645.ass3.model;

import java.util.List;

import javax.faces.bean.ManagedBean;

import swe645.ass3.service.StudentService;

/**
 * @author prasanna parthasarathy
 *This class is used to display all survey results and carry delete actions.
 */
@ManagedBean(name="SurveyList")
public class SurveyList {
	
	/* To hold all the records of the survey so far.. */
	private List<StudentDTO> surveyList;
	
	private String idToDel;
	
	public String getIdToDel() {
		return idToDel;
	}

	public void setIdToDel(String idToDel) {
		this.idToDel = idToDel;
	}

	public SurveyList() {
		System.out.println("Survey List Intialized!");
		StudentService studentService = new StudentService();
		this.surveyList = studentService.getSurveys(null,null,null,null);
		System.out.println("*************************"+ this.surveyList.size());
		
	}

	public List<StudentDTO> getSurveyList() {
	
		return surveyList;
	}
	

	public void setSurveyList(List<StudentDTO> surveyList) {
		this.surveyList = surveyList;
	}
	
	public String searchSurvey() {
		System.out.println("CALLING SEARCH SURVEY in SurvyeList Bean and redirecting to SurveyList");
		return "ListSurvey";
	}
	
	public String deleteSurvey() {
		System.out.println("To Del: "+getIdToDel());
		StudentService studentService = new StudentService();
		studentService.deleteStudent(Long.parseLong(getIdToDel()));
		this.surveyList = studentService.getSurveys(null,null,null,null);
		return "ListSurvey";
	}
	
}
