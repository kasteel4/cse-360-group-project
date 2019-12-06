import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import errors.*;



public class GradeAnalytics {
	
	private ArrayList<Double> data;
	private double upperBound;
	private double lowerBound;
	
	/**
	 * Generic constructor
	 */
	public GradeAnalytics() {
		data = new ArrayList<Double>();
		upperBound = 100.0;
		lowerBound = 0.0;
	}
	
	/**
	 * Overloaded constructor for creating new G.A. object from existing data
	 * 
	 * @param list
	 */
	public GradeAnalytics(ArrayList<Double> list) {
		data = list;
	}
	
	/**
	 * 
	 * @param fileName
	 * @param fileType
	 * @return ArrayList<Double>
	 * @throws InvalidFileTypeException
	 * 
	 * Parses a .txt or .csv file for decimal values and returns them as an
	 * ArrayList. Does not support any other file types.
	 */
	public ArrayList<Double> parseFile(String fileName)
	throws InvalidFileTypeException {
		
		BufferedReader br = null;
		String line = "";
		String delim = "";
		String fileType = "Unknown";
		final File file = new File(fileName);
		
		try {
			
			fileType = Files.probeContentType(file.toPath());
			System.out.println(fileType);
			if (fileType.contentEquals("text/plain"))
				delim = " ";
			else
				delim = ",";
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			throw new InvalidFileTypeException("File type not supported.");
		}
		
		
		
		
		try {
			br = new BufferedReader(new FileReader(fileName));
			
			while ((line = br.readLine()) != null) {
				String[] lineRead = line.split(delim);
				for (int i = 0; i < lineRead.length; i++) {
					data.add(Double.parseDouble(lineRead[i]));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			throw new InvalidFileTypeException("File type not supported.");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return data;
	}
	
	/**
	 * Sets boundaries for G.A. Object
	 * 
	 * upper parameter must be at least 0.1 larger than lower parameter
	 * 
	 * @param upper upper boundary value
	 * @param lower lower boundary value
	 * @throws InvalidBoundaries
	 */
	public void setBoundaries(double upper, double lower) 
	throws InvalidBoundaries {
		
		if (upper < lower)
			throw new InvalidBoundaries("Upper bound must be higher than lower bound");
		if (upper - lower < 0.1) {
			throw new InvalidBoundaries("Bounds must differ by at least 0.1");
		}
		
		this.upperBound = upper;
		this.lowerBound = lower;
	}
	
	public void addData(double newData) {

		data.add(newData);
		
	}
	
	public void deleteData(double deleteData)
	throws DataNotFound
	{
		boolean complete = false;
		complete = data.remove(deleteData);
		if (!complete)
			throw new DataNotFound("The requested value is not in the data set");
	}
	
	public int getSize () {
		
		return data.size();
	}
	
	public double maxValue () {
		
		return Collections.max(data);
	}
	
	public double minValue () {
		
		return Collections.min(data);
	}
	
	public double getMean ()
	{
		double total = 0;
		
		for ( int i =0; i < data.size(); i++)
		{
			total += data.get(i);
		};
		
		return total / data.size();
	}
	
	public ArrayList <Double> getMode ()
	{
		// list of all the numbers with no duplicates
		TreeSet <Double> tree = new TreeSet<Double>(data);
		
	
		ArrayList <Double> modes = new ArrayList <Double>();
		
		int highmark = 0;
		
		for (Double x : tree)
		{
			int freq = Collections.frequency(data, x);
			if (freq == highmark)
			{
				modes.add(x);
			}
			
			if (freq > highmark)
			{
				modes.clear();
				modes.add(x);
				highmark = freq;
			}
		}
		
		return modes;
		
	}
	
}


