package pt.uminho.ceb.biosystems.jecoli.jUnitsTests.testsReader;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import pt.uminho.ceb.biosystems.jecoli.jUnitsTests.ITest;

public class TestsStatistics {

	private int numberTests;
	private ArrayList<String> outputLines;
	private String fileName = "testsResults.log";
	
	public TestsStatistics()
	{
		this.numberTests = 0;
		this.outputLines = new ArrayList<String>();
	}
	
	public TestsStatistics(String filename)
	{
		this.numberTests = 0;
		this.outputLines = new ArrayList<String>();
		this.fileName = filename;
	}
	
	public void addSuccessedTest(ITest test)
	{
		String line = test.getTestFileName()+"\t"+test.getName()+"\t\t"+"SUCCESS";
		this.outputLines.add(line);
		this.numberTests++;
	}
	
	public void addFailedTest(ITest test)
	{
		String line = test.getTestFileName()+"\t"+test.getName()+"\t\t"+"SUCCESS";
		this.outputLines.add(line);
		this.numberTests++;
	}
	
	public void addFailedTest(String fileName,String testName,String errorMessage)
	{
		String line = fileName+"\t"+testName+"\t\t"+errorMessage+"\t\tFAILED";
		this.outputLines.add(line);
		this.numberTests++;
	}
	
	public void outputStats()
	{
		try{ 
		    FileWriter fstream = new FileWriter(this.fileName);
		    BufferedWriter out = new BufferedWriter(fstream);
		    
		    out.write(getCurrentDate()+"\n");
		    out.write("Total number of tests: " + this.numberTests + "\n");
		    
		    out.write("File"+"\t\t"+"Type"+"\t\t"+"Status\n");
		    
		    for(String s : this.outputLines)
		    	out.write(s+"\n");
		    
		    out.close();
	    }
		catch (Exception e){
		      System.err.println("Error: " + e.getMessage());
		}
	}
	
	private String getCurrentDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
	}
}
