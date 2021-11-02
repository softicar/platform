package com.softicar.platform.emf.module.message;

import com.softicar.platform.emf.module.message.bus.IEmfModuleMessageBus;

/**
 * Common interface of all module messages sent over an
 * {@link IEmfModuleMessageBus}.
 * <p>
 * A class implementing this interface may not extend any other class and may
 * not implement any other interface. You can use
 * {@link EmfModuleMessageClassValidator} to ensure this.
 *
 * @author Oliver Richers
 */
public interface IEmfModuleMessage {

	// nothing to add
}
