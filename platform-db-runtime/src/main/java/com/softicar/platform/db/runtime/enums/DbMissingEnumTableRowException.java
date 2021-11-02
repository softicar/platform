package com.softicar.platform.db.runtime.enums;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;

public class DbMissingEnumTableRowException extends SofticarDeveloperException {

	public DbMissingEnumTableRowException(IDbEnumTableRowEnum<?, ?> tableRowEnum) {

		super(//
			"Enum table %s: Failed to load enum table row with id %s from table %s.",
			tableRowEnum.getClass().getCanonicalName(),
			tableRowEnum.getId(),
			tableRowEnum.getTable().getFullName());
	}
}
