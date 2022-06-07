package com.softicar.platform.emf.permission.statik;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.permission.IEmfPermission;
import java.util.UUID;

public abstract class AbstractEmfStaticPermissionBuilder<T, R, B extends AbstractEmfStaticPermissionBuilder<T, R, B>> {

	private final EmfStaticPermissionConfiguration<T> configuration;

	public AbstractEmfStaticPermissionBuilder() {

		this.configuration = new EmfStaticPermissionConfiguration<>();
	}

	protected abstract B getThis();

	protected abstract R createPermission(IEmfStaticPermissionConfiguration<T> configuration);

	/**
	 * Constructs a new {@link IEmfModulePermission} according to the given
	 * parameters.
	 *
	 * @return the new {@link IEmfModulePermission}
	 */
	public R build() {

		configuration.validate();
		return createPermission(configuration);
	}

	/**
	 * Defines the {@link UUID} of the {@link IEmfModulePermission}.
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
	 * Defines the title of the {@link IEmfModulePermission}.
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
	 * Defines another {@link IEmfPermission} to imply the
	 * permission-under-construction.
	 * <p>
	 * As a result, if a user possesses the given {@link IEmfPermission}, they
	 * are assumed to also possess the permission-under-construction.
	 * <p>
	 * In a nutshell: <b>(given permission) "is-a"
	 * (permission-under-construction)</b>
	 *
	 * @param implyingPermission
	 *            the implying permission to add (never <i>null</i>)
	 * @return this
	 */
	public B impliedBy(IEmfPermission<T> implyingPermission) {

		configuration.impliedBy(implyingPermission);
		return getThis();
	}
}
