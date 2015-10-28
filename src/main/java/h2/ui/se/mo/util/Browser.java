package h2.ui.se.mo.util;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author thienthang
 *
 */
public class Browser 
{
	
	/**
	 * @return
	 */
	public static WebDriver getFirefox()
	{
		WebDriver driver = new FirefoxDriver();
		return driver;
	}
	
	/**
	 * @return
	 */
	public static WebDriver getChrome() 
	{
		System.setProperty("webdriver.chrome.driver", "E:\\WORK\\H2\\MyOrder\\Selenium\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		return driver;
	}
	
	/**
	 * @return
	 */
	public static WebDriver getIE()
	{
		System.setProperty("webdriver.chrome.driver", "E:\\WORK\\H2\\MyOrder\\Selenium\\IEDriverServer.exe");
		WebDriver driver = new ChromeDriver();
		return driver;
	}
	
	public static void addTab(WebDriver iDriver) throws InterruptedException
	{
		switch (PosUtil.getPlatform()) 
		{
		case windows:
			iDriver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
			Thread.sleep(1000);
			break;
		case mac:
			iDriver.findElement(By.cssSelector("body")).sendKeys(Keys.COMMAND + "t");
			Thread.sleep(1000);
			break;
		default:
			System.out.println("Doesn't support other OS except windows and mac");
			break;
		}
	}
	
	public static void closeTab(WebDriver iDriver) throws InterruptedException 
	{
		switch (PosUtil.getPlatform()) 
		{
		case windows:
			iDriver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "w");
			Thread.sleep(1000);
			break;
		case mac:
			iDriver.findElement(By.cssSelector("body")).sendKeys(Keys.COMMAND + "w");
			Thread.sleep(1000);
			break;
		default:
			System.out.println("Doesn't support other OS except windows and mac");
			break;
		}
		
	}
	
	public static void loginSalesforce(WebDriver iDriver) throws InterruptedException{
		
		//Add Tab
		addTab(iDriver);
		
		ArrayList<String> lTabs = new ArrayList<String> (iDriver.getWindowHandles());
		//System.out.println(lTabs.get(0));
		//System.out.println(lTabs.get(1));
		iDriver.switchTo().window(lTabs.get(1));
		iDriver.get("https://www.salesforce.com");
		
		WebDriverWait lDriverWait = new WebDriverWait(iDriver,10);
		lDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("button-login")));
		iDriver.findElement(By.id("button-login")).click();
		Thread.sleep(1000);
		PosUtil.fillInfo(iDriver);
	}
	
	public static void openAllMOTab(WebDriver iDriver)
	{
		WebDriverWait lDriverWait = new WebDriverWait(iDriver,10);
		lDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("AllTab_Tab")));
		iDriver.findElement(By.id("AllTab_Tab")).click();
	}
	
	public static void openNewWindow(WebDriver iDriver)
	{
		WebDriverWait lDriverWait = new WebDriverWait(iDriver,10);
		lDriverWait = new WebDriverWait(iDriver,2);
		lDriverWait.until(ExpectedConditions.elementToBeClickable(By.name("new")));
		iDriver.findElement(By.name("new")).click();
	}

}
