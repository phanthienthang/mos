package h2.ui.se.mo.util;

import org.openqa.selenium.WebDriver;

import h2.ui.se.mo.util.PosUtil.BY;

public class PosService
{
	public static void setService(WebDriver iDriver, String iService)
	{
		if (iService != null)
			PosUtil.findnClick(iDriver, BY.LINKTEXT, iService);
		else
			PosUtil.findnClick(iDriver, BY.LINKTEXT, "<未選択>");
			
	}
	
	public static void lunch(WebDriver iDriver){
		
		PosUtil.findnClick(iDriver, BY.LINKTEXT, "ランチ");
	}
	
	public static void dinner(WebDriver iDriver) {
		PosUtil.findnClick(iDriver, BY.LINKTEXT, "ディナー");
	}

}
