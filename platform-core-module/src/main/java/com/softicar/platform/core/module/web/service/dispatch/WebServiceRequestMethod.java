package com.softicar.platform.core.module.web.service.dispatch;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import java.util.Map;
import java.util.TreeMap;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Enumeration of all value HTTP request methods.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public enum WebServiceRequestMethod {

	CONNECT,
	DELETE,
	GET,
	HEAD,
	OPTIONS,
	POST,
	PUT,
	TRACE;

	private static Map<String, WebServiceRequestMethod> map = new TreeMap<>();

	static {
		for (WebServiceRequestMethod requestMethod: values()) {
			map.put(requestMethod.name(), requestMethod);
		}
	}

	public static WebServiceRequestMethod getFromRequest(HttpServletRequest request) {

		if (request != null) {
			String method = request.getMethod();
			WebServiceRequestMethod requestMethod = map.get(method);
			if (requestMethod != null) {
				return requestMethod;
			} else {
				throw new SofticarDeveloperException("Invalid request method: %s", method);
			}
		} else {
			throw new SofticarDeveloperException("No request specified.");
		}
	}
}
