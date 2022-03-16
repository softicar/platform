package com.softicar.platform.common.string.parameter;

import com.softicar.platform.common.core.utils.DevNull;

/**
 * This is a basic implementation of {@link IParameter}.
 *
 * @author Oliver Richers
 */
public abstract class AbstractParameter<T> implements IParameter<T> {

	private final String name;
	private final String valueString;

	public AbstractParameter(AbstractParameter<?> parameter) {

		this.name = parameter.name;
		this.valueString = parameter.valueString;
	}

	public AbstractParameter(String name, String valueString) {

		this.name = name;
		this.valueString = valueString;
	}

	@Override
	public String getName() {

		return name;
	}

	@Override
	public boolean isNull() {

		return valueString == null;
	}

	@Override
	public T getValue() {

		if (isNull()) {
			throw new MissingParameterValueException(this);
		}

		try {
			return parseValueString(valueString);
		} catch (InvalidParameterValueException exception) {
			throw exception;
		} catch (Exception exception) {
			throw new InvalidParameterValueException(exception, this, valueString);
		}
	}

	@Override
	public T getValue(T defaultValue) {

		try {
			return getValue();
		} catch (MissingParameterValueException exception) {
			DevNull.swallow(exception);
			return defaultValue;
		}
	}

	protected abstract T parseValueString(String valueString);
}
