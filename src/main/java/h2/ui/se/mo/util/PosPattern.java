package h2.ui.se.mo.util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PosPattern 
{
	
	private WebElement mWebElement;
	
	public PosPattern(WebElement iWebElement)
	{
		mWebElement = iWebElement;
	}
	
	
	public void minus()
	{
		handleAction("minus");
	}
	
	public void plus()
	{
		handleAction("plus");
	}
	
	private void handleAction(String iAction) {
		WebElement lPanel = mWebElement.findElement(By.xpath(".//div[@class='row']"));
		List<WebElement> lAElements = lPanel.findElements(By.xpath(".//a"));
		for (WebElement lElem: lAElements) {
			if (lElem.getAttribute("class").contains(iAction)) {
				lElem.click();
				break;
			}
		}
	}
	
	public void comment(String iComment)
	{
		mWebElement.findElement(By.linkText("コメント入力")).click();
		mWebElement.findElement(By.xpath(".//textarea")).sendKeys(iComment);
	}
	
	public void discountRate(String iPercent)
	{
		mWebElement.findElement(By.cssSelector("input[ng-model='pattern.discountRate']")).sendKeys(iPercent);
	}
	
	public void priorityPrice(String iAmount)
	{
		mWebElement.findElement(By.cssSelector("input[ng-model='pattern.discountPrice']")).sendKeys(iAmount);
	}
	
	public void discountByNumber(String iPriority)
	{
		mWebElement.findElement(By.cssSelector("input[ng-model='pattern.priorityPrice")).sendKeys(iPriority);
	}


	public void editOption() {
		mWebElement.findElement(By.xpath(".//a[@id='add']")).click();
	}

}
