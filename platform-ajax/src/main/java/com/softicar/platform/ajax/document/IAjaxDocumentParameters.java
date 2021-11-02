package com.softicar.platform.ajax.document;

import com.softicar.platform.ajax.AjaxI18n;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Aggregates the parameters given to an {@link IAjaxDocument}.
 *
 * @author Oliver Richers
 */
public interface IAjaxDocumentParameters {

	/**
	 * Returns the value of the given parameter.
	 * <p>
	 * If the specified parameter was not defined <i>null</i> will be returned.
	 *
	 * @param parameterName
	 *            the name of the parameter
	 * @return the value of the parameter or null
	 */
	default String getParameterOrNull(String parameterName) {

		List<String> list = getParameterValueList(parameterName);
		return !list.isEmpty()? list.get(0) : null;
	}

	/**
	 * Returns the value of the given parameter.
	 * <p>
	 * If the specified parameter was not defined, the given default value will
	 * be returned.
	 *
	 * @param parameterName
	 *            the name of the parameter
	 * @param defaultValue
	 *            the default value to use
	 * @return the value of the parameter or the given default value
	 */
	default String getParameterOrDefault(String parameterName, String defaultValue) {

		String value = getParameterOrNull(parameterName);
		return value != null? value : defaultValue;
	}

	/**
	 * Returns the value of the given parameter.
	 * <p>
	 * If the specified parameter was not defined this throws an exception.
	 *
	 * @param parameterName
	 *            the name of the parameter
	 * @return the value of the parameter (never null)
	 * @throws SofticarUserException
	 *             if the specified parameter was not defined
	 */
	default String getParameterOrThrow(String parameterName) {

		String value = getParameterOrNull(parameterName);
		if (value != null) {
			return value;
		} else {
			throw new SofticarUserException(AjaxI18n.MISSING_DOCUMENT_PARAMETER_ARG1.toDisplay(parameterName));
		}
	}

	/**
	 * Returns the list of values of the given parameter.
	 * <p>
	 * If the specified parameter was not defined this returns an empty list.
	 *
	 * @param parameterName
	 *            the name of the parameter
	 * @return the list of value of the parameter (never null)
	 */
	List<String> getParameterValueList(String parameterName);

	/**
	 * Returns the value of the given parameter.
	 * <p>
	 * If the specified parameter was not defined <i>null</i> will be returned.
	 * The given function parser will be used to convert the string value into
	 * the desired value type.
	 *
	 * @param parameterName
	 *            the name of the parameter
	 * @param parser
	 *            the parser function to use for conversion
	 * @return the value of the parameter or the given default value
	 */
	default <T> T getParameterOrNull(String parameterName, Function<String, T> parser) {

		String value = getParameterOrNull(parameterName);
		return value != null? parser.apply(value) : null;
	}

	/**
	 * Returns the value of the given parameter.
	 * <p>
	 * If the specified parameter was not defined, the given default value will
	 * be returned. The given function parser will be used to convert the string
	 * value into the desired value type.
	 *
	 * @param parameterName
	 *            the name of the parameter
	 * @param defaultValue
	 *            the default value to use
	 * @param parser
	 *            the parser function to use for conversion
	 * @return the value of the parameter or the given default value
	 */
	default <T> T getParameterOrDefault(String parameterName, T defaultValue, Function<String, T> parser) {

		String value = getParameterOrNull(parameterName);
		return value != null? parser.apply(value) : defaultValue;
	}

	/**
	 * Returns the value of the given parameter.
	 * <p>
	 * If the specified parameter was not defined this throws an exception. The
	 * given function parser will be used to convert the string value into the
	 * desired value type.
	 *
	 * @param parameterName
	 *            the name of the parameter
	 * @param parser
	 *            the parser function to use for conversion
	 * @return the value of the parameter (never null)
	 * @throws SofticarUserException
	 *             if the specified parameter was not defined
	 */
	default <T> T getParameterOrThrow(String parameterName, Function<String, T> parser) {

		return parser.apply(getParameterOrThrow(parameterName));
	}

	/**
	 * Returns the list of values of the given parameter.
	 * <p>
	 * If the specified parameter was not defined this returns an empty list.
	 * The given parser function will be used to convert the string values into
	 * the desired value type.
	 *
	 * @param parameterName
	 *            the name of the parameter
	 * @param parser
	 *            the parser function to use for conversion
	 * @return the list of value of the parameter (never null)
	 */
	default <T> List<T> getParameterValueList(String parameterName, Function<String, T> parser) {

		return getParameterValueList(parameterName).stream().map(parser).collect(Collectors.toList());
	}
}
