package com.totalwine.test.checkout;

/*
 * Checkout Workflow
 * Workflow:
 * 	1. Access PDP for item
 * 	2. Add to cart
 * 	3. View Cart
 * 	4. Initial registered checkout
 * 	5. Tab 1 Checkout
 *  6. Tab 2 Checkout (Billing Details)
 *  7. Tab 3 Checkout (Confirm)
 *  8. Order Confirmation Page
 *  
 * Technical Modules:
 * 	1. DataProvider: Checkout test input parameters
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
//@author=rsud
import java.io.IOException;

import jxl.read.biff.BiffException;

import org.testng.*;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.openqa.selenium.Keys;

import com.totalwine.test.config.ConfigurationFunctions;
import com.totalwine.test.trials.Browser;

public class ShipCheckout extends Browser {
	//public String locationSet = "http://twmuatwebserver:webserveruattwm@uat.totalwine.com/?remoteTestIPAddress=";
	//public static WebDriver driver;
	//ProfilesIni profile = new ProfilesIni();
	//FirefoxProfile testProfile = profile.getProfile("WebDriver");

	@DataProvider(name="CheckoutParameters")
    public Object[][] createData() {
    	Object[][] retObjArr=ConfigurationFunctions.getTableArray(ConfigurationFunctions.resourcePath,"Checkout", "ShipcheckoutBF");
        return(retObjArr);
    } 
	
	@BeforeMethod
	  public void setUp() throws Exception {
		//driver = new FirefoxDriver(testProfile);
	    driver.manage().window().maximize();	
		   
	  }  
	
	@Test (dataProvider = "CheckoutParameters")
	public void ShipCheckoutTest (String Location,String StoreName,String PDP,String Quantity,String ShipOption,String Email,String Password, String Phone,String FirstName,String LastName,String Company,String Address1,String Address2,String City,String State,String Zip) throws InterruptedException, BiffException, IOException {
		logger=report.startTest("Ship Registered Checkout Test");
		driver.get(ConfigurationFunctions.locationSet+Location);
		Thread.sleep(5000);
		driver.findElement(By.id("btnYes")).click();
		Thread.sleep(5000);
	    //driver.findElement(By.cssSelector("#email-signup-overlay-new-site > div.modal-dialog > div.modal-content > div.modal-body > p.close > a.btn-close")).click();
	    //Thread.sleep(5000);
	    Assert.assertEquals(StoreName, driver.findElement(By.cssSelector("span.store-details-store-name.flyover-src")).getText());
//	    logger.log(LogStatus.PASS, "The site is configured for a Shipping order");
	    ConfigurationFunctions.highlightElement(driver,driver.findElement(By.cssSelector("span.store-details-store-name.flyover-src")));
		
		// Add to Cart
		driver.get(ConfigurationFunctions.accessURL+PDP);
		Thread.sleep(3000);
		String productId = driver.findElement(By.cssSelector("div.anProductId")).getText();
		System.out.println(productId);
		Thread.sleep(2000);
		Assert.assertEquals(driver.findElements(By.xpath("(//button[@id='"+productId+"'])[2]")).isEmpty(), false,"The ATC button isn't on the PDP indicating that the test item may be OOS");
		driver.findElement(By.xpath("(//button[@id='"+productId+"'])[2]")).click(); //Clicking the ATC button
		//driver.findElement(By.cssSelector("button.btn.btn-red.mini-cart-popup.anAddToCart")).click();
		Thread.sleep (3000);
	    driver.get(ConfigurationFunctions.accessURL+"/cart");
	    Thread.sleep(3000);
//	    logger.log(LogStatus.PASS, "The test item is successfully added to cart");

	    // Shopping Cart
	    //WebElement scroll = driver.findElement(By.id("salesTaxId"));
	    WebElement scroll = driver.findElement(By.id("checkout"));
	    scroll.sendKeys(Keys.PAGE_DOWN);
	    driver.findElement(By.id("zipCode")).click();
	    driver.findElement(By.id("zipCode")).clear();
	    driver.findElement(By.id("zipCode")).sendKeys(Zip);
	    driver.findElement(By.cssSelector("input.anZipForm")).click();
	    Thread.sleep(6000);
	  
	    driver.findElement(By.cssSelector("#deliveryMode > div.customselect > span.itemval")).click();
	    driver.findElement(By.cssSelector("li[data-val="+ShipOption+"]")).click();
	    Thread.sleep(3000);
	    Assert.assertEquals(driver.findElements(By.cssSelector("div[class=\"width-100 totalDotBorder noBorder ship-cost\"]")).isEmpty(),false); //Validate appearance of shipping cost
	    Assert.assertEquals(driver.findElements(By.cssSelector("input.anVoucherForm")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.name("qty")).isEmpty(),false);
	    driver.findElement(By.id("checkout")).click();
	    Thread.sleep(3000);
//	    logger.log(LogStatus.PASS, "Shopping cart elements");
	    
	    // Next Page (Login/Checkout as a registered user)
	    Assert.assertEquals(driver.findElements(By.id("j_username")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.id("j_password")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("div.checkStyle > label")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.id("forgotPasswordCheckout")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.id("checkoutSignIn")).isEmpty(),false);
//	    logger.log(LogStatus.PASS, "Selecting registered/guest checkout");
	    
	    //Login
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys(Email);
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys(Password);
	    driver.findElement(By.id("checkoutSignIn")).click();
	    Thread.sleep(3000);
//	    logger.log(LogStatus.PASS, "Login");
	    
	    // Checkout Tab 1
	    Assert.assertEquals(driver.findElements(By.cssSelector("a.shipping-tab")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("a.analyticsEditCart")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("a.analyticsPromoCode")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("section.giftmessage > div.checkStyle > label")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("section.someoneelsepicking > div.checkStyle > label")).isEmpty(),true);
	    Assert.assertEquals(driver.findElements(By.cssSelector("div.checkStyle > label")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("div.checkStyle > label")).isEmpty(),false);
	    
	    String regd_email = driver.findElement(By.id("shipping-email")).getAttribute("value");
	    String regd_phone = driver.findElement(By.id("shipping-phoneNumber")).getAttribute("value");
	    Assert.assertEquals(regd_email, Email);
	    Assert.assertEquals(regd_phone, Phone);
//	    logger.log(LogStatus.PASS, "Checkout Tab 1"+ "");
	    driver.findElement(By.id("nickName")).clear();
	    driver.findElement(By.id("nickName")).sendKeys("test");
	    driver.findElement(By.id("firstName")).clear();
	    driver.findElement(By.id("firstName")).sendKeys(FirstName);
	    driver.findElement(By.id("lastName")).clear();
	    driver.findElement(By.id("lastName")).sendKeys(LastName);
	    driver.findElement(By.id("companyName")).clear();
	    driver.findElement(By.id("companyName")).sendKeys(Company);
	    driver.findElement(By.id("addressLine1")).clear();
	    driver.findElement(By.id("addressLine1")).sendKeys(Address1);
	    driver.findElement(By.id("city")).clear();
	    driver.findElement(By.id("city")).sendKeys(City);
	    
	    
	    driver.findElement(By.id("btnShipAuth1")).click();
	    Thread.sleep(5000);
//	    logger.log(LogStatus.PASS, "Registered Ship Checkout Tab 1");
	    
	    // Checkout Tab 2
	    Assert.assertEquals(driver.findElements(By.cssSelector("a.billing-tab")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("a.analyticsEditCart")).isEmpty(),false);
	    //Use Saved Credit Card
	    driver.findElement(By.xpath("//div[4]/span")).click();
	    driver.findElement(By.xpath("//button[@onclick='javascript:selectCard();']")).click();
	    Thread.sleep(10000);
//	    logger.log(LogStatus.PASS, "Registered Ship Checkout Tab 2");;
	    
	    // Checkout Tab 3
	    Assert.assertEquals(driver.findElements(By.cssSelector("a.review-tab")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("li[class=\"co-rvw co-rvw-instore\"]")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("li[class=\"co-rvw co-rvw-pymnt\"]")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("li[class=\"co-rvw\"]")).isEmpty(),false);
	    Assert.assertEquals(driver.findElement(By.cssSelector("li.co-rvw.co-rvw-instore > h3")).getText(), "Shipping method");
	    Assert.assertEquals(driver.findElements(By.cssSelector("div.plp-list-img-wdlogo > img")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("span[data-attr=\"itemPrice_1\"]")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("span[data-attr=\"itemPrice_2\"]")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("span[class=\"price-text item-total anTax\"]")).isEmpty(),false);
	    Assert.assertEquals(driver.findElements(By.cssSelector("span[class=\"price-text item-total co-pr-item-total\"]")).isEmpty(),false);
	    driver.findElement(By.id("check_box_age")).click();
	    //driver.findElement(By.xpath("//form[@id='placeOrderForm1']/section/div/button")).click();
	    driver.findElement(By.cssSelector("button.btn-red.btn-place-order.anPlaceOrder")).click();
	    Thread.sleep(10000);
//	    logger.log(LogStatus.PASS, "Registered Ship Checkout Tab 3");
	    
	    // Order Confirmation 
	    //Cannot validate as credit card gets declined
	    //Assert.assertEquals(driver.findElements(By.linkText("Post to Facebook")).isEmpty(),false);
	    //Assert.assertEquals(driver.findElements(By.cssSelector("div.co-conf-help-link")).isEmpty(),false);
	    //Assert.assertEquals(driver.findElements(By.cssSelector("div.co-conf-thank-text")).isEmpty(),false);
	    //Assert.assertEquals(driver.findElements(By.cssSelector("div")).isEmpty(),false);
	}
}
