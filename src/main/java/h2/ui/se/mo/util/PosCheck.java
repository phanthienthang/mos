/**
 * 
 */
package h2.ui.se.mo.util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import h2.ui.se.mo.util.PosNumPad.Card;
import h2.ui.se.mo.util.PosUtil.BY;

/**
 * @author thienthang
 *
 */
public class PosCheck 
{
	
	public enum Type {Info, Payment}
	
	public static void updateDocument(WebDriver iDriver)
	{ 
		handleUpdateLink(iDriver, Type.Info);
	}
	
	public static void updatePaymentInfo(WebDriver iDriver)
	{
		handleUpdateLink(iDriver, Type.Payment);
	}
	
	public static void handlePayByCash(WebDriver iDriver)
	{
		handlePayCash(iDriver, 0);
	}
	
	public static void handlePayExtraByCash(WebDriver iDriver, int iExtraAmount)
	{
		handlePayCash(iDriver, iExtraAmount);
	}
	
	private static void handlePayCash(WebDriver iDriver, int iExtra) {
		
		handlePayment(iDriver, "現金");
		
		int lTotal = Integer.valueOf(PosCheck.handleGetAmount(iDriver)) + Integer.valueOf(PosCheck.handleTaxAmount(iDriver));
		lTotal = iExtra + lTotal;
		//System.out.println("Total Amount: "+CheckOutPOSUtil.handleGetAmount(iDriver)+ " Tax:" + Integer.valueOf(CheckOutPOSUtil.handleTaxAmount(iDriver)));
		PosNumPad.handleInputNo(iDriver, String.valueOf(lTotal));
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
		if (lCheckoutElem.isEnabled())
		{
			lCheckoutElem.click();
		}
		else {
			handleCheckOut(iDriver);
		}
	}
	
	public static void handleCancel(WebDriver iDriver)
	{
		WebElement lCancelElem = iDriver.findElement(By.linkText("会計取消"));
		if (lCancelElem.isEnabled()) {
			lCancelElem.click();
		}
		else {
			handleCancel(iDriver);
		}
	}
	
	public static String handleGetAmount(WebDriver iDriver) 
	{
		String lAmount = "0";
		List<WebElement> lListView = iDriver.findElements(By.xpath("//li[@class='form total']"));
		if (lListView.size() != 0) {
			for (int i = 0; i< lListView.size(); i++) {
				
				//WebElement lElementLbl = lListView.get(i).findElement(By.tagName("label"));
				WebElement lElementDiv = lListView.get(i).findElement(By.tagName("div"));
				lAmount = lElementDiv.getText().replaceAll("[^\\w]", "");
			}
			return lAmount;
		}
		else {
			return handleGetAmount(iDriver);
		}
		
		
		
	}
	
