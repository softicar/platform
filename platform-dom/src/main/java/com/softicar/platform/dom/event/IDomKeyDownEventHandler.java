package com.softicar.platform.dom.event;

import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.engine.IDomEngine;

/**
 * Implement this interface if you want to handle {@link DomEventType#KEYDOWN}.
 * <p>
 * If an {@link IDomElement} implements this interface, the method
 * {@link IDomEngine#listenToEvent} will be called automatically.
 * <p>
 * <b>Important:</b> You must also call {@link IDomEngine#setListenToKeys} or
 * {@link IDomElement#setListenToKeys} to define which keys shall trigger
 * events.
 *
 * @author Oliver Richers
 */
public interface IDomKeyDownEventHandler extends IDomAutoEventHandler {

	void handleKeyDown(IDomEvent event);
}
