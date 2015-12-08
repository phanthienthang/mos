/**
 * 
 */
package h2.ui.se.mo.tab;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import h2.ui.se.mo.util.Browser;
import h2.ui.se.mo.util.NumpadUtil;
import h2.ui.se.mo.util.OrderUtil;
import h2.ui.se.mo.util.Pattern;
import h2.ui.se.mo.util.PosCheckUtil;
import h2.ui.se.mo.util.PosMenuOptionUtil;
import h2.ui.se.mo.util.PosUtil;
import h2.ui.se.mo.util.NumpadUtil.Card;
import h2.ui.se.mo.util.PosCheckUtil.Type;
import h2.ui.se.mo.util.PosUtil.BY;

/**
 * @author thienthang
 *
 */
public class TabTest {
	
	WebDriver mDriver;
	
	public void init() throws InterruptedException 
	{
		mDriver = PosUtil.initLocal();
		Thread.sleep(5000);
	}
	
	
	//@Test
	public void testTab() throws InterruptedException, IOException 
	{
		
		init();
		
		Thread.sleep(5000);
		
		PosUtil.openSetting(mDriver, "注文");

		//Test T-1-7-1-1
		String lTableName = PosUtil.orderRandom(mDriver, 5, 5, 20);
		
		PosUtil.sendOrder(mDriver);
		
		Thread.sleep(5000);
		
		//PosUtil.handleAlert(mDriver);
		String lPosWindow = new ArrayList<String> (mDriver.getWindowHandles()).get(0);
		mDriver.switchTo().alert().dismiss();
		if (PosUtil.hasAlert(mDriver))
		{
			mDriver.switchTo().alert().dismiss();
		}
		mDriver.switchTo().window(lPosWindow);
		//Browse salesforce????
		
		//Test T-1-7-2-1
		PosUtil.openSetting(mDriver, "注文");
		
		PosUtil.handleEdit(mDriver, lTableName);
		
		PosUtil.editRdmOrder(mDriver, 10, 7, 8, "Edit Randomatically");
		
		//Test T-1-7-3-1
		PosUtil.viewTabHistory(mDriver, lTableName);
		Thread.sleep(7000);
		
		PosUtil.screenshot(mDriver, "T1731.png");
		
		PosUtil.close(mDriver);
		
		//Test T-1-7-4-1
		PosUtil.moveTable(mDriver, lTableName);
		WebElement lTblElem = PosUtil.pickRdmTable(mDriver);
		lTableName = lTblElem.getText();
		lTblElem.click();
		
		PosUtil.findnClick(mDriver, BY.LINKTEXT, "移動");
		Thread.sleep(5000);
		PosUtil.findnClick(mDriver, BY.LINKTEXT, "更新");
		PosUtil.screenshot(mDriver, "T1741.png");
		
		//Test T-1-7-5-1
		PosUtil.shareTable(mDriver, lTableName);
		PosUtil.editRdmOrder(mDriver, 4, 5, 6, "Share Table");
		PosUtil.cancel(mDriver);
		Thread.sleep(5000);
		PosUtil.screenshot(mDriver, "T1751.png");
		//TODO-Browse salesforce for checking
		
		//Test T-1-7-6-1
		PosUtil.findnClick(mDriver, BY.LINKTEXT, "選択");
		WebElement lTblEle1Grp = PosUtil.pickRdmTable(mDriver);
		WebElement lTblEle2Grp = PosUtil.pickRdmTable(mDriver);
		PosUtil.findnClick(mDriver, BY.LINKTEXT, "新規伝票");
		PosUtil.editRdmOrder(mDriver, 4, 5, 6, "Share Table");
		PosUtil.findnClick(mDriver, BY.LINKTEXT, "キャンセル");
		
		//Test T-1-7-7-1
		PosUtil.deleteTab(mDriver, lTableName);
		Thread.sleep(5000);
		PosUtil.screenshot(mDriver, "T1771.png");
		
		//Test T-1-7-7-2
		PosUtil.findnClick(mDriver, BY.LINKTEXT, "編集");
		PosUtil.handleAlert(mDriver);
		//Browser the salesforce to check キャンセル済み
	}
	
