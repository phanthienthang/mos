package h2.ui.se.mo.table;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
import h2.ui.se.mo.util.PosCheckUtil;
import h2.ui.se.mo.util.PosOrderHistoryUtil;
import h2.ui.se.mo.util.PosUtil;
import h2.ui.se.mo.util.TableUtil;
import h2.ui.se.mo.util.PosUtil.BY;

public class TableTest {
	
	WebDriver mDriver;
	Floor mFloor;
	@Before
	public void init() throws InterruptedException
	{
		
		//mDriver = PosUtil.init();
		mDriver = PosUtil.initLocal();
		Thread.sleep(2000);
		
	}
	
	private void initSfdc() throws InterruptedException
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
		initSfdc();
		
		String lPosWindow = new ArrayList<String> (mDriver.getWindowHandles()).get(0);
		
		//Open All the tabs
		Browser.openAllMOTab(mDriver);
		
		//Open Table window
		Browser.openTab(mDriver, "テーブルマスタ");
		
		//Open New Table Layout
		Browser.newHandle(mDriver);
		
		//Add new Table
		Table lTable = new Table("T04", mFloor.getName(), false, false, true);
		TableUtil.addTable(mDriver, lTable);
		
		Thread.sleep(1000);
		mDriver.findElement(By.name("save")).click();
		
		Thread.sleep(2000);
		mDriver.close();
		mDriver.switchTo().window(lPosWindow);
		
		PosUtil.refesh(mDriver);
		
		Map<String, List<WebElement>> lFloorMap = TableUtil.parse(mDriver);
		List<WebElement> lTableList = lFloorMap.get(mFloor.getName());
		
