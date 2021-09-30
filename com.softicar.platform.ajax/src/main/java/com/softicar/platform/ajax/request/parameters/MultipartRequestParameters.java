package com.softicar.platform.ajax.request.parameters;

import com.softicar.platform.ajax.request.AjaxUtils;
import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

class MultipartRequestParameters extends AbstractAjaxRequestParameters {

	private Iterable<IDomFileUpload> fileUploads = Collections.emptyList();

	// see http://commons.apache.org/fileupload/streaming.html
	public MultipartRequestParameters(HttpServletRequest request) {

		try {
			ServletFileUpload upload = new ServletFileUpload();
			FileItemIterator iterator = upload.getItemIterator(request);
			while (iterator.hasNext()) {
				FileItemStream item = iterator.next();
				if (item.isFormField()) {
					if (item.getFieldName().equals("ajaxInput")) {
						try (InputStream stream = item.openStream()) {
							setMap(AjaxUtils.parseHexParameterMap(stream));
						}
					}
				} else {
					fileUploads = new AjaxFileUploadList(item, iterator);
					break;
				}
			}
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		} catch (FileUploadException exception) {
			throw new SofticarDeveloperException(exception);
		}
	}

	@Override
	public Iterable<IDomFileUpload> getFileUploads() {

		return fileUploads;
	}
}
