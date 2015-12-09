package h2.ui.se.mo.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import h2.ui.se.mo.util.PosUtil.BY;

public class PosOrderHistoryUtil {
	//Format date DateTimeFormatter.
	public static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	
	//Format date SimpleDateFormat
	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
	
	/**
	 * Search checks by input information check number. 
	 * Automatically input the specified check number and looking for it with the result on the right POS screen.
	 * 
	 * @param iDriver
	 * @param iCheck
	 */
	public static void searchCheck(WebDriver iDriver, String iCheck)
	{
		PosUtil.findnClick(iDriver, BY.ID, "search");
		
		WebElement lSearchForm = iDriver.findElement(By.id("searchform"));
		WebElement lSearchText = lSearchForm.findElement(By.xpath(".//input"));
		
		if (lSearchText.isDisplayed())
		{
			lSearchText.sendKeys(iCheck);
			lSearchText.sendKeys(Keys.RETURN);
		}
	}
	
	/**
	 * Filter the checks by specified day. Automatically select the entered day in test code. 
	 * 
	 * @param iDriver
	 * @param iDate
	 */
	public static void filterByDay(WebDriver iDriver, String iDate)
	{
		handlePickDate(iDriver, iDate);
	}
	
	/**
	 * @param iDriver
	 * @return
	 */
	public static String selectRdmCheck(WebDriver iDriver)
	{
		List<WebElement> lCheckList = PosUtil.findElements(iDriver, BY.CSS, "li[ng-repeat='check in history.checks']");
		WebElement lCheck = lCheckList.get(Util.random(lCheckList.size()));
		lCheck.click();
		return lCheck.getText();
	}
	
	/**
	 * @param iDriver
	 * @param iCheck
	 */
	public static void viewCheck(WebDriver iDriver, String iCheck)
	{
		List<WebElement> lCheckList = PosUtil.findElements(iDriver, BY.CSS, "li[ng-repeat='check in history.checks']");
		for (WebElement webElement : lCheckList) {
			if (webElement.getText().equals(iCheck)) {
				webElement.click();
			}
		}
	}
	
	
	/**
	 * Handle pick specified date.
	 * 
	 * @param iDriver
	 * @param iDate
	 */
	private static void handlePickDate(WebDriver iDriver, String iDate){
		
		LocalDate localDate = LocalDate.parse(iDate, dateFormat);
		localDate = localDate.minusDays(1);
		String lDay = localDate.getDayOfWeek().toString().substring(0, 3);
		String lMonth = localDate.getMonth().toString();
		String lYear = String.valueOf(localDate.getYear());
		System.out.println("Year: "+ lYear + " Month: "+lMonth + " Date: " +lDay);
		
		//WebElement lDateElem = findDate(iDriver, localDate.getDayOfMonth());
		selectDate(iDriver, localDate.getDayOfMonth());
		
	}
	
	/**
	 * Select specified date on POS screen automatically which is entered in test code.
	 * 
	 * @param iDriver
	 * @param iDayInMonth
	 * @return
	 */
	private static WebElement selectDate(WebDriver iDriver, int iDayInMonth)
	{
		WebElement lCalendarPanel = iDriver.findElement(By.className("day-picker"));
		
		WebElement lCalendarContent = lCalendarPanel.findElement(By.className("picker-contents"));
		
		List<WebElement> lTableList = lCalendarContent.findElements(By.xpath(".//table"));
		
		if (lTableList.size() > 1) 
		{
			WebElement lTbody = lTableList.get(1).findElement(By.xpath(".//tbody"));
			List<WebElement> lTdList = lTbody.findElements(By.xpath(".//td"));
			for (WebElement webElement : lTdList) 
			{
				if(webElement.getText().equals(String.valueOf(iDayInMonth))) 
				{
					webElement.click();
					return webElement;
				}
			}
		}
		
		return null;
	}
	
	//Get yesterday.
	private static void getPreDay()
	{
		LocalDate lToday = LocalDate.now();
		LocalDate lPreviousDate = LocalDate.of(lToday.getYear(), lToday.getMonth(), lToday.getDayOfMonth()-1);
	}
	
	//Get tomorrow
	private static void getNextDay() 
	{
		LocalDate lToday = LocalDate.now();
		LocalDate lPreviousDate = LocalDate.of(lToday.getYear(), lToday.getMonth(), lToday.getDayOfMonth()+1);
	}
	
	//Get next month from the current month on POS screen
	private static void getNextMonth(WebDriver iDriver)
	{
		WebElement lNext = iDriver.findElement(By.xpath("//a[@hm-tap='addMonth(1)'"));
		lNext.click();
	}
	
	//Get previous month from the current month on POS screen
	private static void getPreMonth(WebDriver iDriver)
	{
		WebElement lPrev = iDriver.findElement(By.xpath("//a[@hm-tap='addMonth(-1)'"));
		lPrev.click();
	}
	
	private static void handlePickMonth(){}
	
	private static void handleSearch(){}
	
	private static void handleFilter(){}
	
}
