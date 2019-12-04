import java.time.LocalDateTime;;

/**
 * Action object for maintaining history of user action
 * 
 * @author Keenan Steele
 */
public class Action {
	
	/**
	 * actionType keys:
	 * 0 :: Load File
	 * 1 :: Set Boundaries
	 * 2 :: Append Data
	 * 3 :: Add Data
	 * 4 :: Delete Data
	 * 5 :: Analyze
	 * 6 :: Display Grades
	 * 7 :: Display Graphs
	 */
	private int actionType;
	private LocalDateTime timestamp;
	
	/**
	 * General constructor.
	 * Sets timestamp to current data/time according
	 * to user's systems
	 */
	public Action() {
		timestamp = LocalDateTime.now();
	}
	
	/**
	 * Getter method for ActionType
	 * @return actionType Value of the private actionType variable
	 */
	public int getActionType() {
		return actionType;
	}
	
	/**
	 * Setter method for actionType
	 * 
	 * @param type Only accepts values 0 through 7
	 */
	public void setActionType(int type) {
		if (type >= 0 && type <= 7) {
			actionType = type;
		} else {
			System.out.println("Invalid action type; cannot set private variable.");
		}
	}
	
	/**
	 * ToString method for Action object. 
	 * Generates string containing time stamp and brief description
	 * of the action type.
	 */
	public String toString() {
		String str = timestamp.toString();
		switch (actionType) {
		case 0:
			str += " : Loaded File";
			break;
		case 1:
			str = " : Set Boundaries";
			break;
		case 2:
			str = " : Appended Data";
			break;
		case 3:
			str = " : Added Data Point";
			break;
		case 4:
			str = " : Deleted Data Point";
			break;
		case 5:
			str = " : Analysis run on data";
			break;
		case 6:
			str = " : Grade data displayed";
			break;
		case 7:
			str = " : Distribution graphs displayed";
			break;
		}
		
		return str;
	}
	
}
