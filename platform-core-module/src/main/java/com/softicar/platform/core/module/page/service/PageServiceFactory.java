package com.softicar.platform.core.module.page.service;

import com.softicar.platform.common.web.service.IWebService;
import com.softicar.platform.core.module.web.service.IWebServiceFactory;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;

@EmfSourceCodeReferencePointUuid("95cf1a1b-c12e-4594-9d20-783988fe32b9")
public class PageServiceFactory implements IWebServiceFactory {

	@Override
	public IWebService createInstance() {

		return new PageService();
	}
}
