package h2.ui.se.mo.util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import h2.ui.se.mo.table.PosTable;

public class PosUtil 
{
	static Logger logger = Logger.getLogger(PosUtil.class);
	
	public enum OS { windows, mac }
	public enum BY {ID, XPATH, NAME, CSS, LINKTEXT, PARTIALLINKTEXT, TAGNAME, CLASS }
	
	protected static OS mPlatform = OS.windows;
	protected static Map<String, WebElement> configs;
	
	/**
	 * Initialize Chrome driver and auto install MyOrder application as an extension.
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public static WebDriver init() throws InterruptedException 
	{
		
		// For use with ChromeDriver:
		WebDriver driver = new ChromeDriver(initOption());
		
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
		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.get(driver.getCurrentUrl());
		
		return login(driver);
	}
	
	/**
	 * Initialize option
	 * 
	 * @return
	 */
	public static ChromeOptions initOption ()
	{
		System.setProperty(PosConstant.WEBDRIVER_CHROME_DRIVER, PosConfig.getConfig(PosConstant.WEBDRIVER_CHROME_DRIVER));

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--lang=ja");
		
		options.addExtensions(new File(PosConfig.getConfig(PosConstant.MO_CHROME_EXTENSION)));
		
		return options;
	}
	
	/**
	 * Initialize Chrome driver, install MyOrder application as an extension
	 * and run this application with localization.
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public static WebDriver initLocal() throws InterruptedException 
	{
		// For use with ChromeDriver:
		WebDriver driver = new ChromeDriver(initOption());
		
		//driver.manage().window().maximize();
		driver.get("http://localhost:9000");

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.get(driver.getCurrentUrl());
		
		return login(driver);
	}
	
	/**
	 * Login Salesforce to use MyOrder.
	 * 
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
	
	public static String getPosWindow(WebDriver iDriver) {
		
		return new ArrayList<String> (iDriver.getWindowHandles()).get(0);
	}
	
	/**
	 * @param iDriver
	 * @return
	 */
	protected static WebDriver handleLogin(WebDriver iDriver) 
	{
		try 
		{
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
		catch (WebDriverException e) 
		{
			logger.info("ERROR login: "+e.getMessage());
			return handleLogin(iDriver);
		}
	}
	
	/**
	 * Check the element is loaded or not.
	 * 
	 * @param iDriver
	 * @return
	 */
	public static WebDriver isLoaded(WebDriver iDriver)
	{
		try 
		{
			if (iDriver.findElement(By.id("slide-menu")).isDisplayed())
			{
				return iDriver;
			}
		}
		catch (WebDriverException e)
		{
			logger.info("System in loading: "+e.getMessage());
		}
		return isLoaded(iDriver);
	}
	
	/**
	 * Get the current platform.
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
	 * Get platform information
	 * @return
	 */
	public static OS getPlatform() 
	{
		return mPlatform;
	}
	
	/**
	 * @param iDriver
	 */
	public static void loginInputHandle(WebDriver iDriver)
	{
		if (PosValidator.isInputable(iDriver, "username")) 
		{
			iDriver.findElement(By.id("username")).sendKeys(PosConfig.getConfig(PosConstant.MO_LOGIN_USER));
		}
		
		if (PosValidator.isInputable(iDriver, "password")) 
		{
			iDriver.findElement(By.id("password")).sendKeys(PosConfig.getConfig(PosConstant.MO_LOGIN_PASSWORD));
			iDriver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		}
	}
	
	/**
	 * Init Chrome Driver
	 * @return
	 */
	public static WebDriver initChromeDriver() 
	{
		System.setProperty(PosConstant.WEBDRIVER_CHROME_DRIVER, PosConfig.getConfig(PosConstant.WEBDRIVER_CHROME_DRIVER));
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}
	
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
			try 
			{
				menu(iDriver);
			} 
			catch (WebDriverException e) 
			{
				openSetting(iDriver, iName);
			}
		}
		
