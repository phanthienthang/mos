package h2.ui.se.mo.table;

public class Table {
	
	String name;
	String floor;
	boolean isTempAvailable;
	boolean isImmediateCheckOut;
	boolean isActive;
	
	public Table(String iName){
		this.name = iName;
	}
	
	public Table(String iName, String iFloor)
	{
		this.name = iName;
		this.floor = iFloor;
	}
	
	public Table(String iName, String iFloor, boolean isAvailable, boolean isImmediateCheckout, boolean isActive){
		this.name = iName;
		this.floor = iFloor;
		this.isTempAvailable = isAvailable;
		this.isImmediateCheckOut = isImmediateCheckout;
		this.isActive = isActive;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the floor
	 */
	public String getFloor() {
		return floor;
	}
	/**
	 * 一時使用不可
	 * @return the isTempAvailable
	 */
	public boolean isTempAvailable() {
		return isTempAvailable;
	}
	/**
	 * 即時会計
	 * @return the isImmediateCheckOut
	 */
	public boolean isImmediateCheckOut() {
		return isImmediateCheckOut;
	}
	/**
	 * 有効フラグ
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}
	
	
	
	

}
