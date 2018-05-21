package swe645.ass3.service;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import swe645.ass3.model.Student;
import swe645.ass3.model.StudentDTO;

/**
 * @author prasanna parthasarathy
 * Helper class for storage and retrieval actions.
 */
public class StudentService {
	
	//write student survey as a serialized object to a file
	public boolean storeSurvey(Student student)
	{
		ObjectOutputStream oos = null;
		boolean result = true;
		try
		{
			File storeFile = new File("surveys3.txt");
			//first time write, or else append
			if(storeFile.length() == 0)
			{
				oos = new ObjectOutputStream(new FileOutputStream(storeFile));
				oos.writeObject(student);
			}
			else
			{
				oos = new ObjectOutputStream(new FileOutputStream(storeFile, true));
				oos.writeObject(student);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = false;
		}
		finally
		{
			try
			{
				if(oos != null)
				{
					oos.flush();
					oos.close();
				}
			}
			catch(IOException ie){}
		}
		
		return result;
	}
	
	//read objects from file into a list and return it
	public List<Student> getSurveys()
	{
		System.out.println("Get Service List Called..");
		List<Student> surveys = new ArrayList<Student>();
		Student student = null;
		ObjectInputStream ois = null;
		FileInputStream fis = null;
		try 
		{
			fis = new FileInputStream(new File("surveys3.txt"));
			while(true)
			{
				ois = new ObjectInputStream(fis);
				student = (Student) ois.readObject();
				surveys.add(student);
			}
		} catch (EOFException ignored) {
	        // as expected
	    }catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		finally
		{
			try
			{
				if(ois != null)
				{
					ois.close();
				}
				if(fis != null)
				{
					fis.close();
				}
			}
			catch(IOException ie){}
		}
		return surveys;
	}
	
	//method calculates mean and sd based on the numbers in the raffle. If there is any issue
	//in raffle like parseException, mean and sd will be 0.
	public double[] calculateMeanAndSD(String raffle)
	{
		double mean = 0;
		double total = 0;
		double diffTotal = 0;
		double variance = 0;
		double sd = 0;
		int size = 0;
		double[] result = new double[2]; // 0 - mean, 1 - standard deviation
		
		try
		{
			if(raffle != null)
			{
				String[] numbers = raffle.split(",");
				size = numbers.length;
				for(String s : numbers) 
				{
					total = total + Integer.parseInt(s);
				}
				mean = total / size;
				for(String s : numbers)
				{
					int Xi = Integer.parseInt(s);
					diffTotal = diffTotal + ((mean - Xi)*(mean - Xi));
				}
				//unbiased standard deviation
				variance = diffTotal / (size - 1);
				sd = Math.sqrt(variance);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//2 decimal places
		result[0] = ((double) (Math.round(mean*100))) / 100;
		result[1] = ((double) (Math.round(sd*100))) / 100;
		return result;
	}
	
	private final String PERSISTENT_UNIT_NAME = "assign4";
	private static EntityManagerFactory factory;

	
	//store the survey record in db using jpa
	public void storeSurveyInDB(Student student)
	{
		StudentDTO dto = populateStudent(student);
			factory = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
		
		EntityManager em = factory.createEntityManager();
		System.out.println("Persisting student survey...");
		em.getTransaction().begin();
		em.persist(dto);
		em.getTransaction().commit();
		em.close();
	}
	
	public StudentDTO populateStudent(Student student)
	{
		StudentDTO dto = new StudentDTO();
		dto.setFirstName(student.getFirstName());
		dto.setLastName(student.getLastName());
		dto.setStreetAddress(student.getStreetAddress());
		dto.setCity(student.getCityHelper());
		dto.setState(student.getState());
		dto.setZip(student.getZip());
		dto.setTelephoneNo(student.getTelephoneNo());
		dto.setEmail(student.getEmail());
		dto.setDateOfSurvey(student.getDateOfSurvey());
		dto.setDateOfJoining(student.getDateOfJoining());
		dto.setLikesCampusToStore(student.getLikesCampusToStore());
		dto.setInterestUniversity(student.getInterestUniversity());
		dto.setRecommendUniversity(student.getRecommendUniversity());
		dto.setRaffle(student.getRaffle());
		dto.setComments(student.getComments());
		
		return dto;
	}
	
	//read objects from db into a list and return it
	public List<StudentDTO> getSurveys(String firstName, String lastName, String city, String state)
	{
		List<StudentDTO> studentList = null;
			factory = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		System.out.println("Getting student survey list from DB...");
		StringBuilder qs = new StringBuilder();
		boolean insertAnd = false;
		qs.append("from StudentDTO s");
		
		if(notEmpty(firstName) || notEmpty(lastName) || notEmpty(city) || notEmpty(state))
		{
			qs.append(" where ");
		}	
		if(notEmpty(firstName))
		{
			qs.append("s.firstName=:firstName");
			insertAnd = true;
		}
			
		if(notEmpty(lastName))
		{
			if(insertAnd) qs.append(" and ");
			qs.append("s.lastName=:lastName ");
			insertAnd = true;
		}
			
		if(notEmpty(city))
		{
			if(insertAnd) qs.append(" and ");
			qs.append("s.city=:city ");
			insertAnd = true;
		}
			
		if(notEmpty(state))
		{
			if(insertAnd) qs.append(" and ");
			qs.append("s.state=:state");
		}
		
		System.out.println("My final Query: "+ qs.toString());
		
		Query query = em.createQuery(qs.toString());
		if(notEmpty(firstName))
			query.setParameter("firstName", firstName);
		if(notEmpty(lastName))
			query.setParameter("lastName", lastName);
		if(notEmpty(city))
			query.setParameter("city", city);
		if(notEmpty(state))
			query.setParameter("state", state);
		
		studentList = query.getResultList();
		em.close();
		return studentList;
	}
	
	boolean notEmpty(String s)
	{
		return (s != null && !s.equals(""));
	}
	
	public void deleteStudent(long id)
	{
			factory = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		StudentDTO studentToDel = em.find(StudentDTO.class, id);
		if(studentToDel != null)
		{
			em.getTransaction().begin();
			em.remove(studentToDel);
			em.getTransaction().commit();
		}
		System.out.println("Student with id "+id+" is deleted from database.");
		em.close();
	}
	
}
