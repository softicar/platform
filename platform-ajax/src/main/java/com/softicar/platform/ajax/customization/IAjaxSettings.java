package com.softicar.platform.ajax.customization;

import com.softicar.platform.ajax.document.AjaxDocument;
import com.softicar.platform.ajax.framework.IAjaxFramework;

/**
 * This interface provides read-only access to the settings of the
 * {@link IAjaxFramework}.
 *
 * @author Oliver Richers
 */
public interface IAjaxSettings {

	/**
	 * Returns the maximum number of active nodes per {@link AjaxDocument}.
	 *
	 * @return the maximum number of active nodes
	 */
	int getMaximumExistingNodeCount();

	/**
	 * Defines the maximum number of active nodes per {@link AjaxDocument}.
	 *
	 * @param maximumActiveNodeCount
	 *            maximum number of active nodes (must be non-negative)
	 */
	void setMaximumExistingNodeCount(int maximumActiveNodeCount);

	/**
	 * Returns the maximum number of seconds that an {@link AjaxDocument} may be
	 * idle.
	 * <p>
	 * If an {@link AjaxDocument} is not accessed for a longer period of time,
	 * it will be removed from the user session.
	 *
	 * @return the maximum idling time for {@link AjaxDocument}
	 */
	int getMaximumDocumentIdleSeconds();

	/**
	 * Defines the value returned by {@link #getMaximumDocumentIdleSeconds()}.
	 */
	IAjaxSettings setMaximumDocumentIdleSeconds(int maximumDocumentIdleSeconds);

	/**
	 * Returns the maximum number of {@link AjaxDocument} instances per user
	 * session.
	 * <p>
	 * If more {@link AjaxDocument} instances than specified by this value are
	 * opened, the oldest {@link AjaxDocument} will be removed from the session.
	 *
	 * @return maximum number of {@link AjaxDocument} instances allowed per user
	 *         session
	 */
	int getMaximumDocumentsPerSession();

	/**
	 * Defines the value returned by {@link #getMaximumDocumentsPerSession()}.
	 */
	IAjaxSettings setMaximumDocumentsPerSession(int maximumDocumentsPerSesssion);

	/**
	 * Returns the maximum number of seconds that a <b>hashed</b> resource may
	 * be cached by the client.
	 *
	 * @return the maximum number of seconds
	 * @see #setMaximumAgeForCachedResources(int)
	 */
	int getMaximumAgeForCachedResources();

	/**
	 * Defines the maximum number of seconds that a <b>hashed</b> resource may
	 * be cached by the client.
	 * <p>
	 * This is the value used for the <i>max-age</i> attribute of the HTTP
	 * <i>Cache-Control</i> header when sending resources to the client.
	 * <p>
	 * This value is only used for <i>hashed</i> resources. Caching for name or
	 * ID based resource URLs is disabled.
	 *
	 * @param maximumAge
	 *            the maximum number of seconds
	 */
	void setMaximumAgeForCachedResources(int maximumAge);
}
