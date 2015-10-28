package h2.ui.se.mo.floor;

public class Floor 
{
	private String name;
	private boolean isActive = true;
	private String guestConfig;
	private String defaultConfig;
	
	public Floor (String iName){
		this.name = iName;
	}
	
	public Floor (String iName, String iGuessConfig, String iDefaultConfig, boolean isActive) {
		this.name = iName;
		this.guestConfig = iGuessConfig;
		this.defaultConfig = iDefaultConfig;
		this.isActive = isActive;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}
	/**
	 * @return the guestConfig
	 */
	public String getGuestConfig() {
		return guestConfig;
	}
	/**
	 * @return the defaultConfig
	 */
	public String getDefaultConfig() {
		return defaultConfig;
	}

}
