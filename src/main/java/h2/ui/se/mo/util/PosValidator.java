package h2.ui.se.mo.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PosValidator 
{
	/**
	 * Check element with specified ID is display or not
	 * 
	 * @param iDriver
	 * @param iID
	 * @return
	 */
	public static boolean isExistID (WebDriver iDriver, String iID) 
	{
		WebDriverWait lWaitDriver = new WebDriverWait(iDriver, 30);
		lWaitDriver.until(ExpectedConditions.elementToBeClickable(By.id(iID)));
		if (iDriver.findElement(By.id(iID)).isDisplayed())
		{
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * Check the xPath is exist or not.
	 * 
	 * @param iDriver
	 * @param iXPath
	 * @return
	 */
	public static boolean isExistXPath(WebDriver iDriver, String iXPath) 
	{
		WebDriverWait lWaitDriver = new WebDriverWait(iDriver, 30);
		lWaitDriver.until(ExpectedConditions.elementToBeClickable(By.xpath(iXPath)));
		if (iDriver.findElement(By.xpath(iXPath)).isDisplayed())
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * @param iDriver
	 * @param iCssSelector
	 * @return
	 */
	public static boolean isExistCssSelector(WebDriver iDriver, String iCssSelector) 
	{
		WebDriverWait lWaitDriver = new WebDriverWait(iDriver, 30);
		lWaitDriver.until(ExpectedConditions.elementToBeClickable(By.cssSelector(iCssSelector)));
		if (iDriver.findElement(By.cssSelector(iCssSelector)).isDisplayed())
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * @param iDriver
	 * @param iName
	 * @return
	 */
	public static boolean isExistName(WebDriver iDriver, String iName) 
	{
		WebDriverWait lWaitDriver = new WebDriverWait(iDriver, 30);
		lWaitDriver.until(ExpectedConditions.elementToBeClickable(By.name(iName)));
		if (iDriver.findElement(By.name(iName)).isDisplayed())
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Check the element can input value or not.
	 * 
	 * @param iDriver
	 * @param iId
	 * @return
	 */
	public static boolean isInputable(WebDriver iDriver, String iId) 
	{
		WebDriverWait lWaitDriver = new WebDriverWait(iDriver, 30);
		lWaitDriver.until(ExpectedConditions.elementToBeClickable(By.id(iId)));
		if (iDriver.findElement(By.id(iId)).isDisplayed())
		{
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * Check the link is exist or not
	 * 
	 * @param iDriver
	 * @param iLink
	 * @return
	 */
	public static boolean isExistLink(WebDriver iDriver, String iLink) 
	{
		WebDriverWait lWaitDriver = new WebDriverWait(iDriver, 30);
		lWaitDriver.until(ExpectedConditions.elementToBeClickable(By.linkText(iLink)));
		if (iDriver.findElement(By.linkText(iLink)).isDisplayed())
		{
			return true;
		}
		
		return false;
	}

}
