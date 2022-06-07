package com.softicar.platform.emf.permission.statik;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.permission.IEmfPermission;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

public class EmfStaticPermissionConfiguration<T> implements IEmfStaticPermissionConfiguration<T> {

	private UUID uuid;
	private IDisplayString title;
	private final Collection<IEmfPermission<T>> implyingPermissions;

	public EmfStaticPermissionConfiguration() {

		this.uuid = null;
		this.title = null;
		this.implyingPermissions = new ArrayList<>();
	}

	public EmfStaticPermissionConfiguration<T> setUuid(UUID uuid) {

		this.uuid = Objects.requireNonNull(uuid);
		return this;
	}

	public EmfStaticPermissionConfiguration<T> setTitle(IDisplayString title) {

		this.title = Objects.requireNonNull(title);
		return this;
	}

	public EmfStaticPermissionConfiguration<T> impliedBy(IEmfPermission<T> implyingPermission) {

		implyingPermissions.add(implyingPermission);
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
	public Collection<IEmfPermission<T>> getImplyingPermissions() {

		return implyingPermissions;
	}
}
