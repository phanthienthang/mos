package h2.ui.se.mo.util;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import h2.ui.se.mo.util.PosUtil.BY;

public class PosMenuOptionUtil 
{
	
	public static void addComment(){}
	
	
	public static void discountByPercent(WebDriver iDriver, String iCustom)
	{
		handleDiscountPercent(iDriver, iCustom);
	}
	
	private static void handleDiscountPercent(WebDriver iDriver, String iCustom) 
	{
		WebElement lElem = iDriver.findElement(By.xpath(".//input"));
	}

	public static void discountByAmount(WebDriver iDriver, String iCustom){
		handleDiscountAmount(iDriver, iCustom);
	}
	
	private static void handleDiscountAmount(WebDriver iDriver, String iCustom)
	{
		// TODO Auto-generated method stub
	}

	public static void discountByNumber(WebDriver iDriver, String iCustom)
	{
		handleDiscountNumber(iDriver, iCustom);
	}
	
	private static void handleDiscountNumber(WebDriver iDriver, String iCustom)
	{
		// TODO Auto-generated method stub
	}
	
	public static void complete(WebDriver iDriver)
	{
		//完了
		PosUtil.findnClick(iDriver, BY.LINKTEXT, "完了");
	}

	public static Pattern addOption(WebDriver iDriver) throws InterruptedException
	{
		Thread.sleep(5000);
		
		try {
			List<WebElement> lListView = iDriver.findElements(By.xpath(".//ul[@class='listview']"));
			for (WebElement lElement: lListView) {
				List<WebElement> lLiList = lElement.findElements(By.xpath(".//li"));
				for (WebElement lLi : lLiList) {
					if (lLi.getAttribute("class").contains("green")){
						lLi.click();
						break;
					}
				}
			}
			
			List<WebElement> lWebElements = iDriver.findElements(By.cssSelector("div[ng-repeat='pattern in order.patterns']"));
			return new Pattern(lWebElements.get(lWebElements.size()-1));
		}
		catch (ElementNotVisibleException e) {
			return addOption(iDriver);
		}
		catch(WebDriverException e) {
			return addOption(iDriver);
		}
	}
	
	public static void getMenuInfo(WebDriver iDriver)
	{
		PosUtil.findnClick(iDriver, BY.LINKTEXT, "メニュー詳細情報");
	}
	
	public static void back(WebDriver iDriver)
	{
		PosUtil.findnClick(iDriver, BY.LINKTEXT, "閉じる");
	}
	
	public static List<WebElement> getPattern(WebDriver iDriver)
	{
		List<WebElement> lElements = iDriver.findElements(By.cssSelector("div[ng-repeat='pattern in order.patterns']"));
		return lElements;
	}
	
	private static Pattern parsePattern(WebElement iElement) 
	{
		Pattern lPattern = new Pattern(iElement);
		return lPattern;
	}
	
	public static void select(WebDriver iDriver, String iOption) {
		List<WebElement> lOptions = iDriver.findElements(By.cssSelector("li[ng-repeat='option in options']"));
		for (WebElement lOption: lOptions)
		{
			WebElement lLabel = lOption.findElement(By.xpath(".//label"));
			if (lLabel.getText().contains(iOption)) {
				lOption.click();
				break;
			}
		}
		
		
	}
	
	

}
