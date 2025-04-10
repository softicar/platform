package com.softicar.platform.common.web.service.hot;

import com.softicar.platform.common.web.service.IWebService;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Optional;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * A {@link IWebService} wrapper to enable hot code deployment.
 * <p>
 * This {@link IWebService} serves another {@link IWebService} with hot code
 * deployment, which means that changes to the wrapped {@link IWebService} code
 * can be made available without restarting the server.
 * <p>
 * The loading and reloading of the {@link IWebService} code is managed by an
 * instance of {@link IHotWebServiceLoader}.
 *
 * @author Oliver Richers
 */
public class HotWebServiceWrapper implements IWebService {

	private final IHotWebServiceLoader serviceLoader;
	private HotWebServiceClassLoader classLoader;
	private IWebService service;

	/**
	 * Constructs this {@link HotWebServiceWrapper} using the specified
	 * {@link IHotWebServiceLoader}.
	 *
	 * @param serviceLoader
	 *            the {@link IHotWebServiceLoader} to use (never <i>null</i>)
	 */
	public HotWebServiceWrapper(IHotWebServiceLoader serviceLoader) {

		this.serviceLoader = Objects.requireNonNull(serviceLoader);
		this.classLoader = null;
		this.service = null;
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {

		reloadServiceIfNecessary(request);

		service.service(request, response);
	}

	@Override
	public void destroy() {

		destroyService();
		closeClassLoader();
	}

	private void reloadServiceIfNecessary(HttpServletRequest request) {

		synchronized (serviceLoader) {
			if (service == null || serviceLoader.isReloadNecessary(request)) {
				reloadServlet(request);
			}
		}
	}

	private void reloadServlet(HttpServletRequest request) {

		destroyService();
		closeClassLoader();

		this.classLoader = new HotWebServiceClassLoader();
		this.classLoader.addIgnoredClass(IWebService.class);
		this.service = serviceLoader.loadService(classLoader);
		this.service.initialize();

		Optional.ofNullable(request.getSession(false)).ifPresent(this::clearSession);
	}

	private void clearSession(HttpSession session) {

		Enumeration<String> enumeration = session.getAttributeNames();
		while (enumeration.hasMoreElements()) {
			session.removeAttribute(enumeration.nextElement());
		}
	}

	private void destroyService() {

		if (service != null) {
			this.service.destroy();
			this.service = null;
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
