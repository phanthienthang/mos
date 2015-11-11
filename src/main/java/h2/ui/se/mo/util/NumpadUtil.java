package h2.ui.se.mo.util;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NumpadUtil 
{
	static WebElement mEnter;
	static WebElement mClose;
	static WebElement mReset;
	static WebElement mTenThousand;
	static WebElement mCard;
	static WebElement mDoubleZero;
	static WebElement mMinus;
	
	public enum Card{Amex, JCB, Visa, MasterCard}
	
	/**
	 * Close pad number
	 * 
	 * @param iDriver
	 */
	public static void closeNumpad(WebDriver iDriver)
	{
		mClose.click();
	}
	
	/**
	 * Enter order amount with cash. The amount will automatically enter by selenium.
	 * 
	 * @param iDriver
	 * @param iAmount
	 */
	public static void handleInputNo(WebDriver iDriver, String iAmount)
	{
		List<WebElement> lNoElemList = parseTxtNo(iDriver, iAmount);
		for (int i = 0; i < lNoElemList.size(); i++) {
			lNoElemList.get(i).click();
		}
		
		mEnter.click();
	}
	
	/**
	 * Enter amount with specified card type which is chosen automatically.
	 * 
	 * @param iDriver
	 * @param iAmount
	 * @param iCardType
	 */
	public static void handleInputNo(WebDriver iDriver, String iAmount, Card iCardType) 
	{
		List<WebElement> lNoElemList = parseTxtNo(iDriver, iAmount);
		for (int i = 0; i < lNoElemList.size(); i++) 
		{
			lNoElemList.get(i).click();
		}
		
		mEnter.click();
		
	}
	
	/**
	 * Enter ten thousands automatically.
	 * 
	 * @param iDriver
	 */
	public static void handleTenThousand(WebDriver iDriver)
	{
		mTenThousand.click();
	}
	
	/**
	 * Pick the specified card
	 * 
	 * @param iDriver
	 * @param iType
	 */
	public static void handleChoseCard(WebDriver iDriver, Card iType)
	{
		mCard.click();
		List<WebElement> lCardList = iDriver.findElements(By.cssSelector("li[ng-repeat='publisherCompany in publisherCompanies']"));
		switch (iType) {
		case Amex:
			lCardList.get(0).click();
			break;
		case JCB:
			lCardList.get(0).click();
			break;
		case MasterCard:
			lCardList.get(0).click();
			break;
		case Visa:
			lCardList.get(0).click();
			break;
			
		}
	}
	
	/**
	 * @param iDriver
	 */
	public static void isNotPositive(WebDriver iDriver){
		mMinus.click();
	}
	
	/**
	 * @param iDriver
	 */
	public static void handleReset(WebDriver iDriver)
	{
		mReset.click();
	}
	
	
	/**
	 * @param iDriver
	 * @param iNoTxt
	 * @return
	 */
	private static List<WebElement> parseTxtNo(WebDriver iDriver, String iNoTxt) {
		
		char[] lNumbers = iNoTxt.trim().toCharArray();
		/*for (int i=0; i< lNumbers.length; i++) {
			System.out.println(lNumbers[i]);
		}*/
		return parseNumpadElement(iDriver, lNumbers);
	}
	
	/**
	 * Parse numpad element
	 * 
	 * @param iDriver
	 * @param iSeq
	 * @return
	 */
	private static List<WebElement> parseNumpadElement(WebDriver iDriver, char[] iSeq)
	{
		List<WebElement> lSeqList = new ArrayList<WebElement>();
		List<WebElement> lNumpadTRList = parseNumpad(iDriver);
		sequence: 
			for (int i = 0; i < iSeq.length; i++) 
			{
				for (int k = 0; k < lNumpadTRList.size(); k++)
				{
					List<WebElement> lNumpadTDList = lNumpadTRList.get(k).findElements(By.tagName("td"));
					for (int j = 0; j < lNumpadTDList.size(); j++) 
					{
						WebElement lALink = lNumpadTDList.get(j);
						
						if (lALink.getText().equalsIgnoreCase(String.valueOf(iSeq[i])))
						{
							//Comparison here
							lSeqList.add(lALink);
							continue sequence;
						}
					}
				}
			}
		
		return lSeqList;
	}
	
	/**
	 * @param iDriver
	 * @return
	 */
	private static List<WebElement> parseNumpad(WebDriver iDriver)
	{
		WebElement lNumpadTbl = iDriver.findElement(By.className("numpad"));
		WebElement lNumpadBody = lNumpadTbl.findElement(By.tagName("tbody"));
		List<WebElement> lNumpadTRList = lNumpadBody.findElements(By.tagName("tr"));
		initNumpad(lNumpadTRList);
		return lNumpadTRList;
	}
	
	/**
	 * @param iTRList
	 */
	private static void initNumpad(List<WebElement> iTRList) 
	{
		numpad:
		for (int k = 0; k < iTRList.size(); k++)
		{
			List<WebElement> lNumpadTDList = iTRList.get(k).findElements(By.tagName("td"));
			
			for (int j = 0; j < lNumpadTDList.size(); j++) 
			{
				if (k == 4 && j == 3)
				{
					mEnter =lNumpadTDList.get(j);
					continue numpad;
				}
				else if (k == 2 && j == 3) {
					mCard = lNumpadTDList.get(j);
					mTenThousand = lNumpadTDList.get(j);
					continue numpad;
				}
				else if (k == 1 && j == 3) {
					mMinus = lNumpadTDList.get(j);
				}
				else if (k == 1 && j == 0) {
					mClose = lNumpadTDList.get(j);
				}
				else if (k == 1 && j == 1) {
					mReset = lNumpadTDList.get(j);
				}
			}
		}
	}
	
}
