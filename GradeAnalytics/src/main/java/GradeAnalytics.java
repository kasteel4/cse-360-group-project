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

	 * @throws  DataOutOfBounds 
	 */
	public ArrayList<Double> parseFile(String fileName)
	throws InvalidFileTypeException, InvalidDataValue, DataOutOfBounds {
		BufferedReader br = null;
		String line = "";
		String delim = "";
		String fileType = "Unknown";
		double curr;
		
		final File file = new File(fileName);
		
		if (this.data.isEmpty()) {
			this.history.add(new Action(0));
		} else {
			this.history.add(new Action(2));
		}
		
		try {
			
			fileType = Files.probeContentType(file.toPath());
			if (!fileType.contentEquals("text/plain"))
				delim = ",";
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			throw new InvalidFileTypeException("File type not supported.");
		}
		
		try {
			br = new BufferedReader(new FileReader(fileName));
			
			while ((line = br.readLine()) != null) {

				if (!delim.equals("")) {
					String[] lineRead = line.split(delim);
					for (int i = 0; i < lineRead.length; i++) {
						if (!lineRead[i].equals("")) {
							try {
								curr = Double.parseDouble(lineRead[i]);							
								if(isWithinBoundaries(curr)) {
									data.add(curr);
								} else {
									throw new DataOutOfBounds(curr + " is not within the current boundaries.");
								}
							} catch(DataOutOfBounds e) {}
						}
					}
				} else {
					try {
						curr = Double.parseDouble(line);
						if(isWithinBoundaries(curr)) {
							data.add(curr);
						} else {
							throw new DataOutOfBounds(curr + " is not within the current boundaries.");
						}
					} catch(DataOutOfBounds e) {}
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
	throws InvalidBoundaries, DataOutOfBounds {
		
		int dataOutOfBoundsCount = 0;
		
		if (upper < lower)
			throw new InvalidBoundaries("Upper bound must be higher than lower bound");
		if (upper - lower < 0.1) {
			throw new InvalidBoundaries("Bounds must differ by at least 0.1");
		}
		
		this.upperBound = upper;
		this.lowerBound = lower;
		
		//removes data that became out of bounds due to changing boundaries
		double curr;
		for(int i = data.size() - 1; i >= 0; i--) {
			
			if(!isWithinBoundaries(data.get(i))) {
				data.remove(i);
				dataOutOfBoundsCount++;
			}
		}
		if (dataOutOfBoundsCount > 0)
			throw new DataOutOfBounds(dataOutOfBoundsCount + " data points were removed due to it being outside of the new bounds.");
		
		history.add(new Action(1));
	}
	

	/**
	 * Tests if the number passed is within the preset boundaries.
	 * 
	 * @param test	Number to be checked
	 * @return	If the number is within the boundaries
	 */
	private boolean isWithinBoundaries(double test) {
		if(test >= lowerBound && test <= upperBound)
			return true;
		else
			return false;
	}
    
	public ArrayList<Double> getData() {
		return this.data;
	}
	
	public ArrayList<Double> displayData() {
		ArrayList<Double> reverseData = this.data;
		reverseData.sort(Comparator.reverseOrder());
		this.history.add(new Action(6));
		return this.data;
	}
	
	public void addData(double newData) 
	throws DataOutOfBounds {
		if (newData >= this.lowerBound && newData <= this.upperBound) {
			data.add(newData);
			this.sortData();
			
			history.add(new Action(3));
		} else {
			throw new DataOutOfBounds("Data point is out of set boundaries");
		}
		
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
	 * Values are stored in following indices: 
	 * 0: Minimum, 1: Maximum, 2: Mean, 3: Median, 4...n: Mode
	 * 
	 * @return ArrayList with values stored in specified order
	 */
	public ArrayList<Double> analyzeData() {
		ArrayList<Double> results = new ArrayList<Double>();
		
		results.add(this.minValue());
		results.add(this.maxValue());
		results.add(this.getMean());
		results.add(this.getMedian());
		results.addAll(this.getMode());
		
		history.add(new Action(5));
		
		return results;
	}
	
	public int getSize () {
		
		return data.size();
	}
	
	/**
	 * will find the maximum value of the current data
	 * 
	 * @return the maximum
	 */
	public double maxValue () {
		
		return Collections.max(data);
	}
	
	/**
	 * will find the minimum value of the current data
	 * 
	 * @return the minimum
	 */
	public double minValue () {
		
		return Collections.min(data);
	}
	
	/**  Will find the mean of the current data.
	 * 
	 * 
	 * @return the mean of the current data
	 */
	public double getMean ()
	{
		double total = 0;
		
		for ( int i =0; i < data.size(); i++)
		{
			total += data.get(i);
		}
		return total / data.size();
	}
	
	/**
	 * Will find the mode of the current data.
	 * 
	 * If more than one value is found for the mode, 
	 * all of them will be displayed
	 * 
	 * @return  mode of the current data
	 */
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
	 *  Will find the median of the current data.
	 * 
	 * <p>
	 * It assumes the data has already been sorted. If there is an even amount
	 * of entries it will return the average of the two middle numbers.
	 * </p>
	 * 
	 * @return	Median of the current data
	 */
	public double getMedian () {
		//index of the middle of the data
		int ix = data.size() / 2;
		double median;
		
		//checks if there is an even or odd amount of grades in data
		if(data.size() % 2 == 0) {
			//if even it averages the two grades in the middle of the data
			median = data.get(ix) + data.get(--ix);
			median /= 2;
		} else {
			median = data.get(ix);
		}
		
		return median;
	}
	
	/**
	 * Finds the number of entries in each 10% interval of the boundary range
	 * 
	 * Number of elements at a given interval is stored in that index. For example,
	 * for the default range of 0-100, the number of entries between 0 and 10 is
	 * stored in index 0, 10-20 is stored in index 1, etc.
	 * 
	 * @return ArrayList with 10 elements
	 */
	public ArrayList<Integer> numEntriesGraphData() {
		ArrayList<Integer> results = new ArrayList<Integer>();
		
		for (int i = 0; i <10; i++)
			results.add(0);
		
		double boundaryInterval = (this.upperBound - this.lowerBound) / 10.0;
		
		for (double point : this.data) {
			if (point >= this.upperBound) {
				results.set(9, results.get(9) + 1);
			} else {
				results.set((int)((point-this.lowerBound)/boundaryInterval), results.get((int)((point-this.lowerBound)/boundaryInterval))+1);
			}
		}
		
		this.history.add(new Action(7));
		
		return results;
	}
	
	public ArrayList<Double> avgGraphData() {
		ArrayList<Double> results = new ArrayList<Double>();
		ArrayList<Integer> counts = new ArrayList<Integer>();
		
		for (int i = 0; i <10; i++) {
			results.add(0.0);
			counts.add(0);
		}
		
		double boundaryInterval = (this.upperBound - this.lowerBound) / 10.0;
		
		for (double point : this.data) {
			if (point >= this.upperBound) {
				results.set(9,  results.get(9) + point);
				counts.set(9, counts.get(9) + 1);
			} else {
				results.set((int)((point-this.lowerBound)/boundaryInterval), (int)((point-this.lowerBound)/boundaryInterval)+point);
				counts.set((int)((point-this.lowerBound)/boundaryInterval), counts.get((int)((point-this.lowerBound)/boundaryInterval))+1);
			}
			
		}
		
		for (int i = 0; i < results.size(); i ++) {
			if (counts.get(i) != 0)
				results.set(i, results.get(i)/counts.get(i));
		}
		
		return results;
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
		content += "Boundaries: " + this.lowerBound + " --> " + this.upperBound + "\n";
		content += "\n\n*****HISTORY*****\n";
		for (Action i : history) {
			content += i.toString();
		}
		
		content += "\nEND OF REPORT";
		
		return content;
	}
	
	public void clearDataAndHistory() {
		this.data = new ArrayList<Double>();
		this.history = new ArrayList<Action>();
	}
	
	private void sortData() {
		data.sort(null);
	}
	
}


