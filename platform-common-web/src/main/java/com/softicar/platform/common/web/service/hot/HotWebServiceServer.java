package com.softicar.platform.common.web.service.hot;

import com.softicar.platform.common.web.service.IWebService;
import com.softicar.platform.common.web.service.WebServiceServer;

/**
 * A server for {@link IWebService} instances with hot code deployment.
 *
 * @author Oliver Richers
 */
public class HotWebServiceServer extends WebServiceServer {

	public HotWebServiceServer(Class<? extends IWebService> serviceClass) {

		this(new HotWebServiceLoader(serviceClass));
	}

	public HotWebServiceServer(IHotWebServiceLoader serviceLoader) {

		super(new HotWebServiceWrapper(serviceLoader));
	}
}
