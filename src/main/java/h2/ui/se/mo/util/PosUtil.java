package h2.ui.se.mo.util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PosUtil 
{
	
	public enum OS { windows, mac }
	public enum FILTER {ID, XPATH, NAME, CSS}
	
	protected static OS mPlatform = OS.windows;
	protected static Map<String, WebElement> configs;
	
	public static WebDriver initPos() throws InterruptedException 
	{
		System.setProperty(MOConstant.WEBDRIVER_CHROME_DRIVER, MOConfig.getConfig(MOConstant.WEBDRIVER_CHROME_DRIVER));

		ChromeOptions options = new ChromeOptions();
		options.addExtensions(new File(MOConfig.getConfig(MOConstant.MO_CHROME_EXTENSION)));
				// options.setBinary(new
				// File("E:\\WORK\\H2\\MyOrder\\Selenium\\chromedriver.exe"));

		// For use with ChromeDriver:
		WebDriver driver = new ChromeDriver(options);
		//driver.manage().window().maximize();
		driver.get("chrome://apps/");

		List<WebElement> lWebElementList = driver.findElements(By.tagName("div"));

		for (WebElement lWebElement : lWebElementList) 
		{
			if (lWebElement.getAttribute("title").contains("MyOrder"))
			{
				lWebElement.click();
				break;
			}
		}
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(driver.getCurrentUrl());
		
		return login(driver);
	}
	
	protected static WebDriver login(WebDriver iDriver) throws InterruptedException
	{
		Thread.sleep(1000);
		if (iDriver.getWindowHandles().size() > 1)
		{
			return handleLogin(iDriver);
		}
		return login(iDriver);
	}
	
	/**
	 * @param iDriver
	 * @return
	 */
	protected static WebDriver handleLogin(WebDriver iDriver) 
	{
		ArrayList<String> lTabs = new ArrayList<String> (iDriver.getWindowHandles());
		String lPosWindow = lTabs.get(0);
		String lLoginWindow = lTabs.get(1);
		
		iDriver.switchTo().window(lLoginWindow);
		waitClickId(iDriver, "Login");
		
		//Enter user information
		fillInfo(iDriver);
		
		waitClickId(iDriver, "oaapprove");
		
		iDriver.switchTo().window(lPosWindow);
		
		return iDriver;
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	public static String getOs() throws IOException
	{
		String lOsName = System.getProperty("os.name");
		
		if (lOsName == null)
		{
            throw new IOException("os.name not found");
        }
		
		lOsName = lOsName.toLowerCase(Locale.ENGLISH);
		
		if (lOsName.matches("windows")) 
		{
			 mPlatform = OS.windows;
		}
		
		return null;
	}
	
	/**
	 * @return
	 */
	public static OS getPlatform() 
	{
		return mPlatform;
	}
	
	public static void fillInfo(WebDriver iDriver)
	{
		if (CheckUtil.checkInputable(iDriver, "username")) 
		{
			iDriver.findElement(By.id("username")).sendKeys(MOConfig.getConfig(MOConstant.MO_LOGIN_USER));
		}
		
		if (CheckUtil.checkInputable(iDriver, "password")) 
		{
			iDriver.findElement(By.id("password")).sendKeys(MOConfig.getConfig(MOConstant.MO_LOGIN_PASSWORD));
			iDriver.findElement(By.id("password")).sendKeys(Keys.ENTER);
			
		}
		
		//waitClickId(iDriver, "Login");
		
	}
	
	public static WebDriver initChromeDriver() 
	{
		System.setProperty(MOConstant.WEBDRIVER_CHROME_DRIVER, MOConfig.getConfig(MOConstant.WEBDRIVER_CHROME_DRIVER));
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}
	
	
	/*class WatchDog {
	    Timer timer;
	    boolean isLoaded;
	    WebDriver driver;

	    public WatchDog(int seconds, WebDriver iDriver) {
	        timer = new Timer();
	        timer.schedule(new RemindTask(), seconds*1000);
	        isLoaded = iDriver.getWindowHandles().size() > 1;
	        driver = iDriver;
		}
	    
	    public WebDriver getDriver()
	    {
	    	return driver;
	    }

	    class RemindTask extends TimerTask {
	        public void run() {
	            
	            if (isLoaded) 
	            {
	            	driver = handleLogin(driver);
	            	timer.cancel(); //Terminate the timer thread
	            }
	            
	        }
	    }
	}*/
	
	public static void openSetting(WebDriver iDriver, String iName) throws InterruptedException
	{
		if (!isMenuDisplay(iDriver, iName))
		{
			menu(iDriver);
		}
		Thread.sleep(2000);
		//iDriver.findElement(By.xpath("//a[contains(text(), '"+iName+"')]")).click();
		
		Map<String, WebElement> settings =  parseElement(iDriver, "li");
		settings.get(iName).click();
	}
	
	private static Map<String, WebElement> parseElement(WebDriver iDriver, String iTag)
	{
		
		Map<String, WebElement> lSettings = new HashMap<String, WebElement>();
		List<WebElement> lEleList = iDriver.findElements(By.tagName(iTag));
		for(WebElement lEle: lEleList)
		{
			if (lEle.getText() != null){
				lSettings.put(lEle.getText(), lEle);
			}
		}
		return lSettings;
	}
	
	/**
	 * Refresh data from the changing of master table
	 * @param iDriver
	 */
	public static void refesh(WebDriver iDriver)
	{
		List<WebElement> lEleList = iDriver.findElements(By.tagName("a"));
		for(WebElement lEle: lEleList)
		{
			 if (lEle.getText().indexOf("再取得") != -1) {
				 lEle.click();
				 break;
			 }
		}
		
	}

	/**
	 * Open admin settings data
	 * @param iDriver
	 */
	public static void openDataAdmin(WebDriver iDriver) 
	{
		//WebDriverWait lWait = new WebDriverWait(iDriver, 10);
		//lWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='#/settings/data_admin']")));
		iDriver.findElement(By.cssSelector("a[href='#/settings/data_admin']")).click();
	}
	
	/**
	 * Click back link to go back previous windows
	 * @param iDriver
	 */
	public static void back(WebDriver iDriver) 
	{
		//WebDriverWait lWait = new WebDriverWait(iDriver, 10);
		//lWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[data-action='back']")));
		iDriver.findElement(By.cssSelector("a[data-action='back']")).click();
	}
	
	/**
	 * Open menu list of POS
	 * 
	 * @param iDriver
	 */
	public static void menu(WebDriver iDriver)
	{
		iDriver.findElement(By.id("menu")).click();
	}

	/**
	 * Take the picture of web element
	 * 
	 * @param iDriver
	 * @param lElem
	 * @param iFileName
	 * @throws IOException
	 */
	public static void screenShot(WebDriver iDriver, WebElement lElem, String iFileName) throws IOException
	{

		// Select the target WebElement

		// Take screenshot and save to file
		File screenshot = ((TakesScreenshot) iDriver).getScreenshotAs(OutputType.FILE);

		// Create instance of BufferedImage from captured screenshot
		BufferedImage img = ImageIO.read(screenshot);

		// Get the Height and Width of WebElement
		int height = lElem.getSize().height;
		int width = lElem.getSize().width;

		// Create Rectangle using Height and Width to get size
		Rectangle rect = new Rectangle(width, height);

		// Get location of WebElement in a Point
		Point p = lElem.getLocation();
		// This will provide X & Y co-ordinates of the WebElement

		// Create an image for WebElement using its size and location
		BufferedImage dest = img.getSubimage(p.getX(), p.getY(), rect.width, rect.height);
		// This will give image data specific to the WebElement

		// Write back the image data into a File object
		ImageIO.write(dest, "png", screenshot);

		// Copy the file to system ScreenshotPath
		FileUtils.copyFile(screenshot, new File(iFileName));
	}
	
	/**
	 * Click button cancel in Salesforce edit windows
	 * @param iDriver
	 * @param iPosWindow
	 * @return
	 * @throws InterruptedException
	 */
	public static WebDriver cancel(WebDriver iDriver, String iPosWindow) throws InterruptedException
	{
		WebDriverWait lWait = new WebDriverWait(iDriver, 10);
		lWait.until(ExpectedConditions.elementToBeClickable(By.name("cancel")));
		iDriver.findElement(By.name("cancel")).click();
		
		Thread.sleep(2000);
		iDriver.close();
		iDriver.switchTo().window(iPosWindow);
		
		iDriver.get(iDriver.getCurrentUrl());
		
		return iDriver;
	}
	
	/**
	 * Browse the records (data) of specified Salesforce object
	 * Example: 
	 *  When tab table is opened, the automation will click button Go for browse the data inside this tab.
	 * @param iDriver
	 * @throws InterruptedException
	 */
	public static void browse(WebDriver iDriver) throws InterruptedException
	{
		Thread.sleep(2000);
		//WebDriverWait lWait = new WebDriverWait(iDriver, 10);
		//lWait.until(ExpectedConditions.elementToBeClickable(By.id("go")));
		iDriver.findElement(By.name("go")).click();
	}
	
	public static void randomEdit(WebDriver iDriver) throws InterruptedException 
	{
		Thread.sleep(2000);
		//lWait.until(ExpectedConditions.visibilityOfAllElements(iDriver.findElements(By.linkText("Edit"))));
		List<WebElement> lElems = iDriver.findElements(By.linkText("Edit"));
		int lRandomFloor = random(lElems.size())+1;
		
		//System.out.println("Random Floor: "+lElems.get(lRandomFloor).getText());
		lElems.get(lRandomFloor).click();
	}
	
	/**
	 * Get random number with specified limited number except 1
	 * @param iLimit
	 * @return
	 */
	public static int random(int iLimit)
	{
		return new Random().nextInt(iLimit);
	}
	
	/**
	 * Checking for has alert or not
	 * @param iDriver
	 * @return
	 */
	public static boolean hasAlert(WebDriver iDriver)
	{
		try {
			WebDriverWait lAlertWait = new WebDriverWait(iDriver, 10);
			lAlertWait.until(ExpectedConditions.alertIsPresent());
			return true;
		}
		catch (TimeoutException e) {
			return false;
		}
	}
	
	/**
	 * Checking for display menu
	 * @param iDriver
	 * @param iMenuItem
	 * @return
	 */
	public static boolean isMenuDisplay(WebDriver iDriver, String iMenuItem){
		
		try {
			WebDriverWait lAlertWait = new WebDriverWait(iDriver, 10);
			lAlertWait.until(ExpectedConditions.elementToBeClickable(By.id("slide-menu")));
			if (iDriver.findElement(By.id("slide-menu")).isDisplayed()){
				return true;
			}
			return false;
		}
		catch (TimeoutException e) {
			return false;
		}
	}

	public static WebElement pickOrderTable(WebDriver mDriver, String lHoldTable) throws InterruptedException
	{
		Map<String, List<WebElement>> lFloorMap = TableUtil.parseAElement(mDriver);
		for (String lFloor: lFloorMap.keySet()) {
			List<WebElement> lTableElems = lFloorMap.get(lFloor);
			for (WebElement lElem: lTableElems)
			{
				if (lHoldTable.matches(lElem.getText().replaceAll("\\W", ""))){
					return lElem;
				}
			}
		}
		return null;
	}
	
	/**
	 * Random pick category
	 * @param iDriver
	 */
	public static void handleRdmCategory(WebDriver iDriver)
	{
		List<WebElement> lCategoryList = iDriver.findElements(By.cssSelector("li[ng-repeat='category in orderData.categories']"));
		WebElement lCategoryRdm = lCategoryList.get(random(lCategoryList.size()));
		System.out.println("Category: "+lCategoryRdm.getText());
		lCategoryRdm.click();
	}
	
	/**
	 * Random pick menu
	 * @param iDriver
	 */
	public static void handleRdmMenu(WebDriver iDriver)
	{
		List<WebElement> lMenuList = iDriver.findElements(By.cssSelector("a[ng-repeat='menu in orderData.menus | rowSlice:i:colnum"));
		WebElement lMenuRdm = lMenuList.get(random(lMenuList.size()));
		System.out.println("Menu: "+lMenuRdm.getText());
		lMenuRdm.click();
	}
	
	public static void handleRdmMale(WebDriver iDriver) {
		WebElement lMale = iDriver.findElement(By.id("male"));
	}
	
	public static void handleRdmFemale(WebDriver iDriver){
		WebElement lFemale = iDriver.findElement(By.id("female"));
	}
	
	public static void handleRdmComment(WebDriver iDriver){
		
	}
	
	public static void sendOrder(WebDriver iDriver){
		iDriver.findElement(By.linkText("SEND")).click();
	}
	
	public static void waitClickId(WebDriver iDriver, String iId)
	{
		if (!CheckUtil.checkExistID(iDriver, iId))
		{
			waitClickId(iDriver, iId);
		}
		
		iDriver.findElement(By.id(iId)).click();
	}
	
	public static void waitClickXPath(WebDriver iDriver, String iXPath)
	{
		if (!CheckUtil.checkExistXPath(iDriver, iXPath)) {
			waitClickXPath(iDriver, iXPath);
		}
		
		iDriver.findElement(By.xpath(iXPath)).click();
		
	}
	
	public static void waitClickCssSelector(WebDriver iDriver, String iCssSelector)
	{
		if (!CheckUtil.checkExistCssSelector(iDriver, iCssSelector)) {
			waitClickCssSelector(iDriver, iCssSelector);
		}
		
		iDriver.findElement(By.xpath(iCssSelector)).click();
		
	}
	
	public static void waitClickName(WebDriver iDriver, String iName) 
	{
		if (!CheckUtil.checkExistName(iDriver, iName)) {
			waitClickName(iDriver, iName);
		}
		
		iDriver.findElement(By.name(iName)).click();
	}
	
	public static void findnClick(WebDriver iDriver, FILTER iFilter, String iContent) 
	{
		switch (iFilter) {
		case ID: 
			waitClickId(iDriver, iContent);
			break;
		case CSS:
			waitClickCssSelector(iDriver, iContent);
			break;
		case XPATH:
			waitClickXPath(iDriver, iContent);
			break;
		case NAME:
			waitClickName(iDriver, iContent);
			break;
		}
		
	}

}
