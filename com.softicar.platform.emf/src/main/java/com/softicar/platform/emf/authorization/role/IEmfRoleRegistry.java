package com.softicar.platform.emf.authorization.role;

import com.softicar.platform.emf.authorization.role.statik.IEmfStaticRole;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.role.IEmfModuleRole;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface of EMF role registries which provide access to {@link IEmfRole} and
 * {@link IEmfModuleRole} instances.
 *
 * @author Alexander Schmidt
 */
public interface IEmfRoleRegistry {

	// -------------------- modules -------------------- //

	/**
	 * Returns the modules that are known to this registry.
	 *
	 * @return the modules that are known to this registry (never <i>null</i>)
	 */
	Collection<IEmfModule<?>> getModules();

	// -------------------- roles -------------------- //

	/**
	 * Returns all registered {@link IEmfRole} instances.
	 *
	 * @return all registered {@link IEmfRole} instances (never <i>null</i>)
	 */
	Collection<IEmfRole<?>> getRoles();

	/**
	 * Determines the {@link IEmfRole} instances that are registered for the
	 * given {@link IEmfTable}.
	 *
	 * @param table
	 *            the {@link IEmfTable} for which registered {@link IEmfRole}
	 *            instances shall be retrieved (never <i>null</i>)
	 * @return the {@link IEmfRole} instances that are registered for the given
	 *         {@link IEmfTable} (never <i>null</i>)
	 */
	Collection<IEmfRole<?>> getRoles(IEmfTable<?, ?, ?> table);

	/**
	 * Determines the {@link IEmfRole} instances that are registered for the
	 * given {@link IEmfModule}.
	 *
	 * @param module
	 *            the {@link IEmfModule} for which registered {@link IEmfRole}
	 *            instances shall be retrieved (never <i>null</i>)
	 * @return the {@link IEmfRole} instances that are registered for the given
	 *         {@link IEmfModule} (never <i>null</i>)
	 */
	Collection<IEmfRole<?>> getRoles(IEmfModule<?> module);

	// -------------------- static roles -------------------- //

	/**
	 * Determines the {@link IEmfStaticRole} instances that are registered for
	 * the given {@link IEmfModule}.
	 *
	 * @param module
	 *            the {@link IEmfModule} for which registered
	 *            {@link IEmfStaticRole} instances shall be retrieved (never
	 *            <i>null</i>)
	 * @return the {@link IEmfStaticRole} instances that are registered for the
	 *         given {@link IEmfModule} (never <i>null</i>)
	 */
	Collection<IEmfStaticRole<?>> getStaticRoles(IEmfModule<?> module);

	/**
	 * Determines the {@link IEmfStaticRole} that is registered for the given
	 * {@link UUID}, if any.
	 *
	 * @param roleUuid
	 *            the {@link UUID} for which the registered
	 *            {@link IEmfStaticRole} instance shall be retrieved (never
	 *            <i>null</i>)
	 * @return the {@link IEmfStaticRole} that is registered for the given
	 *         {@link UUID}
	 */
	Optional<IEmfStaticRole<?>> getStaticRole(UUID roleUuid);

	/**
	 * Determines all {@link IEmfStaticRole} instances that are registered for
	 * the given {@link IEmfTable}.
	 *
	 * @param table
	 *            the {@link IEmfTable} for which registered
	 *            {@link IEmfStaticRole} instances shall be retrieved (never
	 *            <i>null</i>)
	 * @return the {@link IEmfStaticRole} instances that are registered for the
	 *         given {@link IEmfTable} (never <i>null</i>)
	 */
	Collection<IEmfStaticRole<?>> getStaticRoles(IEmfTable<?, ?, ?> table);

	/**
	 * Determines the type-parameterized {@link IEmfStaticRole} instance that is
	 * registered for the given {@link IEmfTable} and {@link UUID}.
	 * <p>
	 * If there is no such {@link IEmfStaticRole}, {@link Optional#empty()} is
	 * returned.
	 *
	 * @param <R>
	 *            the row type of both the {@link IEmfTable} and the returned
	 *            {@link IEmfStaticRole}
	 * @param table
	 *            the {@link IEmfTable} for which the registered
	 *            {@link IEmfStaticRole} instance shall be retrieved (never
	 *            <i>null</i>)
	 * @param roleUuid
	 *            the {@link UUID} of the {@link IEmfStaticRole} instance that
	 *            shall be retrieved (never <i>null</i>)
	 * @return the {@link IEmfStaticRole} instance that is registered for the
	 *         given {@link IEmfTable} and {@link UUID}
	 */
	<R extends IEmfTableRow<R, ?>> Optional<IEmfStaticRole<R>> getStaticRole(IEmfTable<R, ?, ?> table, UUID roleUuid);

	// -------------------- atomic roles -------------------- //

	/**
	 * Returns all registered atomic {@link IEmfRole} instances.
	 *
	 * @return all registered atomic {@link IEmfRole} instances (never
	 *         <i>null</i>)
	 */
	Collection<IEmfRole<?>> getAtomicRoles();

	/**
	 * Determines the atomic {@link IEmfRole} instances that are registered for
	 * the given {@link IEmfTable}.
	 *
	 * @param table
	 *            the {@link IEmfTable} for which registered atomic
	 *            {@link IEmfRole} instances shall be retrieved (never
	 *            <i>null</i>)
	 * @return the atomic {@link IEmfRole} instances that are registered for the
	 *         given {@link IEmfTable} (never <i>null</i>)
	 */
	Collection<IEmfRole<?>> getAtomicRoles(IEmfTable<?, ?, ?> table);

	/**
	 * Determines the atomic {@link IEmfRole} instances that are registered for
	 * the given {@link IEmfModule}.
	 *
	 * @param module
	 *            the {@link IEmfModule} for which registered atomic
	 *            {@link IEmfRole} instances shall be retrieved (never
	 *            <i>null</i>)
	 * @return the atomic {@link IEmfRole} instances that are registered for the
	 *         given {@link IEmfModule} (never <i>null</i>)
	 */
	Collection<IEmfRole<?>> getAtomicRoles(IEmfModule<?> module);

	/**
	 * Determines the {@link IEmfTable} instances for which the given atomic
	 * {@link IEmfRole} was registered.
	 *
	 * @param role
	 *            the atomic {@link IEmfRole} for which the registered tables
	 *            shall be retrieved (never <i>null</i>)
	 * @return the {@link IEmfTable} instances for which the given atomic
	 *         {@link IEmfRole} was registered (never <i>null</i>)
	 */
	Collection<IEmfTable<?, ?, ?>> getAtomicRoleTables(IEmfRole<?> role);

	// -------------------- miscellaneous -------------------- //

	/**
	 * Returns all {@link IEmfPage} instances for the given
	 * {@link IEmfModule}, mapped by {@link IEmfRole}.
	 *
	 * @param module
	 *            the {@link IEmfModule} for which the {@link IEmfPage}
	 *            instances shall be determined
	 * @return the {@link IEmfPage} instances for the given
	 *         {@link IEmfModule}, mapped by {@link IEmfRole} (never
	 *         <i>null</i>)
	 */
	Map<IEmfRole<?>, Collection<IEmfPage<?>>> getRoleToPagesMap(IEmfModule<?> module);
}
