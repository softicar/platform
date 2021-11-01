package com.softicar.platform.ajax.server.hot;

import com.softicar.platform.common.core.utils.CastUtils;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * Default implementation of {@link IHotDeploymentServletLoader} for
 * {@link HotDeploymentServletServer}.
 * <p>
 * The servlet class is only reloaded when the session is invalidated.
 *
 * @author Oliver Richers
 */
class HotDeploymentServletLoader implements IHotDeploymentServletLoader {

	private final Class<? extends HttpServlet> servletClass;

	public HotDeploymentServletLoader(Class<? extends HttpServlet> servletClass) {

		this.servletClass = servletClass;
	}

	@Override
	public boolean isReloadNecessary(HttpServletRequest request) {

		return request.getSession(false) == null;
	}

	@Override
	public HttpServlet loadServlet(ServletContext servletContext, ClassLoader classLoader) {

		try {
			Class<?> hotServletClass = getServletClass(classLoader);
			HttpServlet hotServlet = CastUtils.cast(hotServletClass.getConstructor().newInstance());
			hotServlet.init(new HotDeploymentServletConfig(servletContext));
			return hotServlet;
		} catch (ReflectiveOperationException | ServletException exception) {
			throw new RuntimeException(exception);
		}
	}

	private Class<?> getServletClass(ClassLoader classLoader) {

		return loadClass(classLoader, servletClass);
	}

	private <T> Class<T> loadClass(ClassLoader classLoader, Class<T> classToLoad) {

		try {
			return CastUtils.cast(classLoader.loadClass(classToLoad.getCanonicalName()));
		} catch (ClassNotFoundException exception) {
			throw new RuntimeException(exception);
		}
	}
}
