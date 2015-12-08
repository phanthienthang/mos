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
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;

import h2.ui.se.mo.util.PosUtil.BY;

public class PosUtil 
{
	
	public enum OS { windows, mac }
	public enum BY {ID, XPATH, NAME, CSS, LINKTEXT, PARTIALLINKTEXT, TAGNAME, CLASS }
	
	protected static OS mPlatform = OS.windows;
	protected static Map<String, WebElement> configs;
	
	public static WebDriver init() throws InterruptedException 
	{
		
		System.setProperty(MOConstant.WEBDRIVER_CHROME_DRIVER, MOConfig.getConfig(MOConstant.WEBDRIVER_CHROME_DRIVER));

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--lang=ja");
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
	
	public static WebDriver initLocal() throws InterruptedException 
	{
		
		System.setProperty(MOConstant.WEBDRIVER_CHROME_DRIVER, MOConfig.getConfig(MOConstant.WEBDRIVER_CHROME_DRIVER));

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--lang=ja");
		
		options.addExtensions(new File(MOConfig.getConfig(MOConstant.MO_CHROME_EXTENSION)));
				// options.setBinary(new
				// File("E:\\WORK\\H2\\MyOrder\\Selenium\\chromedriver.exe"));

		// For use with ChromeDriver:
		WebDriver driver = new ChromeDriver(options);
		
		//driver.manage().window().maximize();
		driver.get("http://localhost:9000");

		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(driver.getCurrentUrl());
		
		return login(driver);
	}
	
	/**
	 * @param iDriver
	 * @return
	 * @throws InterruptedException
	 */
	protected static WebDriver login(WebDriver iDriver)
	{
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
		try {
			ArrayList<String> lTabs = new ArrayList<String> (iDriver.getWindowHandles());
			String lPosWindow = lTabs.get(0);
			String lLoginWindow = lTabs.get(1);
			
			iDriver.switchTo().window(lLoginWindow);
			findnClick(iDriver, BY.ID, "Login");
			
			//Enter user information
			loginInputHandle(iDriver);
			
			findnClick(iDriver, BY.ID, "oaapprove");
			
			iDriver.switchTo().window(lPosWindow);
			
			return isLoaded(iDriver);
		}
		catch (WebDriverException e) {
			return handleLogin(iDriver);
		}
	}
	
	private static WebDriver isLoaded(WebDriver iDriver)
	{
		try {
			if (iDriver.findElement(By.id("slide-menu")).isDisplayed()) {
				return iDriver;
			}
		}
		catch (WebDriverException e) {
			
		}
		return isLoaded(iDriver);
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
	
	public static void loginInputHandle(WebDriver iDriver)
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
	
	/**
	 * Order settings
	 * 
	 * @param iDriver
	 * @param iName
	 * @throws InterruptedException
	 */
	public static void openSetting(WebDriver iDriver, String iName)
	{
		if (!isMenuDisplay(iDriver, iName))
		{
			try {
				menu(iDriver);
			} catch (WebDriverException e) {
				openSetting(iDriver, iName);
			}
		}
		
		Map<String, WebElement> settings =  parseMenu(iDriver);
		if (settings.size() != 0 && settings.get(iName) != null) {
			waitnClick(iDriver, settings.get(iName));
		}
		else {
			openSetting(iDriver, iName);
		}
		
	}
	
	private static Map<String, WebElement> parseMenu(WebDriver iDriver) {
		Map<String, WebElement> lSettings = new HashMap<String, WebElement>();
		WebElement lSettingPanel = iDriver.findElement(By.id("side-menu-items"));
		if (lSettingPanel != null) {
			List<WebElement> lEleList = lSettingPanel.findElements(By.xpath(".//li"));
			for(WebElement lEle: lEleList)
			{
				if (lEle.getText() != null && !lEle.getText().trim().isEmpty()){
					lSettings.put(lEle.getText(), lEle);
				}
			}
			return lSettings;
		}
		else {
			return parseMenu(iDriver);
		}
		
	}
	
	public static void waitnClick(WebDriver iDriver, WebElement iElement) 
	{
		WebDriverWait lWait = new WebDriverWait(iDriver, 30);
		lWait.until(ExpectedConditions.elementToBeClickable(iElement));
		iElement.click();
	}
	
	
	/**
	 * Parse element
	 * 
	 * @param iDriver
	 * @param iTag
	 * @return
	 */
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
	 * @throws InterruptedException 
	 */
	public static void refesh(WebDriver iDriver) throws InterruptedException
	{
		Thread.sleep(1000);
		PosUtil.openSetting(iDriver, "設定"); //SETTINGS
		
		Thread.sleep(1000);
		PosUtil.openDataAdmin(iDriver);
		
		Thread.sleep(1000);
		List<WebElement> lEleList = iDriver.findElements(By.tagName("a"));
		for(WebElement lEle: lEleList)
		{
			 if (lEle.getText().indexOf("再取得") != -1) {
				 lEle.click();
				 break;
			 }
		}
		
		Thread.sleep(3000);
		
		iDriver.switchTo().alert().accept();
		Thread.sleep(2000);
		List<String> lWindows = new ArrayList<String>(iDriver.getWindowHandles());
		iDriver.switchTo().window(lWindows.get(0));
		PosUtil.back(iDriver);
		
		Thread.sleep(2000);
		PosUtil.menu(iDriver);
	
		Thread.sleep(1000);
		PosUtil.openSetting(iDriver, "注文"); //ORDER
	}

	/**
	 * Open admin settings data
	 * @param iDriver
	 */
	public static void openDataAdmin(WebDriver iDriver) 
	{
		//WebDriverWait lWait = new WebDriverWait(iDriver, 10);
		//lWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='#/settings/data_admin']")));
		try {
			iDriver.findElement(By.cssSelector("a[href='#/settings/data_admin']")).click();
		}
		catch (NoSuchElementException e) {
			openDataAdmin(iDriver);
		}
		
	}
	
	/**
	 * Click back link to go back previous windows
	 * @param iDriver
	 */
	public static void back(WebDriver iDriver) 
	{
		try {
			iDriver.findElement(By.cssSelector("a[data-action='back']")).click();
		}catch (WebDriverException e) {
			back(iDriver);
		}
		//WebDriverWait lWait = new WebDriverWait(iDriver, 10);
		//lWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[data-action='back']")));
		
	}
	
	public static void close(WebDriver iDriver) 
	{
		iDriver.findElement(By.cssSelector("a[data-action='close']")).click();
	}
	
	/**
	 * Open menu list of POS
	 * 
	 * @param iDriver
	 */
	public static void menu(WebDriver iDriver)
	{
		findnClick(iDriver, BY.ID, "menu");
	}

	/**
	 * Take the picture of web element
	 * 
	 * @param iDriver
	 * @param lElem
	 * @param iFileName
	 * @throws IOException
	 */
	public static void takeScreenShot(WebDriver iDriver, WebElement lElem, String iFileName) throws IOException
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
		findnClick(iDriver, BY.NAME, "cancel");
		
		iDriver.close();
		iDriver.switchTo().window(iPosWindow);
		
		iDriver.get(iDriver.getCurrentUrl());
		
		return iDriver;
	}
	
	public static void cancel(WebDriver iDriver) {
		try {
			findnClick(iDriver, BY.LINKTEXT, "キャンセル");
		}
		catch (WebDriverException e) {
			cancel(iDriver);
		}
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
		//WebDriverWait lWait = new WebDriverWait(iDriver, 10);
		//lWait.until(ExpectedConditions.elementToBeClickable(By.id("go")));
		findnClick(iDriver, BY.NAME, "go");
	}
	
	public static void randomEdit(WebDriver iDriver)
	{
		if(checkElements(iDriver, BY.LINKTEXT, "Edit")) {
			//lWait.until(ExpectedConditions.visibilityOfAllElements(iDriver.findElements(By.linkText("Edit"))));
			List<WebElement> lElems = iDriver.findElements(By.linkText("Edit"));
			int lRandomFloor = random(lElems.size())+1;
			
			//System.out.println("Random Floor: "+lElems.get(lRandomFloor).getText());
			lElems.get(lRandomFloor).click();
		}
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
	
	public static void handleAlert(WebDriver iDriver)
	{
		try 
		{
			String lPosWindow = new ArrayList<String> (iDriver.getWindowHandles()).get(0);
			iDriver.switchTo().alert().accept();
			if (PosUtil.hasAlert(iDriver))
			{
				iDriver.switchTo().alert().dismiss();
			}
			iDriver.switchTo().window(lPosWindow);
		} catch (NoAlertPresentException e) {
			handleAlert(iDriver);
		}
	}
	
	/**
	 * Checking for display menu
	 * @param iDriver
	 * @param iMenuItem
	 * @return
	 */
	public static boolean isMenuDisplay(WebDriver iDriver, String iMenuItem){
		
		try 
		{
			WebDriverWait lAlertWait = new WebDriverWait(iDriver, 30);
			lAlertWait.until(ExpectedConditions.elementToBeClickable(By.id("slide-menu")));
			if (iDriver.findElement(By.id("slide-menu")).isDisplayed())
			{
				return true;
			}
			return false;
		}
		catch (TimeoutException e) {
			return false;
		}
		
	}

	public static WebElement pickOrderTable(WebDriver iDriver, String iTable)
	{
		System.out.println("Call method pickOrderTable(WebDriver iDriver, String iTable) in PosUtil.java");
		Map<String, List<WebElement>> lFloorMap = TableUtil.parseAElement(iDriver);
		System.out.println("Holding table : "+ iTable);
		if (lFloorMap.size() != 0) {
			for (String lFloor: lFloorMap.keySet()) {
				List<WebElement> lTableElems = lFloorMap.get(lFloor);
				for (WebElement lElem: lTableElems)
				{
					//System.out.println("Element Table : "+ lElem.getText());
					if (lElem.getText().indexOf(iTable) != -1)
					{
						return lElem;
					}
				}
			}
			return null;
		}
		else {
			return pickOrderTable(iDriver, iTable);
		}
		
	}
	
	public static WebElement pickRdmTable(WebDriver iDriver) 
	{
		System.out.println("Call method pickRdmTable(WebDriver iDriver) in PosUtil.java");
		Map<String, List<WebElement>> lFloorMap = TableUtil.parseAElement(iDriver);
		if (lFloorMap.size() != 0) {
			int lRdmFloor = random(lFloorMap.keySet().size());
			//int lCount = 0; 
			List<String> lFloorList = new ArrayList<String>();
			
			for (String lFloor: lFloorMap.keySet()) {
				lFloorList.add(lFloor);
			}
			
			List<WebElement> lTableElems = lFloorMap.get(lFloorList.get(lRdmFloor));
			
			return  lTableElems.get(random(lTableElems.size()));
		}
		else {
			return pickRdmTable(iDriver);
		}
	}
	
	public static WebElement pickRdmUsingTable(WebDriver iDriver) 
	{
		System.out.println("Call method pickRdmTable(WebDriver iDriver) in PosUtil.java");
		Map<String, List<WebElement>> lFloorMap = TableUtil.parseUsingTable(iDriver);
		if (lFloorMap.size() != 0) {
			int lRdmFloor = random(lFloorMap.keySet().size());
			//int lCount = 0; 
			List<String> lFloorList = new ArrayList<String>();
			
			for (String lFloor: lFloorMap.keySet()) {
				lFloorList.add(lFloor);
			}
			
			List<WebElement> lTableElems = lFloorMap.get(lFloorList.get(lRdmFloor));
			
			return  lTableElems.get(0);
		}
		else {
			return pickRdmUsingTable(iDriver);
		}
	}
	
	/**
	 * Random pick category
	 * @param iDriver
	 * @throws InterruptedException 
	 */
	public static void handleRdmCategory(WebDriver iDriver)
	{
		List<WebElement> lCategoryList = findElements(iDriver, BY.CSS, "li[ng-repeat='category in orderData.categories']");
		if (lCategoryList.size() != 0) {
			WebElement lCategoryRdm = lCategoryList.get(random(lCategoryList.size()));
			if (lCategoryRdm != null) {
				try 
				{
					lCategoryRdm.click();
				} 
				catch (WebDriverException e)
				{
					handleRdmCategory(iDriver);
				}
			}
			else {
				lCategoryList.get(0).click();
			}
		} else {
			handleRdmCategory(iDriver);
		}
		
	}
	
	/**
	 * Random pick menu
	 * @param iDriver
	 * @throws InterruptedException 
	 */
	public static void handleRdmMenu(WebDriver iDriver)
	{
		List<WebElement> lMenuList = findElements(iDriver, BY.CSS, "a[ng-repeat='menu in orderData.menus | rowSlice:i:colnum");
		if (lMenuList.size() != 0) {
			WebElement lMenuRdm = lMenuList.get(random(lMenuList.size()));
			if (lMenuRdm != null) {
				
				try 
				{
					lMenuRdm.click();
				} 
				catch (WebDriverException e)
				{
					handleRdmMenu(iDriver);
				}
				
			}
			else {
				lMenuList.get(0).click();
			}
			lMenuRdm.click();
		}
		else {
			handleRdmMenu(iDriver);
		}
		
	}
	
	/**
	 * Random pick menu
	 * @param iDriver
	 * @throws InterruptedException 
	 */
	public static void handleRdmMenu(WebDriver iDriver, int iQuantity)
	{
		List<WebElement> lMenuList = findElements(iDriver, BY.CSS, "a[ng-repeat='menu in orderData.menus | rowSlice:i:colnum");
		if (lMenuList.size() != 0) {
			WebElement lMenuRdm = lMenuList.get(random(lMenuList.size()));
			if (lMenuRdm != null) {
				
				try 
				{
					for (int i = 0; i< iQuantity; i++) {
						lMenuRdm.click();
					}
					
				} 
				catch (WebDriverException e)
				{
					handleRdmMenu(iDriver);
				}
				
			}
			else {
				lMenuList.get(0).click();
			}
		}
		else {
			handleRdmMenu(iDriver);
		}
		
	}
	
	private static void handleRdmMale(WebDriver iDriver)
	{
		handleRdmSex(iDriver, "male");
	}
	
	private static void handleRdmFemale(WebDriver iDriver)
	{
		handleRdmSex(iDriver, "female");
	}
	
	private static void handleRdmSex(WebDriver iDriver, String iSex)
	{
		System.out.println("Enter sex option: "+ iSex);
		WebElement lSex = iDriver.findElement(By.id(iSex));
		if (lSex != null) {
			List<WebElement> lOptionList = lSex.findElements(By.xpath(".//option"));
			int lRdmNo = random(lOptionList.size());
			try {
				lOptionList.get(lRdmNo).click();
			}
			catch (ElementNotVisibleException e) {
				handleRdmSex(iDriver, iSex);
			}
		}
		else {
			handleRdmSex(iDriver, iSex);
		}
		
	}
	
	public static void handleRdmComment(WebDriver iDriver)
	{
		
	}
	
	public static void handleComment(WebDriver iDriver, String iComment)
	{
		//findnClick(iDriver, BY.ID, "comment");
		iDriver.findElement(By.id("comment")).sendKeys(iComment);
	}
	
	public static void sendOrder(WebDriver iDriver)
	{
		System.out.println("Call method sendOrder(Webdriver iDriver) ");
		findnClick(iDriver, BY.LINKTEXT, "送信");
		
	}
	
	private static void waitClickId(WebDriver iDriver, String iId)
	{
		System.out.println("Call method waitClickId(WebDriver iDriver, String iId). With ID: " + iId);
		if (!CheckUtil.checkExistID(iDriver, iId))
		{
			waitClickId(iDriver, iId);
		}
		iDriver.findElement(By.id(iId)).click();
		
	}
	
	private static void waitClickXPath(WebDriver iDriver, String iXPath)
	{
		if (!CheckUtil.checkExistXPath(iDriver, iXPath)) {
			waitClickXPath(iDriver, iXPath);
		}
		
		iDriver.findElement(By.xpath(iXPath)).click();
	}
	
	private static void waitClickCssSelector(WebDriver iDriver, String iCssSelector)
	{
		if (!CheckUtil.checkExistCssSelector(iDriver, iCssSelector)) 
		{
			waitClickCssSelector(iDriver, iCssSelector);
		}
		
		iDriver.findElement(By.xpath(iCssSelector)).click();
		
	}
	
	private static void waitClickName(WebDriver iDriver, String iName) 
	{
		if (!CheckUtil.checkExistName(iDriver, iName)) {
			waitClickName(iDriver, iName);
		}
		
		iDriver.findElement(By.name(iName)).click();
	}
	
	private static void waitClickLink(WebDriver iDriver, String iLink) 
	{
		if (!CheckUtil.checkExistLink(iDriver, iLink)) {
			waitClickLink(iDriver, iLink);
		}
		
		iDriver.findElement(By.linkText(iLink)).click();
	}
	
	
	/**
	 * Find and click
	 * 
	 * @param iDriver
	 * @param iFilter
	 * @param iContent
	 */
	public static void findnClick(WebDriver iDriver, BY iFilter, String iContent) 
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
		case LINKTEXT:
			waitClickLink(iDriver, iContent);
			break;
		}
		
	}
	
