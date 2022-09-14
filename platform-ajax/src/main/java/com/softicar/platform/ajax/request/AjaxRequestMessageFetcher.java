package com.softicar.platform.ajax.request;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.HexDecoderStream;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.string.charset.Charsets;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

class AjaxRequestMessageFetcher {

	private static final String FORM_REQUEST_INPUT_NAME = "form-request-input";
	private final HttpServletRequest request;
	private final String contentType;

	public AjaxRequestMessageFetcher(HttpServletRequest request) {

		this.request = request;
		this.contentType = request.getContentType();
	}

	public Optional<AjaxRequestMessage> fetchMessage() {

		if (isContentType(MimeType.APPLICATION_JSON)) {
			return Optional.of(parseFromRequest());
		} else if (isContentType(MimeType.MULTIPART_FORM_DATA)) {
			return Optional.of(parseFromRequestPart());
		} else {
			return Optional.empty();
		}
	}

	private boolean isContentType(MimeType mimeType) {

		return contentType != null && contentType.startsWith(mimeType.getIdentifier());
	}

	private AjaxRequestMessage parseFromRequest() {

		try (var inputStream = request.getInputStream()) {
			return AjaxRequestMessage.parseJson(StreamUtils.toString(inputStream, Charsets.UTF8));
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private AjaxRequestMessage parseFromRequestPart() {

		try (var inputStream = new HexDecoderStream(request.getPart(FORM_REQUEST_INPUT_NAME).getInputStream())) {
			return AjaxRequestMessage.parseJson(StreamUtils.toString(inputStream, Charsets.UTF8));
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		} catch (ServletException exception) {
			throw new RuntimeException(exception);
		}
	}
}