	@Test
	public void testT1911() throws InterruptedException
	{
		WebDriver lDriver = PosUtil.initLocal();
		Thread.sleep(5000);
		PosUtil.openSetting(lDriver, "注文");
		Thread.sleep(5000);
		WebElement lTable = PosUtil.pickRdmTable(lDriver);
		String lTableName = lTable.getText();
				
		PosUtil.orderByTable(lDriver, lTableName, 0, 0, 0, "T-1-9-4-4");
		//String lTable = PosUtil.orderRandom(lDriver, 0, 0, 0, "Rdm T-1-9-4-3", 1, new int[]{1});
		
		PosUtil.sendOrder(lDriver);
		Thread.sleep(8000);
		
		String lPosWindow = new ArrayList<String> (lDriver.getWindowHandles()).get(0);
		lDriver.switchTo().alert().dismiss();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		
		Thread.sleep(5000);
		
		PosUtil.checkOut(lDriver, lTableName);
		Thread.sleep(5000);
		PosCheckUtil.handlePayExtraByCash(lDriver, 200);
		
		PosCheckUtil.handleCheckOut(lDriver);
		Thread.sleep(5000);
		lDriver.switchTo().alert().accept();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		Thread.sleep(5000);
		//Test T-1-9-4-1
		//Task the screen picture
		try {
			PosUtil.screenshot(lDriver, "T1941.png");
		}
		catch (IOException e) {
			
		}
		Thread.sleep(2000);
		String lCheckNo = PosCheckUtil.getCheckNo(lDriver);
		
		Thread.sleep(5000);
		
		//Test T-1-9-3-1 This test case should go after T-1-9-4-1
		PosUtil.findnClick(lDriver, BY.LINKTEXT, "会計取消");
		Thread.sleep(3000);
		lDriver.switchTo().alert().accept();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		
		Browser.browse(lDriver, "会計", lCheckNo);
		
		Thread.sleep(5000);
		
		lDriver.close();
		
		lDriver.switchTo().window(lPosWindow);
		
		//Browse Salesforce
		/*Inside salesforce, click on the 会計 record, the following fields match
		- 会計日時 = empty
		- 会計済み = false
		- 支払い明細 > 支払い済み = false
		- 支払い明細 > 支払い > キャンセル = true
		4. In 支払い明細 > 入出金記録, the 返金（money return） information should be recorded : 
		- 入金額 = the amount of change, 返金 = true
		- 出金額 = the amount paid, 返金 = true
		　　→ this process cancels the money received in T-1-9-1-1/T-1-9-4-1*/
		
		//Test T-1-9-4-2 - クレジットカード
		PosCheckUtil.payByCard(lDriver, Card.Amex);
		Thread.sleep(3000);
		PosCheckUtil.handleCheckOut(lDriver);
		Thread.sleep(5000);
		lDriver.switchTo().alert().accept();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		Thread.sleep(5000);
		//Browse Salesforce for double check information
		/*3. In salesforce, go to the 会計 > 支払い明細 record
		4. Check that 支払い合計（買掛） is equal to the amount you entered
		5. Check that there is a 支払い record with a for the クレジットカード 支払い方法
		6. In 支払い明細 > 入出金記録, the 入出金 information should be recorded :
		- 入金額 = the amount you entered, 返金 = false
		- 出金額 = the amount of change, 返金 = false*/
		
		//Test T-1-9-3-2 - Cancel Bill (クレジットカード)
		PosUtil.findnClick(lDriver, BY.LINKTEXT, "会計取消");
		Thread.sleep(3000);
		lDriver.switchTo().alert().accept();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		
		/*3. Inside salesforce, click on the 会計 record, the following fields match
		- 会計日時 = empty
		- 会計済み = false
		- 支払い明細 > 支払い済み = false
		- 支払い明細 > 支払い > キャンセル = true
		4. In 支払い明細 > 入出金記録, the 返金（money return） information should be recorded : 
		- 入金額 = the amount of change, 返金 = true
		- 出金額 = the amount paid, 返金 = true
		　　→ this process cancels the money received in T-1-9-1-1/T-1-9-4-2*/
		
		//Test T-1-9-4-3 - 商品券
		PosCheckUtil.payByGift(lDriver);
		Thread.sleep(3000);
		PosCheckUtil.handleCheckOut(lDriver);
		Thread.sleep(5000);
		lDriver.switchTo().alert().accept();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		Thread.sleep(5000);
		
		/*3. In salesforce, go to the 会計 > 支払い明細 record
		4. Check that 支払い合計（買掛） is equal to the amount you entered
		5. Check that there is a 支払い record with a for the クレジットカード 支払い方法
		6. In 支払い明細 > 入出金記録, the 入出金 information should be recorded :
		- 入金額 = the amount you entered, 返金 = false
		- 出金額 = the amount of change, 返金 = false*/

		
		//Test T-1-9-3-3 - Cancel Bill (商品券)
		PosUtil.findnClick(lDriver, BY.LINKTEXT, "会計取消");
		Thread.sleep(3000);
		lDriver.switchTo().alert().accept();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		
		 /*Inside salesforce, click on the 会計 record, the following fields match
		 - 会計日時 = empty
		 - 会計済み = false
		 - 支払い明細 > 支払い済み = false
		 - 支払い明細 > 支払い > キャンセル = true
		 4. In 支払い明細 > 入出金記録, the 返金（money return） information should be recorded : 
		 - 入金額 = the amount of change, 返金 = true
		 - 出金額 = the amount paid, 返金 = true
		 　　→ this process cancels the money received in T-1-9-1-1/T-1-9-4-3*/
		
		//Test - T-1-9-4-4
		PosCheckUtil.payByAll(lDriver);
		Thread.sleep(3000);
		PosCheckUtil.handleCheckOut(lDriver);
		Thread.sleep(5000);
		lDriver.switchTo().alert().accept();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		Thread.sleep(5000);
		
		 /*In salesforce, go to the 会計 > 支払い明細 record
		 4. Check that 支払い合計（現金） is equal to the amount you entered using 現金 (cash)
		 5. Check that 支払い合計（買掛） is equal to the amount you entered using クレジットカード and 商品券
		 5. Check that there are 支払い records for each payment
		 6. In 支払い明細 > 入出金記録, the 入出金 information should be recorded for each payment
		 - 入金額 = the amount you entered, 返金 = false*/
		
		
	}

