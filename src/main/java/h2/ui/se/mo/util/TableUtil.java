package h2.ui.se.mo.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TableUtil 
{
	
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
	
}
