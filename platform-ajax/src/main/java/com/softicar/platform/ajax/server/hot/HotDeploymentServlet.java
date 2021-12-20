package com.softicar.platform.ajax.server.hot;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * A special {@link HttpServlet} wrapper to enable hot deployment.
 * <p>
 * This {@link HttpServlet} wraps another {@link HttpServlet} to enable hot
 * deployment, which means that always the latest version of the wrapped servlet
 * is used (when desired). For this to work, this class uses a new instance of
 * {@link HotDeploymentClassLoader} for each request.
 * <p>
 * The loading of the wrapped servlet is delegated to an instance of
 * {@link IHotDeploymentServletLoader}.
 *
 * @author Oliver Richers
 */
public class HotDeploymentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final IHotDeploymentServletLoader servletLoader;
	private HotDeploymentClassLoader classLoader;
	private HttpServlet servlet;

	/**
	 * Constructs this servlet using the specified
	 * {@link IHotDeploymentServletLoader}.
	 *
	 * @param servletLoader
	 *            the {@link IHotDeploymentServletLoader} to use
	 */
	public HotDeploymentServlet(IHotDeploymentServletLoader servletLoader) {

		this.servletLoader = servletLoader;
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {

		// reload the servlet if necessary
		synchronized (servletLoader) {
			if (servlet == null || servletLoader.isReloadNecessary(request)) {
				reloadServlet(request);
			}
		}

		// service the request
		try {
			servlet.service(request, response);
		} catch (IOException | ServletException exception) {
			throw new RuntimeException(exception);
		}
	}

	private void reloadServlet(HttpServletRequest request) {

		closeClassLoader();
		this.classLoader = new HotDeploymentClassLoader();
		this.servlet = servletLoader.loadServlet(request.getServletContext(), classLoader);
		Optional.ofNullable(request.getSession(false)).ifPresent(this::clearSession);
	}

	private void clearSession(HttpSession session) {

		Enumeration<String> enumeration = session.getAttributeNames();
		while (enumeration.hasMoreElements()) {
			session.removeAttribute(enumeration.nextElement());
		}
	}

	private void closeClassLoader() {

		if (this.classLoader != null) {
			try {
				this.classLoader.close();
				this.classLoader = null;
			} catch (IOException exception) {
				throw new RuntimeException(exception);
			}
		}
	}
}
