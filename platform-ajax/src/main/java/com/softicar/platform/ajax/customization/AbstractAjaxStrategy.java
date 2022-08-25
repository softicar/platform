package com.softicar.platform.ajax.customization;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.export.AjaxMemoryBuffer;
import com.softicar.platform.ajax.export.IAjaxExportBuffer;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.common.io.resource.IResource;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class AbstractAjaxStrategy implements IAjaxStrategy {

	@Override
	public void beforeDocumentRequestHandling(IAjaxDocument document, IAjaxRequest request) {

		// nothing to do by default
	}

	@Override
	public IAjaxExportBuffer createExportBuffer() {

		return new AjaxMemoryBuffer();
	}

	@Override
	public String getResourceUrlSuffix() {

		return "";
	}

	@Override
	public Optional<IResource> getFaviconResource() {

		return Optional.empty();
	}

	@Override
	public void logException(Throwable throwable, HttpServletRequest request) {

		throwable.printStackTrace();
	}

	@Override
	public boolean isAdministrative(HttpSession session) {

		return false;
	}
}
