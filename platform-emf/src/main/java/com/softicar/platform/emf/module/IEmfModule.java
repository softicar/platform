package com.softicar.platform.emf.module;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.java.packages.name.JavaPackageName;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.module.extension.IEmfModuleExtensionRegistry;
import com.softicar.platform.emf.module.message.bus.IEmfModuleMessageBus;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import com.softicar.platform.emf.source.code.reference.point.IEmfSourceCodeReferencePoint;
import java.util.Collection;
import java.util.Optional;

/**
 * Common interface of all modules.
 * <p>
 * Non-abstract implementations are expected to
 * <ul>
 * <li>be annotated with {@link EmfSourceCodeReferencePointUuid}</li>
 * <li>have a parameter-less constructor</li>
 * </ul>
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public interface IEmfModule<I extends IEmfModuleInstance<I>> extends IEmfSourceCodeReferencePoint {

	/**
	 * Returns the class name of this module.
	 *
	 * @return the class name of this module (never null)
	 */
	String getClassName();

	/**
	 * Returns the name of this module.
	 *
	 * @return the name of this module (never null)
	 */
	@Override
	IDisplayString toDisplay();

	/**
	 * Returns the {@link JavaPackageName} of this module.
	 *
	 * @return the {@link JavaPackageName} of this module (never null)
	 */
	JavaPackageName getModulePackage();

	/**
	 * Returns the icon of this {@link IEmfModule}.
	 *
	 * @return the icon (never <i>null</i>)
	 */
	default IResource getIcon() {

		return EmfImages.MODULE_DEFAULT.getResource();
	}

	// ------------------------------ instances ------------------------------ //

	/**
	 * Determines all active instances of this module.
	 *
	 * @return all active instances of this module, if any (never null)
	 */
	Collection<I> getActiveModuleInstances();

	/**
	 * Determines the module instance that is associated with the given ID.
	 * <p>
	 * If module instance IDs are irrelevant for this module, e.g. for singleton
	 * modules, <i>null</i> may be passed as an argument.
	 *
	 * @param moduleInstanceId
	 *            the ID of the module instance (may be null)
	 * @return the module instance associated with the given ID (never null)
	 * @throws RuntimeException
	 *             if no module instance can be determined for the given ID
	 * @deprecated use {@link #getModuleInstance(Integer)} instead
	 */
	@Deprecated
	I getModuleInstanceById(Integer moduleInstanceId);

	/**
	 * Determines the module instance that is associated with the given ID.
	 * <p>
	 * If module instance IDs are irrelevant for this module, e.g. for singleton
	 * modules, <i>null</i> may be passed as an argument.
	 *
	 * @param moduleInstanceId
	 *            the ID of the module instance (may be null)
	 * @return the optional module instance associated with the given ID
	 */
	Optional<I> getModuleInstance(Integer moduleInstanceId);

	// ------------------------------ pages ------------------------------ //

	/**
	 * Determines the root {@link EmfPagePath} for the given instance of this
	 * module. This is the {@link EmfPagePath} at which {@link IEmfPage}
	 * instances are to be displayed.
	 *
	 * @param moduleInstance
	 *            the {@link IEmfModuleInstance} for which the root
	 *            {@link EmfPagePath} shall be determined (never null)
	 * @return the root {@link EmfPagePath} for the given instance of this
	 *         {@link IEmfModule} (never null)
	 */
	EmfPagePath getDefaultPagePath(I moduleInstance);

	/**
	 * Prefetches all data necessary for the page link generation of this
	 * module.
	 *
	 * @param moduleInstances
	 *            the module instances for which to prefetch the data (never
	 *            null)
	 */
	void prefetchPageLinkGenerationData(Collection<I> moduleInstances);

	// ------------------------------ hooks and plug-ins ------------------------------ //

	/**
	 * Registers consumers on the given {@link IEmfModuleMessageBus}.
	 *
	 * @param messageBus
	 *            the {@link IEmfModuleMessageBus} on which consumers shall be
	 *            registered (never null)
	 */
	void registerMessageConsumers(IEmfModuleMessageBus messageBus);

	/**
	 * Registers extensions on the given {@link IEmfModuleExtensionRegistry}.
	 *
	 * @param extensionRegistry
	 *            the {@link IEmfModuleExtensionRegistry} on which extensions
	 *            shall be registered (never null)
	 */
	void registerExtensions(IEmfModuleExtensionRegistry extensionRegistry);
}
