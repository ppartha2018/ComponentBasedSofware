package swe645.ass2.model;

import java.util.List;

import javax.faces.bean.ManagedBean;

import swe645.ass2.service.StudentService;

/**
 * @author prasanna parthasarathy
 *
 */
@ManagedBean(name="Student")
public class Student implements java.io.Serializable {
	
	/**
	 * Managed Bean to hold the Student survey.
	 */
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String streetAddress;
	private String city;
	private String state;
	private String zip;
	private String telephoneNo;
	private String email;
	private String dateOfSurvey;
	
	private List<String> likesCampus;
	private String interestUniversity;
	private String recommendUniversity;
	
	private String raffle;
	private String comments;
	private WinningResult winningResult;
	
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
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
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
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getTelephoneNo() {
		return telephoneNo;
	}
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDateOfSurvey() {
		return dateOfSurvey;
	}
	public void setDateOfSurvey(String dateOfSurvey) {
		this.dateOfSurvey = dateOfSurvey;
	}
	
	public List<String> getLikesCampus() {
		return likesCampus;
	}
	public void setLikesCampus(List<String> likesCampus) {
		this.likesCampus = likesCampus;
	}
	public String getInterestUniversity() {
		return interestUniversity;
	}
	public void setInterestUniversity(String interestUniversity) {
		this.interestUniversity = interestUniversity;
	}
	
	
	public String getRecommendUniversity() {
		return recommendUniversity;
	}
	public void setRecommendUniversity(String recommendUniversity) {
		this.recommendUniversity = recommendUniversity;
	}
	
	public String getRaffle() {
		return raffle;
	}
	public void setRaffle(String raffle) {
		this.raffle = raffle;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public WinningResult getWinningResult() {
		return winningResult;
	}
	public void setWinningResult(WinningResult winningResult) {
		this.winningResult = winningResult;
	}
	
	//capture the submit and call the helper methods to store the survey and decide winner
	public String submitSurvey()
	{
		System.out.println("Submit action called...");
		StudentService studentService = new StudentService();
		boolean storeResult = studentService.storeSurvey(this);
		double[] meanAndSD = studentService.calculateMeanAndSD(this.raffle);
		WinningResult winningResult = new WinningResult();
		winningResult.setMean(meanAndSD[0]);
		winningResult.setStandardDeviation(meanAndSD[1]);
		this.setWinningResult(winningResult);
		
		//determine whether user won a free movie ticket or not
		if(winningResult.getMean() > 90)
		{
			return "winner";
		}
		else
		{
			return "non-winner";
		}
	}
	
}
