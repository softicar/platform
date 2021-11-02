package com.softicar.platform.db.sql.example;

import com.softicar.platform.common.core.item.IBasicItem;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.db.sql.fields.ISqlIntegerField;
import com.softicar.platform.db.sql.test.SqlTestTable;

public class SqlExampleDeliveryItem implements IBasicItem {

	// @formatter:off
	public static final SqlTestTable<SqlExampleDeliveryItem> TABLE = new SqlTestTable<>("example", "deliveryItem", SqlExampleDeliveryItem.class);
	public static final ISqlIntegerField<SqlExampleDeliveryItem> ID = TABLE.addIdField("id");
	public static final ISqlForeignRowField<SqlExampleDeliveryItem, SqlExampleDelivery, Integer> DELIVERY = TABLE.addForeignField(SqlExampleDelivery.ID, "delivery");
	public static final ISqlForeignRowField<SqlExampleDeliveryItem, SqlExampleArticle, Integer> ARTICLE = TABLE.addForeignField(SqlExampleArticle.ID, "article");
	public static final ISqlIntegerField<SqlExampleDeliveryItem> QUANTITY = TABLE.addIntegerField("quantity");
	private final int id;
	// @formatter:on

	public SqlExampleDeliveryItem(int id) {

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
