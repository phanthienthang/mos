package h2.ui.se.mo.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import h2.ui.se.mo.floor.Floor;
import h2.ui.se.mo.table.Table;
import h2.ui.se.mo.util.PosUtil.BY;

/**
 * @author thienthang
 *
 */
public class PosBrowser 
{
	
	static Logger logger = Logger.getLogger(PosBrowser.class);
	
	/**
	 * @param iDriver
	 * @throws InterruptedException
	 */
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
			logger.info("Doesn't support other OS except Windows and Mac");
			break;
		}
	}
	
	/**
	 * @param iDriver
	 * @throws InterruptedException
	 */
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
			logger.info("Doesn't support other OS except Windows and Mac");
			break;
		}
		
	}
	
	/**
	 * @param iDriver
	 * @throws InterruptedException
	 */
	public static void loginSalesforce(WebDriver iDriver) throws InterruptedException{
		
		//Add Tab
		addTab(iDriver);
		
		ArrayList<String> lTabs = new ArrayList<String> (iDriver.getWindowHandles());
		//System.out.println(lTabs.get(0));
		//System.out.println(lTabs.get(1));
		iDriver.switchTo().window(lTabs.get(1));
		iDriver.get(PosConstant.SFDC_URL);
		
		PosUtil.findnClick(iDriver, BY.ID, PosConstant.SFDC_BTN_ID_LOGIN);
		PosUtil.loginInputHandle(iDriver);
	}
	
	/**
	 * @param iDriver
	 */
	public static void openAllMOTab(WebDriver iDriver)
	{
		try {
			WebDriverWait lDriverWait = new WebDriverWait(iDriver,30);
			lDriverWait.until(ExpectedConditions.elementToBeClickable(By.id(PosConstant.SFDC_BTN_ID_ALL_TAB)));
			iDriver.findElement(By.id(PosConstant.SFDC_BTN_ID_ALL_TAB)).click();
		}
		catch (ElementNotVisibleException e) {
			logger.info("ERROR WHEN CLICK ALL TAB BUTTON", new Throwable(e.getMessage()));
		}
		
	}
	
	/**
	 * @param iDriver
	 */
	public static void newHandle(WebDriver iDriver)
	{
		try
		{
			WebDriverWait lDriverWait = new WebDriverWait(iDriver,30);
			lDriverWait.until(ExpectedConditions.elementToBeClickable(By.name(PosConstant.SFDC_BTN_NAME_NEW)));
			iDriver.findElement(By.name(PosConstant.SFDC_BTN_NAME_NEW)).click();
		} 
		catch (ElementNotVisibleException e)
		{
			logger.info("ERROR WHEN CLICK NEW BUTTON", new Throwable(e.getMessage()));
		}
		
	}
	
	/**
	 * @param iDriver
	 */
	public static void kontinue(WebDriver iDriver) {
		
		try {
			WebDriverWait lDriverWait = new WebDriverWait(iDriver,30);
			lDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Continue']")));
			iDriver.findElement(By.xpath("//input[@value='Continue']")).click();
		} catch (ElementNotVisibleException e) {
			kontinue(iDriver);
		}
	}
	
	/**
	 * Browse the records (data) of specified Salesforce object
	 * Example: 
	 *  When tab table is opened, the automation will click button Go for browse the data inside this tab.
	 * @param iDriver
	 * @throws InterruptedException
	 */
	public static void go(WebDriver iDriver) throws InterruptedException
	{
		//WebDriverWait lWait = new WebDriverWait(iDriver, 10);
		//lWait.until(ExpectedConditions.elementToBeClickable(By.id("go")));
		PosUtil.findnClick(iDriver, BY.NAME, "go");
	}
	
	/**
	 * @param iDriver
	 */
	public static void saveNew(WebDriver iDriver) {
		try {
			WebDriverWait lDriverWait = new WebDriverWait(iDriver,30);
			lDriverWait.until(ExpectedConditions.elementToBeClickable(By.name("save_new")));
			iDriver.findElement(By.name("save_new")).click();
		} catch (ElementNotVisibleException e) {
			saveNew(iDriver);
		}
	}
	
	/**
	 * @param iDriver
	 */
	public static void cancel(WebDriver iDriver) {
		try {
			WebDriverWait lDriverWait = new WebDriverWait(iDriver,30);
			lDriverWait.until(ExpectedConditions.elementToBeClickable(By.name("cancel")));
			iDriver.findElement(By.name("cancel")).click();
		} catch (ElementNotVisibleException e) {
			cancel(iDriver);
		}
	}

	/**
	 * @param iDriver
	 */
	public static void save(WebDriver iDriver) {
		try {
			WebDriverWait lDriverWait = new WebDriverWait(iDriver,10);
			lDriverWait.until(ExpectedConditions.elementToBeClickable(By.name("save")));
			iDriver.findElement(By.name("save")).click();
		} catch (ElementNotVisibleException e) {
			save(iDriver);
		}
		
	}
	
	public static void browse(WebDriver iDriver, String iTab, String iRecord) throws InterruptedException
	{
		loginSalesforce(iDriver);
		
		//Open All the tabs
		openAllMOTab(iDriver);		
		
		openTab(iDriver, iTab);
		
		search(iDriver, iTab, iRecord);
		viewSearchResult(iDriver, iRecord);
	}
	
	public static void openTab(WebDriver iDriver, String iTabName)
	{
		WebDriverWait lDriverWait = new WebDriverWait(iDriver,10);
		lDriverWait.until(ExpectedConditions.elementToBeClickable(By.linkText(iTabName)));
		iDriver.findElement(By.linkText(iTabName)).click();
	}
	
	public static void addFloor(WebDriver iDriver, Floor iFloor)
	{
		
		iDriver.findElement(By.id("Name")).sendKeys(iFloor.getName());
		
		if (iFloor.getGuestConfig() != null)
		{
			iDriver.findElement(By.id("CF00N28000006kXDi")).sendKeys(iFloor.getGuestConfig());
		}
		
		if (iFloor.getDefaultConfig() != null) {
			iDriver.findElement(By.id("CF00N28000006kXDj")).sendKeys(iFloor.getDefaultConfig());
		}
		
		if (!iFloor.isActive())
		{
			iDriver.findElement(By.id("00N28000006kXDk")).click();
		}
	}
	
	public static void addTable(WebDriver iDriver, Table iTable) 
	{
		//テーブル名
		iDriver.findElement(By.id("Name")).sendKeys(iTable.getName());
		
		//フロア
		iDriver.findElement(By.id("CF00N28000006kXCA")).sendKeys(iTable.getFloor());
		
		//一時使用不可
		if (iTable.isTempAvailable())
		{
			iDriver.findElement(By.id("00N28000006kXHA")).click();
		}
		
		//即時会計
		if (iTable.isImmediateCheckOut())
		{
			iDriver.findElement(By.id("00N28000006kXH9")).click();
		}
		
		//有効フラグ
		if (iTable.isActive())
		{
			iDriver.findElement(By.id("00N28000006kXH8")).click();
		}
		
	}
	
	/**
	 * Go next
	 * @param iDriver
	 */
	public static void next(WebDriver iDriver)
	{
		iDriver.findElement(By.linkText("Next"));
	}
	
	/**
	 * Go previous
	 * @param iDriver
	 */
	public static void previous(WebDriver iDriver)
	{
		iDriver.findElement(By.linkText("Previous"));
	}
	
	/**
	 * Search the option
	 * 
	 * @param iDriver
	 * @param iObject
	 * @param iSearchText
	 * @throws InterruptedException 
	 */
	public static void search(WebDriver iDriver, String iObject, String iSearchText) throws InterruptedException 
	{
		WebDriverWait lWait = new WebDriverWait(iDriver, 30);
		lWait.until(ExpectedConditions.elementToBeClickable(By.id("sen")));
		WebElement lSenElem = iDriver.findElement(By.id("sen"));
		lSenElem.click();
		List<WebElement> lObjectList = iDriver.findElements(By.xpath(".//option"));
		
		for (WebElement lElem: lObjectList) {
			if (lElem.getText().matches(iObject)){ 
				lElem.click();
				break;
			}
		}
		
		Thread.sleep(2000);
		iDriver.findElement(By.id("sbstr")).sendKeys(iSearchText);
		
		iDriver.findElement(By.name("search")).click();
	}
	
	public static void editSearchResult(WebDriver iDriver) throws InterruptedException
	{
		Thread.sleep(2000);
		
		//lWait.until(ExpectedConditions.visibilityOfAllElements(iDriver.findElements(By.linkText("Edit"))));
		List<WebElement> lElems = iDriver.findElements(By.linkText("Edit"));
		int lRandom = PosUtil.random(lElems.size());
		
		//System.out.println("Random Floor: "+lElems.get(lRandomFloor).getText());
		lElems.get(lRandom).click();
	}
	
	public static void openRecord(WebDriver iDriver, String iLink)
	{
		iDriver.findElement(By.linkText(iLink)).click();
		
		
	}
	
	/*public static void main(String[] args) throws InterruptedException {
		
		WebDriver lDriver = PosUtil.initChromeDriver();
		Browser.loginSalesforce(lDriver);
		
		
		//Search option
		search(lDriver, "オプションマスタ", "大盛り");
		
		//オプションマスタ
		//カテゴリマスタ
		//サービス時間帯マスタ
		//スタッフマスタ
		//テーブルマスタ
		//ドロアログ
		//フロアマスタ
		//メニューマスタ
		//レシートプリンタマスタ
		//レシート設定
		//レジレポート
		//会計
		
		//業務日報
		//支払い方法マスタ
		
		//食材マスタ
		
		//発行元マスタ
		
	}*/

	public static void viewSearchResult(WebDriver iDriver, String iResult) 
	{
		iDriver.findElement(By.linkText(iResult)).click();
	}

}