	//@Test
	public void testT1912() throws InterruptedException {
		WebDriver lDriver = PosUtil.initLocal();
		Thread.sleep(5000);
		PosUtil.openSetting(lDriver, "注文");
		Thread.sleep(5000);
		//WebElement lTable = PosUtil.pickRdmTable(lDriver);
		//String lTableName = lTable.getText();
				
		//PosUtil.orderByTable(lDriver, lTableName, 0, 0, 0, "T-1-9-1-2");
		String lTable = PosUtil.orderRandom(lDriver, 10, 0, 0, "T-1-8-3-1", 2, new int[]{1, 1});
		
		PosUtil.sendOrder(lDriver);
		Thread.sleep(8000);
		
		String lPosWindow = new ArrayList<String> (lDriver.getWindowHandles()).get(0);
		lDriver.switchTo().alert().dismiss();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		
		Thread.sleep(5000);
		
		PosUtil.viewTabHistory(lDriver, lTable);
		Thread.sleep(5000);
		List<WebElement> lOrderHistoryList = lDriver.findElements(By.cssSelector("li[ng-repeat='order in history.Orders__r']"));
		lOrderHistoryList.get(PosUtil.random(lOrderHistoryList.size())).click();
		Thread.sleep(3000);
		
		Thread.sleep(10000);
		OrderUtil.cancel(lDriver);
		lDriver.switchTo().alert().accept();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		
		PosUtil.close(lDriver);
		
		PosUtil.checkOut(lDriver, lTable);
		Thread.sleep(5000);
		PosCheckUtil.handlePayExtraByCash(lDriver, 200);
		
		PosCheckUtil.handleCheckOut(lDriver);
		Thread.sleep(5000);
		lDriver.switchTo().alert().accept();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		
		PosUtil.close(lDriver);
		
		//Test T-1-9-1-5
		 /*In the 支払い明細 record, check the following fields:
		 - 小計サービス料率 = 10%
		 - 小計割引率 = empty
		 - 小計値引額 = empty

		 - 小計金額 = amount of menu item not cancelled
		 - サービス料合計 = 小計金額 * 10%

		 - 合計金額 = 小計金額 + サービス料合計*/
		
	}
	
