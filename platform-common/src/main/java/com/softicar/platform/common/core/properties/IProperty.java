package com.softicar.platform.common.core.properties;

import java.util.Properties;

/**
 * A generic interface for properties.
 * <p>
 * A property is a configuration value usually defined in a {@link Properties}
 * file. Every property has a name and a value of various type.
 *
 * @param <T>
 *            the type of the property value
 * @author Oliver Richers
 */
public interface IProperty<T> {

	/**
	 * Returns the {@link PropertyName} that identifies this {@link IProperty}.
	 *
	 * @return the {@link PropertyName} of this {@link IProperty} (never null)
	 */
	PropertyName getPropertyName();

	/**
	 * Returns the value of this {@link IProperty}.
	 *
	 * @return the value of this {@link IProperty} (may be null)
	 */
	T getValue();
}
