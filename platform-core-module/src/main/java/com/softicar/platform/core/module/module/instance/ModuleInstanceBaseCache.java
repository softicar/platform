package com.softicar.platform.core.module.module.instance;

import com.softicar.platform.common.core.thread.utils.ThreadSafeLazySupplier;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;

/**
 * A controllable cache for {@link AGModuleInstanceBase} records.
 * <p>
 * FIXME After removal of IEmfModuleInstanceGroup, this class assumes that for
 * every {@link IEmfModule}, there exists only one {@link IEmfModuleInstance}.
 * Methods might need to be adapted to return {@link Collection} of
 * {@link IEmfModuleInstance}.
 *
 * @author Alexander Schmidt
 */
public class ModuleInstanceBaseCache {

	private final ThreadSafeLazySupplier<Cache> cache;

	public ModuleInstanceBaseCache() {

		this.cache = new ThreadSafeLazySupplier<>(Cache::new);
	}

	/**
	 * Determines if the module with the given {@link UUID} is deployed.
	 *
	 * @param moduleUuid
	 *            the {@link UUID} of the module which might be deployed
	 * @return true if the module with the given {@link UUID} is deployed; false
	 *         otherwise
	 */
	public boolean isDeployed(UUID moduleUuid) {

		return getActive(moduleUuid).isPresent();
	}

	/**
	 * Determines if the module with the given {@link AGUuid} is deployed.
	 *
	 * @param moduleUuid
	 *            the {@link AGUuid} of the module which might be deployed
	 * @return true if the module with the given {@link AGUuid} is deployed;
	 *         false otherwise
	 */
	public boolean isDeployed(AGUuid moduleUuid) {

		return getActive(moduleUuid).isPresent();
	}

	/**
	 * Determines the active {@link AGModuleInstanceBase} for the given module
	 * {@link UUID}.
	 *
	 * @param moduleUuid
	 *            the {@link UUID} of the module which might be deployed
	 * @return the active {@link AGModuleInstanceBase} for the given module
	 *         {@link UUID} (if any)
	 */
	public Optional<AGModuleInstanceBase> getActive(UUID moduleUuid) {

		return cache.get().getActive(moduleUuid);
	}

	/**
	 * Determines the active {@link AGModuleInstanceBase} for the given module
	 * {@link AGUuid}.
	 *
	 * @param moduleUuid
	 *            the {@link AGUuid} of the module which might be deployed
	 * @return the active {@link AGModuleInstanceBase} for the given module
	 *         {@link AGUuid} (if any)
	 */
	public Optional<AGModuleInstanceBase> getActive(AGUuid moduleUuid) {

		return cache.get().getActive(moduleUuid);
	}

	private class Cache {

		private final Map<ModuleInstanceKey, AGModuleInstanceBase> map;

		public Cache() {

			this.map = new TreeMap<>();
			load();
		}

		public Optional<AGModuleInstanceBase> getActive(UUID moduleUuid) {

			return getActive(AGUuid.getOrCreate(moduleUuid));
		}

		public Optional<AGModuleInstanceBase> getActive(AGUuid moduleUuid) {

			return Optional.ofNullable(map.get(new ModuleInstanceKey(moduleUuid)));
		}

		private void load() {

			for (AGModuleInstanceBase moduleInstance: AGModuleInstanceBase.loadAllActive()) {
				this.map.put(new ModuleInstanceKey(moduleInstance), moduleInstance);
			}
		}
	}

	private class ModuleInstanceKey implements Comparable<ModuleInstanceKey> {

		private final AGUuid moduleUuid;

		public ModuleInstanceKey(AGModuleInstanceBase moduleInstance) {

			this(moduleInstance.getModuleUuid());
		}

		public ModuleInstanceKey(AGUuid moduleUuid) {

			this.moduleUuid = Objects.requireNonNull(moduleUuid);
		}

		public AGUuid getModuleUuid() {

			return moduleUuid;
		}

		@Override
		public int compareTo(ModuleInstanceKey other) {

			return Comparator//
				.comparing(ModuleInstanceKey::getModuleUuid)
				.compare(this, other);
		}
	}
}