		for (WebElement lEle: lTableList)
		{
			if (lEle.getText().contains(lTable.getName())) 
			{
				PosUtil.takeScreenShot(mDriver, lEle, lTable.getName());
				break;
			}
		}
	}
	
	//F-1-3-1-2
	//@Test
	public void testAddTables() throws InterruptedException
	{
		initSfdc();
		
		String lPosWindow = new ArrayList<String> (mDriver.getWindowHandles()).get(0);
		
		//Open All the tabs
		Browser.openAllMOTab(mDriver);
		
		//Open Table window
		Browser.openTab(mDriver, "テーブルマスタ");
		
		//Open New Table Layout
		Browser.newHandle(mDriver);
		
		//Add new Table
		
		//Add new Floors
		List<Table> lTableList = new ArrayList<Table>();
		for (int i = 1; i < 4; i++)
		{
			TableUtil.addTable(mDriver, new Table("T-"+i, mFloor.getName(), false, false, true));
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
		initSfdc();
		
		String lPosWindow = new ArrayList<String> (mDriver.getWindowHandles()).get(0);
		
		//Open All the tabs
		Browser.openAllMOTab(mDriver);
		
		//Open Table window
		Browser.openTab(mDriver, "テーブルマスタ");
		
		//Browse tabbles
		PosUtil.browse(mDriver);
		
		//Random Edit
		PosUtil.randomEdit(mDriver);
		
		activeTable();
		
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
		Browser.openTab(mDriver, "テーブルマスタ");
		
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
	//@Test
	public void testF133T1331() throws InterruptedException, IOException
	{
		String lPosWindow = new ArrayList<String> (mDriver.getWindowHandles()).get(0);
		initSfdc();
		
		//Open Table window
		Browser.openTab(mDriver, "テーブルマスタ");
		
		//Browse tabbles
		PosUtil.browse(mDriver);
		
		//Random Edit
		PosUtil.randomEdit(mDriver);
		
		//Get name of the table
		String lSelectedTbl = mDriver.findElement(By.id("Name")).getAttribute("value");
		
		activeAccount();
		
		mDriver.findElement(By.name("save")).click();
		
		Thread.sleep(3000);
		mDriver.close();
		mDriver.switchTo().window(lPosWindow);
		
		mDriver.get(mDriver.getCurrentUrl());
		
		//Handle Order Menu
		handleOrderMenu(mDriver, lSelectedTbl, 0, 0, 0, "即時会計 - F-1-4-2-1");
		
		Thread.sleep(2000);
		
		//Send / Transform Order
		PosUtil.findnClick(mDriver, BY.LINKTEXT, "送信"); //SEND
		
		Thread.sleep(5000);
		
		PosUtil.screenshot(mDriver, "T1331.png");
		
	}
	
	//@Test
	public void testF141T1411() throws InterruptedException, IOException 
	{
		String lPosWindow = new ArrayList<String> (mDriver.getWindowHandles()).get(0);
		initSfdc();
		
		//Open Table window
		Browser.openTab(mDriver, "テーブルマスタ");
		
		//Browse tabbles
		PosUtil.browse(mDriver);
		
		//Random Edit
		PosUtil.randomEdit(mDriver);
		
		//Get name of the table
		String lSelectedTbl = mDriver.findElement(By.id("Name")).getAttribute("value");
		
		activeAccount();
		
		mDriver.findElement(By.name("save")).click();
		
		Thread.sleep(3000);
		mDriver.close();
		mDriver.switchTo().window(lPosWindow);
		
		mDriver.get(mDriver.getCurrentUrl());
		
		//Handle Order Menu
		handleOrderMenu(mDriver, lSelectedTbl, 0, 0, 0, "F141-T1411");
		
		Thread.sleep(2000);
		
		//Send // Transform Order
		PosUtil.findnClick(mDriver, BY.LINKTEXT, "送信"); //SEND
		
		Thread.sleep(2000);
		PosCheckUtil.handlePayByCash(mDriver);
		
		Thread.sleep(2000);
		PosCheckUtil.handleCheckOut(mDriver);
		
		Thread.sleep(2000);
		PosUtil.screenshot(mDriver, "F141T1411.png");
	}
	
	//@Test
	public void testF141T1412() throws InterruptedException, IOException {
		
		//String lPosWindow = new ArrayList<String> (mDriver.getWindowHandles()).get(0);
		Thread.sleep(2000);
		
		PosUtil.openSetting(mDriver, "伝票履歴");
		
		Thread.sleep(2000);
		DateTimeFormatter lDateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		PosOrderHistoryUtil.filterByDay(mDriver, lDateFormat.format(LocalDate.now()));
		
		Thread.sleep(2000);
		PosUtil.screenshot(mDriver, "F141T1412.png");
	}
	
	
	//@Test 
	public void testF141T1413() throws InterruptedException, IOException 
	{
		for (int i = 0; i < 5; i++) {
			String lTable = PosUtil.orderRandom(mDriver);
			
			Thread.sleep(2000);
			
			//Send // Transform Order
			PosUtil.findnClick(mDriver, BY.LINKTEXT, "送信"); //SEND
			
			Thread.sleep(3000);
			PosUtil.checkOut(mDriver, lTable);
			
			Thread.sleep(2000);
			PosCheckUtil.handlePayByCash(mDriver);
			
			Thread.sleep(2000);
			PosCheckUtil.handleCheckOut(mDriver);
			
			Thread.sleep(1000);
			PosCheckUtil.handleClose(mDriver);
		}
		
		Thread.sleep(2000);

		PosUtil.openSetting(mDriver, "伝票履歴");
		
		
		Thread.sleep(2000);
		DateTimeFormatter lDateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		PosOrderHistoryUtil.filterByDay(mDriver, lDateFormat.format(LocalDate.now()));
		
		Thread.sleep(2000);
		PosUtil.screenshot(mDriver, "F141T1413.png");
	}
	
	//@Test
	public void testF142T1421() throws InterruptedException, IOException {
		
		/*for (int i = 0; i < 2; i++) {
			String lTable = PosUtil.orderRandom(mDriver);
			
			Thread.sleep(2000);
			
			//Send // Transform Order
			PosUtil.findnClick(mDriver, BY.LINKTEXT, "SEND");
			
			Thread.sleep(3000);
			PosUtil.checkOut(mDriver, lTable);
			
			Thread.sleep(2000);
			CheckOutPOSUtil.handlePayByCash(mDriver);
			
			Thread.sleep(2000);
			CheckOutPOSUtil.handleCheckOut(mDriver);
			
			Thread.sleep(1000);
			CheckOutPOSUtil.handleClose(mDriver);
		}*/
		
		//String lPosWindow = new ArrayList<String> (mDriver.getWindowHandles()).get(0);
		Thread.sleep(2000);
		
		PosUtil.openSetting(mDriver, "伝票履歴");
		
		Thread.sleep(2000);
		DateTimeFormatter lDateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		PosOrderHistoryUtil.filterByDay(mDriver, lDateFormat.format(LocalDate.now()));
		
		String lRdmCheck = PosOrderHistoryUtil.selectRdmCheck(mDriver);
		PosUtil.screenshot(mDriver, "F142T1421_Pos.png");
		
		String lPosWindow = new ArrayList<String> (mDriver.getWindowHandles()).get(0);
		initSfdc();
		
		//Open Table window
		Browser.openTab(mDriver, "会計");
		
		Browser.search(mDriver, "会計", lRdmCheck.substring(0, 10));
		Browser.viewSearchResult(mDriver, lRdmCheck.substring(0, 10));
		PosUtil.screenshot(mDriver, "F142T1421_Salesforce.png");
	}
	
	@Test
	public void testF143T1413() throws InterruptedException, IOException {
		Thread.sleep(5000);
		
		PosUtil.openSetting(mDriver, "伝票履歴");
		
		Thread.sleep(2000);
		DateTimeFormatter lDateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		PosOrderHistoryUtil.filterByDay(mDriver, lDateFormat.format(LocalDate.now()));
		
		Thread.sleep(5000);
		String lRdmCheck = PosOrderHistoryUtil.selectRdmCheck(mDriver);
		
		String lPosWindow = new ArrayList<String> (mDriver.getWindowHandles()).get(0);
		
		Thread.sleep(2000);
		PosCheckUtil.handleCancel(mDriver);
		//CheckOutPOSUtil.handleCheckOut(mDriver);
		mDriver.switchTo().alert().accept();
		if (PosUtil.hasAlert(mDriver))
		{
			mDriver.switchTo().alert().dismiss();
		}
		mDriver.switchTo().window(lPosWindow);
		
		Thread.sleep(2000);
		PosUtil.screenshot(mDriver, "F143T1413_Pos.png");
		
		
		initSfdc();
		
		//Open Table window
		Browser.openTab(mDriver, "会計");
		
		Browser.search(mDriver, "会計", lRdmCheck.substring(0, 10));
		Browser.viewSearchResult(mDriver, lRdmCheck.substring(0, 10));
		PosUtil.screenshot(mDriver, "F143T1413_Salesforce.png");
	}
	

	/**
	 * Pick specified table by its name
	 * @param text
	 * @throws InterruptedException 
	 */
	private void pickTable(String iTableName) throws InterruptedException 
	{
		Browser.search(mDriver, "テーブルマスタ", iTableName);
		//SfdcUtil.editSearchResult(mDriver);
		Browser.openRecord(mDriver, iTableName);
	}

	/**
	 * Inactive table
	 */
	private void activeTable()
	{
		handleActiveCheckbox("00N28000007Hr7Y");
	}
	
	private void activeAccount(){
		handleActiveCheckbox("00N28000007Hr7Z");
	}
	
	private void activeTemp()
	{
		handleActiveCheckbox("00N28000007Hr7a");
	}
	
	private void handleActiveCheckbox(String iId)
	{
		WebDriverWait lWait = new WebDriverWait(mDriver, 30);
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
		PosUtil.openSetting(mDriver, "注文");
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
	
	
	private void handleOrderMenu(WebDriver iDriver, String iTable, int iServiceCharge, int iRate, int iPrice,  String iComment) throws InterruptedException 
	{
		PosUtil.orderByTable(iDriver, iTable, iServiceCharge, iRate, iPrice, iComment);
	}
	
	
	
}
