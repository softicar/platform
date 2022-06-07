package com.softicar.platform.emf.permission;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.DisplayStringJoiningCollector;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.EmfI18n;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Aggregates several {@link IEmfPermission} objects into a conjunction.
 * <p>
 * This {@link IEmfPermission} only applies if all contained
 * {@link IEmfPermission} objects apply.
 *
 * @author Oliver Richers
 */
public class EmfAllPermissions<R> implements IEmfPermission<R> {

	private final Collection<IEmfPermission<R>> permissions;

	@SafeVarargs
	public EmfAllPermissions(IEmfPermission<R>...permissions) {

		this(Arrays.asList(permissions));
	}

	public EmfAllPermissions(Collection<IEmfPermission<R>> permissions) {

		this.permissions = permissions;
	}

	public Collection<IEmfPermission<R>> getPermissions() {

		return permissions;
	}

	@Override
	public IDisplayString getTitle() {

		return permissions//
			.stream()
			.map(IEmfPermission::getTitle)
			.collect(new DisplayStringJoiningCollector(new DisplayString(EmfI18n.AND).setEnforceUpperCase().enclose(" ")));
	}

	@Override
	public boolean test(R tableRow, IBasicUser user) {

		return tableRow != null && permissions//
			.stream()
			.allMatch(permission -> permission.test(tableRow, user));
	}

	@Override
	public String toString() {

		return permissions//
			.stream()
			.map(IEmfPermission::toString)
			.collect(Collectors.joining(EmfI18n.AND.enclose(" ").toString()));
	}

	@Override
	public void accept(IEmfPermissionVisitor<R> visitor) {

		visitor.visitAll(permissions);
	}
}
