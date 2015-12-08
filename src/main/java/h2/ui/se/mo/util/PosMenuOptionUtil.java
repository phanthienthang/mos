package h2.ui.se.mo.util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import h2.ui.se.mo.util.PosUtil.BY;

public class PosMenuOptionUtil 
{
	
	/**
	 * @param iDriver
	 */
	public static void complete(WebDriver iDriver)
	{
		//完了
		PosUtil.findnClick(iDriver, BY.LINKTEXT, "完了");
	}

	/**
	 * Add new pattern option and return the latest added pattern.
	 * 
	 * @param iDriver
	 * @return
	 * @throws InterruptedException
	 */
	public static Pattern addOption(WebDriver iDriver) throws InterruptedException
	{
		Thread.sleep(5000);
		
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
	
	/**
	 * @param iDriver
	 */
	public static void getMenuInfo(WebDriver iDriver)
	{
		PosUtil.findnClick(iDriver, BY.LINKTEXT, "メニュー詳細情報");
	}
	
	/**
	 * Click back link
	 * 
	 * @param iDriver
	 */
	public static void back(WebDriver iDriver)
	{
		PosUtil.findnClick(iDriver, BY.LINKTEXT, "閉じる");
	}
	
	/**
	 * Get list of pattern
	 * 
	 * @param iDriver
	 * @return
	 */
	public static List<WebElement> getPattern(WebDriver iDriver)
	{
		List<WebElement> lElements = iDriver.findElements(By.cssSelector("div[ng-repeat='pattern in order.patterns']"));
		return lElements;
	}
	
	/**
	 * Select specified option
	 * 
	 * @param iDriver
	 * @param iOption
	 */
	public static void select(WebDriver iDriver, String iOption) 
	{
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
