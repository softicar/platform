package com.softicar.platform.dom.event;

import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.engine.IDomEngine;

/**
 * Implement this interface if you want to handle {@link DomEventType#DBLCLICK}.
 * <p>
 * If an {@link IDomElement} implements this interface, the method
 * {@link IDomEngine#listenToEvent} will be called automatically.
 *
 * @author Oliver Richers
 */
public interface IDomDoubleClickEventHandler extends IDomAutoEventHandler {

	void handleDoubleClick(IDomEvent event);
}
