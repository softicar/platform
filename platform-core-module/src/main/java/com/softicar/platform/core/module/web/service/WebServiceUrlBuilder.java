package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.network.url.UrlBuilder;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import java.util.UUID;

public class WebServiceUrlBuilder extends UrlBuilder {

	private static final String WEB_SERVICE_SERVLET_NAME = "service";

	public WebServiceUrlBuilder(UUID serviceUuid) {

		AGCoreModuleInstance coreModuleInstance = AGCoreModuleInstance.getInstance();

		setScheme(coreModuleInstance.getPortalProtocol());
		setDomainName(coreModuleInstance.getPortalHost());
		setPath(coreModuleInstance.getPortalApplicationPath() + WEB_SERVICE_SERVLET_NAME);
		addParameter(WebServiceServlet.getIdParameterName(), serviceUuid.toString());
	}

	public WebServiceUrlBuilder(Class<? extends IWebService> serviceClass) {

		this(EmfSourceCodeReferencePoints.getUuidOrThrow(serviceClass));
	}
}
