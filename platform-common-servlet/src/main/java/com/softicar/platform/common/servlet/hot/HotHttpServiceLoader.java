package com.softicar.platform.common.servlet.hot;

import com.softicar.platform.common.core.interfaces.Consumers;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.servlet.service.IHttpService;
import javax.servlet.http.HttpServletRequest;

/**
 * Default implementation of {@link IHotHttpServiceLoader} for
 * {@link HotHttpServer}.
 * <p>
 * The {@link IHttpService} class is only reloaded when the session is
 * invalidated.
 *
 * @author Oliver Richers
 */
public class HotHttpServiceLoader implements IHotHttpServiceLoader {

	private final Class<? extends IHttpService> serviceClass;

	public HotHttpServiceLoader(Class<? extends IHttpService> serviceClass) {

		this.serviceClass = serviceClass;
	}

	@Override
	public boolean isReloadNecessary(HttpServletRequest request) {

		return request.getSession(false) == null;
	}

	@Override
	public IHttpService loadService(ClassLoader classLoader) {

		try {
			Class<?> serviceClass = loadServiceClass(classLoader);
			IHttpService service = (IHttpService) serviceClass.getConstructor().newInstance();
			service.initialize(Consumers.noOperation());
			return service;
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
