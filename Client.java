import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
	public static void main( String[] args ) {
		boolean debug = true;
		String hostname = "127.0.0.1";
		int port = 8888;
		try (Socket socket = new Socket(hostname, port)) {
			 
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            boolean userExists = true;
            String inputString, query, email, pw, role, serverOutput;
            System.out.print("Enter username/email: ");
            email = stdIn.readLine();	//Read user's email
            System.out.print("Enter password: ");
            pw = stdIn.readLine();	//Read user's password
            
            //Check if user/password combination exists
            //Set role based on what server returns
            writer.println("Email: "+email+", PW: "+pw);
            System.out.println(reader.readLine());
            
            if (debug)
            	role = "test_center_admin";
            else
            	role = reader.readLine();
            
            
            Protocol p = new Protocol();
            if (userExists) {
                do {
                	System.out.println("Enter command: ");	//Prompt user
                	inputString = stdIn.readLine();	//Read user's input text
                    
                	if (inputString.equals("exit")) {
                		writer.println(inputString);
                		break;
                	}
                	
                	query = p.processInput(email, inputString, role, stdIn);
                	if (query.equals("ERROR")) {
                		System.out.println("ERROR: Bad command. Please try again.");
                	} else if (query.equals("PERMISSION_DNE")) {
                		System.out.println("ERROR: Role "+role+" doesn't have permission to do that.");
                	} else {               		
                		writer.println(query);	//Write to reader in server          		
                		
                		serverOutput = reader.readLine();
                    
                		System.out.println(serverOutput);	//Get line from writer in server
                	}
                } while (!inputString.equals("exit"));        	
            	
            } else {
            	System.err.println("ERROR: Email and/or password not valid");
            	socket.close();
            }
 
            socket.close(); 
        } catch (UnknownHostException ex) { 
            System.out.println("Server not found: " + ex.getMessage());
         } catch (IOException ex) {
             System.out.println("I/O error: " + ex.getMessage());
        }
    }
}
