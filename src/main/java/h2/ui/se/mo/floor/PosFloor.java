/**
 * 
 */
package h2.ui.se.mo.floor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author thienthang
 *
 */
public class PosFloor {
	
	/**
	 * @param iDriver
	 * @param iFloor
	 */
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

}
