import java.io.BufferedReader;
import java.io.IOException;

public class Protocol {	
	Protocol(){}
	public String processInput(String email, String input, String role, BufferedReader stdIn) {
		String query = null;
		switch(input){
		case "test-results":
			if(role.equals("user") || role.equals("test_center") || role.equals("test_center_admin")) {
				query = "SELECT * FROM test_results WHERE email = " + email + ";";
			} else {
				query = "PERMISSION_DNE";
			}
			
			break;
		case "record-results":
			if (role.equals("test_center") || role.equals("test_center_admin")) {
				//query = "INSERT INTO test_results (col1,col2,col3) VALUES(val1,val2,val3);";	//eventually get userinput
				query = enterTestResult(stdIn);
			} else {
				query = "PERMISSION_DNE";				
			}			
			break;
		default:
			query = "ERROR";
		}
						
		return query;
	}
	public String enterTestResult(BufferedReader stdIn) {
		String email, doctor, result, date;	//todo - format these, check for validity?
		try {
			System.out.println("Enter email of tested user: ");
			email = stdIn.readLine();	//Read user's input text
			System.out.println("Enter doctor who performed test: ");
			doctor = stdIn.readLine();	//Read user's input text
			System.out.println("Enter test result: ");
			result = stdIn.readLine();	//Read user's input text
			System.out.println("Enter date of result: ");
			date = stdIn.readLine();	//Read user's input text
		} catch(IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
		}
		String query = "INSERT INTO test_results (col1,col2,col3) VALUES(val1,val2,val3);";
		return query;
	}
}
