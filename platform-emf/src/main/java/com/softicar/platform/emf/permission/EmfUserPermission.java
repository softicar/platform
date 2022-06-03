package com.softicar.platform.emf.permission;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.emf.mapper.EmfTableRowFieldMapper;

class EmfUserPermission<R> implements IEmfPermission<R> {

	private final ISqlField<R, ? extends IBasicUser> userField;

	public EmfUserPermission(ISqlField<R, ? extends IBasicUser> userField) {

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

		if (object instanceof EmfUserPermission<?>) {
			return userField.equals(((EmfUserPermission<?>) object).userField);
		} else {
			return false;
		}
	}
}
