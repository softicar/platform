package com.softicar.platform.common.core.properties;

import java.util.Objects;
import java.util.function.Function;

/**
 * Base class of {@link IProperty} implementations which define a default values
 * and which parse textual property values.
 *
 * @author Alexander Schmidt
 */
public abstract class AbstractProperty<T> implements IProperty<T> {

	private final PropertyName propertyName;
	private final T defaultValue;
	private final Function<String, T> parser;

	/**
	 * Constructs this property using the given property name.
	 *
	 * @param propertyName
	 *            the name used to read the value from the configuration file
	 * @param defaultValue
	 *            the default value to return by {@link #getValue()} if no value
	 *            is defined
	 * @param parser
	 *            the parser to parse the textual value
	 */
	protected AbstractProperty(PropertyName propertyName, T defaultValue, Function<String, T> parser) {

		Objects.nonNull(propertyName);
		Objects.nonNull(parser);

		this.propertyName = propertyName;
		this.defaultValue = defaultValue;
		this.parser = parser;
	}

	/**
	 * Determines the textual value of the property with the given
	 * {@link PropertyName}.
	 *
	 * @param propertyName
	 *            the {@link PropertyName} that identified the property which
	 *            holds the desired value (never null)
	 * @return the value of the property with the given {@link PropertyName}
	 *         (may be null)
	 */
	protected abstract String getValueString(PropertyName propertyName);

	@Override
	public PropertyName getPropertyName() {

		return propertyName;
	}

	@Override
	public T getValue() {

		String valueString = getValueString(propertyName);
		if (valueString != null) {
			return parser.apply(valueString.trim());
		} else {
			return defaultValue;
		}
	}
}
