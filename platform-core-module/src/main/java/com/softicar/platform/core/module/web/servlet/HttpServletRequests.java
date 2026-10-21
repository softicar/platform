package com.softicar.platform.core.module.web.servlet;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.DayTimeParser;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreProperties;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Enumeration;

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
	 * If the request was received from one of the
	 * {@link CoreProperties#TRUSTED_PROXIES}, the client address is taken from
	 * the <i>X-Forwarded-For</i> header, as described in
	 * {@link ClientAddressDeterminer}. Otherwise, this method returns
	 * {@link HttpServletRequest#getRemoteAddr()}.
	 *
	 * @param request
	 *            the request object
	 * @return the client address
	 */
	public static String getClientAddress(HttpServletRequest request) {

		return getClientAddress(request, CoreProperties.TRUSTED_PROXIES.getValue());
	}

	static String getClientAddress(HttpServletRequest request, String trustedProxies) {

		return new ClientAddressDeterminer(trustedProxies).determine(request.getRemoteAddr(), getForwardedFor(request));
	}

	/**
	 * Joins all <i>X-Forwarded-For</i> headers, since a proxy might add its own
	 * header instead of appending to the one supplied by the client.
	 */
	private static String getForwardedFor(HttpServletRequest request) {

		Enumeration<String> headers = request.getHeaders("X-Forwarded-For");
		if (headers != null && headers.hasMoreElements()) {
			return String.join(",", Collections.list(headers));
		} else {
			return null;
		}
	}
}
