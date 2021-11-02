package com.softicar.platform.db.sql.example;

import com.softicar.platform.common.core.item.IBasicItem;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.db.sql.fields.ISqlIntegerField;
import com.softicar.platform.db.sql.fields.ISqlStringField;
import com.softicar.platform.db.sql.test.SqlTestTable;

public class SqlExampleDelivery implements IBasicItem {

	// @formatter:off
	public static final SqlTestTable<SqlExampleDelivery> TABLE = new SqlTestTable<>("example", "delivery", SqlExampleDelivery.class);
	public static final ISqlIntegerField<SqlExampleDelivery> ID = TABLE.addIdField("id");
	public static final ISqlForeignRowField<SqlExampleDelivery, SqlExampleSupplier, Integer> SUPPLIER = TABLE.addForeignField(SqlExampleSupplier.ID, "supplier");
	public static final ISqlStringField<SqlExampleDelivery> NUMBER = TABLE.addStringField("number");
	private final int id;
	// @formatter:on

	public SqlExampleDelivery(int id) {

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
