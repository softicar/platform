package com.softicar.platform.db.runtime.structure;

import com.softicar.platform.db.runtime.enums.IDbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;

enum InternalEnumTestTableRowEnum implements IDbEnumTableRowEnum<InternalEnumTestTableRowEnum, InternalEnumTestTableRow> {

	FOO(1, "foo"),
	BAR(2, "bar"),
	BAZ(3, "baz"),
	//
	;

	private final int id;
	private final String name;

	private InternalEnumTestTableRowEnum(int id, String name) {

		this.id = id;
		this.name = name;
	}

	@Override
	public IDbEnumTable<InternalEnumTestTableRow, InternalEnumTestTableRowEnum> getTable() {

		return InternalEnumTestTableRow.TABLE;
	}

	@Override
	public Integer getId() {

		return id;
	}

	@Override
	public InternalEnumTestTableRow getRecord() {

		return InternalEnumTestTableRow.TABLE.get(id);
	}

	@Override
	public void setFields(ISqlFieldValueConsumer<InternalEnumTestTableRow> consumer) {

		consumer.consumeFieldValue(InternalEnumTestTableRow.ID, id);
		consumer.consumeFieldValue(InternalEnumTestTableRow.NAME, name);
	}

}