		Map<String, WebElement> settings =  parseMenu(iDriver);
		if (settings.size() != 0 && settings.get(iName) != null)
		{
			waitnClick(iDriver, settings.get(iName));
		}
		else 
		{
			openSetting(iDriver, iName);
		}
	}
	
	/**
	 * Browse Menu Items
	 * @param iDriver
	 * @return
	 */
	private static Map<String, WebElement> parseMenu(WebDriver iDriver) 
	{
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
	
	/**
	 * Wait at least 30 seconds for element and click 
	 * 
	 * @param iDriver
	 * @param iElement
	 */
	public static void waitnClick(WebDriver iDriver, WebElement iElement) 
	{
		WebDriverWait lWait = new WebDriverWait(iDriver, 30);
		lWait.until(ExpectedConditions.elementToBeClickable(iElement));
		iElement.click();
	}
	
	/**
	 * Refresh data from the changing of master table
	 * @param iDriver
	 * @throws InterruptedException 
	 */
	public static void refesh(WebDriver iDriver)
	{
		try
		{
			Thread.sleep(1000);
			PosUtil.openSetting(iDriver, "設定"); //SETTINGS
			
			Thread.sleep(1000);
			PosUtil.openDataAdmin(iDriver);
			
			Thread.sleep(1000);
			List<WebElement> lEleList = iDriver.findElements(By.tagName("a"));
			for(WebElement lEle: lEleList)
			{
				 if (lEle.getText().indexOf("再取得") != -1)
				 {
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
		catch (Exception e)
		{
			logger.info("Error at refresh: "+ e.getMessage(), new Throwable(e.getMessage()));
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
		try 
		{
			iDriver.findElement(By.cssSelector("a[data-action='back']")).click();
		}
		catch (WebDriverException e) 
		{
			logger.info("Error when click back: "+ e.getMessage(), new Throwable(e.getMessage()));
			back(iDriver);
		}
		
	}
	
	/**
	 * Click close link
	 * @param iDriver
	 */
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
	
	/**
	 * @param iDriver
	 */
	public static void cancel(WebDriver iDriver) {
		try 
		{
			findnClick(iDriver, BY.LINKTEXT, "キャンセル");
		}
		catch (WebDriverException e)
		{
			logger.info("Error when click cancel: "+ e.getMessage(), new Throwable(e.getMessage()));
		}
	}
	
	/**
	 * @param iDriver
	 */
	public static void randomEdit(WebDriver iDriver)
	{
		try {
			if(checkElements(iDriver, BY.LINKTEXT, "Edit")) {
				//lWait.until(ExpectedConditions.visibilityOfAllElements(iDriver.findElements(By.linkText("Edit"))));
				List<WebElement> lElems = iDriver.findElements(By.linkText("Edit"));
				int lRandomFloor = PosUtil.random(lElems.size())+1;
				
				//System.out.println("Random Floor: "+lElems.get(lRandomFloor).getText());
				lElems.get(lRandomFloor).click();
			}
		}
		catch (Exception e) {
			logger.info("Error with random edit: "+ e.getMessage(), new Throwable(e.getMessage()));
		}
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
		catch (TimeoutException e) 
		{
			logger.info("Timeout exception error: "+ e.getMessage(), new Throwable(e.getMessage()));
		}
		return false;
	}
	
	/**
	 * @param iDriver
	 */
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
			
			logger.error("Handle Alert error: "+ e.getMessage(), new Throwable(e.getMessage()));
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
			
		}
		catch (TimeoutException e) 
		{
			logger.error("Error at isMenuDisplay: "+ e.getMessage(), new Throwable(e.getMessage()));
		}
		return false;
		
	}
	
	/**
	 * @param iDriver
	 * @param iId
	 */
	private static void waitClickId(WebDriver iDriver, String iId)
	{
		if (!PosValidator.isExistID(iDriver, iId))
		{
			waitClickId(iDriver, iId);
		}
		iDriver.findElement(By.id(iId)).click();
		
	}
	
	/**
	 * @param iDriver
	 * @param iXPath
	 */
	private static void waitClickXPath(WebDriver iDriver, String iXPath)
	{
		if (!PosValidator.isExistXPath(iDriver, iXPath)) {
			waitClickXPath(iDriver, iXPath);
		}
		
		iDriver.findElement(By.xpath(iXPath)).click();
	}
	
	/**
	 * @param iDriver
	 * @param iCssSelector
	 */
	private static void waitClickCssSelector(WebDriver iDriver, String iCssSelector)
	{
		if (!PosValidator.isExistCssSelector(iDriver, iCssSelector)) 
		{
			waitClickCssSelector(iDriver, iCssSelector);
		}
		
		iDriver.findElement(By.xpath(iCssSelector)).click();
		
	}
	
	/**
	 * @param iDriver
	 * @param iName
	 */
	private static void waitClickName(WebDriver iDriver, String iName) 
	{
		if (!PosValidator.isExistName(iDriver, iName)) {
			waitClickName(iDriver, iName);
		}
		
		iDriver.findElement(By.name(iName)).click();
	}
	
	/**
	 * @param iDriver
	 * @param iLink
	 */
	private static void waitClickLink(WebDriver iDriver, String iLink) 
	{
		if (!PosValidator.isExistLink(iDriver, iLink)) {
			waitClickLink(iDriver, iLink);
		}
		
		iDriver.findElement(By.linkText(iLink)).click();
	}
	
	
	/**
	 * Find and click
	 * 
	 * @param iDriver
	 * @param iBy
	 * @param iContent
	 */
	public static void findnClick(WebDriver iDriver, BY iBy, String iContent) 
	{
		switch (iBy) {
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
		default:
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
			default:
				break;
			}
		}
		
		return lElementList;
	}
	
	
	/**
	 * @param iDriver
	 * @param iBy
	 * @param iContent
	 * @return
	 */
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
			default:
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

	
	
	/**
	 * @param iDriver
	 * @param iTableName
	 */
	public static void pickTable(WebDriver iDriver, String iTableName) {
		
		try {
			WebElement lTableElem = PosTable.pickOrderTable(iDriver, iTableName);
			lTableElem.click();
		}
		catch (WebDriverException e) {
			pickTable(iDriver, iTableName);
		}
		
	}
	
	/**
	 * @param iDriver
	 */
	public static void save(WebDriver iDriver)
	{
		//完了
		PosUtil.findnClick(iDriver, BY.LINKTEXT, "完了");
	}

	public static void order(WebDriver iDriver) 
	{
		try {
			findnClick(iDriver, BY.LINKTEXT, "注文");
		}
		catch (WebDriverException e) {
			order(iDriver);
		}
	}

	/**
	 * @param iDriver
	 */
	public static void handleConfirm(WebDriver iDriver) 
	{
		findnClick(iDriver, BY.LINKTEXT, "注文確認"); //ORDER CONFIRM
	}

	/**
	 * @param iDriver
	 * @param iTable
	 * @throws InterruptedException
	 */
	public static void checkOut(WebDriver iDriver, String iTable) throws InterruptedException 
	{
		handleOption(iDriver, iTable, "会計");
	}

	/**
	 * @param iDriver
	 * @param iTable
	 */
	public static void handleEdit(WebDriver iDriver, String iTable)
	{
		handleOption(iDriver, iTable, "編集");
	}
	
	/**
	 * @param iDriver
	 * @param iTable
	 * @param iOption
	 */
	private static void handleOption(WebDriver iDriver, String iTable, String iOption) 
	{
		try {
			WebElement lTable = PosTable.pickOrderTable(iDriver, iTable);
			
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
	
	/**
	 * Check out history
	 * 
	 * @param iDriver
	 * @param iTable
	 */
	public static void viewTabHistory(WebDriver iDriver, String iTable) 
	{
		handleOption(iDriver, iTable, "注文履歴");
	}
	
	/**
	 * Add order
	 * @param iDriver
	 * @param iTable
	 */
	public static void addOrder(WebDriver iDriver, String iTable) {
		handleOption(iDriver, iTable, "追加注文");
	}

	/**
	 * Move table / group table
	 * @param iDriver
	 * @param iTable
	 */
	public static void moveTable(WebDriver iDriver, String iTable) 
	{
		handleOption(iDriver, iTable, "移動");
	}

	/**
	 * Sharing check between multiple table
	 * @param iDriver
	 * @param iTable
	 */
	public static void shareTable(WebDriver iDriver, String iTable) 
	{
		handleOption(iDriver, iTable, "相席");
	}

	/**
	 * Delete tab 
	 * @param iDriver
	 * @param iTable
	 */
	public static void deleteTab(WebDriver iDriver, String iTable) {
		handleOption(iDriver, iTable, "削除");
		
	}

	/**
	 * Group tab
	 * @param iDriver
	 * @param iTable
	 */
	public static void groupTab(WebDriver iDriver, String iTable) {
		handleOption(iDriver, iTable, "相席");
	}
	
	/**
	 * Pick specified category
	 * @param iDriver
	 * @param iCategory
	 */
	public static void pickCategory(WebDriver iDriver, String iCategory)
	{
		List<WebElement> lCategoryList = findElements(iDriver, BY.CSS, "li[ng-repeat='category in orderData.categories']");
		if (lCategoryList.size() != 0) {
			
			for (int i = 0; i < lCategoryList.size(); i++) 
			{
				WebElement lCategory = lCategoryList.get(i);
				if (lCategory.getText().contains(iCategory)) 
				{
					lCategory.click();
					break;
				}
			}
		}
		
	}

	/**
	 * Pick specified menu
	 * 
	 * @param iDriver
	 * @param iMenu
	 * @return
	 */
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
		
		return null;
	}

	public static void holdMenu(WebDriver iDriver, String iMenu) 
	{
		WebElement lMenu = pickMenu(iDriver, iMenu);
		Actions lBuilder = new Actions(iDriver);
		lBuilder.clickAndHold(lMenu).build().perform();
	}
	
	
	public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
	public static final Random mRandom = new Random();
	public static final Set<String> mIdentifier = new HashSet<String>();
	
	/**
	 * Get random number with specified limited number
	 * @param iLimit
	 * @return
	 */
	public static int random(int iLimit)
	{
		return new Random().nextInt(iLimit);
	}
	
	/**
	 * @param iDriver
	 * @param iFileName
	 * @throws IOException
	 */
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
	
	/**
	 * @return
	 */
	public String getRandomName()
	{
		StringBuilder builder = new StringBuilder();
		while (builder.toString().length() == 0) 
		{
			int length = mRandom.nextInt(5) + 5;
			for (int i = 0; i < length; i++)
				builder.append(CHARACTERS.charAt(mRandom.nextInt(CHARACTERS.length())));
			if (mIdentifier.contains(builder.toString()))
				builder = new StringBuilder();
		}
		return builder.toString();
	}
	
	/**
	 * Dismiss both alert and confirm
	 * @param iDriver
	 */
	public static void dismiss(WebDriver iDriver) 
	{
		try 
		{
			String lPosWindow = new ArrayList<String> (iDriver.getWindowHandles()).get(0);
			iDriver.switchTo().alert().dismiss();
			if (PosUtil.hasAlert(iDriver)) {
				iDriver.switchTo().alert().dismiss();
			}
			iDriver.switchTo().window(lPosWindow);
		}
		catch (NoAlertPresentException e)
		{
			logger.info(PosConstant.MESSAGE_ERROR_ALERT_HANDLE, new Throwable(e.getMessage()));
		}
	}

}
