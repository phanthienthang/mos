package h2.ui.se.mo.util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import h2.ui.se.mo.util.PosUtil.BY;

public class PosLogUtil 
{
	/**
	 * Reason open cashbox
	 * @param iDriver
	 */
	public static void reason(WebDriver iDriver, String iReason)
	{
		PosUtil.findnClick(iDriver, BY.ID, "reason");
		WebElement lReason = iDriver.findElement(By.id("reason"));
		
		List<WebElement> lOptions = lReason.findElements(By.xpath(".//option"));
		
		for (WebElement lWebElement: lOptions) 
		{
			if (lWebElement.getText().contains(iReason)) {
				lWebElement.click();
				break;
			}
		}
	}
	
	public static void cashin(WebDriver iDriver, String iAmountIn)
	{
		iDriver.findElement(By.id("amountIn")).sendKeys(iAmountIn);
	}
	
	public static void cash10kIn(WebDriver iDriver, String iAmount10kIn){
		iDriver.findElement(By.id("numberOf10000YenIn")).sendKeys(iAmount10kIn);
	}
	
	public static void cashout(WebDriver iDriver, String iAmountOut){
		iDriver.findElement(By.id("amountOut")).sendKeys(iAmountOut);
	}
	
	public static void cash10kOut(WebDriver iDriver, String iAmount10kOut){
		iDriver.findElement(By.id("numberOf10000YenOut")).sendKeys(iAmount10kOut);
	}
	
	public static void comment(WebDriver iDriver, String iComment){
		iDriver.findElement(By.id("comment")).sendKeys(iComment);
	}
}
