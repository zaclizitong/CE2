/*
This program is a Command Line Interface program called TextBuddy
Name: LI ZITONG
Matric Number: A0115635J
Tutorial Group: 10
CE2: The architecture of the prgramme changed from CE1 due to TDD
*/

import java.util.*;
import java.io.*;

public class TextBuddy {
	
	
	private static final String WELCOME_MESSAGE = "Welcome to TextBuddy. %1$s is ready for use";
	
	//messages for possible errors
	private static final String ERROR_READ_INPUT="Unable to extract user command from input. System Error: ";
	private static final String ERROR_INPUT="Error getting user command. System Error: ";
	private static final String ERROR_PROCESS_COMMAND ="Error executing the command: ";
	private static final String ERROR_FILE = "Error (File exists)";
	
	//varibles to read inout
	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private static String[] inputVector;
	private static final int splitCommandFromInput = 2; 
	private static final int numberOfArguments=1;
	

	//Definition of variables for user commands
	enum OPTION_TYPE{
		ADD, DELETE, CLEAR, DISPLAY, EXIT, SEARCH, SORT, INVALID
	};
	
	//create the TextBUddyMethods to use
	private static TextBuddyMethods textBuddyMethods;
	
	public static void main(String args[]){
		String fileName = args[0];
		printMessage(fileName);
		executeCommand(fileName);
		System.exit(0);
			
	}
	

	
	//print welcome message
	public static void printMessage(String fileName){
		System.out.println(String.format(WELCOME_MESSAGE, fileName));
	}
	
	//function to create the .txt file based on the name user has chosen
	public static void createFile(File textbuddy){
		try{
			if(!textbuddy.exists()){
				textbuddy.createNewFile();
			} 
		} catch (Exception e){
			System.err.println(ERROR_FILE + e.getMessage());
		}
	}
	
	
/*****************************************************************************************************/	
	//this section contains methods to read, process and execute user commands
	public static void executeCommand(String fileName){	
		
		File textBuddy = new File(fileName);
		createFile(textBuddy);
	
		OPTION_TYPE userCommand=null;
		textBuddyMethods = new TextBuddyMethods(fileName);

		do{
			userCommand=readCommand();
			processCommand(userCommand, textBuddyMethods);
		}
		while(userCommand!= userCommand.EXIT);
		
	}
		
	//to get the user input and extract the command
	public static OPTION_TYPE readCommand(){
		
		String input;
		OPTION_TYPE choice=null;
		
		try{				
			System.out.print("command: ");
			input=in.readLine();	
			input=input;
			inputVector=input.split(" ", splitCommandFromInput); 
			String userInputCommand=inputVector[0];
				try
	      {
	        choice = OPTION_TYPE.valueOf(userInputCommand.toUpperCase());
	      }
	      catch(IllegalArgumentException ex)
	      {
	      	System.out.println(ERROR_READ_INPUT+ex.getMessage());
	      	return choice.INVALID;
	      }
			return choice;
		}
		
		catch(IOException e){
			System.out.println(ERROR_INPUT + e.getMessage());
    	return choice.INVALID;
		}
		
	}
		
	//to process the command and call the methods respectively
	public static void processCommand(OPTION_TYPE command, TextBuddyMethods textBuddyMethods){
		
 		try{	
 			
	 		switch(command){
		
	 		case EXIT:
					break;
				
	 		case ADD:
					textBuddyMethods.addItem(inputVector[1]);
					break;	
				
	 		case DELETE:
					int index= Integer.parseInt(inputVector[1]); 
					textBuddyMethods.deleteItem(index);
					break;
				
	 		case CLEAR:
					textBuddyMethods.clearItem();
					break;
				
	 		case DISPLAY:
					textBuddyMethods.displayFile();
					break;
			case SEARCH:
			 	Vector<String> search = textBuddyMethods.searchItem(inputVector[1].trim());
					for(int i=0;i<search.size();i++)
					{
						System.out.println(search.elementAt(i));
					}
					break;

			 case SORT:
		 			textBuddyMethods.sortItem(); 
					break;		
	 		}
 		}
	
 		catch(Exception e){
 			System.out.println(ERROR_PROCESS_COMMAND + e.getMessage());
 		}
	}
	
/*****************************************************************************************************/
		
}