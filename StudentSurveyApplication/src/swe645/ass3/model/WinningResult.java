package swe645.ass3.model;

//hold the mean and sd of the raffle numbers given by a student
/**
 * @author prasanna parthasarathy
 *
 */
public class WinningResult {

	private double mean;
	private double standardDeviation;
	
	
	public double getMean() {
		return mean;
	}
	public void setMean(double mean) {
		this.mean = mean;
	}
	public double getStandardDeviation() {
		return standardDeviation;
	}
	public void setStandardDeviation(double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}
	
	
}
