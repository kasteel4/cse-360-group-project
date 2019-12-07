import java.time.LocalDateTime;

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
	
	/**
	 * General constructor.
	 */
	public Action() {
	}
	
	public Action(int type) {
		if (type >= 0 && type <= 7) {
			actionType = type;
		} else {
			System.out.println("Invalid action type; cannot set private variable.");
		}
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
		String str = "";
		switch (actionType) {
		case 0:
			str += "--Loaded File\n";
			break;
		case 1:
			str = "--Set Boundaries\n";
			break;
		case 2:
			str = "--Appended Data\n";
			break;
		case 3:
			str = "--Added Data Point\n";
			break;
		case 4:
			str = "--Deleted Data Point\n";
			break;
		case 5:
			str = "--Analysis run on data\n";
			break;
		case 6:
			str = "--Grade data displayed\n";
			break;
		case 7:
			str = "--Distribution graphs displayed\n";
			break;
		}
		
		return str;
	}
	
}
