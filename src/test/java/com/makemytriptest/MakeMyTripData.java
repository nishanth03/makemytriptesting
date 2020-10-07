package com.makemytriptest;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MakeMyTripData {

	public static WebDriver driver;
	public WebDriverWait wait;

	@BeforeTest
	public void setuUp() {

		System.setProperty("webdriver.chrome.driver", "D:\\Nishanth\\TestAutomation_Selenium\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.makemytrip.com/");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

	}

	@Test
	public void getMakeMyTripSite() throws Exception {

		WebElement login = driver.findElement(By.xpath("//p[contains(text(),'Login or Create Account')]"));
		wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.elementToBeClickable(login));

		login.click();

		driver.findElement(By.id("username")).sendKeys("nishanth03@gmail.com");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//span[contains(text(),'Continue')]")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Password@0305");
		driver.findElement(By.xpath("//button[@data-cy='login']")).click();
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);

		Thread.sleep(20000);
		WebElement hotelMenu1 = driver.findElement(By.xpath("//li[contains(@data-cy,'menu_Hotels')]/a"));
		hotelMenu1.click();

		driver.findElement(By.xpath("//span[contains(text(),'City / Hotel / Area / Building')]")).click();
		driver.findElement(By.xpath("//input[(@type='text' and @placeholder='Enter city/ Hotel/ Area/ Building')]"))
				.sendKeys("cochin, kerala, india");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		Actions action = new Actions(driver);
		WebElement list = driver
				.findElement(By.xpath("//li[@role='option']//p[contains(text(),'Cochin, Kerala, India')]"));
		action.moveToElement(list).click(list).build().perform();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		WebElement fromDateSelect = driver.findElement(By.id("checkin"));
		fromDateSelect.click();
		driver.findElement(By.xpath("//div[@class='DayPicker-Day'][contains(@aria-label, 'Sat Oct 31 2020')]")).click();
		driver.findElement(By.xpath("//div[@class='DayPicker-Day'][contains(@aria-label, 'Mon Nov 02 2020')]")).click();
		driver.findElement(By.xpath("//input[@id='guest']")).click();
		driver.findElement(By.xpath("//li[@data-cy='adults-3']")).click();
		driver.findElement(By.xpath("//button[contains(text(),'APPLY')]")).click();
		driver.findElement(By.xpath("//button[@id='hsw_search_button']")).click();
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);

		Thread.sleep(10000);
		WebElement minPriceRange = driver.findElement(By.xpath("(//div[@role='slider'])[1]"));
		WebElement maxPriceRange = driver.findElement(By.xpath("(//div[@role='slider'])[2]"));

		action.dragAndDropBy(minPriceRange, 55, 0).perform();
		Thread.sleep(10000);
		action.dragAndDropBy(maxPriceRange, -45, 0).perform();
		Thread.sleep(10000);

		driver.findElement(By.xpath("//span[contains(text(),'Kakkanad')]")).click();
		Thread.sleep(10000);
		WebElement starRating = driver.findElement(By.xpath("//label[contains(text(),'5 Star')]"));
		wait.until(ExpectedConditions.elementToBeClickable(starRating));
		starRating.click();

		driver.findElement(By.xpath("//span[@id='htl_id_seo_201812131329417956']")).click();
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);

		String MainWindow = driver.getWindowHandle();
		Set<String> s1 = driver.getWindowHandles();
		Iterator<String> i1 = s1.iterator();
		while (i1.hasNext()) {
			String ChildWindow = i1.next();

			if (!MainWindow.equalsIgnoreCase(ChildWindow)) {
				driver.switchTo().window(ChildWindow);

				driver.findElement(By.xpath("//a[@id='detpg_headerright_book_now']")).click();

				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

				boolean flag = false;

				flag = driver
						.findElement(
								By.xpath("//p[contains(text(), 'We won’t be able to  save your selection if you')]"))
						.isDisplayed();
				if (flag) {
					driver.findElement(By.xpath("//span[@class='close']")).click();
				}

				driver.findElement(By.id("fName")).sendKeys("Nishanth");
				driver.findElement(By.id("lName")).sendKeys("Chang");
				driver.findElement(By.xpath(" //label[contains(text(),'Early check-in')]")).click();
				driver.findElement(By.id("109")).sendKeys("Please clean the room");
				driver.findElement(By.xpath("//div[contains(text(),'Pay Now')]")).click();

				WebElement cardPay = driver.findElement(By.xpath("//a[@id='CC']"));
				wait.until(ExpectedConditions.elementToBeClickable(cardPay));
				Thread.sleep(20000);
				cardPay.click();
				System.out.println("Card Payment Clicked");

				driver.findElement(By.xpath("//input[@id='PAYMENT_cardNumber']")).sendKeys("1234567890123456");
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.findElement(By.xpath("//input[@id='PAYMENT_nameOnCard']")).sendKeys("NISHANTH C");

				Select month = new Select(driver.findElement(By.xpath("//select[@id='PAYMENT_expiryMonth']")));
				month.selectByValue("03");

				Select year = new Select(driver.findElement(By.xpath("//select[@id='PAYMENT_expiryYear']")));
				year.selectByValue("2021");

				driver.findElement(By.name("PAYMENT_cvv")).sendKeys("123");

				String textError = "This card type is not supported";
				driver.findElement(By.xpath("//button[@id='widgetPayBtn']")).click();
				boolean text = driver.getPageSource().contains(textError);
				Assert.assertTrue(text, "Valid Card Details Entered");

				driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

				driver.close();
			}
		}
		driver.switchTo().window(MainWindow);
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
