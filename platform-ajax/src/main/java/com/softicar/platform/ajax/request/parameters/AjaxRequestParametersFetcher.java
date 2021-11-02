package com.softicar.platform.ajax.request.parameters;

import com.softicar.platform.ajax.exceptions.AjaxHttpBadRequestError;
import com.softicar.platform.ajax.request.AjaxUtils;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;

public class AjaxRequestParametersFetcher {

	private final HttpServletRequest request;

	public AjaxRequestParametersFetcher(HttpServletRequest request) {

		this.request = request;
	}

	public IAjaxRequestParameters fetch() {

		String contentType = request.getContentType();
		try {
			if (contentType != null) {
				if (contentType.startsWith("text/plain")) {
					return parsePlainTextParameters();
				} else if (contentType.startsWith("multipart/form-data")) {
					return parseMultipartParameters();
				}
			}

			return parseHttpParameters();
		} catch (Exception exception) {
			throw new AjaxHttpBadRequestError(exception, "Failed to parse request parameters.");
		}
	}

	private IAjaxRequestParameters parseHttpParameters() {

		return new SimpleRequestParameters(request.getParameterMap());
	}

	private IAjaxRequestParameters parseMultipartParameters() {

		return new MultipartRequestParameters(request);
	}

	private IAjaxRequestParameters parsePlainTextParameters() throws IOException {

		try (InputStream inputStream = request.getInputStream()) {
			return new SimpleRequestParameters(AjaxUtils.parseParameterMap(inputStream));
		}
	}
}
