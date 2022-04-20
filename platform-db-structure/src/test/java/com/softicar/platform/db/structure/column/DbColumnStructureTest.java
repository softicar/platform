package com.softicar.platform.db.structure.column;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.table.DbTableStructure;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import org.junit.Test;

public class DbColumnStructureTest extends AbstractTest {

	private final DbTableName tableName;
	private final IDbTableStructure tableStructure;

	public DbColumnStructureTest() {

		this.tableName = new DbTableName("db", "test");
		this.tableStructure = new DbTableStructure(tableName);
	}

	@Test
	public void testIsIgnored() {

		IDbColumnStructure withOverride = createIntegerColumn().setComment("@ignore@");
		IDbColumnStructure withoutOverride = createIntegerColumn();

		assertTrue(withOverride.isIgnored());
		assertFalse(withoutOverride.isIgnored());
	}

	private DbColumnStructure createIntegerColumn() {

		return new DbColumnStructure(tableStructure)//
			.setName("foo")
			.setFieldType(SqlFieldType.INTEGER);
	}
}
