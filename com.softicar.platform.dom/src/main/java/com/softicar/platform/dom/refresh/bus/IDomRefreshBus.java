package com.softicar.platform.dom.refresh.bus;

import java.lang.ref.WeakReference;

/**
 * Interface for the refresh bus.
 *
 * @author Oliver Richers
 */
public interface IDomRefreshBus {

	/**
	 * Adds the given listener to this bus.
	 * <p>
	 * The bus will only hold a {@link WeakReference} to the given listener,
	 * that is, if the listener is not referenced from somewhere else, it will
	 * be collected (sooner or later).
	 * <p>
	 * The listener is not added immediately. The addition is only executed
	 * right before the submission of the next {@link IDomRefreshBusEvent}.
	 *
	 * @param listener
	 *            the listener (never null)
	 * @return this bus
	 */
	IDomRefreshBus addListener(IDomRefreshBusListener listener);

	/**
	 * Removes the given listener from this bus.
	 * <p>
	 * The listener is not removed immediately. The removal is only executed
	 * right before the submission of the next {@link IDomRefreshBusEvent}.
	 *
	 * @param listener
	 *            the listener (never null)
	 * @return this bus
	 */
	IDomRefreshBus removeListener(IDomRefreshBusListener listener);

	/**
	 * Adds the given object to the current {@link IDomRefreshBusEvent} object.
	 *
	 * @param object
	 *            the object to add (never null)
	 * @return this bus
	 */
	IDomRefreshBus setChanged(Object object);

	/**
	 * Adds the given class to the current {@link IDomRefreshBusEvent} object.
	 *
	 * @param objectClass
	 *            the class to add (never null)
	 * @return this bus
	 */
	IDomRefreshBus setAllChanged(Class<?> objectClass);

	/**
	 * Sets the "all-changed" flag on the current {@link IDomRefreshBusEvent}
	 * object.
	 *
	 * @return this bus
	 */
	IDomRefreshBus setAllChanged();

	/**
	 * <b>WARNING: Do not call this method!</b> It is implicitly called at the
	 * end of every event handling.
	 * <p>
	 * Submits the current {@link IDomRefreshBusEvent} object, that is, sends it
	 * to all listeners on the bus.
	 *
	 * @return this bus
	 */
	IDomRefreshBus submitEvent();

	/**
	 * Discards the currently-assembled event without submitting it.
	 *
	 * @return this bus
	 */
	IDomRefreshBus discardEvent();
}
