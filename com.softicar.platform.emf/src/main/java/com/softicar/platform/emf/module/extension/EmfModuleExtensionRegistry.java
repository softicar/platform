package com.softicar.platform.emf.module.extension;

import com.softicar.platform.common.container.map.list.IListMap;
import com.softicar.platform.common.container.map.list.ListHashMap;
import com.softicar.platform.common.core.java.reflection.ClassHierarchyValidator;
import com.softicar.platform.common.core.utils.CastUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Default implementation of {@link IEmfModuleExtensionRegistry}.
 *
 * @author Oliver Richers
 */
public class EmfModuleExtensionRegistry implements IEmfModuleExtensionRegistry {

	private final IListMap<Class<?>, IEmfModuleExtension> extensionMap;

	public EmfModuleExtensionRegistry() {

		this.extensionMap = new ListHashMap<>();
	}

	@Override
	public <E extends IEmfModuleExtension> void registerExtension(Class<E> extensionInterface, E extensionImplementation) {

		Objects.requireNonNull(extensionInterface);
		Objects.requireNonNull(extensionImplementation);
		validateInterface(extensionInterface);

		this.extensionMap.addToList(extensionInterface, extensionImplementation);
	}

	@Override
	public <E extends IEmfModuleExtension> Collection<E> getExtensions(Class<E> extensionInterface) {

		Objects.requireNonNull(extensionInterface);
		validateInterface(extensionInterface);

		List<E> extensions = CastUtils.cast(extensionMap.getList(extensionInterface));
		return Collections.unmodifiableCollection(extensions);
	}

	private <E extends IEmfModuleExtension> void validateInterface(Class<E> extensionInterface) {

		new ClassHierarchyValidator(extensionInterface).assertOnlyOneInterface(IEmfModuleExtension.class);
	}
}
