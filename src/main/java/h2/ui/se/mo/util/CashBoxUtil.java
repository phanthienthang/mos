package h2.ui.se.mo.util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import h2.ui.se.mo.util.PosUtil.BY;

public class CashBoxUtil 
{
	/**
	 * Select the reasons:
	 * 理由 - Pay off
	 * 入金 --> Payment
	 * 出金 --> Withdrawal
	 * 両替 --> Currency Exchange
	 * 返金 --> Refund
	 * 
	 * @param iDriver
	 * @param iOption
	 */
	public static void selectReason(WebDriver iDriver, String iOption)
	{
		WebElement lReasonWE = iDriver.findElement(By.id("reason"));
		List<WebElement> lReasonList = lReasonWE.findElements(By.xpath(".//option"));
		
		for (WebElement webElement : lReasonList) 
		{
			if(webElement.getText().equalsIgnoreCase(iOption)) 
			{
				webElement.click();
			}
		}
	}
	
	/**
	 * Auto enter value
	 * 
	 * @param iDriver
	 * @param iPayCash
	 * @param iPay10k
	 * @param iWdrawCash
	 * @param iWdraw10k
	 * @param iComment
	 */
	public static void inputValue(WebDriver iDriver, String iPayCash, String iPay10k, String iWdrawCash, String iWdraw10k, String iComment){
		
		handleInputValue(iDriver, iPayCash, iPay10k, iWdrawCash, iWdraw10k, iComment);
	}
	
	private static void handleInputValue(WebDriver iDriver, String iPayCash, String iPay10k, String iWdrawCash, String iWdraw10k, String iComment) {
		
		//入金 - Payment - 円--> yen
		
		//万券 - numberOf10000YenIn
		iDriver.findElement(By.id("numberOf10000YenIn")).sendKeys(iPayCash);
		
		//金額 - amountIn
		iDriver.findElement(By.id("amountIn")).sendKeys(iPay10k);
		
		//金額 - amountOut
		iDriver.findElement(By.id("amountOut")).sendKeys(iWdrawCash);
		
		//万券  - numberOf10000YenOut
		iDriver.findElement(By.id("numberOf10000YenOut")).sendKeys(iWdraw10k);
		
		//コメント - Comment
		iDriver.findElement(By.id("comment")).sendKeys(iComment);
	}
	
	/**
	 * Unlock 
	 * @param iDriver
	 */
	public static void unlock(WebDriver iDriver) 
	{
		//解錠
		PosUtil.findnClick(iDriver, BY.LINKTEXT, "解錠");
		
	}

}
