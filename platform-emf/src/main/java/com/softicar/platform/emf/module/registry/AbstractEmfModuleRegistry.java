package com.softicar.platform.emf.module.registry;

import com.softicar.platform.common.core.java.packages.name.JavaPackageName;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.extension.EmfModuleExtensionRegistry;
import com.softicar.platform.emf.module.extension.IEmfModuleExtension;
import com.softicar.platform.emf.module.message.IEmfModuleMessage;
import com.softicar.platform.emf.module.message.bus.EmfModuleMessageBus;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Abstract base class implementing {@link IEmfModuleRegistry}.
 *
 * @author Oliver Richers
 */
public abstract class AbstractEmfModuleRegistry implements IEmfModuleRegistry {

	private final EmfModuleMap moduleMap;
	private final EmfModuleMessageBus messageBus;
	private final EmfModuleExtensionRegistry extensionRegistry;

	public AbstractEmfModuleRegistry() {

		this.moduleMap = new EmfModuleMap();
		this.messageBus = new EmfModuleMessageBus();
		this.extensionRegistry = new EmfModuleExtensionRegistry();
	}

	// -------------------- modules -------------------- //

	@Override
	public Collection<Class<? extends IEmfModule<?>>> getAllModuleClasses() {

		return moduleMap.getModuleClasses();
	}

	@Override
	public Collection<IEmfModule<?>> getAllModules() {

		return moduleMap.getModules();
	}

	@Override
	public <T extends IEmfModule<?>> T getModule(Class<T> moduleClass) {

		return moduleMap.getModule(moduleClass);
	}

	@Override
	public Optional<IEmfModule<?>> getModule(UUID moduleUuid) {

		return moduleMap.getModule(moduleUuid);
	}

	// -------------------- miscellaneous -------------------- //

	@Override
	public Optional<IEmfModule<?>> getContainingModule(JavaPackageName packageName) {

		return moduleMap.getContainingModule(packageName);
	}

	@Override
	public void sendMessage(IEmfModuleMessage message) {

		messageBus.sendMessage(message);
	}

	@Override
	public <E extends IEmfModuleExtension> Collection<E> getExtensions(Class<E> extensionInterface) {

		return extensionRegistry.getExtensions(extensionInterface);
	}

	// -------------------- private -------------------- //

	protected void registerModule(IEmfModule<?> module) {

		moduleMap.addModule(module.getClass(), module);

		// add message bus consumers
		module.registerMessageConsumers(messageBus);

		// add module extensions
		module.registerExtensions(extensionRegistry);
	}
}
