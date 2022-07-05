package com.softicar.platform.emf.attribute.field.enums.table.row;

import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;

public enum EmfTestEnum implements IDbEnumTableRowEnum<EmfTestEnum, EmfTestEnumRow> {

	ONE(1, "One"),
	TWO(2, "Two"),
	THREE(3, "Three"),
	FOUR(4, "Four"),
	FIVE(5, "Five"),
	//
	;

	private Integer id;
	private String name;

	private EmfTestEnum(Integer id, String name) {

		this.id = id;
		this.name = name;
	}

	@Override
	public DbEnumTable<EmfTestEnumRow, EmfTestEnum> getTable() {

		return EmfTestEnumRow.TABLE;
	}

	@Override
	public Integer getId() {

		return id;
	}

	@Override
	public void setFields(ISqlFieldValueConsumer<EmfTestEnumRow> consumer) {

		consumer.consumeFieldValue(EmfTestEnumRow.ID, id);
		consumer.consumeFieldValue(EmfTestEnumRow.NAME, name);
	}
}
