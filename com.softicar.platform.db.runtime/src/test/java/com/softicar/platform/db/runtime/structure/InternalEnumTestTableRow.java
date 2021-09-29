package com.softicar.platform.db.runtime.structure;

import com.softicar.platform.db.runtime.enums.AbstractDbEnumTableRow;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTable;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;

class InternalEnumTestTableRow extends AbstractDbEnumTableRow<InternalEnumTestTableRow, InternalEnumTestTableRowEnum> {

	public static final DbObjectTableBuilder<InternalEnumTestTableRow, InternalEnumTestTableRow> BUILDER =
			new DbObjectTableBuilder<>("db", "foo", InternalEnumTestTableRow::new, InternalEnumTestTableRow.class);
	public static final IDbIdField<InternalEnumTestTableRow> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbStringField<InternalEnumTestTableRow> NAME = BUILDER.addStringField("name", o -> o.name, (o, v) -> o.name = v).setMaximumLength(255);
	public static final DbEnumTable<InternalEnumTestTableRow, InternalEnumTestTableRowEnum> TABLE =
			new DbEnumTable<>(BUILDER, InternalEnumTestTableRowEnum.values());

	private Integer id;
	private String name;

	@Override
	public IDbEnumTable<InternalEnumTestTableRow, InternalEnumTestTableRowEnum> table() {

		return TABLE;
	}
}
