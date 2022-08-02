package com.softicar.platform.dom.event;

import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.engine.IDomEngine;

/**
 * Implement this interface if you want to handle {@link DomEventType#KEYDOWN}.
 * <p>
 * If an {@link IDomElement} implements this interface, the method
 * {@link IDomEngine#listenToEvent} will be called automatically.
 * <p>
 * You must call {@link IDomEngine#setListenToKeys} to define for which keys to
 * receive events.
 *
 * @author Oliver Richers
 */
public interface IDomKeyDownEventHandler extends IDomAutoEventHandler {

	void handleKeyDown(IDomEvent event);
}
