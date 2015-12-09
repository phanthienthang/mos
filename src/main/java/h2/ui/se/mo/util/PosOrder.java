package h2.ui.se.mo.util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import h2.ui.se.mo.util.PosUtil.BY;

public class PosOrder {

	/**
	 * Minus by click 
	 * 
	 * @param iDriver
	 */
	public static void minus(WebDriver iDriver)
	{
		handleAction(iDriver, "minus");
	}
	
	/**
	 * Plus by click
	 * 
	 * @param iDriver
	 */
	public static void plus(WebDriver iDriver){
		handleAction(iDriver, "plus");
	}
	
	/**
	 * Handle actions plus or minus by find and click
	 * 
	 * @param iDriver
	 * @param iAction
	 */
	private static void handleAction(WebDriver iDriver, String iAction) 
	{
		WebElement lActionPanel = iDriver.findElement(By.className("row"));
		List<WebElement> lActionElements = lActionPanel.findElements(By.xpath(".//a"));
		for (WebElement lElem: lActionElements)
		{
			if (lElem.getAttribute("class").contains(iAction)) {
				lElem.click();
			}
		}
	}

	/**
	 * Enter a discount value by parameter.
	 * @param iDriver
	 * @param iRate
	 */
	public static void discountRate(WebDriver iDriver, String iRate){
		WebElement lElement = iDriver.findElement(By.cssSelector("input[ng-model='order.DiscountRate__c']"));
		lElement.clear();
		lElement.sendKeys(iRate);
	}
	
	/**
	 * Clear value in discount rate field.
	 * 
	 * @param iDriver
	 */
	public static void clearDiscountRate(WebDriver iDriver) {
		iDriver.findElement(By.cssSelector("input[ng-model='order.DiscountRate__c']")).clear();
	}
	
	/**
	 * @param iDriver
	 * @param iPrice
	 */
	public static void discountPrice(WebDriver iDriver, String iPrice){
		WebElement lDiscountPrice = iDriver.findElement(By.cssSelector("input[ng-model='order.DiscountPrice__c']"));
		lDiscountPrice.clear();
		lDiscountPrice.sendKeys(iPrice);
	}
	/**
	 * Clean discount price field
	 * 
	 * @param iDriver
	 */
	public static void clearDiscountPrice(WebDriver iDriver){
		iDriver.findElement(By.cssSelector("input[ng-model='order.DiscountPrice__c']")).clear();
	}
	
	/**
	 * Enter priority price by parameter
	 * 
	 * @param iDriver
	 * @param iFixPrice
	 */
	public static void priorityPrice(WebDriver iDriver, String iFixPrice){
		WebElement lPriorityPriceElement = iDriver.findElement(By.cssSelector("input[ng-model='order.PriorityPrice__c']"));
		lPriorityPriceElement.clear();
		lPriorityPriceElement.sendKeys(iFixPrice);
	}
	
	/**
	 * Clean priority price
	 * 
	 * @param iDriver
	 */
	public static void clearPriorityPrice(WebDriver iDriver) {
		iDriver.findElement(By.cssSelector("input[ng-model='order.PriorityPrice__c']")).clear();
	}
	
	/**
	 * Enter comment value by parameter
	 * @param iDriver
	 * @param iComment
	 */
	public static void comment(WebDriver iDriver, String iComment) {
		WebElement lComment = iDriver.findElement(By.cssSelector("[ng-if='order.Comment__c == null']"));
		if (lComment.isDisplayed()) {
			lComment.click();
		}
		iDriver.findElement(By.cssSelector("input[ng-model='order.Comment__c']")).sendKeys(iComment);
	}
	
	/**
	 * Click update
	 * 
	 * @param iDriver
	 */
	public static void update(WebDriver iDriver)
	{
		//更新
		PosUtil.findnClick(iDriver, BY.LINKTEXT, "更新");
	}
	
	/**
	 * Click cancel
	 * 
	 * @param iDriver
	 */
	public static void cancel(WebDriver iDriver){
		//注文取消
		PosUtil.findnClick(iDriver, BY.LINKTEXT, "注文取消");
	}
	
	/**
	 * @param iDriver
	 * @param iRdmMenu
	 * @return
	 */
	public static String orderRandom(WebDriver iDriver, int iService, int iRate, int iPrice, String iComment, int iRdmMenu, int[] iMenuQuantity) 
	{
		WebElement lRdmTable = PosTable.pickRdmTable(iDriver);
		if (lRdmTable != null) {
			String lTableName = lRdmTable.getText().replaceAll("\\W", "");
			System.out.println("Random table: "+lTableName);
			if (!PosTable.isTabAvailable(lRdmTable)) 
			{
				orderRandom(iDriver, iService, iRate, iPrice, iComment, iRdmMenu, iMenuQuantity);
			} else {
				handleOrder(iDriver, lRdmTable, iService, iRate, iPrice, iComment, iRdmMenu, iMenuQuantity);
			}
			return lTableName;
		}
		return null;
	}
	
