package com.softicar.platform.ajax.request.parameters;

import com.softicar.platform.dom.event.upload.IDomFileUpload;
import java.util.Collections;
import java.util.Map;

class SimpleRequestParameters extends AbstractAjaxRequestParameters {

	public SimpleRequestParameters(Map<String, String[]> map) {

		setMap(map);
	}

	@Override
	public Iterable<IDomFileUpload> getFileUploads() {

		return Collections.emptyList();
	}
}
