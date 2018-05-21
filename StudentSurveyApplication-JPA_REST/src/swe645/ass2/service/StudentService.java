package swe645.ass2.service;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import swe645.ass2.model.Student;

/**
 * @author prasanna parthasarathy
 *
 */
public class StudentService {
	
	//write student survey as a serialized object to a file
	public boolean storeSurvey(Student student)
	{
		ObjectOutputStream oos = null;
		boolean result = true;
		try
		{
			File storeFile = new File("surveys.txt");
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
			fis = new FileInputStream(new File("surveys.txt"));
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

}
