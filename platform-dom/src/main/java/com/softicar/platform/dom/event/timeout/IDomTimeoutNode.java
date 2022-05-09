package com.softicar.platform.dom.event.timeout;

import com.softicar.platform.dom.node.IDomNode;

/**
 * Interface to schedule and receive timeout events.
 *
 * @author Oliver Richers
 */
public interface IDomTimeoutNode extends IDomNode {

	void handleTimeout();

	/**
	 * Schedules a timeout event on this {@link IDomNode}.
	 * <p>
	 * After the specified amount of seconds have passed, the method
	 * {@link IDomTimeoutNode#handleTimeout()} will be called.
	 *
	 * @param seconds
	 *            the seconds to wait before the timeout
	 */
	default void scheduleTimeout(double seconds) {

		getDomEngine().scheduleTimeout(this, seconds);
	}
}
