/*
Due to TDD testing, a separate class needs to be created to contain all the methods for testing as
non-static methods as I couldn't figure our a way to test the methods when they were static ones.
*/

import java.util.*;
import java.io.*;


public class TextBuddyMethods{
	
	//messages for possible errors	
	private static final String ERROR_ADD="Exception in add function";
	private static final String ERROR_DELETE="Exception in delete function";
	private static final String ERROR_DISPLAY="Exception in display function";
	private static final String ERROR_CLEAR="Exception in clear function";
	private static final String ERROR_SORT= "Exception in sort function";
	private static final String ERROR_SEARCH="Exception in search function";
	
	//messages for users
	private static final String MESSAGE_ADD = "added to %1$s :\"%2$s\" ";
	private static final String MESSAGE_DELETE = "deleted from %1$s: \" %2$s\"";
	private static final String MESSAGE_CLEAR = "all content deleted from %1$s";
	private static final String MESSAGE_EMPTY = "File is empty.";
	private static final String MESSAGE_DISPLAY = "%1$d. %2$s";
	private static final String MESSAGE_SORT = "Content Sorted!";
	
	private File textBuddy;
	
	private Vector<String> textBuddyVector = new Vector<String>();
	
	//constructor for TextBuddyMethods class created due to TDD
	TextBuddyMethods(String fileName){
		
		textBuddy = new File(fileName);
	}

	//read data from .txt file
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
			writeToFile();
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
				writeToFile();
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
			writeToFile();
		} catch (Exception e) {
			System.out.println(ERROR_CLEAR + e.getMessage());
		}
	}
	
	//search items in the file according to input keyword and display item(s) contain it
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
	
	//sort items in the file according to default Collections class sort
	public Vector<String> sortItem() {
		try {
			Collections.sort(textBuddyVector);
			writeToFile();
			System.out.println(String.format(MESSAGE_SORT));	
			return textBuddyVector;
		}

		catch (Exception e) {
			System.out.println(ERROR_SORT + e.getMessage());
		}
		return null;
	}
}