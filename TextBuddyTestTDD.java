/*
TDD 
*/

import org.junit.*;
import java.io.File;
import java.util.Vector;



public class TextBuddyTestTDD {
		
	
	@Test
	public void testSearch() {
	
		Vector<String> Test = new Vector<String>();
		
		String testStringI = "1. BIZ BSP2001";
		String testStringII = "4. BIZ FIN2004";
	
		Test.add(testStringI);
		Test.add(testStringII);
	
		String fileName= "Search.txt" ;
	
		TextBuddy testFile =new TextBuddy(fileName);
		File textBuddy = new File();
		testFile.addItem("BIZ BSP2001",textBuddy); 
		testFile.addItem("Tembusu IEM2201P",textBuddy);
		testFile.addItem("Tembusu GEM2902",textBuddy);
		testFile.addItem("BIZ FIN2004",textBuddy);
    
		Vector<String> temp = testFile.searchItem("BIZ");
	
		System.out.println(temp);
		System.out.println(Test);
		System.out.println(temp.equals(Test));
	
		assertEquals(temp,Test); //compare the output and expected
	
		testFile.clearItem(textBuddy);
	}

	

	@Test
	public void testSort() {

		Vector<String> Test = new Vector<String>();
	
		String testStringI = "BSP2001";
		String testStringII = "CS2103";
		String testStringIII = "FIN2004";
	
		Test.add(testStringI);
		Test.add(testStringII);
		Test.add(testStringIII);
	
		String fileName= "Sort.txt" ;
	
		TextBuddy testFile = new TextBuddy(fileName);
		File textBuddy = new File();
		testFile.displayFile();
		testFile.addItem("CS2103",textBuddy); 
		testFile.addItem("FIN2004",textBuddy);
		testFile.addItem("BSP2001",textBuddy);
	  
		Vector<String> temp = testFile.sortItem(textBuddy);
	
		assertEquals(temp,Test); //compare the output and expected
	
		testFile.clearItem(textBuddy);
	} 

}


