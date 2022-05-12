package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.web.service.IWebService;
import com.softicar.platform.emf.source.code.reference.point.IEmfSourceCodeReferencePoint;

/**
 * A {@link IEmfSourceCodeReferencePoint} to create {@link IWebService}
 * instances.
 *
 * @author Oliver Richers
 */
public interface IWebServiceFactory extends IEmfSourceCodeReferencePoint {

	IWebService createInstance();
}
