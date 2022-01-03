package com.softicar.platform.ajax.request.parameters;

import com.softicar.platform.ajax.request.AjaxUtils;
import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

class MultipartRequestParameters extends AbstractAjaxRequestParameters {

	private static final String AJAX_INPUT_REQUEST_PARAMETER = "ajaxInput";
	private Iterable<IDomFileUpload> fileUploads = Collections.emptyList();

	public MultipartRequestParameters(HttpServletRequest request) {

		try {
			setRequestParameters(request);
			initializeFileUploadsIterable(request);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		} catch (ServletException exception) {
			throw new SofticarDeveloperException(exception);
		}
	}

	@Override
	public Iterable<IDomFileUpload> getFileUploads() {

		return fileUploads;
	}

	private void setRequestParameters(HttpServletRequest request) throws IOException, ServletException {

		try (InputStream stream = request.getPart(AJAX_INPUT_REQUEST_PARAMETER).getInputStream()) {
			setMap(AjaxUtils.parseHexParameterMap(stream));
		}
	}

	private void initializeFileUploadsIterable(HttpServletRequest request) throws IOException, ServletException {

		Iterator<Part> iterator = request.getParts().iterator();
		while (iterator.hasNext()) {
			Part part = iterator.next();
			if (!part.getName().equals(AJAX_INPUT_REQUEST_PARAMETER)) {
				fileUploads = new AjaxFileUploadList(part, iterator);
				break;
			}
		}
	}
}
