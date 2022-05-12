package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.servlet.service.IHttpService;
import com.softicar.platform.emf.source.code.reference.point.IEmfSourceCodeReferencePoint;

/**
 * A {@link IEmfSourceCodeReferencePoint} to create {@link IHttpService}
 * instances.
 *
 * @author Oliver Richers
 */
public interface IWebServiceFactory extends IEmfSourceCodeReferencePoint {

	IHttpService createInstance();
}
