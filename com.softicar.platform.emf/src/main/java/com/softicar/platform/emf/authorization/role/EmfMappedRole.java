package com.softicar.platform.emf.authorization.role;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.authorization.IEmfTableRowMapper;
import java.util.Optional;

public class EmfMappedRole<S, T> implements IEmfRole<S> {

	private final IEmfRole<T> role;
	private final IEmfTableRowMapper<S, T> mapper;

	public EmfMappedRole(IEmfRole<T> role, IEmfTableRowMapper<S, T> mapper) {

		this.role = role;
		this.mapper = mapper;
	}

	@Override
	public IDisplayString getTitle() {

		return EmfI18n.ARG1_OF_ARG2.toDisplay(role.getTitle(), mapper.getTitle());
	}

	@Override
	public boolean test(S entity, IBasicUser user) {

		return Optional//
			.ofNullable(entity)
			.flatMap(mapper::apply)
			.map(targetEntity -> role.test(targetEntity, user))
			.orElse(false);
	}

	@Override
	public String toString() {

		return getTitle().toString();
	}

	@Override
	public void accept(IEmfRoleVisitor<S> visitor) {

		visitor.visitMapped(role, mapper);
	}
}
