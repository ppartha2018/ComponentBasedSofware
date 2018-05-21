package swe645.ass3.model;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import swe645.ass3.service.StudentService;

/**
 * @author prasanna parthasarathy
 * Managed Bean for Student Survey Page. Propagates create and error actions.
 */
@ManagedBean(name="Student")
public class Student implements java.io.Serializable {
	
	/**
	 * Managed Bean to hold the Student survey.
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String firstName;
	private String lastName;
	private String streetAddress;
	private String city;
	private String state;
	private String zip;
	private String telephoneNo;
	private String email;
	private Date dateOfSurvey;
	private Date dateOfJoining;
	
	private List<String> likesCampus;
	private String likesCampusToStore;
	private String interestUniversity;
	private String recommendUniversity;
	
	private String raffle;
	private String comments;
	
	private WinningResult winningResult;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	
	public String getCity(){
		
		try
		{
			ServletRequest req = (ServletRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequest());
			//System.out.println(InetAddress.getLocalHost().getHostAddress());
			//System.out.println(req.getLocalPort());
			System.out.println("Invikng the web service city...");
			if(getZip() != null && req != null)
			{
				System.out.println("Invikng actually..");
				//since rest service is also in same war in the context .../rest/*
				Client client = ClientBuilder.newClient();
				String jsonString = client.target("http://"+InetAddress.getLocalHost().getHostAddress()+":"+req.getLocalPort()+FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/rest/ZipCodeService/getCityState/")
						.path("{zipCode}").resolveTemplate("zipCode", getZip()).request().get(String.class);
				if(jsonString != null && !jsonString.isEmpty())
				{
					String[] cityAndState = jsonString.split(",");
					this.city = cityAndState[0];
					this.state = cityAndState[1];
				}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return city;
	}
	
	public String getCityHelper()
	{
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
	
	public Date getDateOfSurvey() {
		return dateOfSurvey;
	}
	public void setDateOfSurvey(Date dateOfSurvey) {
		this.dateOfSurvey = dateOfSurvey;
	}
	
	public Date getDateOfJoining() {
		return dateOfJoining;
	}
	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	public List<String> getLikesCampus() {
		return likesCampus;
	}
	public void setLikesCampus(List<String> likesCampus) {
		this.likesCampus = likesCampus;
		
		//update the storage component as well
		StringBuilder sb = new StringBuilder();
		for(String s: likesCampus)
		{
			sb .append(s+",");
		}
		if(sb != null && sb.length() > 0)
		{
			likesCampusToStore = sb.toString().substring(0,  sb.toString().length() - 1);
		}
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
	
	public String getLikesCampusToStore() {
		
		return likesCampusToStore;
	}
	public void setLikesCampusToStore(String likesCampusToStore) {
		this.likesCampusToStore = likesCampusToStore;

	}
	public WinningResult getWinningResult() {
		return winningResult;
	}
	public void setWinningResult(WinningResult winningResult) {
		this.winningResult = winningResult;
	}
	
	public List<String> completeRecommend(String languagePrefix) {
		List<String> matches = new ArrayList<String>();
		matches.add("Very Likely");
		matches.add("Likely");
		matches.add("Unlikely");
		return(matches);
	}
	
	//validate for date of survey
	//capture the submit and call the helper methods to store the survey and decide winner
	public String submitSurvey()
	{
		System.out.println("Submit action called...");
		//date validation
		FacesContext context = FacesContext.getCurrentInstance();
		if (!dateOfSurvey.before(dateOfJoining)) 
		{
			System.out.println("Error...");
			//dateOfJoining = null;
			FacesMessage errorMessage =new FacesMessage("Semester start date should be after survey date");
			errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(	"surveyForm:dateOfJoining", errorMessage);
			return(null);
		} 
		
		StudentService studentService = new StudentService();
	//	boolean storeResult = studentService.storeSurvey(this);
		studentService.storeSurveyInDB(this);
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
