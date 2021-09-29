package com.softicar.platform.db.sql.example;

import com.softicar.platform.common.core.item.IBasicItem;
import com.softicar.platform.db.sql.fields.ISqlIntegerField;
import com.softicar.platform.db.sql.fields.ISqlStringField;
import com.softicar.platform.db.sql.test.SqlTestTable;

public class SqlExampleArticle implements IBasicItem {

	public static final SqlTestTable<SqlExampleArticle> TABLE = new SqlTestTable<>("example", "article", SqlExampleArticle.class);
	public static final ISqlIntegerField<SqlExampleArticle> ID = TABLE.addIdField("id");
	public static final ISqlStringField<SqlExampleArticle> NUMBER = TABLE.addStringField("number");
	private final int id;

	public SqlExampleArticle(int id) {

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
