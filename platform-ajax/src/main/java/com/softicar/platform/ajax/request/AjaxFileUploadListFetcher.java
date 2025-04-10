package com.softicar.platform.ajax.request;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

class AjaxFileUploadListFetcher {

	private static final String FORM_REQUEST_INPUT_NAME = "form-request-input";
	private final HttpServletRequest request;
	private final String contentType;

	public AjaxFileUploadListFetcher(HttpServletRequest request) {

		this.request = request;
		this.contentType = request.getContentType();
	}

	public Iterable<IDomFileUpload> fetchFileUploads() {

		if (contentType != null && contentType.startsWith(MimeType.MULTIPART_FORM_DATA.getIdentifier())) {
			try {
				Iterator<Part> iterator = request.getParts().iterator();
				while (iterator.hasNext()) {
					Part part = iterator.next();
					if (!part.getName().equals(FORM_REQUEST_INPUT_NAME)) {
						return new AjaxFileUploadList(part, iterator);
					}
				}
			} catch (IOException exception) {
				throw new SofticarIOException(exception);
			} catch (ServletException exception) {
				throw new RuntimeException(exception);
			}
		}
		return Collections.emptyList();
	}
}
