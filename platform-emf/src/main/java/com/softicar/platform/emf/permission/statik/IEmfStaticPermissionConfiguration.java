package com.softicar.platform.emf.permission.statik;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.permission.IEmfPermission;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

public interface IEmfStaticPermissionConfiguration<T> {

	UUID getUuid();

	IDisplayString getTitle();

	Collection<IEmfPermission<T>> getImplyingPermissions();

	default void validate() {

		Objects.requireNonNull(getUuid(), "Permission UUID not defined.");
		Objects.requireNonNull(getTitle(), "Permission title not defined.");
	}
}
