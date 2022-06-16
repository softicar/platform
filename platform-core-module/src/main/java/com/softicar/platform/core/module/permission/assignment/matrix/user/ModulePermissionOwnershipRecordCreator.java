package com.softicar.platform.core.module.permission.assignment.matrix.user;

import com.softicar.platform.common.core.supplier.LazySupplier;
import com.softicar.platform.core.module.permission.assignment.AGModuleInstancePermissionAssignment;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Creates prepared {@link AGModuleInstancePermissionAssignment} records for
 * saving to the database. Determines if saving is necessary, at all.
 *
 * @author Alexander Schmidt
 */
class ModulePermissionOwnershipRecordCreator {

	private final LazySupplier<PermissionMap> mapSupplier;
	private Optional<AGUser> user;
	private Optional<AGUuid> moduleUuid;

	public ModulePermissionOwnershipRecordCreator() {

		this.mapSupplier = new LazySupplier<>(PermissionMap::new);
		this.user = Optional.empty();
		this.moduleUuid = Optional.empty();
	}

	public ModulePermissionOwnershipRecordCreator setUser(AGUser user) {

		this.user = Optional.of(user);
		return this;
	}

	public ModulePermissionOwnershipRecordCreator setModuleUuid(AGUuid moduleUuid) {

		this.moduleUuid = Optional.of(moduleUuid);
		return this;
	}

	/**
	 * Returns a new or existing {@link AGModuleInstancePermissionAssignment},
	 * if such a record needs to be saved for the given
	 * {@link ModulePermissionOwnershipEntry}.
	 *
	 * @param entry
	 *            the {@link ModulePermissionOwnershipEntry} to be persisted
	 *            (never null)
	 * @return a new or existing {@link AGModuleInstancePermissionAssignment},
	 *         if such a record needs to be saved for the given
	 *         {@link ModulePermissionOwnershipEntry}
	 */
	public Optional<AGModuleInstancePermissionAssignment> createRecord(ModulePermissionOwnershipEntry entry) {

		AGModuleInstancePermissionAssignment record = mapSupplier.get().get(entry.getKey());
		if (record != null) {
			if (entry.isActive() != record.isActive()) {
				return Optional.of(entry.applyValuesTo(record));
			} else {
				return Optional.empty();
			}
		} else {
			if (entry.isActive()) {
				return Optional.of(entry.applyValuesTo(new AGModuleInstancePermissionAssignment()));
			} else {
				return Optional.empty();
			}
		}
	}

	private class PermissionMap extends TreeMap<ModulePermissionOwnershipEntryKey, AGModuleInstancePermissionAssignment> {

		public PermissionMap() {

			loadRecords().forEach(record -> put(new ModulePermissionOwnershipEntryKey(record), record));
		}

		private Set<AGModuleInstancePermissionAssignment> loadRecords() {

			IModulePermissionOwnershipQuery query = createQuery();
			setQueryParameters(query);
			return executeQuery(query);
		}

		private IModulePermissionOwnershipQuery createQuery() {

			return IModulePermissionOwnershipQuery.FACTORY//
				.createQuery()
				.setOnlyActive(false);
		}

		private void setQueryParameters(IModulePermissionOwnershipQuery query) {

			user.ifPresent(query::setUser);
			moduleUuid.ifPresent(query::setModuleUuid);
		}

		private Set<AGModuleInstancePermissionAssignment> executeQuery(IModulePermissionOwnershipQuery query) {

			return query//
				.stream()
				.map(row -> row.getAssignment())
				.collect(Collectors.toSet());
		}
	}
}
