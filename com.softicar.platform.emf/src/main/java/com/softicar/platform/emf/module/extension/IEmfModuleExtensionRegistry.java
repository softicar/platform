package com.softicar.platform.emf.module.extension;

import java.util.Collection;

/**
 * Interface of registry for {@link IEmfModuleExtension}.
 *
 * @author Oliver Richers
 */
public interface IEmfModuleExtensionRegistry {

	<E extends IEmfModuleExtension> void registerExtension(Class<E> extensionInterface, E extensionImplementation);

	<E extends IEmfModuleExtension> Collection<E> getExtensions(Class<E> extensionInterface);
}