	//@Test
	public void testT1913() throws InterruptedException {
		WebDriver lDriver = PosUtil.initLocal();
		Thread.sleep(5000);
		PosUtil.openSetting(lDriver, "注文");
		Thread.sleep(5000);
		//WebElement lTable = PosUtil.pickRdmTable(lDriver);
		//String lTableName = lTable.getText();
				
		//PosUtil.orderByTable(lDriver, lTableName, 0, 0, 0, "T-1-9-1-2");
		String lTable = PosUtil.orderRandom(lDriver, 0, 10, 0, "T-1-9-1-3", 2, new int[]{1, 1});
		
		PosUtil.sendOrder(lDriver);
		Thread.sleep(8000);
		
		String lPosWindow = new ArrayList<String> (lDriver.getWindowHandles()).get(0);
		lDriver.switchTo().alert().dismiss();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		
		Thread.sleep(5000);
		
		//PosUtil.viewTabHistory(lDriver, lTable);
		
		//追加注文
		PosUtil.addOrder(lDriver, lTable);
		Thread.sleep(3000);
		PosUtil.pickCategory(lDriver, "ｱﾗｶﾙﾄ");
		PosUtil.holdMenu(lDriver, "牛肉の辛し炒め");
		
		PosMenuOptionUtil.addOption(lDriver);
		
		List<WebElement> lPatternList = PosMenuOptionUtil.getPattern(lDriver);
		//Pattern lPatternOption = new Pattern(lPatternList.get(0));
		//lPatternOption.plus();
		
		Pattern lPattern1 = new Pattern(lPatternList.get(1));
		lPattern1.plus();
		lPattern1.editOption();
		Thread.sleep(2000);
		PosMenuOptionUtil.select(lDriver, "大盛り");
		Thread.sleep(2000);
		PosUtil.back(lDriver);
		Thread.sleep(3000);
		PosUtil.close(lDriver);
		PosUtil.handleConfirm(lDriver);
		PosUtil.sendOrder(lDriver);
		Thread.sleep(5000);
		lDriver.switchTo().alert().accept();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		
		PosUtil.checkOut(lDriver, lTable);
		Thread.sleep(5000);
		PosCheckUtil.handlePayExtraByCash(lDriver, 200);
		
		PosCheckUtil.handleCheckOut(lDriver);
		Thread.sleep(5000);
		lDriver.switchTo().alert().accept();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		
		PosUtil.close(lDriver);
		
		//Test T-1-9-1-6
		//Browse Salesforce to check
		 /*支払い明細 record, check the following fields:
		 - 小計サービス料率 = empty
		 - 小計割引率 = 10%
		 - 小計値引額 = empty

		 - 小計金額 = amount of menu item + option
		 - 小計割引額 = 小計金額 * 10%

		 - 合計金額 = 小計金額 - 小計割引額*/
	}
	
