package com.totalwine.test.events;

/*
 * Browse Event Workflow
 * Workflow:
 * 	1. Click the "Classes & Events" link from the top level nav
 * 	2. Assert the presence of web elements on the Events landing page
 * 	3. Click on the first event displaying on the Events landing page, navigating to the Events detail page.
 * 	4. Assert the presence of web elements on on the Events detail page.
 *  
 * Technical Modules:
 * 	1. BeforeMethod (Test Pre-requisites):
 * 			Invoke webdriver
 * 			Maximize browser window
 * 	2. Test (Workflow)
 * 	3. AfterMethod
 * 			Take screenshot
 * 			Close webdriver
 * 	4. AfterClass
 * 			Quit webdriver
 */

import org.testng.*;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;

import com.totalwine.test.config.ConfigurationFunctions;
import com.totalwine.test.trials.Browser;


public class BrowseEvent extends Browser {
	
	private String IP="71.193.51.0";
	//private String IP="199.241.138.201";
	
	@BeforeMethod
	  public void setUp() throws Exception {
	    driver.manage().window().maximize();
	  }  
	
	@Test 
	public void BrowseEventTest () throws InterruptedException {
		logger=report.startTest("Browse Events Test");
		driver.get(ConfigurationFunctions.locationSet+IP);
		Thread.sleep(5000);
		driver.findElement(By.id("btnYes")).click();
		Thread.sleep(5000);
	    driver.findElement(By.cssSelector("#email-signup-overlay-new-site > div.modal-dialog > div.modal-content > div.modal-body > p.close > a.btn-close")).click();
	    Thread.sleep(5000);

	    driver.findElement(By.linkText("Classes & Events")).click();
	    Thread.sleep(3000);
	    Assert.assertEquals(driver.findElements(By.cssSelector("div.event-title")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Stores")).isEmpty(),false);
	    //Assert.assertEquals(driver.findElements(By.linkText("Event Type")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Event Type")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Event Focus")).isEmpty(),false);
	    //Assert.assertEquals(driver.findElements(By.linkText("Past Events")).isEmpty(),false);
	    driver.findElement(By.cssSelector("a.analyticsEventName")).click();
	    Thread.sleep(3000);
	    Assert.assertEquals(driver.findElements(By.cssSelector("section.store-right-hours-tasting > div.search-result-list-buy-ctrls")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("section.event-testing-profile")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("ul.right-rail-typo > li")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("li.print-container.anPrintEventDetails")).isEmpty(),false);
	    //Assert.assertEquals(driver.findElements(By.xpath("//div[@id='event_addProduct']/button")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Events")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.xpath("//form[@id='eventInfoIcs']/button")).isEmpty(),false);
	}

}
