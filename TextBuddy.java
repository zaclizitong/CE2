/*
This program is a Command Line Interface program called TextBuddy
Name: LI ZITONG
Matric Number: A0115635J
Tutorial Group: 10
*/

import java.util.*;
import java.io.*;

public class TextBuddy {
	
	//all messages for user
	private static final String WELCOME_MESSAGE = "Welcome to TextBuddy. %1$s is ready for use";
	
	//messages for possible errors
	private static final String ERROR_READ_INPUT="Unable to extract user command from input. System Error: ";
	private static final String ERROR_INPUT="Error getting user command. System Error: ";
	private static final String ERROR_PROCESS_COMMAND ="Error executing the command: ";
	
	//varibles to read inout
	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private static String[] inputVector;
	private static final int splitCommandFromInput = 2; 
	private static final int numberOfArguments=1;
	

	//Definition of variables for user commands
	enum OPTION_TYPE{
		ADD, DELETE, CLEAR, DISPLAY, EXIT, SEARCH, SORT, INVALID
	};
	

	private static Vector<String> textBuddyVector = new Vector<String>();
	
	public static void main(String args[]){
		String fileName = args[0];
		printMessage(fileName);
		executeCommand(fileName, textBuddy);
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
	public void executeCommand(String fileName, File textBuddy){
		
		File textBuddy = new File(fileName);
		createFile(textBuddy);
		
		OPTION_TYPE userCommand=null;

		do{
			userCommand=readCommand();
			processCommand(userCommand, textBuddy);
		}
		while(userCommand!= userCommand.EXIT);
		
	}
		
	//to get the user input and extract the command
	private static OPTION_TYPE readCommand(){
		
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
	private static void processCommand(OPTION_TYPE command, File textBuddy){
		
 		try{	
 			
	 		switch(command){
		
	 		case EXIT:
					break;
				
	 		case ADD:
					textBuddy.addItem(inputVector[1]);
					break;	
				
	 		case DELETE:
					int index= Integer.parseInt(inputVector[1]); 
					textBuddy.deleteItem(index);
					break;
				
	 		case CLEAR:
					textBuddy.clearItem();
					break;
				
	 		case DISPLAY:
					textBuddy.displayFile();
					break;
			case SEARCH:
					Vector<String> search = textBuddy.searchItem(inputVector[1].trim());
					for(int i=0;i<search.size();i++)
					{
						System.out.println(search.elementAt(i));
					}
					break;

			case SORT:
				 	textBuddy.sortItem(); 
					break;
					
	 		}
 		}
	
 		catch(Exception e){
 			System.out.println(ERROR_PROCESS_COMMAND + e.getMessage());
 		}
	}
	
/*****************************************************************************************************/
		
}