package com.softicar.platform.core.module.web.library;

import com.softicar.platform.common.string.Trim;
import com.softicar.platform.common.web.service.IWebService;
import com.softicar.platform.common.web.service.IWebServiceFactory;

public class WebLibrary implements IWebServiceFactory {

	private final String root;

	public WebLibrary(String root) {

		this.root = Trim.trimRight(root, '/');
	}

	@Override
	public IWebService createInstance() {

		return new WebLibraryService(root);
	}
}
