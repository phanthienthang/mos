package h2.ui.se.mo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import h2.ui.se.mo.table.Table;

public class PosTable 
{
	
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
	 * @param iDriver
	 * @return
	 * @throws InterruptedException
	 */
	public static Map<String, List<WebElement>> parse(WebDriver iDriver) throws InterruptedException
	{
		System.out.println("Call method: parse(WebDriver iDriver) in TableUtil.java");
		Map<String, List<WebElement>> lFloorMap = new HashMap<String, List<WebElement>>();
		
		List<WebElement> lFloorList = iDriver.findElements(By.xpath("//div[@ng-repeat-start='floor in floorTable']//span"));
		
		if (lFloorList.size() != 0) {
			List<WebElement> lTables = iDriver.findElements(By.cssSelector("div[ng-repeat-end]"));
			
			for (int i = 0; i < lFloorList.size(); i++) 
			{
				WebElement lFloorEle = lFloorList.get(i);
				WebElement lTableEle = lTables.get(i);
				List<WebElement> lWebElemList = lTableEle.findElements(By.xpath(".//a[@ng-repeat='table in floor.tables | rowSlice:i:colnum']//span"));
				lFloorMap.put(lFloorEle.getText(), lWebElemList);
			}
			return lFloorMap;
		} else {
			return parse(iDriver);
		}
		
		
	}
	
	/**
	 * @param iDriver
	 * @return
	 * @throws InterruptedException
	 */
	public static Map<String, List<WebElement>> parseAElement(WebDriver iDriver)
	{
		System.out.println("Call method: parseAElement(WebDriver iDriver) in TableUtil.java");
		
		Map<String, List<WebElement>> lFloorMap = new HashMap<String, List<WebElement>>();
		
		List<WebElement> lFloorList = iDriver.findElements(By.xpath("//div[@ng-repeat-start='floor in floorTable']//span"));
		if (lFloorList.size() != 0) 
		{
			List<WebElement> lTables = iDriver.findElements(By.cssSelector("div[ng-repeat-end]"));
			
			for (int i = 0; i < lFloorList.size(); i++) 
			{
				WebElement lFloorEle = lFloorList.get(i);
				WebElement lTableEle = lTables.get(i);
				List<WebElement> lWebElemList = lTableEle.findElements(By.xpath(".//a[@ng-repeat='table in floor.tables | rowSlice:i:colnum']"));
				lFloorMap.put(lFloorEle.getText(), lWebElemList);
			}
			return lFloorMap;
		} else {
			return parseAElement(iDriver);
		}
	}
	
	/**
	 * @param iDriver
	 * @return
	 * @throws InterruptedException
	 */
	public static Map<String, List<WebElement>> parseEmptyTable(WebDriver iDriver)
	{
		System.out.println("Call method: parseAElement(WebDriver iDriver) in TableUtil.java");
		
		Map<String, List<WebElement>> lFloorMap = new HashMap<String, List<WebElement>>();
		
		List<WebElement> lFloorList = iDriver.findElements(By.xpath("//div[@ng-repeat-start='floor in floorTable']//span"));
		if (lFloorList.size() != 0) 
		{
			List<WebElement> lTables = iDriver.findElements(By.cssSelector("div[ng-repeat-end]"));
			
			for (int i = 0; i < lFloorList.size(); i++) 
			{
				WebElement lFloorEle = lFloorList.get(i);
				WebElement lTableEle = lTables.get(i);
				List<WebElement> lWebElemList = lTableEle.findElements(By.xpath(".//a[@class='cell ng-scope empty']"));
				
				lFloorMap.put(lFloorEle.getText(), lWebElemList);
			}
			return lFloorMap;
		} else {
			return parseAElement(iDriver);
		}
	}
	
	/**
	 * @param iDriver
	 * @return
	 * @throws InterruptedException
	 */
	public static Map<String, List<WebElement>> parseUsingTable(WebDriver iDriver)
	{
		System.out.println("Call method: parseAElement(WebDriver iDriver) in TableUtil.java");
		
		Map<String, List<WebElement>> lFloorMap = new HashMap<String, List<WebElement>>();
		
		List<WebElement> lFloorList = iDriver.findElements(By.xpath("//div[@ng-repeat-start='floor in floorTable']//span"));
		if (lFloorList.size() != 0) 
		{
			List<WebElement> lTables = iDriver.findElements(By.cssSelector("div[ng-repeat-end]"));
			
			for (int i = 0; i < lFloorList.size(); i++) 
			{
				WebElement lFloorEle = lFloorList.get(i);
				WebElement lTableEle = lTables.get(i);
				List<WebElement> lWebElemList = lTableEle.findElements(By.xpath(".//a[@class='cell ng-scope using']"));
				
				lFloorMap.put(lFloorEle.getText(), lWebElemList);
			}
			return lFloorMap;
		} else {
			return parseAElement(iDriver);
		}
	}
	
	/**
	 * Pick specified ordered table
	 * 
	 * @param iDriver
	 * @param iTable
	 * @return
	 */
	public static WebElement pickOrderTable(WebDriver iDriver, String iTable)
	{
		Map<String, List<WebElement>> lFloorMap = PosTable.parseAElement(iDriver);
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
		}
		
		return null;
		
	}
	
	
	public static WebElement pickRdmTable(WebDriver iDriver) 
	{
		System.out.println("Call method pickRdmTable(WebDriver iDriver) in PosUtil.java");
		Map<String, List<WebElement>> lFloorMap = PosTable.parseAElement(iDriver);
		if (lFloorMap.size() != 0) {
			int lRdmFloor = Util.random(lFloorMap.keySet().size());
			//int lCount = 0; 
			List<String> lFloorList = new ArrayList<String>();
			
			for (String lFloor: lFloorMap.keySet()) {
				lFloorList.add(lFloor);
			}
			
			List<WebElement> lTableElems = lFloorMap.get(lFloorList.get(lRdmFloor));
			
			return  lTableElems.get(Util.random(lTableElems.size()));
		}
		else {
			return pickRdmTable(iDriver);
		}
	}
	
	public static WebElement pickRdmUsingTable(WebDriver iDriver) 
	{
		System.out.println("Call method pickRdmTable(WebDriver iDriver) in PosUtil.java");
		Map<String, List<WebElement>> lFloorMap = PosTable.parseUsingTable(iDriver);
		if (lFloorMap.size() != 0) {
			int lRdmFloor = Util.random(lFloorMap.keySet().size());
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
	
	public static boolean isTabAvailable(WebElement iTab)
	{
		if (iTab.getAttribute("class").contains("empty")) {
			return true;
		}
		
		return false;
	}
	
	
}
