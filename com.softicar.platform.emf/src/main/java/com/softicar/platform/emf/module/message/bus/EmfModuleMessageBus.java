package com.softicar.platform.emf.module.message.bus;

import com.softicar.platform.common.container.map.list.IListMap;
import com.softicar.platform.common.container.map.list.ListHashMap;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.emf.module.message.EmfModuleMessageClassValidator;
import com.softicar.platform.emf.module.message.IEmfModuleMessage;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Default implementation of {@link IEmfModuleMessageBus}
 *
 * @author Oliver Richers
 */
public class EmfModuleMessageBus implements IEmfModuleMessageBus {

	private final IListMap<Class<?>, Consumer<?>> consumerMap;

	public EmfModuleMessageBus() {

		this.consumerMap = new ListHashMap<>();
	}

	@Override
	public <M extends IEmfModuleMessage> void registerConsumer(Class<M> messageClass, Consumer<M> messageConsumer) {

		EmfModuleMessageClassValidator.validateMessageClass(messageClass);

		this.consumerMap.addToList(messageClass, messageConsumer);
	}

	@Override
	public <M extends IEmfModuleMessage> void sendMessage(M message) {

		Objects.requireNonNull(message);
		EmfModuleMessageClassValidator.validateMessageClass(message.getClass());

		List<Consumer<M>> consumers = CastUtils.cast(consumerMap.getList(message.getClass()));

		for (Consumer<M> consumer: consumers) {
			consumer.accept(message);
		}
	}
}
