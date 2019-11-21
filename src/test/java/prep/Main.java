package prep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;


public class Main {
	@Test
	public void spiCinemas() {
		System.setProperty("webdriver.chrome.driver", "/home/balaji/softwares/chromedriver_linux64/chromedriver");
		System.setProperty("webdriver.gecko.driver", "/home/balaji/softwares/geckodriver-v0.26.0-linux32/geckodriver");
		
		System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,"true");
		System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");
		
//		WebDriver driver = new FirefoxDriver();
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.spicinemas.in/");
		
		String selectCityIdentifierXPath = "//*[text()='Please select your city']";
		String citySelectionDropdownIdentifierID = "cityList";
		String selectCityOkButtonIdentifierID = "selectCity";
		String dropDownArrowIdentifierClass = "dropdown-arrow";
		String cityOptionValue = "coimbatore";
		String cityOptionIdentifierXPath = "//li[text()='"+ cityOptionValue + "']";
		
		ByXPath cityVerificationIdentifierXpath = new ByXPath("//*[@class='selected-city city allow-click']//span[not(contains(@class,'app-icon current-city'))]");
		
		WebDriverWait wait = new WebDriverWait(driver,5);
		WebElement citySelectionElement = 
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selectCityIdentifierXPath)));
		
		
		
		System.out.println(citySelectionElement.isDisplayed());
		driver.findElement(By.className(dropDownArrowIdentifierClass)).click();
		WebElement cityOptionElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(cityOptionIdentifierXPath)));
//		driver.close();
		cityOptionElement.click();
		driver.findElement(By.id(selectCityOkButtonIdentifierID)).click();
		
		System.out.println(driver.findElement(cityVerificationIdentifierXpath).getText());
		Assert.assertEquals(
				cityOptionValue.toLowerCase(), 
				driver.findElement(cityVerificationIdentifierXpath).getText().toLowerCase());
		Reporter.log("");
		
		String timeStamp;
		File screenShotName;
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//The below method will save the screen shot in d drive with name "screenshot.png"
		LocalDateTime dateObj = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd_kk_mm_ss_SSS");
		
		timeStamp = dateObj.format(formatter) ;
		screenShotName = new File("target/"+timeStamp+".png");
		try {
			Files.copy(scrFile.toPath(), screenShotName.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		String filePath = screenShotName.toString();
		String path = "<img src=\""+filePath+"\">";
		Reporter.log(path);
		
		driver.quit();
		
		
	}
	
	@Test
	public void bookMyShow() {
		
		System.setProperty("webdriver.chrome.driver", "/home/balaji/softwares/chromedriver_linux64/chromedriver");
		System.setProperty("webdriver.gecko.driver", "/home/balaji/softwares/geckodriver-v0.26.0-linux32/geckodriver");
		
		System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,"true");
		System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");
		
		FirefoxProfile geoDisabled = new FirefoxProfile();
		geoDisabled.setPreference("geo.enabled", false);
		geoDisabled.setPreference("geo.provider.use_corelocation", false);
		geoDisabled.setPreference("geo.prompt.testing", false);
		geoDisabled.setPreference("geo.prompt.testing.allow", false);
		
		
		
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability(FirefoxDriver.PROFILE, geoDisabled);
		
		FirefoxOptions options = new FirefoxOptions(capabilities);
		
//		WebDriver driver = new FirefoxDriver();
		WebDriver driver = new FirefoxDriver(options);
		String appUrl = "https://in.bookmyshow.com/";
		driver.get(appUrl);
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(10));
		

//		wait.until(new Function<WebDriver, Boolean>() {}
//				);
		
		wait.until(new Function<WebDriver, Boolean>() {


			@Override
			public Boolean apply(WebDriver t) {
				// TODO Auto-generated method stub
				return ((JavascriptExecutor)t).executeScript("return document.readyState").equals("complete");
				
			}

		});
		ById searchFieldIdentifier = new ById("inp_RegionSearch_top");
		ByXPath searchResultIdentifier = new ByXPath("//input[@id='inp_RegionSearch_top']//parent::span//div[@class='tt-dataset tt-dataset-result']");
		String input = "coimbatore";
		driver.findElement(searchFieldIdentifier).sendKeys(input);
		

		WebElement searchResultElement = wait.until(ExpectedConditions.visibilityOfElementLocated(searchResultIdentifier));
		searchResultElement.click();
//		driver.quit();
		
		
		System.out.println("hello");
	}
	
	public static void main(String [] args) {
		Main obj = new Main();
		obj.bookMyShow();
	}
	
	
	
}