	public static void sendOrder(WebDriver iDriver)
	{
		PosUtil.findnClick(iDriver, BY.LINKTEXT, "送信");
		
	}
	
	public static void orderByTable(WebDriver iDriver, String iTable, int iServiceCharge, int iRate, int iPrice, String iComment) throws InterruptedException {
		
		//POS Processing
		WebElement lTableElem = PosTable.pickOrderTable(iDriver, iTable);
		System.out.println("Order table: " + iTable);
		handleOrder(iDriver, lTableElem, iServiceCharge, iRate, iPrice, iComment);
	}
	
	private static void handleOrder(WebDriver iDriver, WebElement iTableWeb, int iServiceCharge, int iRate, int iPrice, String iComment) throws InterruptedException
	{
		//PosUtil.openSetting(iDriver, "注文");
		
		
		iTableWeb.click();
		//pickTable(iDriver, iTableName);
		Thread.sleep(1000);
		handleRdmMale(iDriver);
		Thread.sleep(1000);
		handleRdmFemale(iDriver);
		Thread.sleep(1000);
		handleServiceCharge(iDriver, iServiceCharge);
		Thread.sleep(1000);
		handleDiscountRate(iDriver, iRate);
		Thread.sleep(1000);
		handleDiscountPrice(iDriver, iPrice);
		Thread.sleep(1000);
		handleComment(iDriver, iComment);
		Thread.sleep(1000);
		
		//Order
		PosUtil.order(iDriver);
		Thread.sleep(3000);
		
		//TODO - Implement the category check here!!!
		handleRdmCategory(iDriver);
		Thread.sleep(3000);
		handleRdmMenu(iDriver);
		Thread.sleep(3000);
		PosUtil.handleConfirm(iDriver);
		Thread.sleep(3000);
	}
	
