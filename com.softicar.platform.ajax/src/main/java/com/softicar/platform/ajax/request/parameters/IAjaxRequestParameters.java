package com.softicar.platform.ajax.request.parameters;

import com.softicar.platform.dom.event.upload.IDomFileUpload;
import java.util.Map;

public interface IAjaxRequestParameters {

	String[] get(String key);

	Map<String, String[]> getMap();

	/**
	 * Returns the file uploads.
	 * <p>
	 * The returned iterable can only be iterated once. Also, the returned
	 * streams can be read only once and one after the other in iteration order.
	 *
	 * @return all file uploads
	 */
	Iterable<IDomFileUpload> getFileUploads();
}
