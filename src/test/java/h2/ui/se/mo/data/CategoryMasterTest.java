package h2.ui.se.mo.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import h2.ui.se.mo.menu.Menu;
import h2.ui.se.mo.util.Browser;
import h2.ui.se.mo.util.Pattern;
import h2.ui.se.mo.util.PosMenuOptionUtil;
import h2.ui.se.mo.util.PosUtil;
import h2.ui.se.mo.util.SfdcUtil;
import h2.ui.se.mo.util.PosUtil.BY;

public class CategoryMasterTest {
	
	@Test //T1811-T1821-T1822-T1823-T1824
	public void testCategoryMaster() throws InterruptedException {
		
		WebDriver lDriver = PosUtil.initLocal();
		Thread.sleep(5000);
		
		String lPosWindow = new ArrayList<String> (lDriver.getWindowHandles()).get(0);
		
		Browser.loginSalesforce(lDriver);
		
		//Open All the tabs
		Browser.openAllMOTab(lDriver);
		
		//Open Table window
		SfdcUtil.openTab(lDriver, "カテゴリマスタ");
		
		//Open New Layout
		Browser.newHandle(lDriver);
		
		//Create 3x categories
		Thread.sleep(2000);
		List<String> lCategoryList = new ArrayList<String>();
		for (int i = 0; i < 3; i++) 
		{
			String lName = "カテゴリ"+i;
			lCategoryList.add(lName);
			addCategoryData(lDriver, lName, lName, i+"");
			Browser.saveNew(lDriver);
			Thread.sleep(2000);
		}
		
		Browser.cancel(lDriver);
		
		//Open All the tabs
		Browser.openAllMOTab(lDriver);
		
		//Open Table window
		SfdcUtil.openTab(lDriver, "メニューマスタ");
		Browser.newHandle(lDriver);
		Browser.kontinue(lDriver);
		
		List<String> lMenuList = new ArrayList<String>();		
		for (int i = 0; i < lCategoryList.size(); i++) 
		{
			Thread.sleep(2000);
			lMenuList.add("メニュー"+i);
			addMenuData(lDriver, "メニュー"+i, lCategoryList.get(i));
			Browser.saveNew(lDriver);
			Thread.sleep(2000);
			Browser.kontinue(lDriver);
		}
		
		Browser.cancel(lDriver);
		//Open All the tabs
		Browser.openAllMOTab(lDriver);
		
		//Open Table window
		SfdcUtil.openTab(lDriver, "メニューマスタ");
		
		for (int i = 0; i < lMenuList.size(); i++) {
			
			PosUtil.findnClick(lDriver, BY.LINKTEXT, lMenuList.get(i));
			Menu.newMenuCategory(lDriver);
			Menu.addMenuRelation(lDriver, "CF00N28000007Hr2Z", lCategoryList.get(i));
			Thread.sleep(3000);
		}
		lDriver.close();
		lDriver.switchTo().window(lPosWindow);
		
		Thread.sleep(5000);
		
		PosUtil.refesh(lDriver);
		
		Thread.sleep(5000);
		WebElement lUsingTable = PosUtil.pickRdmUsingTable(lDriver);
		lUsingTable.click();
		try {
			PosUtil.screenshot(lDriver, "T1822.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PosUtil.cancel(lDriver);
		
		//Test T-1-8-2-3
		Browser.loginSalesforce(lDriver);
		
		//Open All the tabs
		Browser.openAllMOTab(lDriver);
		
		//Open Table window
		SfdcUtil.openTab(lDriver, "メニューマスタ");
		editMenu(lDriver, lMenuList.get(0)); 
		lDriver.close();
		
		
		lDriver.switchTo().window(lPosWindow);
		Thread.sleep(5000);

		PosUtil.refesh(lDriver);

		Thread.sleep(5000);
		WebElement lRdmTbl = PosUtil.pickRdmUsingTable(lDriver);
		lRdmTbl.click();
		Thread.sleep(3000);
		
		PosUtil.pickCategory(lDriver, lCategoryList.get(0));
		Thread.sleep(5000);
		
		try {
			PosUtil.screenshot(lDriver, "T1823.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		PosUtil.cancel(lDriver);
		
		//Test T-1-8-2-4 - Add Menu Option (大盛り, 少なめ and 野菜抜き)
		Browser.loginSalesforce(lDriver);
		
		//Open All the tabs
		Browser.openAllMOTab(lDriver);
		
		//Open Table window
		SfdcUtil.openTab(lDriver, "メニューマスタ");
		editMenu(lDriver, lMenuList.get(0));
		String[] lOptions = new String[]{"大盛り", "少なめ", "野菜抜き"};
		Menu.newMenuOption(lDriver);
		Menu.addMenuRelationList(lDriver, "CF00N28000007Hr2O", lOptions);
		
		lDriver.close();
		lDriver.switchTo().window(lPosWindow);
		
		Thread.sleep(5000);
		
		PosUtil.refesh(lDriver);
		
		Thread.sleep(5000);
		
		
		lRdmTbl = PosUtil.pickRdmUsingTable(lDriver);
		lRdmTbl.click();
		PosUtil.pickCategory(lDriver, lCategoryList.get(0));
		PosUtil.holdMenu(lDriver, lMenuList.get(0));
		Pattern lPattern = PosMenuOptionUtil.addOption(lDriver);
		lPattern.editOption();
		Thread.sleep(5000);
		try {
			PosUtil.screenshot(lDriver, "T1824.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void addCategoryData(WebDriver iDriver, String iName, String iAbbre, String iPriority)
	{
		iDriver.findElement(By.id("Name")).sendKeys(iName);
		iDriver.findElement(By.id("00N28000007Hr3B")).sendKeys(iAbbre);
		iDriver.findElement(By.id("00N28000007Hr3C")).sendKeys(iPriority);
	}
	
	private void addMenuData(WebDriver iDriver, String iName, String iCategory){
		//Name
		iDriver.findElement(By.id("Name")).sendKeys(iName);
		
		//Category relation - CF00N28000007Hr4Y
		iDriver.findElement(By.id("CF00N28000007Hr4Y")).sendKeys(iCategory);
	}
	
	private void editMenu(WebDriver iDriver, String iMenu) throws InterruptedException {
		PosUtil.findnClick(iDriver, BY.LINKTEXT, iMenu);
		WebElement lEpWebElement = iDriver.findElement(By.id("topButtonRow"));
		WebElement lEditWebElement = lEpWebElement.findElement(By.xpath(".//input[@name='edit']"));
		lEditWebElement.click();
		Thread.sleep(3000);
		iDriver.findElement(By.id("00N28000007Hr4c")).click();
		Browser.save(iDriver);
	}
	
	
}
