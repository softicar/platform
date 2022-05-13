package com.softicar.platform.common.web.service.hot;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;

class HotWebServiceEventDelegator implements HttpSessionListener {

	private static final Collection<Class<?>> UNSUPPORTED_INTERFACES = List
		.of(//
			ServletContextAttributeListener.class,
			ServletRequestListener.class,
			ServletRequestAttributeListener.class,
			HttpSessionAttributeListener.class,
			HttpSessionIdListener.class);
	private final List<EventListener> listeners;

	public HotWebServiceEventDelegator() {

		this.listeners = new ArrayList<>();
	}

	public void clear() {

		listeners.clear();
	}

	public void addListener(EventListener listener) {

		for (Class<?> unsupportedInterface: UNSUPPORTED_INTERFACES) {
			if (unsupportedInterface.isInstance(listener)) {
				throw new SofticarDeveloperException("Listeners of type %s are not yet supported.", unsupportedInterface.getSimpleName());
			}
		}

		this.listeners.add(listener);
	}

	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {

		filterListeners(HttpSessionListener.class).forEach(it -> it.sessionCreated(sessionEvent));
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {

		filterListeners(HttpSessionListener.class).forEach(it -> it.sessionDestroyed(sessionEvent));
	}

	private <T> Collection<T> filterListeners(Class<T> listenerClass) {

		return listeners
			.stream()//
			.filter(listenerClass::isInstance)
			.map(listenerClass::cast)
			.collect(Collectors.toList());
	}
}
