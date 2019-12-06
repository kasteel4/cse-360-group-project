import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.time.LocalDateTime;

import errors.*;



public class GradeAnalytics {
	
	private ArrayList<Double> data;
	private ArrayList<Action> history;
	private double upperBound;
	private double lowerBound;
	
	/**
	 * Generic constructor
	 */
	public GradeAnalytics() {
		data = new ArrayList<Double>();
		history = new ArrayList<Action>();
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
		history = new ArrayList<Action>();
		upperBound = 100.0;
		lowerBound = 0.0;
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
		this.sortData();
		history.add(new Action(0));
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
		
		history.add(new Action(1));
	}
	
	public void addData(double newData) {

		data.add(newData);
		this.sortData();
		
		history.add(new Action(3));
	}
	
	public void deleteData(double deleteData)
	throws DataNotFound
	{
		boolean complete = false;
		complete = data.remove(deleteData);
		if (!complete)
			throw new DataNotFound("The requested value is not in the data set");
		
		history.add(new Action(4));
	}
	
	/**
	 * Returns statistical data for the data set
	 * 
	 * Values are stored in following indices
	 * 	0 | Minimum
	 * 	1 | Maximum
	 *  2 | Mean
	 *  3 | Median
	 *  4...n | Mode
	 * 
	 * @return ArrayList with values stored in specified order
	 */
	public ArrayList<Double> analyzeData() {
		ArrayList<Double> results = new ArrayList<Double>();
		
		results.add(this.minValue());
		results.add(this.maxValue());
		results.add(this.getMean());
		//results.add(this.getMedian());
		results.addAll(getMode());
		
		history.add(new Action(5));
		
		return results;
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
		}
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
	
	
	/**
	 * Generates a txt report and saves it in the specified destination
	 * 
	 * @param filepath Destination to save txt file
	 */
	public void saveReport(String filepath)
	throws IOException {
		String content = this.generateReport();
		Path path = Paths.get(filepath);
		
		try {
			BufferedWriter br = Files.newBufferedWriter(path);
			br.write(content);
			br.flush();
		} catch (IOException e) {
			throw new IOException(e);
		}
	}
	
	private String generateReport() {
		String content = "";
		content += "GradeAnalytics Report | " + LocalDateTime.now() + "\n\n";
		content += "Size of data set: " + this.getSize() + "\n";
		content += "Minimum/Maximum: " + this.minValue() + "/" + this.maxValue() + "\n";
		content += "Mean: " + this.getMean() + "\n";
		content += "Median: " + this.getMean() + "\n";
		content += "Mode(s): ";
		for (double val : this.getMode())
			content += val + " ";
		content += "\n\n*****HISTORY*****";
		for (Action i : history) {
			content += i.toString();
		}
		
		content += "\nEND OF REPORT";
		
		return content;
	}
	
	private void sortData() {
		data.sort(null);
	}
	
}


