package h2.ui.se.mo.table;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import h2.ui.se.mo.floor.Floor;
import h2.ui.se.mo.util.Browser;
import h2.ui.se.mo.util.PosUtil;
import h2.ui.se.mo.util.SfdcUtil;
import h2.ui.se.mo.util.TableUtil;

public class TableTest {
	
	WebDriver mDriver;
	Floor mFloor;
	@Before
	public void init() throws InterruptedException
	{
		
		mDriver = PosUtil.initPos();
		Thread.sleep(2000);
		
	}
	
	private void initData() throws InterruptedException
	{
		Browser.loginSalesforce(mDriver);
		
		//Open All the tabs
		Browser.openAllMOTab(mDriver);
		
		//SfdcUtil.openTab(mDriver, "フロアマスタ");
		
		//Browser.openNewWindow(mDriver);
		
		mFloor = new Floor("F-Test");
		//SfdcUtil.addFloor(mDriver, mFloor);
		
		//Thread.sleep(1000);
		//mDriver.findElement(By.name("save")).click();
		
	}
	
	//F-1-3-1-1
	//@Test
	public void testAddTable() throws InterruptedException, IOException 
	{
		initData();
		
		String lPosWindow = new ArrayList<String> (mDriver.getWindowHandles()).get(0);
		
		//Open All the tabs
		Browser.openAllMOTab(mDriver);
		
		//Open Table window
		SfdcUtil.openTab(mDriver, "テーブルマスタ");
		
		//Open New Table Layout
		Browser.openNewWindow(mDriver);
		
		//Add new Table
		Table lTable = new Table("T04", mFloor.getName(), false, false, true);
		SfdcUtil.addTable(mDriver, lTable);
		
		Thread.sleep(1000);
		mDriver.findElement(By.name("save")).click();
		
		Thread.sleep(2000);
		mDriver.close();
		mDriver.switchTo().window(lPosWindow);
		
		Thread.sleep(1000);
		PosUtil.openSetting(mDriver, "SETTINGS");
		
		Thread.sleep(1000);
		PosUtil.openDataAdmin(mDriver);
		
		Thread.sleep(1000);
		PosUtil.refesh(mDriver);
		
		Thread.sleep(3000);
		
		mDriver.switchTo().alert().accept();
		Thread.sleep(2000);
		List<String> lWindows = new ArrayList<String>(mDriver.getWindowHandles());
		mDriver.switchTo().window(lWindows.get(0));
		PosUtil.back(mDriver);
		
		Thread.sleep(2000);
		PosUtil.menu(mDriver);
	
		Thread.sleep(1000);
		PosUtil.openSetting(mDriver, "ORDERS");
		
		Map<String, List<WebElement>> lFloorMap = TableUtil.parse(mDriver);
		List<WebElement> lTableList = lFloorMap.get(mFloor.getName());
		
		for (WebElement lEle: lTableList)
		{
			if (lEle.getText().contains(lTable.getName())) 
			{
				PosUtil.screenShot(mDriver, lEle, lTable.getName());
				break;
			}
		}
	}
	
	//F-1-3-1-2
	//@Test
	public void testAddTables() throws InterruptedException
	{
		initData();
		
		String lPosWindow = new ArrayList<String> (mDriver.getWindowHandles()).get(0);
		
		//Open All the tabs
		Browser.openAllMOTab(mDriver);
		
		//Open Table window
		SfdcUtil.openTab(mDriver, "テーブルマスタ");
		
		//Open New Table Layout
		Browser.openNewWindow(mDriver);
		
		//Add new Table
		
		//Add new Floors
		List<Table> lTableList = new ArrayList<Table>();
		for (int i = 1; i < 4; i++)
		{
			SfdcUtil.addTable(mDriver, new Table("T-"+i, mFloor.getName(), false, false, true));
			Thread.sleep(1000);
			mDriver.findElement(By.name("save_new")).click();
			Thread.sleep(1000);
		}
		
		PosUtil.cancel(mDriver, lPosWindow);
	}
	
	//Test No - F-1-3-1-3
	//@Test
	public void testUpdateTable() throws InterruptedException 
	{
		initData();
		
		String lPosWindow = new ArrayList<String> (mDriver.getWindowHandles()).get(0);
		
		//Open All the tabs
		Browser.openAllMOTab(mDriver);
		
		//Open Table window
		SfdcUtil.openTab(mDriver, "テーブルマスタ");
		
		//Browse tabbles
		PosUtil.browse(mDriver);
		
		//Random Edit
		PosUtil.randomEdit(mDriver);
		
		handleInActive();
		
		Thread.sleep(1000);
		mDriver.findElement(By.name("save")).click();
		
		Thread.sleep(2000);
		mDriver.close();
		mDriver.switchTo().window(lPosWindow);
		
		mDriver.get(mDriver.getCurrentUrl());
	}
	
	//T-1-3-2-1 & T-1-3-2-2 
	//@Test
	public void testActiveTable() throws InterruptedException
	{
		//Test No - T-1-3-2-1. Test Item: 一時使用不可
		String lPosWindow = new ArrayList<String> (mDriver.getWindowHandles()).get(0);
		
		//Browse tabbles
		//PosUtil.browse(mDriver);
		Thread.sleep(2000);
		WebElement lTableElem = pickRandom();
		
		handleClickAndHold(lTableElem, "一時使用不可");
		
		String lHoldTable = lTableElem.getText().replaceAll("\\W", "").trim();
		Browser.loginSalesforce(mDriver);
		
		//Open All the tabs
		Browser.openAllMOTab(mDriver);
		
		//Open Table window
		SfdcUtil.openTab(mDriver, "テーブルマスタ");
		
		//Browse tabbles
		PosUtil.browse(mDriver);
		
		pickTable(lHoldTable);
		
		//Take screenshot here
		//Close and get back to Pos
		mDriver.close();
		
		Thread.sleep(1000);
		mDriver.switchTo().window(lPosWindow);
		
		//T-1-3-2-2 : 一時使用不可解除
		
		lTableElem = PosUtil.pickOrderTable(mDriver, lHoldTable);
		
		if (lTableElem != null)
		{
			handleClickAndHold(lTableElem, "一時使用不可解除");
		}
		//Take the screenshot here
		//Close and get back to Pos
	}
	
