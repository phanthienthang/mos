package h2.ui.se.mo.menu.option.pattern;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PosPattern 
{
	
	private WebElement mWebElement;
	
	/**
	 * Constructor
	 * 
	 * @param iWebElement
	 */
	public PosPattern(WebElement iWebElement)
	{
		mWebElement = iWebElement;
	}
	
	
	/**
	 * Minus one
	 */
	public void minus()
	{
		handleAction("minus");
	}
	
	/**
	 * Plus one
	 */
	public void plus()
	{
		handleAction("plus");
	}
	
	/**
	 * Common handle action used for generous actions like minus or plus
	 * @param iAction
	 */
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
	
	/**
	 * Fill the comment to pattern.
	 * 
	 * @param iComment
	 */
	public void comment(String iComment)
	{
		mWebElement.findElement(By.linkText("コメント入力")).click();
		mWebElement.findElement(By.xpath(".//textarea")).sendKeys(iComment);
	}
	
	/**
	 * Handle discount by rate
	 * 
	 * @param iPercent
	 */
	public void discountRate(String iPercent)
	{
		mWebElement.findElement(By.cssSelector("input[ng-model='pattern.discountRate']")).sendKeys(iPercent);
	}
	
	/**
	 * Handle discount by price
	 * 
	 * @param iAmount
	 */
	public void priorityPrice(String iAmount)
	{
		mWebElement.findElement(By.cssSelector("input[ng-model='pattern.discountPrice']")).sendKeys(iAmount);
	}
	
	/**
	 * Handle discount by specified number
	 * 
	 * @param iPriority
	 */
	public void discountByNumber(String iPriority)
	{
		mWebElement.findElement(By.cssSelector("input[ng-model='pattern.priorityPrice")).sendKeys(iPriority);
	}


	/**
	 * Handle edit option
	 */
	public void editOption() {
		mWebElement.findElement(By.xpath(".//a[@id='add']")).click();
	}

}
