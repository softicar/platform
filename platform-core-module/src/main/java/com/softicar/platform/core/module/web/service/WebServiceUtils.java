package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.mime.MimeType;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WebServiceUtils {

	private static final int SC_TOO_MANY_REQUESTS = 429;

	public static int getTooManyRequestsHttpCode() {

		return SC_TOO_MANY_REQUESTS;
	}

	public static void sendPlainTextResponse(HttpServletResponse response, int statusCode, IDisplayString message) {

		response.setStatus(statusCode);
		response.setContentType(MimeType.TEXT_PLAIN.getIdentifier());
		try (PrintWriter writer = new PrintWriter(response.getOutputStream())) {
			writer.println(message.toString());
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public static void sendAuthorizationRequest(HttpServletResponse response, String scheme, String realm) {

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.addHeader("WWW-Authenticate", String.format("%s realm=\"%s\"", scheme, realm));
	}

	public static void sendSeeOtherResponse(HttpServletRequest request, HttpServletResponse response) {

		response.setStatus(HttpServletResponse.SC_SEE_OTHER);
		response.addHeader("Location", getCompleteRequestUrl(request));
	}

	public static String getCompleteRequestUrl(HttpServletRequest request) {

		return request.getRequestURL() + getOptionalQuerySuffix(request);
	}

	private static String getOptionalQuerySuffix(HttpServletRequest request) {

		String queryString = request.getQueryString();
		return queryString.isEmpty()? "" : "?" + queryString;
	}
}
