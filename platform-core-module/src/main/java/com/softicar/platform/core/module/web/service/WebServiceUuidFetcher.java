package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.string.Trim;
import com.softicar.platform.core.module.CoreI18n;
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
			.orElseThrow(() -> new SofticarUserException(CoreI18n.WEB_SERVICE_UUID_IS_MISSING));
	}

	private Optional<String> getFromPath() {

		String requestUri = Optional.ofNullable(request.getRequestURI()).orElse("");
		String contextPath = Optional.ofNullable(request.getContextPath()).orElse("");
		return WebServicePath//
			.parse(Trim.trimPrefix(requestUri, contextPath))
			.map(WebServicePath::getServiceIdentifier);
	}

	private UUID parseUuid(String uuidString) {

		try {
			return UUID.fromString(uuidString);
		} catch (Exception exception) {
			throw new SofticarUserException(exception, CoreI18n.WEB_SERVICE_UUID_IS_ILLEGAL);
		}
	}
}
