package h2.ui.se.mo.floor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import h2.ui.se.mo.util.PosBrowser;
import h2.ui.se.mo.util.PosUtil;

public class FloorTest 
{
	WebDriver mDriver;
	@Before
	public void init() throws InterruptedException
	{
		mDriver = PosUtil.initLocal();
		Thread.sleep(2000);
	}
	
	@Test
	public void testAddFloor() throws InterruptedException 
	{
		PosBrowser.loginSalesforce(mDriver);
		String lPosWindow = new ArrayList<String> (mDriver.getWindowHandles()).get(0);
		
		openFloor();
		
		//Open New Floor Layout
		PosBrowser.newHandle(mDriver);
		
		//Add new Floor
		PosFloor.addFloor(mDriver, new Floor("F"));
		
		Thread.sleep(1000);
		mDriver.findElement(By.name("save")).click();
		
		Thread.sleep(2000);
		mDriver.close();
		mDriver.switchTo().window(lPosWindow);
		
		mDriver.get(mDriver.getCurrentUrl());
	}
	
	//@Test
	public void testAddFloors() throws InterruptedException
	{
		PosBrowser.loginSalesforce(mDriver);
		String lPosWindow = new ArrayList<String> (mDriver.getWindowHandles()).get(0);
		
		openFloor();
		
		//Open New Floor Layout
		PosBrowser.newHandle(mDriver);
		
		//Add new Floors
		for (int i = 1; i < 4; i++)
		{
			PosFloor.addFloor(mDriver, new Floor("FF"+i));
			Thread.sleep(1000);
			mDriver.findElement(By.name("save_new")).click();
			Thread.sleep(1000);
		}
		
		PosUtil.cancel(mDriver, lPosWindow);
	}
	
	//1. Inside salesforce, select a floor record
	//2. Set the 有効 field to false
	//3. Open app
	//4. Check that the record does not show in the floor list
	//@Test
	public void testUpdateFloor() throws InterruptedException 
	{
		PosBrowser.loginSalesforce(mDriver);
		String lPosWindow = new ArrayList<String> (mDriver.getWindowHandles()).get(0);
		
		//Open All the tabs
		PosBrowser.openAllMOTab(mDriver);
		
		//Open Floor window
		PosBrowser.openTab(mDriver, "フロアマスタ");
		
		//Browse all existence floors
		browseFloor(mDriver);
		
		//inactive floor
		deactiveFloor();
		
		Thread.sleep(1000);
		mDriver.findElement(By.name("save")).click();
		
		Thread.sleep(2000);
		mDriver.close();
		mDriver.switchTo().window(lPosWindow);
		
		mDriver.get(mDriver.getCurrentUrl());
	}
	
	
	/**
	 * Click "Go" button to list all the Floors
	 * @throws InterruptedException 
	 */
	private void browseFloor(WebDriver iDriver) throws InterruptedException
	{
		Thread.sleep(2000);
		//WebDriverWait lWait = new WebDriverWait(iDriver, 10);
		//lWait.until(ExpectedConditions.elementToBeClickable(By.id("go")));
		iDriver.findElement(By.name("go")).click();
		
		Thread.sleep(2000);
		//lWait.until(ExpectedConditions.visibilityOfAllElements(iDriver.findElements(By.linkText("Edit"))));
		List<WebElement> lElems = iDriver.findElements(By.linkText("Edit"));
		int lRandomFloor = random(lElems.size())+1;
		
		//System.out.println("Random Floor: "+lElems.get(lRandomFloor).getText());
		lElems.get(lRandomFloor).click();
	}
	
	
	/**
	 * Inactive floors
	 */
	private void deactiveFloor()
	{
		WebDriverWait lWait = new WebDriverWait(mDriver, 10);
		lWait.until(ExpectedConditions.elementToBeClickable(By.id("00N28000007Hr4A")));
		WebElement lActiveElem = mDriver.findElement(By.id("00N28000007Hr4A"));
		if (lActiveElem.isSelected()) {
			lActiveElem.click();
		}
	}
	
	private int random(int iLimit) 
	{
		return new Random().nextInt(iLimit);
	}
	
	
	/**
	 * Open Floor Tab.
	 * 1- Open all the tabs
	 * 2- Click the text link "フロアマスタ" to open Floor tab
	 */
	private void openFloor()
	{
		//Open All the tabs
		PosBrowser.openAllMOTab(mDriver);
		
		//Open Floor window
		PosBrowser.openTab(mDriver, "フロアマスタ");
	}
}
