package h2.ui.se.mo.staff;

import java.text.SimpleDateFormat;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import h2.ui.se.mo.util.PosUtil;
import h2.ui.se.mo.util.PosUtil.BY;

public class PosStaff 
{
	public static int countStaffs(WebDriver iDriver) throws InterruptedException {

		PosUtil.findnClick(iDriver, BY.ID, "open");

		// Find all the web elements which have attribute ng-repeat
		Thread.sleep(2000);
		List<WebElement> lStaffList = iDriver.findElements(By.cssSelector("li[ng-repeat='staff in staffData.staffs']"));
		return lStaffList.size();
	}

	public static void openStaffTab(WebDriver iDriver) {
		
		PosUtil.findnClick(iDriver, BY.CLASS, "wt-StaffMaster");
		PosUtil.findnClick(iDriver, BY.NAME, "new");
	}

	public static void addStaff(WebDriver iDriver, Staff iStaff) {
		// スタッフ名 - Staff Name
		iDriver.findElement(By.id("Name")).sendKeys(iStaff.getName());

		// ユーザID - User ID
		if (iStaff.getUserId() != null)
			iDriver.findElement(By.id("00N28000007Hr7G")).sendKeys(iStaff.getUserId());

		// 写真ID - Photo ID
		if (iStaff.getPhotoId() != null)
			iDriver.findElement(By.id("00N28000007Hr7C")).sendKeys(iStaff.getPhotoId());

		// 説明 - Description
		if (iStaff.getDescription() != null)
			iDriver.findElement(By.id("00N28000007Hr76")).sendKeys(iStaff.getDescription());

		// ユーザ - User
		if (iStaff.getUser() != null)
			iDriver.findElement(By.id("CF00N28000007Hr7H")).sendKeys(iStaff.getUser());

		// 会計担当 - Account
		if (iStaff.isAccount()) {
			iDriver.findElement(By.id("00N28000007Hr78")).click();
		}

		// 注文担当 - Waiter
		if (iStaff.isWaiter()) {
			iDriver.findElement(By.id("00N28000007Hr79")).click();
		}

		// メール - Mail
		if (iStaff.getMail() != null) {
			iDriver.findElement(By.id("00N28000007Hr77")).sendKeys(iStaff.getMail());
		}

		// 電話 - Phone
		if (iStaff.getPhone() != null) {
			iDriver.findElement(By.id("00N28000007Hr7B")).sendKeys(iStaff.getPhone());
		}

		// 携帯 - Mobile
		if (iStaff.getMobile() != null) {
			iDriver.findElement(By.id("00N28000007Hr7A")).sendKeys(iStaff.getMobile());
		}

		// 誕生日 - Birthday
		if (iStaff.getBirthday() != null) {
			iDriver.findElement(By.id("00N28000007Hr75")).sendKeys(new SimpleDateFormat("yyyy/mm/dd").format(iStaff.getBirthday()));
		}

		// 郵便番号 - Postal Code
		if (iStaff.getPostalcode() != null) {
			iDriver.findElement(By.id("00N28000007Hr7F")).sendKeys(iStaff.getPostalcode());
		}

		// 住所 - Street Address
		if (iStaff.getStreet() != null) {
			iDriver.findElement(By.id("00N28000007Hr74")).sendKeys(iStaff.getStreet());
		}
	}
}
