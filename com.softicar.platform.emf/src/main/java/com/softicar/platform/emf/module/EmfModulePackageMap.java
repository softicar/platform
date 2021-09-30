package com.softicar.platform.emf.module;

import com.softicar.platform.common.core.java.packages.name.JavaPackageName;
import com.softicar.platform.emf.module.registry.IEmfModuleRegistry;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class EmfModulePackageMap {

	private final Map<JavaPackageName, IEmfModule<?>> modulePackageMap;

	public EmfModulePackageMap(IEmfModuleRegistry registry) {

		this.modulePackageMap = new TreeMap<>();
		for (IEmfModule<?> module: registry.getAllModules()) {
			modulePackageMap.put(module.getModulePackage(), module);
		}
	}

	public Optional<IEmfModule<?>> getModule(JavaPackageName modulePackage) {

		return Optional.ofNullable(modulePackageMap.get(modulePackage));
	}

	public Optional<IEmfModule<?>> determineModule(Class<?> someClass) {

		return determineModule(new JavaPackageName(someClass));
	}

	public Optional<IEmfModule<?>> determineModule(JavaPackageName somePackage) {

		IEmfModule<?> module = modulePackageMap.get(somePackage);
		if (module != null) {
			return Optional.of(module);
		} else {
			if (somePackage == JavaPackageName.getEmpty()) {
				return Optional.empty();
			} else {
				return determineModule(somePackage.getParent());
			}
		}
	}
}
