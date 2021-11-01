package com.softicar.platform.emf.authorization.role.statik;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.module.role.IEmfModuleRole;
import java.util.UUID;

public abstract class AbstractEmfStaticRoleBuilder<T, R, B extends AbstractEmfStaticRoleBuilder<T, R, B>> {

	private final EmfStaticRoleConfiguration<T> configuration;

	public AbstractEmfStaticRoleBuilder() {

		this.configuration = new EmfStaticRoleConfiguration<>();
	}

	protected abstract B getThis();

	protected abstract R createRole(IEmfStaticRoleConfiguration<T> configuration);

	/**
	 * Constructs a new {@link IEmfModuleRole} according to the given
	 * parameters.
	 *
	 * @return the new {@link IEmfModuleRole}
	 */
	public R build() {

		configuration.validate();
		return createRole(configuration);
	}

	/**
	 * Defines the {@link UUID} of the {@link IEmfModuleRole}.
	 *
	 * @param uuid
	 *            the {@link UUID} (never <i>null</i>)
	 * @return this
	 */
	public B setUuid(UUID uuid) {

		configuration.setUuid(uuid);
		return getThis();
	}

	/**
	 * Same as {@link #setUuid(UUID)} but calls {@link UUID#fromString(String)}
	 * to parse the given UUID string.
	 *
	 * @param uuid
	 *            the {@link UUID} as {@link String} (never <i>null</i>)
	 * @return this
	 */
	public B setUuid(String uuid) {

		return setUuid(UUID.fromString(uuid));
	}

	/**
	 * Defines the title of the {@link IEmfModuleRole}.
	 *
	 * @param title
	 *            the title (never <i>null</i>)
	 * @return this
	 */
	public B setTitle(IDisplayString title) {

		configuration.setTitle(title);
		return getThis();
	}

	/**
	 * Defines another {@link IEmfRole} to imply the role-under-construction.
	 * <p>
	 * As a result, if a user possesses the given {@link IEmfRole}, they are
	 * assumed to also possess the role-under-construction.
	 * <p>
	 * In a nutshell: <b>(given role) "is-a" (role-under-construction)</b>
	 *
	 * @param implyingRole
	 *            the implying role to add (never <i>null</i>)
	 * @return this
	 */
	public B impliedBy(IEmfRole<T> implyingRole) {

		configuration.impliedBy(implyingRole);
		return getThis();
	}
}
