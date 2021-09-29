package com.softicar.platform.ajax.request;

import com.softicar.platform.ajax.exceptions.AjaxHttpBadRequestError;
import javax.servlet.ServletRequest;

/**
 * Utility methods to fetch AJAX request parameters.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class AjaxParameterUtils {

	/**
	 * Returns the {@link String} value of the request parameter.
	 *
	 * @param request
	 *            the servlet request (never <i>null</i>)
	 * @param parameterName
	 *            the name of the parameter (never <i>null</i>)
	 * @return the {@link String} value of the parameter (never <i>null</i>)
	 * @throws AjaxHttpBadRequestError
	 *             if no value was specified for the given parameter name
	 */
	public static String getStringOrThrow(ServletRequest request, String parameterName) {

		String value = request.getParameter(parameterName);
		if (value == null) {
			throw new AjaxHttpBadRequestError("Missing request parameter: '%s'", parameterName);
		}
		return value;
	}

	/**
	 * Returns the value of the request parameter as {@link Integer}.
	 * <p>
	 * Returns <i>null</i> if no value was specified for the parameter with the
	 * given name.
	 *
	 * @param request
	 *            the servlet request (never <i>null</i>)
	 * @param parameterName
	 *            the name of the parameter (never <i>null</i>)
	 * @return the {@link Integer} value of the parameter (may be <i>null</i>)
	 * @throws AjaxHttpBadRequestError
	 *             if the parameter value cannot be parsed to {@link Integer}
	 */
	public static Integer getInteger(ServletRequest request, String parameterName) {

		String value = request.getParameter(parameterName);
		try {
			return value != null? Integer.parseInt(value) : null;
		} catch (NumberFormatException exception) {
			throw new AjaxHttpBadRequestError(//
				exception,
				"The value of request parameter '%s' must be an integer but was '%s'.",
				parameterName,
				value);
		}
	}

	/**
	 * Returns the value of the request parameter as {@code int}.
	 * <p>
	 * Returns the given default value if no value was specified for the
	 * parameter with the given name.
	 *
	 * @param request
	 *            the servlet request (never <i>null</i>)
	 * @param parameterName
	 *            the name of the parameter (never <i>null</i>)
	 * @param defaultValue
	 *            the default value to use when no parameter with the given name
	 *            was specified
	 * @return the value of the parameter, or the given default value
	 */
	public static int getIntOrDefault(ServletRequest request, String parameterName, int defaultValue) {

		String value = request.getParameter(parameterName);
		try {
			return value != null? Integer.parseInt(value) : defaultValue;
		} catch (NumberFormatException exception) {
			throw new AjaxHttpBadRequestError(//
				exception,
				"The value of request parameter '%s' must be an integer but was '%s'.",
				parameterName,
				value);
		}
	}

	/**
	 * Returns the value of the request parameter as {@code int}.
	 *
	 * @param request
	 *            the servlet request (never <i>null</i>)
	 * @param parameterName
	 *            the name of the parameter (never <i>null</i>)
	 * @return the value of the parameter
	 * @throws AjaxHttpBadRequestError
	 *             if no value was specified for the given parameter name
	 */
	public static int getIntOrThrow(ServletRequest request, String parameterName) {

		Integer value = getInteger(request, parameterName);
		if (value == null) {
			throw new AjaxHttpBadRequestError("Missing request parameter: '%s'", parameterName);
		}
		return value;
	}

	/**
	 * Returns the value of the request parameter as {@link Double}.
	 * <p>
	 * Returns <i>null</i> if no value was specified for the parameter with the
	 * given name.
	 *
	 * @param request
	 *            the servlet request (never <i>null</i>)
	 * @param parameterName
	 *            the name of the parameter (never <i>null</i>)
	 * @return the {@link Double} value of the parameter (may be <i>null</i>)
	 * @throws AjaxHttpBadRequestError
	 *             if the parameter value cannot be parsed to {@link Double}
	 */
	public static Double getDouble(ServletRequest request, String parameterName) {

		String value = request.getParameter(parameterName);
		try {
			return value != null? Double.parseDouble(value) : null;
		} catch (NumberFormatException exception) {
			throw new AjaxHttpBadRequestError(//
				exception,
				"The value of request parameter '%s' must be a number but was '%s'.",
				parameterName,
				value);
		}
	}

	/**
	 * Returns the value of the request parameter as {@link Double}.
	 * <p>
	 * Returns the given default value if no value was specified for the
	 * parameter with the given name.
	 *
	 * @param request
	 *            the servlet request (never <i>null</i>)
	 * @param parameterName
	 *            the name of the parameter (never <i>null</i>)
	 * @param defaultValue
	 *            the default value to use when no value was specified for the
	 *            parameter with the given name
	 * @return the value of the parameter, or the given default value (never
	 *         <i>null</i>)
	 */
	public static Double getDoubleOrDefault(ServletRequest request, String parameterName, double defaultValue) {

		String value = request.getParameter(parameterName);
		try {
			return value != null? Double.parseDouble(value) : defaultValue;
		} catch (NumberFormatException exception) {
			throw new AjaxHttpBadRequestError(//
				exception,
				"The value of request parameter '%s' must be a number but was '%s'.",
				parameterName,
				value);
		}
	}

	/**
	 * Returns the value of the request parameter as {@code Double}.
	 *
	 * @param request
	 *            the servlet request (never <i>null</i>)
	 * @param parameterName
	 *            the name of the parameter (never <i>null</i>)
	 * @return the value of the parameter
	 * @throws AjaxHttpBadRequestError
	 *             if no value was specified for the given parameter name
	 */
	public static Double getDoubleOrThrow(ServletRequest request, String parameterName) {

		Double value = getDouble(request, parameterName);
		if (value == null) {
			throw new AjaxHttpBadRequestError("Missing request parameter: '%s'", parameterName);
		}
		return value;
	}
}
