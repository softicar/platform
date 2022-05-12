package com.softicar.platform.ajax.server;

import com.softicar.platform.ajax.resource.AjaxResourceUrl;
import com.softicar.platform.common.core.interfaces.Consumers;
import com.softicar.platform.common.servlet.hot.HotHttpServiceLoader;
import com.softicar.platform.common.servlet.service.IHttpService;
import com.softicar.platform.dom.node.IDomNode;
import java.lang.reflect.Constructor;
import javax.servlet.http.HttpServletRequest;

class AjaxDomNodeServiceLoader extends HotHttpServiceLoader {

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
	public IHttpService loadService(ClassLoader classLoader) {

		try {
			Class<?> serviceClass = loadServiceClass(classLoader);
			Class<?> nodeClass = loadNodeClass(classLoader);

			Constructor<?> constructor = serviceClass.getConstructor(nodeClass.getClass());
			constructor.setAccessible(true);

			IHttpService service = (IHttpService) constructor.newInstance(nodeClass);
			service.initialize(Consumers.noOperation());
			return service;
		} catch (ReflectiveOperationException exception) {
			throw new RuntimeException(exception);
		}
	}

	private Class<?> loadNodeClass(ClassLoader classLoader) {

		return loadClass(classLoader, nodeClass);
	}
}
