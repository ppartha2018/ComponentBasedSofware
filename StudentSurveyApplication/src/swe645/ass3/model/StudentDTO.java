package swe645.ass3.model;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * @author prasanna parthasarathy
 *
 *	JPA Entity which is the persisted object. create, delete and lookup for survey records are done with this object
 */
@Entity
public class StudentDTO implements java.io.Serializable {
	
	/**
	 * Managed Bean to hold the Student survey.
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
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
	
	@Transient
	private List<String> likesCampus;
	private String likesCampusToStore;
	private String interestUniversity;
	private String recommendUniversity;
	
	private String raffle;
	private String comments;
	
	@Transient
	private WinningResult winningResult;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="FIRST_NAME", length=255, nullable=false)
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name="LAST_NAME", length=255, nullable=false)
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Column(name="STREET_ADDRESS", length=255, nullable=false)
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	
	@Column(name="CITY", length=255)
	public String getCity() throws UnknownHostException {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name="STATE", length=255)
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@Column(name="ZIP", length=255)
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	@Column(name="TELEPHONE_NO", length=255, nullable=false)
	public String getTelephoneNo() {
		return telephoneNo;
	}
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}
	
	@Column(name="EMAIL", length=255, nullable=false)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}	
	
	@Column(name="DATE_OF_SURVEY", nullable=false)
	public Date getDateOfSurvey() {
		return dateOfSurvey;
	}
	public void setDateOfSurvey(Date dateOfSurvey) {
		this.dateOfSurvey = dateOfSurvey;
	}
	
	@Column(name="DATE_OF_JOINING", length=255, nullable=false)
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
	
	@Column(name="INTEREST_UNIVERSITY", length=255, nullable=false)
	public String getInterestUniversity() {
		return interestUniversity;
	}
	public void setInterestUniversity(String interestUniversity) {
		this.interestUniversity = interestUniversity;
	}
	
	@Column(name="RECOMMEND_UNIVERSITY", length=255, nullable=false)
	public String getRecommendUniversity() {
		return recommendUniversity;
	}
	public void setRecommendUniversity(String recommendUniversity) {
		this.recommendUniversity = recommendUniversity;
	}
	
	@Column(name="RAFFLE", length=255, nullable=false)
	public String getRaffle() {
		return raffle;
	}
	public void setRaffle(String raffle) {
		this.raffle = raffle;
	}
	
	@Column(name="Comments", length=255)
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	@Column(name="likesCampus", length=255)
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
	
}
