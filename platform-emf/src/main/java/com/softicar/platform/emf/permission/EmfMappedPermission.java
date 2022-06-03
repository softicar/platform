package com.softicar.platform.emf.permission;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.mapper.IEmfTableRowMapper;
import java.util.Optional;

public class EmfMappedPermission<S, T> implements IEmfPermission<S> {

	private final IEmfPermission<T> permission;
	private final IEmfTableRowMapper<S, T> mapper;

	public EmfMappedPermission(IEmfPermission<T> permission, IEmfTableRowMapper<S, T> mapper) {

		this.permission = permission;
		this.mapper = mapper;
	}

	@Override
	public IDisplayString getTitle() {

		return EmfI18n.ARG1_OF_ARG2.toDisplay(permission.getTitle(), mapper.getTitle());
	}

	@Override
	public boolean test(S entity, IBasicUser user) {

		return Optional//
			.ofNullable(entity)
			.flatMap(mapper::apply)
			.map(targetEntity -> permission.test(targetEntity, user))
			.orElse(false);
	}

	@Override
	public String toString() {

		return getTitle().toString();
	}

	@Override
	public void accept(IEmfPermissionVisitor<S> visitor) {

		visitor.visitMapped(permission, mapper);
	}
}
