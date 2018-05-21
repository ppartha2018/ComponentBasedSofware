package swe645.ass2.model;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import swe645.ass2.service.StudentService;

/**
 * @author prasanna parthasarathy
 *
 */
@ManagedBean(name="SurveyList")
@ApplicationScoped
public class SurveyList {
	
	/* To hold all the records of the survey so far.. */
	private List<Student> surveyList;
	
	public SurveyList() {
		System.out.println("Survey List Intialized!");
	}

	public List<Student> getSurveyList() {
		StudentService studentService = new StudentService();
		this.surveyList = studentService.getSurveys();
		System.out.println("*************************"+ this.surveyList.size());
		return surveyList;
	}

	public void setSurveyList(List<Student> surveyList) {
		this.surveyList = surveyList;
	}
	
}
