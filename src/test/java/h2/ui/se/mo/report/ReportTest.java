package h2.ui.se.mo.report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import h2.ui.se.mo.util.PosCheckUtil;
import h2.ui.se.mo.util.PosControlUtil;
import h2.ui.se.mo.util.PosRegisterReportUtil;
import h2.ui.se.mo.util.PosServiceUtil;
import h2.ui.se.mo.util.PosUtil;
import h2.ui.se.mo.util.PosUtil.BY;

public class ReportTest {
	
	
	WebDriver mDriver;
	
	//@Before
	public void init() throws InterruptedException
	{
		mDriver = PosUtil.initLocal();
		//PosUtil.init();
		Thread.sleep(20000);
	}
	
	//@Test
	public void testAll() throws InterruptedException 
	{
		testT1611();
		testT1612();
		testT1613();
		testT1614();
		testT1615();
		testT1616();
	}
	
	//オープン業務
	//@Test
	public void testT1611() throws InterruptedException
	{
		init();
		
		//1. Go to POS管理 - ja
		PosUtil.openSetting(mDriver, "POS管理");
		
		Thread.sleep(5000);
		//2. Choose a 業務区分: ランチ
		PosControlUtil.openBusiness(mDriver, "ランチ");
	}
	
	//締め確認中
	//@Test 
	public void testT1612() throws InterruptedException
	{
		//仮締め業務
		//1. Go to POS管理 - ja
		//PosUtil.openSetting(mDriver, "POS管理");
		
		//2. Choose a 業務区分: ランチ
		//PosControlUtil.openBusiness(mDriver, "ランチ");
		
		Thread.sleep(5000);
		PosUtil.findnClick(mDriver, BY.LINKTEXT, "仮締め業務");
		
		PosUtil.handleAlert(mDriver);
		
		Thread.sleep(2000);
		PosUtil.back(mDriver);
		
	}
	
	//締め確認中
	//@Test
	public void testT1613() throws InterruptedException 
	{
		//1. Go to POS管理 - ja
		//PosUtil.openSetting(mDriver, "POS管理");
		
		//2. Choose a 業務区分: ランチ
		//PosControlUtil.openBusiness(mDriver, "ランチ");
		
		//Thread.sleep(5000);
		//PosUtil.findnClick(mDriver, BY.LINKTEXT, "仮締め業務");
		
		//Thread.sleep(2000);
		//PosUtil.back(mDriver);
		
		Thread.sleep(3000);
		PosUtil.findnClick(mDriver, BY.LINKTEXT, "作業中の締め業務に戻る");
		
		//PosControlUtil.handleAlert(mDriver);
		
		Thread.sleep(2000);
		PosControlUtil.saveReport(mDriver);
	}
	
	//@Test
	public void testT1614() throws InterruptedException 
	{
		Thread.sleep(3000);
		PosUtil.findnClick(mDriver, BY.LINKTEXT, "本日の業務");
	}
	
	//@Test
	public void testT1615() throws InterruptedException 
	{
		Thread.sleep(3000);
		PosUtil.findnClick(mDriver, BY.LINKTEXT, "過去の業務履歴");
	}
	
	//@Test
	public void testT1616() throws InterruptedException 
	{
		init();
		
		//1. Go to POS管理 - ja
		PosUtil.openSetting(mDriver, "POS管理");
		
		try {
			//2. Choose a 業務区分: ディナー
			PosControlUtil.openBusiness(mDriver, "ディナー");
		}
		catch (NoSuchElementException e) {
			System.out.println("Another business is opening");
		}
		
		//1. Go to POS管理 - ja
		PosUtil.openSetting(mDriver, "注文");
		
		String lTable = PosUtil.orderRandom(mDriver);
		
		PosUtil.sendOrder(mDriver);
		
		//Need a common method for alert cancellation.
		String lPosWindow = new ArrayList<String> (mDriver.getWindowHandles()).get(0);
		handleAlert(mDriver);
		mDriver.switchTo().window(lPosWindow);
		
		PosUtil.checkOut(mDriver, lTable);
		
		PosCheckUtil.payOrder(mDriver);
		
		PosCheckUtil.handleCheckOut(mDriver);
		
		handleAlert(mDriver);
		mDriver.switchTo().window(lPosWindow);
		
		PosControlUtil.back(mDriver);
		
		PosUtil.openSetting(mDriver, "POS管理");
		
		Thread.sleep(5000);
		PosControlUtil.closeBusniness(mDriver);
		//PosControlUtil.handleAlert(mDriver);
		
		Thread.sleep(5000);
		PosRegisterReportUtil.fillRegisterOrder(mDriver);
		PosControlUtil.saveReport(mDriver);
		PosUtil.back(mDriver);
	}

	private void handleAlert(WebDriver iDriver)
	{
		try {
			iDriver.switchTo().alert().dismiss();
			if (PosUtil.hasAlert(iDriver)) {
				iDriver.switchTo().alert().dismiss();
			}
		}
		catch (NoAlertPresentException e) {
			handleAlert(iDriver);
		}
	}
	
	@Test
	public void testT1619() throws IOException, InterruptedException
	{
		init();
		PosUtil.openSetting(mDriver, "POS管理");
		
		PosUtil.findnClick(mDriver, BY.LINKTEXT, "過去の業務履歴");
		vieReportHistory(mDriver);
		
		Thread.sleep(5000);
		PosUtil.screenshot(mDriver, "T1619.png");
	}
	
	private static void vieReportHistory(WebDriver iDriver) 
	{
		System.out.println("Call method viewReportHistory(WebDriver iDriver)");
		try {
			List<WebElement> lReportHistoryList = iDriver.findElements(By.cssSelector("li[ng-repeat='report in histories']"));
			if (lReportHistoryList.size() != 0) {
				lReportHistoryList.get(PosUtil.random(lReportHistoryList.size())).click();
			}
		} catch (WebDriverException e) {
			vieReportHistory(iDriver);
		}
	}
	
	
	
	

}
