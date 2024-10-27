package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders 
{
	//Data Provider 1
	
	@DataProvider(name = "LoginData")
	public String[][] getData() throws IOException 
	{
	    String path = System.getProperty("user.dir")+"\\testData\\Opencart_LoginData.xlsx";  //taking xl file from testdata
	    
	    ExcelUtility xlutil = new ExcelUtility(path);  // Creating ExcelUtility object

	    int totalrows = xlutil.getRowCount("Sheet1");  // Total number of rows (excluding the header)
	    int totalcols = xlutil.getCellCount("Sheet1", 1);  // Total number of columns in row 1

	    System.out.println("Total Rows: " + totalrows);
	    System.out.println("Total Columns: " + totalcols);

	    String logindata[][] = new String[totalrows][totalcols];  // Array to store login data

	    for (int i = 1; i <= totalrows; i++)	// Start from 1 to skip the header row
	    {  
	        for (int j = 0; j < totalcols; j++) 
	        {
	            logindata[i - 1][j] = xlutil.getCellData("Sheet1", i, j);  // Fetch data and store it in the array
	            System.out.println("Data [" + (i - 1) + "][" + j + "]: " + logindata[i - 1][j]);
	        }
	    }

	    return logindata;  // Return the data(two dimension array) to the test method
	}
}