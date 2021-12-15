package com.softicar.platform.ajax.request.parameters;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.Part;

class AjaxFileUpload implements IDomFileUpload {

	private final Part part;

	public AjaxFileUpload(Part part) {

		this.part = part;
	}

	@Override
	public String getFilename() {

		return part.getSubmittedFileName();
	}

	@Override
	public InputStream getStream() {

		try {
			return part.getInputStream();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public String getContentType() {

		return part.getContentType();
	}
}
