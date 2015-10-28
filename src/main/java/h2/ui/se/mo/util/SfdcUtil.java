/**
 * 
 */
package h2.ui.se.mo.util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import h2.ui.se.mo.floor.Floor;
import h2.ui.se.mo.table.Table;

/**
 * @author thienthang
 *
 */
public class SfdcUtil {

	
	public static void openTab(WebDriver iDriver, String iTabName)
	{
		WebDriverWait lDriverWait = new WebDriverWait(iDriver,10);
		lDriverWait.until(ExpectedConditions.elementToBeClickable(By.linkText(iTabName)));
		iDriver.findElement(By.linkText(iTabName)).click();
	}
	
	public static void addFloor(WebDriver iDriver, Floor iFloor)
	{
		
		iDriver.findElement(By.id("Name")).sendKeys(iFloor.getName());
		
		if (iFloor.getGuestConfig() != null)
		{
			iDriver.findElement(By.id("CF00N28000006kXDi")).sendKeys(iFloor.getGuestConfig());
		}
		
		if (iFloor.getDefaultConfig() != null) {
			iDriver.findElement(By.id("CF00N28000006kXDj")).sendKeys(iFloor.getDefaultConfig());
		}
		
		if (!iFloor.isActive())
		{
			iDriver.findElement(By.id("00N28000006kXDk")).click();
		}
	}
	
	public static void addTable(WebDriver iDriver, Table iTable) 
	{
		//テーブル名
		iDriver.findElement(By.id("Name")).sendKeys(iTable.getName());
		
		//フロア
		iDriver.findElement(By.id("CF00N28000006kXCA")).sendKeys(iTable.getFloor());
		
		//一時使用不可
		if (iTable.isTempAvailable())
		{
			iDriver.findElement(By.id("00N28000006kXHA")).click();
		}
		
		//即時会計
		if (iTable.isImmediateCheckOut())
		{
			iDriver.findElement(By.id("00N28000006kXH9")).click();
		}
		
		//有効フラグ
		if (iTable.isActive())
		{
			iDriver.findElement(By.id("00N28000006kXH8")).click();
		}
		
	}
	
	/**
	 * Go next
	 * @param iDriver
	 */
	public static void next(WebDriver iDriver)
	{
		iDriver.findElement(By.linkText("Next"));
	}
	
	/**
	 * Go previous
	 * @param iDriver
	 */
	public static void previous(WebDriver iDriver)
	{
		iDriver.findElement(By.linkText("Previous"));
	}
	
	/**
	 * Search the option
	 * 
	 * @param iDriver
	 * @param iObject
	 * @param iSearchText
	 * @throws InterruptedException 
	 */
	public static void search(WebDriver iDriver, String iObject, String iSearchText) throws InterruptedException 
	{
		WebDriverWait lWait = new WebDriverWait(iDriver, 10);
		lWait.until(ExpectedConditions.elementToBeClickable(By.id("sen")));
		WebElement lSenElem = iDriver.findElement(By.id("sen"));
		lSenElem.click();
		List<WebElement> lObjectList = iDriver.findElements(By.xpath(".//option"));
		
		for (WebElement lElem: lObjectList) {
			if (lElem.getText().matches(iObject)){ 
				lElem.click();
				break;
			}
		}
		
		Thread.sleep(2000);
		iDriver.findElement(By.id("sbstr")).sendKeys(iSearchText);
		
		iDriver.findElement(By.name("search")).click();
	}
	
	public static void editSearchResult(WebDriver iDriver) throws InterruptedException
	{
		Thread.sleep(2000);
		
		//lWait.until(ExpectedConditions.visibilityOfAllElements(iDriver.findElements(By.linkText("Edit"))));
		List<WebElement> lElems = iDriver.findElements(By.linkText("Edit"));
		int lRandom = PosUtil.random(lElems.size());
		
		//System.out.println("Random Floor: "+lElems.get(lRandomFloor).getText());
		lElems.get(lRandom).click();
	}
	
	public static void openRecord(WebDriver iDriver, String iLink)
	{
		iDriver.findElement(By.linkText(iLink)).click();
		
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		WebDriver lDriver = PosUtil.initChromeDriver();
		Browser.loginSalesforce(lDriver);
		
		
		//Search option
		search(lDriver, "オプションマスタ", "大盛り");
		
		//オプションマスタ
		//カテゴリマスタ
		//サービス時間帯マスタ
		//スタッフマスタ
		//テーブルマスタ
		//ドロアログ
		//フロアマスタ
		//メニューマスタ
		//レシートプリンタマスタ
		//レシート設定
		//レジレポート
		//会計
		
		//業務日報
		//支払い方法マスタ
		
		//食材マスタ
		
		//発行元マスタ
		
	}
	
	
}
