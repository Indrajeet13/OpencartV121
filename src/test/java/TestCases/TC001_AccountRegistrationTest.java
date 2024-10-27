package TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {
	
	
	@Test(groups={"Regrression", "Master"})
	public void verify_account_registration()
	{
		logger.info("******Starting TC001_AccountRegistrationTest******");
		
		try {
			HomePage hp = new HomePage(driver);
			
			hp.clickMyAccount();
			logger.info("Clicked on MyAccount Link");
			
			hp.clickRegister();
			logger.info("Clicked on Register Link");
			
			AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
			
			logger.info("Provifing customer details...");
			regpage.setFirstName(randomeString().toUpperCase());
			//regpage.setFirstName("Eric");
			regpage.setLastName(randomeString().toUpperCase());
			//regpage.setLastName("David");
			regpage.setEmail(randomeString()+"@gmail.com");	//Automatically generate the email
			regpage.setTelephoneNum(randomeNumber());
			
			String password = randomeAlphaNumeric();
			
			regpage.setPassword(password);
			regpage.setConfirmPassword(password);
			
			regpage.setPrivacyPolicy();
			regpage.clickContinue();
			
			String confmsg = regpage.getConfirmationMsg();
			
			logger.info("Validating Expected Message");
			
			if(confmsg.equals("Your Account Has Been Created!"))
			{	
				Assert.assertTrue(true);
			}
			else {
				logger.error("Test Failed");
				logger.debug("Debug logs");
				Assert.assertTrue(false);
			}
		}
		catch(Exception e)
		{
			Assert.fail();
			
		}
		logger.info("******Finished Execution TC001_AccountRegistrationTest********");
		
	}

	
}
