package com.softicar.platform.core.module.access.role.assignment.module.instance.matrix.user;

import com.softicar.platform.common.core.supplier.LazySupplier;
import com.softicar.platform.core.module.access.role.assignment.module.instance.AGModuleInstanceRoleAssignment;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Creates prepared {@link AGModuleInstanceRoleAssignment} records for saving to the
 * database. Determines if saving is necessary, at all.
 *
 * @author Alexander Schmidt
 */
class ModuleRoleMembershipRecordCreator {

	private final LazySupplier<PermissionMap> mapSupplier;
	private Optional<AGUser> user;
	private Optional<AGUuid> moduleUuid;

	public ModuleRoleMembershipRecordCreator() {

		this.mapSupplier = new LazySupplier<>(PermissionMap::new);
		this.user = Optional.empty();
		this.moduleUuid = Optional.empty();
	}

	public ModuleRoleMembershipRecordCreator setUser(AGUser user) {

		this.user = Optional.of(user);
		return this;
	}

	public ModuleRoleMembershipRecordCreator setModuleUuid(AGUuid moduleUuid) {

		this.moduleUuid = Optional.of(moduleUuid);
		return this;
	}

	/**
	 * Returns a new or existing {@link AGModuleInstanceRoleAssignment}, if such a
	 * record needs to be saved for the given {@link ModuleRoleMembershipEntry}.
	 *
	 * @param entry
	 *            the {@link ModuleRoleMembershipEntry} to be persisted (never
	 *            null)
	 * @return a new or existing {@link AGModuleInstanceRoleAssignment}, if such a
	 *         record needs to be saved for the given
	 *         {@link ModuleRoleMembershipEntry}
	 */
	public Optional<AGModuleInstanceRoleAssignment> createRecord(ModuleRoleMembershipEntry entry) {

		AGModuleInstanceRoleAssignment record = mapSupplier.get().get(entry.getKey());
		if (record != null) {
			if (entry.isActive() != record.isActive()) {
				return Optional.of(entry.applyValuesTo(record));
			} else {
				return Optional.empty();
			}
		} else {
			if (entry.isActive()) {
				return Optional.of(entry.applyValuesTo(new AGModuleInstanceRoleAssignment()));
			} else {
				return Optional.empty();
			}
		}
	}

	private class PermissionMap extends TreeMap<ModuleRoleMembershipEntryKey, AGModuleInstanceRoleAssignment> {

		public PermissionMap() {

			loadRecords().forEach(record -> put(new ModuleRoleMembershipEntryKey(record), record));
		}

		private Set<AGModuleInstanceRoleAssignment> loadRecords() {

			IModuleRoleMembershipQuery query = createQuery();
			setQueryParameters(query);
			return executeQuery(query);
		}

		private IModuleRoleMembershipQuery createQuery() {

			return IModuleRoleMembershipQuery.FACTORY//
				.createQuery()
				.setOnlyActive(false);
		}

		private void setQueryParameters(IModuleRoleMembershipQuery query) {

			user.ifPresent(query::setUser);
			moduleUuid.ifPresent(query::setModuleUuid);
		}

		private Set<AGModuleInstanceRoleAssignment> executeQuery(IModuleRoleMembershipQuery query) {

			return query//
				.stream()
				.map(row -> row.getMembership())
				.collect(Collectors.toSet());
		}
	}
}
