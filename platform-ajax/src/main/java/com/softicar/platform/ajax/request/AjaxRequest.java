package com.softicar.platform.ajax.request;

import com.softicar.platform.ajax.framework.AjaxFramework;
import com.softicar.platform.ajax.request.parameters.AjaxRequestParametersFetcher;
import com.softicar.platform.ajax.request.parameters.IAjaxRequestParameters;
import com.softicar.platform.ajax.resource.AjaxResourceUrl;
import com.softicar.platform.ajax.service.AbstractAjaxService;
import com.softicar.platform.common.container.iterator.IteratorEnumeration;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.string.Imploder;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Represents an AJAX request that must be executed by an instance of
 * {@link AbstractAjaxService}.
 *
 * @author Oliver Richers
 */
public final class AjaxRequest extends HttpServletRequestWrapper implements IAjaxRequest {

	private final AjaxFramework ajaxFramework;
	private final HttpServletResponse response;
	private final HttpSession httpSession;
	private final IAjaxRequestParameters parameters;

	/**
	 * Creates and initializes the AJAX request object.
	 *
	 * @param request
	 * @param response
	 */
	public AjaxRequest(AjaxFramework ajaxFramework, HttpServletRequest request, HttpServletResponse response) {

		super(request);

		this.ajaxFramework = ajaxFramework;
		this.response = response;
		this.httpSession = request.getSession();
		this.parameters = new AjaxRequestParametersFetcher(request).fetch();

		logParameters();
	}

	private void logParameters() {

		if (isDebug()) {
			Log.finfo("<parameters>");
			for (Map.Entry<String, String[]> parameter: getParameterMap().entrySet()) {
				Log.finfo("%s: %s", parameter.getKey(), Imploder.implodeQuoted(parameter.getValue(), ", ", "'"));
			}
			Log.finfo("</parameters>");
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
	public HttpSession getHttpSession() {

		return httpSession;
	}

	@Override
	public HttpServletResponse getServletResponse() {

		return response;
	}

	// ******************************************************************************** //
	// * parameter functions                                                          * //
	// ******************************************************************************** //

	@Override
	public IAjaxRequestParameters getParameters() {

		return parameters;
	}

	@Override
	public Map<String, String[]> getParameterMap() {

		return parameters.getMap();
	}

	@Override
	public String getParameter(String name) {

		String[] values = parameters.get(name);
		return values != null && values.length > 0? values[0] : null;
	}

	@Override
	public Enumeration<String> getParameterNames() {

		return new IteratorEnumeration<>(parameters.getMap().keySet().iterator());
	}

	@Override
	public String[] getParameterValues(String name) {

		return parameters.get(name);
	}

	// ******************************************************************************** //
	// * convenience functions                                                        * //
	// ******************************************************************************** //

	@Override
	public ServletInputStream getInputStream() {

		try {
			return super.getInputStream();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public boolean isVerbose() {

		return getParameter("verbose") != null && isAdministrative();
	}

	@Override
	public boolean isDebug() {

		return getParameter("debug") != null && isAdministrative();
	}

	@Override
	public boolean isTest() {

		return getParameter("test") != null && isAdministrative();
	}

	@Override
	public boolean isGetMethod() {

		return getMethod().equals("GET");
	}

	@Override
	public boolean isPostMethod() {

		return getMethod().equals("POST");
	}

	@Override
	public boolean isResourceRequest() {

		return isGetMethod() && AjaxResourceUrl.haveResourceParameter(this);
	}

	@Override
	public boolean isDocumentActionRequest() {

		return isPostMethod() && getParameter("a") != null;
	}

	@Override
	public boolean isDocumentCreationRequest() {

		return isGetMethod() && !AjaxResourceUrl.haveResourceParameter(this);
	}

	@Override
	public boolean isFavoriteIconRequest() {

		return isGetMethod() && getRequestURI().toLowerCase().endsWith("/favicon.ico");
	}

	@Override
	public boolean isGZipSupported() {

		String acceptEncoding = getHeader("accept-encoding");
		return acceptEncoding != null && acceptEncoding.toLowerCase().contains("gzip");
	}

	@Override
	public boolean isDeflateSupported() {

		String acceptEncoding = getHeader("accept-encoding");
		return acceptEncoding != null && acceptEncoding.toLowerCase().contains("deflate");
	}

	private boolean isAdministrative() {

		return ajaxFramework.getAjaxStrategy().isAdministrative(httpSession);
	}
}
