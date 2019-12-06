package errors;
/**
 * 
 * @author Keenan Steele
 *
 * Custom exception for invalid file types.
 */
public class InvalidFileTypeException extends Exception{

	public InvalidFileTypeException(String message) {
		
		super(message);
		
	}
	
}
