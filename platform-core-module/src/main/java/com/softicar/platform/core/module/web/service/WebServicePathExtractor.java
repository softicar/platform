package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.network.http.error.HttpBadRequestError;
import com.softicar.platform.common.network.http.error.HttpInternalServerError;
import com.softicar.platform.common.string.Trim;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

/**
 * Extracts the {@link WebServicePath} from an {@link HttpServletRequest}.
 *
 * @author Oliver Richers
 */
public class WebServicePathExtractor {

	private final HttpServletRequest request;

	public WebServicePathExtractor(HttpServletRequest request) {

		this.request = Objects.requireNonNull(request);
	}

	/**
	 * Extracts the {@link WebServicePath} from the {@link HttpServletRequest}.
	 *
	 * @return the extracted {@link WebServicePath} (never <i>null</i>)
	 */
	public Optional<WebServicePath> extractPath() {

		String requestUri = Optional//
			.ofNullable(request.getRequestURI())
			.orElseThrow(() -> new HttpInternalServerError("Failed to retrieve request URI."));

		String contextPath = Optional//
			.ofNullable(request.getContextPath())
			.orElseThrow(() -> new HttpInternalServerError("Failed to retrieve servlet context path."));

		return WebServicePath.parse(Trim.trimPrefix(requestUri, contextPath));
	}

	/**
	 * Extracts the {@link WebServicePath} from the {@link HttpServletRequest}.
	 *
	 * @return the extracted {@link WebServicePath} (never <i>null</i>)
	 */
	public WebServicePath extractPathOrThrow() {

		return extractPath().orElseThrow(() -> new HttpBadRequestError("Failed to determine web service path from request URI."));
	}
}
