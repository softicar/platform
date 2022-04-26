package com.softicar.platform.dom.event;

import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.engine.IDomEngine;

/**
 * Implement this interface if you want to handle
 * {@link DomEventType#CONTEXTMENU}.
 * <p>
 * If a {@link IDomElement} implements this interface, the method
 * {@link IDomEngine#listenToEvent} will be called automatically.
 *
 * @author Alexander Schmidt
 */
public interface IDomContextMenuEventHandler extends IDomAutoEventHandler {

	void handleContextMenu(IDomEvent event);
}
