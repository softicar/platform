package com.softicar.platform.emf.module.registry;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.java.packages.name.JavaPackageName;
import com.softicar.platform.emf.module.IEmfModule;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;

public class EmfModuleMap {

	private final Map<Class<? extends IEmfModule<?>>, IEmfModule<?>> moduleClassMap;
	private final Map<UUID, IEmfModule<?>> moduleUuidMap;
	private final Map<JavaPackageName, IEmfModule<?>> modulePackageMap;

	public EmfModuleMap() {

		this.moduleClassMap = new HashMap<>();
		this.moduleUuidMap = new TreeMap<>();
		this.modulePackageMap = new TreeMap<>();
	}

	public Collection<Class<? extends IEmfModule<?>>> getModuleClasses() {

		return new HashSet<>(moduleClassMap.keySet());
	}

	public Collection<IEmfModule<?>> getModules() {

		return moduleClassMap.values();
	}

	public <T extends IEmfModule<?>> T getModule(Class<T> moduleClass) {

		IEmfModule<?> module = moduleClassMap.get(moduleClass);
		if (module != null) {
			return moduleClass.cast(module);
		} else {
			throw new RuntimeException(String.format("No module registered for class %s.", moduleClass.getCanonicalName()));
		}
	}

	public Optional<IEmfModule<?>> getModule(UUID moduleUuid) {

		return Optional.ofNullable(moduleUuidMap.get(moduleUuid));
	}

	public Optional<IEmfModule<?>> getContainingModule(JavaPackageName packageName) {

		while (!packageName.isEmpty()) {
			IEmfModule<?> module = modulePackageMap.get(packageName);
			if (module != null) {
				return Optional.of(module);
			} else {
				packageName = packageName.getParent();
			}
		}
		return Optional.empty();
	}

	public <T extends IEmfModule<?>> void addModule(Class<T> moduleClass, IEmfModule<?> module) {

		addToClassMap(moduleClass, module);
		addToUuidMap(module);
		addToPackageMap(module);
	}

	private <T extends IEmfModule<?>> void addToClassMap(Class<T> moduleClass, IEmfModule<?> module) {

		IEmfModule<?> previous = moduleClassMap.put(moduleClass, module);
		if (previous != null) {
			throw new IllegalStateException(
				String
					.format(//
						"Multiple modules registered for class %s.",
						moduleClass.getCanonicalName()));
		}
	}

	private void addToUuidMap(IEmfModule<?> module) {

		UUID uuid = module.getAnnotatedUuid();
		if (uuid == null) {
			throw new SofticarDeveloperException("The module class is missing a UUID: %s", module.getClass().getCanonicalName());
		}

		IEmfModule<?> previous = moduleUuidMap.put(uuid, module);

		if (previous != null) {
			throw new IllegalStateException(
				String
					.format(//
						"Multiple modules '%s' and '%s' registered for uuid '%s'.",
						previous.getClassName(),
						module.getClassName(),
						uuid));
		}
	}

	private void addToPackageMap(IEmfModule<?> module) {

		JavaPackageName modulePackage = new JavaPackageName(module.getClass());
		IEmfModule<?> previousModule = modulePackageMap.put(modulePackage, module);
		if (previousModule != null) {
			throw new SofticarDeveloperException(//
				"Module %s and %s map to the same package: %s",
				previousModule.toDisplay(),
				module.toDisplay(),
				modulePackage);
		}
	}
}
