package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener
{
	public ExtentSparkReporter sparkReporter;	
	public ExtentReports extent;	
	public ExtentTest test;		
	
	String repName;
	
	public void onStart(ITestContext testContext) {
		
		/*
		 SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		 Date dt = new Date();
		 String currentDateTimeStamp = df.format(dt);
		 */
		
		String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		
		repName = "Test-Report" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\"+repName); //specify location of the 
		
		sparkReporter.config().setDocumentTitle("OpenCart Automation Report"); //Title of report
		sparkReporter.config().setReportName("OpenCart Functional Testing"); //Name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		
		extent.setSystemInfo("Application", "OpenCart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Enviroment", "QA");
		
		String os = testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		
		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("browser", browser);
		
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString());
		}
	}
	
	public void onTestSuccess(ITestResult result) {
		
		test = extent.createTest(result.getName());	//create new entry in the report
		test.assignCategory(result.getMethod().getGroups()); //to display groups in reports
		test.log(Status.PASS, result.getName() + " got succesfully Executed");	//Update Status p/f/s
		
	}
	
	public void onTestFailure(ITestResult result) {
		 
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.FAIL, result.getName() +" got failed"); 
		test.log(Status.INFO, result.getThrowable().getMessage()); //Get the error message		
		
		try
		{
			String imgPath = new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		}
		catch(IOException e1)
		{
			e1.printStackTrace();
		}
	}
	
	public void onTestSkipped(ITestResult result)
	{	 
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName() +" got skipped ");
		test.log(Status.INFO, result.getThrowable().getMessage()); 
	}
	

	public void onFinish(ITestContext testContext) 
	{
		 extent.flush();
		 
		 //Automatically report will open after execution gets completed
		 String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\" + repName;
		 File extentReport = new File(pathOfExtentReport);
		
		 try {
			 Desktop.getDesktop().browse(extentReport.toURI());
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	}
	
	
}
