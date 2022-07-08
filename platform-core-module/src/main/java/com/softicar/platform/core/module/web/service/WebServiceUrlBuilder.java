package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.common.network.url.UrlBuilder;
import com.softicar.platform.common.web.service.IWebServiceFactory;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import java.util.UUID;

public class WebServiceUrlBuilder extends UrlBuilder {

	private static final String WEB_SERVICE_SERVLET_NAME = "service";

	public WebServiceUrlBuilder(UUID serviceUuid) {

		AGCoreModuleInstance coreModuleInstance = AGCoreModuleInstance.getInstance();

		setScheme(coreModuleInstance.getPortalProtocol());
		setDomainName(coreModuleInstance.getPortalHost());
		setPath(coreModuleInstance.getPortalApplicationPath() + WEB_SERVICE_SERVLET_NAME);
		addParameter(WebServiceIdFetcher.getIdParameterName(), serviceUuid.toString());
	}

	public WebServiceUrlBuilder(Class<? extends IWebServiceFactory> factoryClass) {

		this(SourceCodeReferencePoints.getUuidOrThrow(factoryClass));
	}
}
