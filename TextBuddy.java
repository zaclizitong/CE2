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
	private static final String ERROR_FILE = "Error (File exists)";
	private static final String ERROR_ADD="Exception in add function";
	private static final String ERROR_DELETE="Exception in delete function";
	private static final String ERROR_DISPLAY="Exception in display function";
	private static final String ERROR_CLEAR="Exception in clear function";
	
	private static final String MESSAGE_ADD = "added to %1$s :\"%2$s\" ";
	private static final String MESSAGE_DELETE = "deleted from %1$s: \" %2$s\"";
	private static final String MESSAGE_CLEAR = "all content deleted from %1$s";
	private static final String MESSAGE_EMPTY = "File is empty.";
	private static final String MESSAGE_DISPLAY = "%1$d. %2$s";
	
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
	
	//read data from the .txt file
	public void readFromFile() throws Exception {	
		
		FileInputStream fstream = new FileInputStream(textBuddy);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		String readFromFile; 

		while ((strLine = br.readLine()) != null) { 
			textBuddyVector.add(strLine);
		}
		in.close();
	}
	//write data back to .txt file
	public void writeToFile() throws Exception {
		textBuddy.delete();
		textBuddy.createNewFile();
		String newLine="\n";
		BufferedWriter outFile = new BufferedWriter(new FileWriter(textBuddy));

		for (int i = 0; i < textBuddyVector.size(); i++) {
			outFile.write(textBuddyVector.elementAt(i) + newLine);   
		}

		outFile.close();
	}
	
	//add item to the vector and write to the .txt file
	public void addItem(String userInput){
		try {
			textBuddyVector.add(userInput);
			textBuddy.writeToFile();
			System.out.println(String.format(MESSAGE_ADD, textBuddy, userInput));
		} catch (Exception e) {
			System.out.println(ERROR_ADD + e.getMessage());
		}
	}
	
	//delete item from the .txt file
	public void deleteItem(int deleteIndex){
			try {
				String strToDelete;
				strToDelete = new String(textBuddyVector.elementAt(deleteIndex - 1).toString());   
				textBuddyVector.remove(deleteIndex - 1);
				textBuddy.writeToFile();
				System.out.println(String.format(MESSAGE_DELETE, textBuddy,
						strToDelete));
			} catch (Exception e) {
				System.out.println(ERROR_DELETE + e.getMessage());
			}
	}
	
	//display elements in the .txt file
	public void displayFile(){
		try {
			if(textBuddyVector.size()==0)
				System.out.println(MESSAGE_EMPTY);
			for (int i = 0; i < textBuddyVector.size(); i++) {
				System.out.println(String.format(MESSAGE_DISPLAY, (i + 1),
						textBuddyVector.elementAt(i)));
			}
		} catch (Exception e) {
			System.out.println(ERROR_DISPLAY + e.getMessage());
		}
	}
	
	//clear everything in the .txt file
	public void clearItem(){
		try {
			textBuddyVector.removeAllElements();
			System.out.println(String.format(MESSAGE_CLEAR, textBuddy));
			textBuddy.writeToFile();
		} catch (Exception e) {
			System.out.println(ERROR_CLEAR + e.getMessage());
		}
	}
	
	public Vector<String> searchItem(String searchString) {
		try {
			int flag = 0;
			Vector<String> searchVector=new Vector<String>(); 
			for (int i = 0; i < textBuddyVector.size(); i++) {
				String temp = textBuddyVector.elementAt(i).toString().toLowerCase(); 
				if (temp.contains(searchString.toLowerCase())) {
					flag = 1;
					searchVector.add((i+1)+". "+textBuddyVector.elementAt(i)); 
				} 
			}
			
			if (flag == 0)
				System.out.println("String Not Found!");
			
			return searchVector;

		} catch (Exception e) {
			System.out.println(ERROR_SEARCH + e.getMessage());
		}
		return null;
	}
	
	public Vector<String> sortItem() {
		try {
			Collections.sort(textBuddyVector);
			textBuddy.writeToFile();
			System.out.println(String.format(MESSAGE_SORT));	
			return textBuddyVector;
		}

		catch (Exception e) {
			System.out.println(ERROR_SORT + e.getMessage());
		}
		return null;
	}
		
}