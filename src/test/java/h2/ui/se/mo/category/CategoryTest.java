package h2.ui.se.mo.category;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import h2.ui.se.mo.menu.Menu;
import h2.ui.se.mo.util.PosBrowser;
import h2.ui.se.mo.util.PosMenuOption;
import h2.ui.se.mo.util.PosPattern;
import h2.ui.se.mo.util.PosTable;
import h2.ui.se.mo.util.PosUtil;
import h2.ui.se.mo.util.PosUtil.BY;

public class CategoryTest 
{
	@Test //T1811-T1821-T1822-T1823-T1824
	public void testCategoryMaster() throws InterruptedException 
	{
		
		WebDriver lDriver = PosUtil.initLocal();
		Thread.sleep(5000);
		
		String lPosWindow = new ArrayList<String> (lDriver.getWindowHandles()).get(0);
		
		PosBrowser.loginSalesforce(lDriver);
		
		//Open All the tabs
		PosBrowser.openAllMOTab(lDriver);
		
		//Open Table window
		PosBrowser.openTab(lDriver, "カテゴリマスタ");
		
		//Open New Layout
		PosBrowser.newHandle(lDriver);
		
		//Create 3x categories
		Thread.sleep(2000);
		List<String> lCategoryList = new ArrayList<String>();
		for (int i = 0; i < 3; i++) 
		{
			String lName = "カテゴリ"+i;
			lCategoryList.add(lName);
			addCategoryData(lDriver, lName, lName, i+"");
			PosBrowser.saveNew(lDriver);
			Thread.sleep(2000);
		}
		
		PosBrowser.cancel(lDriver);
		
		//Open All the tabs
		PosBrowser.openAllMOTab(lDriver);
		
		//Open Table window
		PosBrowser.openTab(lDriver, "メニューマスタ");
		PosBrowser.newHandle(lDriver);
		PosBrowser.kontinue(lDriver);
		
		List<String> lMenuList = new ArrayList<String>();		
		for (int i = 0; i < lCategoryList.size(); i++) 
		{
			Thread.sleep(2000);
			lMenuList.add("メニュー"+i);
			addMenuData(lDriver, "メニュー"+i, lCategoryList.get(i));
			PosBrowser.saveNew(lDriver);
			Thread.sleep(2000);
			PosBrowser.kontinue(lDriver);
		}
		
		PosBrowser.cancel(lDriver);
		//Open All the tabs
		PosBrowser.openAllMOTab(lDriver);
		
		//Open Table window
		PosBrowser.openTab(lDriver, "メニューマスタ");
		
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
		WebElement lUsingTable = PosTable.pickRdmUsingTable(lDriver);
		lUsingTable.click();
		try {
			PosUtil.screenshot(lDriver, "T1822.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PosUtil.cancel(lDriver);
		
		//Test T-1-8-2-3
		PosBrowser.loginSalesforce(lDriver);
		
		//Open All the tabs
		PosBrowser.openAllMOTab(lDriver);
		
		//Open Table window
		PosBrowser.openTab(lDriver, "メニューマスタ");
		editMenu(lDriver, lMenuList.get(0)); 
		lDriver.close();
		
		
		lDriver.switchTo().window(lPosWindow);
		Thread.sleep(5000);

		PosUtil.refesh(lDriver);

		Thread.sleep(5000);
		WebElement lRdmTbl = PosTable.pickRdmUsingTable(lDriver);
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
		PosBrowser.loginSalesforce(lDriver);
		
		//Open All the tabs
		PosBrowser.openAllMOTab(lDriver);
		
		//Open Table window
		PosBrowser.openTab(lDriver, "メニューマスタ");
		editMenu(lDriver, lMenuList.get(0));
		String[] lOptions = new String[]{"大盛り", "少なめ", "野菜抜き"};
		Menu.newMenuOption(lDriver);
		Menu.addMenuRelationList(lDriver, "CF00N28000007Hr2O", lOptions);
		
		lDriver.close();
		lDriver.switchTo().window(lPosWindow);
		
		Thread.sleep(5000);
		
		PosUtil.refesh(lDriver);
		
		Thread.sleep(5000);
		
		
		lRdmTbl = PosTable.pickRdmUsingTable(lDriver);
		lRdmTbl.click();
		PosUtil.pickCategory(lDriver, lCategoryList.get(0));
		
		
		PosUtil.holdMenu(lDriver, lMenuList.get(0));
		PosPattern lPattern = PosMenuOption.addOption(lDriver);
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
		PosBrowser.save(iDriver);
	}
	

}
