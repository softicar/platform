package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.core.module.CoreI18n;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

public class WebServiceUuidFetcher {

	private static final String ID_PARAMETER_NAME = "id";
	private final HttpServletRequest request;

	public WebServiceUuidFetcher(HttpServletRequest request) {

		this.request = request;
	}

	public UUID getServiceUuidOrThrow() {

		return getFromPath()//
			.or(this::getFromParameter)
			.map(this::parseUuid)
			.orElseThrow(() -> new SofticarUserException(CoreI18n.WEB_SERVICE_UUID_IS_MISSING));
	}

	private Optional<String> getFromPath() {

		return WebServicePath//
			.parse(request.getPathInfo())
			.map(WebServicePath::getServiceIdentifier);
	}

	private Optional<String> getFromParameter() {

		return Optional.ofNullable(request.getParameter(ID_PARAMETER_NAME));
	}

	private UUID parseUuid(String uuidString) {

		try {
			return UUID.fromString(uuidString);
		} catch (Exception exception) {
			throw new SofticarUserException(exception, CoreI18n.WEB_SERVICE_UUID_IS_ILLEGAL);
		}
	}
}
