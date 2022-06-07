package com.softicar.platform.emf.permission;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.emf.mapper.EmfTableRowFieldMapper;
import com.softicar.platform.emf.mapper.IEmfTableRowMapper;
import java.util.Collection;

public interface IEmfPermission<T> extends IDisplayable {

	IDisplayString getTitle();

	@Override
	default IDisplayString toDisplay() {

		return getTitle();
	}

	boolean test(T tableRow, IBasicUser user);

	default IEmfPermission<T> not() {

		return new EmfNotPermission<>(this);
	}

	default <X> IEmfPermission<X> of(IEmfTableRowMapper<X, T> mapper) {

		return new EmfMappedPermission<>(this, mapper);
	}

	default <X> IEmfPermission<X> of(ISqlForeignRowField<X, T, ?> field) {

		return new EmfMappedPermission<>(this, new EmfTableRowFieldMapper<>(field));
	}

	default void accept(IEmfPermissionVisitor<T> visitor) {

		visitor.visitAtom(this);
	}

	default IEmfPermission<T> prefetchData(Collection<T> entities) {

		DevNull.swallow(entities);
		return this;
	}
}
