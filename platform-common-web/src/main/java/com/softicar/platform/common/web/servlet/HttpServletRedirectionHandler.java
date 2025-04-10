package com.softicar.platform.common.web.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.Callback;

class HttpServletRedirectionHandler extends AbstractHandler {

	private final String sourceUri;
	private final String targetUri;

	public HttpServletRedirectionHandler(String sourceUri, String targetUri) {

		this.sourceUri = sourceUri;
		this.targetUri = targetUri;
	}

	@Override
	public boolean handle(Request request, Response response, Callback callback) throws Exception {
		if (request.getHttpURI().getCanonicalPath().equals(sourceUri)) {
			response.setStatus(HttpServletResponse.SC_FOUND);
			response.getHeaders().add("Location", targetUri);
			callback.succeeded();
			return true;
		}
		return false;
	}
}
