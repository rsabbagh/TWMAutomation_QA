package com.totalwine.test.search;

/*
 * Site Search Workflow
 * Workflow:
 * 	1. Enter the search term on the top right search text bar
 * 	2. Click the search button 
 * 	3. Count the instances of the search term on the search results page
 *    a. Fail the test if the search term doesn't appear in any of the results
 *  
 * Technical Modules:
 * 	1. DataProvider: Search test input parameters
 * 	2. BeforeMethod (Test Pre-requisites):
 * 			Invoke webdriver
 * 			Maximize browser window
 * 	3. Test (Workflow)
 * 	4. AfterMethod
 * 			Take screenshot, in case of failure
 * 			Close webdriver
 * 	5. AfterClass
 * 			Quit webdriver
 */

import java.util.List;

import org.testng.*;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import com.totalwine.test.config.ConfigurationFunctions;
import com.totalwine.test.trials.Browser;


public class Search extends Browser {
	
	public String IP = "71.193.51.0";
	
	@BeforeMethod
	  public void setUp() throws Exception {
		driver.manage().window().maximize();
	  } 
	
	@DataProvider(name="SearchParameters")
    public Object[][] createData() {
    	Object[][] retObjArr=ConfigurationFunctions.getTableArray(ConfigurationFunctions.resourcePath,"Search", "search");
        return(retObjArr);
    }
	
	@Test (dataProvider = "SearchParameters")
	public void SearchTest (String searchTerm) throws InterruptedException {
		logger=report.startTest("Search Test");
		driver.get(ConfigurationFunctions.locationSet+IP);
		Thread.sleep(5000);
		driver.findElement(By.id("btnYes")).click();
		//driver.findElement(By.cssSelector("div.modal-content > div.modal-body > div.pdp-store-modal-wrapper > div.store-modal-content > div.ageGatingMain > div.ageGatingContainer > div.ageGatingButtons > form.add_to_cart_form.clear_fix > button.btn.btn-red")).click();
		Thread.sleep(5000);
	    driver.findElement(By.cssSelector("#email-signup-overlay-new-site > div.modal-dialog > div.modal-content > div.modal-body > p.close > a.btn-close")).click();
	    Thread.sleep(5000);
		
		driver.findElement(By.id("header-search-text")).clear();
	    driver.findElement(By.id("header-search-text")).sendKeys(searchTerm);
	    driver.findElement(By.cssSelector("a[class=\"search-right-cont-mini-search-logo analyticsSearch\"]")).click();
	    Thread.sleep(3000);
	    
	    //Search Page Elements
	    Assert.assertEquals(driver.findElements(By.linkText("Search categories")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("div.inner-items-wrapper > ul > li > a > span")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.xpath("//li[2]/a/span")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.xpath("//li[3]/a/span")).isEmpty(),false);

	    //Search Term Presence on Page
	    List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'" + searchTerm + "')]"));
	    System.out.println(searchTerm+": "+list.size());
	    Assert.assertEquals(list.size() > 1,true);
	}
}
