package h2.ui.se.mo.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MOConfig {

	/**
	 * All non-UI messages go here. UI Labels go in Labels.
	 * 
	 * @author thienthang
	 *
	 */
	private static final String BUNDLE_NAME = "MOConfig";// $NON-NLS-0$

	private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private MOConfig() { }
	
	public static String getConfig(String key) 
	{
		if (RESOURCE_BUNDLE == null) 
		{
			RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
		}
		
		return getProperties(RESOURCE_BUNDLE, key, false, new Object[]{});
	}

	/**
	 * @param key
	 * @param args
	 * @return
	 */
	public static String getConfig(String key, Object... args) {
		return getProperties(RESOURCE_BUNDLE, key, false, args);
	}

	/**
	 * @param section
	 * @param key
	 * @param nullOk
	 * @param args
	 * @return
	 */
	public static String getConfig(String section, String key, boolean nullOk, Object... args) {
		return getConfig(section + "." + key, nullOk, args);
	}

	/**
	 * @param section
	 * @param key
	 * @param args
	 * @return
	 */
	public static String getConfig(String section, String key, Object... args) {
		return getConfig(section, key, false, args);
	}

	/**
	 * @param cls
	 * @param key
	 * @param args
	 * @return
	 */
	public static String getConfig(Class<?> cls, String key, Object... args) {
		return getConfig(cls, key, false, args);
	}

	/**
	 * @param cls
	 * @param key
	 * @param nullOk
	 * @param args
	 * @return
	 */
	public static String getConfig(Class<?> cls, String key, boolean nullOk, Object... args) {
		for (Class<?> currentClass = cls; currentClass != null; currentClass = currentClass.getSuperclass()) {
			final String msg = getConfig(currentClass.getSimpleName(), key, true, args);
			if (msg != null)
				return msg;
		}
		return getConfig(cls.getSimpleName(), key, nullOk, args);
	}

	/**
	 * @param key
	 * @param arg
	 * @return
	 */
	public static String getFormattedString(String key, Object arg) {
		return getConfig(key, arg);
	}

	/**
	 * @param key
	 * @param args
	 * @return
	 */
	public static String getFormattedString(String key, Object[] args) {
		return getConfig(key, args);
	}

	/**
	 * @param resourceBundle
	 * @param key
	 * @param nullOk
	 * @param args
	 * @return
	 */
	public static String getProperties(ResourceBundle resourceBundle, String key, boolean nullOk, Object... args) {
		assert key.contains(".");

		try {
			if (args == null) {
				return resourceBundle.getString(key);
			}
			for (int i = 0; i < args.length; i++) {
				if (args[i] == null) {
					args[i] = "";
				}
			}
			return MessageFormat.format(resourceBundle.getString(key), args);
		} catch (MissingResourceException e) {
			return nullOk ? null : '!' + key + '!';
		}
	}

}
