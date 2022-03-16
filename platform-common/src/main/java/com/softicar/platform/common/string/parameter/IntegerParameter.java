package com.softicar.platform.common.string.parameter;

/**
 * Implementation of an integer parameter.
 * <p>
 * This implementation throws an {@link InvalidParameterValueException} if the
 * given value string cannot be parsed into an integer.
 *
 * @author Oliver Richers
 */
public class IntegerParameter extends AbstractParameter<Integer> {

	public IntegerParameter(String name, String valueString) {

		super(name, valueString);
	}

	@Override
	protected Integer parseValueString(String valueString) {

		return Integer.parseInt(valueString);
	}
}
