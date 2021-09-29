package com.softicar.platform.db.sql.example;

import com.softicar.platform.common.core.item.IBasicItem;
import com.softicar.platform.db.sql.fields.ISqlIntegerField;
import com.softicar.platform.db.sql.fields.ISqlStringField;
import com.softicar.platform.db.sql.test.SqlTestTable;

public class SqlExampleSupplier implements IBasicItem {

	public static final SqlTestTable<SqlExampleSupplier> TABLE = new SqlTestTable<>("example", "supplier", SqlExampleSupplier.class);
	public static final ISqlIntegerField<SqlExampleSupplier> ID = TABLE.addIdField("id");
	public static final ISqlStringField<SqlExampleSupplier> NAME = TABLE.addStringField("name");
	private final int id;

	public SqlExampleSupplier(int id) {

		this.id = id;
	}

	@Override
	public int compareTo(IBasicItem other) {

		return getItemId().compareTo(other.getItemId());
	}

	@Override
	public Integer getId() {

		return id;
	}
}
