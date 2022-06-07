package com.softicar.platform.emf.permission;

import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.permission.statik.IEmfStaticPermission;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface of EMF permission registries which provide access to
 * {@link IEmfPermission} and {@link IEmfModulePermission} instances.
 *
 * @author Alexander Schmidt
 */
public interface IEmfPermissionRegistry {

	// -------------------- modules -------------------- //

	/**
	 * Returns the modules that are known to this registry.
	 *
	 * @return the modules that are known to this registry (never <i>null</i>)
	 */
	Collection<IEmfModule<?>> getModules();

	// -------------------- permissions -------------------- //

	/**
	 * Returns all registered {@link IEmfPermission} instances.
	 *
	 * @return all registered {@link IEmfPermission} instances (never
	 *         <i>null</i>)
	 */
	Collection<IEmfPermission<?>> getPermissions();

	/**
	 * Determines the {@link IEmfPermission} instances that are registered for
	 * the given {@link IEmfTable}.
	 *
	 * @param table
	 *            the {@link IEmfTable} for which registered
	 *            {@link IEmfPermission} instances shall be retrieved (never
	 *            <i>null</i>)
	 * @return the {@link IEmfPermission} instances that are registered for the
	 *         given {@link IEmfTable} (never <i>null</i>)
	 */
	Collection<IEmfPermission<?>> getPermissions(IEmfTable<?, ?, ?> table);

	/**
	 * Determines the {@link IEmfPermission} instances that are registered for
	 * the given {@link IEmfModule}.
	 *
	 * @param module
	 *            the {@link IEmfModule} for which registered
	 *            {@link IEmfPermission} instances shall be retrieved (never
	 *            <i>null</i>)
	 * @return the {@link IEmfPermission} instances that are registered for the
	 *         given {@link IEmfModule} (never <i>null</i>)
	 */
	Collection<IEmfPermission<?>> getPermissions(IEmfModule<?> module);

	// -------------------- static permissions -------------------- //

	/**
	 * Determines the {@link IEmfStaticPermission} instances that are registered
	 * for the given {@link IEmfModule}.
	 *
	 * @param module
	 *            the {@link IEmfModule} for which registered
	 *            {@link IEmfStaticPermission} instances shall be retrieved
	 *            (never <i>null</i>)
	 * @return the {@link IEmfStaticPermission} instances that are registered
	 *         for the given {@link IEmfModule} (never <i>null</i>)
	 */
	Collection<IEmfStaticPermission<?>> getStaticPermissions(IEmfModule<?> module);

	/**
	 * Determines the {@link IEmfStaticPermission} that is registered for the
	 * given {@link UUID}, if any.
	 *
	 * @param permissionUuid
	 *            the {@link UUID} for which the registered
	 *            {@link IEmfStaticPermission} instance shall be retrieved
	 *            (never <i>null</i>)
	 * @return the {@link IEmfStaticPermission} that is registered for the given
	 *         {@link UUID}
	 */
	Optional<IEmfStaticPermission<?>> getStaticPermission(UUID permissionUuid);

	/**
	 * Determines all {@link IEmfStaticPermission} instances that are registered
	 * for the given {@link IEmfTable}.
	 *
	 * @param table
	 *            the {@link IEmfTable} for which registered
	 *            {@link IEmfStaticPermission} instances shall be retrieved
	 *            (never <i>null</i>)
	 * @return the {@link IEmfStaticPermission} instances that are registered
	 *         for the given {@link IEmfTable} (never <i>null</i>)
	 */
	Collection<IEmfStaticPermission<?>> getStaticPermissions(IEmfTable<?, ?, ?> table);

	/**
	 * Determines the type-parameterized {@link IEmfStaticPermission} instance
	 * that is registered for the given {@link IEmfTable} and {@link UUID}.
	 * <p>
	 * If there is no such {@link IEmfStaticPermission},
	 * {@link Optional#empty()} is returned.
	 *
	 * @param <R>
	 *            the row type of both the {@link IEmfTable} and the returned
	 *            {@link IEmfStaticPermission}
	 * @param table
	 *            the {@link IEmfTable} for which the registered
	 *            {@link IEmfStaticPermission} instance shall be retrieved
	 *            (never <i>null</i>)
	 * @param permissionUuid
	 *            the {@link UUID} of the {@link IEmfStaticPermission} instance
	 *            that shall be retrieved (never <i>null</i>)
	 * @return the {@link IEmfStaticPermission} instance that is registered for
	 *         the given {@link IEmfTable} and {@link UUID}
	 */
	<R extends IEmfTableRow<R, ?>> Optional<IEmfStaticPermission<R>> getStaticPermission(IEmfTable<R, ?, ?> table, UUID permissionUuid);

	// -------------------- atomic permissions -------------------- //

	/**
	 * Returns all registered atomic {@link IEmfPermission} instances.
	 *
	 * @return all registered atomic {@link IEmfPermission} instances (never
	 *         <i>null</i>)
	 */
	Collection<IEmfPermission<?>> getAtomicPermissions();

	/**
	 * Determines the atomic {@link IEmfPermission} instances that are
	 * registered for the given {@link IEmfTable}.
	 *
	 * @param table
	 *            the {@link IEmfTable} for which registered atomic
	 *            {@link IEmfPermission} instances shall be retrieved (never
	 *            <i>null</i>)
	 * @return the atomic {@link IEmfPermission} instances that are registered
	 *         for the given {@link IEmfTable} (never <i>null</i>)
	 */
	Collection<IEmfPermission<?>> getAtomicPermissions(IEmfTable<?, ?, ?> table);

	/**
	 * Determines the atomic {@link IEmfPermission} instances that are
	 * registered for the given {@link IEmfModule}.
	 *
	 * @param module
	 *            the {@link IEmfModule} for which registered atomic
	 *            {@link IEmfPermission} instances shall be retrieved (never
	 *            <i>null</i>)
	 * @return the atomic {@link IEmfPermission} instances that are registered
	 *         for the given {@link IEmfModule} (never <i>null</i>)
	 */
	Collection<IEmfPermission<?>> getAtomicPermissions(IEmfModule<?> module);

	/**
	 * Determines the {@link IEmfTable} instances for which the given atomic
	 * {@link IEmfPermission} was registered.
	 *
	 * @param permission
	 *            the atomic {@link IEmfPermission} for which the registered
	 *            tables shall be retrieved (never <i>null</i>)
	 * @return the {@link IEmfTable} instances for which the given atomic
	 *         {@link IEmfPermission} was registered (never <i>null</i>)
	 */
	Collection<IEmfTable<?, ?, ?>> getAtomicPermissionTables(IEmfPermission<?> permission);

	// -------------------- miscellaneous -------------------- //

	/**
	 * Returns all {@link IEmfPage} instances for the given {@link IEmfModule},
	 * mapped by {@link IEmfPermission}.
	 *
	 * @param module
	 *            the {@link IEmfModule} for which the {@link IEmfPage}
	 *            instances shall be determined
	 * @return the {@link IEmfPage} instances for the given {@link IEmfModule},
	 *         mapped by {@link IEmfPermission} (never <i>null</i>)
	 */
	Map<IEmfPermission<?>, Collection<IEmfPage<?>>> getPermissionToPagesMap(IEmfModule<?> module);
}
