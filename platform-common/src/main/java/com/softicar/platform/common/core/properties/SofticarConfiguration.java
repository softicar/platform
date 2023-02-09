package com.softicar.platform.common.core.properties;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Represents the configuration file of SoftiCAR.
 *
 * @author Oliver Richers
 */
public class SofticarConfiguration implements IPropertyMap {

	private static final String CONFIGURATION_FILENAME = "config.properties";
	private static final SofticarConfiguration SINGLETON = new SofticarConfiguration();
	private volatile Properties properties;

	private SofticarConfiguration() {

		this.properties = loadProperties();
	}

	/**
	 * Reloads all properties.
	 */
	public synchronized void reload() {

		this.properties = loadProperties();
	}

	public static SofticarConfiguration getSingleton() {

		return SINGLETON;
	}

	/**
	 * Creates a new instance of {@link PropertyFactory} using this
	 * {@link SofticarConfiguration} as {@link IPropertyMap}.
	 *
	 * @return the new property factory, never null
	 */
	public static PropertyFactory createPropertyFactory(String prefix) {

		return new PropertyFactory(SINGLETON, prefix);
	}

	/**
	 * Returns the value of the given property.
	 * <p>
	 * This method checks the SoftiCAR configuration file for the given property
	 * and returns it when found. Otherwise, null is returned.
	 *
	 * @param propertyName
	 *            the name of the property to return
	 * @return the value from the configuration file, or null
	 */
	@Override
	public String getValueString(PropertyName propertyName) {

		return properties.getProperty(propertyName.toString());
	}

	private static Properties loadProperties() {

		File configFile = getConfigFile();
		if (configFile.exists()) {
			try (FileInputStream inputStream = new FileInputStream(configFile)) {
				Properties properties = new Properties();
				properties.load(inputStream);
				return properties;
			} catch (IOException exception) {
				throw new SofticarIOException(exception);
			}
		} else {
			return new Properties();
		}
	}

	private static File getConfigFile() {

		return SofticarHome.getPath().resolve(CONFIGURATION_FILENAME).toFile();
	}
}
