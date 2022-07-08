package com.softicar.platform.common.web.service;

import com.softicar.platform.common.code.reference.point.ISourceCodeReferencePoint;

/**
 * A {@link ISourceCodeReferencePoint} to create {@link IWebService} instances.
 *
 * @author Oliver Richers
 */
public interface IWebServiceFactory extends ISourceCodeReferencePoint {

	/**
	 * Creates a new instance of {@link IWebService}.
	 *
	 * @return the new instance (never <i>null</i>)
	 */
	IWebService createInstance();
}
