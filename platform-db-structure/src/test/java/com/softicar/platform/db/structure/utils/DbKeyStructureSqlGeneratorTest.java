package com.softicar.platform.db.structure.utils;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.column.DbColumnStructure;
import com.softicar.platform.db.structure.key.DbKeyStructure;
import com.softicar.platform.db.structure.key.DbKeyType;
import com.softicar.platform.db.structure.key.IDbKeyStructure;
import com.softicar.platform.db.structure.table.DbTableStructure;
import java.util.Arrays;
import org.junit.Test;

public class DbKeyStructureSqlGeneratorTest extends AbstractTest {

	private static final String A = "A";
	private static final String B = "B";
	private static final String I = "I";
	private static final String U = "U";
	private final DbTableStructure tableStructure;
	private final IDbKeyStructure pk;
	private final IDbKeyStructure uk;
	private final IDbKeyStructure ik;

	public DbKeyStructureSqlGeneratorTest() {

		this.tableStructure = new DbTableStructure(new DbTableName("db", "test"));
		this.tableStructure.addColumnStructure(createColumn(tableStructure, A, SqlFieldType.INTEGER));
		this.tableStructure.addColumnStructure(createColumn(tableStructure, B, SqlFieldType.STRING));

		this.pk = createKey(tableStructure, null, DbKeyType.PRIMARY, A);
		this.uk = createKey(tableStructure, U, DbKeyType.UNIQUE, A, B);
		this.ik = createKey(tableStructure, I, DbKeyType.INDEX, B, A);
	}

	@Test
	public void test() {

		assertEquals("PRIMARY KEY (`A`)", new DbKeyStructureSqlGenerator(pk).toString());
		assertEquals("UNIQUE KEY `U` (`A`, `B`)", new DbKeyStructureSqlGenerator(uk).toString());
		assertEquals("KEY `I` (`B`, `A`)", new DbKeyStructureSqlGenerator(ik).toString());
	}

	@Test
	public void testWithH2Database() {

		assertEquals("PRIMARY KEY (`A`)", new DbKeyStructureSqlGenerator(pk).setH2Database(true).toString());
		assertEquals("UNIQUE KEY `db$test$U` (`A`, `B`)", new DbKeyStructureSqlGenerator(uk).setH2Database(true).toString());
		assertEquals("KEY `db$test$I` (`B`, `A`)", new DbKeyStructureSqlGenerator(ik).setH2Database(true).toString());
	}

	private DbColumnStructure createColumn(DbTableStructure tableStructure, String name, SqlFieldType fieldType) {

		return new DbColumnStructure(tableStructure)//
			.setName(name)
			.setFieldType(fieldType);
	}

	private DbKeyStructure createKey(DbTableStructure tableStructure, String name, DbKeyType type, String...columnNames) {

		return new DbKeyStructure(tableStructure)//
			.setName(name)
			.setType(type)
			.addColumnNames(Arrays.asList(columnNames));
	}
}
