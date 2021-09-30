package com.softicar.platform.db.runtime.table.statements;

import com.softicar.platform.db.runtime.object.DbTestObject;
import com.softicar.platform.db.sql.statement.ISqlDelete;
import java.util.List;
import org.junit.Test;

public class DbTableDeleteTest extends AbstractDbStatementTest {

	@Test
	public void test() {

		createAndSaveObject(1, "foo");
		createAndSaveObject(1, "bar");
		createAndSaveObject(1, "bar");
		createAndSaveObject(1, "baz");

		ISqlDelete<DbTestObject> delete = DbTestObject.TABLE.createDelete();
		delete.where(DbTestObject.STRING_FIELD.isEqual("bar"));
		delete.execute();

		List<DbTestObject> objects = DbTestObject.TABLE.loadAll();
		assertEquals(2, objects.size());
		assertEquals("foo", objects.get(0).getString());
		assertEquals("baz", objects.get(1).getString());
	}
}
