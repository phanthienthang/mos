package h2.ui.se.mo.util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import h2.ui.se.mo.util.PosUtil.BY;

public class OrderUtil {

	public static void minus(WebDriver iDriver){
		handleAction(iDriver, "minus");
	}
	
	private static void handleAction(WebDriver iDriver, String iAction) {
		WebElement lActionPanel = iDriver.findElement(By.className("row"));
		List<WebElement> lActionElements = lActionPanel.findElements(By.xpath(".//a"));
		for (WebElement lElem: lActionElements)
		{
			if (lElem.getAttribute("class").contains(iAction)) {
				lElem.click();
			}
		}
	}

	public static void plus(WebDriver iDriver){
		handleAction(iDriver, "plus");
	}
	
	public static void discountRate(WebDriver iDriver, String iRate){
		WebElement lElement = iDriver.findElement(By.cssSelector("input[ng-model='order.DiscountRate__c']"));
		lElement.clear();
		lElement.sendKeys(iRate);
	}
	
	public static void clearDiscountRate(WebDriver iDriver) {
		iDriver.findElement(By.cssSelector("input[ng-model='order.DiscountRate__c']")).clear();
	}
	
	public static void discountPrice(WebDriver iDriver, String iPrice){
		WebElement lDiscountPrice = iDriver.findElement(By.cssSelector("input[ng-model='order.DiscountPrice__c']"));
		lDiscountPrice.clear();
		lDiscountPrice.sendKeys(iPrice);
	}
	public static void clearDiscountPrice(WebDriver iDriver){
		iDriver.findElement(By.cssSelector("input[ng-model='order.DiscountPrice__c']")).clear();
	}
	
	public static void priorityPrice(WebDriver iDriver, String iFixPrice){
		WebElement lPriorityPriceElement = iDriver.findElement(By.cssSelector("input[ng-model='order.PriorityPrice__c']"));
		lPriorityPriceElement.clear();
		lPriorityPriceElement.sendKeys(iFixPrice);
	}
	
	public static void clearPriorityPrice(WebDriver iDriver) {
		iDriver.findElement(By.cssSelector("input[ng-model='order.PriorityPrice__c']")).clear();
	}
	
	public static void comment(WebDriver iDriver, String iComment) {
		WebElement lComment = iDriver.findElement(By.cssSelector("[ng-if='order.Comment__c == null']"));
		if (lComment.isDisplayed()) {
			lComment.click();
		}
		iDriver.findElement(By.cssSelector("input[ng-model='order.Comment__c']")).sendKeys(iComment);
	}
	
	public static void update(WebDriver iDriver)
	{
		//更新
		PosUtil.findnClick(iDriver, BY.LINKTEXT, "更新");
	}
	
	public static void cancel(WebDriver iDriver){
		//注文取消
		PosUtil.findnClick(iDriver, BY.LINKTEXT, "注文取消");
	}
}
