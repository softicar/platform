package com.softicar.platform.core.module.permission;

import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Container for {@link IEmfModulePermission} instances and associated "active"
 * state flags.
 *
 * @author Alexander Schmidt
 */
public class EmfModulePermissionStateContainer {

	private final Map<IEmfModulePermission<?>, Boolean> map;

	public EmfModulePermissionStateContainer() {

		this.map = new TreeMap<>();
	}

	/**
	 * Puts the given {@link IEmfModulePermission} with the given state flag to
	 * this container.
	 *
	 * @param permission
	 *            the {@link IEmfModulePermission} to put to this container
	 *            (non-null)
	 * @param active
	 *            the "active" state flag of the {@link IEmfModulePermission}
	 * @return this {@link EmfModulePermissionStateContainer}
	 */
	public EmfModulePermissionStateContainer put(IEmfModulePermission<?> permission, boolean active) {

		map.put(permission, active);
		return this;
	}

	/**
	 * Returns all {@link IEmfModulePermission} instances in this container.
	 *
	 * @return all {@link IEmfModulePermission} instances in this container
	 *         (never null)
	 */
	public Collection<IEmfModulePermission<?>> getPermissions() {

		return map.keySet();
	}

	/**
	 * Determines if this container holds any information.
	 *
	 * @return true if this container does not hold any information; false
	 *         otherwise
	 */
	public boolean isEmpty() {

		return map.isEmpty();
	}

	/**
	 * Determines if the given {@link IEmfModulePermission} is contained with an
	 * "active" state flag.
	 *
	 * @param permission
	 *            the {@link IEmfModulePermission} for which the "active" state
	 *            flag shall be determined (may be null)
	 * @return true if the given {@link IEmfModulePermission} is contained with
	 *         an "active" state flag; false otherwise; false if null is given
	 */
	public boolean isActive(IEmfModulePermission<?> permission) {

		return Optional//
			.ofNullable(permission)
			.map(it -> map.getOrDefault(it, false))
			.orElse(false);
	}

	/**
	 * Determines if all contained permissions are flagged as inactive.
	 *
	 * @return true if all contained permissions are flagged as inactive; false
	 *         if at least one contained permission is flagged as active; false
	 *         if no permissions are contained
	 */
	public boolean isAllInactive() {

		return !map.values().stream().anyMatch(it -> it == true);
	}

	/**
	 * Adds the elements from the given
	 * {@link EmfModulePermissionStateContainer} to this
	 * {@link EmfModulePermissionStateContainer}.
	 * <p>
	 * If both {@link EmfModulePermissionStateContainer} instances contain an
	 * entry for the same {@link IEmfModulePermission}, the state from the given
	 * {@link EmfModulePermissionStateContainer} will overwrite the state in
	 * this {@link EmfModulePermissionStateContainer}.
	 *
	 * @param other
	 *            the other {@link EmfModulePermissionStateContainer} (non-null)
	 * @return this {@link EmfModulePermissionStateContainer}
	 */
	public EmfModulePermissionStateContainer merge(EmfModulePermissionStateContainer other) {

		for (IEmfModulePermission<?> permission: other.getPermissions()) {
			map.put(permission, other.isActive(permission));
		}
		return this;
	}
}
