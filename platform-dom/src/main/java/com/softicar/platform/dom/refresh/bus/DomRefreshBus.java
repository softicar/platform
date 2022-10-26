package com.softicar.platform.dom.refresh.bus;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.document.DomBody;
import com.softicar.platform.dom.elements.popup.compositor.DomParentNodeFinder;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

/**
 * Default implementation of {@link IDomRefreshBus}.
 *
 * @author Oliver Richers
 */
public class DomRefreshBus implements IDomRefreshBus {

	private final Map<IDomRefreshBusListener, Boolean> listeners;
	private final Map<IDomRefreshBusListener, Boolean> queue;
	private DomRefreshBusEvent refreshEvent;

	public DomRefreshBus() {

		this.listeners = new WeakHashMap<>();
		this.queue = new WeakHashMap<>();
		initializeNewEvent();
	}

	@Override
	public IDomRefreshBus addListener(IDomRefreshBusListener listener) {

		queue.put(listener, true);
		return this;
	}

	@Override
	public IDomRefreshBus setChanged(Object object) {

		refreshEvent.setChanged(object);
		return this;
	}

	@Override
	public IDomRefreshBus setAllChanged(Class<?> classes) {

		refreshEvent.setAllChanged(classes);
		return this;
	}

	@Override
	public IDomRefreshBus setAllChanged() {

		refreshEvent.setAllChanged();
		return this;
	}

	@Override
	public IDomRefreshBus submitEvent() {

		if (!refreshEvent.isEmpty()) {
			submitQueue();
			convertDanglingNodeListeners();
			listeners.keySet().forEach(listener -> listener.invalidateCachedData(refreshEvent));
			listeners.keySet().forEach(listener -> listener.refresh(refreshEvent));
			initializeNewEvent();
		}
		return this;
	}

	@Override
	public IDomRefreshBus discardEvent() {

		initializeNewEvent();
		return this;
	}

	private void initializeNewEvent() {

		this.refreshEvent = new DomRefreshBusEvent();
	}

	private void submitQueue() {

		for (Entry<IDomRefreshBusListener, Boolean> entry: queue.entrySet()) {
			if (entry.getValue()) {
				listeners.put(entry.getKey(), true);
			} else {
				listeners.remove(entry.getKey());
			}
		}
		queue.clear();
	}

	private void convertDanglingNodeListeners() {

		var bodyFinder = new DomParentNodeFinder<>(DomBody.class);
		for (var listener: new ArrayList<>(listeners.keySet())) {
			if (bodyFinder.findMostDistantParent(listener).isEmpty()) {
				listeners.remove(listener);
				listener//
					.getDomDocument()
					.getDeferredInitializationController()
					.addDeferredInitializer(listener, new ReappendedNodeInitializer(listener));
			}
		}
	}

	private class ReappendedNodeInitializer implements INullaryVoidFunction {

		private final IDomRefreshBusListener listener;

		public ReappendedNodeInitializer(IDomRefreshBusListener listener) {

			this.listener = listener;
		}

		@Override
		public void apply() {

			listener.refresh(new DomRefreshBusEvent().setAllChanged());
			listener.getDomDocument().getRefreshBus().addListener(listener);
		}
	}
}
