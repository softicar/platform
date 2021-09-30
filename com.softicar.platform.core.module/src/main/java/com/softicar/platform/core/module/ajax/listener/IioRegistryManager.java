package com.softicar.platform.core.module.ajax.listener;

import com.softicar.platform.common.container.iterable.IteratorIterable;
import com.softicar.platform.common.core.logging.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.spi.IIORegistry;
import javax.imageio.spi.ServiceRegistry;

/**
 * This class takes care of issues with the {@link IIORegistry} in context of a
 * web application.
 * <p>
 * The original problem is caused by the <i>com.github.jaiimageio</i> library,
 * registering service provider into the global {@link IIORegistry} instance.
 * This class aims to properly register and deregister the services when a web
 * application context is initialized or destroyed.
 *
 * @author Oliver Richers
 */
public class IioRegistryManager {

	public void registerProviders() {

		Log.finfo("%s: Updating service providers of %s.", getClass().getSimpleName(), IIORegistry.class.getSimpleName());

		IIORegistry.getDefaultInstance().registerApplicationClasspathSpis();
	}

	public void deregisterProviders() {

		Log.finfo("%s: Removing service providers from %s.", getClass().getSimpleName(), IIORegistry.class.getSimpleName());

		copyToList(IIORegistry.getDefaultInstance().getCategories()).forEach(this::cleanup);
	}

	private <T> void cleanup(Class<T> category) {

		for (T provider: copyToList(getServiceProviders(category))) {
			Log.finfo("%s: Deregister service provider class: %s", getClass().getSimpleName(), provider.getClass());
			IIORegistry.getDefaultInstance().deregisterServiceProvider(provider);
		}
	}

	// important: we need to copy the elements to avoid concurrent modification exceptions
	private <T> List<T> copyToList(Iterator<T> iterator) {

		List<T> list = new ArrayList<>();
		for (T element: new IteratorIterable<>(iterator)) {
			list.add(element);
		}
		return list;
	}

	private <T> Iterator<T> getServiceProviders(Class<T> category) {

		return IIORegistry.getDefaultInstance().getServiceProviders(category, new ClassLoaderFilter(), false);
	}

	private static class ClassLoaderFilter implements ServiceRegistry.Filter {

		private final ClassLoader classLoader;

		public ClassLoaderFilter() {

			this.classLoader = Thread.currentThread().getContextClassLoader();
		}

		@Override
		public boolean filter(Object provider) {

			return provider.getClass().getClassLoader() == classLoader;
		}
	}
}
