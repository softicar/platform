package com.softicar.platform.emf.authorization.role;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.emf.authorization.EmfTableRowFieldMapper;
import com.softicar.platform.emf.authorization.IEmfTableRowMapper;
import java.util.Collection;

public interface IEmfRole<T> extends IDisplayable {

	IDisplayString getTitle();

	@Override
	default IDisplayString toDisplay() {

		return getTitle();
	}

	boolean test(T tableRow, IBasicUser user);

	default IEmfRole<T> not() {

		return new EmfNotRole<>(this);
	}

	default <X> IEmfRole<X> of(IEmfTableRowMapper<X, T> mapper) {

		return new EmfMappedRole<>(this, mapper);
	}

	default <X> IEmfRole<X> of(ISqlForeignRowField<X, T, ?> field) {

		return new EmfMappedRole<>(this, new EmfTableRowFieldMapper<>(field));
	}

	default void accept(IEmfRoleVisitor<T> visitor) {

		visitor.visitAtom(this);
	}

	default IEmfRole<T> prefetchData(Collection<T> entities) {

		DevNull.swallow(entities);
		return this;
	}
}
