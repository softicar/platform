package com.softicar.platform.ajax.request;

import com.softicar.platform.ajax.framework.AjaxFramework;
import com.softicar.platform.ajax.request.parameters.IAjaxRequestParameters;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Interface to access the parameters of an AJAX request.
 *
 * @author Oliver Richers
 */
public interface IAjaxRequest extends HttpServletRequest {

	AjaxFramework getAjaxFramework();

	IAjaxRequestParameters getParameters();

	HttpSession getHttpSession();

	HttpServletResponse getServletResponse();

	boolean isDebug();

	boolean isTest();

	boolean isVerbose();

	boolean isGetMethod();

	boolean isPostMethod();

	boolean isResourceRequest();

	boolean isDocumentActionRequest();

	boolean isDocumentCreationRequest();

	boolean isFavoriteIconRequest();

	boolean isGZipSupported();

	boolean isDeflateSupported();
}
