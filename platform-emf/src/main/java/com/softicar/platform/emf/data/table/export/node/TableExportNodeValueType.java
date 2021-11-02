package com.softicar.platform.emf.data.table.export.node;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;

public enum TableExportNodeValueType {

	DAY(Day.class),
	DAYTIME(DayTime.class),
	NUMBER(Number.class),
	STRING(String.class);

	private final Class<?> clazz;

	private TableExportNodeValueType(Class<?> clazz) {

		this.clazz = clazz;
	}

	public Class<?> get() {

		return clazz;
	}

	/**
	 * @param valueType
	 * @return True if the given {@link TableExportNodeValueType} wraps a class
	 *         matching either the type or a super type of the class this
	 *         {@link TableExportNodeValueType} wraps.
	 */
	public boolean isA(TableExportNodeValueType valueType) {

		return valueType.get().isAssignableFrom(get());
	}
}