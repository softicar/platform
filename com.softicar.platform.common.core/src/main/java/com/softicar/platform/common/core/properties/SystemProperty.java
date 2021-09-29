package com.softicar.platform.common.core.properties;

import java.util.function.Function;

/**
 * An {@link IProperty} implementation for JVM system properties.
 *
 * @author Alexander Schmidt
 */
public class SystemProperty<T> extends AbstractProperty<T> {

	protected SystemProperty(PropertyName propertyName, T defaultValue, Function<String, T> parser) {

		super(propertyName, defaultValue, parser);
	}

	@Override
	protected String getValueString(PropertyName propertyName) {

		return System.getProperty(propertyName.toString());
	}
}
