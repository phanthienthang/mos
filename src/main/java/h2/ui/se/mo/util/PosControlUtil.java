package h2.ui.se.mo.util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import h2.ui.se.mo.util.PosUtil.BY;

public class PosControlUtil {
	
	//Select timeslot
	private static void setTimeSlot(WebDriver iDriver, String iTimeSlot)
	{
		//id - timeSlot
		WebElement lTimeSlot = iDriver.findElement(By.id("timeSlot"));
		List<WebElement> lSlot = lTimeSlot.findElements(By.xpath(".//option"));
		for (WebElement webElement : lSlot) {
			if (webElement.getText().contains(iTimeSlot)){
				webElement.click();
				break;
			}
		}
		
	}
	
	public static void openBusiness(WebDriver iDriver, String iTimeSlot)
	{
		setTimeSlot(iDriver, iTimeSlot);
		PosUtil.findnClick(iDriver, BY.LINKTEXT, "オープン業務");
		PosUtil.handleAlert(iDriver);
	}
	
	public static void closeBusniness(WebDriver iDriver) {
		try {
			PosUtil.findnClick(iDriver, BY.LINKTEXT, "本締め業務");
		} catch (WebDriverException e) {
			PosUtil.findnClick(iDriver, BY.LINKTEXT, "作業中の締め業務に戻る");
		}
		
	}
	
	public static void openReportHistory(WebDriver iDriver)
	{
		//過去の業務履歴
		
	}
	
	public static void closeReport(WebDriver iDriver){
		//仮締め業務
	}
	
	public static void saveReport(WebDriver iDriver)
	{
		//完了
		PosUtil.findnClick(iDriver, BY.LINKTEXT, "完了");
	}
	
	public static void back(WebDriver iDriver){
		PosUtil.findnClick(iDriver, BY.LINKTEXT, "閉じる");
	}
	
	public static void returnReport(WebDriver iDriver) {}
	
	public static void vietReport(WebDriver iDriver)
	{
		
	}
	
}