	//@Test
	public void testT1914() throws InterruptedException{
		WebDriver lDriver = PosUtil.initLocal();
		Thread.sleep(5000);
		PosUtil.openSetting(lDriver, "注文");
		Thread.sleep(5000);
		//WebElement lTable = PosUtil.pickRdmTable(lDriver);
		//String lTableName = lTable.getText();
				
		//PosUtil.orderByTable(lDriver, lTableName, 0, 0, 0, "T-1-9-1-2");
		String lTable = PosUtil.orderRandom(lDriver, 10, 0, 0, "T-1-9-1-4", 2, new int[]{1, 1});
		
		PosUtil.sendOrder(lDriver);
		Thread.sleep(8000);
		
		String lPosWindow = new ArrayList<String> (lDriver.getWindowHandles()).get(0);
		lDriver.switchTo().alert().dismiss();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		
		Thread.sleep(5000);
		
		PosUtil.checkOut(lDriver, lTable);
		Thread.sleep(5000);
		PosCheckUtil.handlePayExtraByCash(lDriver, 200);
		
		PosCheckUtil.handleCheckOut(lDriver);
		Thread.sleep(5000);
		lDriver.switchTo().alert().accept();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		
		PosUtil.close(lDriver);
		
		//Test T-1-9-1-7
		//Browser Salesforce to test
		/*In the 支払い明細 record, check the following fields:
		- 小計サービス料率 = empty
		- 小計割引率 = empty
		- 小計値引額 = 10

		- 小計金額 = amount of all menu items

		- 合計金額 = 小計金額 - 小計値引額*/
		
		
	}
	
	//@Test
	public void testT1918() throws InterruptedException {
		
		WebDriver lDriver = PosUtil.initLocal();
		Thread.sleep(5000);
		PosUtil.openSetting(lDriver, "注文");
		Thread.sleep(5000);
		//WebElement lTable = PosUtil.pickRdmTable(lDriver);
		//String lTableName = lTable.getText();
				
		//PosUtil.orderByTable(lDriver, lTableName, 0, 0, 0, "T-1-9-1-2");
		String lTable = PosUtil.orderRandom(lDriver, 10, 100, 300, "T-1-9-1-8", 2, new int[]{1, 1});
		
		PosUtil.sendOrder(lDriver);
		Thread.sleep(8000);
		
		String lPosWindow = new ArrayList<String> (lDriver.getWindowHandles()).get(0);
		lDriver.switchTo().alert().dismiss();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		
		Thread.sleep(5000);
		PosUtil.checkOut(lDriver, lTable);
		Thread.sleep(5000);
		
/*		PosUtil.checkOut(lDriver, lTable);
		Thread.sleep(5000);
		PosCheckUtil.handlePayExtraByCash(lDriver, 200);
		
		PosCheckUtil.handleCheckOut(lDriver);
		Thread.sleep(5000);
		lDriver.switchTo().alert().accept();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
*/		
		PosUtil.close(lDriver);
		
		//T-1-9-1-9 - 支払い情報確認
		
		//T-1-9-2-1 -伝票情報の編集
		Thread.sleep(5000);
		PosUtil.checkOut(lDriver, lTable);
		Thread.sleep(5000);
		
		PosCheckUtil.updateDocument(lDriver);
		PosCheckUtil.updateMale(lDriver, 5);
		PosCheckUtil.updateFemale(lDriver, 5);
		PosCheckUtil.updateComment(lDriver, "T-1-9-2-1");
		PosCheckUtil.update(lDriver, Type.Info);
		Thread.sleep(5000);
		
		//T-1-9-2-2 - 支払い情報の編集
		PosCheckUtil.updatePaymentInfo(lDriver);
		PosCheckUtil.updateServiceCharge(lDriver, 5);
		PosCheckUtil.updateDiscountRate(lDriver, 10);
		PosCheckUtil.updateDiscountPrice(lDriver, 200);
		PosCheckUtil.update(lDriver, Type.Payment);
	}
	