	public static String handleTaxAmount(WebDriver iDriver) 
	{
		String lAmount = "0";
		List<WebElement> lListView = iDriver.findElements(By.xpath("//li[@class='form tax']"));
		if (lListView.size() != 0) {
			for (int i = 0; i< lListView.size(); i++) {
				
				WebElement lElementDiv = lListView.get(i).findElement(By.tagName("div"));
				lAmount = lElementDiv.getText().replaceAll("[^\\w]", "");
			}
			return lAmount;
		} else {
			return handleTaxAmount(iDriver);
		}
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
		if (lPaymentList.size() != 0) {
			for (int i = 0; i < lPaymentList.size(); i++) 
			{
				if (lPaymentList.get(i).getText().equals(iPayment)) 
				{
					lPaymentList.get(i).click();
					break;
				}
			}
		}
		else {
			handlePayment(iDriver, iPayment);
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

	public static void payOrder(WebDriver iDriver)
	{
		handlePayment(iDriver, "現金");
		int lTotal = Integer.valueOf(PosCheck.handleGetAmount(iDriver)) + Integer.valueOf(PosCheck.handleTaxAmount(iDriver));
		int lHalf = lTotal/2;
		if ((lTotal%2)!= 0) 
		{
			lHalf = Math.round(lHalf);
		}
		//Thread.sleep(5000);
		PosNumPad.handleInputNo(iDriver, String.valueOf(lHalf));
		
		handlePayment(iDriver, "クレジットカード");
		PosNumPad.handleInputNo(iDriver, String.valueOf(lHalf), Card.Amex);
		
	}
	
	public static void updateMale(WebDriver iDriver, int iNo) {
		WebElement lSelectElement = iDriver.findElement(By.cssSelector("select[ng-model='editingCheck.NumberOfMale__c']"));
		lSelectElement.click();
		List<WebElement> lOptions = lSelectElement.findElements(By.xpath(".//option"));
		for (WebElement option : lOptions) {
			if (option.getAttribute("value").equalsIgnoreCase(String.valueOf(iNo))) {
				option.click();
				break;
			}
		}
	}
	
	public static void updateFemale(WebDriver iDriver, int iNo) {
		WebElement lSelectElement = iDriver.findElement(By.cssSelector("select[ng-model='editingCheck.NumberOfFemale__c']"));
		lSelectElement.click();
		List<WebElement> lOptions = lSelectElement.findElements(By.xpath(".//option"));
		for (WebElement option : lOptions) {
			if (option.getAttribute("value").equalsIgnoreCase(String.valueOf(iNo))) {
				option.click();
				break;
			}
		}
	}
	
	public static void updateComment(WebDriver iDriver, String iComment) {
		WebElement lCommentElement = iDriver.findElement(By.cssSelector("textarea[ng-model='editingCheck.Comment__c']"));
		lCommentElement.clear();
		lCommentElement.sendKeys(iComment);
	}
	
	public static void update(WebDriver iDriver, Type iType) {
		
		switch (iType) {
		case Info:
			handleUpdate(iDriver, "ul[ng-if='!!isCheckEditing']");
			break;
		case Payment:
			handleUpdate(iDriver, "ul[ng-if='!!isBillEditing']");
			break;
		}
		
	}
	
	private static void handleUpdate(WebDriver iDriver, String iCss) 
	{
		WebElement lUlElem = iDriver.findElement(By.cssSelector(iCss));
		WebElement lDivElem = lUlElem.findElement(By.xpath(".//div"));
		List<WebElement> lAElem = lDivElem.findElements(By.xpath(".//a"));
		for (WebElement webElement : lAElem)
		{
			if (webElement.getText().contains("更新")) {
				webElement.click();
				break;
			}
		}
	}
	
	public static void updateServiceCharge(WebDriver iDriver, int iAmount) {
		WebElement lElement = iDriver.findElement(By.cssSelector("input[ng-model='editingBill.ServiceCharge__c']"));
		lElement.clear();
		lElement.sendKeys(String.valueOf(iAmount));
	}
	
	public static void updateDiscountRate(WebDriver iDriver, int iAmount) {
		WebElement lElement = iDriver.findElement(By.cssSelector("input[ng-model='editingBill.DiscountRate__c']"));
		lElement.clear();
		lElement.sendKeys(String.valueOf(iAmount));
	}
	
	public static void updateDiscountPrice(WebDriver iDriver, int iAmount) {
		WebElement lElement = iDriver.findElement(By.cssSelector("input[ng-model='editingBill.DiscountPrice__c']"));
		lElement.clear();
		lElement.sendKeys(String.valueOf(iAmount));
	}

	public static void payByCard(WebDriver iDriver, Card iAmex)
	{
		handlePayByCard(iDriver);
		int lTotal = Integer.valueOf(PosCheck.handleGetAmount(iDriver)) + Integer.valueOf(PosCheck.handleTaxAmount(iDriver));
		PosNumPad.handleInputNo(iDriver, String.valueOf(lTotal), Card.Amex);
	}
	
	public static void payByGift(WebDriver iDriver) {
		handlePayByGifs(iDriver);
		int lTotal = Integer.valueOf(PosCheck.handleGetAmount(iDriver)) + Integer.valueOf(PosCheck.handleTaxAmount(iDriver));
		PosNumPad.handleInputNo(iDriver, String.valueOf(lTotal));
		
	}

	public static void payByAll(WebDriver iDriver)
	{
		handlePayByCash(iDriver);
		int lTotal = Integer.valueOf(PosCheck.handleGetAmount(iDriver)) + Integer.valueOf(PosCheck.handleTaxAmount(iDriver));
		int lTriple = lTotal/3;
		if ((lTotal%3)!= 0) 
		{
			lTriple = Math.round(lTriple);
		}
		//Thread.sleep(5000);
		PosNumPad.handleInputNo(iDriver, String.valueOf(lTriple));
		
		handlePayByCard(iDriver);
		PosNumPad.handleInputNo(iDriver, String.valueOf(lTriple), Card.Amex);
		
		handlePayByGifs(iDriver);
		PosNumPad.handleInputNo(iDriver, String.valueOf(lTriple));
		
	}
	
	public static String getCheckNo(WebDriver iDriver) {
		WebElement lElem = iDriver.findElement(By.cssSelector("ul[ng-if='!isCheckEditing']"));
		WebElement lLiElem = lElem.findElement(By.xpath(".//li[@class='form']"));
		WebElement lDivElem = lLiElem.findElement(By.xpath(".//div[@class='ng-binding']"));
		return lDivElem.getText();
	}

}
