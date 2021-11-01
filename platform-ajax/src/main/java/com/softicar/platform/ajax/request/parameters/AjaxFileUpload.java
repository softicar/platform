package com.softicar.platform.ajax.request.parameters;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.fileupload.FileItemStream;

class AjaxFileUpload implements IDomFileUpload {

	private final FileItemStream item;

	public AjaxFileUpload(FileItemStream item) {

		this.item = item;
	}

	@Override
	public String getFilename() {

		return item.getName();
	}

	@Override
	public InputStream getStream() {

		try {
			return item.openStream();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public String getContentType() {

		return item.getContentType();
	}
}
