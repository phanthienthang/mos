package h2.ui.se.mo.util;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckUtil 
{
	/**
	 * Check element display or not
	 * 
	 * @param iDriver
	 * @param iID
	 * @return
	 */
	private static boolean isExistID (WebDriver iDriver, String iID) 
	{
		try
		{
			WebDriverWait lWaitDriver = new WebDriverWait(iDriver, 30);
			lWaitDriver.until(ExpectedConditions.elementToBeClickable(By.id(iID)));
			if (iDriver.findElement(By.id(iID)).isDisplayed())
			{
				return true;
			}
			
			return false;
		} catch (NotFoundException e){
			System.out.println("Element was not found!");
			return false;
			
		} catch (TimeoutException e) {
			System.out.println("Loading time is expired!");
			new Throwable(e.getMessage());
			//return true;
		}
		return false;
	}
	
	/**
	 * Check element is display or not
	 * 
	 * @param iDriver
	 * @param iID
	 * @return
	 */
	public static boolean checkExistID(WebDriver iDriver, String iID)
	{
		return isExistID(iDriver, iID);
	}
	
	
	/**
	 * Check the xPath is exist or not.
	 * 
	 * @param iDriver
	 * @param iXPath
	 * @return
	 */
	private static boolean isExistXPath(WebDriver iDriver, String iXPath) 
	{
		try
		{
			WebDriverWait lWaitDriver = new WebDriverWait(iDriver, 30);
			lWaitDriver.until(ExpectedConditions.elementToBeClickable(By.xpath(iXPath)));
			if (iDriver.findElement(By.xpath(iXPath)).isDisplayed())
			{
				return true;
			}
			
			return false;
		} catch (NotFoundException e){
			System.out.println("Element was not found!");
			return false;
			
		} catch (TimeoutException e) {
			System.out.println("Loading time is expired!");
			new Throwable(e.getMessage());
			//return true;
		}
		return false;
	}
	
	/**
	 * Check the xPath is exist or not in loop.
	 * 
	 * @param iDriver
	 * @param iXPath
	 * @return
	 */
	public static boolean checkExistXPath(WebDriver iDriver, String iXPath) 
	{
		return isExistXPath(iDriver, iXPath);
	}
	
	
	private static boolean isExistCssSelector(WebDriver iDriver, String iCssSelector) 
	{
		try
		{
			WebDriverWait lWaitDriver = new WebDriverWait(iDriver, 30);
			lWaitDriver.until(ExpectedConditions.elementToBeClickable(By.cssSelector(iCssSelector)));
			if (iDriver.findElement(By.cssSelector(iCssSelector)).isDisplayed())
			{
				return true;
			}
			
			return false;
			
		} catch (NotFoundException e){
			System.out.println("Element was not found!");
			return false;
			
		} catch (TimeoutException e) {
			System.out.println("Loading time is expired!");
			new Throwable(e.getMessage());
			//return true;
		}
		return false;
	}
	
	public static boolean checkExistCssSelector(WebDriver iDriver, String iCssSelector) 
	{
		return isExistCssSelector(iDriver, iCssSelector);
	}
	
	private static boolean isExistName(WebDriver iDriver, String iName) 
	{
		try
		{
			WebDriverWait lWaitDriver = new WebDriverWait(iDriver, 30);
			lWaitDriver.until(ExpectedConditions.elementToBeClickable(By.cssSelector(iName)));
			if (iDriver.findElement(By.name(iName)).isDisplayed())
			{
				return true;
			}
			
			return false;
			
		} catch (NotFoundException e){
			System.out.println("Element was not found!");
			return false;
			
		} catch (TimeoutException e) {
			System.out.println("Loading time is expired!");
			new Throwable(e.getMessage());
			//return true;
		}
		
		return false;
	}
	
	public static boolean checkExistName(WebDriver iDriver, String iName) 
	{
		return isExistName(iDriver, iName);
	}

}
