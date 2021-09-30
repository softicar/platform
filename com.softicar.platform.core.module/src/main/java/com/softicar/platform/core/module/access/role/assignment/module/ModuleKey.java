package com.softicar.platform.core.module.access.role.assignment.module;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.registry.CurrentEmfModuleRegistry;
import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;

public class ModuleKey implements Comparable<ModuleKey> {

	private final UUID uuid;
	private final IDisplayString displayName;

	public ModuleKey(IEmfModule<?> module) {

		this(module.getAnnotatedUuid());
	}

	public ModuleKey(UUID uuid) {

		this.uuid = Objects.requireNonNull(uuid);
		this.displayName = createDisplayName(uuid);
	}

	public UUID getUuid() {

		return uuid;
	}

	public IDisplayString getDisplayName() {

		return displayName;
	}

	@Override
	public int compareTo(ModuleKey other) {

		return Comparator//
			.comparing(ModuleKey::getUuid)
			.compare(this, other);
	}

	private IDisplayString createDisplayName(UUID uuid) {

		return CurrentEmfModuleRegistry//
			.get()
			.getModule(uuid)
			.map(it -> it.toDisplay())
			.orElse(IDisplayString.create(uuid.toString()));
	}
}