	private static void handleOrder(WebDriver iDriver, WebElement iTableWeb, int iServiceCharge, int iRate, int iPrice, String iComment, int iMenuNo, int[] iMenuQuantity)
	{
		//PosUtil.openSetting(iDriver, "注文");
		iTableWeb.click();
		//pickTable(iDriver, iTableName);
		
		handleRdmMale(iDriver);
		
		handleRdmFemale(iDriver);
		
		handleServiceCharge(iDriver, iServiceCharge);
		
		handleDiscountRate(iDriver, iRate);
		
		handleDiscountPrice(iDriver, iPrice);
		
		handleComment(iDriver, iComment);
		
		//Order
		PosUtil.order(iDriver);
		
		//TODO - Implement the category check here!!!
		handleRdmCategory(iDriver);
		
		for (int i = 0; i < iMenuNo; i++) {
			handleRdmMenu(iDriver, iMenuQuantity[i]);
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		PosUtil.handleConfirm(iDriver);
	}
	
	/**
	 * Random order menu
	 * 
	 * @param iDriver
	 * @throws InterruptedException
	 */
	public static String orderRandom(WebDriver iDriver) throws InterruptedException 
	{
		System.out.println("Call method orderRandom(WebDriver iDriver) in PosUtil.java");
		WebElement lRdmTable = PosTable.pickRdmTable(iDriver);
		if (lRdmTable != null) {
			String lTableName = lRdmTable.getText().replaceAll("\\W", "");
			System.out.println("Random table: "+lTableName);
			if (!PosTable.isTabAvailable(lRdmTable)) 
			{
				orderRandom(iDriver);
			}
			handleOrder(iDriver, lRdmTable, 0, 0, 0, "Random Order");
			return lTableName;
		}
		else {
			return orderRandom(iDriver);
		}
		
	}
	
	public static String orderRandom(WebDriver iDriver, int iService, int iRate, int iPrice) throws InterruptedException 
	{
		System.out.println("Call method orderRandom(WebDriver iDriver) in PosUtil.java");
		WebElement lRdmTable = PosTable.pickRdmTable(iDriver);
		if (lRdmTable != null) {
			String lTableName = lRdmTable.getText().replaceAll("\\W", "");
			System.out.println("Random table: "+lTableName);
			if (!PosTable.isTabAvailable(lRdmTable)) 
			{
				orderRandom(iDriver,iService, iRate, iPrice);
			}
			handleOrder(iDriver, lRdmTable, iService, iRate, iPrice, " Order randomatically");
			return lTableName;
		}
		else {
			return orderRandom(iDriver, iService, iRate, iPrice);
		}
		
	}
	
	/**
	 * Random pick category
	 * @param iDriver
	 * @throws InterruptedException 
	 */
	public static void handleRdmCategory(WebDriver iDriver)
	{
		List<WebElement> lCategoryList = PosUtil.findElements(iDriver, BY.CSS, "li[ng-repeat='category in orderData.categories']");
		if (lCategoryList.size() != 0) {
			WebElement lCategoryRdm = lCategoryList.get(Util.random(lCategoryList.size()));
			if (lCategoryRdm != null) {
				try 
				{
					lCategoryRdm.click();
				} 
				catch (WebDriverException e)
				{
					handleRdmCategory(iDriver);
				}
			}
			else {
				lCategoryList.get(0).click();
			}
		} else {
			handleRdmCategory(iDriver);
		}
		
	}
	
	/**
	 * Random pick menu
	 * @param iDriver
	 * @throws InterruptedException 
	 */
	public static void handleRdmMenu(WebDriver iDriver)
	{
		List<WebElement> lMenuList = PosUtil.findElements(iDriver, BY.CSS, "a[ng-repeat='menu in orderData.menus | rowSlice:i:colnum");
		if (lMenuList.size() != 0) {
			WebElement lMenuRdm = lMenuList.get(Util.random(lMenuList.size()));
			if (lMenuRdm != null) {
				
				try 
				{
					lMenuRdm.click();
				} 
				catch (WebDriverException e)
				{
					handleRdmMenu(iDriver);
				}
				
			}
			else {
				lMenuList.get(0).click();
			}
			lMenuRdm.click();
		}
		else {
			handleRdmMenu(iDriver);
		}
		
	}
	
	/**
	 * Random pick menu
	 * @param iDriver
	 * @throws InterruptedException 
	 */
	public static void handleRdmMenu(WebDriver iDriver, int iQuantity)
	{
		List<WebElement> lMenuList = PosUtil.findElements(iDriver, BY.CSS, "a[ng-repeat='menu in orderData.menus | rowSlice:i:colnum");
		if (lMenuList.size() != 0) {
			WebElement lMenuRdm = lMenuList.get(Util.random(lMenuList.size()));
			if (lMenuRdm != null) {
				
				try 
				{
					for (int i = 0; i< iQuantity; i++) {
						lMenuRdm.click();
					}
					
				} 
				catch (WebDriverException e)
				{
					handleRdmMenu(iDriver);
				}
				
			}
			else {
				lMenuList.get(0).click();
			}
		}
		else {
			handleRdmMenu(iDriver);
		}
		
	}
	
	private static void handleRdmMale(WebDriver iDriver)
	{
		handleRdmSex(iDriver, "male");
	}
	
	private static void handleRdmFemale(WebDriver iDriver)
	{
		handleRdmSex(iDriver, "female");
	}
	
	private static void handleRdmSex(WebDriver iDriver, String iSex)
	{
		System.out.println("Enter sex option: "+ iSex);
		WebElement lSex = iDriver.findElement(By.id(iSex));
		if (lSex != null) {
			List<WebElement> lOptionList = lSex.findElements(By.xpath(".//option"));
			int lRdmNo = Util.random(lOptionList.size());
			try {
				lOptionList.get(lRdmNo).click();
			}
			catch (ElementNotVisibleException e) {
				handleRdmSex(iDriver, iSex);
			}
		}
		else {
			handleRdmSex(iDriver, iSex);
		}
		
	}
	
	public static void editRdmOrder(WebDriver iDriver, int iServiceCharge, int iRate, int iPrice, String iComment)
	{
		//PosUtil.openSetting(iDriver, "注文");
		
		//lTableList.get(lTableRdm).click();
		
		handleRdmMale(iDriver);
		
		handleRdmFemale(iDriver);
		
		handleServiceCharge(iDriver, iServiceCharge);
		
		handleDiscountRate(iDriver, iRate);
		
		handleDiscountPrice(iDriver, iPrice);
		
		handleComment(iDriver, iComment);
		
		update(iDriver);
		//Order
		//PosUtil.order(iDriver);
		
		//TODO - Implement the category check here!!!
		//handleRdmCategory(iDriver);
		
		//handleRdmMenu(iDriver);
		//handleConfirm(iDriver);
		
		
	}
	
	public static void handleComment(WebDriver iDriver, String iComment)
	{
		//findnClick(iDriver, BY.ID, "comment");
		iDriver.findElement(By.id("comment")).sendKeys(iComment);
	}
	
	private static void handleServiceCharge(WebDriver iDriver, int iServiceCharge)
	{
		iDriver.findElement(By.id("service_rate")).sendKeys(String.valueOf(iServiceCharge));
	}

	private static void handleDiscountRate(WebDriver iDriver, int iRate) 
	{
		iDriver.findElement(By.id("discount_rate")).sendKeys(String.valueOf(iRate));
	}

	public static void handleDiscountPrice(WebDriver iDriver, int iPrice)
	{
		iDriver.findElement(By.id("discount_price")).sendKeys(String.valueOf(iPrice));
	}
	
	
}
