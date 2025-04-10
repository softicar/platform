package com.softicar.platform.core.module.web.servlet;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.DayTimeParser;
import com.softicar.platform.core.module.CoreI18n;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Utility methods for {@link HttpServletRequest}.
 *
 * @author Oliver Richers
 */
public class HttpServletRequests {

	/**
	 * Gets the parameter value for the specified parameter name from the given
	 * {@link HttpServletRequest}.
	 *
	 * @param request
	 *            the {@link HttpServletRequest} to get the value from
	 * @param parameterName
	 *            the name of the request parameter (never null)
	 * @return the value
	 * @throws SofticarException
	 *             if the value is missing
	 */
	public static String getParameter(HttpServletRequest request, String parameterName) throws SofticarException {

		String parameterValue = request.getParameter(parameterName);
		if (parameterValue != null) {
			return parameterValue;
		} else {
			throw new SofticarUserException(CoreI18n.SERVLET_PARAMETER_ARG1_IS_REQUIRED.toDisplay(parameterName));
		}
	}

	/**
	 * Gets the parameter with the specified name from the HTTP request and
	 * converts it to integer.
	 *
	 * @param request
	 *            the HTTP servlet request to get the parameter from
	 * @param parameterName
	 *            the name of the HTTP request parameter
	 * @return the parsed integer
	 * @throws SofticarException
	 *             if the parameter is missing or if it can't be parsed into an
	 *             integer
	 */
	public static int getIntParameter(HttpServletRequest request, String parameterName) throws RuntimeException {

		Integer parameterValue = testIntegerParameter(request, parameterName);
		if (parameterValue == null) {
			throw new SofticarUserException(CoreI18n.SERVLET_PARAMETER_ARG1_IS_REQUIRED.toDisplay(parameterName));
		}
		return parameterValue.intValue();
	}

	/**
	 * Tries to get the parameter with the specified name from the HTTP request
	 * and converts it to integer if it exists.
	 *
	 * @param request
	 *            the HTTP servlet request to get the parameter from
	 * @param parameterName
	 *            the name of the HTTP request parameter
	 * @return the parsed integer
	 * @throws SofticarException
	 *             if the parameter can't be parsed into an integer
	 */
	public static Integer testIntegerParameter(HttpServletRequest request, String parameterName) throws RuntimeException {

		String parameterValue = request.getParameter(parameterName);
		try {
			return parameterValue != null? Integer.parseInt(parameterValue) : null;
		} catch (NumberFormatException exception) {
			throw new SofticarUserException(//
				exception,
				CoreI18n.THE_VALUE_OF_THE_SERVLET_PARAMETER_ARG1_MUST_BE_AN_INTEGER_BUT_WAS_ARG2.toDisplay(parameterName, parameterValue));
		}
	}

	/**
	 * Gets the parameter with the specified name from the HTTP request and
	 * converts it into a DayTime object.
	 *
	 * @param request
	 *            the HTTP servlet request to get the parameter from
	 * @param parameterName
	 *            the name of the HTTP request parameter
	 * @return the parsed DayTime object
	 * @throws RuntimeException
	 *             if the parameter is missing or if it can't be parsed into a
	 *             DayTime object
	 */
	public static DayTime getDayTimeParameter(HttpServletRequest request, String parameterName) throws RuntimeException {

		String parameterValue = request.getParameter(parameterName);
		if (parameterValue != null) {
			try {
				return DayTimeParser.parse(parameterValue, DayTimeParser.Format.YYYY_MM_DD_HH_MM_SS);
			} catch (Exception exception) {
				throw new SofticarUserException(//
					exception,
					CoreI18n.THE_VALUE_OF_THE_SERVLET_PARAMETER_ARG1_MUST_BE_A_DAY_TIME_SPECIFICATION_BUT_WAS_ARG2.toDisplay(parameterName, parameterValue));

			}
		} else {
			throw new SofticarUserException(CoreI18n.SERVLET_PARAMETER_ARG1_IS_REQUIRED.toDisplay(parameterName));
		}
	}

	/**
	 * Returns the client address from the given {@link HttpServletRequest}.
	 * <p>
	 * This method first checks for the <i>x-forwarded-for</i> header in the
	 * request and returns the first IP address listed in this header.
	 * Otherwise, if no such header exists, this method returns
	 * {@link HttpServletRequest#getRemoteAddr()}.
	 *
	 * @param request
	 *            the request object
	 * @return the client address
	 */
	public static String getClientAddress(HttpServletRequest request) {

		String forwardedForAddrees = getForwardedForAddrees(request);
		if (forwardedForAddrees != null && !forwardedForAddrees.isEmpty()) {
			return forwardedForAddrees;
		} else {
			return request.getRemoteAddr();
		}
	}

	private static String getForwardedForAddrees(HttpServletRequest request) {

		String xffHeader = request.getHeader("X-Forwarded-For");
		if (xffHeader != null) {
			String[] parts = xffHeader.split(",", 2);
			return parts.length > 0? parts[0].trim() : null;
		} else {
			return null;
		}
	}
}
