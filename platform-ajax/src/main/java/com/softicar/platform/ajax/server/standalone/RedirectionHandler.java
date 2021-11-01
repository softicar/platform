package com.softicar.platform.ajax.server.standalone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

class RedirectionHandler extends AbstractHandler {

	private final String sourceUri;
	private final String targetUri;

	public RedirectionHandler(String sourceUri, String targetUri) {

		this.sourceUri = sourceUri;
		this.targetUri = targetUri;
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) {

		if (request.getRequestURI().equals(sourceUri)) {
			response.setStatus(HttpServletResponse.SC_FOUND);
			response.setHeader("Location", targetUri);
			baseRequest.setHandled(true);
		}
	}
}
