package h2.ui.se.mo.log;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import h2.ui.se.mo.util.PosLog;
import h2.ui.se.mo.util.PosUtil;
import h2.ui.se.mo.util.PosUtil.BY;

public class LogTest 
{
	WebDriver mDriver;
	
	@Before
	public void init() throws InterruptedException
	{
		mDriver = PosUtil.initLocal();
		Thread.sleep(10000);
	}
	
	@Test
	public void testT1511() throws InterruptedException
	{
		PosUtil.openSetting(mDriver, "入出金");
		
		PosLog.reason(mDriver, "入金");
		
		PosLog.cash10kIn(mDriver, "5000");
		
		PosLog.cash10kOut(mDriver, "1000");
		
		PosLog.comment(mDriver, "In 5000, out 1000");
		
		PosUtil.findnClick(mDriver, BY.LINKTEXT, "解錠");
		String lPosWindow = new ArrayList<String> (mDriver.getWindowHandles()).get(0);
		if (PosUtil.hasAlert(mDriver))
		{
			mDriver.switchTo().alert().dismiss();
		}
		mDriver.switchTo().window(lPosWindow);
		if (PosUtil.hasAlert(mDriver))
		{
			mDriver.switchTo().alert().accept();
		}
		mDriver.switchTo().window(lPosWindow);
		
	}

}
