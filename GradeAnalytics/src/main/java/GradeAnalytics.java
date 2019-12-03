import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GradeAnalytics {
	
	public static void main(String[] args) {
		
		String fileName = "/Users/Keenan/Desktop/testData.txt";
		
		ArrayList<Double> data = new ArrayList<Double>();
		try {
			data = parseFile(fileName, 0);
		} catch (InvalidFileTypeException e) {
			System.out.println(e.getMessage());
		}
		
		for (double i : data) {
			System.out.println(i);
		}
	}
	
	private static ArrayList<Double> parseFile(String fileName, int fileType)
	throws InvalidFileTypeException {
		//.txt file is a fileType of 0
		//.csv file is a fileType of 1
		//No other file types are supported
		BufferedReader br = null;
		String line = "";
		
		ArrayList<Double> data = new ArrayList<Double>();
		
		try {
			String delim;
			if (fileType == 0) {
				delim = " ";
			} else if (fileType == 1) {
				delim = ",";
			} else {
				throw new InvalidFileTypeException("Not a supported file type.");
			}
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
			e.printStackTrace();
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
	
}
