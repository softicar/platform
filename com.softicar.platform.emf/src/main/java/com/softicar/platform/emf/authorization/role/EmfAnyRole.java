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
 * Aggregates several roles into a disjunction.
 * <p>
 * This role applies to a user if at least one sub-role applies.
 *
 * @author Oliver Richers
 */
public class EmfAnyRole<R> implements IEmfRole<R> {

	private final Collection<IEmfRole<R>> roles;

	@SafeVarargs
	public EmfAnyRole(IEmfRole<R>...roles) {

		this(Arrays.asList(roles));
	}

	public EmfAnyRole(Collection<IEmfRole<R>> roles) {

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
			.collect(new DisplayStringJoiningCollector(new DisplayString(EmfI18n.OR).setEnforceUpperCase().enclose(" ")));
	}

	@Override
	public boolean test(R tableRow, IBasicUser user) {

		return tableRow != null && roles//
			.stream()
			.anyMatch(role -> role.test(tableRow, user));
	}

	@Override
	public String toString() {

		return roles//
			.stream()
			.map(IEmfRole::toString)
			.collect(Collectors.joining(EmfI18n.OR.enclose(" ").toString()));
	}

	@Override
	public void accept(IEmfRoleVisitor<R> visitor) {

		visitor.visitAny(roles);
	}
}
