package com.softicar.platform.emf.module.message.bus;

import com.softicar.platform.emf.module.message.IEmfModuleMessage;
import java.util.function.Consumer;

/**
 * A message bus for intermodule communication.
 *
 * @author Oliver Richers
 */
public interface IEmfModuleMessageBus {

	/**
	 * Registers the given consumer for the given class of messages.
	 *
	 * @param messageClass
	 *            the message class (never null)
	 * @param messageConsumer
	 *            the message consumer (never null)
	 */
	<M extends IEmfModuleMessage> void registerConsumer(Class<M> messageClass, Consumer<M> messageConsumer);

	/**
	 * Sends the given message over this bus.
	 *
	 * @param message
	 *            the module message to send
	 */
	<M extends IEmfModuleMessage> void sendMessage(M message);
}
