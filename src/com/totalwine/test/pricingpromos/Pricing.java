package com.totalwine.test.pricingpromos;

/*
 * Pricing Workflow
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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.totalwine.test.config.ConfigurationFunctions;
import com.totalwine.test.trials.Browser;

public class Pricing extends Browser {

	//private String IP="98.169.134.0";
	private String IP="74.96.91.139";

	@BeforeMethod
	  public void setUp() throws Exception {
	    driver.manage().window().maximize();
	}  
	
	@Test 
	public void PricingTest () throws InterruptedException, BiffException, IOException, AWTException {
		logger=report.startTest("Pricing Test");
		driver.get(ConfigurationFunctions.locationSet+IP);
		Thread.sleep(5000);
		driver.findElement(By.id("btnYes")).click();
		Thread.sleep(5000);
	    driver.findElement(By.cssSelector("#email-signup-overlay-new-site > div.modal-dialog > div.modal-content > div.modal-body > p.close > a.btn-close")).click();
	    Thread.sleep(5000);
	    
	    Actions action=new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor)driver;

	    //Access Wine PLP
		WebElement wineNav = driver.findElement(By.xpath("//a[contains(@href,'/c/c0020')]")); 
		action.moveToElement(wineNav).build().perform(); //Top Level Menu Hover
		WebElement winePLPNav=driver.findElement(By.xpath("//a[contains(@href,'/wine/white-wine/c/')]"));
		js.executeScript("arguments[0].click();", winePLPNav);
		Thread.sleep(5000);
		WebElement wineMove = driver.findElement(By.cssSelector("ul.header-classes")); //Moving the mouse away from the top level menu 
		action.moveToElement(wineMove).build().perform(); 
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("a.btn.btn-red.clpviewall")).click();
		Thread.sleep(3000);
		
		//Validate Pricing on PLP
		String plpPrice = driver.findElement(By.cssSelector("span.price")).getText();
		//driver.findElement(By.cssSelector("a.analyticsProductName")).getAttribute("id");
		//String productID = driver.findElement(By.cssSelector("a.analyticsProductName")).getCssValue("href");
		//System.out.println(productID);
						
		//Click through to first available PDP and validate pricing match on PDP against PLP
		driver.findElement(By.cssSelector("a.analyticsProductName")).click();
		Thread.sleep(5000);
		String pdpPrice = driver.findElement(By.cssSelector("span.price-style-mid")).getText();
		String productId = driver.findElement(By.cssSelector("div.anProductId")).getText();
		
		/*//Click the ATC button and validate price on ATC interstitial, then close the ATC interstitial
		wineMove = driver.findElement(By.cssSelector("ul.header-classes")); //Moving the mouse away from the top level menu 
		action.moveToElement(wineMove).build().perform(); 
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[contains(@href,'000002?viewall=true')]")).click(); //For production since the SubCat Land page is setup
		Thread.sleep(5000);
		//driver.findElement(By.cssSelector("btn.btn-red.mini-cart-popup-plp.anAddToCartInit")).click();
		
		String atcInterPrice = driver.findElement(By.cssSelector("span.price-style-mid")).getText();
		driver.findElement(By.cssSelector("section.pdp-tab-overview-type > span.pdp-emailmodal-emailwrapper-emailclose")).click();*/
		
		//Add to cart, navigate to cart and validate pricing in Cart
		//String productId = driver.findElement(By.cssSelector("div.anProductId")).getText();
	    driver.findElement(By.xpath("(//button[@id='"+productId+"'])[2]")).click(); //Clicking the ATC button
	    Thread.sleep (2000);
	    driver.get(ConfigurationFunctions.accessURL+"/cart");
	    Thread.sleep(3000);
	    String cartUnitPrice = driver.findElement(By.cssSelector("span.price-text")).getText();
	    String cartTotalPrice = driver.findElement(By.cssSelector("span.price-text.item-total")).getText();
	    String cartTotalinclTax = driver.findElement(By.id("estimTotal")).getText();
	    
		//Navigate to ELP
	    driver.findElement(By.linkText("Classes & Events")).click();
	    Thread.sleep(3000);
	    String elpPrice = driver.findElement(By.cssSelector("span.price")).getText();
	    		
		//Click through to first available EDP and validate pricing match on EDP against ELP
	    driver.findElement(By.cssSelector("a.analyticsEventName")).click();
	    Thread.sleep(3000);
	    String edpPrice = driver.findElement(By.cssSelector("section.store-right-hours-tasting > div.search-result-list-buy-ctrls > div.search-product-buy-price > span.price")).getText();
	    
	    System.out.println("PLP Price: "+plpPrice+"\n"+"PDP Price: "+pdpPrice+"\n"+"Cart Unit Price: "+cartUnitPrice+"\n"+"Cart Total Price: "+cartTotalPrice+"\n"+"Cart Total Incl. Taxes Price: "+cartTotalinclTax);
	    //Assert.assertTrue(plpPrice.equals(pdpPrice)&&pdpPrice.equals(cartUnitPrice)&&cartUnitPrice.equals("$ "+cartTotalPrice));
	    System.out.println("PLP Price: "+plpPrice+"\n"+"PDP Price: "+pdpPrice+"\n"+"Cart Unit Price: "+cartUnitPrice+"\n"+"Cart Total Price: "+cartTotalPrice+"\n"+"Cart Total Incl. Taxes Price: "+cartTotalinclTax+"\n"+"ELP Price: "+elpPrice+"\n"+"EDP Price: "+edpPrice);
	    Assert.assertTrue(plpPrice.equals(pdpPrice),"PLP price is not the same as PDP price");
	    Assert.assertTrue(pdpPrice.equals(cartUnitPrice),"PDP price is not the same as unit price in cart");
	    Assert.assertTrue(cartUnitPrice.equals(cartTotalPrice),"Unit price in cart is not the same as total price in cart for the single item");
	    Assert.assertTrue(elpPrice.equals(edpPrice),"ELP price is not the same as EDP price");
	}
}
