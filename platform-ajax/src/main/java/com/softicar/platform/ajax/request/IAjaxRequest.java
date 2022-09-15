package com.softicar.platform.ajax.request;

import com.softicar.platform.ajax.framework.AjaxFramework;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Interface to access the parameters of an AJAX request.
 *
 * @author Oliver Richers
 */
public interface IAjaxRequest {

	AjaxFramework getAjaxFramework();

	HttpServletRequest getHttpRequest();

	HttpServletResponse getHttpResponse();

	HttpSession getHttpSession();

	AjaxRequestMessage getRequestMessageOrThrow();

	Iterable<IDomFileUpload> getFileUploads();

	boolean isDebug();

	boolean isVerbose();

	boolean isDocumentActionRequest();

	boolean isDocumentCreationRequest();

	boolean isResourceRequest();

	boolean isFavoriteIconRequest();

	boolean isGZipSupported();

	boolean isDeflateSupported();
}
