/**
 * 
 */
package h2.ui.se.mo.util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import h2.ui.se.mo.util.PosUtil.BY;

/**
 * @author thienthang
 *
 */
public class CheckOutPOSUtil 
{
	
	public enum Type {Info, Payment}
	
	public static void updateDocument(WebDriver iDriver){ 
		handleUpdateLink(iDriver, Type.Info);
	}
	
	public static void updatePaymentInfo(WebDriver iDriver)
	{
		handleUpdateLink(iDriver, Type.Payment);
	}
	
	public static void handlePayByCash(WebDriver iDriver)
	{
		handlePayment(iDriver, "現金");
		
		int lTotal = Integer.valueOf(CheckOutPOSUtil.handleGetAmount(iDriver)) + Integer.valueOf(CheckOutPOSUtil.handleTaxAmount(iDriver));
		//System.out.println("Total Amount: "+CheckOutPOSUtil.handleGetAmount(iDriver)+ " Tax:" + Integer.valueOf(CheckOutPOSUtil.handleTaxAmount(iDriver)));
		NumpadUtil.handleInputNo(iDriver, String.valueOf(lTotal));
	}
	
	public static void handlePayByCard(WebDriver iDriver)
	{
		handlePayment(iDriver, "クレジットカード");
	}
	
	public static void handlePayByGifs(WebDriver iDriver)
	{
		handlePayment(iDriver, "商品券");
	}
	
	public static void handlePayByGif(WebDriver iDriver)
	{
		handlePayment(iDriver, "商品券(釣り有り)");
	}
	
	public static void handlePayByOther(WebDriver iDriver)
	{
		handlePayment(iDriver, "掛計");
	}
	
	public static void handleCheckOut(WebDriver iDriver)
	{
		WebElement lCheckoutElem = iDriver.findElement(By.linkText("チェックアウト"));
		if (lCheckoutElem.isEnabled()) {
			lCheckoutElem.click();
		}	
	}
	
	public static void handleCancel(WebDriver iDriver)
	{
		WebElement lCancelElem = iDriver.findElement(By.linkText("会計取消"));
		if (lCancelElem.isEnabled()) {
			lCancelElem.click();
		}
	}
	
	public static String handleGetAmount(WebDriver iDriver) 
	{
		String lAmount = "0";
		List<WebElement> lListView = iDriver.findElements(By.xpath("//li[@class='form total']"));
		for (int i = 0; i< lListView.size(); i++) {
			
			//WebElement lElementLbl = lListView.get(i).findElement(By.tagName("label"));
			WebElement lElementDiv = lListView.get(i).findElement(By.tagName("div"));
			lAmount = lElementDiv.getText().replaceAll("[^\\w]", "");
		}
		
		return lAmount;
	}
	
	public static String handleTaxAmount(WebDriver iDriver) 
	{
		String lAmount = "0";
		List<WebElement> lListView = iDriver.findElements(By.xpath("//li[@class='form tax']"));
		for (int i = 0; i< lListView.size(); i++) {
			
			WebElement lElementDiv = lListView.get(i).findElement(By.tagName("div"));
			lAmount = lElementDiv.getText().replaceAll("[^\\w]", "");
		}
		
		return lAmount;
		
	}
	
	
	
	public static void handleInputServiceCharge(WebDriver iDriver, String iValue)
	{
		handleInput(iDriver, "input[ng-model='editingBill.ServiceCharge__c']", iValue);
	}
	
	public static void handleInputDiscountRate(WebDriver iDriver, String iValue){
		handleInput(iDriver, "input[ng-model='editingBill.DiscountRate__c']", iValue);
	}
	
	public static void handleInputDiscountPrice(WebDriver iDriver, String iValue)
	{
		handleInput(iDriver, "input[ng-model='editingBill.DiscountPrice__c']", iValue);
	}
	
	//Close and get back to previous screen
	public static void handleClose(WebDriver iDriver){
		PosUtil.findnClick(iDriver, BY.LINKTEXT, "Close");
	}
	
	//会計取消 - cancel account
	public static void handleCancelAccount(WebDriver iDriver)
	{
		PosUtil.findnClick(iDriver, BY.LINKTEXT, "会計取消");
	}
	
	private static void handlePayment(WebDriver iDriver, String iPayment)
	{
		List<WebElement> lPaymentList = iDriver.findElements(By.cssSelector("li[ng-repeat-start='paymentMethod in data.paymentMethods']"));
		for (int i = 0; i < lPaymentList.size(); i++) 
		{
			if (lPaymentList.get(i).getText().equals(iPayment)) 
			{
				lPaymentList.get(i).click();
				break;
			}
		}
	}
	
	private static void handleInput(WebDriver iDriver, String iSelector, String iValue) {
		WebElement lElement = iDriver.findElement(By.cssSelector(iSelector));
		lElement.sendKeys(iValue);
	}
	
	private static void handleUpdateLink(WebDriver iDriver, Type iType){
		List<WebElement> lElementList = iDriver.findElements(By.linkText("編集"));
		switch (iType) {
		case Info:
			lElementList.get(0).click();
			break;
		case Payment:
			lElementList.get(1).click();
			break;
		}
		
	}

}
