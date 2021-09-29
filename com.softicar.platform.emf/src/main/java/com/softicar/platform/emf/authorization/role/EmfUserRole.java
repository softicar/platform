package com.softicar.platform.emf.authorization.role;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.emf.authorization.EmfTableRowFieldMapper;

class EmfUserRole<R> implements IEmfRole<R> {

	private final ISqlField<R, ? extends IBasicUser> userField;

	public EmfUserRole(ISqlField<R, ? extends IBasicUser> userField) {

		this.userField = userField;
	}

	@Override
	public IDisplayString getTitle() {

		return new EmfTableRowFieldMapper<>(userField).getTitle();
	}

	@Override
	public boolean test(R tableRow, IBasicUser user) {

		return user.is(userField.getValue(tableRow));
	}

	@Override
	public int hashCode() {

		return userField.hashCode();
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof EmfUserRole<?>) {
			return userField.equals(((EmfUserRole<?>) object).userField);
		} else {
			return false;
		}
	}
}
