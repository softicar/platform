package com.softicar.platform.ajax.customization;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.export.IAjaxExportBuffer;
import com.softicar.platform.ajax.framework.AjaxFramework;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.node.IDomNode;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Customization point for {@link AjaxFramework}.
 *
 * @author Oliver Richers
 * @see AjaxFramework
 * @see AbstractAjaxStrategy
 */
public interface IAjaxStrategy {

	// -------------------- documents -------------------- //

	/**
	 * Builds the given {@link IAjaxDocument} instance by appending respective
	 * {@link IDomNode} instances.
	 *
	 * @param document
	 *            an empty {@link IAjaxDocument} instance to build (never
	 *            <i>null</i>)
	 */
	void buildDocument(IAjaxDocument document);

	/**
	 * Called before an {@link IAjaxRequest} for an {@link IAjaxDocument} is
	 * handled.
	 *
	 * @param document
	 *            the {@link IAjaxDocument} instance (never <i>null</i>)
	 * @param request
	 *            the {@link IAjaxRequest} instance (never <i>null</i>)
	 */
	void beforeDocumentRequestHandling(IAjaxDocument document, IAjaxRequest request);

	// -------------------- exports -------------------- //

	/**
	 * Creates a new {@link IAjaxExportBuffer} for data exports.
	 *
	 * @return a new {@link IAjaxExportBuffer} (never <i>null</i>)
	 */
	IAjaxExportBuffer createExportBuffer();

	// -------------------- resources -------------------- //

	/**
	 * Returns the URL suffix to append to all resource requests.
	 * <p>
	 * Depending on the servlet implementation, it may be necessary to supply
	 * additional HTTP query parameters on every request. If no additional
	 * suffix is necessary, this method should return an empty string, not
	 * <i>null</i>.
	 *
	 * @return the URL suffix to use when requesting resources (never null)
	 */
	String getResourceUrlSuffix();

	/**
	 * Returns an {@link IResource} that represents the site's favicon (favorite
	 * icon; cf. <a href="https://en.wikipedia.org/wiki/Favicon">Wikipedia</a>).
	 *
	 * @return the {@link IResource} of the favicon
	 */
	Optional<IResource> getFaviconResource();

	// -------------------- sessions -------------------- //

	/**
	 * Tests whether the given {@link HttpSession} is a super-user session.
	 * <p>
	 * Some features like debug mode, etc. are only available to super-user
	 * sessions.
	 *
	 * @param session
	 *            the {@link HttpSession} to test (never <i>null</i>)
	 * @return <i>true</i> if the given {@link HttpSession} is a super-user
	 *         session; <i>false</i> otherwise
	 */
	boolean isSuperUser(HttpSession session);

	// -------------------- logging -------------------- //

	/**
	 * Logs the given {@link Throwable} caused by the specified
	 * {@link HttpServletRequest}.
	 *
	 * @param throwable
	 *            the raised {@link Throwable} (never <i>null</i>)
	 * @param request
	 *            the causing {@link HttpServletRequest} (never <i>null</i>)
	 */
	void logException(Throwable throwable, HttpServletRequest request);
}
