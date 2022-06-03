package com.softicar.platform.emf.permission.statik;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Basic implementation of {@link IEmfModulePermission}.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public abstract class AbstractEmfStaticPermission<T> implements IEmfStaticPermission<T> {

	private final UUID permissionUuid;
	private final IDisplayString title;
	private final Collection<IEmfPermission<T>> inheritedPermissions;

	public AbstractEmfStaticPermission(IEmfStaticPermissionConfiguration<T> configuration) {

		this.permissionUuid = configuration.getUuid();
		this.title = configuration.getTitle();
		this.inheritedPermissions = List.copyOf(configuration.getImplyingPermissions());
	}

	/**
	 * FIXME i80598 This override should not be necessary because the UUID of
	 * any static permission should be determined from an
	 * {@link EmfSourceCodeReferencePointUuid} annotation.
	 */
	@Override
	public UUID getAnnotatedUuid() {

		return permissionUuid;
	}

	@Override
	public IDisplayString getTitle() {

		return title;
	}

	@Override
	public Collection<IEmfPermission<T>> getInheritedPermissions() {

		return inheritedPermissions;
	}

	@Override
	public int hashCode() {

		return Objects.hash(permissionUuid);
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof AbstractEmfStaticPermission) {
			var other = (AbstractEmfStaticPermission<?>) object;
			return Objects.equals(permissionUuid, other.permissionUuid);
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(IEmfStaticPermission<T> other) {

		return Comparator//
			.comparing(IEmfStaticPermission<T>::getAnnotatedUuid)
			.compare(this, other);
	}

	protected boolean testInheritedPermissions(T tableRow, IBasicUser user) {

		return inheritedPermissions.stream().anyMatch(permission -> permission.test(tableRow, user));
	}
}
