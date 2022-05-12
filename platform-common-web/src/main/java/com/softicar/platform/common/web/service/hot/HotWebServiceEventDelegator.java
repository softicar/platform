package com.softicar.platform.common.web.service.hot;

import com.softicar.platform.common.core.utils.CastUtils;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

class HotWebServiceEventDelegator implements HttpSessionListener {

	private final List<EventListener> listeners;

	public HotWebServiceEventDelegator() {

		this.listeners = new ArrayList<>();
	}

	public void clear() {

		listeners.clear();
	}

	public void addListener(EventListener listener) {

		this.listeners.add(listener);
	}

	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {

		listeners.forEach(listener -> CastUtils.tryCast(listener, HttpSessionListener.class).ifPresent(it -> it.sessionCreated(sessionEvent)));
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {

		listeners.forEach(listener -> CastUtils.tryCast(listener, HttpSessionListener.class).ifPresent(it -> it.sessionDestroyed(sessionEvent)));
	}
}
