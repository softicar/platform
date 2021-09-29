package com.softicar.platform.common.core.properties;

/**
 * This class enumerates all valid system properties.
 * <p>
 * These are the standard properties returned by {@link System#getProperties()}.
 * 
 * @author Oliver Richers
 */
public enum SystemPropertyEnum {

	FILE_SEPARATOR("file.separator"),
	JAVA_CLASS_PATH("java.class.path"),
	JAVA_HOME("java.home"),
	JAVA_VENDOR("java.vendor"),
	JAVA_VENDOR_URL("java.vendor.url"),
	JAVA_VERSION("java.version"),
	LINE_SEPARATOR("line.separator"),
	OPERATING_SYSTEM_ARCHITECTURE("os.arch"),
	OPERATING_SYSTEM_NAME("os.name"),
	OPERATING_SYSTEM_VERSION("os.version"),
	USER_DIR("user.dir"),
	USER_HOME("user.home"),
	USER_NAME("user.name");

	private final String name;

	private SystemPropertyEnum(String name) {

		this.name = name;
	}

	/**
	 * Returns the internal name of this property enum.
	 * <p>
	 * The returned name is the actual name of the system property represented
	 * by this enum constant.
	 * 
	 * @return the property name
	 */
	public String getName() {

		return name;
	}

	/**
	 * Returns the value of this property.
	 * 
	 * @return the value as string
	 */
	public String get() {

		return System.getProperty(name);
	}
}
