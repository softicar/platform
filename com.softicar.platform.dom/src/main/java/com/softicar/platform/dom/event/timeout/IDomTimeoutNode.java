package com.softicar.platform.dom.event.timeout;

import com.softicar.platform.dom.node.IDomNode;

/**
 * This interface can be implemented to handle timeouts scheduled by
 * {@link DomTimeout}.
 *
 * @author Oliver Richers
 */
public interface IDomTimeoutNode extends IDomNode {

	void handleTimeout();
}
