package com.softicar.platform.db.sql.test;

import com.softicar.platform.common.core.item.BasicItemComparator;
import com.softicar.platform.common.core.item.IBasicItem;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.db.sql.fields.ISqlIntegerField;
import com.softicar.platform.db.sql.fields.ISqlStringField;

public class SqlTestEntity implements IBasicItem {

	public static final SqlTestTable<SqlTestEntity> TABLE = new SqlTestTable<>("db", "test", SqlTestEntity.class);
	public static final ISqlIntegerField<SqlTestEntity> ID = TABLE.addIdField("id");
	public static final ISqlIntegerField<SqlTestEntity> INTEGER = TABLE.addIntegerField("Integer");
	public static final ISqlStringField<SqlTestEntity> STRING = TABLE.addStringField("String");
	public static final ISqlForeignRowField<SqlTestEntity, SqlTestEntity, Integer> FOREIGN = TABLE.addForeignField(SqlTestEntity.ID, "Foreign");
	private final Integer id;

	public SqlTestEntity(Integer id) {

		this.id = id;
	}

	@Override
	public Integer getId() {

		return id;
	}

	@Override
	public int compareTo(IBasicItem other) {

		return BasicItemComparator.get().compare(this, other);
	}
}
