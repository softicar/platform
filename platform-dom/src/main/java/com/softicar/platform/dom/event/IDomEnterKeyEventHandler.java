package com.softicar.platform.dom.event;

import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.engine.IDomEngine;

/**
 * Implement this interface if you want to handle {@link DomEventType#ENTER}.
 * <p>
 * If an {@link IDomElement} implements this interface, the method
 * {@link IDomEngine#listenToEvent} will be called automatically.
 *
 * @author Oliver Richers
 */
public interface IDomEnterKeyEventHandler extends IDomAutoEventHandler {

	void handleEnterKey(IDomEvent event);
}
