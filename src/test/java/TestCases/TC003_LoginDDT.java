package TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

/*
Data is valid - Login success - test PASS - Logout 
			   - Login failed - test FAIL
			   
Data is invalid - Login success - test FAIL - Logout 
			   	- Login failed - test PASS
*/

public class TC003_LoginDDT extends BaseClass{
	
	
	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups={"Sanity", "Master", "dataDriven"} )	//getting data provider from different class
	public void verify_LoginDDT(String email, String pwd, String expRes)
	{
		logger.info("******Starting TC003_LoginDDT ******");
		
		try
		{
		//HomePage
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//LoginPage
		LoginPage lp = new LoginPage(driver);
		lp.setEmailAddress(email);
		lp.setPassword(pwd);
		lp.clickLoginBtn();
	
		//MyAcoount
		MyAccountPage macc = new MyAccountPage(driver);
		boolean targetPage = macc.isMyAccountPageExist();
		
		if(expRes.equalsIgnoreCase("Valid"))
		{
			if(targetPage ==true)
			{
				Assert.assertTrue(true);
				macc.clickLogoutBtn();
			}
			else
			{
				Assert.assertTrue(false);
			}
		}

		if(expRes.equalsIgnoreCase("Invalid"))
		{
			if(targetPage ==true)
			{
				macc.clickLogoutBtn();
				Assert.assertTrue(false);
			}
			else
			{
				Assert.assertTrue(true);
			}
		}
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		
		logger.info("******Starting TC003_LoginDDT ******");
		
		
		
	}
	
	
}
