package com.softicar.platform.emf.module;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.java.packages.name.JavaPackageName;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.emf.module.extension.IEmfModuleExtensionRegistry;
import com.softicar.platform.emf.module.message.bus.IEmfModuleMessageBus;
import com.softicar.platform.emf.page.EmfPagePath;
import java.util.Collection;

/**
 * Common base class of all modules.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 * @see IEmfModule
 */
public abstract class AbstractEmfModule<I extends IEmfModuleInstance<I>> implements IEmfModule<I> {

	@Override
	public final String getClassName() {

		return getClass().getSimpleName();
	}

	@Override
	public IDisplayString toDisplay() {

		return IDisplayString.create(getClassName());
	}

	@Override
	public final JavaPackageName getModulePackage() {

		return new JavaPackageName(getClass());
	}

	@Override
	public EmfPagePath getDefaultPagePath(I moduleInstance) {

		return new EmfPagePath().append(toDisplay());
	}

	@Override
	public void prefetchPageLinkGenerationData(Collection<I> moduleInstances) {

		// nothing to prefetch by default
	}

	/**
	 * Registers message consumers to the provided {@link IEmfModuleMessageBus}.
	 * <p>
	 * Should be overridden if necessary. Does nothing by default.
	 *
	 * @param messageBus
	 *            the {@link IEmfModuleMessageBus} to which message consumers
	 *            shall be registered (never null)
	 */
	@Override
	public void registerMessageConsumers(IEmfModuleMessageBus messageBus) {

		DevNull.swallow(messageBus);
	}

	/**
	 * Registers extensions to the provided {@link IEmfModuleExtensionRegistry}.
	 * <p>
	 * Should be overridden if necessary. Does nothing by default.
	 *
	 * @param extensionRegistry
	 *            the {@link IEmfModuleExtensionRegistry} to which message
	 *            consumers shall be registered (never null)
	 */
	@Override
	public void registerExtensions(IEmfModuleExtensionRegistry extensionRegistry) {

		DevNull.swallow(extensionRegistry);
	}
}
