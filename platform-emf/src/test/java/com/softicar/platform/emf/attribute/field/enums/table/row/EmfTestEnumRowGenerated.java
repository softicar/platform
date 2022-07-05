package com.softicar.platform.emf.attribute.field.enums.table.row;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.enums.AbstractDbEnumTableRow;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;

public class EmfTestEnumRowGenerated extends AbstractDbEnumTableRow<EmfTestEnumRow, EmfTestEnum> {

	// @formatter:off
	public static final DbObjectTableBuilder<EmfTestEnumRow, EmfTestEnumRowGenerated> BUILDER = new DbObjectTableBuilder<>("Test", "Enum", EmfTestEnumRow::new, EmfTestEnumRow.class);
	static {
		BUILDER.setTitle(IDisplayString.create("Test Enum"));
		BUILDER.setTitle(IDisplayString.create("Test Enums"));
	}
	public static final IDbIdField<EmfTestEnumRow> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbStringField<EmfTestEnumRow> NAME = BUILDER.addStringField("name", o -> o.name, (o, v) -> o.name = v).setNullable().setDefault(null);
	public static final DbEnumTable<EmfTestEnumRow, EmfTestEnum> TABLE = new DbEnumTable<>(BUILDER, EmfTestEnum.values());
	// @formatter:on

	private Integer id;
	private String name;

	public EmfTestEnumRow setName(String name) {

		return setValue(NAME, name);
	}

	public String getName() {

		return getValue(NAME);
	}

	@Override
	public DbEnumTable<EmfTestEnumRow, EmfTestEnum> table() {

		return TABLE;
	}
}