	//@Test
	public void testT1910() throws InterruptedException {
		WebDriver lDriver = PosUtil.initLocal();
		Thread.sleep(5000);
		PosUtil.openSetting(lDriver, "注文");
		Thread.sleep(5000);
		//WebElement lTable = PosUtil.pickRdmTable(lDriver);
		//String lTableName = lTable.getText();
				
		//PosUtil.orderByTable(lDriver, lTableName, 0, 0, 0, "T-1-9-1-2");
		String lTable = PosUtil.orderRandom(lDriver, 0, 10, 0, "T-1-9-1-10", 2, new int[]{1, 1});
		
		PosUtil.sendOrder(lDriver);
		Thread.sleep(8000);
		
		String lPosWindow = new ArrayList<String> (lDriver.getWindowHandles()).get(0);
		lDriver.switchTo().alert().dismiss();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		
		Thread.sleep(5000);
		
		//PosUtil.viewTabHistory(lDriver, lTable);
		
		//追加注文
		PosUtil.addOrder(lDriver, lTable);
		Thread.sleep(3000);
		PosUtil.pickCategory(lDriver, "ｱﾗｶﾙﾄ");
		PosUtil.holdMenu(lDriver, "牛肉の辛し炒め");
		
		PosMenuOptionUtil.addOption(lDriver);
		
		List<WebElement> lPatternList = PosMenuOptionUtil.getPattern(lDriver);
		//Pattern lPatternOption = new Pattern(lPatternList.get(0));
		//lPatternOption.plus();
		
		Pattern lPattern1 = new Pattern(lPatternList.get(1));
		lPattern1.plus();
		lPattern1.priorityPrice("2000");
		lPattern1.editOption();
		Thread.sleep(2000);
		PosMenuOptionUtil.select(lDriver, "大盛り");
		Thread.sleep(2000);
		PosUtil.back(lDriver);
		Thread.sleep(3000);
		PosUtil.close(lDriver);
		PosUtil.handleConfirm(lDriver);
		PosUtil.sendOrder(lDriver);
		Thread.sleep(5000);
		lDriver.switchTo().alert().accept();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		
		PosUtil.checkOut(lDriver, lTable);
//		Thread.sleep(5000);
//		PosCheckUtil.handlePayExtraByCash(lDriver, 200);
//		
//		PosCheckUtil.handleCheckOut(lDriver);
//		Thread.sleep(5000);
//		lDriver.switchTo().alert().accept();
//		if (PosUtil.hasAlert(lDriver))
//		{
//			lDriver.switchTo().alert().dismiss();
//		}
//		lDriver.switchTo().window(lPosWindow);
//		
//		PosUtil.close(lDriver);
	}
	
	//@Test
	public void testT19111() throws InterruptedException {
		WebDriver lDriver = PosUtil.initLocal();
		Thread.sleep(5000);
		PosUtil.openSetting(lDriver, "注文");
		Thread.sleep(5000);
		//WebElement lTable = PosUtil.pickRdmTable(lDriver);
		//String lTableName = lTable.getText();
				
		//PosUtil.orderByTable(lDriver, lTableName, 0, 0, 0, "T-1-9-1-2");
		String lTable = PosUtil.orderRandom(lDriver, 0, 10, 0, "T-1-9-1-11", 2, new int[]{1, 1});
		
		PosUtil.sendOrder(lDriver);
		Thread.sleep(8000);
		
		String lPosWindow = new ArrayList<String> (lDriver.getWindowHandles()).get(0);
		lDriver.switchTo().alert().dismiss();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		
		Thread.sleep(5000);
		
		//PosUtil.viewTabHistory(lDriver, lTable);
		
		//追加注文
		PosUtil.addOrder(lDriver, lTable);
		Thread.sleep(3000);
		PosUtil.pickCategory(lDriver, "ｱﾗｶﾙﾄ");
		PosUtil.holdMenu(lDriver, "牛肉の辛し炒め");
		
		PosMenuOptionUtil.addOption(lDriver);
		
		List<WebElement> lPatternList = PosMenuOptionUtil.getPattern(lDriver);
		//Pattern lPatternOption = new Pattern(lPatternList.get(0));
		//lPatternOption.plus();
		
		Pattern lPattern1 = new Pattern(lPatternList.get(1));
		lPattern1.plus();
		lPattern1.discountRate("5");
		lPattern1.editOption();
		Thread.sleep(2000);
		PosMenuOptionUtil.select(lDriver, "大盛り");
		Thread.sleep(2000);
		PosUtil.back(lDriver);
		Thread.sleep(3000);
		PosUtil.close(lDriver);
		PosUtil.handleConfirm(lDriver);
		PosUtil.sendOrder(lDriver);
		Thread.sleep(5000);
		lDriver.switchTo().alert().accept();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		
		PosUtil.checkOut(lDriver, lTable);
//		Thread.sleep(5000);
//		PosCheckUtil.handlePayExtraByCash(lDriver, 200);
//		
//		PosCheckUtil.handleCheckOut(lDriver);
//		Thread.sleep(5000);
//		lDriver.switchTo().alert().accept();
//		if (PosUtil.hasAlert(lDriver))
//		{
//			lDriver.switchTo().alert().dismiss();
//		}
//		lDriver.switchTo().window(lPosWindow);
//		
//		PosUtil.close(lDriver);
	}
	
