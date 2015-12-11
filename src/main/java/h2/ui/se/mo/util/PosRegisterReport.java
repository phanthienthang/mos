package h2.ui.se.mo.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import h2.ui.se.mo.util.PosUtil.BY;

public class PosRegisterReport
{
	
	
	
	public static void viewRegisterOrder(){}
	
	public static void fillRegisterOrder(WebDriver iDriver)
	{
		String lAmount = parseReportAmount(iDriver);
		registCash(iDriver, Double.valueOf(lAmount));
		
	}
	
	private static void registCash(WebDriver iDriver, Double iAmount)
	{
		Map<Double, String> lCouterMap = count(iAmount);
		for(Double lEntry: lCouterMap.keySet()) 
		{
			
			iDriver.findElement(By.cssSelector(new StringBuilder().append("input[ng-model='report.ActNumberOf")
					.append(String.valueOf(Math.round(Double.valueOf(lEntry)))).append("Yen__c']").toString()))
					.sendKeys(String.valueOf(lCouterMap.get(lEntry)));
		}
	}
	
	
	static double[] yen = new double[]{1, 5, 10, 50, 100, 500, 1000, 2000, 5000, 10000};
	
	private static Map<Double, String>  count(double iAmount) 
	{
		Map<Double, String>  lResult = new HashMap<Double, String>();
		for (int i = 1; i < yen.length; i++) {
			
			if (iAmount >= yen[yen.length-i]) 
			{
				Long lAmountNo = Math.round(Math.floor(iAmount/(yen[yen.length-i])));
				lResult.put(yen[yen.length-i], Long.toString(lAmountNo) );
				iAmount = iAmount - (yen[yen.length-i]*lAmountNo);
				//System.out.println(lResult);
			}
		}
		return lResult;
	}
	
	private static String parseReportAmount(WebDriver iDriver) 
	{
		StringBuilder lTotalAmount = new StringBuilder();
		List<WebElement> lUlElemList = iDriver.findElements(By.cssSelector("ul[class='listview']"));
		boolean isContinue = true;
		for(WebElement lUL: lUlElemList) {
			if (lUL.isDisplayed() && isContinue) 
			{
				List<WebElement> lLiElementList = lUL.findElements(By.xpath(".//li"));
				for (WebElement lLi: lLiElementList) {
					WebElement lLabelElemt = null;
					try 
					{
						lLabelElemt = lLi.findElement(By.xpath(".//label"));
					}
					catch (NoSuchElementException e) {
						continue;
					}
					
					if (lLabelElemt != null && lLabelElemt.getText().contains("合計")) {
						WebElement lDivElemt = lLi.findElement(By.xpath(".//div"));
						lTotalAmount.append(lDivElemt.getText().replaceAll("[^\\w]", ""));
						isContinue = false;
						break;
					}
				}
			}
		}
		
		return lTotalAmount.toString();
	}
}
