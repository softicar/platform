package com.softicar.platform.emf.authorization.role.statik;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

public class EmfStaticRoleConfiguration<T> implements IEmfStaticRoleConfiguration<T> {

	private UUID uuid;
	private IDisplayString title;
	private final Collection<IEmfRole<T>> implyingRoles;

	public EmfStaticRoleConfiguration() {

		this.uuid = null;
		this.title = null;
		this.implyingRoles = new ArrayList<>();
	}

	public EmfStaticRoleConfiguration<T> setUuid(UUID uuid) {

		this.uuid = Objects.requireNonNull(uuid);
		return this;
	}

	public EmfStaticRoleConfiguration<T> setTitle(IDisplayString title) {

		this.title = Objects.requireNonNull(title);
		return this;
	}

	public EmfStaticRoleConfiguration<T> impliedBy(IEmfRole<T> implyingRole) {

		implyingRoles.add(implyingRole);
		return this;
	}

	@Override
	public UUID getUuid() {

		return uuid;
	}

	@Override
	public IDisplayString getTitle() {

		return title;
	}

	@Override
	public Collection<IEmfRole<T>> getImplyingRoles() {

		return implyingRoles;
	}
}
