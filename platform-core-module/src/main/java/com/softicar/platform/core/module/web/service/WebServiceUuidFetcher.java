package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.network.http.error.HttpBadRequestError;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

public class WebServiceUuidFetcher {

	private final HttpServletRequest request;

	public WebServiceUuidFetcher(HttpServletRequest request) {

		this.request = request;
	}

	public UUID getServiceUuidOrThrow() {

		String serviceIdentifier = new WebServicePathExtractor(request)//
			.extractPathOrThrow()
			.getServiceIdentifier();
		return parseUuid(serviceIdentifier);
	}

	private UUID parseUuid(String uuidString) {

		try {
			return UUID.fromString(uuidString);
		} catch (Exception exception) {
			throw new HttpBadRequestError(exception, "Request URL contains malformed web service UUID.");
		}
	}
}
