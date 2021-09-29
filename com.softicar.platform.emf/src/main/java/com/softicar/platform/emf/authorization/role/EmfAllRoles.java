package com.softicar.platform.emf.authorization.role;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.DisplayStringJoiningCollector;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.EmfI18n;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Aggregates several roles into a conjunction.
 * <p>
 * This role only applies to a user if all sub-roles applies.
 *
 * @author Oliver Richers
 */
public class EmfAllRoles<R> implements IEmfRole<R> {

	private final Collection<IEmfRole<R>> roles;

	@SafeVarargs
	public EmfAllRoles(IEmfRole<R>...roles) {

		this(Arrays.asList(roles));
	}

	public EmfAllRoles(Collection<IEmfRole<R>> roles) {

		this.roles = roles;
	}

	public Collection<IEmfRole<R>> getRoles() {

		return roles;
	}

	@Override
	public IDisplayString getTitle() {

		return roles//
			.stream()
			.map(IEmfRole::getTitle)
			.collect(new DisplayStringJoiningCollector(new DisplayString(EmfI18n.AND).setEnforceUpperCase().enclose(" ")));
	}

	@Override
	public boolean test(R tableRow, IBasicUser user) {

		return tableRow != null && roles//
			.stream()
			.allMatch(role -> role.test(tableRow, user));
	}

	@Override
	public String toString() {

		return roles//
			.stream()
			.map(IEmfRole::toString)
			.collect(Collectors.joining(EmfI18n.AND.enclose(" ").toString()));
	}

	@Override
	public void accept(IEmfRoleVisitor<R> visitor) {

		visitor.visitAll(roles);
	}
}
