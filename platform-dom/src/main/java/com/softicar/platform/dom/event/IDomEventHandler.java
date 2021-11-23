package com.softicar.platform.dom.event;

/**
 * Generic interface to receive {@link IDomEvent} notifications.
 * <p>
 * A simpler API that automatically enables listening to events is available
 * through {@link IDomAutoEventHandler}.
 *
 * @author Oliver Richers
 */
public interface IDomEventHandler {

	void handleDOMEvent(IDomEvent event);
}
