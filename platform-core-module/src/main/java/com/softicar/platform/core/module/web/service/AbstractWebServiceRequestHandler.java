package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.common.io.file.name.FilenameCleaner;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.web.service.IWebService;
import com.softicar.platform.core.module.ajax.login.SofticarAjaxLoginExecutor;
import com.softicar.platform.core.module.ajax.session.SofticarAjaxSession;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.web.service.html.AbstractHtmlServiceRequestHandler;
import com.softicar.platform.core.module.web.servlet.HttpServletRequests;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Base class for {@link IWebService} request handlers.
 * <p>
 * If you need to verify user permissions in your service, you should inherit
 * your service from this class. This class will ensure that the user has been
 * authenticated properly.
 * <p>
 * If your service request handler does only speak HTML, you should derive from
 * {@link AbstractHtmlServiceRequestHandler}. That class provides some auxiliary
 * methods for such print HTML code.
 *
 * @author Oliver Richers
 */
public abstract class AbstractWebServiceRequestHandler {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private SofticarAjaxSession ajaxSession;

	protected abstract void service() throws Exception;

	public final void service(HttpServletRequest request, HttpServletResponse response) {

		this.request = request;
		this.response = response;

		if (authenticate()) {
			AGUser user = getUser();
			CurrentUser.set(user);
			CurrentLocale.set(user.getLocale());

			// execute service
			try {
				service();
			} catch (RuntimeException exception) {
				// don't wrap runtime exceptions again
				exception.printStackTrace();
				throw exception;
			} catch (Exception exception) {
				// wrap checked exceptions into a RuntimeException
				exception.printStackTrace();
				throw new RuntimeException(exception);
			} catch (Throwable throwable) {
				// print errors
				throwable.printStackTrace();
				throw throwable;
			}
		}
	}

	/**
	 * Executes user authentication.
	 * <p>
	 * You can use the default implementation or override this method in your
	 * web service class.
	 *
	 * @return true if service handling can start, false otherwise
	 */
	protected boolean authenticate() {

		this.ajaxSession = SofticarAjaxSession.getInstance(request.getSession()).orElse(null);

		if (this.ajaxSession == null) {
			this.ajaxSession = new SofticarAjaxLoginExecutor(request, response).executeLogin().orElse(null);
		}

		return this.ajaxSession != null;
	}

	protected final PrintWriter startPlainTextOutput() {

		return startTextOutput(MimeType.TEXT_PLAIN.getIdentifier());
	}

	protected final PrintWriter startHtmlOutput() {

		return startTextOutput(MimeType.TEXT_HTML.getIdentifier());
	}

	protected final PrintWriter startTextOutput(String type) {

		try {
			response.setContentType(type + "; charset=UTF-8");
			return response.getWriter();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	protected final ServletOutputStream startBinaryOutput() {

		return startBinaryOutput(MimeType.APPLICATION_OCTET_STREAM.getIdentifier());
	}

	protected final ServletOutputStream startBinaryOutput(String contentType) {

		try {
			response.setContentType(contentType);
			return response.getOutputStream();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	protected final void setContentDisposition(String filename) {

		setContentDisposition(filename, "attachment");
	}

	protected final void setContentDisposition(String filename, String streamType) {

		String disposition = String.format("%s; filename=%s", streamType, new FilenameCleaner(filename).getCleanFilename());
		response.setHeader("Content-Disposition", disposition);
	}

	protected final ServletOutputStream startBinaryOutput(String contentType, String fileName) {

		setContentDisposition(fileName);
		return startBinaryOutput(contentType);
	}

	protected final ServletOutputStream startBinaryOutput(String contentType, String streamType, String fileName) {

		setContentDisposition(fileName, streamType);
		return startBinaryOutput(contentType);
	}

	protected final HttpServletResponse getResponse() {

		return response;
	}

	protected final AGUser getUser() {

		return ajaxSession.getUser();
	}

	protected final HttpSession getSession() {

		return request.getSession();
	}

	protected final HttpServletRequest getRequest() {

		return request;
	}

	protected final String getRequestMethod() {

		return request.getMethod();
	}

	/**
	 * Returns the request parameter value with the specified name or null.
	 *
	 * @param name
	 *            the name of the parameter
	 * @return the value or null
	 */
	protected final String getStringParameter(String name) {

		return request.getParameter(name);
	}

	protected final String[] getStringParameters(String name) {

		return request.getParameterValues(name);
	}

	/**
	 * Returns the request parameter value with the specified name or throws an
	 * exception.
	 *
	 * @param name
	 *            the name of the parameter
	 * @return the value (never null)
	 * @throw {@link SofticarException} if the specified parameter is missing
	 */
	protected final String getRequiredStringParameter(String name) {

		return HttpServletRequests.getParameter(request, name);
	}

	/**
	 * Returns the request parameter int value with the specified name or throws
	 * an exception.
	 *
	 * @param name
	 *            the name of the parameter
	 * @return the int value
	 * @throw {@link SofticarException} if the specified parameter is missing
	 * @throw {@link NumberFormatException} if the specified value is not an int
	 */
	protected final int getRequiredIntParameter(String name) {

		return HttpServletRequests.getIntParameter(request, name);
	}
}
