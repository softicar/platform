package com.softicar.platform.common.core.properties;

import java.util.Objects;
import java.util.function.Function;

/**
 * Default implementation of {@link IProperty}.
 *
 * @author Oliver Richers
 */
public class Property<T> extends AbstractProperty<T> {

	private final IPropertyMap propertyMap;

	/**
	 * Constructs this property using the given property name.
	 *
	 * @param propertyMap
	 *            the name holding the value of the property (never null)
	 * @param propertyName
	 *            the name used to read the value from the configuration file
	 * @param defaultValue
	 *            the default value to return by {@link #getValue()} if no value
	 *            is defined
	 * @param parser
	 *            the parser to parse the textual value
	 */
	protected Property(IPropertyMap propertyMap, PropertyName propertyName, T defaultValue, Function<String, T> parser) {

		super(propertyName, defaultValue, parser);
		this.propertyMap = Objects.requireNonNull(propertyMap);
	}

	@Override
	protected String getValueString(PropertyName propertyName) {

		return propertyMap.getValueString(propertyName);
	}
}
