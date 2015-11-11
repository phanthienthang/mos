package h2.ui.se.mo.staff;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import h2.ui.se.mo.util.Browser;
import h2.ui.se.mo.util.PosUtil;
import junit.framework.Assert;

public class StaffTest {
	
	WebDriver mDriver;
	
	@Before
	public void init() throws InterruptedException
	{
		mDriver = PosUtil.init();
		Thread.sleep(2000);
	}

	@Test
	public void testAddStaff() throws InterruptedException 
	{
		Thread.sleep(5000);
		int beforeAddStaff = countStaffs();
		
		//Add login salesforce
		Browser.loginSalesforce(mDriver);
		String lPosWindow = new ArrayList<String> (mDriver.getWindowHandles()).get(0);
		 
		//Add Staff
		openStaffTab();
		
		Thread.sleep(1000);
		addStaff(new Staff("Sem-Staff"));
		
		Thread.sleep(1000);
		mDriver.findElement(By.name("save")).click();
		
		Thread.sleep(2000);
		//Browser.closeTab(mDriver);
		mDriver.close();
		mDriver.switchTo().window(lPosWindow);
		
		mDriver.get(mDriver.getCurrentUrl());
		
		Thread.sleep(5000);
		int afterAddStaff = countStaffs();
		
		Assert.assertEquals(beforeAddStaff+1, afterAddStaff);
	}
	
	@Test
	public void testAddStaffs() throws InterruptedException
	{
		Thread.sleep(5000);
		int lNoStaff = 10;
		int beforeAddStaff = countStaffs();
		
		//Add login salesforce
		Browser.loginSalesforce(mDriver);
		String lPosWindow = new ArrayList<String> (mDriver.getWindowHandles()).get(0);
		 
		// Add Staff
		openStaffTab();
		
		Thread.sleep(1000);
		
		for (int i=0; i < lNoStaff; i++)
		{
			Thread.sleep(1000);
			
			addStaff(new Staff("Sem-Staff"+i));
			
			Thread.sleep(1000);
			mDriver.findElement(By.name("save_new")).click();
			
			Thread.sleep(1000);
		}
		
		WebDriverWait lWait = new WebDriverWait(mDriver, 10);
		lWait.until(ExpectedConditions.elementToBeClickable(By.name("cancel")));
		mDriver.findElement(By.name("cancel")).click();
		
		Thread.sleep(2000);
		//Browser.closeTab(mDriver);
		mDriver.close();
		mDriver.switchTo().window(lPosWindow);
		mDriver.get(mDriver.getCurrentUrl());
		
		Thread.sleep(5000);
		int afterAddStaff = countStaffs();
		Assert.assertEquals(beforeAddStaff+lNoStaff, afterAddStaff);
	}
	
	protected int countStaffs() throws InterruptedException {
		
		WebDriverWait lDriverWait = new WebDriverWait(mDriver,10);
		lDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("open")));
		mDriver.findElement(By.id("open")).click();
		
		//Find all the web elements which have attribute ng-repeat
		Thread.sleep(1000);
		List<WebElement> lStaffList = mDriver.findElements(By.cssSelector("li[ng-repeat='staff in staffData.staffs']"));
		return lStaffList.size();
	}
	
	
	protected void openStaffTab() 
	{
		WebDriverWait lDriverWait = new WebDriverWait(mDriver,10);
		lDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.className("wt-StaffMaster")));
		mDriver.findElement(By.className("wt-StaffMaster")).click();
		
		lDriverWait = new WebDriverWait(mDriver,2);
		lDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.name("new")));
		mDriver.findElement(By.name("new")).click();
	}
	
	protected void countStaff() {
		mDriver.findElements(By.className(""));
	}
	
	protected void addStaff(Staff iStaff) 
	{
		//スタッフ名 - Staff Name
		mDriver.findElement(By.id("Name")).sendKeys(iStaff.getName());
		
		//ユーザID - User ID
		if (iStaff.getUserId() != null)
			mDriver.findElement(By.id("00N28000007Hr7G")).sendKeys(iStaff.getUserId());
		
		//写真ID - Photo ID
		if (iStaff.getPhotoId() != null)
			mDriver.findElement(By.id("00N28000007Hr7C")).sendKeys(iStaff.getPhotoId());
		
		//説明 - Description
		if (iStaff.getDescription() != null)
			mDriver.findElement(By.id("00N28000007Hr76")).sendKeys(iStaff.getDescription());
		
		//ユーザ - User
		if (iStaff.getUser() != null)
			mDriver.findElement(By.id("CF00N28000007Hr7H")).sendKeys(iStaff.getUser());
		
		//会計担当 - Account
		if (iStaff.isAccount()){
			mDriver.findElement(By.id("00N28000007Hr78")).click();
		}
		
		//注文担当 - Waiter
		if (iStaff.isWaiter()){
			mDriver.findElement(By.id("00N28000007Hr79")).click();
		}
		
		//メール - Mail
		if (iStaff.getMail() != null)
		{
			mDriver.findElement(By.id("00N28000007Hr77")).sendKeys(iStaff.getMail());
		}
		
		//電話 - Phone
		if (iStaff.getPhone() != null)
		{
			mDriver.findElement(By.id("00N28000007Hr7B")).sendKeys(iStaff.getPhone());
		}

		//携帯 - Mobile
		if (iStaff.getMobile() != null)
		{
			mDriver.findElement(By.id("00N28000007Hr7A")).sendKeys(iStaff.getMobile());
		}
		
		//誕生日	- Birthday
		if (iStaff.getBirthday() != null)
		{
			mDriver.findElement(By.id("00N28000007Hr75")).sendKeys(new SimpleDateFormat("yyyy/mm/dd").format(iStaff.getBirthday()));
		}
		
		//郵便番号 - Postal Code
		if (iStaff.getPostalcode() != null)
		{
			mDriver.findElement(By.id("00N28000007Hr7F")).sendKeys(iStaff.getPostalcode());
		}
		
		//住所 - Street Address
		if (iStaff.getStreet() != null)
		{
			mDriver.findElement(By.id("00N28000007Hr74")).sendKeys(iStaff.getStreet());
		}
	}
	
	
}
