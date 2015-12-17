package h2.ui.se.mo.staff;

import java.util.Date;

public class Staff 
{
	public Staff(){}
	
	//スタッフ名 - Staff Name
	private String name;
	
	//ユーザID - User ID
	private String userId;
	
	//写真ID - Photo ID
	private String photoId;
	
	//説明 - Description
	private String description;
	
	//ユーザ 
	private String user;
	
	//会計担当 - Account
	private boolean isAccount;
	
	//注文担当 - Waiter
	private boolean isWaiter;
	
	//メール - Mail
	private String mail;
	
	//電話 - Phone
	private String phone;

	//携帯 - Mobile
	private String mobile;
	
	//誕生日	- Birthday
	private Date birthday;
	
	//郵便番号 - Postal Code
	private String postalcode;
	
	//住所 - Street Address
	private String street;
	
	public Staff(String iName) {
		this.name = iName;
	}
	
	/**
	 * @param iName
	 * @param iBirthDay
	 * @param iMail
	 * @param iPhone
	 * @param iMobile
	 * @param iPostalCode
	 * @param iStreet
	 * @param isAccount
	 * @param isWaiter
	 */
	public Staff(String iName, Date iBirthDay, String iMail, String iPhone, String iMobile, String iPostalCode, String iStreet, boolean isAccount, boolean isWaiter)
	{
		this.name = iName;
		this.isAccount = isAccount;
		this.isWaiter = isWaiter;
		this.mail = iMail;
		this.phone = iPhone;
		this.mobile = iMobile;
		this.birthday = iBirthDay;
		this.postalcode = iPostalCode;
		this.street = iStreet;
		
	}
	
	/**
	 * @param iName
	 * @param iUserId
	 * @param iPhotoId
	 * @param iDesc
	 * @param iUser
	 * @param isAccount
	 * @param isWaiter
	 * @param iMail
	 * @param iPhone
	 * @param iMobile
	 * @param iBirthDay
	 * @param iPostalCode
	 * @param iStreet
	 */
	public Staff(String iName, String iUserId, String iPhotoId, String iDesc, String iUser, boolean isAccount, boolean isWaiter, 
			String iMail, String iPhone, String iMobile, Date iBirthDay, String iPostalCode, String iStreet) 
	{
		this.name = iName;
		this.userId = iUserId;
		this.photoId = iPhotoId;
		this.user = iUser;
		this.isAccount = isAccount;
		this.isWaiter = isWaiter;
		this.mail = iMail;
		this.phone = iPhone;
		this.mobile = iMobile;
		this.birthday = iBirthDay;
		this.postalcode = iPostalCode;
		this.street = iStreet;
	}
	
	

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return the photoId
	 */
	public String getPhotoId() {
		return photoId;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @return the isAccount
	 */
	public boolean isAccount() {
		return isAccount;
	}

	/**
	 * @return the isWaiter
	 */
	public boolean isWaiter() {
		return isWaiter;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @return the postalcode
	 */
	public String getPostalcode() {
		return postalcode;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}
	
	

}
