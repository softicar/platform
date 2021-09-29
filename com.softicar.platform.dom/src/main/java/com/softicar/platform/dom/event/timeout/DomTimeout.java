package com.softicar.platform.dom.event.timeout;

import com.softicar.platform.dom.node.IDomNode;

/**
 * This class can be used to schedule a timeout event.
 * <p>
 * After a specified amount of seconds the provided {@link IDomTimeoutNode} is
 * called.
 * <p>
 * <b>Please note that the {@link IDomTimeoutNode} is only called once and has
 * to re-schedule a new timeout to be called again.</b>
 */
public class DomTimeout {

	private final double seconds;

	/**
	 * Constructs this timeout builder with the specified amount of seconds.
	 *
	 * @param seconds
	 *            the seconds to wait before the {@link IDomTimeoutNode} is
	 *            called
	 */
	public DomTimeout(double seconds) {

		this.seconds = seconds;
	}

	/**
	 * After the specified amount of seconds have passed, the method
	 * {@link IDomTimeoutNode#handleTimeout()} will be called.
	 *
	 * @param timeoutNode
	 *            the {@link IDomNode} to handle the timeout event
	 */
	public void schedule(IDomTimeoutNode timeoutNode) {

		timeoutNode.getDomEngine().scheduleTimeout(timeoutNode, seconds);
	}
}
