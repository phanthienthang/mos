package h2.ui.se.mo.menu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import h2.ui.se.mo.util.PosUtil;
import h2.ui.se.mo.util.PosUtil.BY;

public class Menu 
{
	
	public static void newMenuCategory(WebDriver iDriver)
	{
		newHandle(iDriver, "new00N28000007Hr2M");
	}
	
	public static void newMenuOption(WebDriver iDriver)
	{
		newHandle(iDriver, "new00N28000007Hr2b");
	}
	
	public static void newMenuService(WebDriver iDriver)
	{
		newHandle(iDriver, "new00N28000007Hr2P");
	}
	
	private static void newHandle(WebDriver iDriver, String iID) 
	{
		try
		{
			PosUtil.findnClick(iDriver, BY.NAME, iID);
		}
		catch (WebDriverException e) 
		{
			newMenuService(iDriver);
		}
	}
	
	public static void addMenuRelation(WebDriver iDriver, String iID, String iData)
	{
		//CF00N28000007Hr2M - Menu Category
		
		
		//CF00N28000007Hr2O - Menu Option
		//個別価格 - Individual price - 00N28000007Hr50 - Menu Option
		//00N28000007Hr4x - Comment - Menu Option
		
		//CF00N28000007Hr2c - Menu Service Window
		
		try
		{
			PosUtil.findnClick(iDriver, BY.ID, iID);
			iDriver.findElement(By.id(iID)).sendKeys(iData);
			PosUtil.findnClick(iDriver, BY.NAME, "save");
		}
		catch (WebDriverException e) 
		{
			addMenuRelation(iDriver, iID, iData);
		}
	}
	
	public static void addMenuRelationList(WebDriver iDriver, String iID, String...iData) {
		for (int i = 0; i < iData.length; i++) {
			addNewRelation(iDriver, iID, iData[i]);
		}
	}
	
	private static void addNewRelation(WebDriver iDriver, String iID, String iData) {
		try
		{
			PosUtil.findnClick(iDriver, BY.ID, iID);
			iDriver.findElement(By.id(iID)).sendKeys(iData);
			PosUtil.findnClick(iDriver, BY.NAME, "save_new");
			Thread.sleep(2000);
		}
		catch (WebDriverException e) 
		{
			addMenuRelation(iDriver, iID, iData);
		} catch (InterruptedException e) {
			addMenuRelation(iDriver, iID, iData);
		}
	}

}
