package com.softicar.platform.common.web.service.hot;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.web.service.IWebService;
import java.util.Objects;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Default implementation of {@link IHotWebServiceLoader}.
 * <p>
 * The {@link IWebService} class is reloaded when no {@link HttpSession} exists.
 *
 * @author Oliver Richers
 */
public class HotWebServiceLoader implements IHotWebServiceLoader {

	private final Class<? extends IWebService> serviceClass;

	public HotWebServiceLoader(Class<? extends IWebService> serviceClass) {

		this.serviceClass = Objects.requireNonNull(serviceClass);
	}

	@Override
	public boolean isReloadNecessary(HttpServletRequest request) {

		return request.getSession(false) == null;
	}

	@Override
	public IWebService loadService(ClassLoader classLoader) {

		try {
			Class<?> serviceClass = loadServiceClass(classLoader);
			return (IWebService) serviceClass.getConstructor().newInstance();
		} catch (ReflectiveOperationException exception) {
			throw new RuntimeException(exception);
		}
	}

	protected Class<?> loadServiceClass(ClassLoader classLoader) {

		return loadClass(classLoader, serviceClass);
	}

	protected Class<?> loadClass(ClassLoader classLoader, Class<?> classToLoad) {

		try {
			return CastUtils.cast(classLoader.loadClass(classToLoad.getCanonicalName()));
		} catch (ClassNotFoundException exception) {
			throw new RuntimeException(exception);
		}
	}
}
