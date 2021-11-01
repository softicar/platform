package com.softicar.platform.emf.authorization.role.statik;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.module.role.IEmfModuleRole;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Basic implementation of {@link IEmfModuleRole}.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public abstract class AbstractEmfStaticRole<T> implements IEmfStaticRole<T> {

	private final UUID roleUuid;
	private final IDisplayString title;
	private final Collection<IEmfRole<T>> inheritedRoles;

	public AbstractEmfStaticRole(IEmfStaticRoleConfiguration<T> configuration) {

		this.roleUuid = configuration.getUuid();
		this.title = configuration.getTitle();
		this.inheritedRoles = List.copyOf(configuration.getImplyingRoles());
	}

	/**
	 * FIXME i80598 This override should not be necessary because the UUID of
	 * any static role should be determined from an
	 * {@link EmfSourceCodeReferencePointUuid} annotation.
	 */
	@Override
	public UUID getAnnotatedUuid() {

		return roleUuid;
	}

	@Override
	public IDisplayString getTitle() {

		return title;
	}

	@Override
	public Collection<IEmfRole<T>> getInheritedRoles() {

		return inheritedRoles;
	}

	@Override
	public int hashCode() {

		return Objects.hash(roleUuid);
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof AbstractEmfStaticRole) {
			var other = (AbstractEmfStaticRole<?>) object;
			return Objects.equals(roleUuid, other.roleUuid);
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(IEmfStaticRole<T> other) {

		return Comparator//
			.comparing(IEmfStaticRole<T>::getAnnotatedUuid)
			.compare(this, other);
	}

	protected boolean testInheritedRoles(T tableRow, IBasicUser user) {

		return inheritedRoles.stream().anyMatch(role -> role.test(tableRow, user));
	}
}
