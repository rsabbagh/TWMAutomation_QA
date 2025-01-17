package com.totalwine.test.aml;

/*
 * Web Account Registration Workflow
 * Workflow:
 * 	1. Click the "Account" link in top nav
 * 	2. Assert the presence of all links in the popup screen
 * 	3. Click the "Sign Up" link and navigate to the Registration screen
 * 	4. Complete the form with registration information and submit the form
 * 	5. Validate the presence of elements on the registration confirmation screen
 * 	6. Complete preferences and save the information
 *  7. Validate the elements presented on the Account home page.
 *  8. Log out and attempt to re-login using the same credentials
 *  9. Validate the logged in state ensuring the credentials work.
 *  
 * Technical Modules:
 * 	1. BeforeMethod (Test Pre-requisites):
 * 			Invoke webdriver
 * 			Maximize browser window
 * 	2. Test (Workflow)
 * 	3. AfterMethod
 * 			Take screenshot, in case of failure
 * 			Close webdriver
 * 	4. AfterClass
 * 			Quit webdriver
 */


import java.io.IOException;
import java.util.Random;

import jxl.read.biff.BiffException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.totalwine.test.config.ConfigurationFunctions;
import com.totalwine.test.trials.Browser;

public class EmailSignup extends Browser {
	
	@DataProvider(name="EmailSignup")
    public Object[][] createData() {
    	Object[][] retObjArr=ConfigurationFunctions.getTableArray(ConfigurationFunctions.resourcePath,"EmailSignup", "emailaddresses");
        return(retObjArr);
    } 
	
	@BeforeMethod
	  public void setUp() throws Exception {
	    driver.manage().window().maximize();
	}  
	
	@Test //(dataProvider = "EmailSignup") //Existing Email Address
	public void EmailSignupTest () throws InterruptedException, BiffException, IOException {
		//String [] emailAddresses = {"automate1@totalwine.com","automate2@totalwine.com","automate3@totalwine.com","automate4@totalwine.com"};
		logger=report.startTest("Email Signup Test");
		driver.get(ConfigurationFunctions.locationSet+"71.193.51.0");
		Thread.sleep(2000);
		driver.findElement(By.id("btnYes")).click();
		Thread.sleep(2000);
	    driver.findElement(By.cssSelector("#email-signup-overlay-new-site > div.modal-dialog > div.modal-content > div.modal-body > p.close > a.btn-close")).click();
	    Thread.sleep(2000);
    	driver.findElement(By.cssSelector("span.footer-Email-text.analyticsJoinOurEmail")).click();
    	Thread.sleep(2000);
    	driver.switchTo().frame(driver.findElement(By.id("iframe-signup-overlay")));
	    driver.findElement(By.id("emailAnonomous")).clear();
	    driver.findElement(By.id("emailAnonomous")).sendKeys("automate1@totalwine.com");
	    driver.findElement(By.id("checkEmailAnonomous")).clear();
	    driver.findElement(By.id("checkEmailAnonomous")).sendKeys("automate1@totalwine.com");
	    //driver.findElement(By.cssSelector("label")).click();
	    driver.findElement(By.id("check_box_100")).click();
	    driver.findElement(By.id("emailuserregister")).click();
	    /*Actions action = new Actions(driver);
	    //action.moveToElement(driver.findElement(By.id("emailuserregister"))).doubleClick().build().perform(); //Double-click
	    action.moveToElement(driver.findElement(By.id("emailuserregister"))).click();*/
	    Thread.sleep(3000);
	    Assert.assertEquals("The email provided matches an existing account. Please try again.", driver.findElement(By.cssSelector("div.email-container-signin > div.notice")).getText());
	}
	
	@Test //New Email Address
	public void NewEmailSignupTest () throws InterruptedException {
		Random rand = new Random();
	    int randomNum = rand.nextInt((1000 - 1) + 1) + 1;
	    int randomNum2 = rand.nextInt((1000 - 1) + 1) + 1;
		driver.get(ConfigurationFunctions.locationSet+"71.193.51.0");
		Thread.sleep(2000);
		driver.findElement(By.id("btnYes")).click();
		Thread.sleep(2000);
	    driver.findElement(By.cssSelector("#email-signup-overlay-new-site > div.modal-dialog > div.modal-content > div.modal-body > p.close > a.btn-close")).click();
	    Thread.sleep(2000);
    	driver.findElement(By.cssSelector("span.footer-Email-text.analyticsJoinOurEmail")).click();
    	Thread.sleep(2000);
    	driver.switchTo().frame(driver.findElement(By.id("iframe-signup-overlay")));
    	Thread.sleep(2000);
	    driver.findElement(By.id("emailAnonomous")).clear();
	    driver.findElement(By.id("emailAnonomous")).sendKeys("test"+randomNum+"_"+randomNum2+"@totalwine.com");
	    driver.findElement(By.id("checkEmailAnonomous")).clear();
	    driver.findElement(By.id("checkEmailAnonomous")).sendKeys("test"+randomNum+"_"+randomNum2+"@totalwine.com");
	    //driver.findElement(By.cssSelector("label")).click();
	    driver.findElement(By.id("check_box_100")).click();
	    driver.findElement(By.id("emailuserregister")).click();
	    /*Actions action = new Actions(driver);
	    //action.moveToElement(driver.findElement(By.id("emailuserregister"))).doubleClick().build().perform(); //Double-click
	    action.moveToElement(driver.findElement(By.id("emailuserregister"))).click();*/ 
	    //Thread.sleep(3000);
	    //Assert.assertEquals("Thank you for signing up to receive emails from Total Wine & More!", driver.findElement(By.cssSelector("#email-signup-overlay-success > div.modal-dialog > div.modal-content > div.modal-body > div.heading-h1")).getText());
	}
}
