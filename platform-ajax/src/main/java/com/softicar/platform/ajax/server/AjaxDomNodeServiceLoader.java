package com.softicar.platform.ajax.server;

import com.softicar.platform.ajax.resource.AjaxResourceUrl;
import com.softicar.platform.common.web.service.IWebService;
import com.softicar.platform.common.web.service.hot.HotWebServiceLoader;
import com.softicar.platform.dom.node.IDomNode;
import java.lang.reflect.Constructor;
import javax.servlet.http.HttpServletRequest;

class AjaxDomNodeServiceLoader extends HotWebServiceLoader {

	private final Class<? extends IDomNode> nodeClass;

	public AjaxDomNodeServiceLoader(Class<? extends IDomNode> nodeClass) {

		super(AjaxDomNodeService.class);
		this.nodeClass = nodeClass;
	}

	@Override
	public boolean isReloadNecessary(HttpServletRequest request) {

		return request.getMethod().equals("GET") && !AjaxResourceUrl.haveResourceParameter(request);
	}

	@Override
	public IWebService loadService(ClassLoader classLoader) {

		try {
			Class<?> serviceClass = loadServiceClass(classLoader);
			Class<?> nodeClass = loadNodeClass(classLoader);

			Constructor<?> constructor = serviceClass.getConstructor(nodeClass.getClass());
			constructor.setAccessible(true);

			return (IWebService) constructor.newInstance(nodeClass);
		} catch (ReflectiveOperationException exception) {
			throw new RuntimeException(exception);
		}
	}

	private Class<?> loadNodeClass(ClassLoader classLoader) {

		return loadClass(classLoader, nodeClass);
	}
}
