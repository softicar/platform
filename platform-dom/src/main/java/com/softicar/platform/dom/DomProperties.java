package com.softicar.platform.dom;

import com.softicar.platform.common.core.properties.IProperty;
import com.softicar.platform.common.core.properties.SystemPropertyFactory;

/**
 * Defines system properties related to the DOM framework.
 *
 * @author Alexander Schmidt
 */
public class DomProperties {

	private static final SystemPropertyFactory FACTORY = new SystemPropertyFactory("com.softicar.platform.dom");

	/**
	 * Designates whether the DOM framework is in test mode.
	 */
	public static final IProperty<Boolean> TEST_MODE = FACTORY.createBooleanProperty("test.mode", false);
}
