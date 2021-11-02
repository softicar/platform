package com.softicar.platform.ajax.server.development;

import com.softicar.platform.ajax.resource.AjaxResourceUrl;
import com.softicar.platform.ajax.server.hot.IHotDeploymentServletLoader;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.dom.node.IDomNode;
import java.lang.reflect.Constructor;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

class AjaxDevelopmentServerServletLoader implements IHotDeploymentServletLoader {

	private final Class<? extends HttpServlet> servletClass;
	private final Class<? extends IDomNode> nodeClass;

	public AjaxDevelopmentServerServletLoader(Class<? extends IDomNode> nodeClass) {

		this.servletClass = AjaxDevelopmentServerServlet.class;
		this.nodeClass = nodeClass;
	}

	@Override
	public boolean isReloadNecessary(HttpServletRequest request) {

		return request.getMethod().equals("GET") && !AjaxResourceUrl.haveResourceParameter(request);
	}

	@Override
	public HttpServlet loadServlet(ServletContext servletContext, ClassLoader classLoader) {

		try {
			Class<?> servletClass = getServletClass(classLoader);
			Class<?> nodeClass = getNodeClass(classLoader);

			Constructor<?> constructor = servletClass.getConstructor(nodeClass.getClass());
			constructor.setAccessible(true);
			return CastUtils.cast(constructor.newInstance(nodeClass));
		} catch (ReflectiveOperationException exception) {
			throw new RuntimeException(exception);
		}
	}

	private Class<?> getServletClass(ClassLoader classLoader) {

		return loadClass(classLoader, servletClass);
	}

	private Class<?> getNodeClass(ClassLoader classLoader) {

		return loadClass(classLoader, nodeClass);
	}

	private <T> Class<T> loadClass(ClassLoader classLoader, Class<T> classToLoad) {

		try {
			return CastUtils.cast(classLoader.loadClass(classToLoad.getName()));
		} catch (ClassNotFoundException exception) {
			throw new RuntimeException(exception);
		}
	}
}