	/**
	 * @param iDriver
	 * @param iBy
	 * @param iContent
	 * @return
	 */
	public static List<WebElement> findElements(WebDriver iDriver, BY iBy, String iContent)
	{
		List<WebElement> lElementList = new ArrayList<WebElement>();
		
		
		if (checkElements(iDriver, iBy, iContent)) 
		{
			switch (iBy) 
			{
			case CSS:
				lElementList = iDriver.findElements(By.cssSelector(iContent));
				break;
			case LINKTEXT:
				lElementList = iDriver.findElements(By.linkText(iContent));
				break;
			case TAGNAME:
				lElementList = iDriver.findElements(By.tagName(iContent));
				break;
			case CLASS:
				lElementList = iDriver.findElements(By.className(iContent));
				break;
			case PARTIALLINKTEXT:
				lElementList = iDriver.findElements(By.partialLinkText(iContent));
				break;
			}
		}
		
		return lElementList;
	}
	
	
	
	private static boolean isElementsExist(WebDriver iDriver, BY iBy, String iContent) 
	{
		List<WebElement> lElementList = new ArrayList<WebElement>();
		try {
			WebDriverWait lWait = new WebDriverWait(iDriver, 30);
			switch (iBy) {
			case LINKTEXT:
				lElementList = lWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.linkText(iContent)));
				break;
			case XPATH:
				lElementList = lWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(iContent)));
				break;
			case CSS:
				lElementList = lWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(iContent)));
				break;
			}
			
			if (lElementList != null && lElementList.size() != 0) {
				return true;
			}
		}
		catch (TimeoutException e)
		{
			new Throwable(e.getMessage());
		}
		
		return false;
	}
	
	/**
	 * @param iDriver
	 * @param iBY
	 * @param iContent
	 * @return
	 */
	private static boolean checkElements(WebDriver iDriver, BY iBY, String iContent) 
	{
		return isElementsExist(iDriver, iBY, iContent);
	}

	private static void handleServiceCharge(WebDriver iDriver, int iServiceCharge)
	{
		iDriver.findElement(By.id("service_rate")).sendKeys(String.valueOf(iServiceCharge));
	}

	private static void handleDiscountRate(WebDriver iDriver, int iRate) 
	{
		iDriver.findElement(By.id("discount_rate")).sendKeys(String.valueOf(iRate));
	}

	public static void handleDiscountPrice(WebDriver iDriver, int iPrice)
	{
		iDriver.findElement(By.id("discount_price")).sendKeys(String.valueOf(iPrice));
	}

	public static void handleRdmComment(WebDriver iDriver, String iComment)
	{
		// TODO Not yet implemented
		
	}

	public static void orderByTable(WebDriver iDriver, String iTable, int iServiceCharge, int iRate, int iPrice, String iComment) throws InterruptedException {
		
		//POS Processing
		WebElement lTableElem = pickOrderTable(iDriver, iTable);
		System.out.println("Order table: " + iTable);
		handleOrder(iDriver, lTableElem, iServiceCharge, iRate, iPrice, iComment);
	}
	
	private static void handleOrder(WebDriver iDriver, WebElement iTableWeb, int iServiceCharge, int iRate, int iPrice, String iComment) throws InterruptedException
	{
		//PosUtil.openSetting(iDriver, "注文");
		
		
		iTableWeb.click();
		//pickTable(iDriver, iTableName);
		Thread.sleep(1000);
		handleRdmMale(iDriver);
		Thread.sleep(1000);
		handleRdmFemale(iDriver);
		Thread.sleep(1000);
		handleServiceCharge(iDriver, iServiceCharge);
		Thread.sleep(1000);
		handleDiscountRate(iDriver, iRate);
		Thread.sleep(1000);
		handleDiscountPrice(iDriver, iPrice);
		Thread.sleep(1000);
		handleComment(iDriver, iComment);
		Thread.sleep(1000);
		
		//Order
		PosUtil.order(iDriver);
		Thread.sleep(3000);
		
		//TODO - Implement the category check here!!!
		handleRdmCategory(iDriver);
		Thread.sleep(3000);
		handleRdmMenu(iDriver);
		Thread.sleep(3000);
		handleConfirm(iDriver);
		Thread.sleep(3000);
	}
	
	private static void handleOrder(WebDriver iDriver, WebElement iTableWeb, int iServiceCharge, int iRate, int iPrice, String iComment, int iMenuNo, int[] iMenuQuantity)
	{
		//PosUtil.openSetting(iDriver, "注文");
		iTableWeb.click();
		//pickTable(iDriver, iTableName);
		
		handleRdmMale(iDriver);
		
		handleRdmFemale(iDriver);
		
		handleServiceCharge(iDriver, iServiceCharge);
		
		handleDiscountRate(iDriver, iRate);
		
		handleDiscountPrice(iDriver, iPrice);
		
		handleComment(iDriver, iComment);
		
		//Order
		PosUtil.order(iDriver);
		
		//TODO - Implement the category check here!!!
		handleRdmCategory(iDriver);
		
		for (int i = 0; i < iMenuNo; i++) {
			handleRdmMenu(iDriver, iMenuQuantity[i]);
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		handleConfirm(iDriver);
	}
	
	private static void pickTable(WebDriver iDriver, String iTableName) {
		
		try {
			WebElement lTableElem = pickOrderTable(iDriver, iTableName);
			lTableElem.click();
		}
		catch (WebDriverException e) {
			pickTable(iDriver, iTableName);
		}
		
	}

	private static void order(WebDriver iDriver) 
	{
		try {
			findnClick(iDriver, BY.LINKTEXT, "注文");
		}
		catch (WebDriverException e) {
			order(iDriver);
		}
	}

	/**
	 * Random order menu
	 * 
	 * @param iDriver
	 * @throws InterruptedException
	 */
	public static String orderRandom(WebDriver iDriver) throws InterruptedException 
	{
		System.out.println("Call method orderRandom(WebDriver iDriver) in PosUtil.java");
		WebElement lRdmTable = pickRdmTable(iDriver);
		if (lRdmTable != null) {
			String lTableName = lRdmTable.getText().replaceAll("\\W", "");
			System.out.println("Random table: "+lTableName);
			if (!isTabAvailable(lRdmTable)) 
			{
				orderRandom(iDriver);
			}
			handleOrder(iDriver, lRdmTable, 0, 0, 0, "Random Order");
			return lTableName;
		}
		else {
			return orderRandom(iDriver);
		}
		
	}
	
	public static String orderRandom(WebDriver iDriver, int iService, int iRate, int iPrice) throws InterruptedException 
	{
		System.out.println("Call method orderRandom(WebDriver iDriver) in PosUtil.java");
		WebElement lRdmTable = pickRdmTable(iDriver);
		if (lRdmTable != null) {
			String lTableName = lRdmTable.getText().replaceAll("\\W", "");
			System.out.println("Random table: "+lTableName);
			if (!isTabAvailable(lRdmTable)) 
			{
				orderRandom(iDriver,iService, iRate, iPrice);
			}
			handleOrder(iDriver, lRdmTable, iService, iRate, iPrice, " Order randomatically");
			return lTableName;
		}
		else {
			return orderRandom(iDriver, iService, iRate, iPrice);
		}
		
	}
	
	/**
	 * @param iDriver
	 * @param iRdmMenu
	 * @return
	 */
	public static String orderRandom(WebDriver iDriver, int iService, int iRate, int iPrice, String iComment, int iRdmMenu, int[] iMenuQuantity) 
	{
		System.out.println("Call method orderRandom(WebDriver iDriver, int iService, int iRate, int iPrice, String iComment, int iRdmMenu, int[] iMenuQuantity)  in PosUtil.java");
		WebElement lRdmTable = pickRdmTable(iDriver);
		if (lRdmTable != null) {
			String lTableName = lRdmTable.getText().replaceAll("\\W", "");
			System.out.println("Random table: "+lTableName);
			if (!isTabAvailable(lRdmTable)) 
			{
				orderRandom(iDriver, iService, iRate, iPrice, iComment, iRdmMenu, iMenuQuantity);
			} else {
				handleOrder(iDriver, lRdmTable, iService, iRate, iPrice, iComment, iRdmMenu, iMenuQuantity);
			}
			return lTableName;
		}
		else {
			return orderRandom(iDriver, iService, iRate, iPrice, iComment, iRdmMenu, iMenuQuantity);
		}
	}
	
	

	/**
	 * @param iDriver
	 */
	public static void handleConfirm(WebDriver iDriver) 
	{
		findnClick(iDriver, BY.LINKTEXT, "注文確認"); //ORDER CONFIRM
	}

	public static void screenshot(WebDriver iDriver, String iFileName) throws IOException 
	{
		// Select the target WebElement

				// Take screenshot and save to file
				File screenshot = ((TakesScreenshot) iDriver).getScreenshotAs(OutputType.FILE);

				// Create instance of BufferedImage from captured screenshot
				//BufferedImage img = ImageIO.read(screenshot);

				// Get the Height and Width of WebElement

				// Create Rectangle using Height and Width to get size
				//Rectangle rect = new Rectangle(width, height);

				// Get location of WebElement in a Point
				//Point p = lElem.getLocation();
				// This will provide X & Y co-ordinates of the WebElement

				// Create an image for WebElement using its size and location
				//BufferedImage dest = img.getSubimage(p.getX(), p.getY(), rect.width, rect.height);
				// This will give image data specific to the WebElement

				// Write back the image data into a File object
				//ImageIO.write(dest, "png", screenshot);

				// Copy the file to system ScreenshotPath
				FileUtils.copyFile(screenshot, new File(iFileName));
	}
	
	public static boolean isTabAvailable(WebElement iTab)
	{
		if (iTab.getAttribute("class").contains("empty")) {
			return true;
		}
		
		return false;
	}

	public static void checkOut(WebDriver iDriver, String iTable) throws InterruptedException 
	{
		/*Thread.sleep(1000);
		PosUtil.openSetting(iDriver, "注文");
		Thread.sleep(2000);*/
		
		/*WebElement lTable = pickOrderTable(iDriver, iTable);
		
		Actions lBuilder = new Actions(iDriver);
		lBuilder.clickAndHold(lTable).build().perform();
		Thread.sleep(2000);
		lBuilder.release(lTable).build().perform();
		
		Thread.sleep(1000);
		iDriver.findElement(By.linkText("会計")).click();*/
		handleOption(iDriver, iTable, "会計");
	}

	public static void handleEdit(WebDriver iDriver, String iTable)
	{
		handleOption(iDriver, iTable, "編集");
	}
	
	private static void handleOption(WebDriver iDriver, String iTable, String iOption) 
	{
		try {
			WebElement lTable = pickOrderTable(iDriver, iTable);
			
			Actions lBuilder = new Actions(iDriver);
			lBuilder.clickAndHold(lTable).build().perform();
			try 
			{
				Thread.sleep(3000);
			} 
			catch (InterruptedException e)
			{
				System.out.println("Can not sleep");
			}
			lBuilder.release(lTable).build().perform();
			
			iDriver.findElement(By.linkText(iOption)).click();
		}
		catch (WebDriverException e)
		{
			handleOption(iDriver, iTable, iOption);
		}
	}
	
	public static void editRdmOrder(WebDriver iDriver, int iServiceCharge, int iRate, int iPrice, String iComment)
	{
		//PosUtil.openSetting(iDriver, "注文");
		
		//lTableList.get(lTableRdm).click();
		
		handleRdmMale(iDriver);
		
		handleRdmFemale(iDriver);
		
		handleServiceCharge(iDriver, iServiceCharge);
		
		handleDiscountRate(iDriver, iRate);
		
		handleDiscountPrice(iDriver, iPrice);
		
		handleComment(iDriver, iComment);
		
		done(iDriver);
		//Order
		//PosUtil.order(iDriver);
		
		//TODO - Implement the category check here!!!
		//handleRdmCategory(iDriver);
		
		//handleRdmMenu(iDriver);
		//handleConfirm(iDriver);
		
		
	}
	
	private static void done(WebDriver iDriver) {
		
		findnClick(iDriver, BY.LINKTEXT, "更新");
	}

	public static void viewTabHistory(WebDriver iDriver, String iTable) 
	{
		handleOption(iDriver, iTable, "注文履歴");
	}
	
	public static void addOrder(WebDriver iDriver, String iTable) {
		handleOption(iDriver, iTable, "追加注文");
	}

	public static void moveTable(WebDriver iDriver, String iTable) 
	{
		handleOption(iDriver, iTable, "移動");
	}

	public static void shareTable(WebDriver iDriver, String iTable) 
	{
		handleOption(iDriver, iTable, "相席");
	}

	public static void deleteTab(WebDriver iDriver, String iTable) {
		handleOption(iDriver, iTable, "削除");
		
	}

	public static void groupTab(WebDriver iDriver, String iTable) {
		handleOption(iDriver, iTable, "相席");
	}
	
	public static void sendData(WebDriver iDriver, String iID, String iData) 
	{
		
	}

	public static void pickCategory(WebDriver iDriver, String iCategory)
	{
		List<WebElement> lCategoryList = findElements(iDriver, BY.CSS, "li[ng-repeat='category in orderData.categories']");
		if (lCategoryList.size() != 0) {
			
			for (int i = 0; i < lCategoryList.size(); i++) {
				WebElement lCategory = lCategoryList.get(i);
				if (lCategory.getText().contains(iCategory)) {
					lCategory.click();
					break;
				}
			}
		} else {
			pickCategory(iDriver, iCategory);
		}
		
	}

	public static WebElement pickMenu(WebDriver iDriver, String iMenu) 
	{
		List<WebElement> lMenuList = findElements(iDriver, BY.CSS, "a[ng-repeat='menu in orderData.menus | rowSlice:i:colnum");
		if (lMenuList.size() != 0) {
			
			for (int i = 0; i < lMenuList.size(); i++) {
				WebElement lMenu = lMenuList.get(i);
				if (lMenu.getText().contains(iMenu)) {
					lMenu.click();
					return lMenu;
				}
			}
		}
		else {
			return pickMenu(iDriver, iMenu);
		}
		return null;
	}

	public static void holdMenu(WebDriver iDriver, String iMenu) {
		WebElement lMenu = pickMenu(iDriver, iMenu);
		Actions lBuilder = new Actions(iDriver);
		lBuilder.clickAndHold(lMenu).build().perform();
	}

}
