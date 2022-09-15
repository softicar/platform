package com.softicar.platform.ajax.request;

import com.softicar.platform.ajax.exceptions.AjaxHttpBadRequestError;
import com.softicar.platform.ajax.framework.AjaxFramework;
import com.softicar.platform.ajax.resource.AjaxResourceUrl;
import com.softicar.platform.ajax.service.AbstractAjaxService;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Represents an {@link HttpServletRequest} that must be executed by an instance
 * of {@link AbstractAjaxService}.
 *
 * @author Oliver Richers
 */
public final class AjaxRequest implements IAjaxRequest {

	private final AjaxFramework ajaxFramework;
	private final HttpServletRequest httpRequest;
	private final HttpServletResponse httpResponse;
	private final Optional<AjaxRequestMessage> requestMessage;
	private final Iterable<IDomFileUpload> fileUploads;

	public AjaxRequest(AjaxFramework ajaxFramework, HttpServletRequest request, HttpServletResponse response) {

		this.ajaxFramework = ajaxFramework;
		this.httpRequest = request;
		this.httpResponse = response;
		this.requestMessage = new AjaxRequestMessageFetcher(request).fetchMessage();
		this.fileUploads = new AjaxFileUploadListFetcher(request).fetchFileUploads();

		log();
	}

	private void log() {

		if (isDebug()) {
			Log.finfo("parameters: %s", httpRequest.getParameterMap());
			requestMessage.ifPresent(message -> Log.finfo("message: %s", message));
		}
	}

	// ******************************************************************************** //
	// * member access functions                                                      * //
	// ******************************************************************************** //

	@Override
	public AjaxFramework getAjaxFramework() {

		return ajaxFramework;
	}

	@Override
	public HttpServletRequest getHttpRequest() {

		return httpRequest;
	}

	@Override
	public HttpServletResponse getHttpResponse() {

		return httpResponse;
	}

	@Override
	public HttpSession getHttpSession() {

		return httpRequest.getSession();
	}

	@Override
	public AjaxRequestMessage getRequestMessageOrThrow() {

		return requestMessage//
			.orElseThrow(() -> new AjaxHttpBadRequestError("Missing request message."));
	}

	@Override
	public Iterable<IDomFileUpload> getFileUploads() {

		return fileUploads;
	}

	// ******************************************************************************** //
	// * convenience functions                                                        * //
	// ******************************************************************************** //

	@Override
	public boolean isVerbose() {

		return httpRequest.getParameter("verbose") != null && isAdministrative();
	}

	@Override
	public boolean isDebug() {

		return httpRequest.getParameter("debug") != null && isAdministrative();
	}

	@Override
	public boolean isDocumentActionRequest() {

		return requestMessage.isPresent();
	}

	@Override
	public boolean isDocumentCreationRequest() {

		return isGetMethod() && !AjaxResourceUrl.haveResourceParameter(httpRequest);
	}

	@Override
	public boolean isResourceRequest() {

		return isGetMethod() && AjaxResourceUrl.haveResourceParameter(httpRequest);
	}

	@Override
	public boolean isFavoriteIconRequest() {

		return isGetMethod() && httpRequest.getRequestURI().toLowerCase().endsWith("/favicon.ico");
	}

	@Override
	public boolean isGZipSupported() {

		String acceptEncoding = httpRequest.getHeader("accept-encoding");
		return acceptEncoding != null && acceptEncoding.toLowerCase().contains("gzip");
	}

	@Override
	public boolean isDeflateSupported() {

		String acceptEncoding = httpRequest.getHeader("accept-encoding");
		return acceptEncoding != null && acceptEncoding.toLowerCase().contains("deflate");
	}

	private boolean isAdministrative() {

		return ajaxFramework.getAjaxStrategy().isAdministrative(httpRequest.getSession());
	}

	private boolean isGetMethod() {

		return httpRequest.getMethod().equals("GET");
	}
}
