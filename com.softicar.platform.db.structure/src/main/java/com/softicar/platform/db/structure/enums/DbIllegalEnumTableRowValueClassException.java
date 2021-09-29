package com.softicar.platform.db.structure.enums;

public class DbIllegalEnumTableRowValueClassException extends IllegalArgumentException {

	public DbIllegalEnumTableRowValueClassException(Class<?> clazz) {

		super(String.format("Illegal enum table row value of type %s.", clazz.getCanonicalName()));
	}
}
