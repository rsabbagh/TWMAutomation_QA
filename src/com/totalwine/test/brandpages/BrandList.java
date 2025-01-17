package com.totalwine.test.brandpages;

/*
 * Brand List Page Workflow
 * Workflow:
 * 	1. 
 *  2.
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

import java.awt.AWTException;
import java.io.IOException;

import jxl.read.biff.BiffException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.totalwine.test.config.ConfigurationFunctions;
import com.totalwine.test.trials.Browser;

public class BrandList extends Browser {

	private String IP="71.193.51.0";

	@BeforeMethod
	  public void setUp() throws Exception {
	    driver.manage().window().maximize();
	}  
	
	@Test 
	public void BrandListPageTest () throws InterruptedException, BiffException, IOException, AWTException {
		logger=report.startTest("Brand List Page Test");
		driver.get(ConfigurationFunctions.locationSet+IP);
		Thread.sleep(5000);
		driver.findElement(By.id("btnYes")).click();
		Thread.sleep(5000);
	    driver.findElement(By.cssSelector("#email-signup-overlay-new-site > div.modal-dialog > div.modal-content > div.modal-body > p.close > a.btn-close")).click();
	    Thread.sleep(5000);
	    
	    //Access Brand Story Telling page via PDP's View All link
	    driver.get(ConfigurationFunctions.accessURL+"/wine/white-wine/chardonnay/cloud-break-chardonnay/p/110892750");
	    Thread.sleep(3000);
	    driver.findElement(By.linkText("View all Cloud Break products >")).click();
	    Thread.sleep(3000);
	    String BrandListPageURL = driver.getCurrentUrl();
	    Assert.assertEquals(BrandListPageURL.toLowerCase().contains("/wine/brand/".toLowerCase()),true);
	    
	    
	    //Validate elements of the Brand List page are present
	    Assert.assertEquals(driver.findElements(By.linkText("Cloud Break")).isEmpty(),false);
	    String BLPName = driver.findElement(By.cssSelector("a.analyticsProductName")).getText();
	    Assert.assertEquals(BLPName.toLowerCase().contains("Cloud Break".toLowerCase()),true);
	    Assert.assertEquals(driver.findElements(By.id("plp-aty-tab")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.id("plp-productfull-tabs")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Stores")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Wine Category")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Wine Varietal/Type")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Brand")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Country/State")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Wine Region")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Appellation")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Expert Rating")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Rating Source")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Price")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Wine Volume")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("div.dropdown.plp-product-sorting-sortby-dropdown > div.customselect > span.itemval > span")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.linkText("Pick up or ship")).isEmpty(),false);
	    
	    
	    //Validate elements of Brand Story Telling page are absent
	    Assert.assertEquals(driver.findElements(By.cssSelector("section.wrapper-bleed > img")).isEmpty(),true);
	    Assert.assertEquals(driver.findElements(By.id("btnShopStoryTelling")).isEmpty(),true);
	    Assert.assertEquals(driver.findElements(By.cssSelector("section.formbg.education-container")).isEmpty(),true);
	    
	    //Click the first link and validate that the PDP appears
	    String BrandLPName = driver.findElement(By.cssSelector("a.analyticsProductName")).getText();
	    driver.findElement(By.cssSelector("a.analyticsProductName")).click();
	    Thread.sleep(3000);
	    String PDPName = driver.findElement(By.cssSelector("h1.product-name")).getText();
	    Assert.assertEquals(BrandLPName,PDPName);
	}
}
