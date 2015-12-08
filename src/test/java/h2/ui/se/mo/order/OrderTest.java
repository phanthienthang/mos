package h2.ui.se.mo.order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import h2.ui.se.mo.util.OrderUtil;
import h2.ui.se.mo.util.Pattern;
import h2.ui.se.mo.util.PosMenuOptionUtil;
import h2.ui.se.mo.util.PosUtil;
import h2.ui.se.mo.util.PosUtil.BY;

public class OrderTest {
	
	//@Test
	public void testT1831() throws InterruptedException
	{
		
		WebDriver lDriver = PosUtil.initLocal();
		Thread.sleep(5000);
		PosUtil.openSetting(lDriver, "注文");
		Thread.sleep(5000);
		String lTable = PosUtil.orderRandom(lDriver, 0, 0, 0, "T-1-8-3-1", 2, new int[]{1, 2});
		
		PosUtil.sendOrder(lDriver);
		Thread.sleep(8000);
		
		String lPosWindow = new ArrayList<String> (lDriver.getWindowHandles()).get(0);
		lDriver.switchTo().alert().dismiss();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		
		PosUtil.viewTabHistory(lDriver, lTable);
		
		Thread.sleep(8000);
		try {
			PosUtil.screenshot(lDriver, "T1831.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread.sleep(6000);
		PosUtil.close(lDriver);
		
		//Browser Salesforce 
		//Get the latest record
		//Capture screen
		
		//Test T-1-8-3-2
		//追加注文
		PosUtil.addOrder(lDriver, lTable);
		Thread.sleep(3000);
		PosUtil.pickCategory(lDriver, "ｱﾗｶﾙﾄ");
		PosUtil.holdMenu(lDriver, "牛肉の辛し炒め");
		
		PosMenuOptionUtil.addOption(lDriver);
		
		List<WebElement> lPatternList = PosMenuOptionUtil.getPattern(lDriver);
		Pattern lPatternOption = new Pattern(lPatternList.get(0));
		lPatternOption.plus();
		
		Pattern lPattern1 = new Pattern(lPatternList.get(1));
		handleAddOption(lPattern1, 2);
		lPattern1.editOption();
		Thread.sleep(2000);
		PosMenuOptionUtil.select(lDriver, "大盛り");
		PosUtil.back(lDriver);
		
		
		Pattern lPattern = PosMenuOptionUtil.addOption(lDriver);
		handleAddOption(lPattern, 3);
		lPattern.editOption();
		Thread.sleep(2000);
		PosMenuOptionUtil.select(lDriver, "少なめ");
		PosUtil.back(lDriver);
		
		Pattern lPattern2 = PosMenuOptionUtil.addOption(lDriver);
		handleAddOption(lPattern2, 4);
		lPattern2.editOption();
		Thread.sleep(2000);
		PosMenuOptionUtil.select(lDriver, "野菜抜き");
		PosUtil.back(lDriver);
		
		Thread.sleep(3000);
		PosUtil.close(lDriver);
		PosUtil.handleConfirm(lDriver);
		PosUtil.sendOrder(lDriver);
		Thread.sleep(5000);
		lDriver.switchTo().alert().dismiss();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		
		//Test T-1-8-4-1
		Thread.sleep(5000);
		PosUtil.viewTabHistory(lDriver, lTable);
		Thread.sleep(5000);
		List<WebElement> lOrderHistoryList = lDriver.findElements(By.cssSelector("li[ng-repeat='order in history.Orders__r']"));
		lOrderHistoryList.get(PosUtil.random(lOrderHistoryList.size())).click();
		Thread.sleep(3000);
		
		OrderUtil.plus(lDriver);
		
		Thread.sleep(1000);
		
		OrderUtil.update(lDriver);
		
		//Test T-1-8-4-2
		Thread.sleep(5000);
		PosUtil.close(lDriver);
		PosUtil.viewTabHistory(lDriver, lTable);
		Thread.sleep(5000);
		lOrderHistoryList = lDriver.findElements(By.cssSelector("li[ng-repeat='order in history.Orders__r']"));
		lOrderHistoryList.get(PosUtil.random(lOrderHistoryList.size())).click();
		Thread.sleep(3000);
		
		OrderUtil.minus(lDriver);
		Thread.sleep(2000);
		
		OrderUtil.update(lDriver);
		Thread.sleep(5000);
		PosUtil.close(lDriver);
		
		
		
		//Test T-1-8-4-3 -値引
		Thread.sleep(5000);
		PosUtil.viewTabHistory(lDriver, lTable);
		Thread.sleep(5000);
		lOrderHistoryList = lDriver.findElements(By.cssSelector("li[ng-repeat='order in history.Orders__r']"));

		//Random position
		int lDiscountRate = 1;
		int lDiscountPrice = 2;
		int lPriority = 3;
		
		lOrderHistoryList.get(lDiscountRate).click();
		Thread.sleep(3000);
		
		OrderUtil.discountRate(lDriver, "15");
		
		Thread.sleep(2000);
		OrderUtil.update(lDriver);
		Thread.sleep(5000);
		PosUtil.close(lDriver);
		
		//T-1-8-4-4 - 値引
		Thread.sleep(5000);
		PosUtil.viewTabHistory(lDriver, lTable);
		Thread.sleep(5000);
		lOrderHistoryList = lDriver.findElements(By.cssSelector("li[ng-repeat='order in history.Orders__r']"));
		lOrderHistoryList.get(lDiscountPrice).click();
		Thread.sleep(3000);
		
		OrderUtil.discountPrice(lDriver, "100");
		
		Thread.sleep(2000);
		OrderUtil.update(lDriver);
		Thread.sleep(5000);
		
		//Check Salesforce here
		
		PosUtil.close(lDriver);
		
		//T-1-8-4-5-金額指定 
		Thread.sleep(5000);
		PosUtil.viewTabHistory(lDriver, lTable);
		Thread.sleep(5000);
		lOrderHistoryList = lDriver.findElements(By.cssSelector("li[ng-repeat='order in history.Orders__r']"));
		lOrderHistoryList.get(lPriority).click();
		Thread.sleep(3000);
		OrderUtil.priorityPrice(lDriver, "500");
		Thread.sleep(2000);
		OrderUtil.update(lDriver);
		Thread.sleep(5000);
		PosUtil.close(lDriver);
		
		//T-1-8-4-6
		Thread.sleep(5000);
		PosUtil.viewTabHistory(lDriver, lTable);
		Thread.sleep(5000);
		lOrderHistoryList = lDriver.findElements(By.cssSelector("li[ng-repeat='order in history.Orders__r']"));
		lOrderHistoryList.get(lPriority).click();
		Thread.sleep(3000);
		
		OrderUtil.priorityPrice(lDriver, "0");
		
		Thread.sleep(2000);
		OrderUtil.update(lDriver);
		Thread.sleep(5000);
		PosUtil.close(lDriver);
		
		//T-1-8-4-7
		Thread.sleep(5000);
		PosUtil.viewTabHistory(lDriver, lTable);
		Thread.sleep(5000);
		lOrderHistoryList = lDriver.findElements(By.cssSelector("li[ng-repeat='order in history.Orders__r']"));
		lOrderHistoryList.get(lPriority).click();
		Thread.sleep(3000);
		
		OrderUtil.clearPriorityPrice(lDriver);
		
		Thread.sleep(2000);
		OrderUtil.update(lDriver);
		Thread.sleep(5000);
		PosUtil.close(lDriver);
		
		//T-1-8-4-8
		Thread.sleep(5000);
		PosUtil.viewTabHistory(lDriver, lTable);
		Thread.sleep(5000);
		lOrderHistoryList = lDriver.findElements(By.cssSelector("li[ng-repeat='order in history.Orders__r']"));
		lOrderHistoryList.get(1).click();
		Thread.sleep(3000);
		
		OrderUtil.clearDiscountRate(lDriver);
		
		Thread.sleep(2000);
		OrderUtil.update(lDriver);
		Thread.sleep(5000);
		PosUtil.close(lDriver);
		
		//T-1-8-4-9
		Thread.sleep(5000);
		PosUtil.viewTabHistory(lDriver, lTable);
		Thread.sleep(5000);
		lOrderHistoryList = lDriver.findElements(By.cssSelector("li[ng-repeat='order in history.Orders__r']"));
		lOrderHistoryList.get(2).click();
		Thread.sleep(3000);
		
		OrderUtil.clearDiscountPrice(lDriver);
		
		Thread.sleep(2000);
		OrderUtil.update(lDriver);
		Thread.sleep(5000);
		PosUtil.close(lDriver);
		
		//T-1-8-4-10
		Thread.sleep(5000);
		PosUtil.viewTabHistory(lDriver, lTable);
		Thread.sleep(5000);
		lOrderHistoryList = lDriver.findElements(By.cssSelector("li[ng-repeat='order in history.Orders__r']"));
		lOrderHistoryList.get(1).click();
		Thread.sleep(3000);
		OrderUtil.discountRate(lDriver, "10");
		OrderUtil.clearDiscountRate(lDriver);
		
		OrderUtil.discountPrice(lDriver, "100");
		Thread.sleep(2000);
		OrderUtil.update(lDriver);
		Thread.sleep(5000);
		PosUtil.close(lDriver);
		
		//T-1-8-4-11
		Thread.sleep(5000);
		PosUtil.viewTabHistory(lDriver, lTable);
		Thread.sleep(5000);
		lOrderHistoryList = lDriver.findElements(By.cssSelector("li[ng-repeat='order in history.Orders__r']"));
		lOrderHistoryList.get(2).click();
		Thread.sleep(3000);
		
		OrderUtil.priorityPrice(lDriver, "500");
		OrderUtil.clearPriorityPrice(lDriver);
		OrderUtil.discountRate(lDriver, "10");
		Thread.sleep(2000);
		OrderUtil.update(lDriver);
		Thread.sleep(5000);
		PosUtil.close(lDriver);
		
		//T-1-8-4-12
		Thread.sleep(5000);
		PosUtil.viewTabHistory(lDriver, lTable);
		Thread.sleep(5000);
		lOrderHistoryList = lDriver.findElements(By.cssSelector("li[ng-repeat='order in history.Orders__r']"));
		lOrderHistoryList.get(2).click();
		Thread.sleep(3000);
		OrderUtil.discountPrice(lDriver, "90");
		OrderUtil.clearDiscountPrice(lDriver);
		OrderUtil.discountRate(lDriver, "10");
		
		Thread.sleep(2000);
		OrderUtil.update(lDriver);
		Thread.sleep(5000);
		PosUtil.close(lDriver);
		
		//T-1-8-4-13
		Thread.sleep(5000);
		PosUtil.viewTabHistory(lDriver, lTable);
		Thread.sleep(5000);
		lOrderHistoryList = lDriver.findElements(By.cssSelector("li[ng-repeat='order in history.Orders__r']"));
		lOrderHistoryList.get(3).click();
		Thread.sleep(3000);
		OrderUtil.priorityPrice(lDriver, "200");
		OrderUtil.clearPriorityPrice(lDriver);
		OrderUtil.discountRate(lDriver, "10");
		
		Thread.sleep(2000);
		OrderUtil.update(lDriver);
		Thread.sleep(5000);
		PosUtil.close(lDriver);
		
		//T-1-8-4-14
		Thread.sleep(5000);
		PosUtil.viewTabHistory(lDriver, lTable);
		Thread.sleep(5000);
		lOrderHistoryList = lDriver.findElements(By.cssSelector("li[ng-repeat='order in history.Orders__r']"));
		lOrderHistoryList.get(1).click();
		Thread.sleep(3000);
		OrderUtil.discountRate(lDriver, "5");
		OrderUtil.clearDiscountRate(lDriver);
		OrderUtil.priorityPrice(lDriver, "200");
		
		
		Thread.sleep(2000);
		OrderUtil.update(lDriver);
		Thread.sleep(5000);
		PosUtil.close(lDriver);
		
		//T-1-8-4-15
		Thread.sleep(5000);
		PosUtil.viewTabHistory(lDriver, lTable);
		Thread.sleep(5000);
		lOrderHistoryList = lDriver.findElements(By.cssSelector("li[ng-repeat='order in history.Orders__r']"));
		lOrderHistoryList.get(2).click();
		Thread.sleep(3000);
		OrderUtil.discountPrice(lDriver, "30");
		OrderUtil.clearDiscountPrice(lDriver);
		OrderUtil.priorityPrice(lDriver, "50");
		
		Thread.sleep(2000);
		OrderUtil.update(lDriver);
		Thread.sleep(5000);
		PosUtil.close(lDriver);
		
		
	}
	
	@Test
	public void testT1851() throws InterruptedException
	{
		WebDriver lDriver = PosUtil.initLocal();
		
		Thread.sleep(5000);
		PosUtil.openSetting(lDriver, "注文");
		Thread.sleep(5000);
		
		WebElement lTable = PosUtil.pickRdmUsingTable(lDriver);
		
		PosUtil.viewTabHistory(lDriver, lTable.getText());
		Thread.sleep(10000);
		List<WebElement> lOrderHistoryList = lDriver.findElements(By.cssSelector("li[ng-repeat='order in history.Orders__r']"));
		int lOrder = lOrderHistoryList.size()-1;
		lOrderHistoryList.get(lOrder).click();
		Thread.sleep(10000);
		OrderUtil.cancel(lDriver);
		String lPosWindow = new ArrayList<String> (lDriver.getWindowHandles()).get(0);
		lDriver.switchTo().alert().accept();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		
		PosUtil.close(lDriver);
		
		//Check Salesforce Record to make sure this field  キャンセル済み is set to true
		
		
		
		
	}
	
	
	private void handleAddOption(Pattern iPattern, int iTime){
		
		for (int i = 0; i< iTime; i++)
		{
			iPattern.plus();
		}
		
	}
	

}
