package TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC002_AccountLoginTest extends BaseClass{
	
	@Test(groups={"Sanity", "Master"})
	public void verify_account_Login()
	{
		logger.info("******Starting TC002_AccountLoginTest******");
		
		try {
			
			//Home Page
			HomePage hp = new HomePage(driver);	
			hp.clickMyAccount();
			hp.clickLogin();
			
			//LoginPage
			LoginPage loginpage = new LoginPage(driver);
			loginpage.setEmailAddress(p.getProperty("email"));
			loginpage.setPassword(p.getProperty("password"));
			loginpage.clickLoginBtn();
			
			//MyAcoount
			MyAccountPage macc = new MyAccountPage(driver);
			boolean targetPage = macc.isMyAccountPageExist();
			
			Assert.assertTrue(targetPage); //Assert.assertEquals(targetPage, true, "Login Failed");
		}
		catch(Exception e)
		{
			Assert.fail();
			
		}
		logger.info("******Finished Execution TC002_AccountLoginTest********");
		
	}
	
	
}
