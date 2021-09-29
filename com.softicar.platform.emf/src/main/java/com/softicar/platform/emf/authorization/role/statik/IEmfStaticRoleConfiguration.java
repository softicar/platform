package com.softicar.platform.emf.authorization.role.statik;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

// FIXME remove this interface (i80977)
public interface IEmfStaticRoleConfiguration<T> {

	UUID getUuid();

	IDisplayString getTitle();

	Collection<IEmfRole<T>> getImplyingRoles();

	default void validate() {

		Objects.requireNonNull(getUuid(), "Role UUID not defined.");
		Objects.requireNonNull(getTitle(), "Role title not defined.");
	}
}
