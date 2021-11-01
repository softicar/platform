package com.softicar.platform.emf.module;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.item.ItemId;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.module.registry.IEmfModuleRegistry;
import com.softicar.platform.emf.module.role.IEmfModuleRole;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface of a module instance entry.
 * <p>
 * An {@link IEmfModuleInstance} provides the root scope for all data contained
 * in the database tables of an {@link IEmfModule}. Only static reference data
 * is exempt from this scoping.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public interface IEmfModuleInstance<I extends IEmfModuleInstance<I>> {

	/**
	 * Returns the {@link ItemId} of this module instance.
	 *
	 * @return the {@link ItemId} (never null)
	 */
	ItemId getItemId();

	/**
	 * Returns the {@link IUuid} of this {@link IEmfModuleInstance}.
	 *
	 * @return the {@link IUuid} of this {@link IEmfModuleInstance} (never null)
	 */
	IUuid getModuleUuid();

	/**
	 * Determines the {@link IEmfModule} that matches the {@link IUuid} of this
	 * {@link IEmfModuleInstance}, from the module registry.
	 *
	 * @return the module that matches the {@link IUuid} of this
	 *         {@link IEmfModuleInstance} (never null)
	 * @throws RuntimeException
	 *             if no {@link IEmfModule} can be found for the {@link IUuid}
	 *             of this {@link IEmfModuleInstance}
	 */
	default IEmfModule<?> getModuleOrThrow() {

		UUID moduleUuid = getModuleUuid().getUuid();
		return getModule().orElseThrow(() -> new SofticarUserException(EmfI18n.FAILED_TO_DETERMINE_THE_MODULE_FOR_UUID_ARG1.toDisplay(moduleUuid)));
	}

	/**
	 * Determines the {@link IEmfModule} that matches the {@link IUuid} of this
	 * {@link IEmfModuleInstance}, from the module registry.
	 *
	 * @return the module that matches the {@link IUuid} of this
	 *         {@link IEmfModuleInstance} (empty if no matching module could be
	 *         found)
	 */
	default Optional<IEmfModule<?>> getModule() {

		return Optional//
			.ofNullable(getModuleUuid())
			.map(IUuid::getUuid)
			.map(IEmfModuleRegistry.get()::getModule)
			.orElse(Optional.empty());
	}

	/**
	 * Determines the name of the module of this {@link IEmfModuleInstance}.
	 *
	 * @return the name of the module of this {@link IEmfModuleInstance}, or
	 *         {@link EmfI18n#UNKNOWN_MODULE} if it could not be determined
	 *         (never null)
	 */
	default String getModuleName() {

		return Optional//
			.of(getModuleOrThrow())
			.map(IEmfModule::toDisplay)
			.orElse(EmfI18n.UNKNOWN_MODULE)
			.toString();
	}

	/**
	 * Tests if the given user possesses the specified {@link IEmfModuleRole}.
	 *
	 * @param role
	 *            the role to test (never <i>null</i>)
	 * @param user
	 *            the user to test (never <i>null</i>)
	 * @return <i>true</i> if the user possesses the specified
	 *         {@link IEmfModuleRole}; <i>false</i> otherwise
	 */
	boolean hasRole(IEmfModuleRole<I> role, IBasicUser user);
}
