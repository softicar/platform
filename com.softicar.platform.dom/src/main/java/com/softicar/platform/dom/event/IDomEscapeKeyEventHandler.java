package com.softicar.platform.dom.event;

import com.softicar.platform.dom.engine.IDomEngine;
import com.softicar.platform.dom.node.DomNode;

/**
 * Implement this interface if you want to handle {@link DomEventType#ESCAPE}.
 * <p>
 * If a {@link DomNode} implements this interface, the method
 * {@link IDomEngine#listenToEvent} will be called automatically.
 *
 * @author Oliver Richers
 */
public interface IDomEscapeKeyEventHandler extends IDomAutoEventHandler {

	void handleEscapeKey(IDomEvent event);
}
