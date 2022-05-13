package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.web.service.IWebService;
import com.softicar.platform.emf.source.code.reference.point.IEmfSourceCodeReferencePoint;

/**
 * A {@link IEmfSourceCodeReferencePoint} to create {@link IWebService}
 * instances.
 * <p>
 * TODO Extract this class (PLAT-861).
 *
 * @author Oliver Richers
 */
public interface IWebServiceFactory extends IEmfSourceCodeReferencePoint {

	/**
	 * Creates a new instance of {@link IWebService}.
	 *
	 * @return the new instance (never <i>null</i>)
	 */
	IWebService createInstance();
}
