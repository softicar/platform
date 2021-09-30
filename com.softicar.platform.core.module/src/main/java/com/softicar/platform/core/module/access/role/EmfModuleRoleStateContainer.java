package com.softicar.platform.core.module.access.role;

import com.softicar.platform.emf.module.role.IEmfModuleRole;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Container for {@link IEmfModuleRole} instances and associated "active" state
 * flags.
 *
 * @author Alexander Schmidt
 */
public class EmfModuleRoleStateContainer {

	private final Map<IEmfModuleRole<?>, Boolean> map;

	public EmfModuleRoleStateContainer() {

		this.map = new TreeMap<>();
	}

	/**
	 * Puts the given {@link IEmfModuleRole} with the given state flag to this
	 * container.
	 *
	 * @param role
	 *            the {@link IEmfModuleRole} to put to this container (non-null)
	 * @param active
	 *            the "active" state flag of the {@link IEmfModuleRole}
	 * @return this {@link EmfModuleRoleStateContainer}
	 */
	public EmfModuleRoleStateContainer put(IEmfModuleRole<?> role, boolean active) {

		map.put(role, active);
		return this;
	}

	/**
	 * Returns all {@link IEmfModuleRole} instances in this container.
	 *
	 * @return all {@link IEmfModuleRole} instances in this container (never null)
	 */
	public Collection<IEmfModuleRole<?>> getRoles() {

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
	 * Determines if the given {@link IEmfModuleRole} is contained with an "active"
	 * state flag.
	 *
	 * @param role
	 *            the {@link IEmfModuleRole} for which the "active" state flag
	 *            shall be determined (may be null)
	 * @return true if the given {@link IEmfModuleRole} is contained with an
	 *         "active" state flag; false otherwise; false if null is given
	 */
	public boolean isActive(IEmfModuleRole<?> role) {

		return Optional//
			.ofNullable(role)
			.map(it -> map.getOrDefault(it, false))
			.orElse(false);
	}

	/**
	 * Determines if all contained roles are flagged as inactive.
	 *
	 * @return true if all contained roles are flagged as inactive; false if at
	 *         least one contained role is flagged as active; false if no roles
	 *         are contained
	 */
	public boolean isAllInactive() {

		return !map.values().stream().anyMatch(it -> it == true);
	}

	/**
	 * Adds the elements from the given {@link EmfModuleRoleStateContainer} to this
	 * {@link EmfModuleRoleStateContainer}.
	 * <p>
	 * If both {@link EmfModuleRoleStateContainer} instances contain an entry for
	 * the same {@link IEmfModuleRole}, the state from the given
	 * {@link EmfModuleRoleStateContainer} will overwrite the state in this
	 * {@link EmfModuleRoleStateContainer}.
	 *
	 * @param other
	 *            the other {@link EmfModuleRoleStateContainer} (non-null)
	 * @return this {@link EmfModuleRoleStateContainer}
	 */
	public EmfModuleRoleStateContainer merge(EmfModuleRoleStateContainer other) {

		for (IEmfModuleRole<?> role: other.getRoles()) {
			map.put(role, other.isActive(role));
		}
		return this;
	}
}
