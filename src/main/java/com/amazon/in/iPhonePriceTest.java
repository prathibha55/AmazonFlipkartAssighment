package com.amazon.in;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class iPhonePriceTest {

	public static void main(String[] args) {

		// Instantiate a ChromeDriver class.
		WebDriverManager.chromedriver().version("91").setup();

		WebDriver driver = new ChromeDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Maximize the browser
		driver.manage().window().maximize();

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		By yellowPhoneLink = By.xpath("//div[@class='a-section a-spacing-none']//span[contains(text(),'Yellow')]");
		By yellowPhoneLinkFLipkart = By.xpath("//div[contains(text(),'APPLE iPhone 11 (Yellow, 64 GB)')]");

		// Launch Website
		driver.navigate().to("https://www.amazon.in/");

		//Search iPhone in Amazon
		driver.findElement(By.cssSelector("#twotabsearchtextbox")).sendKeys("iPhone XR (64GB) - Yellow");

		driver.findElement(By.id("nav-search-submit-button")).click();
		js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(yellowPhoneLink));

		String phoneName = driver.findElement(yellowPhoneLink).getText();

		//Get the price iPhone in Amazon
		String priceInAmazon = driver.findElement(By.xpath("//div[@class='a-section a-spacing-none']//span[contains(text(),'Yellow')]//ancestor::div[@class='a-section a-spacing-none']//following-sibling::div[@class='sg-row']//span[@class='a-price-whole']")).getText();
		priceInAmazon = priceInAmazon.replaceAll("[^\\d.]", "");
		int phonePriceInAmazon = Integer.parseInt(priceInAmazon);
		
		System.out.println("The iPhone XR (64GB) - Yellow phone price in amazon - "+phonePriceInAmazon);

		//Launch website
		driver.get("https://www.flipkart.com/");

		driver.findElement(By.xpath("//button[contains(text(),'✕')]")).click();

		//Search iPhone in Flipkart
		driver.findElement(By.xpath("//input[@placeholder='Search for products, brands and more']")).sendKeys(phoneName);

		driver.findElement(By.xpath("//button[@type='submit']")).click();

		js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(yellowPhoneLinkFLipkart));

		//Get the iPhone price in Flipkart
		String priceInFlipkart = driver.findElement(By.xpath("//div[contains(text(),'APPLE iPhone 11 (Yellow, 64 GB)')]/parent::div/following-sibling::div[@class='col col-5-12 nlI3QM']//div[@class='_30jeq3 _1_WHN1']")).getText();

		priceInFlipkart = priceInFlipkart.replaceAll("[^\\d.]", "");
		int phonePriceInFlipkart = Integer.parseInt(priceInFlipkart);

		System.out.println("The iPhone XR (64GB) - Yellow phone price in flipKart - " +phonePriceInFlipkart);
		
		//close the website
		driver.close();

		//Compare the price 
		if(phonePriceInFlipkart>phonePriceInAmazon) {
			int price = phonePriceInFlipkart-phonePriceInAmazon;
			System.out.println("Phone price is "+ price +" less in Amazon compare to Flipkart" );
		}
		else if (phonePriceInFlipkart<phonePriceInAmazon) {
			int price = phonePriceInAmazon-phonePriceInFlipkart;
			System.out.println("Phone price is "+ price +" less in Flipkart compare to Amazon" );
		}
		else if(phonePriceInFlipkart==phonePriceInAmazon) {
			System.out.println("Phone price is same in Flipkart and Amazon" );
		}

	}

}
