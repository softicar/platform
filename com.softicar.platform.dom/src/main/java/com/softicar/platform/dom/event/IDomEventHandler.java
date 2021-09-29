package com.softicar.platform.dom.event;

/**
 * You should implement this interface if you want to receive DOM events.
 *
 * @author Oliver Richers
 */
public interface IDomEventHandler {

	void handleDOMEvent(IDomEvent event);
}