	//即時会計 - F-1-3-3/T-1-3-3-1
	@Test
	public void testF133T1331() throws InterruptedException
	{
		String lPosWindow = new ArrayList<String> (mDriver.getWindowHandles()).get(0);
		initData();
		
		//Open Table window
		SfdcUtil.openTab(mDriver, "テーブルマスタ");
		
		//Browse tabbles
		PosUtil.browse(mDriver);
		
		//Random Edit
		PosUtil.randomEdit(mDriver);
		
		//Get name of the table
		String lSelectedTbl = mDriver.findElement(By.id("Name")).getAttribute("value");
		
		handleIsAccount();
		
		Thread.sleep(1000);
		mDriver.findElement(By.name("save")).click();
		
		Thread.sleep(2000);
		mDriver.close();
		mDriver.switchTo().window(lPosWindow);
		
		mDriver.get(mDriver.getCurrentUrl());
		
		//POS Processing
		WebElement lOrderedTable = PosUtil.pickOrderTable(mDriver, lSelectedTbl);
		
		//Handle Order Menu
		handleOrderMenu(lOrderedTable);
		
		Thread.sleep(1000);
		
		//Order
		mDriver.findElement(By.id("done")).click();
		
		Thread.sleep(1000);
		
		PosUtil.handleRdmCategory(mDriver);
		
		PosUtil.handleRdmMenu(mDriver);
		
	}
	

	/**
	 * Pick specified table by its name
	 * @param text
	 * @throws InterruptedException 
	 */
	private void pickTable(String iTableName) throws InterruptedException 
	{
		SfdcUtil.search(mDriver, "テーブルマスタ", iTableName);
		//SfdcUtil.editSearchResult(mDriver);
		SfdcUtil.openRecord(mDriver, iTableName);
	}

	/**
	 * Inactive table
	 */
	private void handleInActive()
	{
		handleActiveCheckbox("00N28000007Hr7Y");
	}
	
	private void handleIsAccount(){
		handleActiveCheckbox("00N28000007Hr7Z");
	}
	
	private void handleIsTmp()
	{
		handleActiveCheckbox("00N28000007Hr7a");
	}
	
	private void handleActiveCheckbox(String iId)
	{
		WebDriverWait lWait = new WebDriverWait(mDriver, 10);
		lWait.until(ExpectedConditions.elementToBeClickable(By.id(iId)));
		WebElement lActiveElem = mDriver.findElement(By.id(iId));
		if (!lActiveElem.isSelected()) 
		{
			lActiveElem.click();
		}
	}
	
	/**
	 * Pick random table
	 * @return
	 * @throws InterruptedException
	 */
	private WebElement pickRandom() throws InterruptedException
	{
		Map<String, List<WebElement>> lFloorMap = TableUtil.parseAElement(mDriver);
		List<String> lFloorList = new ArrayList<String>();
		for (String lFloor: lFloorMap.keySet()) {
			lFloorList.add(lFloor);
		}
		int lFloorRdm = PosUtil.random(lFloorList.size());
		String lFloorRdmTxt = lFloorList.get(lFloorRdm);
		
		List<WebElement> lTableList = lFloorMap.get(lFloorRdmTxt);
		if (lTableList.isEmpty())
			return null;
			
		int lTableRdm = PosUtil.random(lTableList.size());
		WebElement lWebElem = lTableList.get(lTableRdm);
		
		//System.out.println("Selected Table: "+lSelTable);
		
		return lWebElem;
	}
	
	private void handleClickAndHold(WebElement iTable, String iActionLink) throws InterruptedException{
		
		Thread.sleep(1000);
		PosUtil.openSetting(mDriver, "ORDERS");
		Thread.sleep(2000);
		//lTableList.get(lTableRdm).click();
		
		Actions lBuilder = new Actions(mDriver);
		lBuilder.clickAndHold(iTable).build().perform();
		Thread.sleep(2000);
		lBuilder.release(iTable).build().perform();
		
		Thread.sleep(1000);
		
		mDriver.findElement(By.linkText(iActionLink)).click();
		
		Thread.sleep(2000);
		String lPosWindow = new ArrayList<String> (mDriver.getWindowHandles()).get(0);
		mDriver.switchTo().alert().accept();
		mDriver.switchTo().window(lPosWindow);
		Thread.sleep(2000);
		
		if (PosUtil.hasAlert(mDriver))
		{
			mDriver.switchTo().alert().dismiss();
		}
		mDriver.switchTo().window(lPosWindow);
	}
	
	
	private void handleOrderMenu(WebElement iTable) throws InterruptedException{
		Thread.sleep(1000);
		PosUtil.openSetting(mDriver, "ORDERS");
		Thread.sleep(2000);
		//lTableList.get(lTableRdm).click();
		
		Actions lBuilder = new Actions(mDriver);
		lBuilder.click(iTable).build().perform();
		Thread.sleep(2000);
		
	}
	
}
