package com.softicar.platform.common.string.parameter;

/**
 * Implementation of a string parameter.
 * <p>
 * This implementation will not throw {@link InvalidParameterValueException}
 * because any string value is considered to be valid.
 * 
 * @author Oliver Richers
 */
public class StringParameter extends AbstractParameter<String> {

	public StringParameter(String name, String valueString) {

		super(name, valueString);
	}

	@Override
	protected String parseValueString(String valueString) {

		return valueString;
	}
}
