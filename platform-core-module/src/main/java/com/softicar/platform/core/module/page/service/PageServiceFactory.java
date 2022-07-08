package com.softicar.platform.core.module.page.service;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.web.service.IWebService;
import com.softicar.platform.common.web.service.IWebServiceFactory;

@SourceCodeReferencePointUuid("95cf1a1b-c12e-4594-9d20-783988fe32b9")
public class PageServiceFactory implements IWebServiceFactory {

	@Override
	public IWebService createInstance() {

		return new PageService();
	}
}
