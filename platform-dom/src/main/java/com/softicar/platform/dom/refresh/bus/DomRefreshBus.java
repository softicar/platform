package com.softicar.platform.dom.refresh.bus;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
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
			convertListenersNotAppendedToDocument();
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

	private void convertListenersNotAppendedToDocument() {

		for (var listener: new ArrayList<>(listeners.keySet())) {
			if (!listener.isAppended()) {
				listeners.remove(listener);
				listener//
					.getDomDocument()
					.getDeferredInitializationController()
					.addDeferredInitializer(listener, new ReappendedListenerInitializer(listener));
			}
		}
	}

	private class ReappendedListenerInitializer implements INullaryVoidFunction {

		private final IDomRefreshBusListener listener;

		public ReappendedListenerInitializer(IDomRefreshBusListener listener) {

			this.listener = listener;
		}

		@Override
		public void apply() {

			listener.refresh(new DomRefreshBusEvent().setAllChanged());
			listener.getDomDocument().getRefreshBus().addListener(listener);
		}
	}
}
