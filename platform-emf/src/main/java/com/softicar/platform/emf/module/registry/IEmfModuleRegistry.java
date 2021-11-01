package com.softicar.platform.emf.module.registry;

import com.softicar.platform.common.code.classpath.metadata.IClasspathFilesMetadata;
import com.softicar.platform.common.core.java.packages.name.JavaPackageName;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.extension.IEmfModuleExtension;
import com.softicar.platform.emf.module.message.IEmfModuleMessage;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Central point of reference for all modules.
 *
 * @author Oliver Richers
 */
public interface IEmfModuleRegistry {

	static IEmfModuleRegistry get() {

		return CurrentEmfModuleRegistry.get();
	}

	IClasspathFilesMetadata getClasspathFilesMetadata();

	// -------------------- modules -------------------- //

	Optional<IEmfModule<?>> getModule(UUID moduleUuid);

	Collection<IEmfModule<?>> getAllModules();

	<M extends IEmfModule<?>> M getModule(Class<M> moduleClass);

	Collection<Class<? extends IEmfModule<?>>> getAllModuleClasses();

	// -------------------- miscellaneous -------------------- //

	/**
	 * Determines the {@link IEmfModule} containing the given
	 * {@link JavaPackageName}.
	 * <p>
	 * If more than one {@link IEmfModule} matches the given
	 * {@link JavaPackageName}, the {@link IEmfModule} with the longer
	 * {@link JavaPackageName} will be returned.
	 *
	 * @param packageName
	 *            the {@link JavaPackageName} (never <i>null</i>)
	 * @return the containing {@link IEmfModule}
	 */
	Optional<IEmfModule<?>> getContainingModule(JavaPackageName packageName);

	/**
	 * Sends the given message over the message bus of this
	 * {@link IEmfModuleRegistry}.
	 *
	 * @param message
	 *            the message to send (never null)
	 */
	void sendMessage(IEmfModuleMessage message);

	/**
	 * Returns all extensions implementing the given extension interface.
	 *
	 * @param extensionInterface
	 * @return collection of all implementations (never null)
	 */
	<E extends IEmfModuleExtension> Collection<E> getExtensions(Class<E> extensionInterface);
}
