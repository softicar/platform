package com.softicar.platform.dom.event;

import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.engine.IDomEngine;

/**
 * Implement this interface if you want to handle {@link DomEventType#ESCAPE}.
 * <p>
 * If a {@link IDomElement} implements this interface, the method
 * {@link IDomEngine#listenToEvent} will be called automatically.
 *
 * @author Oliver Richers
 */
public interface IDomEscapeKeyEventHandler extends IDomAutoEventHandler {

	void handleEscapeKey(IDomEvent event);
}
