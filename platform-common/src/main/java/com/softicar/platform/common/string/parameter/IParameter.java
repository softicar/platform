package com.softicar.platform.common.string.parameter;

/**
 * This class represents a parameter given to a program or web service.
 * <p>
 * A parameter is basically a pair of the parameter name and a corresponding
 * value. A missing value, that is, a parameter not given to a program or web
 * service, is represented by a null-pointer.
 * 
 * @author Oliver Richers
 */
public interface IParameter<T> {

	/**
	 * Returns the name of this parameter.
	 * 
	 * @return the parameter name, never null
	 */
	String getName();

	/**
	 * Returns true if no value was given for this parameter.
	 * 
	 * @return true if the value is missing
	 */
	boolean isNull();

	/**
	 * Returns the value of this parameter.
	 * 
	 * @return the parameter value, never null
	 * @throws MissingParameterValueException
	 *             if no value was specified for this parameter
	 * @throws InvalidParameterValueException
	 *             if the parameter value is invalid, e.g. not a valid integer
	 */
	T getValue();

	/**
	 * Returns the value of this parameter or a default value if no value was
	 * specified for this parameter.
	 * 
	 * @return the parameter value or the specified default value
	 * @throws InvalidParameterValueException
	 *             if the parameter value is invalid, e.g. not a valid integer
	 */
	T getValue(T defaultValue);
}