	//@Test
	public void testT19112() throws InterruptedException {
		WebDriver lDriver = PosUtil.initLocal();
		Thread.sleep(5000);
		PosUtil.openSetting(lDriver, "注文");
		Thread.sleep(5000);
		//WebElement lTable = PosUtil.pickRdmTable(lDriver);
		//String lTableName = lTable.getText();
				
		//PosUtil.orderByTable(lDriver, lTableName, 0, 0, 0, "T-1-9-1-2");
		String lTable = PosUtil.orderRandom(lDriver, 0, 10, 0, "T-1-9-1-12", 2, new int[]{1, 1});
		
		PosUtil.sendOrder(lDriver);
		Thread.sleep(8000);
		
		String lPosWindow = new ArrayList<String> (lDriver.getWindowHandles()).get(0);
		lDriver.switchTo().alert().dismiss();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		
		Thread.sleep(5000);
		
		//PosUtil.viewTabHistory(lDriver, lTable);
		
		//追加注文
		PosUtil.addOrder(lDriver, lTable);
		Thread.sleep(3000);
		PosUtil.pickCategory(lDriver, "ｱﾗｶﾙﾄ");
		PosUtil.holdMenu(lDriver, "牛肉の辛し炒め");
		
		PosMenuOptionUtil.addOption(lDriver);
		
		List<WebElement> lPatternList = PosMenuOptionUtil.getPattern(lDriver);
		//Pattern lPatternOption = new Pattern(lPatternList.get(0));
		//lPatternOption.plus();
		
		Pattern lPattern1 = new Pattern(lPatternList.get(1));
		lPattern1.plus();
		lPattern1.discountByNumber("60");
		lPattern1.editOption();
		Thread.sleep(2000);
		PosMenuOptionUtil.select(lDriver, "大盛り");
		Thread.sleep(2000);
		PosUtil.back(lDriver);
		Thread.sleep(3000);
		PosUtil.close(lDriver);
		PosUtil.handleConfirm(lDriver);
		PosUtil.sendOrder(lDriver);
		Thread.sleep(5000);
		lDriver.switchTo().alert().accept();
		if (PosUtil.hasAlert(lDriver))
		{
			lDriver.switchTo().alert().dismiss();
		}
		lDriver.switchTo().window(lPosWindow);
		
		PosUtil.checkOut(lDriver, lTable);
//		Thread.sleep(5000);
//		PosCheckUtil.handlePayExtraByCash(lDriver, 200);
//		
//		PosCheckUtil.handleCheckOut(lDriver);
//		Thread.sleep(5000);
//		lDriver.switchTo().alert().accept();
//		if (PosUtil.hasAlert(lDriver))
//		{
//			lDriver.switchTo().alert().dismiss();
//		}
//		lDriver.switchTo().window(lPosWindow);
//		
//		PosUtil.close(lDriver);
	}
	
}
