package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.network.http.error.HttpBadRequestError;
import com.softicar.platform.common.network.http.error.HttpInternalServerError;
import com.softicar.platform.common.string.Trim;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

public class WebServiceUuidFetcher {

	private final HttpServletRequest request;

	public WebServiceUuidFetcher(HttpServletRequest request) {

		this.request = request;
	}

	public UUID getServiceUuidOrThrow() {

		return getFromPath()//
			.map(this::parseUuid)
			.orElseThrow(() -> new HttpBadRequestError("Request URL is missing web service UUID."));
	}

	private Optional<String> getFromPath() {

		String requestUri = Optional//
			.ofNullable(request.getRequestURI())
			.orElseThrow(() -> new HttpInternalServerError("Failed to retrieve request URL."));
		String contextPath = Optional//
			.ofNullable(request.getContextPath())
			.orElseThrow(() -> new HttpInternalServerError("Failed to retrieve servlet context path."));
		return WebServicePath//
			.parse(Trim.trimPrefix(requestUri, contextPath))
			.map(WebServicePath::getServiceIdentifier);
	}

	private UUID parseUuid(String uuidString) {

		try {
			return UUID.fromString(uuidString);
		} catch (Exception exception) {
			throw new HttpBadRequestError(exception, "Request URL contains malformed web service UUID.");
		}
	}
}
