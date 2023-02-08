package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.network.http.error.HttpBadRequestError;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

public class WebServiceUuidFetcher {

	private final HttpServletRequest request;

	public WebServiceUuidFetcher(HttpServletRequest request) {

		this.request = request;
	}

	public UUID getServiceUuidOrThrow() {

		return getFromParameter().orElseGet(this::getFromPath);
	}

	private UUID getFromPath() {

		String serviceIdentifier = new WebServicePathExtractor(request)//
			.extractPathOrThrow()
			.getServiceIdentifier();
		return parseUuid(serviceIdentifier);
	}

	// only necessary for temporary backwards compatibility
	private Optional<UUID> getFromParameter() {

		String idParameter = request.getParameter("id");
		if (idParameter != null) {
			try {
				return Optional.of(UUID.fromString(idParameter));
			} catch (Exception exception) {
				DevNull.swallow(exception);
				Log.fwarning("Got id request parameter but failed to parse it into UUID.");
			}
		}
		return Optional.empty();
	}

	private UUID parseUuid(String uuidString) {

		try {
			return UUID.fromString(uuidString);
		} catch (Exception exception) {
			throw new HttpBadRequestError(exception, "Request URL contains malformed web service UUID.");
		}
	}
}
