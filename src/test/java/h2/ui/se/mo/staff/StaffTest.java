package h2.ui.se.mo.staff;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import h2.ui.se.mo.util.PosBrowser;
import h2.ui.se.mo.util.PosUtil;
import h2.ui.se.mo.util.PosUtil.BY;
import junit.framework.Assert;

public class StaffTest {
	
	WebDriver mDriver;
	
	@Before
	public void init() throws InterruptedException
	{
		mDriver = PosUtil.initLocal();
		Thread.sleep(2000);
	}

	//T-1-1-1-1
	@Test
	public void testAddStaff() throws InterruptedException 
	{
		Thread.sleep(5000);
		int beforeAddStaff = PosStaff.countStaffs(mDriver);
		
		//Add login salesforce
		PosBrowser.loginSalesforce(mDriver);
		String lPosWindow = new ArrayList<String> (mDriver.getWindowHandles()).get(0);
		 
		//Add Staff
		PosStaff.openStaffTab(mDriver);
		
		Thread.sleep(1000);
		PosStaff.addStaff(mDriver, new Staff("SS"));
		
		Thread.sleep(1000);
		mDriver.findElement(By.name("save")).click();
		
		Thread.sleep(2000);
		//Browser.closeTab(mDriver);
		mDriver.close();
		mDriver.switchTo().window(lPosWindow);
		
		mDriver.get(mDriver.getCurrentUrl());
		
		Thread.sleep(5000);
		int afterAddStaff = PosStaff.countStaffs(mDriver);
		
		Assert.assertEquals(beforeAddStaff+1, afterAddStaff);
	}
	
	//T-1-1-1-2
	@Test
	public void testAddStaffs() throws InterruptedException
	{
		Thread.sleep(5000);
		int lNoStaff = 3;
		int beforeAddStaff = PosStaff.countStaffs(mDriver);
		
		//Add login salesforce
		PosBrowser.loginSalesforce(mDriver);
		String lPosWindow = new ArrayList<String> (mDriver.getWindowHandles()).get(0);
		 
		// Add Staff
		PosStaff.openStaffTab(mDriver);
		
		Thread.sleep(1000);
		
		for (int i=0; i < lNoStaff; i++)
		{
			Thread.sleep(1000);			
			PosStaff.addStaff(mDriver, new Staff("SS"+i));
			
			Thread.sleep(1000);
			mDriver.findElement(By.name("save_new")).click();
			
			Thread.sleep(1000);
		}
		
		PosUtil.findnClick(mDriver, BY.NAME, "cancel");
		
		Thread.sleep(2000);
		//Browser.closeTab(mDriver);
		mDriver.close();
		mDriver.switchTo().window(lPosWindow);
		mDriver.get(mDriver.getCurrentUrl());
		
		PosUtil.isLoaded(mDriver);
		int afterAddStaff = PosStaff.countStaffs(mDriver);
		Assert.assertEquals(beforeAddStaff+lNoStaff, afterAddStaff);
	}
}
