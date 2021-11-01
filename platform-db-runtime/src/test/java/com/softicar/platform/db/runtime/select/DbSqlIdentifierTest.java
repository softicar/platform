package com.softicar.platform.db.runtime.select;

import com.softicar.platform.db.core.connection.quirks.IDbServerQuirks;
import com.softicar.platform.db.core.dbms.microsoft.sqlserver.DbMicrosoftSqlServer2000Quirks;
import com.softicar.platform.db.core.dbms.mysql.DbMysqlQuirks;
import org.junit.Assert;
import org.junit.Test;

public class DbSqlIdentifierTest extends Assert {

	@Test
	public void testToStringWithSqlServer2000() {

		IDbServerQuirks quirks = new DbMicrosoftSqlServer2000Quirks();

		assertEquals("foo", new DbSqlIdentifier(quirks, "foo").toString());
		assertEquals("foo$bar", new DbSqlIdentifier(quirks, "foo$bar").toString());
		assertEquals("[1foo]", new DbSqlIdentifier(quirks, "1foo").toString());
		assertEquals("[change]", new DbSqlIdentifier(quirks, "change").toString());
		assertEquals("[select]", new DbSqlIdentifier(quirks, "select").toString());
		assertEquals("[foo bar]", new DbSqlIdentifier(quirks, "foo bar").toString());
	}

	@Test
	public void testToStringWithMySql() {

		IDbServerQuirks quirks = new DbMysqlQuirks();

		assertEquals("foo", new DbSqlIdentifier(quirks, "foo").toString());
		assertEquals("foo$bar", new DbSqlIdentifier(quirks, "foo$bar").toString());
		assertEquals("`1foo`", new DbSqlIdentifier(quirks, "1foo").toString());
		assertEquals("`change`", new DbSqlIdentifier(quirks, "change").toString());
		assertEquals("`select`", new DbSqlIdentifier(quirks, "select").toString());
		assertEquals("`foo bar`", new DbSqlIdentifier(quirks, "foo bar").toString());
	}
}
