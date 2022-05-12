package com.softicar.platform.common.servlet.hot;

import com.softicar.platform.common.servlet.service.IHttpService;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Optional;
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
 * {@link HotHttpServiceClassLoader} for each request.
 * <p>
 * The loading of the wrapped servlet is delegated to an instance of
 * {@link IHotHttpServiceLoader}.
 *
 * @author Oliver Richers
 */
public class HotHttpServiceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final IHotHttpServiceLoader serviceLoader;
	private HotHttpServiceClassLoader classLoader;
	private IHttpService service;

	/**
	 * Constructs this {@link HotHttpServiceServlet} using the specified
	 * {@link IHotHttpServiceLoader}.
	 *
	 * @param serviceLoader
	 *            the {@link IHotHttpServiceLoader} to use
	 */
	public HotHttpServiceServlet(IHotHttpServiceLoader serviceLoader) {

		this.serviceLoader = serviceLoader;
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {

		// reload the servlet if necessary
		synchronized (serviceLoader) {
			if (service == null || serviceLoader.isReloadNecessary(request)) {
				reloadServlet(request);
			}
		}

		// service the request
		service.service(request, response);
	}

	private void reloadServlet(HttpServletRequest request) {

		closeClassLoader();
		this.classLoader = new HotHttpServiceClassLoader();
		this.classLoader.addIgnoredClass(IHttpService.class);
		this.service = serviceLoader.loadService(classLoader);
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
