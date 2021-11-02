package com.softicar.platform.dom.event;

import com.softicar.platform.dom.engine.IDomEngine;
import com.softicar.platform.dom.node.DomNode;

/**
 * Implement this interface if you want to handle {@link DomEventType#TAB}.
 * <p>
 * If a {@link DomNode} implements this interface, the method
 * {@link IDomEngine#listenToEvent} will be called automatically.
 *
 * @author Oliver Richers
 */
public interface IDomTabKeyEventHandler extends IDomAutoEventHandler {

	void handleTabKey(IDomEvent event);
}
